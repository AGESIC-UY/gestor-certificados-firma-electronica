
package uy.gub.agesic.firma.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtenerDocumentosFirmadosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtenerDocumentosFirmadosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="respuesta" type="{http://ws.firma.agesic.gub.uy/}dataDocumento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerDocumentosFirmadosResponse", propOrder = {
    "respuesta"
})
public class ObtenerDocumentosFirmadosResponse {

    protected List<DataDocumento> respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the respuesta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRespuesta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataDocumento }
     * 
     * 
     */
    public List<DataDocumento> getRespuesta() {
        if (respuesta == null) {
            respuesta = new ArrayList<DataDocumento>();
        }
        return this.respuesta;
    }

}
