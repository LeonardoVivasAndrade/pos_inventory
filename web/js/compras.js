var listCompras = [];

$(document).ready(function () {
    //detectar si esta desde ventas
    if (window.location.pathname === "/compras") {
        loadCompras(moment().format('YYYY-MM-DD'), moment().format('YYYY-MM-DD'));
        $('li').removeClass('active');
        $('#lireportes').addClass('active');
    }
});

/*=============================================
    FUNCION CARGAR LISTA DE COMPRAS
 =============================================*/
function loadCompras(start, end) {
    var params = {
        option: "getListCompras",
        start: start,
        end: end
    };

    showLoader('Cargando compras');

    $.get("/CtrCompras.do", params, function (responseJson) {
        listCompras = responseJson;
        loadComprasTabla();
    }).done(function () {
        closeLoader();
    });
}



/*=============================================
 CARGAR LA TABLA DINÁMICA DE COMPRAS REALIZADAS
 =============================================*/

function loadComprasTabla() {
    var cantidadCompras = 0;
    var totalCompras = 0;
    
    $.each(listCompras, function (index, v) {
        var botones = '<div class="btn-group">\n\
                        <div class="tooltipnuevo"><button class="btn btn-primary btnVerCompra" data-toggle="modal" data-target="#modalDetalleCompra" data-toggle="tooltip" title="Ver Compra" numeroCompra="' + v.numero + '" idCompra="' + v.id + '"><i class="fa fa-eye"></i></button><span class="tooltiptext">Ver Compra</span></div>\n\
                        <div class="tooltipnuevo"><button class="btn btn-danger btnEliminarCompra" data-toggle="tooltip" title="Eliminar Compra"  idCompra="' + v.id + '"><i class="fa fa-times"></i></button><span class="tooltiptext">Anular Compra</span></div></div>';

        listCompras[index].botones = botones;
        cantidadCompras += listCompras[index].cantidad;
        totalCompras += listCompras[index].total;
    });
    
    $("#cantidadCompras").text("Total artículos: " + cantidadCompras);
    $("#totalCompras").text("Total Compras: $" + totalCompras.toLocaleString("de-DE"));

    $(".tablaCompras").DataTable().destroy();

    $(".tablaCompras").DataTable({
        data: listCompras,
        columns: [
            {data: 'numero'},
            {data: 'fecha'},
            {data: 'cantidad'},
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

//    var tabla = $(".tablaCompras").DataTable();
//    tabla.clear();
//    $.each(listCompras, function (index, v) {
//        //buttons action view and delete
//        var botones = '<div class="btn-group">\n\
//                        <div class="tooltipnuevo"><button class="btn btn-primary btnVerCompra" data-toggle="modal" data-target="#modalDetalleCompra" data-toggle="tooltip" title="Ver Compra" numeroCompra="' + v.numero + '" idCompra="' + v.id + '"><i class="fa fa-eye"></i></button><span class="tooltiptext">Ver Compra</span></div>\n\
//                        <div class="tooltipnuevo"><button class="btn btn-danger btnEliminarCompra" data-toggle="tooltip" title="Eliminar Compra"  idCompra="' + v.id + '"><i class="fa fa-times"></i></button><span class="tooltiptext">Anular Compra</span></div></div>';
//
//        tabla.row.add([
//            v.numero,
//            v.fecha,
//            v.cantidad,
//            v.total.toLocaleString("de-DE"),
//            botones
//        ]).draw(false);
//
//    });
//    tabla.order([0, 'desc']).draw();

//    setTimeout(() => {
//    }, 1000);
}

function deleteCompra(idCompra) {

    var params = {
        option: "deleteCompra",
        idCompra: idCompra
    };

    $.get("/CtrCompras.do", params, function (response) {
        var indexPage = $('.tablaCompras').DataTable().page();

        if (response.result === "success") {
            var id = Number(idCompra);
            var o = listCompras.find(result => result.idProducto === id); //extrae el objeto con el argumento idVenta
            var index = listCompras.indexOf(o); //busca el indice del objeto en el array
            listCompras.splice(index, 1); //elimina el objeto del array

            alertBottomEnd("Compra eliminada con éxito!", response.result);
            loadComprasTabla(); //redibuja la tabla
        } else if (response.result === "error") {
            alertBottomEnd("No se pudo eliminar la compra!", response.result);
        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo eliminar la compra!", response.result);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('.tablaCompras').DataTable().page(indexPage).draw('page');
    });
}

/*=============================================
 BORRAR COMPRA
 =============================================*/
$(".tablas").on("click", ".btnEliminarCompra", function () {

    var idCompra = $(this).attr("idCompra");

    Swal.fire({
        title: '¿Está seguro de anular la compra?',
        text: "¡Si no lo está puede cancelar la accíón!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, anular compra!'
    }).then((result) => {
        if (result.value)
            deleteCompra(idCompra);
    });

});

/*=============================================
 VER COMPRA
 =============================================*/
$(".tablas").on("click", ".btnVerCompra", function () {
    document.getElementsByClassName("productoDetalleCompra")[0].innerHTML = "<strong><h4>Productos vendidos</h4></strong>";

    var idCompra = $(this).attr("idCompra");
    var numeroCompra = $(this).attr("numeroCompra");

    $("#titleModalDetalleCompra").text("Detalle de compra #" + numeroCompra);

    var params = {
        option: "getDetalleCompra",
        idCompra: idCompra
    };


    $.get("/CtrCompras.do", params, function (responseJson) {
        var listProd = responseJson;
        var total = 0;

        $.each(listProd, function (index, p) {
            
            var costo = new Intl.NumberFormat('de-DE').format(p.costo);
            
            total += p.costo;
           

            $(".productoDetalleCompra").append(
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
                    '<input type="text" class="form-control" value="' + costo + '" readonly>' +
                    '</div>' +
                    '</div>' +
                    '</div>');

        });
        
        var totalFormated = new Intl.NumberFormat('de-DE').format(total);
        $(".productoDetalleCompra").append("<strong><h4>Total Compra: $ "+totalFormated+"</h4></strong>");
    });
});


/*=============================================
 RANGO DE FECHAS VENTAS
 =============================================*/

$('#daterange-btn2').daterangepicker(
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

            loadCompras(fechaInicial, fechaFinal);
        }
)



