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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByClId", query = "SELECT c FROM Cliente c WHERE c.clId = :clId")
    , @NamedQuery(name = "Cliente.findByClCodigo", query = "SELECT c FROM Cliente c WHERE c.clCodigo = :clCodigo")
    , @NamedQuery(name = "Cliente.findByClDescripcion", query = "SELECT c FROM Cliente c WHERE c.clDescripcion = :clDescripcion")
    , @NamedQuery(name = "Cliente.findByClDocumento", query = "SELECT c FROM Cliente c WHERE c.clDocumento = :clDocumento")
    , @NamedQuery(name = "Cliente.findByClDireccion", query = "SELECT c FROM Cliente c WHERE c.clDireccion = :clDireccion")
    , @NamedQuery(name = "Cliente.findByClTelefono", query = "SELECT c FROM Cliente c WHERE c.clTelefono = :clTelefono")
    , @NamedQuery(name = "Cliente.findByClTelefono2", query = "SELECT c FROM Cliente c WHERE c.clTelefono2 = :clTelefono2")
    , @NamedQuery(name = "Cliente.findByClEmail", query = "SELECT c FROM Cliente c WHERE c.clEmail = :clEmail")
    , @NamedQuery(name = "Cliente.findByClCelular", query = "SELECT c FROM Cliente c WHERE c.clCelular = :clCelular")
    , @NamedQuery(name = "Cliente.findByClCelular2", query = "SELECT c FROM Cliente c WHERE c.clCelular2 = :clCelular2")
    , @NamedQuery(name = "Cliente.findByClWhatsapp", query = "SELECT c FROM Cliente c WHERE c.clWhatsapp = :clWhatsapp")
    , @NamedQuery(name = "Cliente.findByClFechacreacion", query = "SELECT c FROM Cliente c WHERE c.clFechacreacion = :clFechacreacion")
    , @NamedQuery(name = "Cliente.findByClFechamodificacion", query = "SELECT c FROM Cliente c WHERE c.clFechamodificacion = :clFechamodificacion")
    , @NamedQuery(name = "Cliente.findByClStatus", query = "SELECT c FROM Cliente c WHERE c.clStatus = :clStatus")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cl_id")
    private Integer clId;
    @Basic(optional = false)
    @Column(name = "cl_codigo")
    private String clCodigo;
    @Basic(optional = false)
    @Column(name = "cl_descripcion")
    private String clDescripcion;
    @Basic(optional = false)
    @Column(name = "cl_documento")
    private int clDocumento;
    @Basic(optional = false)
    @Column(name = "cl_direccion")
    private String clDireccion;
    @Basic(optional = false)
    @Column(name = "cl_telefono")
    private String clTelefono;
    @Basic(optional = false)
    @Column(name = "cl_telefono2")
    private String clTelefono2;
    @Basic(optional = false)
    @Column(name = "cl_email")
    private String clEmail;
    @Basic(optional = false)
    @Column(name = "cl_celular")
    private String clCelular;
    @Basic(optional = false)
    @Column(name = "cl_celular2")
    private String clCelular2;
    @Basic(optional = false)
    @Column(name = "cl_whatsapp")
    private String clWhatsapp;
    @Basic(optional = false)
    @Column(name = "cl_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clFechacreacion;
    @Basic(optional = false)
    @Column(name = "cl_fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clFechamodificacion;
    @Basic(optional = false)
    @Column(name = "cl_status")
    private int clStatus;
    @JoinColumn(name = "cl_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario clIduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veIdcliente")
    private List<Venta> ventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dveIdcliente")
    private List<Dventa> dventaList;

    public Cliente() {
    }

    public Cliente(Integer clId) {
        this.clId = clId;
    }

    public Cliente(Integer clId, String clCodigo, String clDescripcion, int clDocumento, String clDireccion, String clTelefono, String clTelefono2, String clEmail, String clCelular, String clCelular2, String clWhatsapp, Date clFechacreacion, Date clFechamodificacion, int clStatus) {
        this.clId = clId;
        this.clCodigo = clCodigo;
        this.clDescripcion = clDescripcion;
        this.clDocumento = clDocumento;
        this.clDireccion = clDireccion;
        this.clTelefono = clTelefono;
        this.clTelefono2 = clTelefono2;
        this.clEmail = clEmail;
        this.clCelular = clCelular;
        this.clCelular2 = clCelular2;
        this.clWhatsapp = clWhatsapp;
        this.clFechacreacion = clFechacreacion;
        this.clFechamodificacion = clFechamodificacion;
        this.clStatus = clStatus;
    }

    public Integer getClId() {
        return clId;
    }

    public void setClId(Integer clId) {
        this.clId = clId;
    }

    public String getClCodigo() {
        return clCodigo;
    }

    public void setClCodigo(String clCodigo) {
        this.clCodigo = clCodigo;
    }

    public String getClDescripcion() {
        return clDescripcion;
    }

    public void setClDescripcion(String clDescripcion) {
        this.clDescripcion = clDescripcion;
    }

    public int getClDocumento() {
        return clDocumento;
    }

    public void setClDocumento(int clDocumento) {
        this.clDocumento = clDocumento;
    }

    public String getClDireccion() {
        return clDireccion;
    }

    public void setClDireccion(String clDireccion) {
        this.clDireccion = clDireccion;
    }

    public String getClTelefono() {
        return clTelefono;
    }

    public void setClTelefono(String clTelefono) {
        this.clTelefono = clTelefono;
    }

    public String getClTelefono2() {
        return clTelefono2;
    }

    public void setClTelefono2(String clTelefono2) {
        this.clTelefono2 = clTelefono2;
    }

    public String getClEmail() {
        return clEmail;
    }

    public void setClEmail(String clEmail) {
        this.clEmail = clEmail;
    }

    public String getClCelular() {
        return clCelular;
    }

    public void setClCelular(String clCelular) {
        this.clCelular = clCelular;
    }

    public String getClCelular2() {
        return clCelular2;
    }

    public void setClCelular2(String clCelular2) {
        this.clCelular2 = clCelular2;
    }

    public String getClWhatsapp() {
        return clWhatsapp;
    }

    public void setClWhatsapp(String clWhatsapp) {
        this.clWhatsapp = clWhatsapp;
    }

    public Date getClFechacreacion() {
        return clFechacreacion;
    }

    public void setClFechacreacion(Date clFechacreacion) {
        this.clFechacreacion = clFechacreacion;
    }

    public Date getClFechamodificacion() {
        return clFechamodificacion;
    }

    public void setClFechamodificacion(Date clFechamodificacion) {
        this.clFechamodificacion = clFechamodificacion;
    }

    public int getClStatus() {
        return clStatus;
    }

    public void setClStatus(int clStatus) {
        this.clStatus = clStatus;
    }

    public Usuario getClIduser() {
        return clIduser;
    }

    public void setClIduser(Usuario clIduser) {
        this.clIduser = clIduser;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
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
        hash += (clId != null ? clId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clId == null && other.clId != null) || (this.clId != null && !this.clId.equals(other.clId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Cliente[ clId=" + clId + " ]";
    }
    
}
