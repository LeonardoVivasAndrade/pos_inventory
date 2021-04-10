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
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByPrId", query = "SELECT p FROM Proveedor p WHERE p.prId = :prId")
    , @NamedQuery(name = "Proveedor.findByPrCodigo", query = "SELECT p FROM Proveedor p WHERE p.prCodigo = :prCodigo")
    , @NamedQuery(name = "Proveedor.findByPrNit", query = "SELECT p FROM Proveedor p WHERE p.prNit = :prNit")
    , @NamedQuery(name = "Proveedor.findByPrDocumento2", query = "SELECT p FROM Proveedor p WHERE p.prDocumento2 = :prDocumento2")
    , @NamedQuery(name = "Proveedor.findByPrDescripcion", query = "SELECT p FROM Proveedor p WHERE p.prDescripcion = :prDescripcion")
    , @NamedQuery(name = "Proveedor.findByPrDireccion", query = "SELECT p FROM Proveedor p WHERE p.prDireccion = :prDireccion")
    , @NamedQuery(name = "Proveedor.findByPrTelefono", query = "SELECT p FROM Proveedor p WHERE p.prTelefono = :prTelefono")
    , @NamedQuery(name = "Proveedor.findByPrTelefono2", query = "SELECT p FROM Proveedor p WHERE p.prTelefono2 = :prTelefono2")
    , @NamedQuery(name = "Proveedor.findByPrCelular", query = "SELECT p FROM Proveedor p WHERE p.prCelular = :prCelular")
    , @NamedQuery(name = "Proveedor.findByPrCelular2", query = "SELECT p FROM Proveedor p WHERE p.prCelular2 = :prCelular2")
    , @NamedQuery(name = "Proveedor.findByPrWhatsapp", query = "SELECT p FROM Proveedor p WHERE p.prWhatsapp = :prWhatsapp")
    , @NamedQuery(name = "Proveedor.findByPrEmail", query = "SELECT p FROM Proveedor p WHERE p.prEmail = :prEmail")
    , @NamedQuery(name = "Proveedor.findByPrFechacreacion", query = "SELECT p FROM Proveedor p WHERE p.prFechacreacion = :prFechacreacion")
    , @NamedQuery(name = "Proveedor.findByPrFechamodificacion", query = "SELECT p FROM Proveedor p WHERE p.prFechamodificacion = :prFechamodificacion")
    , @NamedQuery(name = "Proveedor.findByPrContacto", query = "SELECT p FROM Proveedor p WHERE p.prContacto = :prContacto")
    , @NamedQuery(name = "Proveedor.findByPrStatus", query = "SELECT p FROM Proveedor p WHERE p.prStatus = :prStatus")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pr_id")
    private Integer prId;
    @Basic(optional = false)
    @Column(name = "pr_codigo")
    private String prCodigo;
    @Basic(optional = false)
    @Column(name = "pr_nit")
    private int prNit;
    @Basic(optional = false)
    @Column(name = "pr_documento2")
    private int prDocumento2;
    @Basic(optional = false)
    @Column(name = "pr_descripcion")
    private String prDescripcion;
    @Column(name = "pr_direccion")
    private String prDireccion;
    @Column(name = "pr_telefono")
    private String prTelefono;
    @Column(name = "pr_telefono2")
    private String prTelefono2;
    @Column(name = "pr_celular")
    private String prCelular;
    @Column(name = "pr_celular2")
    private String prCelular2;
    @Column(name = "pr_whatsapp")
    private String prWhatsapp;
    @Column(name = "pr_email")
    private String prEmail;
    @Basic(optional = false)
    @Column(name = "pr_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prFechacreacion;
    @Basic(optional = false)
    @Column(name = "pr_fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prFechamodificacion;
    @Basic(optional = false)
    @Column(name = "pr_contacto")
    private String prContacto;
    @Basic(optional = false)
    @Column(name = "pr_status")
    private boolean prStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coIdproveedor")
    private List<Compra> compraList;
    @JoinColumn(name = "pr_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario prIduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcoIdproveedor")
    private List<Dcompra> dcompraList;

    public Proveedor() {
    }

    public Proveedor(Integer prId) {
        this.prId = prId;
    }

    public Proveedor(Integer prId, String prCodigo, int prNit, int prDocumento2, String prDescripcion, Date prFechacreacion, Date prFechamodificacion, String prContacto, boolean prStatus) {
        this.prId = prId;
        this.prCodigo = prCodigo;
        this.prNit = prNit;
        this.prDocumento2 = prDocumento2;
        this.prDescripcion = prDescripcion;
        this.prFechacreacion = prFechacreacion;
        this.prFechamodificacion = prFechamodificacion;
        this.prContacto = prContacto;
        this.prStatus = prStatus;
    }

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public String getPrCodigo() {
        return prCodigo;
    }

    public void setPrCodigo(String prCodigo) {
        this.prCodigo = prCodigo;
    }

    public int getPrNit() {
        return prNit;
    }

    public void setPrNit(int prNit) {
        this.prNit = prNit;
    }

    public int getPrDocumento2() {
        return prDocumento2;
    }

    public void setPrDocumento2(int prDocumento2) {
        this.prDocumento2 = prDocumento2;
    }

    public String getPrDescripcion() {
        return prDescripcion;
    }

    public void setPrDescripcion(String prDescripcion) {
        this.prDescripcion = prDescripcion;
    }

    public String getPrDireccion() {
        return prDireccion;
    }

    public void setPrDireccion(String prDireccion) {
        this.prDireccion = prDireccion;
    }

    public String getPrTelefono() {
        return prTelefono;
    }

    public void setPrTelefono(String prTelefono) {
        this.prTelefono = prTelefono;
    }

    public String getPrTelefono2() {
        return prTelefono2;
    }

    public void setPrTelefono2(String prTelefono2) {
        this.prTelefono2 = prTelefono2;
    }

    public String getPrCelular() {
        return prCelular;
    }

    public void setPrCelular(String prCelular) {
        this.prCelular = prCelular;
    }

    public String getPrCelular2() {
        return prCelular2;
    }

    public void setPrCelular2(String prCelular2) {
        this.prCelular2 = prCelular2;
    }

    public String getPrWhatsapp() {
        return prWhatsapp;
    }

    public void setPrWhatsapp(String prWhatsapp) {
        this.prWhatsapp = prWhatsapp;
    }

    public String getPrEmail() {
        return prEmail;
    }

    public void setPrEmail(String prEmail) {
        this.prEmail = prEmail;
    }

    public Date getPrFechacreacion() {
        return prFechacreacion;
    }

    public void setPrFechacreacion(Date prFechacreacion) {
        this.prFechacreacion = prFechacreacion;
    }

    public Date getPrFechamodificacion() {
        return prFechamodificacion;
    }

    public void setPrFechamodificacion(Date prFechamodificacion) {
        this.prFechamodificacion = prFechamodificacion;
    }

    public String getPrContacto() {
        return prContacto;
    }

    public void setPrContacto(String prContacto) {
        this.prContacto = prContacto;
    }

    public boolean getPrStatus() {
        return prStatus;
    }

    public void setPrStatus(boolean prStatus) {
        this.prStatus = prStatus;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    public Usuario getPrIduser() {
        return prIduser;
    }

    public void setPrIduser(Usuario prIduser) {
        this.prIduser = prIduser;
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
        hash += (prId != null ? prId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.prId == null && other.prId != null) || (this.prId != null && !this.prId.equals(other.prId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Proveedor[ prId=" + prId + " ]";
    }
    
}
