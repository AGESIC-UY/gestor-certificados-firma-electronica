package org.agesic.certificados.web.mb;

import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.sofis.adminis.entities.SsUsuario;
import java.io.File;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import javax.faces.context.ExternalContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.agesic.certificados.delegate.LogicDelegate;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.to.CampoEntrada;
import org.agesic.certificados.to.ErrorValidacion;
import org.agesic.certificados.to.TipoCertificado;
import org.jopendocument.util.FileUtils;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "listartCertificadoMB")
@ViewScoped
public class ListarCertificadoMB implements Serializable {

    LogicDelegate logicDelegate;
    @ManagedProperty("#{inicioMB}")
    private InicioMB inicioMB;
    private Long tipoCertificadoSelected;
    TipoCertificado certificadoSelecionado = null;
    Certificado certificado = null;
    CampoEntrada[] campoEntrada = null;
    List<Certificado> certificados = new ArrayList();
    String campoBusqueda = null;
    String usuarioBusqueda = null;
    Date fechaDesde = null;
    Date fechaHasta = null;

    public ListarCertificadoMB() {
    }

    @PostConstruct
    public void init() {
        logicDelegate = new LogicDelegate();
    }

    public String buscar() {
        certificados = logicDelegate.obtenerCertificados(certificadoSelecionado, fechaDesde, fechaHasta, campoBusqueda, null);
        return null;
    }

    public String imprimir(Certificado cert) throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (session != null) {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String clave = "sofis" + sdf.format(new Date());
            String docEntrada = req.getContextPath() + "/ImprimirServlet?clave=" + clave;

            if (cert.getCertPluginBlob() != null && cert.getCertPluginBlob()) {
                String path = cert.getCertPath();
                byte[] b = FileUtils.readBytes(new File(path));
                session.setAttribute("report_collection_data", b);
            } else {
                session.setAttribute("report_collection_data", cert.getCertDoc());
            }


            JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "window.open('" + docEntrada + "','Descargar');");
        }
        return null;
    }

    public String enviarPorEmail(Certificado cert) {
        /*
         * try { String[] enviarEmaillCorreos =
         * logicDelegate.enviarEmaill(certificadoSelecionado,certificado,campoEntrada);
         * String msg = Arrays.toString(enviarEmaillCorreos);
         * FacesContext.getCurrentInstance().addMessage(null, new
         * FacesMessage("Envió correo de emails a: " + msg,"Envió correo de
         * emails a: " + msg)); } catch (Exception ex) { ex.printStackTrace();
         * FacesContext.getCurrentInstance().addMessage(null, new
         * FacesMessage("Error en el servidor: " + ex.getMessage(), "Error en el
         * servidor: " + ex.getMessage()));
            }
         */
        return null;
    }

    public Long getTipoCertificadoSelected() {
        return tipoCertificadoSelected;
    }

    public void setTipoCertificadoSelected(Long tipoCertificadoSelected) {
        this.tipoCertificadoSelected = tipoCertificadoSelected;
    }

    public InicioMB getInicioMB() {
        return inicioMB;
    }

    public void setInicioMB(InicioMB inicioMB) {
        this.inicioMB = inicioMB;
    }

    public TipoCertificado getCertificadoSelecionado() {
        return certificadoSelecionado;
    }

    public void setCertificadoSelecionado(TipoCertificado certificadoSelecionado) {
        this.certificadoSelecionado = certificadoSelecionado;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public String getUsuarioBusqueda() {
        return usuarioBusqueda;
    }

    public void setUsuarioBusqueda(String usuarioBusqueda) {
        this.usuarioBusqueda = usuarioBusqueda;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
    
}
