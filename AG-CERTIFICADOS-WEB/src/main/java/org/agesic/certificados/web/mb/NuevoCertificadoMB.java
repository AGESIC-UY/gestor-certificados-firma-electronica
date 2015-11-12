package org.agesic.certificados.web.mb;

import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.sofis.adminis.entities.SsUsuario;
import java.io.File;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.security.Principal;
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
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.agesic.certificados.delegate.LogicDelegate;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.to.CampoEntrada;
import org.agesic.certificados.to.ErrorValidacion;
import org.agesic.certificados.to.TipoCertificado;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.jopendocument.util.FileUtils;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "nuevoCertificadoMB")
@ViewScoped
public class NuevoCertificadoMB implements Serializable {

    LogicDelegate logicDelegate;
    @ManagedProperty("#{inicioMB}")
    private InicioMB inicioMB;
    private HtmlPanelGroup formularioDatos;
    private Long tipoCertificadoSelected;
    TipoCertificado certificadoSelecionado = null;
    private Boolean accionesPosteriores = false;
    Certificado certificado = null;
    CampoEntrada[] campoEntrada = null;
    //applet de firma
    private Boolean popupFirma = false;
    private String transaccionId = null;
    private String certPath = null;
    private String wsdlFirma = "http://localhost:8080/AgesicFirmaWS/AgesicFirma?wsdl";
    private String pathBibliotecasFirma = "http://localhost:8080/applet/";
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public NuevoCertificadoMB() {
    }

    @PostConstruct
    public void init() {
        logicDelegate = new LogicDelegate();
        ResourceBundle bundle = ResourceBundle.getBundle("appconfig");
        wsdlFirma = bundle.getString("wsdlFirma");
        pathBibliotecasFirma = bundle.getString("pathBibliotecasFirma");

        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (p != null) {
            SsUsuario user = new SsUsuario();
            user.setUsuCod(p.getName());
            inicioMB.setUser(user);

            List<SelectItem> tipoCertificadosSI = new ArrayList();
            tipoCertificadosSI.add(new SelectItem(Long.MIN_VALUE, "-----------"));
            for (TipoCertificado t : inicioMB.getTiposCertificados()) {
                if (p != null && t.validarUsuario(user.getUsuCod())) {
                    tipoCertificadosSI.add(new SelectItem(new Long(t.obtenerIdentificador()), t.obtenerNombre()));
                }
            }
        }


    }

    public void limpiar() {
        if (formularioDatos != null) {
            formularioDatos.getChildren().clear();
        }
        tipoCertificadoSelected = null;
        certificadoSelecionado = null;
        accionesPosteriores = false;
        certificado = null;
        campoEntrada = null;
        popupFirma = false;
        transaccionId = null;
        certPath = null;
    }

    public void tipoCertificadoValueChange(ValueChangeEvent event) {
        Long l = (Long) event.getNewValue();
        certificadoSelecionado = null;
        if (l.longValue() != Long.MIN_VALUE) {
            //el certificado seleccionado
            for (TipoCertificado t : inicioMB.getTiposCertificados()) {
                if (t.obtenerIdentificador() == l.longValue()) {
                    certificadoSelecionado = t;
                    break;
                }
            }
        }
        if (certificadoSelecionado != null) {

            //inicializa el plug in
            logicDelegate.inicializarPlugIn(certificadoSelecionado);

            //renderiza el formaulario
            javax.faces.component.html.HtmlPanelGroup panelGroup = logicDelegate.generarFormulario(certificadoSelecionado);
            formularioDatos.getChildren().clear();
            formularioDatos.getChildren().add(panelGroup);
        } else {
            formularioDatos.getChildren().clear();
        }
    }

    public String cancelarFirmaApplet() {
        System.out.println("cancelarFirmaApplet");
        popupFirma = false;
        return null;
    }

    public String imprimir() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (session != null) {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String clave = "sofis" + sdf.format(new Date());
            String docEntrada = req.getContextPath() + "/ImprimirServlet?clave=" + clave;

            if (certificadoSelecionado.almacenaCertificadoBlob()) {
                session.setAttribute("report_collection_data", certificado.getCertDoc());
            } else {
                String path = certificado.getCertPath();
                byte[] b = FileUtils.readBytes(new File(path));
                session.setAttribute("report_collection_data", b);
            }
            JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "window.open('" + docEntrada + "','Descargar');");
        }
        return null;
    }

    public String continuarAccionPosterior() {
        limpiar();
        return null;
    }

    public String enviarPorEmail() {
        try {
            String[] enviarEmaillCorreos = logicDelegate.enviarEmaill(certificadoSelecionado, certificado, campoEntrada);
            String msg = Arrays.toString(enviarEmaillCorreos);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Envió correo de emails a: " + msg, "Envió correo de emails a: " + msg));
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en el servidor: " + ex.getMessage(), "Error en el servidor: " + ex.getMessage()));
        }


        return null;
    }

    public String continuarFirmaApplet() {
        System.out.println("continuarFirmaApplet");
        popupFirma = false;
        if (certificadoSelecionado != null) {
            try {
                //firma el certificado con las firmas del organismo
                certificado = logicDelegate.generarCertificado(certificadoSelecionado, certificado, transaccionId);
                accionesPosteriores = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en el servidor: " + ex.getMessage(), "Error en el servidor: " + ex.getMessage()));
            }
        }
        return null;
    }

    public String continuar() {
        if (certificadoSelecionado != null) {
            //toma los datos del formulario y los setea en el campo valor de la clase CampoEntrada
            HtmlPanelGroup panelGroup = (HtmlPanelGroup) formularioDatos.getChildren().get(0);
            campoEntrada = certificadoSelecionado.obtenerCamposEntradaFormulario();
            for (CampoEntrada c : campoEntrada) {
                if (c.getTipo().equals(CampoEntrada.CampoEntradaTipo.Imagen)) {
                    File f = fileValues.get("c_" + c.hashCode());
                    System.out.println("FILE VALE:" + f);
                    c.setValor((Serializable) f);
                } else {
                    UIComponent compo = panelGroup.findComponent("c_" + c.hashCode());
                    if (compo != null) {
                        Object valor = logicDelegate.obtenerValorCampo(compo, c);
                        c.setValor((Serializable) valor);
                        System.out.println(c.getNombre() + " Valor " + c.getValor());
                    }
                }
            }

            ErrorValidacion[] errores = logicDelegate.validarDatosFormulario(certificadoSelecionado, campoEntrada);

            if (errores != null && errores.length > 0) {
                //despliega los mensajes y no continua
                for (ErrorValidacion error : errores) {
                    if (error != null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error.getMensaje(), error.getMensaje()));
                    }

                }
            } else {

                try {
                    campoEntrada = logicDelegate.obtenerDatos(certificadoSelecionado, campoEntrada);
                    ErrorValidacion[] erroresFinales = logicDelegate.validarDatos(certificadoSelecionado, campoEntrada);
                    if (erroresFinales != null && erroresFinales.length > 0) {
                        //despliega los mensajes y no continua
                        for (ErrorValidacion error : erroresFinales) {
                            if (error != null) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error.getMensaje(), error.getMensaje()));
                                return null;
                            }

                        }
                    }
                    if (certificadoSelecionado.requiereFirmaFuncionario()) {
                        //requiere firma funcionario, genera el certificado con estado 2
                        certificado = logicDelegate.crearCertificado(certificadoSelecionado, campoEntrada, inicioMB.getUser().getUsuCod());
                        certPath = certificado.getCertPath();
                        //solocita al applet la firma
                        transaccionId = logicDelegate.obtenerTransaccionFirmaServer(certPath);
                        System.out.println("TRANSACCION ID " + transaccionId);
                        //renderiza el applet de firma
                        popupFirma = true;
                    } else {
                        //sin firma necesaria se crear el certificado y se firma
                        certificado = logicDelegate.generarCertificado(certificadoSelecionado, campoEntrada, inicioMB.getUser().getUsuCod());
                        accionesPosteriores = true;
                    }

                } catch (Exception w) {
                    w.printStackTrace();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en el servidor: " + w.getMessage(), "Error en el servidor: " + w.getMessage()));
                }
            }
        }
        return null;
    }
    public HashMap<String, File> fileValues = new HashMap();

    public void fileEntryListener(FileEntryEvent event) {
        FileEntry fileEntry = (FileEntry) event.getSource();
        FileEntryResults results = fileEntry.getResults();
        for (FileEntryResults.FileInfo fileInfo : results.getFiles()) {
            if (fileInfo.isSaved()) {
                // Process the file. Only save cloned copies of results or fileInfo
                System.out.println("CLIENT ID:" + fileEntry.getClientId());
                System.out.println("CLIENT ID COMP:" + event.getComponent().getId());
                System.out.println("FILE:" + fileInfo.getFile());
                fileValues.put(event.getComponent().getId(), fileInfo.getFile());
            }
        }
    }

    public Long getTipoCertificadoSelected() {
        return tipoCertificadoSelected;
    }

    public void setTipoCertificadoSelected(Long tipoCertificadoSelected) {
        this.tipoCertificadoSelected = tipoCertificadoSelected;
    }

    public HtmlPanelGroup getFormularioDatos() {
        return formularioDatos;
    }

    public void setFormularioDatos(HtmlPanelGroup formularioDatos) {
        this.formularioDatos = formularioDatos;
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

    public Boolean getPopupFirma() {
        return popupFirma;
    }

    public void setPopupFirma(Boolean popupFirma) {
        this.popupFirma = popupFirma;
    }

    public String getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(String transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getPathBibliotecasFirma() {
        return pathBibliotecasFirma;
    }

    public void setPathBibliotecasFirma(String pathBibliotecasFirma) {
        this.pathBibliotecasFirma = pathBibliotecasFirma;
    }

    public String getWsdlFirma() {
        return wsdlFirma;
    }

    public void setWsdlFirma(String wsdlFirma) {
        this.wsdlFirma = wsdlFirma;
    }

    public Boolean getAccionesPosteriores() {
        return accionesPosteriores;
    }

    public void setAccionesPosteriores(Boolean accionesPosteriores) {
        this.accionesPosteriores = accionesPosteriores;
    }
}
