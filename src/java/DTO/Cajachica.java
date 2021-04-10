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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "cajachica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cajachica.findAll", query = "SELECT c FROM Cajachica c")
    , @NamedQuery(name = "Cajachica.findByCaId", query = "SELECT c FROM Cajachica c WHERE c.caId = :caId")
    , @NamedQuery(name = "Cajachica.findByCaDescripcion", query = "SELECT c FROM Cajachica c WHERE c.caDescripcion = :caDescripcion")
    , @NamedQuery(name = "Cajachica.findByCaDetalle", query = "SELECT c FROM Cajachica c WHERE c.caDetalle = :caDetalle")
    , @NamedQuery(name = "Cajachica.findByCaMonto", query = "SELECT c FROM Cajachica c WHERE c.caMonto = :caMonto")
    , @NamedQuery(name = "Cajachica.findByCaFecha", query = "SELECT c FROM Cajachica c WHERE c.caFecha = :caFecha")})
public class Cajachica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ca_id")
    private Integer caId;
    @Basic(optional = false)
    @Column(name = "ca_descripcion")
    private String caDescripcion;
    @Basic(optional = false)
    @Column(name = "ca_detalle")
    private String caDetalle;
    @Basic(optional = false)
    @Column(name = "ca_monto")
    private float caMonto;
    @Basic(optional = false)
    @Column(name = "ca_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caFecha;
    @JoinColumn(name = "ca_idcompra", referencedColumnName = "co_id")
    @ManyToOne
    private Compra caIdcompra;
    @JoinColumn(name = "ca_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario caIduser;
    @JoinColumn(name = "ca_idventa", referencedColumnName = "ve_id")
    @ManyToOne
    private Venta caIdventa;
    @JoinColumn(name = "ca_tipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cajatipo caTipo;

    public Cajachica() {
    }

    public Cajachica(Integer caId) {
        this.caId = caId;
    }

    public Cajachica(Integer caId, String caDescripcion, String caDetalle, float caMonto, Date caFecha) {
        this.caId = caId;
        this.caDescripcion = caDescripcion;
        this.caDetalle = caDetalle;
        this.caMonto = caMonto;
        this.caFecha = caFecha;
    }

    public Integer getCaId() {
        return caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    public String getCaDescripcion() {
        return caDescripcion;
    }

    public void setCaDescripcion(String caDescripcion) {
        this.caDescripcion = caDescripcion;
    }

    public String getCaDetalle() {
        return caDetalle;
    }

    public void setCaDetalle(String caDetalle) {
        this.caDetalle = caDetalle;
    }

    public float getCaMonto() {
        return caMonto;
    }

    public void setCaMonto(float caMonto) {
        this.caMonto = caMonto;
    }

    public Date getCaFecha() {
        return caFecha;
    }

    public void setCaFecha(Date caFecha) {
        this.caFecha = caFecha;
    }

    public Compra getCaIdcompra() {
        return caIdcompra;
    }

    public void setCaIdcompra(Compra caIdcompra) {
        this.caIdcompra = caIdcompra;
    }

    public Usuario getCaIduser() {
        return caIduser;
    }

    public void setCaIduser(Usuario caIduser) {
        this.caIduser = caIduser;
    }

    public Venta getCaIdventa() {
        return caIdventa;
    }

    public void setCaIdventa(Venta caIdventa) {
        this.caIdventa = caIdventa;
    }

    public Cajatipo getCaTipo() {
        return caTipo;
    }

    public void setCaTipo(Cajatipo caTipo) {
        this.caTipo = caTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caId != null ? caId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajachica)) {
            return false;
        }
        Cajachica other = (Cajachica) object;
        if ((this.caId == null && other.caId != null) || (this.caId != null && !this.caId.equals(other.caId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Cajachica[ caId=" + caId + " ]";
    }
    
}
