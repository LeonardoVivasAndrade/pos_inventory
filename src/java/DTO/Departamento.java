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
@Table(name = "departamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")
    , @NamedQuery(name = "Departamento.findByDpId", query = "SELECT d FROM Departamento d WHERE d.dpId = :dpId")
    , @NamedQuery(name = "Departamento.findByDpDescripcion", query = "SELECT d FROM Departamento d WHERE d.dpDescripcion = :dpDescripcion")
    , @NamedQuery(name = "Departamento.findByDpIsservice", query = "SELECT d FROM Departamento d WHERE d.dpIsservice = :dpIsservice")
    , @NamedQuery(name = "Departamento.findByDpFechacreacion", query = "SELECT d FROM Departamento d WHERE d.dpFechacreacion = :dpFechacreacion")
    , @NamedQuery(name = "Departamento.findByDpFechamodificacion", query = "SELECT d FROM Departamento d WHERE d.dpFechamodificacion = :dpFechamodificacion")
    , @NamedQuery(name = "Departamento.findByDpStatus", query = "SELECT d FROM Departamento d WHERE d.dpStatus = :dpStatus")})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dp_id")
    private Integer dpId;
    @Basic(optional = false)
    @Column(name = "dp_descripcion")
    private String dpDescripcion;
    @Basic(optional = false)
    @Column(name = "dp_isservice")
    private boolean dpIsservice;
    @Basic(optional = false)
    @Column(name = "dp_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dpFechacreacion;
    @Basic(optional = false)
    @Column(name = "dp_fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dpFechamodificacion;
    @Basic(optional = false)
    @Column(name = "dp_status")
    private boolean dpStatus;
    @JoinColumn(name = "dp_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario dpIduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dveIddepartamento")
    private List<Dventa> dventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stIddepartamento")
    private List<Stock> stockList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inIddepartamento")
    private List<Inventario> inventarioList;

    public Departamento() {
    }

    public Departamento(Integer dpId) {
        this.dpId = dpId;
    }

    public Departamento(Integer dpId, String dpDescripcion, boolean dpIsservice, Date dpFechacreacion, Date dpFechamodificacion, boolean dpStatus) {
        this.dpId = dpId;
        this.dpDescripcion = dpDescripcion;
        this.dpIsservice = dpIsservice;
        this.dpFechacreacion = dpFechacreacion;
        this.dpFechamodificacion = dpFechamodificacion;
        this.dpStatus = dpStatus;
    }

    public Integer getDpId() {
        return dpId;
    }

    public void setDpId(Integer dpId) {
        this.dpId = dpId;
    }

    public String getDpDescripcion() {
        return dpDescripcion;
    }

    public void setDpDescripcion(String dpDescripcion) {
        this.dpDescripcion = dpDescripcion;
    }

    public boolean getDpIsservice() {
        return dpIsservice;
    }

    public void setDpIsservice(boolean dpIsservice) {
        this.dpIsservice = dpIsservice;
    }

    public Date getDpFechacreacion() {
        return dpFechacreacion;
    }

    public void setDpFechacreacion(Date dpFechacreacion) {
        this.dpFechacreacion = dpFechacreacion;
    }

    public Date getDpFechamodificacion() {
        return dpFechamodificacion;
    }

    public void setDpFechamodificacion(Date dpFechamodificacion) {
        this.dpFechamodificacion = dpFechamodificacion;
    }

    public boolean getDpStatus() {
        return dpStatus;
    }

    public void setDpStatus(boolean dpStatus) {
        this.dpStatus = dpStatus;
    }

    public Usuario getDpIduser() {
        return dpIduser;
    }

    public void setDpIduser(Usuario dpIduser) {
        this.dpIduser = dpIduser;
    }

    @XmlTransient
    public List<Dventa> getDventaList() {
        return dventaList;
    }

    public void setDventaList(List<Dventa> dventaList) {
        this.dventaList = dventaList;
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
        hash += (dpId != null ? dpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.dpId == null && other.dpId != null) || (this.dpId != null && !this.dpId.equals(other.dpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Departamento[ dpId=" + dpId + " ]";
    }
    
}
