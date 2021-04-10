/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s")
    , @NamedQuery(name = "Stock.findByStIdinventario", query = "SELECT s FROM Stock s WHERE s.stIdinventario = :stIdinventario")
    , @NamedQuery(name = "Stock.findByStCantidad", query = "SELECT s FROM Stock s WHERE s.stCantidad = :stCantidad")
    , @NamedQuery(name = "Stock.findByStCostototal", query = "SELECT s FROM Stock s WHERE s.stCostototal = :stCostototal")
    , @NamedQuery(name = "Stock.findByStImpuestototal", query = "SELECT s FROM Stock s WHERE s.stImpuestototal = :stImpuestototal")
    , @NamedQuery(name = "Stock.findByStUtilidadtotal", query = "SELECT s FROM Stock s WHERE s.stUtilidadtotal = :stUtilidadtotal")
    , @NamedQuery(name = "Stock.findByStPreciototal", query = "SELECT s FROM Stock s WHERE s.stPreciototal = :stPreciototal")
    , @NamedQuery(name = "Stock.findByStFecha", query = "SELECT s FROM Stock s WHERE s.stFecha = :stFecha")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "st_idinventario")
    private Integer stIdinventario;
    @Basic(optional = false)
    @Column(name = "st_cantidad")
    private float stCantidad;
    @Basic(optional = false)
    @Column(name = "st_costototal")
    private float stCostototal;
    @Basic(optional = false)
    @Column(name = "st_impuestototal")
    private float stImpuestototal;
    @Basic(optional = false)
    @Column(name = "st_utilidadtotal")
    private float stUtilidadtotal;
    @Basic(optional = false)
    @Column(name = "st_preciototal")
    private float stPreciototal;
    @Basic(optional = false)
    @Column(name = "st_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stFecha;
    @JoinColumn(name = "st_iddepartamento", referencedColumnName = "dp_id")
    @ManyToOne(optional = false)
    private Departamento stIddepartamento;
    @JoinColumn(name = "st_idinventario", referencedColumnName = "in_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Inventario inventario;
    @JoinColumn(name = "st_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario stIduser;

    public Stock() {
    }

    public Stock(Integer stIdinventario) {
        this.stIdinventario = stIdinventario;
    }

    public Stock(Integer stIdinventario, float stCantidad, float stCostototal, float stImpuestototal, float stUtilidadtotal, float stPreciototal, Date stFecha) {
        this.stIdinventario = stIdinventario;
        this.stCantidad = stCantidad;
        this.stCostototal = stCostototal;
        this.stImpuestototal = stImpuestototal;
        this.stUtilidadtotal = stUtilidadtotal;
        this.stPreciototal = stPreciototal;
        this.stFecha = stFecha;
    }

    public Integer getStIdinventario() {
        return stIdinventario;
    }

    public void setStIdinventario(Integer stIdinventario) {
        this.stIdinventario = stIdinventario;
    }

    public float getStCantidad() {
        return stCantidad;
    }

    public void setStCantidad(float stCantidad) {
        this.stCantidad = stCantidad;
    }

    public float getStCostototal() {
        return stCostototal;
    }

    public void setStCostototal(float stCostototal) {
        this.stCostototal = stCostototal;
    }

    public float getStImpuestototal() {
        return stImpuestototal;
    }

    public void setStImpuestototal(float stImpuestototal) {
        this.stImpuestototal = stImpuestototal;
    }

    public float getStUtilidadtotal() {
        return stUtilidadtotal;
    }

    public void setStUtilidadtotal(float stUtilidadtotal) {
        this.stUtilidadtotal = stUtilidadtotal;
    }

    public float getStPreciototal() {
        return stPreciototal;
    }

    public void setStPreciototal(float stPreciototal) {
        this.stPreciototal = stPreciototal;
    }

    public Date getStFecha() {
        return stFecha;
    }

    public void setStFecha(Date stFecha) {
        this.stFecha = stFecha;
    }

    public Departamento getStIddepartamento() {
        return stIddepartamento;
    }

    public void setStIddepartamento(Departamento stIddepartamento) {
        this.stIddepartamento = stIddepartamento;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Usuario getStIduser() {
        return stIduser;
    }

    public void setStIduser(Usuario stIduser) {
        this.stIduser = stIduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stIdinventario != null ? stIdinventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        
        Stock other = (Stock) object;
        if ((this.stIdinventario == null && other.stIdinventario != null) || (this.stIdinventario != null && !this.stIdinventario.equals(other.stIdinventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Stock[ stIdinventario=" + stIdinventario + " ]";
    }
    
}
