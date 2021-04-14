var listProductosVenta = [];

$(document).ready(function () {
    //detectar si esta desde ventas
    if ((window.location.href).includes("crear-venta")) {
        loadProductos();
    }
});

function existVenta() {

    //obtiene una venta pendiente
    if (localStorage.getItem('listProductosVenta') === null || localStorage.getItem('listProductosVenta') === "[]") {
        console.log('localStorage sin listProductosVenta');
        closeLoader();
    } else {
        Swal.fire({
            title: 'Existe una venta pendiente!',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: `Ver`,
            denyButtonText: `Borrar`
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                mostrarProductosPendientesVenta();
            } else if (result.isDenied) {
                clearListProductosVenta();
            }
        });
    }
}

/*=============================================
 CARGAR LA TABLA DINÁMICA DE NUEVA VENTA
 =============================================*/

function loadProductosVenta() {

    $.each(listProductos, function (index, p) {

        //button stock
        var btnCantidad = "<button class='btn btn-danger'>" + p.existencia + "</button>";
        if (p.existencia > 0)
            btnCantidad = "<button class='btn btn-success'>" + p.existencia + "</button>";

        //button action add
        var botonAdd = "<div class='btn-group'><button class='btn btn-primary agregarProducto recuperarBoton' idProducto='" + p.idProducto + "'>+</button></div>";

        listProductos[index].botonAdd = botonAdd;
        listProductos[index].btnCantidad = btnCantidad;
    });

    $(".tablaVenta").DataTable().destroy();

    $(".tablaVenta").DataTable({
        data: listProductos,
        columns: [
            {data: 'descripcion'},
            {data: 'precio',
            render: $.fn.dataTable.render.number( '.', ',', 0, '$' )},
            {data: 'btnCantidad'},
            {data: 'botonAdd'}
        ],
        order: [0, 'asc'],
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


    /*=============================================
     AGREGANDO PRODUCTOS A LA VENTA DESDE LA TABLA
     =============================================*/
    $('.tablaVenta tbody').on('click', 'button.agregarProducto', function () {
        var idProducto = $(this).attr('idProducto');
        //busca el objeto producto
        var id = Number(idProducto);
        var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

        var descripcion = o.descripcion;
        var precio = o.precio;
        var stock = o.existencia;

        //verifica que exista cantidades disponibes
        if (stock <= 0) {
            alertBottomEnd('Producto sin existencia!', 'error');
            return;
        } else {
            $(this).removeClass("btn-primary agregarProducto");
            $(this).addClass("btn-default");

            o.cantidad = 1;
            listProductosVenta.push(o);
            
            precio = precio.toLocaleString("de-DE");

            //dibuja elementos del producto agregado a la venta
            $(".nuevoProducto").append(
                    '<div class="row" style="padding:5px 15px">' +
                    '<!-- Descripción del producto -->' +
                    '<div class="col-xs-7" style="padding-right:0px">' +
                    '<div class="input-group">' +
                    '<span class="input-group-addon input-sm"><button type="button" onclick="borrarProductoVenta(this)" class="btn btn-danger btn-xs" idProducto="' + idProducto + '"><i class="fa fa-times"></i></button></span>' +
                    '<input type="text" class="form-control nuevaDescripcionProducto" name="agregarProducto" value="' + descripcion + '" readonly required>' +
                    '</div>' +
                    '</div>' +
                    '<!-- Cantidad del producto -->' +
                    '<div class="col-xs-2">' +
                    '<input type="number" class="form-control nuevaCantidadProducto" oninput="modificarCantidadItem(this)" idProducto="' + idProducto + '" onblur="setCantidadDefaultVenta(this)" min="1" value="1" stock="' + stock + '" nuevoStock="' + Number(stock - 1) + '" required>' +
                    '</div>' +
                    '<!-- Precio del producto -->' +
                    '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">' +
                    '<div class="input-group">' +
                    '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>' +
                    '<input type="text" class="form-control nuevoPrecioProducto" oninput="modificarPrecioItem(this)" precioReal="' + precio + '" idProducto="' + idProducto + '" name="nuevoPrecioProducto" value="' + precio + '" required>' +
                    '</div>' +
                    '</div>' +
                    '</div>');


            // SUMAR TOTAL DE PRECIOS
            sumarTotalPrecios();


            //guardando el localStorage
            var tmpListVentaJSON = JSON.stringify(listProductosVenta);
            localStorage.setItem('listProductosVenta', tmpListVentaJSON);

            //activa el boton totalizar
            $("#btnTotalizar").prop("disabled", false);
        }
    });


    //busca el siguiente numero de factura
    loadNewNumFactura();

    setTimeout(() => {
        closeLoader();
        existVenta();
    }, 1000);
}


/*=============================================
 QUITAR PRODUCTOS DE LA VENTA Y RECUPERAR BOTÓN
 =============================================*/
function borrarProductoVenta(e) {
    var idProducto = $(e).attr("idProducto");
    $(e).parent().parent().parent().parent().remove();

    //busca el objeto producto
    var id = Number(idProducto);
    var o = listProductosVenta.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

    var index = listProductosVenta.indexOf(o); //busca el indice del objeto en el array
    listProductosVenta.splice(index, 1); //elimina el objeto del array


    $("button.recuperarBoton[idProducto='" + idProducto + "']").removeClass('btn-default');
    $("button.recuperarBoton[idProducto='" + idProducto + "']").addClass('btn-primary agregarProducto');

    sumarTotalPrecios();

    //guardando el localStorage
    var tmpListVentaJSON = JSON.stringify(listProductosVenta);
    localStorage.setItem('listProductosVenta', tmpListVentaJSON);

    //desactiva el boton totalizar si no hay productos
    if (listProductosVenta.length === 0)
        $("#btnTotalizar").prop("disabled", true);
}

/*=============================================
 MODIFICAR LA CANTIDAD
 =============================================*/
function modificarCantidadItem(e) {
    if (e.value < 0 || e.value == "-0") {
        $(e).val("");
        return;
    }

    var idProductoText = $(e).attr('idproducto');
    var id = Number(idProductoText);

    var o = listProductosVenta.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
    var existencia = o.existencia;

    var nuevaCantidad = Number(e.value);

    if (existencia < nuevaCantidad && o.categoria !== "SERVICIO") {
        $(e).val(existencia);
        alertBottomEnd("La cantidad supera la existencia disponible!", "warning");
    } else {

        var index = listProductosVenta.indexOf(o); //busca el indice del objeto en el array
        listProductosVenta[index].cantidad = nuevaCantidad;

        //guardando el localStorage
        var tmpListVentaJSON = JSON.stringify(listProductosVenta);
        localStorage.setItem('listProductosVenta', tmpListVentaJSON);
    }
    sumarTotalPrecios();
    calcularCambio();
}

function modificarPrecioItem(e) {
    var val = replaceAll(e.value, ".", "");
    var nuevoPrecio = Number(val);
    var idProductoText = $(e).attr('idproducto');
    var id = Number(idProductoText);

    var o = listProductosVenta.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
    var index = listProductosVenta.indexOf(o); //busca el indice del objeto en el array

    if (nuevoPrecio < 1) {
        $(e).val(1);
        return;
    } else {
        listProductosVenta[index].precio = nuevoPrecio;
        //guardando el localStorage
        var tmpListVentaJSON = JSON.stringify(listProductosVenta);
        localStorage.setItem('listProductosVenta', tmpListVentaJSON);
        sumarTotalPrecios();
        calcularCambio();
        var nuevoValor = new Intl.NumberFormat('de-DE').format(val);
        $(e).val(nuevoValor);
    }

}

function setCantidadDefaultVenta(e) {
    var nuevaCantidad = e.value;

    if (nuevaCantidad == "") {
        var idProductoText = $(e).attr('idproducto');
        var id = Number(idProductoText);

        var o = listProductosVenta.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        var index = listProductosVenta.indexOf(o); //busca el indice del objeto en el array

        listProductosVenta[index].cantidad = 1;

        //guardando el localStorage
        var tmpListVentaJSON = JSON.stringify(listProductosVenta);
        localStorage.setItem('listProductosVenta', tmpListVentaJSON);

        $(e).val(1);
        sumarTotalPrecios();
        calcularCambio();
    }
}

function setPrecioDefault(e) {
    var nuevoPrecio = e.value;

    if (nuevoPrecio == "") {
        var idProductoText = $(e).attr('idproducto');
        var id = Number(idProductoText);

        //buscando producto en listaProductos
        var o = listProductos.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        var index = listProductos.indexOf(o); //busca el indice del objeto en el array
        var precioOld = listProductos[index].precio;

        //buscando producto en listaProductosVenta
        o = listProductosVenta.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        index = listProductosVenta.indexOf(o); //busca el indice del objeto en el array        
        listProductosVenta[index].precio = precioOld;

        //guardando el localStorage
        var tmpListVentaJSON = JSON.stringify(listProductosVenta);
        localStorage.setItem('listProductosVenta', tmpListVentaJSON);

        $(e).val(precioOld);
        sumarTotalPrecios();
        calcularCambio();
    }
}

/*=============================================
 SUMAR TODOS LOS PRECIOS
 =============================================*/
function sumarTotalPrecios() {
    var sumaTotalPrecio = 0;
    $.each(listProductosVenta, function (index, p) {
        var precio = replaceAll(p.precio, ".", "");
        var cantidad = p.cantidad;
        sumaTotalPrecio += precio * cantidad;
    });

    //formato miles
    var totalformat = new Intl.NumberFormat('de-DE').format(sumaTotalPrecio);
    $("#nuevoTotalVenta").val(totalformat);

    //formato miles
    var efectivoformat = new Intl.NumberFormat('de-DE').format(sumaTotalPrecio);
    $("#nuevoValorEfectivo").val(efectivoformat);
}

/*=============================================
 CAMBIO EN EFECTIVO
 =============================================*/
$("#nuevoValorEfectivo").on('input', function () {
    calcularCambio();
});

function calcularCambio() {
    var val = $("#nuevoValorEfectivo").val();
    var val1 = replaceAll(val, '.', '');
    var efectivo = val1;
    var totalVenta = $('#nuevoTotalVenta').val();
    var totalVentaUnformat = replaceAll(totalVenta, '.', '');
    var cambio = efectivo - totalVentaUnformat;

    //formato miles
    var cambioformat = new Intl.NumberFormat('de-DE').format(cambio);
    $("#nuevoCambioEfectivo").val(cambioformat);

    var efectivoformat = new Intl.NumberFormat('de-DE').format(val1);
    $("#nuevoValorEfectivo").val(efectivoformat);
}

function mostrarProductosPendientesVenta() {
    //obtenienddo de localStorage    
    var tmpListVentaJSON = localStorage.getItem('listProductosVenta');
    listProductosVenta = JSON.parse(tmpListVentaJSON);

    $.each(listProductosVenta, function (index, p) {
        var btnSave = $("button.recuperarBoton[idProducto='" + p.idProducto + "']");
        drawItemVenta(btnSave);
    });
    $("#btnTotalizar").prop("disabled", false);
}

function drawItemVenta(e) {
    var idProducto = $(e).attr("idProducto");
    //busca el objeto producto
    var id = Number(idProducto);
    var o = listProductosVenta.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

    var descripcion = o.descripcion;
    var precio = o.precio;
    var stock = o.existencia;
    var cantidad = o.cantidad;

    //verifica que exista cantidades disponibes
    if (stock <= 0) {
        alertBottomEnd('Algunos productos faltaron por falta de existencia!', 'error');
        return;
    } else {
        $(e).removeClass("btn-primary agregarProducto");
        $(e).addClass("btn-default");
        
        precio = precio.toLocaleString("de-DE");

        //dibuja elementos del producto agregado a la venta
        $(".nuevoProducto").append(
                '<div class="row" style="padding:5px 15px">' +
                '<!-- Descripción del producto -->' +
                '<div class="col-xs-7" style="padding-right:0px">' +
                '<div class="input-group">' +
                '<span class="input-group-addon input-sm"><button type="button" onclick="borrarProductoVenta(this)" class="btn btn-danger btn-xs" idProducto="' + idProducto + '"><i class="fa fa-times"></i></button></span>' +
                '<input type="text" class="form-control nuevaDescripcionProducto" name="agregarProducto" value="' + descripcion + '" readonly required>' +
                '</div>' +
                '</div>' +
                '<!-- Cantidad del producto -->' +
                '<div class="col-xs-2">' +
                '<input type="number" class="form-control nuevaCantidadProducto" oninput="modificarCantidadItem(this)" idProducto="' + idProducto + '" onblur="setCantidadDefaultVenta(this)" min="1" value="' + cantidad + '" stock="' + stock + '" nuevoStock="' + Number(stock - 1) + '" required>' +
                '</div>' +
                '<!-- Precio del producto -->' +
                '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">' +
                '<div class="input-group">' +
                '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>' +
                '<input type="text" class="form-control nuevoPrecioProducto" oninput="modificarPrecioItem(this)" precioReal="' + precio + '" onblur="setPrecioDefault(this)" idProducto="' + idProducto + '" name="nuevoPrecioProducto" value="' + precio + '" required>' +
                '</div>' +
                '</div>' +
                '</div>');


        // SUMAR TOTAL DE PRECIOS
        sumarTotalPrecios();
    }
}

function clearListProductosVenta() {
    listProductosVenta = [];
    localStorage.setItem('listProductosVenta', "[]");
}

/*=============================================
 FUNCION GENERA NUEVO NUMERO DE FACTURA
 =============================================*/
function loadNewNumFactura() {

    $.post("/CtrVentas.do", "", function (response) {
        $("#nuevaVenta").val(response);
    });

}

function totalizarVenta() {
    var params = {
        option: 'addVenta',
        listProductos: JSON.stringify(listProductosVenta),
        idCliente: '1',
        numFactura: $("#nuevaVenta").val()
    };

    showLoader("Generando venta");
    //Response con parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrVentas.do", params, function (response) {
        if (response.result === "success") {


            //actualiza existencia listProdcutos
            $.each(listProductosVenta, function (index, p) {

                var id = p.idProducto;
                var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
                var index = listProductos.indexOf(o); //busca el indice del objeto en el array
                var existenciaActual = listProductos[index].existencia;
                var cantidadVenta = p.cantidad;
                listProductos[index].existencia = existenciaActual - cantidadVenta;
            });

            clearListProductosVenta();
            $('.nuevoProducto').empty();

        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo realizar la venta!", response.result);
        } else if (response.result === "error") {
            alertBottomEnd("Ya existe un producto con ese nombre ó código!", response.result);
        }
    }).done(function () {
        closeLoader();
        alertBottomEnd("Venta realizada con exito!", "success");
        setTimeout(() => {
            window.location.href = "crear-venta";
        }, 500);

    }).fail(function () {
        closeLoader();
        alertBottomEnd("Error interno, Reiniciar sistema!", "error");
    });
}

function replaceAll(text, busca, reemplaza) {
    while (text.toString().indexOf(busca) != - 1)
        text = text.toString().replace(busca, reemplaza);
    return text;
}