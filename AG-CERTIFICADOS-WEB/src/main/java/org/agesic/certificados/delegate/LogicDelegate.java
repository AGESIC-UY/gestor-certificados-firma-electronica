/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.delegate;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.namespace.QName;
import org.agesic.certificados.classfinder.JavaClassFinder;
import org.agesic.certificados.repositorio.dao.CertificadoDAO;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.template.OpenOfficeTemplate;
import org.agesic.certificados.to.*;
import org.agesic.certificados.web.gen.FormularioGenerador;
import org.agesic.firma.utils.Constantes;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import uy.gub.agesic.firma.FirmaAgesic;
import uy.gub.agesic.firma.cliente.pdf.PDFSignatureImpl;

/**
 *
 * @author sofis solutions
 */
public class LogicDelegate implements Serializable {

    /**
     * Obtiene la lista de plugIn disponibles
     *
     * @return
     */
    public List<TipoCertificado> obtenerPlugIn() {
        JavaClassFinder j = new JavaClassFinder();
        return j.obtenerPlugIn();
    }
    
   

    /**
     * Inicializa el plug in
     *
     * @param t
     */
    public void inicializarPlugIn(TipoCertificado t) {
        t.inicializar();
    }
    
    /**
     * Valida los datos del formulario generado
     */
    public ErrorValidacion[] validarDatosFormulario(TipoCertificado t, CampoEntrada[] datos) {
        return t.validarDatosFormulario(datos);
    }

    /**
     * Valida los datos del formulario generado
     */
    public ErrorValidacion[] validarDatos(TipoCertificado t, CampoEntrada[] datos) {
        return t.validarDatos(datos);
    }

    /**
     * Genera el formulario web para colectar los datos del certificado a
     * generar
     *
     * @param t
     * @return
     */
    public HtmlPanelGroup generarFormulario(TipoCertificado t) {
        FormularioGenerador generador = new FormularioGenerador();
        return generador.generarFormulario(t);
    }

    /*
     * Dado un campo de entrada y el componente JSF obtiene el valor del mismo
     */
    public Object obtenerValorCampo(UIComponent compo, CampoEntrada c) {
        FormularioGenerador generador = new FormularioGenerador();
        return generador.obtenerValorCampo(compo, c);
    }

    /**
     * Obtiene del servidor de firma el id de la transaccion para pasar al
     * Applet de Firma
     *
     * @param certificado el certificado a firmar
     * @return el id de la transaccion generado por firma Server
     * @throws Exception
     */
    public String obtenerTransaccionFirmaServer(String certPath) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("appconfig");
        URL wsdlURL = new URL(bundle.getString("wsdlFirmaServer"));
        System.out.println("wsdlFirm:"+bundle.getString("wsdlFirmaServer"));
        uy.gub.agesic.firma.ws.AgesicFirmaServerWS service = new uy.gub.agesic.firma.ws.AgesicFirmaServerWS(wsdlURL,new QName("http://ws.firma.agesic.gub.uy/", "AgesicFirmaServerWS"));
        uy.gub.agesic.firma.ws.AgesicFirmaServer port = service.getAgesicFirmaServerWSPort();
        java.lang.String tipoFirma = Constantes.TIPO_FIRMA_PDF;
        java.util.List<byte[]> documentos = new ArrayList();
        byte[] certificado = FileUtils.readFileToByteArray(new File(certPath));
        documentos.add(certificado);
        java.lang.String result = port.firmarDocumentos(tipoFirma, documentos);
        return result;
    }

    public CampoEntrada[] obtenerDatos(TipoCertificado tipoCertificado, CampoEntrada[] datosEntrada) {
        CampoEntrada[] datosTotales = tipoCertificado.obtenerDatos(datosEntrada);
        if (datosTotales == null || datosTotales.length == 0) {
            datosTotales = datosEntrada;
        }
        return datosTotales;
    }

    /**
     * Crea el certificado, genera el template y retorna el certificado sin almanacer en base
     * bytes
     *
     * @param tipoCertificadoSelected
     * @param campoEntrada
     */
    public Certificado crearCertificado(TipoCertificado tipoCertificado, CampoEntrada[] datosTotales, String userId) throws Exception {
        //Obtiene la configuracion
        Map<String, Serializable> configuracion = tipoCertificado.obtenerConfiguracion();
        //obtiene el path del template
        String templatePath = tipoCertificado.obtenerTemplate(datosTotales);
        System.out.println("TEMLATE1 " + templatePath);
        Date dNow = new Date();
        String name = RandomStringUtils.randomAlphanumeric(8);
        String codigoValidacion = dNow.getTime()+ "-"+name;
        //genera el certificado a partir del template.
        OpenOfficeTemplate oTemplate = new OpenOfficeTemplate();
        String certPath = oTemplate.generarCertificadoDesdeTemplate(templatePath, datosTotales,codigoValidacion, configuracion);
        byte[] certificadoBytes = FileUtils.readFileToByteArray(new File(certPath));
        //crea el certificado
        Certificado cert = crearCertificado(datosTotales,certificadoBytes, certPath, tipoCertificado, codigoValidacion, userId);
        System.out.println("TEMLATE2 " + certPath);
        return cert;
    }

    /**
     * Crea y Genera el certificado lo almacena en base y le realiza las ultimas firmas necesarias
     *
     * @param tipoCertificadoSelected
     * @param campoEntrada
     */
    public Certificado generarCertificado(TipoCertificado tipoCertificado, CampoEntrada[] datosEntrada, String userId) throws Exception {

        FirmaAgesic agesicFirma = new FirmaAgesic();
        HashMap<String, String> opcionesFirma = new HashMap();
        //crea el certificado
        Certificado cert = crearCertificado(tipoCertificado, datosEntrada, userId);
        byte[] certificadoBytes = FileUtils.readFileToByteArray(new File(cert.getCertPath()));
        //obtiene los certificados digitales
        CertificadoDigital[] certificadoDigital = tipoCertificado.obtenerCertificadosDigitalesOrganismo();
        if (certificadoDigital != null) {
            for (CertificadoDigital cDigital : certificadoDigital) {
                String keyStore = cDigital.getKeystore();
                String password = cDigital.getContraseña();
                String alias = cDigital.getAlias();
                if (alias != null && !alias.equals("")){
                    opcionesFirma.put("alias",alias);
                }
                int idOpcion = agesicFirma.inicializarP12(keyStore, password, opcionesFirma);
                certificadoBytes = agesicFirma.firmaDocumento(idOpcion, uy.gub.agesic.firma.TipoDocumento.PDF, certificadoBytes, opcionesFirma);
            }
        }
        //almacena el certificado emitido en el repositorio de certificados.
        cert = guardarCertificado(tipoCertificado,cert);
        return cert;
    }

    /**
     * Genera el certificado lo almacena en base y le realiza las ultimas firmas necesarias
     * @param tipoCertificado
     * @param certificado
     * @param tokenId
     * @return
     * @throws Exception 
     */
    public Certificado generarCertificado(TipoCertificado tipoCertificado,Certificado certificado, String tokenId) throws Exception {
        FirmaAgesic agesicFirma = new FirmaAgesic();
        HashMap<String, String> opcionesFirma = new HashMap();
        
        String certPath = certificado.getCertPath();
        
        ResourceBundle bundle = ResourceBundle.getBundle("appconfig");
        URL wsdlURL = new URL(bundle.getString("wsdlFirmaServer"));
        uy.gub.agesic.firma.ws.AgesicFirmaServerWS service = new uy.gub.agesic.firma.ws.AgesicFirmaServerWS(wsdlURL,new QName("http://ws.firma.agesic.gub.uy/", "AgesicFirmaServerWS"));
        uy.gub.agesic.firma.ws.AgesicFirmaServer port  = service.getAgesicFirmaServerWSPort();
        byte[] certificadoBytes = port.obtenerDocumentosFirmados(tokenId).get(0).getDoc().get(0);

        FileUtils.writeByteArrayToFile(new File(certPath), certificadoBytes);

        //obtiene los certificados digitales del organismo y firma el pdf con estos certificados
        CertificadoDigital[] certificadoDigital = tipoCertificado.obtenerCertificadosDigitalesOrganismo();
        if (certificadoDigital != null) {

            for (CertificadoDigital cDigital : certificadoDigital) {
                String keyStore = cDigital.getKeystore();
                String password = cDigital.getContraseña();
                String alias = cDigital.getAlias();
                int idOpcion = agesicFirma.inicializarP12(keyStore, password, opcionesFirma);
                certificadoBytes = agesicFirma.firmaDocumento(idOpcion, uy.gub.agesic.firma.TipoDocumento.PDF, certificadoBytes, opcionesFirma);
            }
        }
        
        //el certificado firmado se almacena como bytes en la base
        certificado.setCertDoc(certificadoBytes);
        //almacena el certificado emitido en el repositorio de certificados.
        Certificado cert = guardarCertificado(tipoCertificado,certificado);
        
        return cert;
    }
    

    public Certificado validarCertificado(String codigo) throws Exception {
        if (codigo == null || codigo.equalsIgnoreCase("")){
            return null;
        }
        CertificadoDAO dao = new CertificadoDAO();
        Certificado cert  = dao.obtenerCertificadoPorCodigoVal(codigo);
       return cert;
    }
    
    public Certificado guardarCertificado(TipoCertificado t,Certificado cert) throws Exception {
        //si no debe almacenar el certificado en base
        if (!t.almacenaCertificadoBlob()){
            cert.setCertDoc(null);
        }
        CertificadoDAO dao = new CertificadoDAO();
        cert = dao.guardar(cert);
        return cert;
    }
    
    private Certificado crearCertificado(CampoEntrada[] datosTotales ,byte[] certificado, String certPath, TipoCertificado tipoCertificado, String codigoValidacion, String userId) throws Exception {
        Certificado cert = new Certificado();
        cert.setCertDoc(certificado);
        cert.setCertPluginNombre(tipoCertificado.obtenerNombre());
        cert.setCertPluginId(tipoCertificado.obtenerIdentificador());
        cert.setCertPluginBlob(tipoCertificado.almacenaCertificadoBlob());
        cert.setCertDate(new Date());
        cert.setCertEstado(1);
        cert.setCertUserId(userId);
        cert.setCertPath(certPath);
        cert.setCertCodigoValidacion(codigoValidacion);
        cert.setCampoBusqueda(tipoCertificado.obtenerCamposBusqueda(datosTotales));
        if (tipoCertificado.obtenerTiempoMantenimientoDias() < 0){
            //menor que 0 se mantiene siempre el certificado
            cert.setCertManDias(null);
        }else{
            cert.setCertManDias(tipoCertificado.obtenerTiempoMantenimientoDias());
        }
        
        return cert;
    }
    
    public Certificado actualizarEstadoCertificado(Certificado cert, Integer estadoId) throws Exception {
        CertificadoDAO dao = new CertificadoDAO();
        cert.setCertEstado(estadoId);
        cert = dao.guardar(cert);
        return cert;
    }

    public String[] enviarEmaill(TipoCertificado tipoCertificado, Certificado certificado, CampoEntrada[] campoEntrada) throws Exception {
        String[] corresEnviar = tipoCertificado.obtenerDireccionesCorreoElectronico(campoEntrada);
        Map<String, Serializable> configuracion = tipoCertificado.obtenerConfiguracion();
        String from = "" + configuracion.get(ConfiguracionClaves.MAIL_FROM);
        String subject = "" + configuracion.get(ConfiguracionClaves.MAIL_SUBJECT);
        String contenido = "" + configuracion.get(ConfiguracionClaves.MAIL_BODY);
        String filePath = "";

        filePath = certificado.getCertPath();
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        Session session = (Session) envCtx.lookup("mail/Session");
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress to[] = new InternetAddress[corresEnviar.length];
        int x = 0;
        for (String cenviar : corresEnviar) {
            to[x] = new InternetAddress(cenviar);
            x++;
        }
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(contenido, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(filePath);
        multipart.addBodyPart(attachPart);

        msg.setContent(multipart);


        Transport.send(msg);

        return corresEnviar;

    }

    public List<Certificado> obtenerCertificados(TipoCertificado certificadoSelecionado, Date fechaDesde, Date fechaHasta,String campoBusqueda, String usuario) {
        CertificadoDAO dao = new CertificadoDAO();
        return dao.obtenerCertificados(certificadoSelecionado,fechaDesde, fechaHasta,campoBusqueda, usuario);
    }
    
    
    public void borrarCertificadosExpirados(){
        CertificadoDAO dao = new CertificadoDAO();
        System.out.println("EJECUTA");
        dao.borrarCertificadosExpirados();
    }
   
}
