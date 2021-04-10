var listVentas = [];

$(document).ready(function () {
    //detectar si esta desde ventas
    if ((window.location.href).includes("ventas")) {
        loadVentas("", "", "default");
    }
});

/*=============================================
 FUNCION CARGAR LISTA DE VENTAS
 =============================================*/
function loadVentas(fecha1, fecha2, range) {
    var params = {
        option: "getListVentas",
        range: range,
        fechaStart: fecha1,
        fechaEnd: fecha2
    };

//    showLoader('Cargando ventas');

    $.get("/CtrVentas.do", params, function (responseJson) {
        listVentas = responseJson;
        loadVentasTabla();
    });
}



/*=============================================
 CARGAR LA TABLA DINÁMICA DE VENTAS REALIZADAS
 =============================================*/

function loadVentasTabla() {

    var tabla = $(".tablaVentas").DataTable();
    tabla.clear();
    $.each(listVentas, function (index, v) {
        console.log(v);
        //buttons action view and delete
        var botones = '<div class="btn-group">\n\
                        <div class="tooltipnuevo"><button class="btn btn-primary btnVerVenta" data-toggle="modal" data-target="#modalDetalleVenta" data-toggle="tooltip" title="Ver Factura" numeroVenta="' + v.numero + '" idVenta="' + v.id + '"><i class="fa fa-eye"></i></button><span class="tooltiptext">Ver Factura</span></div>\n\
                        <div class="tooltipnuevo"><button class="btn btn-danger btnEliminarVenta" data-toggle="tooltip" title="Eliminar Factura"  idVenta="' + v.id + '"><i class="fa fa-times"></i></button><span class="tooltiptext">Anular Factura</span></div></div>';

        tabla.row.add([
            v.numero,
            v.fecha,
            v.cantidad,
            v.utilidad.toLocaleString("de-DE"),
            v.total.toLocaleString("de-DE"),
            botones
        ]).draw(false);

    });
    tabla.order([0, 'desc']).draw();

//    setTimeout(() => {
//    }, 1000);
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
        $(".productoDetalleVenta").append("<strong><h4>Total Venta: $ "+totalFormated+"</h4></strong>");
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
                'Últimos 7 días': [moment().subtract(6, 'days'), moment()],
                'Últimos 30 días': [moment().subtract(29, 'days'), moment()],
                'Este mes': [moment().startOf('month'), moment().endOf('month')]
            },
            startDate: moment(),
            endDate: moment()
        },
        function (start, end) {
            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

            var fechaInicial = start.format('YYYY-MM-DD');
            var fechaFinal = end.format('YYYY-MM-DD');
            var capturarRango = $("#daterange-btn span").html();
            localStorage.setItem("capturarRango", capturarRango);
//            window.location = "index.php?ruta=ventas&fechaInicial=" + fechaInicial + "&fechaFinal=" + fechaFinal;

        }

)

/*=============================================
 CANCELAR RANGO DE FECHAS
 =============================================*/

$(".daterangepicker.opensleft .range_inputs .cancelBtn").on("click", function () {

    localStorage.removeItem("capturarRango");
    localStorage.clear();
    window.location = "ventas";
})

/*=============================================
 CAPTURAR HOY
 =============================================*/

$(".daterangepicker.opensleft .ranges li").on("click", function () {

    var textoHoy = $(this).attr("data-range-key");

    if (textoHoy == "Hoy") {

        var d = new Date();

        var dia = d.getDate();
        var mes = d.getMonth() + 1;
        var año = d.getFullYear();

        // if(mes < 10){

        // 	var fechaInicial = año+"-0"+mes+"-"+dia;
        // 	var fechaFinal = año+"-0"+mes+"-"+dia;

        // }else if(dia < 10){

        // 	var fechaInicial = año+"-"+mes+"-0"+dia;
        // 	var fechaFinal = año+"-"+mes+"-0"+dia;

        // }else if(mes < 10 && dia < 10){

        // 	var fechaInicial = año+"-0"+mes+"-0"+dia;
        // 	var fechaFinal = año+"-0"+mes+"-0"+dia;

        // }else{

        // 	var fechaInicial = año+"-"+mes+"-"+dia;
        //    	var fechaFinal = año+"-"+mes+"-"+dia;

        // }

        dia = ("0" + dia).slice(-2);
        mes = ("0" + mes).slice(-2);

        var fechaInicial = año + "-" + mes + "-" + dia;
        var fechaFinal = año + "-" + mes + "-" + dia;

        localStorage.setItem("capturarRango", "Hoy");

//        window.location = "index.php?ruta=ventas&fechaInicial=" + fechaInicial + "&fechaFinal=" + fechaFinal;

    }

})



