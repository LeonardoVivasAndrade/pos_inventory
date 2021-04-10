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
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findByCoId", query = "SELECT c FROM Compra c WHERE c.coId = :coId")
    , @NamedQuery(name = "Compra.findByCoFactcompra", query = "SELECT c FROM Compra c WHERE c.coFactcompra = :coFactcompra")
    , @NamedQuery(name = "Compra.findByCoControlcompra", query = "SELECT c FROM Compra c WHERE c.coControlcompra = :coControlcompra")
    , @NamedQuery(name = "Compra.findByCoFechacompra", query = "SELECT c FROM Compra c WHERE c.coFechacompra = :coFechacompra")
    , @NamedQuery(name = "Compra.findByCoFechacreacion", query = "SELECT c FROM Compra c WHERE c.coFechacreacion = :coFechacreacion")
    , @NamedQuery(name = "Compra.findByCoCantarticulos", query = "SELECT c FROM Compra c WHERE c.coCantarticulos = :coCantarticulos")
    , @NamedQuery(name = "Compra.findByCoCostototal", query = "SELECT c FROM Compra c WHERE c.coCostototal = :coCostototal")
    , @NamedQuery(name = "Compra.findByCoImpuestototal", query = "SELECT c FROM Compra c WHERE c.coImpuestototal = :coImpuestototal")
    , @NamedQuery(name = "Compra.findByCoPreciototal", query = "SELECT c FROM Compra c WHERE c.coPreciototal = :coPreciototal")
    , @NamedQuery(name = "Compra.findByCoDescuentototal", query = "SELECT c FROM Compra c WHERE c.coDescuentototal = :coDescuentototal")
    , @NamedQuery(name = "Compra.findByCoIscanceled", query = "SELECT c FROM Compra c WHERE c.coIscanceled = :coIscanceled")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "co_id")
    private Integer coId;
    @Basic(optional = false)
    @Column(name = "co_factcompra")
    private String coFactcompra;
    @Basic(optional = false)
    @Column(name = "co_controlcompra")
    private String coControlcompra;
    @Basic(optional = false)
    @Column(name = "co_fechacompra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date coFechacompra;
    @Basic(optional = false)
    @Column(name = "co_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date coFechacreacion;
    @Basic(optional = false)
    @Column(name = "co_cantarticulos")
    private int coCantarticulos;
    @Basic(optional = false)
    @Column(name = "co_costototal")
    private float coCostototal;
    @Basic(optional = false)
    @Column(name = "co_impuestototal")
    private float coImpuestototal;
    @Basic(optional = false)
    @Column(name = "co_preciototal")
    private float coPreciototal;
    @Basic(optional = false)
    @Column(name = "co_descuentototal")
    private float coDescuentototal;
    @Basic(optional = false)
    @Column(name = "co_iscanceled")
    private boolean coIscanceled;
    @JoinColumn(name = "co_idproveedor", referencedColumnName = "pr_id")
    @ManyToOne(optional = false)
    private Proveedor coIdproveedor;
    @JoinColumn(name = "co_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario coIduser;
    @OneToMany(mappedBy = "caIdcompra")
    private List<Cajachica> cajachicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcoIdcompra")
    private List<Dcompra> dcompraList;

    public Compra() {
    }

    public Compra(Integer coId) {
        this.coId = coId;
    }

    public Compra(Integer coId, String coFactcompra, String coControlcompra, Date coFechacompra, Date coFechacreacion, int coCantarticulos, float coCostototal, float coImpuestototal, float coPreciototal, float coDescuentototal, boolean coIscanceled) {
        this.coId = coId;
        this.coFactcompra = coFactcompra;
        this.coControlcompra = coControlcompra;
        this.coFechacompra = coFechacompra;
        this.coFechacreacion = coFechacreacion;
        this.coCantarticulos = coCantarticulos;
        this.coCostototal = coCostototal;
        this.coImpuestototal = coImpuestototal;
        this.coPreciototal = coPreciototal;
        this.coDescuentototal = coDescuentototal;
        this.coIscanceled = coIscanceled;
    }

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    public String getCoFactcompra() {
        return coFactcompra;
    }

    public void setCoFactcompra(String coFactcompra) {
        this.coFactcompra = coFactcompra;
    }

    public String getCoControlcompra() {
        return coControlcompra;
    }

    public void setCoControlcompra(String coControlcompra) {
        this.coControlcompra = coControlcompra;
    }

    public Date getCoFechacompra() {
        return coFechacompra;
    }

    public void setCoFechacompra(Date coFechacompra) {
        this.coFechacompra = coFechacompra;
    }

    public Date getCoFechacreacion() {
        return coFechacreacion;
    }

    public void setCoFechacreacion(Date coFechacreacion) {
        this.coFechacreacion = coFechacreacion;
    }

    public int getCoCantarticulos() {
        return coCantarticulos;
    }

    public void setCoCantarticulos(int coCantarticulos) {
        this.coCantarticulos = coCantarticulos;
    }

    public float getCoCostototal() {
        return coCostototal;
    }

    public void setCoCostototal(float coCostototal) {
        this.coCostototal = coCostototal;
    }

    public float getCoImpuestototal() {
        return coImpuestototal;
    }

    public void setCoImpuestototal(float coImpuestototal) {
        this.coImpuestototal = coImpuestototal;
    }

    public float getCoPreciototal() {
        return coPreciototal;
    }

    public void setCoPreciototal(float coPreciototal) {
        this.coPreciototal = coPreciototal;
    }

    public float getCoDescuentototal() {
        return coDescuentototal;
    }

    public void setCoDescuentototal(float coDescuentototal) {
        this.coDescuentototal = coDescuentototal;
    }

    public boolean getCoIscanceled() {
        return coIscanceled;
    }

    public void setCoIscanceled(boolean coIscanceled) {
        this.coIscanceled = coIscanceled;
    }

    public Proveedor getCoIdproveedor() {
        return coIdproveedor;
    }

    public void setCoIdproveedor(Proveedor coIdproveedor) {
        this.coIdproveedor = coIdproveedor;
    }

    public Usuario getCoIduser() {
        return coIduser;
    }

    public void setCoIduser(Usuario coIduser) {
        this.coIduser = coIduser;
    }

    @XmlTransient
    public List<Cajachica> getCajachicaList() {
        return cajachicaList;
    }

    public void setCajachicaList(List<Cajachica> cajachicaList) {
        this.cajachicaList = cajachicaList;
    }

    @XmlTransient
    public List<Dcompra> getDcompraList() {
        return dcompraList;
    }

    public void setDcompraList(List<Dcompra> dcompraList) {
        this.dcompraList = dcompraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coId != null ? coId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.coId == null && other.coId != null) || (this.coId != null && !this.coId.equals(other.coId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Compra[ coId=" + coId + " ]";
    }
    
}
