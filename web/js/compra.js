var listProductosCompra = [];

$(document).ready(function () {
    //detectar si esta desde compra
    if (window.location.pathname === "/crear-compra") {
        loadProductos();
        $('li').removeClass('active');
        $('#licompra').addClass('active');
    }
});

function existCompra() {

    //obtiene una compra pendiente
    if (localStorage.getItem('listProductosCompra') === null || localStorage.getItem('listProductosCompra') === "[]") {

        closeLoader();
    } else {
        Swal.fire({
            title: 'Existe una compra pendiente!',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: `Ver`,
            denyButtonText: `Borrar`
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                mostrarProductosPendientesCompra();
            } else if (result.isDenied) {
                clearListProductosCompra();
            }
        });
    }
}

/*=============================================
 CARGAR LA TABLA DINÁMICA DE NUEVA COMPRA
 =============================================*/

function loadProductosCompra() {

    $.each(listProductos, function (index, p) {

        //button stock
        var btnCantidad = "<button class='btn btn-success'>" + p.existencia + "</button>";

        //button action add
        var botonAdd = "<div class='btn-group'><button class='btn btn-danger agregarProducto recuperarBoton' idProducto='" + p.idProducto + "'>+</button></div>";

        listProductos[index].botonAdd = botonAdd;
        listProductos[index].btnCantidad = btnCantidad;
    });

    $(".tablaCompra").DataTable().destroy();

    $(".tablaCompra").DataTable({
        data: listProductos,
        columns: [
            {data: 'descripcion'},
            {data: 'costo',
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
     AGREGANDO PRODUCTOS A LA COMPRA DESDE LA TABLA
     =============================================*/
    $(".tablaCompra tbody").on("click", "button.agregarProducto", function () {
        var idProducto = $(this).attr("idProducto");
        //busca el objeto producto
        var id = Number(idProducto);
        var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

        var descripcion = o.descripcion;
        var costo = o.costo;
        var stock = o.existencia;


        $(this).removeClass("btn-danger agregarProducto");
        $(this).addClass("btn-default");

        o.cantidad = 1;
        listProductosCompra.push(o);

        //dibuja elementos del producto agregado a la compra
        $(".nuevoProducto").append(
                '<div class="row" style="padding-right:8px; padding-left:8px;">' +
                '<!-- Descripción del producto -->' +
                '<div class="col-xs-7" style="padding-right:0px">' +
                '<div class="input-group">' +
                '<span class="input-group-addon input-sm"><button type="button" onclick="borrarProductoCompra(this)" class="btn btn-danger btn-xs" idProducto="' + idProducto + '"><i class="fa fa-times"></i></button></span>' +
                '<input type="text" class="form-control nuevaDescripcionProducto" name="agregarProducto" value="' + descripcion + '" readonly required>' +
                '</div>' +
                '</div>' +
                '<!-- Cantidad del producto -->' +
                '<div class="col-xs-2" style="padding-right:0px; padding-left:0px">' +
                '<input type="number" class="form-control nuevaCantidadProducto" oninput="modificarCantidadItemCompra(this)" idProducto="' + idProducto + '" onblur="setCantidadDefaultCompra(this)" min="1" value="1" required>' +
                '</div>' +
                '<!-- Precio del producto -->' +
                '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">' +
                '<div class="input-group">' +
                '<input type="text" class="form-control nuevoCostoProducto" oninput="modificarCostoItem(this)" precioReal="' + costo + '" idProducto="' + idProducto + '" name="nuevoCostoProducto" value="' + costo + '" required>' +
                '</div>' +
                '</div>' +
                '</div>');


        // SUMAR TOTAL DE COSTOS
        sumarTotalCostos();

        // PONER FORMATO DE MILES 
        $(".nuevoCostoProducto").number(true, 0);
        $("#nuevoTotalCompra").number(true, 0);

        //guardando el localStorage
        var tmpListCompraJSON = JSON.stringify(listProductosCompra);
        localStorage.setItem('listProductosCompra', tmpListCompraJSON);

        //activa el boton totalizar
        $("#btnTotalizar").prop("disabled", false);

    });

    //busca el siguiente numero de factura
    loadNewNumCompra();

    setTimeout(() => {
        closeLoader();
        existCompra();
    }, 1000);
}

/*=============================================
 QUITAR PRODUCTOS DE LA COMPRA Y RECUPERAR BOTÓN
 =============================================*/
function borrarProductoCompra(e) {
    var idProducto = $(e).attr("idProducto");
    $(e).parent().parent().parent().parent().remove();

    //busca el objeto producto
    var id = Number(idProducto);
    var o = listProductosCompra.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

    var index = listProductosCompra.indexOf(o); //busca el indice del objeto en el array
    listProductosCompra.splice(index, 1); //elimina el objeto del array


    $("button.recuperarBoton[idProducto='" + idProducto + "']").removeClass('btn-default');
    $("button.recuperarBoton[idProducto='" + idProducto + "']").addClass('btn-danger agregarProducto');

    sumarTotalCostos();

    $("#nuevoTotalCompra").number(true, 0);

    //guardando el localStorage
    var tmpListCompraJSON = JSON.stringify(listProductosCompra);
    localStorage.setItem('listProductosCompra', tmpListCompraJSON);

    //desactiva el boton totalizar si no hay productos
    if (listProductosCompra.length === 0)
        $("#btnTotalizar").prop("disabled", true);
}

/*=============================================
 MODIFICAR LA CANTIDAD
 =============================================*/
function modificarCantidadItemCompra(e) {
    if (e.value < 0 || e.value == "-0") {
        $(e).val("");
        return;
    }

    var idProductoText = $(e).attr('idproducto');
    var id = Number(idProductoText);
    var o = listProductosCompra.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento

    var nuevaCantidad = Number(e.value);
    var index = listProductosCompra.indexOf(o); //busca el indice del objeto en el array
    listProductosCompra[index].cantidad = nuevaCantidad;

    //guardando el localStorage
    var tmpListCompraJSON = JSON.stringify(listProductosCompra);
    localStorage.setItem('listProductosCompra', tmpListCompraJSON);

    sumarTotalCostos();
}

function modificarCostoItem(e) {
    var val = replaceAllCompra(e.value, ",", "");
    var nuevoCosto = Number(val);
    var idProductoText = $(e).attr('idproducto');
    var id = Number(idProductoText);

    var o = listProductosCompra.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
    var index = listProductosCompra.indexOf(o); //busca el indice del objeto en el array

    if (nuevoCosto < 1) {
        $(e).val(1);
        return;
    } else {
        listProductosCompra[index].costo = nuevoCosto;
        //guardando el localStorage
        var tmpListCompraJSON = JSON.stringify(listProductosCompra);
        localStorage.setItem('listProductosCompra', tmpListCompraJSON);
        sumarTotalCostos();
    }

}

function setCantidadDefaultCompra(e) {
    var nuevaCantidad = e.value;

    if (nuevaCantidad == "") {
        var idProductoText = $(e).attr('idproducto');
        var id = Number(idProductoText);

        var o = listProductosCompra.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        var index = listProductosCompra.indexOf(o); //busca el indice del objeto en el array

        listProductosCompra[index].cantidad = 1;

        //guardando el localStorage
        var tmpListCompraJSON = JSON.stringify(listProductosCompra);
        localStorage.setItem('listProductosCompra', tmpListCompraJSON);

        $(e).val(1);
        sumarTotalCostos();
    }
}

function setCostoDefault(e) {
    var nuevoCosto = e.value;

    if (nuevoCosto == "") {
        var idProductoText = $(e).attr('idproducto');
        var id = Number(idProductoText);

        //buscando producto en listaProductos
        var o = listProductos.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        var index = listProductos.indexOf(o); //busca el indice del objeto en el array
        var costoOld = listProductos[index].costo;

        //buscando producto en listaProductosCompra
        o = listProductosCompra.find(result => result.idProducto === id); //extra el objeto con el argumento idDepartamento
        index = listProductosCompra.indexOf(o); //busca el indice del objeto en el array        
        listProductosCompra[index].costo = costoOld;

        //guardando el localStorage
        var tmpListCompraJSON = JSON.stringify(listProductosCompra);
        localStorage.setItem('listProductosCompra', tmpListCompraJSON);

        $(e).val(costoOld);
        sumarTotalCostos();
    }
}

/*=============================================
 SUMAR TODOS LOS COSTOS
 =============================================*/
function sumarTotalCostos() {
    var sumaTotalCosto = 0;
    $.each(listProductosCompra, function (index, p) {
        var costo = replaceAllCompra(p.costo,",","");
        var cantidad = p.cantidad;
        sumaTotalCosto += costo * cantidad;
    });

    $("#nuevoTotalCompra").val(sumaTotalCosto);
    $("#totalCompra").val(sumaTotalCosto);
    $("#nuevoTotalCompra").attr("total", sumaTotalCosto);
}


function mostrarProductosPendientesCompra() {
    //obtenienddo de localStorage    
    var tmpListCompraJSON = localStorage.getItem('listProductosCompra');
    listProductosCompra = JSON.parse(tmpListCompraJSON);

    $.each(listProductosCompra, function (index, p) {
//        var btnSave = $("button.recuperarBoton[idProducto='" + p.idProducto + "']"); //no funciona porque no recupera boton de una pagina de la tabla no pintada
        var id = p.idProducto;
        drawItemCompra(id);
    });
    $("#btnTotalizar").prop("disabled", false);
}

function drawItemCompra(idProducto) {
//    var idProducto = $(e).attr("idProducto");
    //busca el objeto producto
    var id = Number(idProducto);
    var o = listProductosCompra.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

    var descripcion = o.descripcion;
    var costo = o.costo;
    var cantidad = o.cantidad;


//    $(e).removeClass("btn-danger agregarProducto");
//    $(e).addClass("btn-default");

    //dibuja elementos del producto agregado a la compra
    $(".nuevoProducto").append(
            '<div class="row" style="padding-right:8px; padding-left:8px;">' +
            '<!-- Descripción del producto -->' +
            '<div class="col-xs-7" style="padding-right:0px">' +
            '<div class="input-group">' +
            '<span class="input-group-addon input-sm"><button type="button" onclick="borrarProductoCompra(this)" class="btn btn-danger btn-xs" idProducto="' + idProducto + '"><i class="fa fa-times"></i></button></span>' +
            '<input type="text" class="form-control nuevaDescripcionProducto" name="agregarProducto" value="' + descripcion + '" readonly required>' +
            '</div>' +
            '</div>' +
            '<!-- Cantidad del producto -->' +
            '<div class="col-xs-2" style="padding-right:0px; padding-left:0px">' +
            '<input type="number" class="form-control nuevaCantidadProducto" oninput="modificarCantidadItemCompra(this)" idProducto="' + idProducto + '" onblur="setCantidadDefaultCompra(this)" min="1" value="' + cantidad + '" required>' +
            '</div>' +
            '<!-- Precio del producto -->' +
            '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">' +
            '<div class="input-group">' +
            '<input type="text" class="form-control nuevoCostoProducto" oninput="modificarCostoItem(this)" precioReal="' + costo + '" onblur="setCostoDefault(this)" idProducto="' + idProducto + '" name="nuevoCostoProducto" value="' + costo + '" required>' +
            '</div>' +
            '</div>' +
            '</div>');


    // SUMAR TOTAL DE COSTOS
    sumarTotalCostos();

    // PONER FORMATO DE MILES 
    $(".nuevoCostoProducto").number(true, 0);
    $("#nuevoTotalCompra").number(true, 0);
}

function clearListProductosCompra() {
    listProductosCompra = [];
    localStorage.setItem('listProductosCompra', "[]");
}

/*=============================================
 FUNCION GENERA NUEVO NUMERO DE FACTURA
 =============================================*/
function loadNewNumCompra() {

    $.post("/CtrCompras.do", "", function (response) {
        $("#nuevaCompra").val(response);
    });

}

function totalizarCompra() {
    var params = {
        option: 'addCompra',
        listProductos: JSON.stringify(listProductosCompra),
        idProveedor: '1',
        numCompra: $("#nuevaCompra").val()
    };

    showLoader("Generando compra");
    //Response con parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrCompras.do", params, function (response) {
        if (response.result === "success") {
            
            //actualiza existencia listProdcutos
            $.each(listProductosCompra, function (index, p) {

                var id = p.idProducto;
                var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
                var index = listProductos.indexOf(o); //busca el indice del objeto en el array
                var existenciaActual = listProductos[index].existencia;
                var cantidadCompra = p.cantidad;
                listProductos[index].existencia = existenciaActual + cantidadCompra;
            });

            clearListProductosCompra();
            $('.nuevoProducto').empty();

        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo realizar la compra!", response.result);
        } else if (response.result === "error") {
            alertBottomEnd("No se pudo realizar la compra!", response.result);
        }
    }).done(function () {
        closeLoader();
        alertBottomEnd("Compra realizada con exito!", "success");
        setTimeout(() => {
            window.location.href = "crear-compra";
        }, 500);
    }).fail(function () {
        closeLoader();
        alertBottomEnd("No se pudo realizar la compra!", "error");
    });
}

function replaceAllCompra(text, busca, reemplaza) {
    while (text.toString().indexOf(busca) != - 1)
        text = text.toString().replace(busca, reemplaza);
    return text;
}