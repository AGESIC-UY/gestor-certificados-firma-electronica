package org.agesic.certificados.to;

import java.io.Serializable;

/**
 * La plantilla Open Office del certificado
 * @author sofis solutions
 */
public class PlantillaCertificado implements Serializable{
    
    private String nombre;
    private Serializable documento;

    public Serializable getDocumento() {
        return documento;
    }

    public void setDocumento(Serializable documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
