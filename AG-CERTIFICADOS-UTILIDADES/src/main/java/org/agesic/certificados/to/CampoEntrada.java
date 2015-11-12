package org.agesic.certificados.to;

import java.io.Serializable;


/**
 * Clase que representa un campo de entrada de un formulario Web
 * @author sofis solutions
 */
public class CampoEntrada implements Serializable {
    
    public enum CampoEntradaTipo{ Cadena, Texto, Numero, Fecha, Imagen};

    /**
     * El nombre identificador del campo
     */
    private String nombre;
    /**
     * El valor del campo
     */
    private Serializable valor;
    /**
     * Define el tipo de campo
     */
    private CampoEntradaTipo tipo;
    /*
     * La etiqueta asociada al campo a utilizar en el formulario Web de Ingreso de datos
     */
    private String etiqueta;
    
    /**
     * Las opciones validas que puede tomar el campo
     */
    private ClaveValor[] opciones;
    
    /**
     * Si el campo es requerido para marcar en el formulario Web de Ingreso de datos
     */
    private boolean requerido;
    
    /**
     * Como formatear la fecha
     */
    private String fechaPattern;

    public ClaveValor[] getOpciones() {
        return opciones;
    }

    public void setOpciones(ClaveValor[] opciones) {
        this.opciones = opciones;
    }

    
    
    
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRequerido() {
        return requerido;
    }

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    public CampoEntradaTipo getTipo() {
        return tipo;
    }

    public void setTipo(CampoEntradaTipo tipo) {
        this.tipo = tipo;
    }

    public Serializable getValor() {
        return valor;
    }

    public void setValor(Serializable valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        if (nombre != null){
            return tipo.hashCode() +nombre.hashCode();
        }else{
            return tipo.hashCode();
        }
        
    }

    public String getFechaPattern() {
        return fechaPattern;
    }

    public void setFechaPattern(String fechaPattern) {
        this.fechaPattern = fechaPattern;
    }
   
    
    
    
    
}
