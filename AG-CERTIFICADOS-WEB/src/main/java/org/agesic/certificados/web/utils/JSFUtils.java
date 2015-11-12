package org.agesic.certificados.web.utils;

import com.icesoft.faces.context.effects.JavascriptContext;
import com.sun.faces.component.visit.FullVisitContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.icefaces.util.FocusController;

public class JSFUtils {

	public static void mostrarMensaje(String mensaje, FacesMessage.Severity sev) {
		FacesMessage msg = new FacesMessage(sev, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FocusController.setFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.applicationFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.focus(FacesContext.getCurrentInstance(), "messages");
//		JSFUtils.redirect("#messages");
	}

	public static void mostrarMensajes(List<String> mensajes, FacesMessage.Severity sev) {
		for (String mensaje : mensajes) {
			FacesMessage msg = new FacesMessage(sev, mensaje, mensaje);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public static void agregarFatal(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensaje, mensaje));
		FocusController.setFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.applicationFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.focus(FacesContext.getCurrentInstance(), "messages");
//		JSFUtils.redirect("#messages");
	}

	public static void agregarError(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
		FocusController.setFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.applicationFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.focus(FacesContext.getCurrentInstance(), "messages");
//		JSFUtils.redirect("#messages");
	}

	public static void agregarWarn(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
		FocusController.setFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.applicationFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.focus(FacesContext.getCurrentInstance(), "messages");
//		JSFUtils.redirect("#messages");
	}

	public static void agregarInfo(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		FocusController.setFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.applicationFocus(FacesContext.getCurrentInstance(), "messages");
		JavascriptContext.focus(FacesContext.getCurrentInstance(), "messages");
//		JSFUtils.redirect("#messages");
	}

	//Mensajes por id de componente	
	public static void mostrarMensaje(String componentId, String mensaje, FacesMessage.Severity sev) {
		FacesMessage msg = new FacesMessage(sev, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(componentId, msg);
	}

	public static void mostrarMensajes(String componentId, List<String> mensajes, FacesMessage.Severity sev) {
		for (String mensaje : mensajes) {
			FacesMessage msg = new FacesMessage(sev, mensaje, mensaje);
			FacesContext.getCurrentInstance().addMessage(componentId, msg);
		}
	}

	public static void agregarFatal(String componentId, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensaje, mensaje));
	}

	public static void agregarError(String componentId, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
	}

	public static void agregarWarn(String componentId, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
	}

	public static void agregarInfo(String componentId, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
	}

	public static void borrarMensajes() {

//		List<FacesMessage> lstMsg = FacesContext.getCurrentInstance().getMessageList();
//		lstMsg.clear();
	}

	//DOM Utils
	public static UIComponent findComponent(final String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];
		root.visitTree(new FullVisitContext(context), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (component.getId().equals(id)) {
					found[0] = component;
					return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});
		return found[0];
	}

	public static void redirect(String go) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(go);
		} catch (IOException ex) {
			Logger.getLogger(JSFUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void redirectNewWin(String go) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = "window.open('"+ go+ "','');";
		JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), url);
	}

	public static Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public static void closeSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		
	}

}
