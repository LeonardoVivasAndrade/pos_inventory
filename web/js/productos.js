
/* global listProductos */

var listProductos = [];

$(document).ready(function () {
    //detectar si esta desde productos
    if ((window.location.href).includes("productos")) {
        loadCategorias();
        loadProductos();
//        loadProductsTable();        
    }
});


/*=============================================
 CREAR NUEVA CATEGORIA DESDE PRODUCTOS
 =============================================*/
$(".nuevaCategoriaSelect").change(function () {
    var index = $(".nuevaCategoriaSelect").index($(this));
    validarSelect(index);

    var idCategoria = $(this).val();

    if (idCategoria == -1) {
        $("#modalAgregarCategoria").modal('show');
        loadCategoriasSelect(index);
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
 EVENTO EDITAR PRODUCTO
 =============================================*/
$(".tablaProductos tbody").on("click", "button.btnEditarProducto", function () {
    loadCategoriasSelect(1); //1, select del modal editar
    var id = $(this).attr('idProducto');
    llenarInputsEditProducto(id);
});

/*=============================================
 EVENTO ELIMINAR PRODUCTO
 =============================================*/
$(".tablaProductos tbody").on("click", "button.btnEliminarProducto", function () {

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

/*=============================================
 EVENTOS INPUTS MODAL AGREGAR PRODUCTO
 =============================================*/
$(".nuevoPrecio").on('input', function () {
    var index = $(".nuevoPrecio").index($(this));
    calcularPorcentaje(index);
    validarPrecio(index);
});

$(".nuevoCosto").on('input', function () {
    var index = $(".nuevoCosto").index($(this));
    calcularPrecioFromCosto(index);
    calcularPorcentaje(index);
    validarCosto(index);
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
        var porcentaje = Number(($(".nuevoCosto").eq(index).val() * valorPorcentaje / 100)) + Number($(".nuevoCosto").eq(index).val());

        $(".nuevoPrecio").eq(index).val(porcentaje.toFixed(0));
        $(".nuevoPrecio").eq(index).prop("readonly", true);

    }
}

function calcularPorcentaje(index) {
    if (!$(".porcentaje").eq(index).prop("checked")) {
        var venta = $(".nuevoPrecio").eq(index).val();
        var compra = $(".nuevoCosto").eq(index).val();

        var nuevoPorcentaje = Number(((venta - compra) / compra) * 100);
        $(".nuevoPorcentaje").eq(index).val(nuevoPorcentaje.toFixed(0));
    }
}

/*=============================================
 FUNCIONES CRUD PARA PRODUCTO
 =============================================*/
function addProducto() {

    if (!validarSelect(0)) {
        alertBottomEnd("Seleccionar una categoria", 'warning');
        return;
    }


    var params = {
        option: "prodAdd",
        idCategoria: $('.nuevaCategoriaSelect option:selected').eq(0).attr('id'),
        codigo: $(".nuevoCodigo").eq(0).val(),
        descripcion: $(".nuevaDescripcion").eq(0).val(),
        cantidad: $(".nuevaCantidad").eq(0).val(),
        porcentaje: $(".nuevoPorcentaje").eq(0).val(),
        costo: $(".nuevoCosto").eq(0).val(),
        precio: $(".nuevoPrecio").eq(0).val()
    };

    //Retorna un JSON con los atributos del nuevo producto creado
    //Parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrProductos.do", params, function (response) {

        if (response.result === "success") {
            alertBottomEnd("Producto creado con éxito!", response.result);

            listProductos.push(response); //agrega el nuevo producto al array
            loadProductsTable(); //redibuja la tabla

            $("#modalAgregarProducto").modal('hide');
        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo crear el producto!", response.result);
        } else if (response.result === "error") {
            alertBottomEnd("Ya existe un producto con ese nombre ó código!", response.result);
        }
    });
}

function llenarInputsEditProducto(idProducto) {
    var id = Number(idProducto);
    var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto

    $(".nuevoCosto").eq(1).val(o.costo);
    $(".nuevoPrecio").eq(1).val(o.precio);
    $(".nuevaDescripcion").eq(1).val(o.descripcion);
    $(".nuevoCodigo").eq(1).val(o.codigo);
    $(".nuevaCantidad").eq(1).val(o.existencia);
    $('.nuevaCategoriaSelect').eq(1).val(o.categoria);

    calcularPorcentaje(1);
    localStorage.setItem("idProductoToEdit", idProducto);
}

function editProducto() {
    var idProducto = localStorage.getItem("idProductoToEdit");
    if (!validarSelect(1)) {
        alertBottomEnd("Seleccionar una categoria", 'warning');
        return;
    }

    var params = {
        option: "prodEdit",
        idProducto: idProducto,
        idCategoria: $('.nuevaCategoriaSelect option:selected').eq(1).attr('id'),
        descripcion: $(".nuevaDescripcion").eq(1).val(),
        cantidad: $(".nuevaCantidad").eq(1).val(),
        porcentaje: $(".nuevoPorcentaje").eq(1).val(),
        costo: $(".nuevoCosto").eq(1).val(),
        precio: $(".nuevoPrecio").eq(1).val()
    };

    //Retorna un JSON con los atributos del producto editado
    //Parametro result (SUCCESS,ERROR, WARNING)
    $.get("/CtrProductos.do", params, function (response) {
        var indexPage = $('.tablaProductos').DataTable().page();

        if (response.result === "success") {
            alertBottomEnd("Producto editado con éxito!", response.result);

            var id = Number(idProducto);
            var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
            var index = listProductos.indexOf(o); //busca el indice del objeto en el array

            listProductos[index] = response; //elimina el objeto del array  
            loadProductsTable(); //redibuja la tabla

            $("#modalEditarProducto").modal('hide');
            localStorage.setItem("idProductoToEdit", "");
        } else if (response.result === "warning") {
            alertBottomEnd("No se pudo editar el producto!", response.result);
        } else if (response.result === "error") {
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

    $.get("/CtrProductos.do", params, function (response) {
        var indexPage = $('.tablaProductos').DataTable().page();

        if (response.result === "success") {
            var id = Number(idProducto);
            var o = listProductos.find(result => result.idProducto === id); //extrae el objeto con el argumento idProducto
            var index = listProductos.indexOf(o); //busca el indice del objeto en el array
            listProductos.splice(index, 1); //elimina el objeto del array

            alertBottomEnd("Producto eliminado con éxito!", response.result);
            loadProductsTable(); //redibuja la tabla
        } else if (response.result === "error") {
            alertBottomEnd("El Producto tiene operaciones!", response.result);
        } else if (response.result === "warning") {
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
        if ((window.location.href).includes("productos")) {
            loadProductsTable();            
        }
        if ((window.location.href).includes("venta")) {
            loadProductosVenta();
        }
        if ((window.location.href).includes("compra")) {
            loadProductosCompra();
        }
        
    });
}

function loadProductsTable() {

    var tabla = $(".tablaProductos").DataTable();
    tabla.clear();

    $.each(listProductos, function (index, p) {
        //imagen producto
        var imagenUrl = p.imagen;
        if (imagenUrl === "")
            imagenUrl = "img/productos/default/anonymous.png";
//        var imagen = "<img src='" + imagenUrl + "' width='40px'>";

        //button stock
        var btnCantidad = "<button class='btn btn-danger'>" + p.existencia + "</button>";
        if (p.existencia > 0)
            btnCantidad = "<button class='btn btn-success'>" + p.existencia + "</button>";

        //buttons actions
        var botones = "<div class='btn-group'><button class='btn btn-warning btnEditarProducto' idProducto='" + p.idProducto + "' data-toggle='modal' data-target='#modalEditarProducto'><i class='fa fa-pencil'></i></button>\n\
                            <button class='btn btn-danger btnEliminarProducto' idProducto='" + p.idProducto + "' codigo='" + p.codigo + "' imagen='" + p.imagen + "'><i class='fa fa-times'></i></button></div>";

        tabla.row.add([
            p.codigo,
            p.descripcion,
            p.categoria,
            btnCantidad,
            p.costo.toLocaleString("de-DE"),
            p.utilidad.toLocaleString("de-DE"),
            p.precio.toLocaleString("de-DE"),
//            p.fechaCreacion,
            botones
        ]).draw(false);
    });
    tabla.order([2, 'asc']).draw();
    
    setTimeout(() => {
        closeLoader();
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
        var porcentaje = Number(($(".nuevoCosto").eq(index).val() * valorPorcentaje / 100)) + Number($(".nuevoCosto").eq(index).val());

        $(".nuevoPrecio").eq(index).val(porcentaje.toFixed(0));
        $(".nuevoPrecio").eq(index).prop("readonly", true);

    }
}

function allInputsValidos(index) {
    var result = validarDescripcion(index) === "" && validarCantidad(index) === "" &&
            validarInputCosto() === "" && validarInputPrecio() === "";

    return result;
}

function validarSelect(index) {
    var idSelect = $('.nuevaCategoriaSelect option:selected').eq(index).attr('id');

    if (idSelect === -2 || idSelect == undefined) {
        $(".errorInputSelect").text("Seleccionar una categoria!");
        return false;
    } else {
        $(".errorInputSelect").text("");
        return true;
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
    $(".errorInputPrecio").text('');
    $(".errorInputCosto").text('');
    $(".errorInputSelect").text('');

    $(".porcentaje").eq(1).prop("checked", false);
    $(".porcentaje").eq(1).parent().removeClass('checked');
}






