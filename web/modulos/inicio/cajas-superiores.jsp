<%@page import="java.time.LocalDate"%>
<%@page import="Controller.CtrDepartamentos"%>
<%@page import="Controller.CtrClientes"%>
<%@page import="Controller.CtrProductos"%>
<%@page import="Controller.CtrVentas"%>
<%@page import="Utileria.Util"%>


<%
    CtrVentas ctrVentas = new CtrVentas();
    Double suma = ctrVentas.totalVentasDia(LocalDate.now());
    String sumaVentas = Util.doubleFormat(suma);
    
    CtrProductos ctrProductos = new CtrProductos();
    CtrDepartamentos ctrDepartamentos = new CtrDepartamentos();
    int sumaProductos = ctrProductos.productosCount();   
    int sumaCategorias = ctrDepartamentos.departamentosCount();
    
    CtrClientes ctrCliente = new CtrClientes();
    int sumaClientes = ctrCliente.clientesCount();

%>
<!--caja ventas-->
<div class="col-lg-3 col-xs-6">    
    <div class="small-box bg-aqua">
        <div class="inner">
            <h3><%=sumaVentas%></h3>
            <p>Total Venta Diaria</p>
        </div>

        <div class="icon">
            <i class="ion ion-social-usd"></i>
        </div>

        <a href="#" class="small-box-footer">
            Más info <i class="fa fa-arrow-circle-right"></i>
        </a>
    </div>
</div>

<!--caja categorias-->
<div class="col-lg-3 col-xs-6">
    <div class="small-box bg-green">
        <div class="inner">
            <h3><%=sumaCategorias%></h3>
            <p>Categorías</p>
        </div>

        <div class="icon">
            <i class="ion ion-clipboard"></i>
        </div>

        <a href="categorias" class="small-box-footer">
            Más info <i class="fa fa-arrow-circle-right"></i>
        </a>
    </div>
</div>

<!--caja clientes-->
<div class="col-lg-3 col-xs-6">

    <div class="small-box bg-yellow">

        <div class="inner">
            <h3><%=sumaClientes%></h3>
            <p>Clientes</p>
        </div>

        <div class="icon">
            <i class="ion ion-person-add"></i>
        </div>

        <a href="#" class="small-box-footer">
            Más info <i class="fa fa-arrow-circle-right"></i>
        </a>
    </div>
</div>

<!--caja productos-->
<div class="col-lg-3 col-xs-6">
    <div class="small-box bg-red">
        <div class="inner">
            <h3><%=sumaProductos%></h3>
            <p>Productos</p>
        </div>

        <div class="icon">
            <i class="ion ion-ios-cart"></i>
        </div>

        <a href="productos" class="small-box-footer">
            Más info <i class="fa fa-arrow-circle-right"></i>
        </a>
    </div>
</div>