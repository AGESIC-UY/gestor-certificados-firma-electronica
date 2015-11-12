package org.agesic.certificados.web.mb;

import com.sofis.adminis.entities.SsUsuario;
import java.io.FileInputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.agesic.certificados.classfinder.JavaClassFinder;
import org.agesic.certificados.delegate.LogicDelegate;
import org.agesic.certificados.to.TipoCertificado;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "inicioMB")
@SessionScoped
public class InicioMB implements Serializable {

    LogicDelegate logicDelegate;
    //el usuario logueado
    private SsUsuario user;
    private List<TipoCertificado> tiposCertificados = new ArrayList();
    private List<SelectItem> tipoCertificadosSI = new ArrayList();

    public InicioMB() {
    }

    @PostConstruct
    public void init() {
        logicDelegate = new LogicDelegate();
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (p != null){
            user = new SsUsuario();
            user.setUsuCod(p.getName());
        }
        tiposCertificados = obtenerTiposCertificados();
        tipoCertificadosSI.add(new SelectItem(Long.MIN_VALUE, "-----------"));
        for (TipoCertificado t : tiposCertificados) {
            //if (p!= null && t.validarUsuario(user.getUsuCod())){
                tipoCertificadosSI.add(new SelectItem(new Long(t.obtenerIdentificador()), t.obtenerNombre()));
            //}
        }
    }

    public SsUsuario getUser() {
        return user;
    }

    public void setUser(SsUsuario user) {
        this.user = user;
    }

    //obtiene la lista de los tipos de certificados instalados
    private List<TipoCertificado> obtenerTiposCertificados() {
        return logicDelegate.obtenerPlugIn();
    }

    public List<SelectItem> getTipoCertificadosSI() {
        return tipoCertificadosSI;
    }

    public void setTipoCertificadosSI(List<SelectItem> tipoCertificadosSI) {
        this.tipoCertificadosSI = tipoCertificadosSI;
    }

    public List<TipoCertificado> getTiposCertificados() {
        return tiposCertificados;
    }

    public void setTiposCertificados(List<TipoCertificado> tiposCertificados) {
        this.tiposCertificados = tiposCertificados;
    }
    
    public void logout() throws IOException{
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession(false);
        Enumeration<String> attsNames = session.getAttributeNames();
        while(attsNames.hasMoreElements()){
            String att = attsNames.nextElement();
            session.removeAttribute(att);
        }
         session.invalidate();
         FacesContext.getCurrentInstance().getExternalContext().redirect("nuevoCertificadoCliente.xhtml?faces-redirect=true");
    }
}
