/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.repositorio.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.agesic.certificados.to.CampoEntrada;

/**
 *
 * @author Sofis Solutions (www.sofis-solutions.com)
 */
@Entity
@Table(name = "certificado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Certificado.findAll", query = "SELECT c FROM Certificado c"),
    @NamedQuery(name = "Certificado.findByCertId", query = "SELECT c FROM Certificado c WHERE c.certId = :certId"),
    @NamedQuery(name = "Certificado.findByCertUserId", query = "SELECT c FROM Certificado c WHERE c.certUserId = :certUserId"),
    @NamedQuery(name = "Certificado.findByCertDate", query = "SELECT c FROM Certificado c WHERE c.certDate = :certDate"),
    @NamedQuery(name = "Certificado.findByCertEstado", query = "SELECT c FROM Certificado c WHERE c.certEstado = :certEstado"),
    @NamedQuery(name = "Certificado.findByCertPluginId", query = "SELECT c FROM Certificado c WHERE c.certPluginId = :certPluginId")})
public class Certificado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "cert_id")
    private Integer certId;
    @Size(max = 45)
    @Column(name = "cert_user_id")
    private String certUserId;
    @Column(name = "cert_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date certDate;
    @Column(name = "cert_estado")
    private Integer certEstado;
    
    
    @Column(name = "cert_plugin_id")
    private Long certPluginId;
    
    
    @Column(name = "cert_plugin_nombre")
    private String certPluginNombre;

    
    
    @Column(name = "cert_plugin_blob")
    private Boolean certPluginBlob;
    
    @Column(length= Integer.MAX_VALUE,name = "cert_doc")
    private byte[] certDoc;
    
    @Column(length= Integer.MAX_VALUE,name = "cert_path")
    private String certPath;
    
    @Column(length= Integer.MAX_VALUE,name = "cert_codigo_val")
    private String certCodigoValidacion;
    
    @Column(length= Integer.MAX_VALUE, name = "cert_busqueda")
    private String campoBusqueda;
    
    @Column(length= Integer.MAX_VALUE, name = "cert_man_dias")
    private Long certManDias;
    
    
    
    public Certificado() {
    }

    public Certificado(Integer certId) {
        this.certId = certId;
    }

    public Integer getCertId() {
        return certId;
    }

    public void setCertId(Integer certId) {
        this.certId = certId;
    }

    public String getCertUserId() {
        return certUserId;
    }

    public void setCertUserId(String certUserId) {
        this.certUserId = certUserId;
    }

    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    public Integer getCertEstado() {
        return certEstado;
    }

    public void setCertEstado(Integer certEstado) {
        this.certEstado = certEstado;
    }

    public Long getCertPluginId() {
        return certPluginId;
    }

    public void setCertPluginId(Long certPluginId) {
        this.certPluginId = certPluginId;
    }

    

    public String getCertPluginNombre() {
        return certPluginNombre;
    }

    public void setCertPluginNombre(String certPluginNombre) {
        this.certPluginNombre = certPluginNombre;
    }

   

    public byte[] getCertDoc() {
        return certDoc;
    }

    public void setCertDoc(byte[] certDoc) {
        this.certDoc = certDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (certId != null ? certId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Certificado)) {
            return false;
        }
        Certificado other = (Certificado) object;
        if ((this.certId == null && other.certId != null) || (this.certId != null && !this.certId.equals(other.certId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.agesic.certificados.entidades.Certificado[ certId=" + certId + " ]";
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getCertCodigoValidacion() {
        return certCodigoValidacion;
    }

    public void setCertCodigoValidacion(String certCodigoValidacion) {
        this.certCodigoValidacion = certCodigoValidacion;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public Boolean getCertPluginBlob() {
        return certPluginBlob;
    }

    public void setCertPluginBlob(Boolean certPluginBlob) {
        this.certPluginBlob = certPluginBlob;
    }

    public Long getCertManDias() {
        return certManDias;
    }

    public void setCertManDias(Long certManDias) {
        this.certManDias = certManDias;
    }

    
    
}
