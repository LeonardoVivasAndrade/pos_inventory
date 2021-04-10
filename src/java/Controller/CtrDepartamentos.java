package Controller;

import DAO.Conexion;
import DAO.DepartamentoJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Departamento;
import DTO.Usuario;
import Utileria.Util;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class CtrDepartamentos extends HttpServlet {

    protected EntityManagerFactory emf = new Conexion().getBd();
    protected DepartamentoJpaController departamentoDao = new DepartamentoJpaController(emf);
    protected final String SUCCESS = "success";
    protected final String ERROR_INTEGRITY = "error";
    protected final String ERROR = "warning";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String option = request.getParameter("option");
        String idDepartamento = request.getParameter("idDepartamento");

        switch (option) {
            case "depDelete":
                String delDeptReturn = deleteDepartamento(Integer.valueOf(idDepartamento));
                response.getWriter().write(delDeptReturn);
                break;

            case "depEdit": {
                String descriptionDepartamento = request.getParameter("descriptionDepartamento");
                String editDeptReturn = editDepartamento(Integer.valueOf(idDepartamento), descriptionDepartamento);
                response.getWriter().write(editDeptReturn);
                break;
            }
            case "getDepartamento":
                String descripcion = departamentoDao.findDepartamento(Integer.valueOf(idDepartamento)).getDpDescripcion();
                response.getWriter().write(descripcion);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String option = request.getParameter("option");

        switch (option) {

            case "depAdd": {
                try {                  
                    String descriptionDepartamento = request.getParameter("descriptionDepartamento");
                    String retorno = addDepartamento(descriptionDepartamento).toString();

                    response.getWriter().write(retorno);
                } catch (Exception ex) {
                    //pendient enviar mensaje si no funciona
//                    String retorno = new JSONObject().put("result", ERROR).toString();
//                    response.getWriter().write(retorno);
                }
                break;
            }
            case "getListDepartamentos":
                String list = "";
                try {
                    list = Util.departamentosToJSON(listDepartamentos()).toString();
                } catch (JSONException ex) {
                    Logger.getLogger(CtrProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.getWriter().write(list);
                break;
        }
    }

    public int departamentosCount() {
        return departamentoDao.getDepartamentoCount();
    }

    public List<Departamento> listDepartamentos() {
        return departamentoDao.findDepartamentoEntities();
    }

    private String deleteDepartamento(int idDepartamento) {
        try {
            departamentoDao.destroy(idDepartamento);
            return SUCCESS;
        } catch (IllegalOrphanException ex) {
            if (ex.getMessage().contains("in its inventarioList field"));
            return ERROR_INTEGRITY;
        } catch (NonexistentEntityException ex) {
            return ERROR;
        }
    }

    private String editDepartamento(int idDepartamento, String descripcion) {

        try {
            Departamento departamento = departamentoDao.findDepartamento(idDepartamento);
            departamento.setDpDescripcion(descripcion.toUpperCase());
            departamento.setDpFechamodificacion(new Date());
            departamentoDao.edit(departamento);
            return SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CtrProductos.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        } catch (Exception ex) {
            if (ex.getMessage().contains("Duplicate entry"));
            return ERROR_INTEGRITY;
        }
    }

    private JSONObject addDepartamento(String descripcion) throws JSONException {
        JSONObject documento = new JSONObject();
        
        try {
            Departamento departamento = new Departamento(0);
            departamento.setDpDescripcion(descripcion.toUpperCase());
            departamento.setDpFechacreacion(new Date());
            departamento.setDpFechamodificacion(new Date());
            departamento.setDpIduser(new Usuario(1));
            departamentoDao.create(departamento);
            
            documento = Util.departamentoToJSON(departamento);
            documento.put("result", SUCCESS);            
            
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                documento.put("result", ERROR_INTEGRITY);
            } else {
                documento.put("result", ERROR);
            }
        }
        return documento;
    }

}
