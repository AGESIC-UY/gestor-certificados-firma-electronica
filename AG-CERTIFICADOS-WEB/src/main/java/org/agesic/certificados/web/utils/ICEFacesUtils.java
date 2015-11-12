/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.agesic.certificados.web.utils;

import javax.faces.context.FacesContext;
import org.icefaces.util.FocusController;

/**
 *
 * @author Bruno
 */
public class ICEFacesUtils {
	
	
	
	private static FacesContext getContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static String getFocus(){
		String f = FocusController.getFocus(ICEFacesUtils.getContext());
//		System.out.println("GET FOCUS: " +f);
		return f;
	}
	
	public static void setFocus(String id){
		FocusController.setFocus(ICEFacesUtils.getContext(), id);		

	}
	
}
