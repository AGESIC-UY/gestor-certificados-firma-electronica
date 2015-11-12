/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.template;

import com.itextpdf.text.pdf.qrcode.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.Base64;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
//import fr.opensagres.xdocreport.converter.ConverterRegistry;
//import fr.opensagres.xdocreport.converter.ConverterTypeTo;
//import fr.opensagres.xdocreport.converter.IConverter;
//import fr.opensagres.xdocreport.converter.Options;
//import fr.opensagres.xdocreport.core.document.DocumentKind;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import ooo.connector.example.BootstrapSocketConnectorUtil;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.to.CampoEntrada;
import org.agesic.certificados.to.ClaveValor;
import org.agesic.certificados.to.ConfiguracionClaves;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.icefaces.apache.commons.io.IOUtils;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.text.Heading;
import org.jopendocument.model.OpenDocument;
import org.jopendocument.renderer.ODTRenderer;

/**
 *
 * @author sofis solutions
 */
public class OpenOfficeTemplate {

    public String generarCertificadoDesdeTemplate(String templateFile, CampoEntrada[] camposEntrada, String codigoValidacion, Map<String, Serializable> configuracion) throws Exception {
        JavaScriptFileTemplate template = new JavaScriptFileTemplate(templateFile);

        //los campos normales
        for (CampoEntrada cEntrada : camposEntrada) {
            try {
                if (cEntrada.getTipo().equals(CampoEntrada.CampoEntradaTipo.Imagen)) {
                    //nothing to do
                } else if (cEntrada.getTipo().equals(CampoEntrada.CampoEntradaTipo.Fecha)) {

                    String datePattern = cEntrada.getFechaPattern();
                    if (datePattern != null && !datePattern.equalsIgnoreCase("")) {
                        SimpleDateFormat f = new SimpleDateFormat(datePattern);
                        template.setField(cEntrada.getNombre(), f.format(cEntrada.getValor()));
                    } else {
                        template.setField(cEntrada.getNombre(), cEntrada.getValor());
                    }
                } else {
                    template.setField(cEntrada.getNombre(), cEntrada.getValor());
                }
            } catch (Exception ex) {
            }
        }



        //si aplica los campos de fecha y hora
        String fechaHoraPattern = "dd/MM/yyyy HH:mm:ss";
        String fechaHoraFieldName = "FECHAHORA_CODE";
        try {
            if (configuracion.containsKey(ConfiguracionClaves.TEMPLATE_FECHAHORA_CODE)) {
                fechaHoraFieldName = configuracion.get(ConfiguracionClaves.TEMPLATE_FECHAHORA_CODE) + "";
            }
            if (configuracion.containsKey(ConfiguracionClaves.TEMPLATE_FECHAHORA_PATTERN)) {
                fechaHoraPattern = configuracion.get(ConfiguracionClaves.TEMPLATE_FECHAHORA_PATTERN) + "";
            }
            SimpleDateFormat f = new SimpleDateFormat(fechaHoraPattern);
            template.setField(fechaHoraFieldName, f.format(new Date()));
        } catch (Exception w) {
        }



        String outpudir = null;
        if (configuracion.containsKey(ConfiguracionClaves.CERTIFICADO_OUTPUT_DIR)) {
            outpudir = "" + configuracion.get(ConfiguracionClaves.CERTIFICADO_OUTPUT_DIR);
        } else {
            outpudir = System.getProperty("java.io.tmpdir");
        }
        if (!(outpudir.endsWith("/") || outpudir.endsWith("\\"))) {
            outpudir = outpudir + File.separator;
        }

        System.out.println("outpudir:" + outpudir);

        Date dNow = new Date();
        String name = RandomStringUtils.randomAlphanumeric(8);
        String outFilePathOdt = outpudir + name + "_" + dNow.getTime() + ".odt";
        String outFilePathPdf = outpudir + name + "_1_" + dNow.getTime() + ".pdf";


        System.out.println("outFilePathOdt:" + outFilePathOdt);
        System.out.println("outFilePathPdf:" + outFilePathPdf);

        File outFileOdt = new File(outFilePathOdt);
        File outFilePdf = new File(outFilePathPdf);

        System.out.println("saveToPackageAs1:" + outFileOdt);
        template.createDocument().saveAs(outFileOdt);
        ODSingleXMLDocument doc = template.createDocument();

        //los campos de tipo imagen
        for (CampoEntrada cEntrada : camposEntrada) {
            try {
                if (cEntrada.getTipo().equals(CampoEntrada.CampoEntradaTipo.Imagen)) {
                    try {
                        org.jdom.Element elemento = (org.jdom.Element) doc.getDescendantByName("draw:frame", cEntrada.getNombre()).getChildren().get(0);
                        elemento.getAttributes().clear();
                        String base64 = Base64.encodeObject(cEntrada.getValor());
                        elemento.addContent(new org.jdom.Element("binary-data", Namespace.getNamespace("office", "urn:oasis:names:tc:opendocument:xmlns:office:1.0")).setText(base64));
                    } catch (Exception w) {
                    }
                } else {
                    //nothing
                }
            } catch (Exception ex) {
            }
        }

        String urlValidacionContextRoot = "";
        if (configuracion.containsKey(ConfiguracionClaves.CONTEXT_ROOT_VAL)) {
            urlValidacionContextRoot = "" + configuracion.get(ConfiguracionClaves.CONTEXT_ROOT_VAL);
        } else {
        }
        
        String urlValFinal = urlValidacionContextRoot + "?codigo=" + codigoValidacion;
        //crea el QR
        BarcodeQRCode qrcode = new BarcodeQRCode(urlValFinal, 300, 300, null);
        Image qrcodeImage = qrcode.getImage();

        java.awt.Image awtImage = qrcode.createAwtImage(Color.BLACK, Color.WHITE);
        BufferedImage bImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bImage.createGraphics();
        g.drawImage(awtImage, 0, 0, null);
        g.dispose();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", byteOutput);
        String qr64 = Base64.encodeBytes(byteOutput.toByteArray());

        try {
            String qrCodeFieldName = "QR_CODE";
            if (configuracion.containsKey(ConfiguracionClaves.TEMPLATE_QR_CODE)) {
                qrCodeFieldName = configuracion.get(ConfiguracionClaves.TEMPLATE_QR_CODE) + "";
            }
            //el codigo QR
            org.jdom.Element elemento = (org.jdom.Element) doc.getDescendantByName("draw:frame", qrCodeFieldName).getChildren().get(0);
            elemento.getAttributes().clear();
            elemento.addContent(new org.jdom.Element("binary-data", Namespace.getNamespace("office", "urn:oasis:names:tc:opendocument:xmlns:office:1.0")).setText(qr64));
        } catch (Exception w) {
            w.printStackTrace();
        }
        
        
        //LA URL para el template
        String urlFieldName = "URL_CODE";
        try {
            if (configuracion.containsKey(ConfiguracionClaves.TEMPLATE_URL_CODE)) {
                urlFieldName = configuracion.get(ConfiguracionClaves.TEMPLATE_URL_CODE) + "";
            }
            template.setField(urlFieldName, urlValFinal);
        } catch (Exception w) {
        }



        //crea el documento ODT
        outFileOdt = doc.saveToPackageAs(outFileOdt);
        System.out.println("saveToPackageAs2:" + outFileOdt);

        //convierte el template a PDF usando OpenOffice
        String url1 = outFileOdt.toURI().toURL().toString().replaceAll("file:/", "file:///");
        String url2 = outFilePdf.toURI().toURL().toString().replaceAll("file:/", "file:///");
        BootstrapSocketConnectorUtil.convertWithStaticConnector(url1, url2);



        return outFilePdf.getPath();
    }

    public static void main1(String... s) {


        File f = new File("C:\\desarrollo\\netbeans7.1.2\\AGESIC_CERTIFICADOS\\AG-CERTIFICADOS-WEB\\lib\\lib");
        File[] files = f.listFiles();
        for (File ff : files) {
            String ns = ff.getName();
            String dep = "<dependency>\n"
                    + "<groupId>org.agesic.firma.applet</groupId>\n"
                    + "<artifactId>firmaApi" + ns + "</artifactId>\n"
                    + "<version>2.2</version>\n"
                    + "<scope>system</scope>\n"
                    + "<systemPath>${basedir}/lib/lib/" + ns + "</systemPath>\n"
                    + "</dependency>\n";
            System.out.println(dep);
        }


    }

    
    private static Map<String, String> createMap(String n, String min, String max) {
        final Map<String, String> res = new HashMap<String, String>();
        res.put("name", n);
        res.put("min", min);
        res.put("max", max);
        return res;
    }

    
}