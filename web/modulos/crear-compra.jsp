
<div class="content-wrapper">
    <section class="content-header">    
        <h1>      
            Compra de Mercancía
        </h1>

        <ol class="breadcrumb">      
            <li><a href="#"><i class="fa fa-dashboard"></i> Inicio</a></li>      
            <li class="active">Compra Mercancía</li>    
        </ol>
    </section>
    <section class="content">

        <div class="row">
            <!--=====================================
            LA TABLA DE PRODUCTOS
            ======================================-->

            <div class="col-sm-6">  
                <div class="box box-danger">
                    <div class="box-header with-border">
                        <div class="box-body">            
                            <table class="table tablas table-bordered table-striped  tablaCompra">              
                                <thead>
                                    <tr>                                    
                                        <th>Descripción</th>
                                        <th style="width: 30px">Costo</th>
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
                            ENTRADA DEL CÓDIGO
                            ======================================--> 
                            <strong><p>Número de compra</p></strong>
                            <div class="form-group">                     
                                <div class="input-group">                    
                                    <span class="input-group-addon"><i class="fa fa-key"></i></span> 
                                    <input type="text" class="form-control" id="nuevaCompra" readonly>  
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
                                                <th><h3>Total Compra</h3></th>      
                                            </tr>
                                        </thead>
                                        <tbody>                      
                                            <tr>
                                                <td style="width: 50%">                            
                                                    <div class="input-group">                           
                                                        <span class="input-group-addon"><i class="ion ion-social-usd"></i></span>
                                                        <input type="text" class="form-control input-lg" id="nuevoTotalCompra" name="nuevoTotalCompra" total="" placeholder="0" readonly required>
                                                        <input type="hidden" name="totalCompra" id="totalCompra"> 
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <hr>
                        </div>
                    </div>

                    <div class="box-footer">
                        <button id="btnTotalizar" ype="button" onclick='totalizarCompra()' class="btn btn-block btn-danger" style="font-weight: bold; font-size: large;" disabled>TOTALIZAR</button>
                    </div>
                </div>            
            </div>
        </div>   
    </section>
</div>


