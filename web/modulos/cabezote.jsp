
<header class="main-header">

    <!--=====================================
    LOGOTIPO
    ======================================-->
    <a href="/" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>SIS</b> V</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>SIS</b> VENTAS</span>
    </a>

    <!--=====================================
    BARRA DE NAVEGACIÓN
    ======================================-->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Botón de navegación -->

        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>        

        <!-- perfil de usuario -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                
                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="img/usuarios/default/anonymous.png" class="user-image" alt="User Image">
                        <span class="hidden-xs">Administrador</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="img/usuarios/default/anonymous.png" class="img-circle" alt="User Image">
                            <p>
                                Usuario Administrador
                                <small></small>
                            </p>
                        </li>
                        <!-- Menu Body -->
                        <li class="user-body">
                            <div class="row">
                                <div class="col-xs-4 text-center">
                                    <a href="productos">Productos</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="categorias">Categorias</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="crear-venta">Ventas</a>
                                </div>
                            </div>
                            <!-- /.row -->
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
<!--                            <div class="pull-left">
                                <a href="#" class="btn btn-default btn-flat">Profile</a>
                            </div>-->
                            <div class="pull-right">
                                <a onclick='logout()' class="btn btn-default btn-flat">Salir</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>