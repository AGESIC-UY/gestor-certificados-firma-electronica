/*
 * 
 * 
 */

package org.agesic.certificados.web.utils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author sofis
 */
public class SofisComboG<T> {

    private HashMap<Integer, T> objetos = new HashMap<Integer, T>();
    private HashMap<Integer, T> objetosComunes = new HashMap<Integer, T>();
    private HashMap<Integer, T> objetosNoComunes = new HashMap<Integer, T>();
    private List<SelectItem> items = new LinkedList<SelectItem>();
    private List<SelectItem> itemsComunes = new LinkedList<SelectItem>();
    private List<SelectItem> itemsNoComunes = new LinkedList<SelectItem>();
    private Integer selected = null;
    private Boolean visible = true;
    private Boolean disabled = false;
    private String propertyName;
    // Esta variable solo se setea cuando se usan los items comunes y no comunes
    private Boolean comunes = null;

    // <editor-fold defaultstate="collapsed" desc="  Constructores ">

    public SofisComboG() {

    }

    public SofisComboG(List<T> objetos) {
        if ((objetos != null) && (objetos.size() > 0)) {
            for (T obj : objetos) {
                add(obj);
            }
            selected = objetos.get(0).hashCode();
        }
    }

    public SofisComboG(List<T> objetos, String propertyName) {
        this.propertyName = propertyName;
        if ((objetos != null) && (objetos.size() > 0)) {
            for (T obj : objetos) {
                add(obj);
            }
            selected = objetos.get(0).hashCode();
        }
        this.objetos = this.objetosNoComunes;
        this.items = this.itemsNoComunes;
    }
    
    public SofisComboG(List<T> objetosComunes, List<T> objetos, String propertyName){
    	this.propertyName = propertyName;
        if ((objetos != null) && (objetos.size() > 0)) {
            for (T obj : objetos) {
                add(obj);
            }
        }
        if ((objetosComunes != null) && (objetosComunes.size() > 0)) {
            for (T obj : objetosComunes) {
                addComun(obj);
            }
            selected = objetos.get(0).hashCode();
        }
        this.objetos = this.objetosComunes;
        this.items = this.itemsComunes;
        SelectItem si = new SelectItem(-2, "Otro...");
        this.items.add(si);
        comunes = true;
    }
    
    public SofisComboG(Set<T> objetos, String propertyName) {
        this.propertyName = propertyName;
        if ((objetos != null) && (objetos.size() > 0)) {
            for (T obj : objetos) {
                add(obj);
            }
            selected = objetos.iterator().next().hashCode();
        }
        this.objetos = this.objetosNoComunes;
        this.items = this.itemsNoComunes;
    }

    // </editor-fold>

    /**
     * Devulve todos los objetos que tiene el combo
     * @return
     */
    public List getAllTs(){
        LinkedList resultado = new LinkedList();
        Collection<T> c = objetos.values();
        for (T o : c){
            resultado.add(o);
        }
        return resultado;
    }
    
    /**
     * Elimina el objeto del combo
     * @param objeto
     */
    public void remove (T objeto){
        int hc = objeto.hashCode();
        SelectItem item = null;
        for (SelectItem si : items){
            if (si.getValue().hashCode() == hc){
                item = si;
            }
        }
        if (item !=null){
            items.remove(item);
            objetos.remove(item.getValue());
        }
    }
    
    public void addComun(T objeto){
    	String name;
        if (propertyName == null){
            name = objeto.toString();
        }else{
            name = getPropertyValue(objeto, propertyName).toString();
        }
        SelectItem selectItem = new SelectItem(objeto.hashCode(), name);
        itemsComunes.add(selectItem);
        objetosComunes.put(objeto.hashCode(), objeto);
    }
    
    
    public void add(T objeto) {
        String name;
        if (propertyName == null){
            name = objeto.toString();
        }else{
            name = getPropertyValue(objeto, propertyName).toString();
        }
        SelectItem selectItem = new SelectItem(objeto.hashCode(), name);
        itemsNoComunes.add(selectItem);
        objetosNoComunes.put(objeto.hashCode(), objeto);
    }
    
    public T getSelectedT() {
        if (selected != null) {
            return objetos.get(selected);
        } else {
            return null;
        }
    }

    public void setSelectedT(T objeto) {
    	if (comunes!=null && comunes == true){
    		if (!this.objetos.containsValue(objeto)){
    			this.items = itemsNoComunes;
    			this.objetos = objetosNoComunes;
    			comunes = false;
    		}
    	}
        if (objeto != null) {
            selected = objeto.hashCode();
        }
    }

    public void addEmptyItem(String name){
        SelectItem si = new SelectItem(-1, name);
        items.add(0, si);
        objetos.put(-1, null);
        selected = -1;
    }
    
    public void addEmptyWithoutSelecting(String name){
        SelectItem si = new SelectItem(-1, name);
        items.add(0, si);
        objetos.put(-1, null);
    }
    
    /**
     * Este evento es solo para cuando hay elementos comunes y no comunes
     * @param event
     */
    public void changeItem(ValueChangeEvent event){
    	this.selected = (Integer) event.getNewValue();
    	if (this.selected == -2){
    		this.items = this.itemsNoComunes;
    		this.objetos = this.objetosNoComunes;
    		comunes = false;
    	}
    }
    

// <editor-fold defaultstate="collapsed" desc="  Funciones Auxiliares ">
    private Object getPropertyValue(T o, String propertyName) {
        if (o == null) {
            return null;
        }
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        Method m = null;
        try {
            m = o.getClass().getMethod(methodName);
        } catch (NoSuchMethodException nsme) {
            methodName = "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            try {
                m = o.getClass().getMethod(methodName);
            } catch (NoSuchMethodException nsme1) {
                nsme.printStackTrace();
                return null;
            }
        } catch (SecurityException se) {
            se.printStackTrace();
            return null;
        }

        Object propertyValue = null;
        try {
            propertyValue = m.invoke(o);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }

        return propertyValue;
    }

    private void setPropertyValue(T o, String propertyName,
                                    Class propertyClass, T value) {

        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        Method m = null;
        try {
            m = o.getClass().getDeclaredMethod(methodName, propertyClass);
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
            return;

        } catch (SecurityException se) {
            se.printStackTrace();
            return;
        }

        try {
            m.invoke(o, value);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="  Getters y setters para la jsf ">
    public List<SelectItem> getItems() {
        return items;
    }

    public void setItems(List<SelectItem> items) {
        this.items = items;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean v) {
        this.visible = v;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    // </editor-fold>
}