
package DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ACER
 */
public class Conexion {
    private static Conexion conexion;
    private EntityManagerFactory bd; 
 
    public Conexion() {
        this.bd=Persistence.createEntityManagerFactory("Inventory_posPU"); 
    }
        
    public static Conexion getConexion()
    {
        if(conexion==null)
        {
            conexion=new Conexion();
        } System.out.println("La conexion se ha realizado");
    return conexion;
    }
 
    public EntityManagerFactory getBd() {
        return bd;
    }
}
