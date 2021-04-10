
package Controller;

import DAO.CompraJpaController;
import DAO.Conexion;
import DAO.DcompraJpaController;
import DAO.StockJpaController;
import DTO.Compra;
import DTO.Dcompra;
import DTO.Stock;
import Utileria.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;

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
            
            case "getListCompras": {
//                try {
//                    Map<String, String[]> parameters = request.getParameterMap();
////                    response.getWriter().write(getVentas(parameters).toString());
//                } catch (JSONException ex) {
//                    Logger.getLogger(CtrVentas.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

            case "deleteVenta":
//                String delProductoReturn = deleteProducto(Integer.valueOf(idProducto));
//                response.getWriter().write("{\"result\":\"" + delProductoReturn + "\"}");
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

    

}
