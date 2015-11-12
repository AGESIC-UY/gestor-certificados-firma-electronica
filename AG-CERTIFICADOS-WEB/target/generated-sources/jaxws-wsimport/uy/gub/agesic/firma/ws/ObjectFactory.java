
package uy.gub.agesic.firma.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the uy.gub.agesic.firma.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FirmarDocumentos_QNAME = new QName("http://ws.firma.agesic.gub.uy/", "firmarDocumentos");
    private final static QName _FirmarDocumentosResponse_QNAME = new QName("http://ws.firma.agesic.gub.uy/", "firmarDocumentosResponse");
    private final static QName _ObtenerDocumentosFirmados_QNAME = new QName("http://ws.firma.agesic.gub.uy/", "obtenerDocumentosFirmados");
    private final static QName _ObtenerDocumentosFirmadosResponse_QNAME = new QName("http://ws.firma.agesic.gub.uy/", "obtenerDocumentosFirmadosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uy.gub.agesic.firma.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FirmarDocumentos }
     * 
     */
    public FirmarDocumentos createFirmarDocumentos() {
        return new FirmarDocumentos();
    }

    /**
     * Create an instance of {@link DataDocumento }
     * 
     */
    public DataDocumento createDataDocumento() {
        return new DataDocumento();
    }

    /**
     * Create an instance of {@link ObtenerDocumentosFirmadosResponse }
     * 
     */
    public ObtenerDocumentosFirmadosResponse createObtenerDocumentosFirmadosResponse() {
        return new ObtenerDocumentosFirmadosResponse();
    }

    /**
     * Create an instance of {@link ObtenerDocumentosFirmados }
     * 
     */
    public ObtenerDocumentosFirmados createObtenerDocumentosFirmados() {
        return new ObtenerDocumentosFirmados();
    }

    /**
     * Create an instance of {@link FirmarDocumentosResponse }
     * 
     */
    public FirmarDocumentosResponse createFirmarDocumentosResponse() {
        return new FirmarDocumentosResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirmarDocumentos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.firma.agesic.gub.uy/", name = "firmarDocumentos")
    public JAXBElement<FirmarDocumentos> createFirmarDocumentos(FirmarDocumentos value) {
        return new JAXBElement<FirmarDocumentos>(_FirmarDocumentos_QNAME, FirmarDocumentos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirmarDocumentosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.firma.agesic.gub.uy/", name = "firmarDocumentosResponse")
    public JAXBElement<FirmarDocumentosResponse> createFirmarDocumentosResponse(FirmarDocumentosResponse value) {
        return new JAXBElement<FirmarDocumentosResponse>(_FirmarDocumentosResponse_QNAME, FirmarDocumentosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDocumentosFirmados }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.firma.agesic.gub.uy/", name = "obtenerDocumentosFirmados")
    public JAXBElement<ObtenerDocumentosFirmados> createObtenerDocumentosFirmados(ObtenerDocumentosFirmados value) {
        return new JAXBElement<ObtenerDocumentosFirmados>(_ObtenerDocumentosFirmados_QNAME, ObtenerDocumentosFirmados.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDocumentosFirmadosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.firma.agesic.gub.uy/", name = "obtenerDocumentosFirmadosResponse")
    public JAXBElement<ObtenerDocumentosFirmadosResponse> createObtenerDocumentosFirmadosResponse(ObtenerDocumentosFirmadosResponse value) {
        return new JAXBElement<ObtenerDocumentosFirmadosResponse>(_ObtenerDocumentosFirmadosResponse_QNAME, ObtenerDocumentosFirmadosResponse.class, null, value);
    }

}
