<!--=====================================
MODAL AGREGAR CATEGORÍA
======================================-->

<div id="modalAgregarCategoria" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--=====================================
            CABEZA DEL MODAL
            ======================================-->
            <div class="modal-header" style="background:#3c8dbc; color:white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Agregar categoría</h4>
            </div>

            <!--=====================================
            CUERPO DEL MODAL
            ======================================-->

            <div class="modal-body">
                <div class="box-body">
                    <!-- ENTRADA PARA EL NOMBRE -->
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-th"></i></span> 
                            <input type="text" class="form-control input-lg input-categoria" name="nuevaCategoria" id="nuevaCategoria" placeholder="Ingresar categoría" autocomplete="off" required>
                        </div>
                        <p style='color:red' class="errorinput"></p>
                    </div>
                </div>
            </div>

            <!--=====================================
            PIE DEL MODAL
            ======================================-->
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left exit-cat" data-dismiss="modal">Salir</button>
                <button type="button" class="btn btn-primary submit-cat" onclick="addCategoria()" disabled="true">Guardar categoría</button>
            </div>
        </div>
    </div>
</div>
