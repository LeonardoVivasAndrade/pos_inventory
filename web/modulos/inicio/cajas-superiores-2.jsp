<%@page import="Controller.CtrDepartamentos"%>
<%@page import="Controller.CtrClientes"%>
<%@page import="Controller.CtrProductos"%>
<%@page import="Controller.CtrVentas"%>


<%
    CtrProductos ctrProductos = new CtrProductos();
    String sumaValorInventario = ctrProductos.totalValorInventario();
    String sumaValorUtilidad = ctrProductos.totalValorUtilidad();
    String sumaValorPrecios = ctrProductos.totalValorPrecios();
    String sumaExistencia = ctrProductos.totalExistencia();
%>
<!--caja ventas-->
<div class="col-lg-3 col-xs-6">    
    <div class="small-box bg-aqua">
        <div class="inner">
            <h3><%=sumaValorInventario%></h3>
            <p>Total Valor del Inventario</p>
        </div>

        <div class="icon">
            <i class="ion ion-social-usd"></i>
        </div>

        <a href="#" class="small-box-footer">
             
        </a>
    </div>
</div>

<!--caja categorias-->
<div class="col-lg-3 col-xs-6">
    <div class="small-box bg-green">
        <div class="inner">
            <h3><%=sumaValorUtilidad%></h3>
            <p>Total Ganancia</p>
        </div>

        <div class="icon">
            <i class="ion ion-clipboard"></i>
        </div>

        <a href="#" class="small-box-footer">
             
        </a>
    </div>
</div>

<!--caja clientes-->
<div class="col-lg-3 col-xs-6">

    <div class="small-box bg-yellow">

        <div class="inner">
            <h3><%=sumaValorPrecios%></h3>
            <p>Total Venta futura</p>
        </div>

        <div class="icon">
            <i class="ion ion-person-add"></i>
        </div>

        <a href="#" class="small-box-footer">
             
        </a>
    </div>
</div>

<!--caja productos-->
<div class="col-lg-3 col-xs-6">
    <div class="small-box bg-red">
        <div class="inner">
            <h3><%=sumaExistencia%></h3>
            <p>Total Existencia</p>
        </div>

        <div class="icon">
            <i class="ion ion-ios-cart"></i>
        </div>

        <a href="#" class="small-box-footer">
            
        </a>
    </div>
</div>
            