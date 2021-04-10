<div class="content-wrapper">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-md-10">
            <section class="content-header">    
                <h1>      
                    Ventas realizadas   
                </h1>

                <ol class="breadcrumb">      
                    <li><a href="inicio"><i class="fa fa-dashboard"></i> Inicio</a></li>      
                    <li class="active">Ventas</li>    
                </ol>
            </section>
        </div>
    </div>
    <div class="row">
        <div class="col-md-1">

        </div>
        <div class="col-md-10">
            <section class="content">
                <div class="box">
                    <div class="box-header with-border">  
                        <a href="crear-venta">
                            <button class="btn btn-primary">            
                                Nueva Venta
                            </button>
                        </a>
                        <button type="button" class="btn btn-default pull-right" id="daterange-btn">           
                            <span>
                                <i class="fa fa-calendar"></i> 
                                Rango de fecha
                            </span>
                            <i class="fa fa-caret-down"></i>
                        </button>
                    </div>
                    <div class="box-body">        
                        <table class="table table-bordered table-striped dt-responsive tablas tablaVentas" width="100%">         
                            <thead>         
                                <tr>           
                                    <th style="width:100px">#Factura</th>
                                    <th>Fecha</th>
                                    <th style="width:50px">Cant. Productos</th>
                                    <th style="width:100px">Ganancia o utilidad</th>
                                    <th style="width:100px">Total Venta</th>            
                                    <th style="width:100px">Acciones</th>
                                </tr> 
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>

<!--=====================================
MODAL DETALLE DE VENTA
======================================-->
<%@include file="./modal/modalDetalleVenta.jsp" %>