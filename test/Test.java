
import Controller.CtrVentas;
import DAO.Conexion;
import DAO.DventaJpaController;
import DAO.InventarioJpaController;
import DAO.VentaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Cliente;
import DTO.Dventa;
import DTO.Inventario;
import DTO.Usuario;
import Utileria.Util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Leonardo
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException, NonexistentEntityException, Exception {

        EntityManagerFactory emf = new Conexion().getBd();
//        DventaJpaController dventaDao = new DventaJpaController(emf);
//        Object dventaList = dventaDao.getBestProducts2();
//        List<Object[]> a = (List<Object[]>) dventaList;
//        System.out.println(a.get(0)[0]);
//        System.out.println(dventaList.get(1).getDveCantidad());

//        CtrVentas ctrventas = new CtrVentas();
//        String ventashoy = ctrventas.totalVentasDia("2021-04-06");
//        Date d = new Date();
//        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MMM-dd");
//        System.out.println(formato.format(d));
//        DecimalFormat df = new DecimalFormat("###,###");
//        System.out.println(df.format(3.855));
//        LocalDate monday = Util.getFirstDayOfWeek();
//        LocalDate sunday = monday.minusDays(15);

        LocalDate ld = LocalDate.now();
LocalDate sunday = ld.minusDays(14);
//        LocalDate initial = LocalDate.now();
//        LocalDate start = initial.withDayOfMonth(1);
//        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());

        System.out.println(sunday);

//        EntityManagerFactory emf = new Conexion().getBd();
//        VentaJpaController ventaDao = new VentaJpaController(emf);
////        Double sumaVentaTotal = ventaDao.getSumVentaTotal();
////        System.out.println();
//        // TODO code application logic here
//        
//        Date d = new Date();
//        
//        System.out.println(Util.dateToString2(d));
    }

}
