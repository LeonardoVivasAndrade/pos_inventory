
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--si es vendedor lo manda a inicio-->

<div class="content-wrapper">

    <section class="content-header">
        <h1>
            Administrar categorías
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> Inicio</a></li>
            <li class="active">Administrar categorías</li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <button class="btn btn-primary" data-toggle="modal" data-target="#modalAgregarCategoria">
                    Agregar categoría
                </button>
            </div>
            <div class="box-body">
                <table id="tableCategorias" class="table table-bordered table-striped dt-responsive tablas" width="100%">
                    <thead>
                        <tr>
                            <th style="width:10px">#</th>
                            <th>Nombre de Categoría</th>
                            <th>Fecha Creación</th>
                            <th data-orderable="false">Acciones</th>
                        </tr> 
                    </thead>
                    <tbody>   
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>

<!--=====================================
MODAL AGREGAR CATEGORÍA
======================================-->

<%@include file="./modal/modalAgregarCategoria.jsp" %>

<!--=====================================
MODAL EDITAR CATEGORÍA
======================================-->

<div id="modalEditarCategoria" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--=====================================
            CABEZA DEL MODAL
            ======================================-->

            <div class="modal-header" style="background:#3c8dbc; color:white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Editar categoría</h4>
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
                            <input type="text" class="form-control input-lg input-categoria" name="editarCategoria" id="editarCategoria" required>
                            <input type="hidden" name="idCategoria" id="idCategoria" required>
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
                <button id='btnSaveCategoria' type="button" onclick="editCategoria()" class="btn btn-primary submit-cat" disabled="true">Guardar cambios</button>
            </div>
        </div>
    </div>
</div>



