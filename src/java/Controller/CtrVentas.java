package Controller;

import DAO.Conexion;
import DAO.DventaJpaController;
import DAO.InventarioJpaController;
import DAO.StockJpaController;
import DAO.VentaJpaController;
import DTO.Dventa;
import DTO.Inventario;
import DTO.Stock;
import DTO.Venta;
import Utileria.Util;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
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

public class CtrVentas extends HttpServlet {

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
            case "addVenta":

                try {
                    Map<String, String[]> parameters = request.getParameterMap();
                    String numFactura = parameters.get("numFactura")[0];

                    Venta venta = Util.jsonVentaToVentaDTO(parameters.get("listProductos")[0], numFactura);
                    String result = addVenta(venta, parameters.get("listProductos")[0]);

                    response.getWriter().write("{\"result\":\"" + result + "\"}");
                } catch (Exception ex) {
                    String result = ERROR;
                    response.getWriter().write("{\"result\":\"" + result + "\"}");
                }

                break;
            case "weeklySales": {
                try {
                    response.getWriter().write(weeklySales().toString());
                } catch (JSONException ex) {
                    Logger.getLogger(CtrVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "getListVentas": {
                try {
                    Map<String, String[]> parameters = request.getParameterMap();
                    response.getWriter().write(getVentas(parameters).toString());
                } catch (JSONException ex) {
                    Logger.getLogger(CtrVentas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case "deleteVenta":
//                String delProductoReturn = deleteProducto(Integer.valueOf(idProducto));
//                response.getWriter().write("{\"result\":\"" + delProductoReturn + "\"}");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String newCodigo = numFacturaNext();
        resp.getWriter().write(newCodigo);
    }

    public Double totalVentasDia(LocalDate date) {
        VentaJpaController ventaDao = new VentaJpaController(emf);
        Double suma = ventaDao.getSumPrecioVentaTotal(date.toString());

        return suma == null ? 0 : suma;
    }

    public Double totalCostoDia(LocalDate date) {
        VentaJpaController ventaDao = new VentaJpaController(emf);
        Double suma = ventaDao.getSumCostoVentaTotal(date.toString());

        return suma == null ? 0 : suma;
    }

    // formato de numero de factura inicia en (10001) 
    protected String numFacturaNext() {
        String prefijo = "1";
        VentaJpaController ventaDao = new VentaJpaController(emf);
        Venta ventaLast = ventaDao.getNumFacLast();

        if (ventaLast != null) {
            int numFacturaNext = Integer.valueOf(ventaLast.getVeFactventa()) + 1;
            return Util.llenarCeros(4, numFacturaNext);
        } else {
            return prefijo + "0001";
        }

    }

    protected String addVenta(Venta venta, String productos) throws JSONException {

        try {
            VentaJpaController ventaDao = new VentaJpaController(emf);
            DventaJpaController dventaDao = new DventaJpaController(emf);
            StockJpaController stockDao = new StockJpaController(emf);
            ventaDao.create(venta);

            List<Dventa> listDv = Util.jsonProductosToListDventa(productos, venta);

            for (Dventa dventa : listDv) {
                dventaDao.create(dventa);
                Stock stock = stockDao.findStock(dventa.getDveIdinventario().getInId());
                float cantidadActual = stock.getStCantidad();
                float cantidadVenta = dventa.getDveCantidad();
                stock.setStCantidad(cantidadActual - cantidadVenta);
                stockDao.edit(stock);
            }

            return SUCCESS;
        } catch (Exception e) {
            return ERROR;
        }
    }

    public Map<String, Object> getProductosMasVendidos() {

        Map<String, Object> list = new HashMap<>();
        DventaJpaController dventaDao = new DventaJpaController(emf);
        List<Dventa> dventaList = dventaDao.getBestProducts();

        if (dventaList != null) {

            for (int i = 0; i < dventaList.size(); i++) {
                int cantidad = 0;
                Dventa v = dventaList.get(i);
                int idInventario = v.getDveIdinventario().getInId();
                InventarioJpaController inventarioDao = new InventarioJpaController(emf);
                Inventario in = inventarioDao.findInventario(idInventario);

                for (Dventa dv : in.getDventaList()) {
                    cantidad += dv.getDveCantidad();
                }

                Map<String, String> dventa = new HashMap<>();
                dventa.put("descripcion", in.getInDescripcion());
                dventa.put("departamento", in.getInIddepartamento().getDpDescripcion());
                dventa.put("cantidad", String.valueOf(cantidad));
                list.put(i + "", dventa);
            }
        }
        return list;
    }

    private JSONArray weeklySales() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject ventas = new JSONObject();
        JSONObject utilidad = new JSONObject();

        try {
            LocalDate lunes = Util.getFirstDayOfWeek();
            for (int i = 0; i < 7; i++) {
                LocalDate day = lunes.plusDays(i);
                Double venta = totalVentasDia(day);
                Double costo = totalCostoDia(day);
                Double ganancia = venta - costo;
                ventas.put("v" + i, Util.doubleFormat(venta));
                utilidad.put("u" + i, Util.doubleFormat(ganancia));
            }
            jsonArray.put(ventas);
            jsonArray.put(utilidad);
        } catch (Exception e) {
        }

        return jsonArray;
    }

    protected JSONArray getVentas(Map<String, String[]> parameters) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject ventas = new JSONObject();

        try {
            String range = parameters.get("range")[0];
            switch (range) {
                case "default":
                    VentaJpaController ventasDao = new VentaJpaController(emf);
                    List<Venta> ventasList = ventasDao.findVentaEntities();

                    for (Venta venta : ventasList) {
                        JSONObject o = Util.ventaToJSON(venta);
                        jsonArray.put(o);
                    }
                    break;
            }

        } catch (Exception e) {
        }

        return jsonArray;

    }

}
