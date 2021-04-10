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
@Table(name = "dventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dventa.findAll", query = "SELECT d FROM Dventa d")
    , @NamedQuery(name = "Dventa.findByDveId", query = "SELECT d FROM Dventa d WHERE d.dveId = :dveId")
    , @NamedQuery(name = "Dventa.findByDveCantidad", query = "SELECT d FROM Dventa d WHERE d.dveCantidad = :dveCantidad")
    , @NamedQuery(name = "Dventa.findByDveCosto", query = "SELECT d FROM Dventa d WHERE d.dveCosto = :dveCosto")
    , @NamedQuery(name = "Dventa.findByDveImpuesto", query = "SELECT d FROM Dventa d WHERE d.dveImpuesto = :dveImpuesto")
    , @NamedQuery(name = "Dventa.findByDvePreciosiva", query = "SELECT d FROM Dventa d WHERE d.dvePreciosiva = :dvePreciosiva")
    , @NamedQuery(name = "Dventa.findByDvePreciociva", query = "SELECT d FROM Dventa d WHERE d.dvePreciociva = :dvePreciociva")
    , @NamedQuery(name = "Dventa.findByDveFechaventa", query = "SELECT d FROM Dventa d WHERE d.dveFechaventa = :dveFechaventa")
    , @NamedQuery(name = "Dventa.findByDveFechacreacion", query = "SELECT d FROM Dventa d WHERE d.dveFechacreacion = :dveFechacreacion")
    , @NamedQuery(name = "Dventa.findByDveIscanceled", query = "SELECT d FROM Dventa d WHERE d.dveIscanceled = :dveIscanceled")})
public class Dventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dve_id")
    private Integer dveId;
    @Basic(optional = false)
    @Column(name = "dve_cantidad")
    private int dveCantidad;
    @Basic(optional = false)
    @Column(name = "dve_costo")
    private float dveCosto;
    @Basic(optional = false)
    @Column(name = "dve_impuesto")
    private float dveImpuesto;
    @Basic(optional = false)
    @Column(name = "dve_preciosiva")
    private float dvePreciosiva;
    @Basic(optional = false)
    @Column(name = "dve_preciociva")
    private float dvePreciociva;
    @Basic(optional = false)
    @Column(name = "dve_fechaventa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dveFechaventa;
    @Basic(optional = false)
    @Column(name = "dve_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dveFechacreacion;
    @Basic(optional = false)
    @Column(name = "dve_iscanceled")
    private boolean dveIscanceled;
    @JoinColumn(name = "dve_idcliente", referencedColumnName = "cl_id")
    @ManyToOne(optional = false)
    private Cliente dveIdcliente;
    @JoinColumn(name = "dve_iddepartamento", referencedColumnName = "dp_id")
    @ManyToOne(optional = false)
    private Departamento dveIddepartamento;
    @JoinColumn(name = "dve_idinventario", referencedColumnName = "in_id")
    @ManyToOne(optional = false)
    private Inventario dveIdinventario;
    @JoinColumn(name = "dve_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario dveIduser;
    @JoinColumn(name = "dve_idventa", referencedColumnName = "ve_id")
    @ManyToOne(optional = false)
    private Venta dveIdventa;

    public Dventa() {
    }

    public Dventa(Integer dveId) {
        this.dveId = dveId;
    }

    public Dventa(Integer dveId, int dveCantidad, float dveCosto, float dveImpuesto, float dvePreciosiva, float dvePreciociva, Date dveFechaventa, Date dveFechacreacion, boolean dveIscanceled) {
        this.dveId = dveId;
        this.dveCantidad = dveCantidad;
        this.dveCosto = dveCosto;
        this.dveImpuesto = dveImpuesto;
        this.dvePreciosiva = dvePreciosiva;
        this.dvePreciociva = dvePreciociva;
        this.dveFechaventa = dveFechaventa;
        this.dveFechacreacion = dveFechacreacion;
        this.dveIscanceled = dveIscanceled;
    }

    public Integer getDveId() {
        return dveId;
    }

    public void setDveId(Integer dveId) {
        this.dveId = dveId;
    }

    public int getDveCantidad() {
        return dveCantidad;
    }

    public void setDveCantidad(int dveCantidad) {
        this.dveCantidad = dveCantidad;
    }

    public float getDveCosto() {
        return dveCosto;
    }

    public void setDveCosto(float dveCosto) {
        this.dveCosto = dveCosto;
    }

    public float getDveImpuesto() {
        return dveImpuesto;
    }

    public void setDveImpuesto(float dveImpuesto) {
        this.dveImpuesto = dveImpuesto;
    }

    public float getDvePreciosiva() {
        return dvePreciosiva;
    }

    public void setDvePreciosiva(float dvePreciosiva) {
        this.dvePreciosiva = dvePreciosiva;
    }

    public float getDvePreciociva() {
        return dvePreciociva;
    }

    public void setDvePreciociva(float dvePreciociva) {
        this.dvePreciociva = dvePreciociva;
    }

    public Date getDveFechaventa() {
        return dveFechaventa;
    }

    public void setDveFechaventa(Date dveFechaventa) {
        this.dveFechaventa = dveFechaventa;
    }

    public Date getDveFechacreacion() {
        return dveFechacreacion;
    }

    public void setDveFechacreacion(Date dveFechacreacion) {
        this.dveFechacreacion = dveFechacreacion;
    }

    public boolean getDveIscanceled() {
        return dveIscanceled;
    }

    public void setDveIscanceled(boolean dveIscanceled) {
        this.dveIscanceled = dveIscanceled;
    }

    public Cliente getDveIdcliente() {
        return dveIdcliente;
    }

    public void setDveIdcliente(Cliente dveIdcliente) {
        this.dveIdcliente = dveIdcliente;
    }

    public Departamento getDveIddepartamento() {
        return dveIddepartamento;
    }

    public void setDveIddepartamento(Departamento dveIddepartamento) {
        this.dveIddepartamento = dveIddepartamento;
    }

    public Inventario getDveIdinventario() {
        return dveIdinventario;
    }

    public void setDveIdinventario(Inventario dveIdinventario) {
        this.dveIdinventario = dveIdinventario;
    }

    public Usuario getDveIduser() {
        return dveIduser;
    }

    public void setDveIduser(Usuario dveIduser) {
        this.dveIduser = dveIduser;
    }

    public Venta getDveIdventa() {
        return dveIdventa;
    }

    public void setDveIdventa(Venta dveIdventa) {
        this.dveIdventa = dveIdventa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dveId != null ? dveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dventa)) {
            return false;
        }
        Dventa other = (Dventa) object;
        if ((this.dveId == null && other.dveId != null) || (this.dveId != null && !this.dveId.equals(other.dveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Dventa[ dveId=" + dveId + " ]";
    }
    
}
