package org.agesic.certificados.classfinder;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.agesic.certificados.to.TipoCertificado;

/**
 *
 * @author sofis solutions
 */
public class JavaClassFinder {

    public List<TipoCertificado> obtenerPlugIn() {
        List<Class> pluginClass = new ArrayList();
        ClassLoader l = TipoCertificado.class.getClassLoader();
        java.net.URLClassLoader lr = (java.net.URLClassLoader) l;
        List<URL> pluginURL = new ArrayList();
        for (URL u : lr.getURLs()) {
            if (u.getPath().endsWith("-plugin_ag.jar")) {
                pluginURL.add(u);
            }
        }
        for (URL up : pluginURL) {
            try {
                List<String> classNames = new ArrayList<String>();
                ZipInputStream zip = new ZipInputStream(new FileInputStream(up.getPath()));
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                        StringBuilder className = new StringBuilder();
                        for (String part : entry.getName().split("/")) {
                            if (className.length() != 0) {
                                className.append(".");
                            }
                            className.append(part);
                            if (part.endsWith(".class")) {
                                className.setLength(className.length() - ".class".length());
                            }
                        }
                        classNames.add(className.toString());
                        Class c = l.loadClass(className.toString());

                        if (TipoCertificado.class.isAssignableFrom(c)) {
                            //la clase es un plug in
                            pluginClass.add(c);
                        }

                    }
                }
            } catch (Exception w) {
                w.printStackTrace();
            }
        }
        List<TipoCertificado> pluginObjects = new ArrayList();
        try {
            for (Class plug : pluginClass) {
                TipoCertificado tipoCertificado = (TipoCertificado) plug.newInstance();
                pluginObjects.add(tipoCertificado);;
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        return pluginObjects;
    }
}
