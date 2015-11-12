/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.to;

import java.io.Serializable;
import java.util.Map;



/**
 *
 * @author sofis solutions
 */
public interface TipoCertificado  {
    
    public long obtenerIdentificador();
    public String obtenerNombre();
    public String obtenerDescripcion();
    public void inicializar();
    public String obtenerTemplate(CampoEntrada[] claveValor);
    public Map<String, Serializable> obtenerConfiguracion();
    public CampoEntrada[] obtenerCamposEntradaFormulario();
    public ErrorValidacion[] validarDatosFormulario(CampoEntrada[] datos);
    public CampoEntrada[] obtenerDatos(CampoEntrada[] datos);
    public ErrorValidacion[] validarDatos(CampoEntrada[] datos);
    public boolean requiereFirmaFuncionario();
    public boolean almacenaCertificadoBlob();
    public CertificadoDigital[] obtenerCertificadosDigitalesOrganismo();
    public String[] obtenerDireccionesCorreoElectronico(CampoEntrada[] datos);
    public void finalizar();
    public long obtenerTiempoMantenimientoDias();
    public String obtenerCamposBusqueda(CampoEntrada[] datos);
    public boolean validarUsuario(String userCode);
    
}
