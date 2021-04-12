
package Controller;

import DAO.CompraJpaController;
import DAO.Conexion;
import DAO.DcompraJpaController;
import DAO.StockJpaController;
import DTO.Compra;
import DTO.Dcompra;
import DTO.Inventario;
import DTO.Stock;
import Utileria.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CtrCompras extends HttpServlet {
    
    protected EntityManagerFactory emf = new Conexion().getBd();
    protected final String SUCCESS = "success";
    protected final String ERROR_INTEGRITY = "error";
    protected final String ERROR = "warning";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String option = request.getParameter("option");

        switch (option) {
            case "addCompra":

                try {
                    Map<String, String[]> parameters = request.getParameterMap();
                    String numFactura = parameters.get("numCompra")[0];

                    Compra compra = Util.jsonCompraToCompraDTO(parameters.get("listProductos")[0], numFactura);
                    String result = addCompra(compra, parameters.get("listProductos")[0]);

                    response.getWriter().write("{\"result\":\"" + result + "\"}");
                } catch (Exception ex) {
                    String result = ERROR;
                    response.getWriter().write("{\"result\":\"" + result + "\"}");
                }

                break;
            
            case "getListCompras": 
                try {
                    Map<String, String[]> parameters = request.getParameterMap();
                    response.getWriter().write(getCompras(parameters).toString());
                } catch (JSONException ex) {
                    Logger.getLogger(CtrVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
            case "getDetalleCompra":
                try {
                    String idC = request.getParameter("idCompra");
                    response.getWriter().write(getDetalleCompra(idC).toString());
                } catch (JSONException ex) {
                    Logger.getLogger(CtrVentas.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case "deleteCompra":
                String idCompra = request.getParameter("idCompra");
                String delCompraReturn = deleteCompra(Integer.valueOf(idCompra));
                response.getWriter().write("{\"result\":\"" + delCompraReturn + "\"}");
                break;
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String newCodigo = numFacturaNext();
        resp.getWriter().write(newCodigo);
        
    }
    
    protected String addCompra(Compra compra, String productos) throws JSONException {

        try {
            CompraJpaController compraDao = new CompraJpaController(emf);
            DcompraJpaController dcompraDao = new DcompraJpaController(emf);
            StockJpaController stockDao = new StockJpaController(emf);
            compraDao.create(compra);

            List<Dcompra> listDc = Util.jsonProductosToListDcompra(productos, compra);

            for (Dcompra dcompra : listDc) {
                dcompraDao.create(dcompra);
                Stock stock = stockDao.findStock(dcompra.getDcoIdinventario().getInId());
                float cantidadActual = 0;
                if( stock != null){
                    cantidadActual = stock.getStCantidad();
                    float cantidadCompra = dcompra.getDcoCantidad();
                    stock.setStCantidad(cantidadActual + cantidadCompra);
                }              
                
                stockDao.edit(stock);
            }

            return SUCCESS;
        } catch (Exception e) {
            return ERROR;
        }
    }
    
    // formato de numero de factura inicia en (20001) 
    protected String numFacturaNext() {
        String prefijo = "2";
        CompraJpaController compraDao = new CompraJpaController(emf);
        Compra compraLast = compraDao.getNumFacLast();

        if (compraLast != null) {
            int numFacturaNext = Integer.valueOf(compraLast.getCoFactcompra()) + 1;
            return Util.llenarCeros(4, numFacturaNext);
        } else {
            return prefijo + "0001";
        }

    }
    
    protected JSONArray getCompras(Map<String, String[]> parameters) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        try {
            String range = parameters.get("range")[0];
            switch (range) {
                case "default":
                    CompraJpaController ventasDao = new CompraJpaController(emf);
                    List<Compra> comprasList = ventasDao.findCompraEntities();

                    for (Compra compra : comprasList) {
                        JSONObject o = Util.compraToJSON(compra);
                        jsonArray.put(o);
                    }
                    break;
            }

        } catch (Exception e) {
        }

        return jsonArray;
    }
    
    protected JSONArray getDetalleCompra(String idCompra) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        CompraJpaController compraDao = new CompraJpaController(emf);
        Compra compra = compraDao.findCompra(Integer.valueOf(idCompra));

        for (Dcompra dcompra : compra.getDcompraList()) {
            JSONObject dventaJson = Util.dcompraToJSON(dcompra);
            jsonArray.put(dventaJson);
        }

        return jsonArray;
    }
    
    private String deleteCompra(int idCompra) {
        try {
            CompraJpaController compraDao = new CompraJpaController(emf);
            StockJpaController stockDao = new StockJpaController(emf);

            Compra compra = compraDao.findCompra(idCompra);

            List<Dcompra> listDcompra = compra.getDcompraList();

            for (Dcompra dcompra : listDcompra) {
                Inventario inventario = dcompra.getDcoIdinventario();
                float cantidadCompra = dcompra.getDcoCantidad();

                Stock stock = inventario.getStock();
                float cantidadStock = stock.getStCantidad();

                //Edit Stock
                stock.setStCantidad(cantidadStock - cantidadCompra);
                stockDao.edit(stock);

                //Edit inventario
                inventario.setStock(stock);
            }

            compraDao.destroy(idCompra);
            return SUCCESS;

        } catch (Exception ex) {
            return ERROR;
        }

    }

    

}
