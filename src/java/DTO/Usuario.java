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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsId", query = "SELECT u FROM Usuario u WHERE u.usId = :usId")
    , @NamedQuery(name = "Usuario.findByUsDescripcion", query = "SELECT u FROM Usuario u WHERE u.usDescripcion = :usDescripcion")
    , @NamedQuery(name = "Usuario.findByUsNick", query = "SELECT u FROM Usuario u WHERE u.usNick = :usNick")
    , @NamedQuery(name = "Usuario.findByUsFoto", query = "SELECT u FROM Usuario u WHERE u.usFoto = :usFoto")
    , @NamedQuery(name = "Usuario.findByUsFechacreacion", query = "SELECT u FROM Usuario u WHERE u.usFechacreacion = :usFechacreacion")
    , @NamedQuery(name = "Usuario.findByUsFechamodificacion", query = "SELECT u FROM Usuario u WHERE u.usFechamodificacion = :usFechamodificacion")
    , @NamedQuery(name = "Usuario.findByUsPassword", query = "SELECT u FROM Usuario u WHERE u.usPassword = :usPassword")
    , @NamedQuery(name = "Usuario.findByUsLastlogin", query = "SELECT u FROM Usuario u WHERE u.usLastlogin = :usLastlogin")
    , @NamedQuery(name = "Usuario.findByUsStatus", query = "SELECT u FROM Usuario u WHERE u.usStatus = :usStatus")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "us_id")
    private Integer usId;
    @Basic(optional = false)
    @Column(name = "us_descripcion")
    private String usDescripcion;
    @Basic(optional = false)
    @Column(name = "us_nick")
    private String usNick;
    @Column(name = "us_foto")
    private String usFoto;
    @Basic(optional = false)
    @Column(name = "us_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usFechacreacion;
    @Basic(optional = false)
    @Column(name = "us_fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usFechamodificacion;
    @Basic(optional = false)
    @Column(name = "us_password")
    private String usPassword;
    @Basic(optional = false)
    @Column(name = "us_lastlogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usLastlogin;
    @Basic(optional = false)
    @Column(name = "us_status")
    private boolean usStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clIduser")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coIduser")
    private List<Compra> compraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veIduser")
    private List<Venta> ventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dpIduser")
    private List<Departamento> departamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caIduser")
    private List<Cajachica> cajachicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prIduser")
    private List<Proveedor> proveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dveIduser")
    private List<Dventa> dventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcoIduser")
    private List<Dcompra> dcompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stIduser")
    private List<Stock> stockList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inIduser")
    private List<Inventario> inventarioList;

    public Usuario() {
    }

    public Usuario(Integer usId) {
        this.usId = usId;
    }

    public Usuario(Integer usId, String usDescripcion, String usNick, Date usFechacreacion, Date usFechamodificacion, String usPassword, Date usLastlogin, boolean usStatus) {
        this.usId = usId;
        this.usDescripcion = usDescripcion;
        this.usNick = usNick;
        this.usFechacreacion = usFechacreacion;
        this.usFechamodificacion = usFechamodificacion;
        this.usPassword = usPassword;
        this.usLastlogin = usLastlogin;
        this.usStatus = usStatus;
    }

    public Integer getUsId() {
        return usId;
    }

    public void setUsId(Integer usId) {
        this.usId = usId;
    }

    public String getUsDescripcion() {
        return usDescripcion;
    }

    public void setUsDescripcion(String usDescripcion) {
        this.usDescripcion = usDescripcion;
    }

    public String getUsNick() {
        return usNick;
    }

    public void setUsNick(String usNick) {
        this.usNick = usNick;
    }

    public String getUsFoto() {
        return usFoto;
    }

    public void setUsFoto(String usFoto) {
        this.usFoto = usFoto;
    }

    public Date getUsFechacreacion() {
        return usFechacreacion;
    }

    public void setUsFechacreacion(Date usFechacreacion) {
        this.usFechacreacion = usFechacreacion;
    }

    public Date getUsFechamodificacion() {
        return usFechamodificacion;
    }

    public void setUsFechamodificacion(Date usFechamodificacion) {
        this.usFechamodificacion = usFechamodificacion;
    }

    public String getUsPassword() {
        return usPassword;
    }

    public void setUsPassword(String usPassword) {
        this.usPassword = usPassword;
    }

    public Date getUsLastlogin() {
        return usLastlogin;
    }

    public void setUsLastlogin(Date usLastlogin) {
        this.usLastlogin = usLastlogin;
    }

    public boolean getUsStatus() {
        return usStatus;
    }

    public void setUsStatus(boolean usStatus) {
        this.usStatus = usStatus;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @XmlTransient
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> departamentoList) {
        this.departamentoList = departamentoList;
    }

    @XmlTransient
    public List<Cajachica> getCajachicaList() {
        return cajachicaList;
    }

    public void setCajachicaList(List<Cajachica> cajachicaList) {
        this.cajachicaList = cajachicaList;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @XmlTransient
    public List<Dventa> getDventaList() {
        return dventaList;
    }

    public void setDventaList(List<Dventa> dventaList) {
        this.dventaList = dventaList;
    }

    @XmlTransient
    public List<Dcompra> getDcompraList() {
        return dcompraList;
    }

    public void setDcompraList(List<Dcompra> dcompraList) {
        this.dcompraList = dcompraList;
    }

    @XmlTransient
    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usId != null ? usId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usId == null && other.usId != null) || (this.usId != null && !this.usId.equals(other.usId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Usuario[ usId=" + usId + " ]";
    }
    
}
