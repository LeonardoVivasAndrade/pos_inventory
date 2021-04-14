
/* global listProductos */

var listProductos = [];

$(document).ready(function () {
    //detectar si esta desde productos
    if (window.location.pathname === "/productos") {
        loadCategorias();
        loadProductos();
        $('li').removeClass('active');
        $('#liproductos').addClass('active');
    }
});


/*=============================================
 CREAR NUEVA CATEGORIA DESDE PRODUCTOS
 =============================================*/
$(".nuevaCategoriaSelect").change(function () {
    var index = $(".nuevaCategoriaSelect").index($(this));
    var idCategoria = $(this).val();

    if (idCategoria == -1) {
        $("#modalAgregarCategoria").modal('show');
        loadCategoriasSelect(index);
    } else {
        var respuesta = validarSelect(index);

        if (respuesta === "" && allInputsValidos(index)) {
            $(".errorInputSelect").text("");
            $(".submit-prod").prop("disabled", false);
        } else {
            $(".errorInputSelect").text(respuesta);
            $(".submit-prod").prop("disabled", true);
        }
    }

});

/*=============================================
 EVENTO HIDE MODAL AGREGAR PRODUCTO | limpiar inputs al cerrar el modal
 =============================================*/
$('#modalAgregarProducto, #modalEditarProducto').on('hidden.bs.modal', function () {
    clearInputsModal();
});

/*=============================================
 EVENTO AGREGAR PRODUCTO
 =============================================*/
$(".btnAgregarProducto").click(function () {
    loadCategoriasSelect(0); // 0, select del modal agregar
    loadNewCodigo();
});

/*=============================================
 EVENTOS INPUTS MODAL AGREGAR PRODUCTO
 =============================================*/
$(".nuevoPrecio").on('input', function () {
    var index = $(".nuevoPrecio").index($(this));
    calcularPorcentaje(index);
    validarPrecio(index);
    
    var input = $(".nuevoPrecio").eq(index).val();
    var nuevoValor = formatoPuntos(input);
    $(".nuevoPrecio").eq(index).val(nuevoValor);
});

$(".nuevoCosto").on('input', function () {
    var index = $(".nuevoCosto").index($(this));
        
    calcularPrecioFromCosto(index);
    calcularPorcentaje(index);
    validarCosto(index);
    
    var input = $(".nuevoCosto").eq(index).val();
    var nuevoValor = formatoPuntos(input);
    $(".nuevoCosto").eq(index).val(nuevoValor);

});

$(".porcentaje").on("ifUnchecked", function () {
    var index = $(".porcentaje").index($(this));

    $(".nuevoPrecio").eq(index).prop("readonly", false);
    $(".nuevoPorcentaje").eq(index).prop("disabled", true);
    $(".errorInputPrecio").text("");
});

$(".porcentaje").on("ifChecked", function () {
    var index = $(".porcentaje").index($(this));

    $(".nuevoPrecio").eq(index).prop("readonly", true);
    $(".nuevoPorcentaje").eq(index).prop("disabled", false);
    $(".errorInputPrecio").text("Desactiva Calcular para ingresar precio manualmente!");
    calcularPrecioFromCosto(index);
});

$(".nuevoPorcentaje").on('input', function () {
    var index = $(".nuevoPorcentaje").index($(this));

    calcularPrecioFromPorcentaje($(this), index);
});

$(".nuevaDescripcion").on('input', function () {
    var index = $(".nuevaDescripcion").index($(this));

    var respuesta = validarDescripcion(index);

    if (respuesta === "" && allInputsValidos(index)) {
        $(".errorInputDescripcion").text("");
        $(".submit-prod").prop("disabled", false);
    } else {
        $(".errorInputDescripcion").text(respuesta);
        $(".submit-prod").prop("disabled", true);
    }

});

$(".nuevaCantidad").on('input', function () {
    var index = $(".nuevaCantidad").index($(this));

    var respuesta = validarCantidad(index);

    if (respuesta === "" && allInputsValidos(index)) {
        $(".errorInputCantidad").text("");
        $(".submit-prod").prop("disabled", false);
    } else {
        $(".errorInputCantidad").text(respuesta);
        $(".submit-prod").prop("disabled", true);
    }
});

/*=============================================
 FUNCIONES
 =============================================*/

/*=============================================
 FUNCIONES DE CALCULOS PRECIO Y PORCENTAJE
 =============================================*/
function calcularPrecioFromCosto(index) {
    if ($(".porcentaje").eq(index).prop("checked")) {

        var valorPorcentaje = $(".nuevoPorcentaje").eq(index).val();
        var valIn = $(".nuevoCosto").eq(index).val();
        var costo = unformato(valIn);

        var precio = (costo * Number(valorPorcentaje) / 100) + costo;
        var precioSinDecimal = (precio.toFixed(0)).toString();
        var nuevoPrecio = formatoPuntos(precioSinDecimal);

        $(".nuevoPrecio").eq(index).val(nuevoPrecio);
        $(".nuevoPrecio").eq(index).prop("readonly", true);
    }
}

function calcularPorcentaje(index) {
    if (!$(".porcentaje").eq(index).prop("checked")) {
        var ventaInput = $(".nuevoPrecio").eq(index).val();
        var venta = unformato(ventaInput);
        var compraInput = $(".nuevoCosto").eq(index).val();
        var compra = unformato(compraInput);

        var nuevoPorcentaje = Number(((venta - compra) / compra) * 100);
        $(".nuevoPorcentaje").eq(index).val(nuevoPorcentaje.toFixed(0));
    }
}

/*=============================================
 FUNCIONES CRUD PARA PRODUCTO
 =============================================*/
function addProducto() {
    
    var costo = $(".nuevoCosto").eq(0).val();
    var precio = $(".nuevoPrecio").eq(0).val();

    var params = {
        option: "prodAdd",
        idCategoria: $('.nuevaCategoriaSelect option:selected').eq(0).attr('id'),
        codigo: $(".nuevoCodigo").eq(0).val(),
        descripcion: $(".nuevaDescripcion").eq(0).val(),
        cantidad: $(".nuevaCantidad").eq(0).val(),
        porcentaje: $(".nuevoPorcentaje").eq(0).val(),
        costo: unformato(costo),
        precio: unformato(precio)
    };
    showLoader("Creando producto");

    //Retorna un JSON con los atributos del nuevo producto creado
    //Parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrProductos.do", params, function (response) {

        if (response.result === "success") {

            listProductos.push(response); //agrega el nuevo producto al array
            loadProductsTable("Producto creado con éxito!"); //redibuja la tabla

            $("#modalAgregarProducto").modal('hide');
        } else if (response.result === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo crear el producto!", response.result);
        } else if (response.result === "error") {
            closeLoader();
            alertBottomEnd("Ya existe un producto con ese nombre ó código!", response.result);
        }
    });
}

function llenarInputsEditProducto(idProducto) {
    var id = Number(idProducto);
    var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
    
    var costo = formatoPuntos(o.costo.toString());
    var precio = formatoPuntos(o.precio.toString());
    
    $(".nuevoCosto").eq(1).val(costo);
    $(".nuevoPrecio").eq(1).val(precio);
    $(".nuevaDescripcion").eq(1).val(o.descripcion);
    $(".nuevoCodigo").eq(1).val(o.codigo);
    $(".nuevaCantidad").eq(1).val(o.existencia);
    $('.nuevaCategoriaSelect').eq(1).val(o.categoria);

    calcularPorcentaje(1);
    localStorage.setItem("idProductoToEdit", idProducto);
}

function editProducto() {
    var idProducto = localStorage.getItem("idProductoToEdit");
    
    var costo = $(".nuevoCosto").eq(1).val();
    var precio = $(".nuevoPrecio").eq(1).val();

    var params = {
        option: "prodEdit",
        idProducto: idProducto,
        idCategoria: $('.nuevaCategoriaSelect option:selected').eq(1).attr('id'),
        descripcion: $(".nuevaDescripcion").eq(1).val(),
        cantidad: $(".nuevaCantidad").eq(1).val(),
        porcentaje: $(".nuevoPorcentaje").eq(1).val(),
        costo: unformato(costo),
        precio: unformato(precio)
    };
    showLoader("Editando producto");

    //Retorna un JSON con los atributos del producto editado
    //Parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrProductos.do", params, function (response) {
        var indexPage = $('.tablaProductos').DataTable().page();

        if (response.result === "success") {
            var id = Number(idProducto);
            var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
            var index = listProductos.indexOf(o); //busca el indice del objeto en el array

            listProductos[index] = response; //agrega el objeto del array  
            loadProductsTable("Producto editado con éxito!"); //redibuja la tabla

            $("#modalEditarProducto").modal('hide');
            localStorage.setItem("idProductoToEdit", "");
        } else if (response.result === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo editar el producto!", response.result);
        } else if (response.result === "error") {
            closeLoader();
            alertBottomEnd("Ya existe un producto con ese nombre ó código!", response.result);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('.tablaProductos').DataTable().page(indexPage).draw('page');
    });
}

function deleteProducto(idProducto) {

    var params = {
        option: "prodDelete",
        idProducto: idProducto
    };

    showLoader("Eliminando producto");

    $.get("/CtrProductos.do", params, function (response) {
        var indexPage = $('.tablaProductos').DataTable().page();

        if (response.result === "success") {
            var id = Number(idProducto);
            var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
            var index = listProductos.indexOf(o); //busca el indice del objeto en el array
            listProductos.splice(index, 1); //elimina el objeto del array

            loadProductsTable("Producto eliminado con éxito!"); //redibuja la tabla
        } else if (response.result === "error") {
            closeLoader();
            alertBottomEnd("El Producto tiene operaciones!", response.result);
        } else if (response.result === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo eliminar!", response.result);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('.tablaProductos').DataTable().page(indexPage).draw('page');
    });
}


/*=============================================
 FUNCION CARGAR DATATABLE DE PRODUCTOS
 =============================================*/
function loadProductos() {
    var params = {
        option: "getListProducts",
        idProducto: 0
    };

    showLoader('Cargando productos');

    $.get("/CtrProductos.do", params, function (responseJson) {
        listProductos = responseJson;
        localStorage.setItem("listProductos", listProductos);
        if ((window.location.href).includes("productos")) {
            loadProductsTable("");
        }
        if ((window.location.href).includes("venta")) {
            loadProductosVenta();
        }
        if ((window.location.href).includes("compra")) {
            loadProductosCompra();
        }

    });
}

function loadProductsTable(mensaje) {

    $.each(listProductos, function (index, p) {

        //button stock
        var btnCantidad = "<button class='btn btn-danger'>" + p.existencia + "</button>";
        if (p.existencia > 0)
            btnCantidad = "<button class='btn btn-success'>" + p.existencia + "</button>";

        //buttons actions
        var botones = "<div class='btn-group'><button class='btn btn-warning btnEditarProducto' idProducto='" + p.idProducto + "' data-toggle='modal' data-producto-id='" + p.idProducto + "'  data-target='#modalEditarProducto'><i class='fa fa-pencil'></i></button>\n\
                            <button class='btn btn-danger btnEliminarProducto' idProducto='" + p.idProducto + "' codigo='" + p.codigo + "'><i class='fa fa-times'></i></button></div>";

        listProductos[index].btnCantidad = btnCantidad;
        listProductos[index].botones = botones;
    });


    $(".tablaProductos").DataTable().destroy();

    $(".tablaProductos").DataTable({

        data: listProductos,
        columns: [
            {data: 'codigo'},
            {data: 'descripcion'},
            {data: 'categoria'},
            {data: 'btnCantidad'},
            {data: 'costo',
                render: $.fn.dataTable.render.number('.', ',', 0, '$')},
            {data: 'utilidad',
                render: $.fn.dataTable.render.number('.', ',', 0, '$')},
            {data: 'precio',
                render: $.fn.dataTable.render.number('.', ',', 0, '$')},
            {data: 'botones'}
        ],
        order: [1, 'asc'],
        "language": {
//            "thousands": ",",
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
//            "sInfoThousands": ",",
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
     EVENTO EDITAR PRODUCTO
     =============================================*/
    $('#tablaProductos tbody').on('click', '.btnEditarProducto', function () {
        var id = $(this).attr('idProducto');
        loadCategoriasSelect(1); //1, select del modal editar
        llenarInputsEditProducto(id);
    });

    /*=============================================
     EVENTO ELIMINAR PRODUCTO
     =============================================*/
    $('#tablaProductos tbody').on('click', '.btnEliminarProducto', function () {

        var idProducto = $(this).attr("idProducto");

        Swal.fire({
            title: '¿Está seguro de borrar el producto?',
            text: "¡Si no lo está puede cancelar la accíón!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Si, borrar producto!'
        }).then((result) => {
            if (result.value)
                deleteProducto(idProducto);
        });
    });

    setTimeout(() => {
        closeLoader();
        if (mensaje != "") {
            alertBottomEnd(mensaje, "success");
        }

    }, 1000);

}

/*=============================================
 FUNCION CARGAR SELECT DE CATEGORIAS
 =============================================*/
function loadCategoriasSelect(i) {

    clearCategoriasSelect(i);
    sortCategorias();
    $.each(alistCategorias, function (index, categoria) {
        var se = document.getElementsByClassName("nuevaCategoriaSelect").item(i);
        var option = document.createElement("option");
        option.text = categoria.descripcion;
        option.id = categoria.idCategoria;
        se.add(option);
    });
}

function clearCategoriasSelect(i) {
    $(".errorInputSelect").text('');
    var se = document.getElementsByClassName("nuevaCategoriaSelect").item(i);
    var tam = se.length;

    for (var i = 2; i < tam; i++) {
        se.remove(2);
    }
}

/*=============================================
 FUNCION GENERA NUEVO CODIGO DE PRODUCTO
 =============================================*/
function loadNewCodigo() {

    $.post("/CtrProductos.do", "", function (response) {
        $(".nuevoCodigo").eq(0).val(response);
    });

}

function existProducto(descripcion) {
    var result = listProductos.find(result => result.descripcion === descripcion.toUpperCase());
    return result != undefined;
}

function sortCategorias() {
    alistCategorias.sort(function (a, b) {
        if (a.descripcion > b.descripcion) {
            return 1;
        }
        if (a.descripcion < b.descripcion) {
            return -1;
        }
        return 0;
    });
}


/*=============================================
 FUNCIONES DE VALIDACION DE INPUTS MODAL AGREGAR PRODUCTO
 =============================================*/
function validarDescripcion(index) {
    var valor = $(".nuevaDescripcion").eq(index).val();
    if (valor === "") {
        return "Campo Vacío!";
    } else if (index === 0 && existProducto(valor)) {
        return "Este nombre de producto ya existe!";
    } else {
        return "";
    }
}

function validarCantidad(index) {
    var valor = $(".nuevaCantidad").eq(index).val();
    if (valor === "") {
        return "Campo Vacío!";
    } else if (valor < 0) {
        return "Cantidad no puede ser negativa!";
    } else {
        return "";
    }
}

function validarCosto(index) {
    var respuesta = validarInputCosto(index);

    if (respuesta === "" && allInputsValidos(index)) {
        $(".errorInputCosto").text("");
        $(".submit-prod").prop("disabled", false);
    } else {
        $(".errorInputCosto").text(respuesta);
        $(".submit-prod").prop("disabled", true);
    }
}

function validarInputCosto(index) {
    var valor = $(".nuevoCosto").eq(index).val();
    if (valor === "") {
        return "Campo Vacío!";
    } else if (valor < 0) {
        return "Cantidad no puede ser negativa!";
    } else {
        return "";
    }
}

function validarPrecio(index) {
    var respuesta = validarInputPrecio(index);

    if (respuesta === "" && allInputsValidos(index)) {
        $(".errorInputPrecio").text("");
        $(".submit-prod").prop("disabled", false);
    } else {
        $(".errorInputPrecio").text(respuesta);
        $(".submit-prod").prop("disabled", true);
    }
}

function validarInputPrecio(index) {
    var valor = $(".nuevoPrecio").eq(index).val();
    if (valor === "") {
        return "Campo Vacío!";
    } else if (valor < 0) {
        return "Cantidad no puede ser negativa!";
    } else {
        return "";
    }
}

function calcularPrecioFromPorcentaje(o, index) {  
    if ($(".porcentaje").eq(index).prop("checked")) {

        var valorPorcentaje = o.val();
        var valIn = $(".nuevoCosto").eq(index).val();
        var costo = unformato(valIn);
        
        var precio = (costo * Number(valorPorcentaje) / 100) + costo;
        var precioSinDecimal = (precio.toFixed(0)).toString();
        var nuevoPrecio = formatoPuntos(precioSinDecimal);

        $(".nuevoPrecio").eq(index).val(nuevoPrecio);
        $(".nuevoPrecio").eq(index).prop("readonly", true);
    }
}

function allInputsValidos(index) {
    var result = validarDescripcion(index) === "" && validarCantidad(index) === "" &&
            validarInputCosto(index) === "" && validarInputPrecio(index) === "" &&
            validarSelect(index) === "";

    return result;
}

function validarSelect(index) {

    var idSelect = $('.nuevaCategoriaSelect option:selected').eq(index).attr('id');
    if (idSelect === -2 || idSelect == undefined) {
        return "Seleccionar una categoria!";
    } else {
        return "";
    }
}

function clearInputsModal() {
    $(".nuevoCosto").val('');
    $(".nuevoPrecio").val('');
    $(".nuevaDescripcion").val('');
    $(".nuevoPorcentaje").val('30');
    $(".nuevaCantidad").val('');
    $('.nuevaCategoriaSelect').val("-2");

    $(".errorInputDescripcion").text('');
    $(".errorInputCantidad").text('');
    $(".errorInputPrecio").eq(0).text('Desactiva Calcular para ingresar precio manualmente!');
    $(".errorInputPrecio").eq(1).text('');
    $(".errorInputCosto").text('');
    $(".errorInputSelect").text('');

    $(".porcentaje").eq(0).prop("checked", true);
    $(".porcentaje").eq(0).parent().addClass('checked');
    
    $(".porcentaje").eq(1).prop("checked", false);
    $(".porcentaje").eq(1).parent().removeClass('checked');

    $(".submit-prod").prop("disabled", true);  
    $(".nuevoPrecio").eq(0).prop("readonly", true);
}

function replaceAllP(text, busca, reemplaza) {
    while (text.toString().indexOf(busca) != - 1)
        text = text.toString().replace(busca, reemplaza);
    return text;
}

function unformato(text) {
    var numText = replaceAllP(text, ".", ""); //quita los puntos de miles
    return Number(numText); //convierte en numero
}

function formato(text) {
    var num = Number(text);
    return num.toLocaleString("de-DE"); //le agrega el punto de miles    
}


function formatoPuntos(input){
    var num = input.replace(/\./g, '');
    if (!isNaN(num)) {
        num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1.');
        num = num.split('').reverse().join('').replace(/^[\.]/, '');
        return num;
    } else {
        return input.replace(/[^\d\.]*/g, '');
    }
}






