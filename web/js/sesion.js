
function login() {
    showLoader("Inicializando");

    setTimeout(() => {
        var params = {
            option: "login",
            user: $("#user").val(),
            password: $("#password").val()
        };

        $.get("/CtrLogin.do", params, function (response) {
            if (response === "error") {
                alertBottomEnd("Error al iniciar sesión", "error");
            } else if (response === "warning") {
                alertBottomEnd("Usuario o contraseña incorrectos!", "warning");
            } else {
                window.location.href = "inicio";
            }

        });
    }, 1000);
}

function logout() {
    showLoader("Cerrando sistema");

    setTimeout(() => {
        var params = {
            option: "logout"
        };

        $.get("/CtrLogin.do", params, function (response) {
            window.location.href = "login";
            closeLoader();
        });

    }, 2000);

}


//animacion
$(document).ready(function () {
    if ((window.location.href).includes("login")) {
        const body = document.querySelector("body");
        const modal = document.querySelector(".modal");
        const modalButton = document.querySelector(".modal-button");
        const closeButton = document.querySelector(".close-button");
        const scrollDown = document.querySelector(".scroll-down");
        let isOpened = false;

        

        const openModal = () => {
            modal.classList.add("is-open");
            body.style.overflow = "hidden";
            $("#user").focus();
        };

        const closeModal = () => {
            modal.classList.remove("is-open");
            body.style.overflow = "initial";
            $('html, body').scrollTop(0);
            $('.scroll-down').show();
            modal.classList.remove("is-open");
            isOpened = false;
        };
        
        modalButton.addEventListener("click", openModal);
        closeButton.addEventListener("click", closeModal);

        document.onkeydown = evt => {
            evt = evt || window.event;
            evt.keyCode === 27 ? closeModal() : false;
        };

        document.getElementById("password").onkeyup = evt => {
            evt = evt || window.event;
            evt.keyCode === 13 ? login() : true;
        };

        document.getElementById("user").onkeyup = evt => {
            evt = evt || window.event;
            evt.keyCode === 13 ? $("#password").focus() : false;
        };

        window.addEventListener("scroll", () => {
            if (window.scrollY > window.innerHeight / 3 && !isOpened) {
                isOpened = true;
                scrollDown.style.display = "none";
                openModal();
            }
        });
    }
});











