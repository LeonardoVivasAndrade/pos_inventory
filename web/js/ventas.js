var listVentas = [];

$(document).ready(function () {
    //detectar si esta desde ventas
    if (window.location.pathname === "/ventas") {
        loadVentas(moment().format('YYYY-MM-DD'), moment().format('YYYY-MM-DD'));
        $('li').removeClass('active');
        $('#lireportes').addClass('active');
    }
});

/*=============================================
 FUNCION CARGAR LISTA DE VENTAS
 =============================================*/
function loadVentas(start, end) {
    var params = {
        option: "getListVentas",
        start: start,
        end: end
    };

    showLoader('Cargando ventas');

    $.get("/CtrVentas.do", params, function (responseJson) {
        listVentas = responseJson;
        loadVentasTabla();
    }).done(function () {
        closeLoader();
    });
}



/*=============================================
 CARGAR LA TABLA DINÁMICA DE VENTAS REALIZADAS
 =============================================*/

function loadVentasTabla() {
    var cantidadVentas = 0;
    var gananciaVentas = 0;
    var totalVentas = 0;
    
    $.each(listVentas, function (index, v) {
        var botones = '<div class="btn-group">\n\
                        <div class="tooltipnuevo"><button class="btn btn-primary btnVerVenta" data-toggle="modal" data-target="#modalDetalleVenta" data-toggle="tooltip" title="Ver Factura" numeroVenta="' + v.numero + '" idVenta="' + v.id + '"><i class="fa fa-eye"></i></button><span class="tooltiptext">Ver Factura</span></div>\n\
                        <div class="tooltipnuevo"><button class="btn btn-danger btnEliminarVenta" data-toggle="tooltip" title="Eliminar Factura"  idVenta="' + v.id + '"><i class="fa fa-times"></i></button><span class="tooltiptext">Anular Factura</span></div></div>';

        listVentas[index].botones = botones;
        cantidadVentas += listVentas[index].cantidad;
        gananciaVentas += listVentas[index].utilidad;
        totalVentas += listVentas[index].total;
    });
    
    $("#cantidadVentas").text("Total artículos: " + cantidadVentas);
    $("#gananciaVentas").text("Total Ganancia: $" + gananciaVentas.toLocaleString("de-DE"));
    $("#totalVentas").text("Total Venta: $" + totalVentas.toLocaleString("de-DE"));

    $(".tablaVentas").DataTable().destroy();

    $(".tablaVentas").DataTable({
        data: listVentas,
        columns: [
            {data: 'numero'},
            {data: 'fecha'},
            {data: 'cantidad'},
            {data: 'utilidad',
                render: $.fn.dataTable.render.number( '.', ',', 0, '$' )},
            {data: 'total',
                render: $.fn.dataTable.render.number( '.', ',', 0, '$' )},            
            {data: 'botones'}
        ],
        order: [0, 'desc'],
        "language": {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        }
    });
}

function deleteVenta(idVenta) {

    var params = {
        option: "deleteVenta",
        idVenta: idVenta
    };

    $.get("/CtrVentas.do", params, function (response) {
        var indexPage = $('.tablaVentas').DataTable().page();

        if (response.result === "success") {
            var id = Number(idVenta);
            var o = listVentas.find(result => result.idProducto === id); //extrae el objeto con el argumento idVenta
            var index = listVentas.indexOf(o); //busca el indice del objeto en el array
            listVentas.splice(index, 1); //elimina el objeto del array

            alertBottomEnd("Venta eliminada con éxito!", response.result);
            loadVentasTabla(); //redibuja la tabla
        } else if (response.result === "error") {
            alertBottomEnd("No se pudo eliminar la venta!", response.result);
        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo eliminar la venta!", response.result);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('.tablaVentas').DataTable().page(indexPage).draw('page');
    });
}

/*=============================================
 BORRAR VENTA
 =============================================*/
$(".tablas").on("click", ".btnEliminarVenta", function () {

    var idVenta = $(this).attr("idVenta");

    Swal.fire({
        title: '¿Está seguro de anular la venta?',
        text: "¡Si no lo está puede cancelar la accíón!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, anular venta!'
    }).then((result) => {
        if (result.value)
            deleteVenta(idVenta);
    });

});

/*=============================================
 VER VENTA
 =============================================*/
$(".tablas").on("click", ".btnVerVenta", function () {
    document.getElementsByClassName("productoDetalleVenta")[0].innerHTML = "<strong><h4>Productos vendidos</h4></strong>";

    var idVenta = $(this).attr("idVenta");
    var numeroVenta = $(this).attr("numeroVenta");

    $("#titleModalDetalleVenta").text("Detalle de venta #" + numeroVenta);

    var params = {
        option: "getDetalleVenta",
        idVenta: idVenta
    };


    $.get("/CtrVentas.do", params, function (responseJson) {
        var listProd = responseJson;
        var total = 0;

        $.each(listProd, function (index, p) {

            var precio = new Intl.NumberFormat('de-DE').format(p.precio);

            total += p.precio;


            $(".productoDetalleVenta").append(
                    '<div class="row" style="padding:5px 15px">' +
                    '<!-- Descripción del producto -->' +
                    '<div class="col-xs-7" style="padding-right:0px">' +
//                '<div class="input-group">' +
                    '<input type="text" class="form-control" value="' + p.descripcion + '" readonly required>' +
//                '</div>' +
                    '</div>' +
                    '<!-- Cantidad del producto -->' +
                    '<div class="col-xs-2">' +
                    '<input type="text" class="form-control" min="1" value="' + p.cantidad + '" readonly>' +
                    '</div>' +
                    '<!-- Precio del producto -->' +
                    '<div class="col-xs-3" style="padding-left:0px">' +
                    '<div class="input-group">' +
                    '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>' +
                    '<input type="text" class="form-control" value="' + precio + '" readonly>' +
                    '</div>' +
                    '</div>' +
                    '</div>');

        });

        var totalFormated = new Intl.NumberFormat('de-DE').format(total);
        $(".productoDetalleVenta").append("<strong><h4>Total Venta: $ " + totalFormated + "</h4></strong>");
    });
});


/*=============================================
 RANGO DE FECHAS VENTAS
 =============================================*/

$('#daterange-btn').daterangepicker(
        {
            ranges: {
                'Hoy': [moment(), moment()],
                'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Esta Semana': [moment().startOf('week').subtract(-1, 'days'), moment().endOf('week').subtract(-1, 'days')],
                'Últimos 15 días': [moment().subtract(14, 'days'), moment()],
                'Este mes': [moment().startOf('month'), moment().endOf('month')]
            },
            startDate: moment(),
            endDate: moment()
        },
        function (start, end) {
            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

            var fechaInicial = start.format('YYYY-MM-DD');
            var fechaFinal = end.format('YYYY-MM-DD');

            loadVentas(fechaInicial, fechaFinal);
        }
)