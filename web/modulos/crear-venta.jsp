
<div class="content-wrapper">
    <section class="content-header">    
        <h1>      
            Nueva Venta 
        </h1>

        <ol class="breadcrumb">      
            <li><a href="#"><i class="fa fa-dashboard"></i> Inicio</a></li>      
            <li class="active">Crear venta</li>    
        </ol>
    </section>
    <section class="content">

        <div class="row">
            <!--=====================================
            LA TABLA DE PRODUCTOS
            ======================================-->

            <div class="col-sm-6">  
                <div class="box box-warning">
                    <div class="box-header with-border">
                        <div class="box-body">            
                            <table class="table tablas table-bordered table-striped  tablaVenta">              
                                <thead>
                                    <tr>                                    
                                        <th>Descripción</th>
                                        <th style="width: 30px">Precio</th>
                                        <th style="width: 20px">Stock</th>
                                        <th data-orderable="false" style="width: 20px"></th>
                                    </tr>                                                
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!--=====================================
            EL FORMULARIO
            ======================================-->
            <div class="col-sm-6">        
                <div class="box box-success">          
                    <div class="box-header with-border"></div>                    
                    <div class="box-body">  
                        <div class="box">

                            <!--=====================================
                            ENTRADA DEL VENDEDOR
                            ======================================-->

                            <!--                <div class="form-group">                
                                              <div class="input-group">                    
                                                <span class="input-group-addon"><i class="fa fa-user"></i></span> 
                                                <input type="text" class="form-control" id="nuevoVendedor" value="<?php echo $_SESSION["nombre"]; ?>" readonly>
                            
                                                <input type="hidden" name="idVendedor" value="<?php echo $_SESSION["id"]; ?>">
                                              </div>
                                            </div> -->

                            <!--=====================================
                            ENTRADA DEL CÓDIGO
                            ======================================--> 
                            <strong><p>Número de factura</p></strong>
                            <div class="form-group">                     
                                <div class="input-group">                    
                                    <span class="input-group-addon"><i class="fa fa-key"></i></span> 
                                    <input type="text" class="form-control" id="nuevaVenta" readonly>  
                                </div>                
                            </div>

                            <!--=====================================
                            ENTRADA DEL CLIENTE
                            ======================================--> 

                            <div class="form-group">                 
                                <div class="input-group">                    
                                    <span class="input-group-addon"><i class="fa fa-users"></i></span>                    
                                    <select class="form-control" id="seleccionarCliente" name="seleccionarCliente" required>
                                        <option value="">Seleccionar cliente</option>
                                        <option value="1" selected>Cliente General</option>
                                    </select>                    
                                    <!--<span class="input-group-addon"><button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#modalAgregarCliente" data-dismiss="modal">Agregar cliente</button></span>-->
                                </div>                
                            </div>

                            <!--=====================================
                            ENTRADA PARA AGREGAR PRODUCTO
                            ======================================--> 
                            <strong><p>Lista de productos</p></strong>
                            <div class="form-group row nuevoProducto">                                
                            </div>

                            <hr>
                            <div class="row">
                                <!--=====================================
                                ENTRADA TOTAL
                                ======================================-->

                                <div class="col-xs-8 pull-right">                    
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th><h3>Total Venta</h3></th>      
                                            </tr>
                                        </thead>
                                        <tbody>                      
                                            <tr>
                                                <td style="width: 50%">                            
                                                    <div class="input-group">                           
                                                        <span class="input-group-addon"><i class="ion ion-social-usd"></i></span>
                                                        <input type="text" class="form-control input-lg" id="nuevoTotalVenta" name="nuevoTotalVenta" total="" placeholder="0" readonly required>
                                                        <input type="hidden" name="totalVenta" id="totalVenta"> 
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <hr>

                            <!--=====================================
                            ENTRADA MÉTODO DE PAGO
                            ======================================-->

                            <div class="form-group row">                  
                                <div class="col-xs-4" style="padding-right:0px">                                           
                                    <div class="input-group">       
                                        <p style="font-weight: bold; font-size: medium;" class="input-group">Tipo de pago</p>
                                        <select class="form-control" id="nuevoMetodoPago" name="nuevoMetodoPago" required>
                                            <option value="">Seleccione método de pago</option>
                                            <option value="Efectivo" selected>Efectivo</option>
                                            <option value="TC">Tarjeta Crédito</option>
                                            <option value="TD">Tarjeta Débito</option>                 
                                        </select> 
                                    </div>
                                </div>
                                <div class="cajasMetodoPago">
                                    <div class="col-xs-4">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="ion ion-social-usd"></i>
                                            </span>
                                            <p style="color: green; font-weight: bold; font-size: medium;" class="form-control">Recibido</p>        
                                            <input type="text" min='0' class="form-control" autocomplete="off" id="nuevoValorEfectivo" placeholder="0" required=""></div></div><div class="col-xs-4" id="capturarCambioEfectivo" style="padding-left:0px"><div class="input-group">
                                            <span class="input-group-addon"><i class="ion ion-social-usd"></i>
                                            </span>
                                            <p style="color: red; font-weight: bold; font-size: medium;" class="form-control">Cambio</p>                                                
                                            <input style="color: red; font-size: x-large;" autocomplete="off" type="text" class="form-control" id="nuevoCambioEfectivo" placeholder="0" readonly="" required="">
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" id="listaMetodoPago" name="listaMetodoPago">
                            </div>  
                        </div>
                    </div>

                    <div class="box-footer">
                        <button id="btnTotalizar" ype="button" onclick='totalizarVenta()' class="btn btn-block btn-primary" style="font-weight: bold; font-size: large;" disabled>TOTALIZAR</button>
                    </div>
                </div>            
            </div>


        </div>   
    </section>
</div>

