/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.repositorio.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.agesic.certificados.repositorio.entidades.Certificado;
import org.agesic.certificados.to.TipoCertificado;

/**
 *
 * @author sofis solutions
 */
public class CertificadoDAO {

    static EntityManagerFactory factory = null;
    private EntityManager em;

    static {
        factory = Persistence.createEntityManagerFactory("AG-CERTIFICADOS-PU");
    }

    public CertificadoDAO() {
        em = factory.createEntityManager();
    }

    public void check() {
        if (em == null || !em.isOpen()) {
            em = factory.createEntityManager();
        }
    }

    public Certificado guardar(Certificado cert) throws Exception {
        System.out.println("guardar");
        try {

            check();
            em.getTransaction().begin();
            cert = em.merge(cert);
            em.getTransaction().commit();

            return cert;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }

    }

    public List<Certificado> obtenerCertificados(TipoCertificado certificadoSelecionado, Date fechaDesde, Date fechaHasta, String campoBusqueda, String usuario) {
        String query = "select c from Certificado c";

        boolean where = false;
        if (campoBusqueda != null && !campoBusqueda.equalsIgnoreCase("")) {
            where = true;
            query = query + " where c.campoBusqueda like :campoBus";
        }

        if (fechaDesde != null) {
            if (!where) {
                query = query + " where c.certDate >= :fechaDesde";
            } else {
                query = query + " and c.certDate >= :fechaDesde";
            }
            where = true;
        }

        if (fechaHasta != null) {
            if (!where) {
                query = query + " where c.certDate <= :fechaHasta";
            } else {
                query = query + " and c.certDate <= :fechaHasta";
            }
            where = true;
        }

        Query qCertificado = em.createQuery(query);

        if (campoBusqueda != null && !campoBusqueda.equalsIgnoreCase("")) {
            qCertificado.setParameter("campoBus", "%" + campoBusqueda + "%");
        }

        if (fechaDesde != null) {
            qCertificado.setParameter("fechaDesde", fechaDesde);
        }
        if (fechaHasta != null) {
            qCertificado.setParameter("fechaHasta", fechaHasta);
        }
        return qCertificado.getResultList();
    }

    public Certificado obtenerCertificadoPorCodigoVal(String codigo) {

        String query = "select c from Certificado c where c.certCodigoValidacion =:codigo";
        Query qCertificado = em.createQuery(query);
        qCertificado.setParameter("codigo", codigo);
        List<Certificado> result = qCertificado.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public void borrarCertificadosExpirados() {
        String sqlCount = "SELECT count(*) FROM agesic_certificados.certificado where cert_man_dias is not null and date_add(cert_date, INTERVAL cert_man_dias DAY) < CURDATE()";
        String sql = "SELECT cert_id FROM agesic_certificados.certificado where cert_man_dias is not null and date_add(cert_date, INTERVAL cert_man_dias DAY) < CURDATE()";
        String sqlDelete = "delete FROM agesic_certificados.certificado where cert_id = :id";

        check();
        Query qCertificado = em.createNativeQuery(sql);
        Query qDelete = em.createNativeQuery(sqlDelete);
        List<Integer> listaCertificados = qCertificado.getResultList();


        for (int x = 0; x < listaCertificados.size(); x++) {
            if (x != 0 && x % 100 == 0) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().commit();
                }
            }

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            Integer cert_id = listaCertificados.get(x);
            qDelete.setParameter("id", cert_id);
            qDelete.executeUpdate();
        }
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }

    }
}
