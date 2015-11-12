package org.agesic.certificados.web.mb;

import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "menuMB")
@SessionScoped
public class MenuMB implements Serializable {

    private HashMap<String, Boolean> permisos = new HashMap<String, Boolean>();

    /**
     * Creates a new instance of MenuMB
     */
    public MenuMB() {
    }

    @PostConstruct
    public void init() {
    }

    public String fireAction(String codigo) {
        return codigo;
    }

    public HashMap<String, Boolean> getPermisos() {
        return permisos;
    }

    public void setPermisos(HashMap<String, Boolean> permisos) {
        this.permisos = permisos;
    }
}
