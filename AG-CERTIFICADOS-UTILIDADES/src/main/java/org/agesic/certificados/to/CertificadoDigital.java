
package org.agesic.certificados.to;

import java.io.Serializable;

/**
 *
 * @author sofis solutions
 */
public class CertificadoDigital implements Serializable{

    private String keystore;
    private String alias;
    private String contraseña;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }
    
    
}
