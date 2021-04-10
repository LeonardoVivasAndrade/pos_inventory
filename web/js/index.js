/*=============================================
 SideBar Menu
 =============================================*/

$('.sidebar-menu').tree();

/*=============================================
 Data Table
 =============================================*/

$(".tablas").DataTable({

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
 //iCheck for checkbox and radio inputs
 =============================================*/

$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
    checkboxClass: 'icheckbox_minimal-blue',
    radioClass: 'iradio_minimal-blue'
})

/*=============================================
 //input Mask
 =============================================*/

//Datemask dd/mm/yyyy
$('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
//Datemask2 mm/dd/yyyy
$('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/dd/yyyy'})
//Money Euro
$('[data-mask]').inputmask()

/*=============================================
 CORRECCIÓN BOTONERAS OCULTAS BACKEND	
 =============================================*/

if (window.matchMedia("(max-width:767px)").matches) {

    $("body").removeClass('sidebar-collapse');

} else {

    $("body").addClass('sidebar-collapse');
}

/*=============================================
 PLUGIN PARA EVENTO DE TECLA ENTER, USAGE -> $("element").enterKey(function () { code here });	
 =============================================*/
$.fn.enterKey = function (fnc) {
    return this.each(function () {
        $(this).keypress(function (ev) {
            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
            if (keycode == '13') {
                fnc.call(this, ev);
            }
        });
    });
};


function showLoader(mensaje) {
    Swal.fire({
        title: mensaje,
        timer: 0,
        timerProgressBar: true,
        allowEscapeKey: true,
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading()
        }
    })
}

function closeLoader() {
    Swal.close();
}

function alertBottomEnd(msg, icon) {
    Swal.fire({
        position: 'bottom-end',
        icon: icon,
        title: msg,
        showConfirmButton: false,
        timer: 5000,
        backdrop: false
    });
}


