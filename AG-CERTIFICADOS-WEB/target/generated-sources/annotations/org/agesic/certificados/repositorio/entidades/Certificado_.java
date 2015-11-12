package org.agesic.certificados.repositorio.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-23T13:32:13")
@StaticMetamodel(Certificado.class)
public class Certificado_ { 

    public static volatile SingularAttribute<Certificado, Long> certManDias;
    public static volatile SingularAttribute<Certificado, String> certUserId;
    public static volatile SingularAttribute<Certificado, String> campoBusqueda;
    public static volatile SingularAttribute<Certificado, byte[]> certDoc;
    public static volatile SingularAttribute<Certificado, Date> certDate;
    public static volatile SingularAttribute<Certificado, Integer> certId;
    public static volatile SingularAttribute<Certificado, Boolean> certPluginBlob;
    public static volatile SingularAttribute<Certificado, String> certPath;
    public static volatile SingularAttribute<Certificado, Long> certPluginId;
    public static volatile SingularAttribute<Certificado, String> certCodigoValidacion;
    public static volatile SingularAttribute<Certificado, Integer> certEstado;
    public static volatile SingularAttribute<Certificado, String> certPluginNombre;

}