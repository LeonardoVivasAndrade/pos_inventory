<%@page import="java.util.Map"%>
<%@page import="Controller.CtrProductos"%>
<%
    CtrVentas ctrVentas1 = new CtrVentas();
    Map<String, Object> bestProducts = ctrVentas1.getProductosMasVendidos();
    int cantidadMayor = 0;
    if (bestProducts.size() > 0) {
        Map<String, String> vent = (Map<String, String>) bestProducts.get("0");
        String c = vent.get("cantidad");
        cantidadMayor = Integer.valueOf(c);
    }
%>

<div class="content-wrapper">

    <section class="content-header">

        <h1>      
            Tablero Principal     
            <small>Resúmen de operaciones</small>    
        </h1>

        <ol class="breadcrumb">      
            <li><a href="inicio"><i class="fa fa-dashboard"></i> Inicio</a></li>      
            <li class="active">Tablero</li>    
        </ol>

    </section>
    <section class="content">
        <div class="row">      
            <!--si es administrador-->
            <%@include file="inicio/cajas-superiores.jsp" %>
        </div> 

        <div class="row">       
            <div class="col-lg-12">
                <!--si es administrador-->

                <!--include "reportes/grafico-ventas.php";-->          

            </div>
            <div class="col-lg-6">
                <!--si es administrador-->          
                <!--include "reportes/productos-mas-vendidos.php";-->
            </div>
            <div class="col-lg-6">
                <!--si es administrador-->

                <!--include "inicio/productos-recientes.php";-->
            </div>

            <div class="col-lg-12">          

                <!--si es especial o vendedor-->
                <!--             <div class="box box-success">
                             <div class="box-header">
                             <h1>Bienvenid@ ' .$_SESSION["nombre"].'</h1>
                             </div>
                             </div>-->
            </div>
        </div>

        <!--GRAFICO VENTAS DIARIAS-->
        <div class="row">   
            <div class="col-lg-12">
               
            <%@include file="inicio/grafico-ventas.jsp" %>

            </div>
        </div>

        <!--PRODUCTOS MAS VENDIDOS-->
        <div class="row">      
            <div class="col-lg-6">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">Productos más vendidos</h3>
                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                <i class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove">
                                <i class="fa fa-times"></i>
                            </button>
                        </div>
                    </div>

                    <div class="box-body">
                        <ul class="products-list product-list-in-box">

                            <%for (Map.Entry<String, Object> entry : bestProducts.entrySet()) {
                                    Map<String, String> map = (Map<String, String>) entry.getValue();
                                    int p = (Integer.valueOf(map.get("cantidad")) * 100) / cantidadMayor;
                            %>

                            <li class="item">
                                <div class="row">
                                    <div class="col-md-8">
                                        <div class="product-img">
                                            <img src="../img/productos/default/anonymous.png" alt=""/>
                                        </div>
                                        <div class="product-info">
                                            <a href="#" class="product-title">
                                                <%=map.get("descripcion")%>
                                            </a>
                                            <br>
                                            <span class="label label-info"><%=map.get("departamento")%></span>
                                        </div>

                                    </div>
                                    <div class="col-md-4">
                                        <!--<div class="info-box-content">-->                                                
                                        <span style="font-size: small" class="info-box-number"><%=map.get("cantidad")%> unidades</span>
                                        <!-- The progress section is optional -->
                                        <div class="progress">
                                            <div class="progress-bar" style="width: <%=p%>%"><%=p%>%</div>
                                        </div>
                                        <!--</div>-->
                                    </div>
                                </div>
                            </li>            
                            <%}%>

                        </ul>
                    </div>
                    <div class="box-footer text-center">
                        <a href="productos" class="uppercase">Ver todos los productos</a>
                    </div>
                </div>
            </div>
        </div> 
    </section> 
</div>
