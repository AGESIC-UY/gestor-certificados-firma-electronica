/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.web.gen;

import com.icesoft.faces.component.selectinputdate.SelectInputDate;
import java.io.File;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.*;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import org.agesic.certificados.to.CampoEntrada;
import org.agesic.certificados.to.TipoCertificado;

import org.icefaces.ace.component.datetimeentry.DateTimeEntry;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;

/**
 *
 * @author sofis solutions
 */
public class FormularioGenerador {

    public HtmlPanelGroup generarFormulario(TipoCertificado t) {
        HtmlPanelGroup paneGroup = new com.icesoft.faces.component.ext.HtmlPanelGroup();
        paneGroup.setStyleClass("form-horizontal");
        CampoEntrada[] campoEntrada = t.obtenerCamposEntradaFormulario();
        for (CampoEntrada campo : campoEntrada) {
            if (campo != null) {
                UIComponent comp = crearComponente(campo);
                if (comp != null) {
                    paneGroup.getChildren().add(comp);
                }
            }

        }
        return paneGroup;
    }

    public UIComponent crearComponente(CampoEntrada e) {


        HtmlPanelGroup panelGroupCampo = new HtmlPanelGroup();
        panelGroupCampo.setId("campo_" + e.hashCode());
        panelGroupCampo.setStyleClass("form-group");
        HtmlOutputLabel outLabel = new HtmlOutputLabel();
        outLabel.setStyleClass("col-sm-3 control-label");
        outLabel.setValue(e.getEtiqueta());
        panelGroupCampo.getChildren().add(outLabel);


        HtmlPanelGroup panelGroupInput = new HtmlPanelGroup();
        panelGroupInput.setStyleClass("col-sm-9");
        panelGroupInput.setId("input_" + e.hashCode());
        panelGroupCampo.getChildren().add(panelGroupInput);


        switch (e.getTipo()) {
            case Cadena:
                HtmlInputText inpText = new HtmlInputText();
                inpText.setId("c_" + e.hashCode());
                inpText.setStyleClass("form-control");
                panelGroupInput.getChildren().add(inpText);
                break;
            case Texto:
                HtmlInputTextarea inpTextArea = new HtmlInputTextarea();
                inpTextArea.setId("c_" + e.hashCode());
                inpTextArea.setStyleClass("form-control");
                panelGroupInput.getChildren().add(inpTextArea);
                break;
            case Fecha:
                SelectInputDate inpDate = new SelectInputDate();
                inpDate.setId("c_" + e.hashCode());
                inpDate.setRenderAsPopup(true);
                DateTimeConverter converter = getDateTimeConverter("America/Montevideo", "dd/MM/yyyy", null, null);
                if (converter != null) {
                    inpDate.setConverter(converter);
                }
                panelGroupInput.getChildren().add(inpDate);
                break;
            case Numero:
                HtmlInputText inpNum = new HtmlInputText();
                inpNum.setId("c_" + e.hashCode());
                inpNum.setStyleClass("form-control");
                panelGroupInput.getChildren().add(inpNum);
                break;
            case Imagen:
                
                HtmlCommandButton uploadB = new HtmlCommandButton();
                uploadB.setId("c_uploadFile_" + e.hashCode());
                uploadB.setType("submit");
                uploadB.setValue("Subir archivo");
                
                FileEntry inpFile = new FileEntry();
                inpFile.setId("c_" + e.hashCode());
                inpFile.setStyleClass("col-sm-7");
                
                
                
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                MethodExpression me = FacesContext.getCurrentInstance().getApplication().
                getExpressionFactory().
                createMethodExpression(elContext,
                "#{nuevoCertificadoMB.fileEntryListener}", Void.class, new Class[]{FileEntryEvent.class});
                inpFile.setFileEntryListener(me);
                
                panelGroupInput.getChildren().add(inpFile);
                panelGroupInput.getChildren().add(uploadB);
                break;
        }
        return panelGroupCampo;
    }

    /**
     * Crea el dateTimeConverter a partir de la meta informacion
     *
     * @param timeZone
     * @param pattern
     * @param type
     * @param locale
     * @return
     */
    private static DateTimeConverter getDateTimeConverter(String timeZone, String pattern, String type, String locale) {
        if (timeZone != null && !timeZone.equalsIgnoreCase("")
                || pattern != null && !pattern.equalsIgnoreCase("")
                || type != null && !type.equalsIgnoreCase("")
                || locale != null && !locale.equalsIgnoreCase("")) {

            try {
                DateTimeConverter converter = new DateTimeConverter();

                if (timeZone != null && !timeZone.equalsIgnoreCase("")) {
                    TimeZone t = TimeZone.getTimeZone(timeZone);
                    converter.setTimeZone(t);
                }
                if (pattern != null && !pattern.equalsIgnoreCase("")) {
                    converter.setPattern(pattern);
                }
                if (type != null && !type.equalsIgnoreCase("")) {
                    converter.setType(type);
                }
                if (locale != null && !locale.equalsIgnoreCase("")) {
                    Locale l = new Locale(locale);
                    converter.setLocale(l);
                }

                return converter;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public Object obtenerValorCampo(UIComponent compo, CampoEntrada c) {

        switch (c.getTipo()) {
            case Cadena:
                HtmlInputText inpText = (HtmlInputText) compo;
                return inpText.getValue();

            case Texto:
                HtmlInputTextarea inpTextArea = (HtmlInputTextarea) compo;
                return inpTextArea.getValue();

            case Fecha:
                SelectInputDate inpDate = (SelectInputDate) compo;
                return inpDate.getValue();

            case Numero:
                HtmlInputText inpNum = (HtmlInputText) compo;
                return inpNum.getValue();

            case Imagen:
                return null;
        }

        return null;
    }
}
