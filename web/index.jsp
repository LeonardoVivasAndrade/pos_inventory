<%-- 
    Document   : Index.jsp
    Created on : 25/03/2021, 03:54:43 PM
    Author     : Leonardo Vivas
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    if(request.getSession().getAttribute("user") == null)
        response.sendRedirect("login");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Sistema Ventas</title>

        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="icon" href="img/plantilla/icon.png">

        <!--=====================================
       PLUGINS DE CSS
       ======================================-->

        <!-- Bootstrap 3.3.7 -->  
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">

        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.css">

        <!-- AdminLTE Skins -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">

        <!-- Google Font -->
        <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">-->

        <!-- DataTables -->
        <link rel="stylesheet" href="bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="bower_components/datatables.net-bs/css/responsive.bootstrap.min.css">

        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="plugins/iCheck/all.css">

        <!-- Daterange picker -->
        <link rel="stylesheet" href="bower_components/bootstrap-daterangepicker/daterangepicker.css">

        <!-- Morris chart -->
        <link rel="stylesheet" href="bower_components/morris.js/morris.css">

        <!-- Tooltip costumer-->
        <link rel="stylesheet" href="dist/css/tooltip.css">

        <!--=====================================
        PLUGINS DE JAVASCRIPT
        ======================================-->

        <!-- jQuery 3 -->
        <script src="bower_components/jquery/dist/jquery.min.js"></script>

        <!-- Bootstrap 3.3.7 -->
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

        <!-- FastClick -->
        <script src="bower_components/fastclick/lib/fastclick.js"></script>

        <!-- AdminLTE App -->
        <!--<script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1.0/dist/js/adminlte.min.js"></script>-->
        <!--<script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>-->
        <script src="dist/js/adminlte.min.js"></script>

        <!-- DataTables -->
        <script src="bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script src="bower_components/datatables.net-bs/js/dataTables.responsive.min.js"></script>
        <script src="bower_components/datatables.net-bs/js/responsive.bootstrap.min.js"></script>

        <!-- SweetAlert 2 -->
        <script src="plugins/sweetalert2/sweetalert2.all.js"></script>
        <!-- By default SweetAlert2 doesn't support IE. To enable IE 11 support, include Promise polyfill:-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>

        <!-- iCheck 1.0.1 -->
        <script src="plugins/iCheck/icheck.min.js"></script>

        <!-- InputMask -->
        <script src="plugins/input-mask/jquery.inputmask.js"></script>
        <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
        <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script>

        <!-- jQuery Number -->
        <script src="plugins/jqueryNumber/jquerynumber.min.js"></script>

        <!-- daterangepicker http://www.daterangepicker.com/-->
        <script src="bower_components/moment/min/moment.min.js"></script>
        <script src="bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>

        <!-- Morris.js charts http://morrisjs.github.io/morris.js/-->
        <!--<script src="bower_components/raphael/raphael.min.js"></script>-->
        <!--<script src="bower_components/morris.js/morris.min.js"></script>-->

        <!-- ChartJS http://www.chartjs.org/-->
        <script src="bower_components/chart.js/Chart.min.js"></script>
        <!--<script src="https://raw.githack.com/chartjs/Chart.js/v1.1.1/Chart.min.js"></script>-->

    </head>

    <!--=====================================
    CUERPO DOCUMENTO
    ======================================-->

    <body class="hold-transition skin-blue sidebar-collapse sidebar-mini login-page">
        <div class="wrapper">
            <!--=============================================
                CABEZOTE
            =============================================-->
            <%@include file="modulos/cabezote.jsp" %>

            <!--============================================
                MENU
            =============================================-->
            <%@include file="modulos/menu.jsp" %>

            <!--============================================
                CONTENT
            =============================================-->
            <%                String ruta = request.getServletPath();

                if (ruta.equals("/categorias")) {%>
            <%@include file="modulos/categorias.jsp" %>
            <%} else if (ruta.equals("/inicio")) {%>
            <%@include file="modulos/inicio.jsp" %>
            <%} else if (ruta.equals("")) {%>
            <%@include file="modulos/inicio.jsp" %>
            <%} else if (ruta.equals("/productos")) {%>
            <%@include file="modulos/productos.jsp" %>
            <%} else if (ruta.equals("/crear-venta")) {%>
            <%@include file="modulos/crear-venta.jsp" %>
            <%} else if (ruta.equals("/ventas")) {%>
            <%@include file="modulos/ventas.jsp" %>
            <%} else if (ruta.equals("/crear-compra")) {%>
            <%@include file="modulos/crear-compra.jsp" %>
            <%} else if (ruta.equals("/compras")) {%>
            <%@include file="modulos/compras.jsp" %>
            <%} else {%>
            <%@include file="modulos/404.jsp" %>
            <%}%>

            <!--=============================================
                FOOTER
            =============================================-->
            <%@include file="modulos/footer.jsp" %>

        </div>

        <script src="js/index.js"></script>
        <script src="js/sesion.js"></script>
        <script src="js/categorias.js"></script>
        <script src="js/productos.js"></script>
        <script src="js/grafica.js"></script>
        <script src="js/venta.js"></script>
        <script src="js/ventas.js"></script>
        <script src="js/compra.js"></script>
        <script src="js/compras.js"></script>
        <!--<script src="js/reportes.js"></script>-->

<!--        <div class="row" style="padding:5px 15px"> 
             Descripción del producto  
            <div class="col-xs-7" style="padding-right:0px"> 
                <div class="input-group"> 
                    <span class="input-group-addon input-sm"><button type="button" onclick="borrarProductoVenta(this)" class="btn btn-danger btn-xs" idProducto="  idProducto  "><i class="fa fa-times"></i></button></span> 
                    <input type="text" class="form-control nuevaDescripcionProducto" name="agregarProducto" value="  descripcion  " readonly required> 
                </div> 
            </div> 
             Cantidad del producto  
            <div class="col-xs-2"> 
                <input type="number" class="form-control nuevaCantidadProducto" oninput="modificarCantidadItem(this)" idProducto="  idProducto  " onblur="setCantidadDefaultVenta(this)" min="1" value="1" stock="  stock  " nuevoStock="  Number(stock - 1)  " required> 
            </div> 
             Precio del producto  
            <div class="col-xs-3 ingresoPrecio" style="padding-left:0px"> 
                <div class="input-group"> 
                    <span class="input-group-addon"><i class="ion ion-social-usd"></i></span> 
                    <input type="text" class="form-control nuevoPrecioProducto" oninput="modificarPrecioItem(this)" precioReal="  precio  " idProducto="  idProducto  " name="nuevoPrecioProducto" value="  precio  " required> 
                </div> 
            </div> 
        </div>-->

    </body>
</html>
