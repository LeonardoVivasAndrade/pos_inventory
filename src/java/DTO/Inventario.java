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
import javax.persistence.OneToOne;
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
@Table(name = "inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i")
    , @NamedQuery(name = "Inventario.findByInId", query = "SELECT i FROM Inventario i WHERE i.inId = :inId")
    , @NamedQuery(name = "Inventario.findByInCodigo", query = "SELECT i FROM Inventario i WHERE i.inCodigo = :inCodigo")
    , @NamedQuery(name = "Inventario.findByInDescripcion", query = "SELECT i FROM Inventario i WHERE i.inDescripcion = :inDescripcion")
    , @NamedQuery(name = "Inventario.findByInReferencia", query = "SELECT i FROM Inventario i WHERE i.inReferencia = :inReferencia")
    , @NamedQuery(name = "Inventario.findByInCosto", query = "SELECT i FROM Inventario i WHERE i.inCosto = :inCosto")
    , @NamedQuery(name = "Inventario.findByInPrecioSiva", query = "SELECT i FROM Inventario i WHERE i.inPrecioSiva = :inPrecioSiva")
    , @NamedQuery(name = "Inventario.findByInPrecioCiva", query = "SELECT i FROM Inventario i WHERE i.inPrecioCiva = :inPrecioCiva")
    , @NamedQuery(name = "Inventario.findByInImpuesto", query = "SELECT i FROM Inventario i WHERE i.inImpuesto = :inImpuesto")
    , @NamedQuery(name = "Inventario.findByInImpuestoporcent", query = "SELECT i FROM Inventario i WHERE i.inImpuestoporcent = :inImpuestoporcent")
    , @NamedQuery(name = "Inventario.findByInUtilidad", query = "SELECT i FROM Inventario i WHERE i.inUtilidad = :inUtilidad")
    , @NamedQuery(name = "Inventario.findByInUtilidadporcent", query = "SELECT i FROM Inventario i WHERE i.inUtilidadporcent = :inUtilidadporcent")
    , @NamedQuery(name = "Inventario.findByInIsexento", query = "SELECT i FROM Inventario i WHERE i.inIsexento = :inIsexento")
    , @NamedQuery(name = "Inventario.findByInImage", query = "SELECT i FROM Inventario i WHERE i.inImage = :inImage")
    , @NamedQuery(name = "Inventario.findByInFechacreacion", query = "SELECT i FROM Inventario i WHERE i.inFechacreacion = :inFechacreacion")
    , @NamedQuery(name = "Inventario.findByInFechamodificacion", query = "SELECT i FROM Inventario i WHERE i.inFechamodificacion = :inFechamodificacion")
    , @NamedQuery(name = "Inventario.findByInStatus", query = "SELECT i FROM Inventario i WHERE i.inStatus = :inStatus")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "in_id")
    private Integer inId;
    @Basic(optional = false)
    @Column(name = "in_codigo")
    private String inCodigo;
    @Basic(optional = false)
    @Column(name = "in_descripcion")
    private String inDescripcion;
    @Basic(optional = false)
    @Column(name = "in_referencia")
    private String inReferencia;
    @Basic(optional = false)
    @Column(name = "in_costo")
    private float inCosto;
    @Basic(optional = false)
    @Column(name = "in_precio_siva")
    private float inPrecioSiva;
    @Basic(optional = false)
    @Column(name = "in_precio_civa")
    private float inPrecioCiva;
    @Basic(optional = false)
    @Column(name = "in_impuesto")
    private float inImpuesto;
    @Basic(optional = false)
    @Column(name = "in_impuestoporcent")
    private int inImpuestoporcent;
    @Basic(optional = false)
    @Column(name = "in_utilidad")
    private float inUtilidad;
    @Basic(optional = false)
    @Column(name = "in_utilidadporcent")
    private int inUtilidadporcent;
    @Basic(optional = false)
    @Column(name = "in_isexento")
    private boolean inIsexento;
    @Basic(optional = false)
    @Column(name = "in_image")
    private String inImage;
    @Basic(optional = false)
    @Column(name = "in_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inFechacreacion;
    @Basic(optional = false)
    @Column(name = "in_fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inFechamodificacion;
    @Basic(optional = false)
    @Column(name = "in_status")
    private boolean inStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dveIdinventario")
    private List<Dventa> dventaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dcoIdinventario")
    private List<Dcompra> dcompraList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "inventario")
    private Stock stock;
    @JoinColumn(name = "in_iddepartamento", referencedColumnName = "dp_id")
    @ManyToOne(optional = false)
    private Departamento inIddepartamento;
    @JoinColumn(name = "in_iduser", referencedColumnName = "us_id")
    @ManyToOne(optional = false)
    private Usuario inIduser;

    public Inventario() {
    }

    public Inventario(Integer inId) {
        this.inId = inId;
    }

    public Inventario(Integer inId, String inCodigo, String inDescripcion, String inReferencia, float inCosto, float inPrecioSiva, float inPrecioCiva, float inImpuesto, int inImpuestoporcent, float inUtilidad, int inUtilidadporcent, boolean inIsexento, String inImage, Date inFechacreacion, Date inFechamodificacion, boolean inStatus) {
        this.inId = inId;
        this.inCodigo = inCodigo;
        this.inDescripcion = inDescripcion;
        this.inReferencia = inReferencia;
        this.inCosto = inCosto;
        this.inPrecioSiva = inPrecioSiva;
        this.inPrecioCiva = inPrecioCiva;
        this.inImpuesto = inImpuesto;
        this.inImpuestoporcent = inImpuestoporcent;
        this.inUtilidad = inUtilidad;
        this.inUtilidadporcent = inUtilidadporcent;
        this.inIsexento = inIsexento;
        this.inImage = inImage;
        this.inFechacreacion = inFechacreacion;
        this.inFechamodificacion = inFechamodificacion;
        this.inStatus = inStatus;
    }

    public Integer getInId() {
        return inId;
    }

    public void setInId(Integer inId) {
        this.inId = inId;
    }

    public String getInCodigo() {
        return inCodigo;
    }

    public void setInCodigo(String inCodigo) {
        this.inCodigo = inCodigo;
    }

    public String getInDescripcion() {
        return inDescripcion;
    }

    public void setInDescripcion(String inDescripcion) {
        this.inDescripcion = inDescripcion;
    }

    public String getInReferencia() {
        return inReferencia;
    }

    public void setInReferencia(String inReferencia) {
        this.inReferencia = inReferencia;
    }

    public float getInCosto() {
        return inCosto;
    }

    public void setInCosto(float inCosto) {
        this.inCosto = inCosto;
    }

    public float getInPrecioSiva() {
        return inPrecioSiva;
    }

    public void setInPrecioSiva(float inPrecioSiva) {
        this.inPrecioSiva = inPrecioSiva;
    }

    public float getInPrecioCiva() {
        return inPrecioCiva;
    }

    public void setInPrecioCiva(float inPrecioCiva) {
        this.inPrecioCiva = inPrecioCiva;
    }

    public float getInImpuesto() {
        return inImpuesto;
    }

    public void setInImpuesto(float inImpuesto) {
        this.inImpuesto = inImpuesto;
    }

    public int getInImpuestoporcent() {
        return inImpuestoporcent;
    }

    public void setInImpuestoporcent(int inImpuestoporcent) {
        this.inImpuestoporcent = inImpuestoporcent;
    }

    public float getInUtilidad() {
        return inUtilidad;
    }

    public void setInUtilidad(float inUtilidad) {
        this.inUtilidad = inUtilidad;
    }

    public int getInUtilidadporcent() {
        return inUtilidadporcent;
    }

    public void setInUtilidadporcent(int inUtilidadporcent) {
        this.inUtilidadporcent = inUtilidadporcent;
    }

    public boolean getInIsexento() {
        return inIsexento;
    }

    public void setInIsexento(boolean inIsexento) {
        this.inIsexento = inIsexento;
    }

    public String getInImage() {
        return inImage;
    }

    public void setInImage(String inImage) {
        this.inImage = inImage;
    }

    public Date getInFechacreacion() {
        return inFechacreacion;
    }

    public void setInFechacreacion(Date inFechacreacion) {
        this.inFechacreacion = inFechacreacion;
    }

    public Date getInFechamodificacion() {
        return inFechamodificacion;
    }

    public void setInFechamodificacion(Date inFechamodificacion) {
        this.inFechamodificacion = inFechamodificacion;
    }

    public boolean getInStatus() {
        return inStatus;
    }

    public void setInStatus(boolean inStatus) {
        this.inStatus = inStatus;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Departamento getInIddepartamento() {
        return inIddepartamento;
    }

    public void setInIddepartamento(Departamento inIddepartamento) {
        this.inIddepartamento = inIddepartamento;
    }

    public Usuario getInIduser() {
        return inIduser;
    }

    public void setInIduser(Usuario inIduser) {
        this.inIduser = inIduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inId != null ? inId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.inId == null && other.inId != null) || (this.inId != null && !this.inId.equals(other.inId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Inventario[ inId=" + inId + " ]";
    }
    
}
