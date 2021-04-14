<aside class="main-sidebar">

    <section class="sidebar">
        <ul class="sidebar-menu">
            <!--si es administrador-->
            <li id="liinicio" class='li-sidebar'>
                <a href="inicio">
                    <i class="fa fa-home"></i>
                    <span>Inicio</span>
                </a>
            </li>

            <!--si es administrador o especial-->
            <li id="licategorias" class='li-sidebar'>
                <a href="categorias">
                    <i class="fa fa-th"></i>
                    <span>Categorías</span>
                </a>
            </li>

            <li id="liproductos" class='li-sidebar'>
                <a href="productos">
                    <i class="fa fa-product-hunt"></i>
                    <span>Productos</span>
                </a>

            </li>
            <li id="licompra" class='li-sidebar'>
                <a href="crear-compra">
                    <i class="fa fa-credit-card-alt"></i>
                    <span>Compra Mercancia</span>
                </a>

            </li>
            <!--si es administrador o vendedor-->
            <li id="liventa" class='li-sidebar'>
                <a href="crear-venta">
                    <i class="fa fa-cart-plus"></i>
                    <span>Nueva Venta</span>
                </a>
            </li>

            <!--si es administrador o vendedor-->
            <li id="lireportes" class="li-sidebar treeview">
                <a href="#">
                    <i class="fa fa-list-ul"></i>
                    <span>Reportes</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>

                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="ventas">
                            <i class="fa fa-circle-o"></i>
                            <span>Consultar ventas</span>
                        </a>
                    </li>
                    <li>
                        <a href="compras">
                            <i class="fa fa-circle-o"></i>
                            <span>Consultar compras</span>
                        </a>
                    </li>

                    <!--si es administrador-->

<!--                    <li>
                        <a href="#">
                            <i class="fa fa-circle-o"></i>
                            <span>Reporte compras</span>
                        </a>
                    </li>-->
                </ul>
            </li>

            <li>
                <a href='#' onclick='logout()'>
                    <i class="fa fa-sign-out"></i>
                    <span>Salir</span>
                </a>
            </li>
        </ul>
    </section>
</aside>
