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
@Table(name = "dcompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dcompra.findAll", query = "SELECT d FROM Dcompra d")
    , @NamedQuery(name = "Dcompra.findByDcoId", query = "SELECT d FROM Dcompra d WHERE d.dcoId = :dcoId")
    , @NamedQuery(name = "Dcompra.findByDcoCantidad", query = "SELECT d FROM Dcompra d WHERE d.dcoCantidad = :dcoCantidad")
    , @NamedQuery(name = "Dcompra.findByDcoCosto", query = "SELECT d FROM Dcompra d WHERE d.dcoCosto = :dcoCosto")
    , @NamedQuery(name = "Dcompra.findByDcoImpuesto", query = "SELECT d FROM Dcompra d WHERE d.dcoImpuesto = :dcoImpuesto")
    , @NamedQuery(name = "Dcompra.findByDcoFechacompra", query = "SELECT d FROM Dcompra d WHERE d.dcoFechacompra = :dcoFechacompra")
    , @NamedQuery(name = "Dcompra.findByDcoFechacreacion", query = "SELECT d FROM Dcompra d WHERE d.dcoFechacreacion = :dcoFechacreacion")
    , @NamedQuery(name = "Dcompra.findByDcoIscanceled", query = "SELECT d FROM Dcompra d WHERE d.dcoIscanceled = :dcoIscanceled")})
public class Dcompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dco_id")
    private Integer dcoId;
    @Basic(optional = false)
    @Column(name = "dco_cantidad")
    private int dcoCantidad;
    @Basic(optional = false)
    @Column(name = "dco_costo")
    private float dcoCosto;
    @Basic(optional = false)
    @Column(name = "dco_impuesto")
    private float dcoImpuesto;
    @Basic(optional = false)
    @Column(name = "dco_fechacompra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcoFechacompra;
    @Basic(optional = false)
    @Column(name = "dco_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcoFechacreacion;
    @Basic(optional = false)
    @Column(name = "dco_iscanceled")
    private boolean dcoIscanceled;
    @JoinColumn(name = "dco_idcompra", referencedColumnName = "co_id")
    @ManyToOne(optional = false)
    private Compra dcoIdcompra;
    @JoinColumn(name = "dco_idinventario", referencedColumnName = "in_id")
    @ManyToOne(optional = false)
    private Inventario dcoIdinventario;
    @JoinColumn(name = "dco_idproveedor", referencedColumnName = "pr_id")
    @ManyToOne(optional = false)
    private Proveedor dcoIdproveedor;
    @JoinColumn(name = "dco_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario dcoIduser;

    public Dcompra() {
    }

    public Dcompra(Integer dcoId) {
        this.dcoId = dcoId;
    }

    public Dcompra(Integer dcoId, int dcoCantidad, float dcoCosto, float dcoImpuesto, Date dcoFechacompra, Date dcoFechacreacion, boolean dcoIscanceled) {
        this.dcoId = dcoId;
        this.dcoCantidad = dcoCantidad;
        this.dcoCosto = dcoCosto;
        this.dcoImpuesto = dcoImpuesto;
        this.dcoFechacompra = dcoFechacompra;
        this.dcoFechacreacion = dcoFechacreacion;
        this.dcoIscanceled = dcoIscanceled;
    }

    public Integer getDcoId() {
        return dcoId;
    }

    public void setDcoId(Integer dcoId) {
        this.dcoId = dcoId;
    }

    public int getDcoCantidad() {
        return dcoCantidad;
    }

    public void setDcoCantidad(int dcoCantidad) {
        this.dcoCantidad = dcoCantidad;
    }

    public float getDcoCosto() {
        return dcoCosto;
    }

    public void setDcoCosto(float dcoCosto) {
        this.dcoCosto = dcoCosto;
    }

    public float getDcoImpuesto() {
        return dcoImpuesto;
    }

    public void setDcoImpuesto(float dcoImpuesto) {
        this.dcoImpuesto = dcoImpuesto;
    }

    public Date getDcoFechacompra() {
        return dcoFechacompra;
    }

    public void setDcoFechacompra(Date dcoFechacompra) {
        this.dcoFechacompra = dcoFechacompra;
    }

    public Date getDcoFechacreacion() {
        return dcoFechacreacion;
    }

    public void setDcoFechacreacion(Date dcoFechacreacion) {
        this.dcoFechacreacion = dcoFechacreacion;
    }

    public boolean getDcoIscanceled() {
        return dcoIscanceled;
    }

    public void setDcoIscanceled(boolean dcoIscanceled) {
        this.dcoIscanceled = dcoIscanceled;
    }

    public Compra getDcoIdcompra() {
        return dcoIdcompra;
    }

    public void setDcoIdcompra(Compra dcoIdcompra) {
        this.dcoIdcompra = dcoIdcompra;
    }

    public Inventario getDcoIdinventario() {
        return dcoIdinventario;
    }

    public void setDcoIdinventario(Inventario dcoIdinventario) {
        this.dcoIdinventario = dcoIdinventario;
    }

    public Proveedor getDcoIdproveedor() {
        return dcoIdproveedor;
    }

    public void setDcoIdproveedor(Proveedor dcoIdproveedor) {
        this.dcoIdproveedor = dcoIdproveedor;
    }

    public Usuario getDcoIduser() {
        return dcoIduser;
    }

    public void setDcoIduser(Usuario dcoIduser) {
        this.dcoIduser = dcoIduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcoId != null ? dcoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dcompra)) {
            return false;
        }
        Dcompra other = (Dcompra) object;
        if ((this.dcoId == null && other.dcoId != null) || (this.dcoId != null && !this.dcoId.equals(other.dcoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Dcompra[ dcoId=" + dcoId + " ]";
    }
    
}
