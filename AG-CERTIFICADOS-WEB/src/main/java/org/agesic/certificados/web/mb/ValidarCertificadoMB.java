package org.agesic.certificados.web.mb;

import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.octo.captcha.service.CaptchaServiceException;
import com.sofis.adminis.entities.SsUsuario;
import java.io.File;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.agesic.certificados.delegate.LogicDelegate;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.to.CampoEntrada;
import org.agesic.certificados.to.ErrorValidacion;
import org.agesic.certificados.to.TipoCertificado;
import org.agesic.certificados.web.captcha.CaptchaService;
import org.jopendocument.util.FileUtils;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "validarCertificadoMB")
@ViewScoped
public class ValidarCertificadoMB implements Serializable {

    LogicDelegate logicDelegate;
    private Resource resource;
    private String codigo;
    private Boolean certificadoValido = false;
    private Boolean validar = false;
    private String challengeCaptcha;
    private Boolean validCaptcha = false;
    private Long now = new Date().getTime();

    public ValidarCertificadoMB() {
    }

    @PostConstruct
    public void init() {
        logicDelegate = new LogicDelegate();
        certificadoValido = false;
        validar = false;
        String codigoParam = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("codigo");
        if (codigoParam != null) {
            codigo = codigoParam;
            try {
                validCaptcha = true;
                validarCertificadoAction();
            } catch (Exception w) {
                w.printStackTrace();
            }
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String validarCertificadoAction() throws Exception {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String id = request.getSession().getId();

        String entry = getChallengeCaptcha();

        //pos si viene desde el QR, no se valida el captcha
        if (!validCaptcha) {
            try {
                validCaptcha = CaptchaService.getInstance().validateResponseForID(id, entry);
            } catch (CaptchaServiceException ex) {
                ex.printStackTrace();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La imágen no coincide con el texto ingresado", "La imágen no coincide con el texto ingresado"));
            now = new Date().getTime();
            return null;
        }

        validar = true;
        Certificado cert = logicDelegate.validarCertificado(codigo);
        if (cert != null) {
            if (cert.getCertPluginBlob() != null && cert.getCertPluginBlob()) {
                resource = new ByteArrayResource(cert.getCertDoc());
            } else {
                String path = cert.getCertPath();
                byte[] b = FileUtils.readBytes(new File(path));
                resource = new ByteArrayResource(b);
            }
            certificadoValido = true;
        } else {
            certificadoValido = false;
        }
        challengeCaptcha = "";
        validCaptcha = false;
        now = new Date().getTime();
        return null;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }

    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Boolean getCertificadoValido() {
        return certificadoValido;
    }

    public void setCertificadoValido(Boolean certificadoValido) {
        this.certificadoValido = certificadoValido;
    }

    public String getChallengeCaptcha() {
        return challengeCaptcha;
    }

    public void setChallengeCaptcha(String challengeCaptcha) {
        this.challengeCaptcha = challengeCaptcha;
    }
}
