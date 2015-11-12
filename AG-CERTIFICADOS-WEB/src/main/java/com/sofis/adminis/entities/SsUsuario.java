
/*
 * Clase creada para el proyecto SGREC
 * Desarrollada por Sofis Solutions
*/


package com.sofis.adminis.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author Sofis Solutions
 */
@Entity
//@Audited
@Table(name = "ss_usuario")
public class SsUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_id")
    private Integer usuId;
    @Column(name = "usu_correo_electronico")
    private String usuCorreoElectronico;
    @Column(name = "usu_cod")
    private String usuCod;
    /*@Basic(optional = false)
    @Column(name = "usu_fechamod")
    @Temporal(TemporalType.TIMESTAMP)*/
    @Transient private Date usuFechamod;
    @Basic(optional = false)
    @Column(name = "usu_user_code")
    private int usuUserCode;
    @Basic(optional = false)
    @Column(name = "usu_origen")
    private String usuOrigen;
    @Column(name = "usu_version")
    @Version private Integer usuVersion;
    @Column(name = "usu_descripcion")
    private String usuDescripcion;
    @Basic(optional = false)
    @Column(name = "usu_vigente")
    private boolean usuVigente;
    @Column(name = "usu_cambio_estado_desc")
    private String usuCambioEstadoDesc;
    @Basic(optional = false)
    @Column(name = "usu_primer_nombre")
    private String usuPrimerNombre;
    @Basic(optional = false)
    @Column(name = "usu_primer_apellido")
    private String usuPrimerApellido;
    @Column(name = "usu_segundo_nombre")
    private String usuSegundoNombre;
    @Column(name = "usu_segundo_apellido")
    private String usuSegundoApellido;
    @Basic(optional = false)
    @Column(name = "usu_nro_doc")
    private String usuNroDoc;
    @Column(name = "usu_telefono")
    private String usuTelefono;
    @Column(name = "usu_direccion")
    private String usuDireccion;
    //@Lob
    //@Column(name = "usu_foto")
    private byte[] usuFoto;
    //@JoinColumn(name = "usu_oficina_por_defecto", referencedColumnName = "ofi_id")
    //@ManyToOne
    @Column(name = "usu_oficina_por_defecto")
    private Integer usuOficinaPorDefecto;
    @Basic(optional = false)
    @Column(name = "usu_fecha_password")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaPassword;
    @Column(name = "usu_administrador")
    private boolean usuAdministrador;
    @Column(name = "usu_registrado")
    private boolean usuRegistrado;
    @Column(name = "usu_intentos_fallidos")
    private Integer usuIntentosFallidos; 
    @Column(name = "usu_password")
    private String usuPassword;
    @Column(name = "usu_cuenta_bloqueada")
    private boolean usuCuentaBloqueada;
    @Column(name = "usu_uuid_des")
    private String usuUuidDes;
    @Column(name="usu_fecha_uuid")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaUuid;
    
    public boolean isUsuAdministrador() {
        return usuAdministrador;
    }

    public void setUsuAdministrador(boolean usuAdministrador) {
        this.usuAdministrador = usuAdministrador;
    }

    public Date getUsuFechaPassword() {
        return usuFechaPassword;
    }

    public boolean isUsuRegistrado() {
        return usuRegistrado;
    }

    public void setUsuRegistrado(boolean usuRegistrado) {
        this.usuRegistrado = usuRegistrado;
    }

    public void setUsuFechaPassword(Date usuFechaPassword) {
        this.usuFechaPassword = usuFechaPassword;
    }

    public Integer getUsuIntentosFallidos() {
        return usuIntentosFallidos;
    }

    public void setUsuIntentosFallidos(Integer usuIntentosFallidos) {
        this.usuIntentosFallidos = usuIntentosFallidos;
    }

    public String getUsuCambioEstadoDesc() {
        return usuCambioEstadoDesc;
    }

    public void setUsuCambioEstadoDesc(String usuCambioEstadoDesc) {
        this.usuCambioEstadoDesc = usuCambioEstadoDesc;
    }

    public SsUsuario() {
    }

    public SsUsuario(Integer usuId) {
        this.usuId = usuId;
    }

    public boolean getUsuCuentaBloqueada() {
        return usuCuentaBloqueada;
    }

    public void setUsuCuentaBloqueada(boolean usuCuentaBloqueada) {
        this.usuCuentaBloqueada = usuCuentaBloqueada;
    }

    public String getUsuUuidDes() {
        return usuUuidDes;
    }

    public void setUsuUuidDes(String usuUuidDes) {
        this.usuUuidDes = usuUuidDes;
    }

    public SsUsuario(Integer usuId, String usuCorreoElectronico, String usuCod, Date usuFechamod, int usuUserCode, String usuOrigen) {
        this.usuId = usuId;
        this.usuCorreoElectronico = usuCorreoElectronico;
        this.usuCod = usuCod;
        this.usuFechamod = usuFechamod;
        this.usuUserCode = usuUserCode;
        this.usuOrigen = usuOrigen;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public byte[] getUsuFoto() {
        return usuFoto;
    }

    public void setUsuFoto(byte[] usuFoto) {
        this.usuFoto = usuFoto;
    }

    public String getUsuNroDoc() {
        return usuNroDoc;
    }

    public void setUsuNroDoc(String usuNroDoc) {
        this.usuNroDoc = usuNroDoc;
    }

    public Integer getUsuOficinaPorDefecto() {
        return usuOficinaPorDefecto;
    }

    public void setUsuOficinaPorDefecto(Integer usuOficinaPorDefecto) {
        this.usuOficinaPorDefecto = usuOficinaPorDefecto;
    }

    public String getUsuPrimerApellido() {
        return usuPrimerApellido;
    }

    public void setUsuPrimerApellido(String usuPrimerApellido) {
        this.usuPrimerApellido = usuPrimerApellido;
    }

    public String getUsuPrimerNombre() {
        return usuPrimerNombre;
    }

    public void setUsuPrimerNombre(String usuPrimerNombre) {
        this.usuPrimerNombre = usuPrimerNombre;
    }

    public String getUsuSegundoApellido() {
        return usuSegundoApellido;
    }

    public void setUsuSegundoApellido(String usuSegundoApellido) {
        this.usuSegundoApellido = usuSegundoApellido;
    }

    public String getUsuSegundoNombre() {
        return usuSegundoNombre;
    }

    public void setUsuSegundoNombre(String usuSegundoNombre) {
        this.usuSegundoNombre = usuSegundoNombre;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public boolean isUsuVigente() {
        return usuVigente;
    }

    public void setUsuVigente(boolean usuVigente) {
        this.usuVigente = usuVigente;
    }

    public String getUsuDescripcion() {
        return usuDescripcion;
    }

    public void setUsuDescripcion(String usuDescripcion) {
        this.usuDescripcion = usuDescripcion;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public String getUsuCorreoElectronico() {
        return usuCorreoElectronico;
    }

    public void setUsuCorreoElectronico(String usuCorreoElectronico) {
        this.usuCorreoElectronico = usuCorreoElectronico;
    }

    public String getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(String usuCod) {
        this.usuCod = usuCod;
    }

    public Date getUsuFechamod() {
        return usuFechamod;
    }

    public void setUsuFechamod(Date usuFechamod) {
        this.usuFechamod = usuFechamod;
    }

    public int getUsuUserCode() {
        return usuUserCode;
    }

    public void setUsuUserCode(int usuUserCode) {
        this.usuUserCode = usuUserCode;
    }

    public String getUsuOrigen() {
        return usuOrigen;
    }

    public void setUsuOrigen(String usuOrigen) {
        this.usuOrigen = usuOrigen;
    }

    public Integer getUsuVersion() {
        return usuVersion;
    }

    public void setUsuVersion(Integer usuVersion) {
        this.usuVersion = usuVersion;
    }

    public Date getUsuFechaUuid() {
        return usuFechaUuid;
    }

    public void setUsuFechaUuid(Date usuFechaUuid) {
        this.usuFechaUuid = usuFechaUuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

  public String getUsuPassword()
  {
    return usuPassword;
  }

  public void setUsuPassword(String usuPassword)
  {
    this.usuPassword = usuPassword;
  }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SsUsuario)) {
            return false;
        }
        SsUsuario other = (SsUsuario) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sofis.dgrec.business.entities.SsUsuario[usuId=" + usuId + "]";
    }

}