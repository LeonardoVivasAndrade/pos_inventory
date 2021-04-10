/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findByVeId", query = "SELECT v FROM Venta v WHERE v.veId = :veId")
    , @NamedQuery(name = "Venta.findByVeFactventa", query = "SELECT v FROM Venta v WHERE v.veFactventa = :veFactventa")
    , @NamedQuery(name = "Venta.findByVeControlventa", query = "SELECT v FROM Venta v WHERE v.veControlventa = :veControlventa")
    , @NamedQuery(name = "Venta.findByVeFechaventa", query = "SELECT v FROM Venta v WHERE v.veFechaventa = :veFechaventa")
    , @NamedQuery(name = "Venta.findByVeCantarticulos", query = "SELECT v FROM Venta v WHERE v.veCantarticulos = :veCantarticulos")
    , @NamedQuery(name = "Venta.findByVeCostototal", query = "SELECT v FROM Venta v WHERE v.veCostototal = :veCostototal")
    , @NamedQuery(name = "Venta.findByVeImpuestototal", query = "SELECT v FROM Venta v WHERE v.veImpuestototal = :veImpuestototal")
    , @NamedQuery(name = "Venta.findByVePreciototal", query = "SELECT v FROM Venta v WHERE v.vePreciototal = :vePreciototal")
    , @NamedQuery(name = "Venta.findByVeDescuentototal", query = "SELECT v FROM Venta v WHERE v.veDescuentototal = :veDescuentototal")
    , @NamedQuery(name = "Venta.findByVeIscanceled", query = "SELECT v FROM Venta v WHERE v.veIscanceled = :veIscanceled")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ve_id")
    private Integer veId;
    @Basic(optional = false)
    @Column(name = "ve_factventa")
    private String veFactventa;
    @Column(name = "ve_controlventa")
    private String veControlventa;
    @Basic(optional = false)
    @Column(name = "ve_fechaventa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date veFechaventa;
    @Basic(optional = false)
    @Column(name = "ve_cantarticulos")
    private int veCantarticulos;
    @Basic(optional = false)
    @Column(name = "ve_costototal")
    private float veCostototal;
    @Basic(optional = false)
    @Column(name = "ve_impuestototal")
    private float veImpuestototal;
    @Basic(optional = false)
    @Column(name = "ve_preciototal")
    private float vePreciototal;
    @Basic(optional = false)
    @Column(name = "ve_descuentototal")
    private float veDescuentototal;
    @Basic(optional = false)
    @Column(name = "ve_iscanceled")
    private boolean veIscanceled;
    @JoinColumn(name = "ve_idcliente", referencedColumnName = "cl_id")
    @ManyToOne(optional = false)
    private Cliente veIdcliente;
    @JoinColumn(name = "ve_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario veIduser;
    @OneToMany(mappedBy = "caIdventa")
    private List<Cajachica> cajachicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dveIdventa")
    private List<Dventa> dventaList;

    public Venta() {
    }

    public Venta(Integer veId) {
        this.veId = veId;
    }

    public Venta(Integer veId, String veFactventa, Date veFechaventa, int veCantarticulos, float veCostototal, float veImpuestototal, float vePreciototal, float veDescuentototal, boolean veIscanceled) {
        this.veId = veId;
        this.veFactventa = veFactventa;
        this.veFechaventa = veFechaventa;
        this.veCantarticulos = veCantarticulos;
        this.veCostototal = veCostototal;
        this.veImpuestototal = veImpuestototal;
        this.vePreciototal = vePreciototal;
        this.veDescuentototal = veDescuentototal;
        this.veIscanceled = veIscanceled;
    }

    public Integer getVeId() {
        return veId;
    }

    public void setVeId(Integer veId) {
        this.veId = veId;
    }

    public String getVeFactventa() {
        return veFactventa;
    }

    public void setVeFactventa(String veFactventa) {
        this.veFactventa = veFactventa;
    }

    public String getVeControlventa() {
        return veControlventa;
    }

    public void setVeControlventa(String veControlventa) {
        this.veControlventa = veControlventa;
    }

    public Date getVeFechaventa() {
        return veFechaventa;
    }

    public void setVeFechaventa(Date veFechaventa) {
        this.veFechaventa = veFechaventa;
    }

    public int getVeCantarticulos() {
        return veCantarticulos;
    }

    public void setVeCantarticulos(int veCantarticulos) {
        this.veCantarticulos = veCantarticulos;
    }

    public float getVeCostototal() {
        return veCostototal;
    }

    public void setVeCostototal(float veCostototal) {
        this.veCostototal = veCostototal;
    }

    public float getVeImpuestototal() {
        return veImpuestototal;
    }

    public void setVeImpuestototal(float veImpuestototal) {
        this.veImpuestototal = veImpuestototal;
    }

    public float getVePreciototal() {
        return vePreciototal;
    }

    public void setVePreciototal(float vePreciototal) {
        this.vePreciototal = vePreciototal;
    }

    public float getVeDescuentototal() {
        return veDescuentototal;
    }

    public void setVeDescuentototal(float veDescuentototal) {
        this.veDescuentototal = veDescuentototal;
    }

    public boolean getVeIscanceled() {
        return veIscanceled;
    }

    public void setVeIscanceled(boolean veIscanceled) {
        this.veIscanceled = veIscanceled;
    }

    public Cliente getVeIdcliente() {
        return veIdcliente;
    }

    public void setVeIdcliente(Cliente veIdcliente) {
        this.veIdcliente = veIdcliente;
    }

    public Usuario getVeIduser() {
        return veIduser;
    }

    public void setVeIduser(Usuario veIduser) {
        this.veIduser = veIduser;
    }

    @XmlTransient
    public List<Cajachica> getCajachicaList() {
        return cajachicaList;
    }

    public void setCajachicaList(List<Cajachica> cajachicaList) {
        this.cajachicaList = cajachicaList;
    }

    @XmlTransient
    public List<Dventa> getDventaList() {
        return dventaList;
    }

    public void setDventaList(List<Dventa> dventaList) {
        this.dventaList = dventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veId != null ? veId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.veId == null && other.veId != null) || (this.veId != null && !this.veId.equals(other.veId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Venta[ veId=" + veId + " ]";
    }
    
}
