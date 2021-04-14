<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Administrar productos o servicios
        </h1>
        <ol class="breadcrumb">
            <li><a href="inicio"><i class="fa fa-dashboard"></i> Inicio</a></li>
            <li class="active">Administrar productos</li>
        </ol>
    </section>

    <section class="content">
        <div class="row">      
            <!--si es administrador-->
            <%@include file="inicio/cajas-superiores-2.jsp" %>
        </div> 
        <div class="box">
            <div class="box-header with-border">
                <button class="btn btn-primary btnAgregarProducto" data-toggle="modal" data-target="#modalAgregarProducto">
                    Agregar producto
                </button>
            </div>

            <div class="box-body">
                <table id="tablaProductos" class="table table-bordered table-striped dt-responsive tablaProductos tablas" width="100%">
                    <thead>
                        <tr>
                            <!--<th data-orderable="false" style="width:50px">Imagen</th>-->
                            <th style="width:50px">Código</th>
                            <th style="width:500px">Descripción</th>
                            <th style="width:150px">Categoría</th>
                            <th style="width:20px">Existencia</th>
                            <th style="width:100px">Costo</th>
                            <th style="width:100px">Ganancia</th>
                            <th style="width:100px" >Precio</th>
                            <th style="width:80px" data-orderable="false">Acciones</th>
                        </tr> 
                    </thead> 
                </table>
                <input type="hidden" value="<?php echo $_SESSION['perfil']; ?>" id="perfilOculto">
            </div>
        </div>
    </section>
</div>

<!--=====================================
MODAL AGREGAR PRODUCTO
======================================-->
<div id="modalAgregarProducto" class="modal fade" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!--=====================================
            CABEZA DEL MODAL
            ======================================-->

            <div class="modal-header" style="background:#3c8dbc; color:white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Agregar producto</h4>
            </div>

            <!--=====================================
            CUERPO DEL MODAL
            ======================================-->

            <div class="modal-body">
                <div class="box-body">

                    <!-- ENTRADA PARA SELECCIONAR CATEGORÍA -->
                    <div class="form-group">
                        <div class="input-group">

                            <span class="input-group-addon"><i class="fa fa-th"></i></span> 
                            <select class="form-control input-lg nuevaCategoriaSelect" required>
                                <option value="-2">Seleccionar categoría</option>
                                <option style="color:blue; font-weight: bold;" value="-1">Agregar Nueva Categoria</option>
                            </select>

                        </div>
                        <p style='color:red' class="errorInputSelect"></p>
                    </div>

                    <!-- ENTRADA PARA NUEVO CÓDIGO -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-code"></i></span> 
                            <input type="text" class="form-control input-lg nuevoCodigo" placeholder="Ingresar código" aria-describedby="basic-addon1" disabled="true" required>
                            <span class="input-group-addon" id="basic-addon1">Código</span>
                        </div>
                    </div>
                    
                    <!-- ENTRADA PARA NUEVA DESCRIPCIÓN -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-product-hunt"></i></span> 
                            <input type="text" maxlength="100" class="form-control input-lg nuevaDescripcion" placeholder="Ingresar descripción" autocomplete="off" aria-describedby="basic-addon2" required>
                            <span class="input-group-addon" id="basic-addon2">Descripción</span>
                        </div>                        
                        <p style='color:red' class="errorInputDescripcion"></p>
                    </div>

                    <!-- ENTRADA PARA NUEVA CANTIDAD INICIAL 
-->                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-check"></i></span> 
                            <input type="number" class="form-control input-lg nuevaCantidad" min="0" placeholder="Cantidad inicial" aria-describedby="basic-addon3" required>
                            <span class="input-group-addon" id="basic-addon3">Cantidad</span>
                        </div>
                        <p style='color:red' class="errorInputCantidad"></p>
                    </div><!--



                    <!-- CHECKBOX PARA NUEVO PORCENTAJE -->
                    <div class="form-group row col-lg-6">
                        <strong><p>Porcentaje de ganancia</p></strong>
                        <div class="col-xs-6">
                            <div class="form-group">
                                <label>
                                    <input type="checkbox" class="minimal porcentaje" checked required>
                                    Calcular
                                </label>
                            </div>
                        </div>

                        <!-- ENTRADA PARA NUEVO PORCENTAJE -->

                        <div class="col-xs-6" style="padding:0">
                            <div class="input-group">
                                <input type="text" class="form-control input-lg nuevoPorcentaje" min="0" value="30" required>
                                <span class="input-group-addon"><i class="fa fa-percent"></i></span>
                            </div>
                        </div>
                        <br>
                        <p>Imagen del producto</p>
                        <img src="img/productos/default/anonymous.png" class="img-thumbnail previsualizar" width="100px">
                    </div>

                    <!-- ENTRADA PARA NUEVO COSTO -->

                    <div class="col-xs-6">
                        <strong><p>Valor de compra</p></strong>
                        <div class="input-group">                                    
                            <span class="input-group-addon"><i class="fa fa-arrow-up"></i></span>                                     
                            <input type="text" class="form-control input-lg nuevoCosto" step="any" min="0" placeholder="Costo compra" required>
                        </div>
                        <p style='color:red' class="errorInputCosto"></p>
                        <br>

                        <!-- ENTRADA PARA NUEVO PRECIO -->
                        <strong><p>Valor de venta</p></strong>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-arrow-down"></i></span> 
                            <input type="text" class="form-control input-lg nuevoPrecio" step="any" min="0" placeholder="Precio de venta" readonly required>
                        </div>
                        <p style="color:red;" class="errorInputPrecio">Desactiva Calcular para ingresar precio manualmente!</p>
                        <br>
                    </div>

                </div>

                <!-- ENTRADA PARA SUBIR FOTO -->

                <!--                    <div class="form-group">
                                        <div class="panel">SUBIR IMAGEN</div>
                                        <input type="file" class="nuevaImagen" name="nuevaImagen" disabled>
                                        <p class="help-block">Peso máximo de la imagen 2MB</p>
                                        <img src="img/productos/default/anonymous.png" class="img-thumbnail previsualizar" width="100px">
                                    </div>-->
            </div>

            <!--=====================================
            PIE DEL MODAL
            ======================================-->
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Salir</button>
                <button type="button" onclick="addProducto()" class="btn btn-primary submit-prod" disabled="true">Guardar producto</button>
            </div>
            <!--</form>-->
        </div>
    </div>
</div>

<!--=====================================
MODAL EDITAR PRODUCTO
======================================-->
<div id="modalEditarProducto" class="modal fade" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!--=====================================
            CABEZA DEL MODAL
            ======================================-->

            <div class="modal-header" style="background:#3c8dbc; color:white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Editar producto</h4>
            </div>

            <!--=====================================
            CUERPO DEL MODAL
            ======================================-->

            <div class="modal-body">
                <div class="box-body">

                    <!-- ENTRADA PARA SELECCIONAR CATEGORÍA -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-th"></i></span> 
                            <select class="form-control input-lg nuevaCategoriaSelect" required>
                                <option value="-2">Seleccionar categoría</option>
                                <option style="color:blue; font-weight: bold;" value="-1">Agregar Nueva Categoria</option>
                            </select>
                        </div>
                        <p style='color:red' class="errorInputSelect"></p>
                    </div>

                    <!-- CÓDIGO -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-code"></i></span> 
                            <input type="text" class="form-control input-lg nuevoCodigo" placeholder="Ingresar código" aria-describedby="basic-addon11" required disabled="true">
                            <span class="input-group-addon" id="basic-addon11">Código</span>
                        </div>

                    </div>
                    <!-- ENTRADA PARA EDITAR DESCRIPCIÓN -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-product-hunt"></i></span> 
                            <input type="text" maxlength="100" class="form-control input-lg nuevaDescripcion" placeholder="Ingresar descripción" autocomplete="off" aria-describedby="basic-addon22" required>
                            <span class="input-group-addon" id="basic-addon22">Descripción</span>
                        </div>
                        <p style='color:red' class="errorInputDescripcion"></p>
                    </div>

                    <!-- ENTRADA PARA EDITAR CANTIDAD -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-check"></i></span> 
                            <input type="number" class="form-control input-lg nuevaCantidad" min="0" placeholder="Cantidad inicial" aria-describedby="basic-addon33" required>
                            <span class="input-group-addon" id="basic-addon33">Existencia</span>
                        </div>
                        <p style='color:red' class="errorInputCantidad"></p>
                    </div>



                    <!-- CHECKBOX PARA EDITAR PORCENTAJE -->
                    <div class="form-group row col-lg-6">
                        <strong><p>Porcentaje de ganancia</p></strong>
                        <div class="col-xs-6">
                            <div class="form-group">
                                <label>
                                    <input type="checkbox" class="minimal porcentaje" required>
                                    Calcular
                                </label>
                            </div>
                        </div>

                        <!-- ENTRADA PARA EDITAR PORCENTAJE -->

                        <div class="col-xs-6" style="padding:0">
                            <div class="input-group">
                                <input type="number" class="form-control input-lg nuevoPorcentaje" min="0" value="30" required>
                                <span class="input-group-addon"><i class="fa fa-percent"></i></span>
                            </div>
                        </div>
                        <br>
                        <p>Imagen del producto</p>
                        <img src="img/productos/default/anonymous.png" class="img-thumbnail previsualizar" width="100px">
                    </div>

                    <!-- ENTRADA PARA EDITAR COSTO -->

                    <div class="col-xs-6">
                        <strong><p>Valor de compra</p></strong>
                        <div class="input-group">                                    
                            <span class="input-group-addon"><i class="fa fa-arrow-up"></i></span>                                     
                            <input type="text" class="form-control input-lg nuevoCosto" step="any" min="0" placeholder="Costo compra" required>
                        </div>
                        <p style='color:red' class="errorInputCosto"></p>
                        <br>

                        <!-- ENTRADA PARA EDITAR PRECIO -->
                        <strong><p>Valor de venta</p></strong>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-arrow-down"></i></span> 
                            <input type="text" class="form-control input-lg nuevoPrecio" step="any" min="0" placeholder="Precio de venta" required>
                        </div>
                        <p style="color:red;" class="errorInputPrecio"></p>
                        <br>
                    </div>

                </div>

                <!-- ENTRADA PARA SUBIR FOTO -->

                <!--                    <div class="form-group">
                                        <div class="panel">SUBIR IMAGEN</div>
                                        <input type="file" class="nuevaImagen" name="nuevaImagen" disabled>
                                        <p class="help-block">Peso máximo de la imagen 2MB</p>
                                        <img src="img/productos/default/anonymous.png" class="img-thumbnail previsualizar" width="100px">
                                    </div>-->
            </div>

            <!--=====================================
            PIE DEL MODAL
            ======================================-->
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Salir</button>
                <button type="button" onclick="editProducto()" class="btn btn-primary submit-prod" disabled="true">Guardar Cambios</button>
            </div>
            <!--</form>-->
        </div>
    </div>
</div>

<!--=====================================
MODAL AGREGAR CATEGORÍA
======================================-->
<%@include file="./modal/modalAgregarCategoria.jsp" %>