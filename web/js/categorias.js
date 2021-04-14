/*=============================================
 VARIABLE GLOBAL - ARRAY CON OBJETOS CATEGORIA TRAIDOS DE BD
 =============================================*/

var alistCategorias;

$(document).ready(function () {
//    $("#box-widget").activateBox();
    //detectar si esta desde categorias
    if (window.location.pathname === "/categorias") {        
        loadCategorias();
        $('li').removeClass('active');
        $('#licategorias').addClass('active');
    }
});


$("#modalAgregarCategoria").on("hidden.bs.modal", function () {
    $("#nuevaCategoria").val('');
});


/*=============================================
 EDITAR CATEGORIA - LLENA EL MODAL CON LOS DATOS DE LA CATEGORIA
 =============================================*/
$(".tablas").on("click", ".btnEditarCategoria", function () {
    var idDepartamento = $(this).attr("idCategoria");

    var params = {
        option: "getDepartamento",
        idDepartamento: idDepartamento
    };

    $.get("/CtrDepartamentos.do", params, function (response) {
        $("#editarCategoria").val(response);
        $("#idCategoria").val(idDepartamento);
    });
});

//Validar input de categorias
$(".input-categoria").on('input', function () {
    var descripcion = this.value;
//    var res = existCategoria2(descripcion);
    if (descripcion === '') {
        $(".errorinput").text("El campo no puede quedar vacío!");
        $(".submit-cat").prop("disabled", true);
    } else if (existCategoria(descripcion)) {
        $(".errorinput").text("Este nombre de categoria ya existe!");
        $(".submit-cat").prop("disabled", true);
    } else {
        $(".errorinput").text("");
        $(".submit-cat").prop("disabled", false);
    }
});

$(".input-categoria").enterKey(function () {
    editCategoria();
});

$(".exit-cat").click(function () {
    $(".errorinput").text("");
    $(".input-categoria").val("");
});


/*=============================================
 ELIMINAR CATEGORIA
 =============================================*/
$(".tablas").on("click", ".btnEliminarCategoria", function () {

    var idDepartamento = $(this).attr("idCategoria");

    Swal.fire({
        title: '¿Está seguro de borrar la categoría?',
        text: "¡Si no lo está puede cancelar la acción!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, borrar categoria!'
    }).then((result) => {
        if (result.isConfirmed) {
            deleteCategoria(idDepartamento);
        }
    });
});


function deleteCategoria(idDepartamento) {

    var params = {
        option: "depDelete",
        idDepartamento: idDepartamento
    };

    showLoader("Eliminando categoria");

    $.get("/CtrDepartamentos.do", params, function (response) {
        var indexPage = $('#tableCategorias').DataTable().page();

        if (response === "success") {
            var id = Number(idDepartamento);
            var o = alistCategorias.find(result => result.idCategoria === id); //extra el objeto con el argumento idDepartamento
            var index = alistCategorias.indexOf(o); //busca el indice del objeto en el array
            alistCategorias.splice(index, 1); //elimina el objeto del array

            loadCategoriaTable("Categoria eliminada con éxito!"); //redibuja la tabla
        } else if (response === "error") {
            closeLoader();
            alertBottomEnd("La categoria tiene productos!", response);
        } else if (response === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo eliminar!", response);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('#tableCategorias').DataTable().page(indexPage).draw('page');
    });
}

function editCategoria() {

    var idDepartamento = $("#idCategoria").val();
    var descripcion = $("#editarCategoria").val();

    var params = {
        option: "depEdit",
        idDepartamento: idDepartamento,
        descriptionDepartamento: descripcion
    };
    
    showLoader("Editando categoria");
    $.get("/CtrDepartamentos.do", params, function (response) {
        var indexPage = $('#tableCategorias').DataTable().page();

        if (response === "success") {
            console.log(response);
            var id = Number(idDepartamento);
            var o = alistCategorias.find(result => result.idCategoria === id); //extra el objeto con el argumento idDepartamento
            var index = alistCategorias.indexOf(o); //busca el indice del objeto en el array
            alistCategorias[index].descripcion = descripcion.toUpperCase(); //agrega descripcion al objeto del array

            loadCategoriaTable("Categoria modificada con éxito!"); //redibuja la tabla
            $("#modalEditarCategoria").modal('hide');
        }
        if (response === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo modificar la categoria!", response);
        } else if (response === "error") {
            closeLoader();
            alertBottomEnd("Ya existe una categoria con ese nombre!", response);
        }

        //Se ubica en la pagina de la tabla donde estaba
        $('#tableCategorias').DataTable().page(indexPage).draw('page');
    });

}

function addCategoria() {

    var descripcion = $("#nuevaCategoria").val();

    var params = {
        option: "depAdd",
        descriptionDepartamento: descripcion
    };
    
    showLoader("Creando categoria");
    $.post("/CtrDepartamentos.do", params, function (response) {

        if (response.result === "success") {

            alistCategorias.push(response); //agrega el nuevo producto al array
            loadCategoriaTable("Categoria creada con éxito!"); //redibuja la tabla        
            $("#modalAgregarCategoria").modal('hide');
        } else if (response.result === "warning") {
            closeLoader();
            alertBottomEnd("No se pudo crear la categoria!", response.result);
        } else if (response.result === "error") {
            closeLoader();
            alertBottomEnd("Ya existe una categoria con ese nombre!", response.result);
        }

        //detectar si esta desde categorias o productos
        if ((window.location.href).includes("productos")) {
            loadCategoriasSelect();
            var selectCategorias = $(".nuevaCategoriaSelect");
            selectCategorias.val(descripcion.toUpperCase());
        }
    });

}

function existCategoria(descripcion) {
    var result = alistCategorias.find(result => result.descripcion === descripcion.toUpperCase());
    return result != undefined;
}

function loadCategorias() {
    var params = {
        option: "getListDepartamentos"
    };
    showLoader('Cargando Categorias');
    $.post("/CtrDepartamentos.do", params, function (responseJson) {
        alistCategorias = responseJson;
        loadCategoriaTable("");
    });
}

function loadCategoriaTable(mensaje) {
    var tabla = $("#tableCategorias").DataTable();
    tabla.clear();

    $.each(alistCategorias, function (index, c) {

        //buttons actions
        var botones = "<div class='btn-group'><button class='btn btn-warning btnEditarCategoria' idCategoria='" + c.idCategoria + "' data-toggle='modal' data-target='#modalEditarCategoria'><i class='fa fa-pencil'></i></button>\n\
                            <button class='btn btn-danger btnEliminarCategoria' idCategoria='" + c.idCategoria + "'><i class='fa fa-times'></i></button></div>";

        tabla.row.add([
            index + 1,
            c.descripcion,
            c.fechaCreacion,
            botones
        ]).draw(false);
    });
    tabla.order([1, 'asc']).draw();

    setTimeout(() => {
        closeLoader();
        if(mensaje != ""){
            alertBottomEnd(mensaje, "success");
        }        
    }, 1000);
}
