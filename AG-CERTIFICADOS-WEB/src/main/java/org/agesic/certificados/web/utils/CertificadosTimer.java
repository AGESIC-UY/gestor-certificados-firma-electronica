/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.web.utils;

import java.util.TimerTask;
import org.agesic.certificados.delegate.LogicDelegate;

/**
 *
 * @author sofis solutions
 */
public class CertificadosTimer extends TimerTask{

    @Override
    public void run() {
        //obtiene la lista de certificados emitidos y para cada certificado ejecuta el delete si corresponde.
        LogicDelegate delegate = new LogicDelegate();
        delegate.borrarCertificadosExpirados();
        
    }
    
    
    
}
