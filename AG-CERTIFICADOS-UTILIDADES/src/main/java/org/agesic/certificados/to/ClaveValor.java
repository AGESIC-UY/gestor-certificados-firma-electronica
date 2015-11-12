package org.agesic.certificados.to;

import java.io.Serializable;

/**
 * Clase que representa un par Clave - Valor
 * @author sofis solutions
 */
public class ClaveValor implements Serializable{
    
    private String clave;
    private Serializable valor;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Serializable getValor() {
        return valor;
    }

    public void setValor(Serializable valor) {
        this.valor = valor;
    }
    
    
    
}
