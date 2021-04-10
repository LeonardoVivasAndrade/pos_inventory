var listVentas = [];

$(document).ready(function () {
    //detectar si esta desde ventas
    if ((window.location.href).includes("ventas")) {
        loadVentas("","","default");
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
                        <div class="tooltipnuevo"><button class="btn btn-primary btnVerVenta" data-toggle="tooltip" title="Ver Factura" idVenta="'+v.id+'"><i class="fa fa-eye"></i></button><span class="tooltiptext">Ver Factura</span></div>\n\
                        <div class="tooltipnuevo"><button class="btn btn-danger btnEliminarVenta" data-toggle="tooltip" title="Eliminar Factura" idVenta="'+v.id+'"><i class="fa fa-times"></i></button><span class="tooltiptext">Eliminar Factura</span></div></div>';

        tabla.row.add([          
            v.numero,
            v.fecha,
            v.cantidad,
            v.utilidad.toLocaleString("en-US"),
            v.total.toLocaleString("en-US"),            
            botones
        ]).draw(false);

    });
    tabla.order([0, 'desc']).draw();

//    setTimeout(() => {
//    }, 1000);
}

/*=============================================
 BORRAR VENTA
 =============================================*/
$(".tablas").on("click", ".btnEliminarVenta", function () {

    var idVenta = $(this).attr("idVenta");

    swal({
        title: '¿Está seguro de borrar la venta?',
        text: "¡Si no lo está puede cancelar la accíón!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, borrar venta!'
    }).then(function (result) {
        if (result.value) {

            window.location = "index.php?ruta=ventas&idVenta=" + idVenta;
        }

    })

})


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



