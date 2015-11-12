
package org.agesic.certificados.to;

import java.io.Serializable;

/**
 * Clase que representa los errores de validacions de los campos de entrada del certificado.
 * @author sofis solutions
 */
public class ErrorValidacion implements Serializable{
    
    private String campo;
    private String mensaje;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
