package Utileria;

import DAO.Conexion;
import DAO.InventarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.*;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class Util {

    public static JSONArray departamentosToJSON(List<Departamento> listDepartamentos) throws JSONException {
        JSONArray documentoArray = new JSONArray();
        for (Departamento elemento : listDepartamentos) {
            JSONObject documento = departamentoToJSON(elemento);
            documentoArray.put(documento);
        }
        return documentoArray;
    }

    public static JSONObject departamentoToJSON(Departamento departamento) throws JSONException {
        JSONObject documento = new JSONObject();
        documento.put("idCategoria", departamento.getDpId());
        documento.put("descripcion", departamento.getDpDescripcion());
        documento.put("fechaCreacion", dateToString(departamento.getDpFechacreacion()));
        return documento;
    }

    public static JSONObject productoToJSON(Inventario inventario) throws JSONException {
        JSONObject documento = new JSONObject();
        documento.put("idProducto", inventario.getInId());
        documento.put("codigo", inventario.getInCodigo());
        documento.put("descripcion", inventario.getInDescripcion());
        documento.put("categoria", inventario.getInIddepartamento().getDpDescripcion());
        if (inventario.getStock() != null) 
            documento.put("existencia", inventario.getStock().getStCantidad());        
        else
            documento.put("existencia", 0);        
        documento.put("imagen", inventario.getInImage());
        documento.put("costo", inventario.getInCosto());
        documento.put("precio", inventario.getInPrecioCiva());
        documento.put("utilidad", inventario.getInPrecioCiva() - inventario.getInCosto());
        documento.put("fechaCreacion", dateToString(inventario.getInFechacreacion()));
        return documento;
    }

    public static JSONArray productosToJSON(List<Inventario> listInventario) throws JSONException {
        JSONArray documentoArray = new JSONArray();
        for (Inventario elemento : listInventario) {
            JSONObject documento = productoToJSON(elemento);
            documentoArray.put(documento);
        }
        return documentoArray;
    }

    public static Venta jsonVentaToVentaDTO(String productos, String numFacturaNext) throws JSONException {

        JSONArray json = new JSONArray(productos);

        int cantidadTotal = 0;
        int costoTotal = 0;
        int precioTotal = 0;

        Venta v = new Venta();
        for (int i = 0; i < json.length(); i++) {
            JSONObject o = json.getJSONObject(i);

            Object p = o.get("precio");
            Object c = o.get("cantidad");
            Object co = o.get("costo");

            int cantidad = Integer.valueOf(c.toString());
            float costo = Float.valueOf(co.toString()) * cantidad;
            float precio = Float.valueOf(p.toString()) * cantidad;

            cantidadTotal += cantidad;
            costoTotal += costo;
            precioTotal += precio;

        }

        v.setVeIdcliente(new Cliente(1));
        v.setVeIduser(new Usuario(1));
        v.setVeFactventa(numFacturaNext);
        v.setVeCantarticulos(cantidadTotal);
        v.setVeCostototal(costoTotal);
        v.setVePreciototal(precioTotal);
        v.setVeFechaventa(new Date());
        v.setVeImpuestototal(0);
        v.setVeControlventa(numFacturaNext);
        v.setVeDescuentototal(0);
        v.setVeIscanceled(false);

        return v;
    }
    
    public static List<Dventa> jsonProductosToListDventa(String productos, Venta venta) throws JSONException, NonexistentEntityException, Exception {
        JSONArray json = new JSONArray(productos);
        

        EntityManagerFactory emf = new Conexion().getBd();
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);

        List<Dventa> listDv = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            
            JSONObject o = json.getJSONObject(i);

            Object id = o.get("idProducto");
            Object p = o.get("precio");
            Object c = o.get("cantidad");
            Object co = o.get("costo");

            int idProducto = Integer.valueOf(id.toString());
            int cantidad = Integer.valueOf(c.toString());
            float costo = Float.valueOf(co.toString());
            float precio = Float.valueOf(p.toString());

            Inventario inv = inventarioDao.findInventario(idProducto);
            inv.setInPrecioCiva(precio);

            Dventa dv = new Dventa();
            dv.setDveIdventa(venta);
            dv.setDveIdinventario(inv);
            dv.setDveIddepartamento(inv.getInIddepartamento());
            dv.setDveCantidad(cantidad);
            dv.setDveCosto(costo * cantidad);
            dv.setDveImpuesto(0);
            dv.setDvePreciociva(precio * cantidad);
            dv.setDvePreciosiva(precio * cantidad);
            dv.setDveIdcliente(new Cliente(1));
            dv.setDveFechaventa(venta.getVeFechaventa());
            dv.setDveFechacreacion(venta.getVeFechaventa());
            dv.setDveIduser(new Usuario(1));
            dv.setDveIscanceled(false);
            listDv.add(dv);

            inv.getDventaList().add(dv);            
        }

        return listDv;
    }

    public static JSONObject dventaToJSON(Dventa dventa) throws JSONException {
        JSONObject documento = new JSONObject();
        int cantidad = (int) dventa.getDveCantidad();
        Inventario i = dventa.getDveIdinventario();

//        for (Dventa dv : i.getDventaList()) {
//            cantidad += dv.getDveCantidad();
//        }

        documento.put("descripcion", i.getInDescripcion());
        documento.put("cantidad", cantidad);
        documento.put("departamento", i.getInIddepartamento().getDpDescripcion());
        documento.put("precio", dventa.getDvePreciociva());
        return documento;
    }  
    
    public static JSONObject ventaToJSON(Venta venta) throws JSONException {
        JSONObject documento = new JSONObject();        

        documento.put("id", venta.getVeId());
        documento.put("numero", venta.getVeFactventa());
        documento.put("fecha", dateToString(venta.getVeFechaventa()));
        documento.put("cantidad", venta.getVeCantarticulos());
        
        float costo = venta.getVeCostototal();
        float total = venta.getVePreciototal();
        float utilidad = total-costo;
        
        documento.put("utilidad", floatFormat(utilidad));
        documento.put("total", floatFormat(total));
        return documento;
    }
    
    
    public static Compra jsonCompraToCompraDTO(String productos, String numCompraNext) throws JSONException {

        JSONArray json = new JSONArray(productos);

        int cantidadTotal = 0;
        int costoTotal = 0;
        int precioTotal = 0;

        Compra c = new Compra();
        for (int i = 0; i < json.length(); i++) {
            JSONObject o = json.getJSONObject(i);

            Object p = o.get("precio");
            Object cn = o.get("cantidad");
            Object co = o.get("costo");

            int cantidad = Integer.valueOf(cn.toString());
            float costo = Float.valueOf(co.toString()) * cantidad;;
            float precio = Float.valueOf(p.toString()) * cantidad;

            cantidadTotal += cantidad;
            costoTotal += costo;
            precioTotal += precio;

        }

        c.setCoIdproveedor(new Proveedor(1));
        c.setCoIduser(new Usuario(1));
        c.setCoFactcompra(numCompraNext);
        c.setCoCantarticulos(cantidadTotal);
        c.setCoCostototal(costoTotal);
        c.setCoPreciototal(precioTotal);
        c.setCoFechacreacion(new Date());
        c.setCoFechacompra(new Date());
        c.setCoImpuestototal(0);
        c.setCoControlcompra(numCompraNext);
        c.setCoDescuentototal(0);
        c.setCoIscanceled(false);

        return c;
    }
    
    public static List<Dcompra> jsonProductosToListDcompra(String productos, Compra compra) throws JSONException {
        JSONArray json = new JSONArray(productos);

        EntityManagerFactory emf = new Conexion().getBd();
        InventarioJpaController inventarioDao = new InventarioJpaController(emf);

        List<Dcompra> listDc = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject o = json.getJSONObject(i);

            Object id = o.get("idProducto");
            Object c = o.get("cantidad");
            Object co = o.get("costo");

            int idProducto = Integer.valueOf(id.toString());
            int cantidad = Integer.valueOf(c.toString());
            float costo = Float.valueOf(co.toString());

            Inventario inv = inventarioDao.findInventario(idProducto);

            Dcompra dc = new Dcompra();
            dc.setDcoIdcompra(compra);
            dc.setDcoIdproveedor(new Proveedor(1));
            dc.setDcoIdinventario(inv);
            dc.setDcoCantidad(cantidad);
            dc.setDcoCosto(costo);
            dc.setDcoImpuesto(0);
            dc.setDcoFechacompra(compra.getCoFechacompra());
            dc.setDcoFechacreacion(compra.getCoFechacompra());
            dc.setDcoIduser(new Usuario(1));
            dc.setDcoIscanceled(false);
            listDc.add(dc);

            inv.setDcompraList(listDc);
        }

        return listDc;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MMM-dd");
        return formato.format(date);
    }

    public static String dateToString2(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(date);
    }

    public static LocalDate getFirstDayOfWeek() {
        LocalDate ld = LocalDate.now();        
        return ld.with(DayOfWeek.MONDAY);
    }

    /**
     * Método llenarCeros. Método que toma un numero dado y le agrega tantos ceros a la izquierda segun el parametro tamaño , a su vez le agrega un prefijo dado.
     *
     * @param tamano Cantidad de caracteres para el número retornado.
     * @param numero Número en formato entero para aplicarle ceros a la izquierda.
     * @return Retorna el numero con n ceros a la izquierda, en valor String.
     */
    public static String llenarCeros(int tamano, int numero) {
        String numeroText = String.valueOf(numero);
        int lenNumero = numeroText.length();

        for (int i = lenNumero; i < tamano; i++) {
            numeroText = "0" + numeroText;
        }
        return numeroText;
    }
    
    public static String floatFormat(Float number){        
        DecimalFormat df = new DecimalFormat("###,###");
        return number != null ? df.format(number) : "0";
    }
    
    public static String doubleFormat(Double number){        
        DecimalFormat df = new DecimalFormat("###,###,###");
        return number != null ? df.format(number) : "0";
    }

}
