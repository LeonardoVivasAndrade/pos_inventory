package Controller;

import DAO.Conexion;
import DAO.DepartamentoJpaController;
import DAO.InventarioJpaController;
import DAO.StockJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Departamento;
import DTO.Inventario;
import DTO.Stock;
import DTO.Usuario;
import Utileria.Util;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class CtrProductos extends HttpServlet {

    protected EntityManagerFactory emf = new Conexion().getBd();
    protected InventarioJpaController inventarioDao = new InventarioJpaController(emf);
    protected DepartamentoJpaController departamentoDao = new DepartamentoJpaController(emf);
    protected StockJpaController stockDao = new StockJpaController(emf);
    protected final String SUCCESS = "success";
    protected final String ERROR_INTEGRITY = "error";
    protected final String ERROR = "warning";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String option = request.getParameter("option");
        String idProducto = request.getParameter("idProducto");

        switch (option) {
            case "prodDelete":
                String delProductoReturn = deleteProducto(Integer.valueOf(idProducto));
                response.getWriter().write("{\"result\":\"" + delProductoReturn + "\"}");
                break;
            case "prodEdit":
            case "prodAdd":
                
                try {                  
                    Map<String, String[]> parameters = request.getParameterMap();

                    String retorno = option.endsWith("prodEdit")
                            ? editProducto(parameters).toString()
                            : addProducto(parameters).toString();

                    response.getWriter().write(retorno);
                } catch (Exception ex) {
                    String result = ERROR;
                    response.getWriter().write("{\"result\":\"" + result + "\"}");
                }

                break;
            case "getListProducts":
                String list = "";
                try {
                    list = Util.productosToJSON(listProductos()).toString();
                } catch (JSONException ex) {
                    Logger.getLogger(CtrProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.getWriter().write(list);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String newCodigo = getNewCodigoProducto();
        response.getWriter().write(newCodigo);
    }

    public int productosCount() {
        return inventarioDao.getInventarioCount();
    }

    public List<Inventario> listProductos() {
        return inventarioDao.findInventarioEntities();
    }

    private String deleteProducto(int idProducto) {
        try {
            Inventario producto = inventarioDao.findInventario(idProducto);
            
            boolean existCompras = !producto.getDcompraList().isEmpty();
            boolean existVentas = !producto.getDventaList().isEmpty();
            
            if(existCompras || existVentas){
                return ERROR_INTEGRITY;
            }else{
                
                Stock stock = producto.getStock();
                stockDao.destroy(stock.getStIdinventario());
                inventarioDao.destroy(idProducto);
                return SUCCESS;
            }
        } catch (IllegalOrphanException ex) {
            if (ex.getMessage().contains("cannot be destroyed since"));
            return ERROR_INTEGRITY;
        } catch (NonexistentEntityException ex) {
            return ERROR;
        }
    }

    private JSONObject editProducto(Map<String, String[]> datos) throws JSONException {

        JSONObject documento = new JSONObject();        
        
        try {
            int idProducto = Integer.valueOf(datos.get("idProducto")[0]);
            int idDepartamento = Integer.valueOf(datos.get("idCategoria")[0]);
            String descripcion = datos.get("descripcion")[0].toUpperCase();            
            int porcentaje = Integer.valueOf(datos.get("porcentaje")[0]);
            float costo = Float.valueOf(datos.get("costo")[0]);
            float precio = Float.valueOf(datos.get("precio")[0]);
            float cantidad = Float.valueOf(datos.get("cantidad")[0]);
            
            Departamento departamento = departamentoDao.findDepartamento(idDepartamento);
            
            Stock stock = stockDao.findStock(idProducto);
            stock.setStCantidad(cantidad);
                                
            Inventario producto = inventarioDao.findInventario(idProducto);
            producto.setInIddepartamento(departamento);
            producto.setInDescripcion(descripcion);
            producto.setInUtilidadporcent(porcentaje);
            producto.setInCosto(costo);
            producto.setInPrecioCiva(precio);
            producto.setInFechamodificacion(new Date());
            producto.setStock(stock);
            inventarioDao.edit(producto);
            
            //creat JSON inventario
            documento = Util.productoToJSON(producto);
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

    private JSONObject addProducto(Map<String, String[]> datos) throws JSONException {
        JSONObject documento = new JSONObject();
        
        try {                
            Inventario producto = new Inventario();
            int idDepartamento = Integer.valueOf(datos.get("idCategoria")[0]);
            Departamento departamento = departamentoDao.findDepartamento(idDepartamento);
            
            producto.setInIddepartamento(departamento);
            producto.setInCodigo(datos.get("codigo")[0]);
            producto.setInDescripcion(datos.get("descripcion")[0].toUpperCase());
            producto.setInUtilidadporcent(Integer.valueOf(datos.get("porcentaje")[0]));
            float costo = Float.valueOf(datos.get("costo")[0]);
            float precio = Float.valueOf(datos.get("precio")[0]);
            producto.setInUtilidad(precio-costo);
            producto.setInCosto(costo);
            producto.setInPrecioCiva(precio); 
            producto.setInIduser(new Usuario(1));//pendiente ajustar usuario
            producto.setInFechacreacion(new Date());
            producto.setInFechamodificacion(new Date());
            producto.setInStatus(true);
            producto.setInImage("");
            producto.setInReferencia("");
            
            inventarioDao.create(producto);
            
            //stock create
            float newCantidad = Integer.valueOf(datos.get("cantidad")[0]);
            Stock newStock = new Stock();
            newStock.setInventario(producto);
            newStock.setStIdinventario(producto.getInId());
            newStock.setStCantidad(newCantidad);
            
            newStock.setStFecha(new Date());
            newStock.setStIddepartamento(departamento);
            newStock.setStIduser(new Usuario(1));
            
            float precioTotal = producto.getInPrecioCiva()*newCantidad;
            float costoTotal = producto.getInCosto()*newCantidad;
            float utilidad = (precioTotal-costoTotal);
            
            newStock.setStPreciototal(precioTotal);
            newStock.setStCostototal(costoTotal);            
            newStock.setStUtilidadtotal(utilidad); 
                        
            stockDao.create(newStock);
            
            //add stock al inventario
            producto.setStock(newStock);
            
            //creat JSON inventario
            documento = Util.productoToJSON(producto);
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

    protected String getNewCodigoProducto() {
        Inventario producto = inventarioDao.getInventarioLast();
        if(producto==null) return "0001";
        
        String lastCodigo = producto.getInCodigo();
        int nextCodigo = Integer.valueOf(lastCodigo) + 1;
        
        String newCodigo = Util.llenarCeros(4, nextCodigo);
        return String.valueOf(newCodigo);
    }
    
    public String totalValorInventario() {
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);
        List<Inventario> listInventario = inventarioDao.findInventarioEntities();

        float total = 0;
        for (Inventario inventario : listInventario) {
            float subtotal = inventario.getInCosto() * inventario.getStock().getStCantidad();
            total += subtotal;
        }

        DecimalFormat df = new DecimalFormat("###,###.##");
        return "$" + df.format(total);
    }

    public String totalValorUtilidad() {
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);
        List<Inventario> listInventario = inventarioDao.findInventarioEntities();

        float total = 0;
        for (Inventario inventario : listInventario) {
            float subtotal = inventario.getInUtilidad() * inventario.getStock().getStCantidad();
            total += subtotal;
        }

        DecimalFormat df = new DecimalFormat("###,###.##");
        return "$" + df.format(total);
    }

    public String totalValorPrecios() {
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);
        List<Inventario> listInventario = inventarioDao.findInventarioEntities();

        float total = 0;
        for (Inventario inventario : listInventario) {
            float subtotal = inventario.getInPrecioCiva() * inventario.getStock().getStCantidad();
            total += subtotal;
        }

        DecimalFormat df = new DecimalFormat("###,###.##");
        return "$" + df.format(total);
    }
    
    public String totalExistencia() {
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);
        List<Inventario> listInventario = inventarioDao.findInventarioEntities();

        float total = 0;
        for (Inventario inventario : listInventario) {
            float subtotal = inventario.getStock().getStCantidad();
            total += subtotal;
        }

        DecimalFormat df = new DecimalFormat("###,###.##");
        return df.format(total);
    }
    
    
}
