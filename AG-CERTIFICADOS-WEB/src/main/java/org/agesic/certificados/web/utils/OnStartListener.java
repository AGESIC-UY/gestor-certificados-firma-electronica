/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.web.utils;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 * @author usuario
 */
public class OnStartListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("inicia timer expirados...");
        ResourceBundle bundle = ResourceBundle.getBundle("appconfig");
        long periodo = new Long(bundle.getString("timerExpirados"));
        TimerTask task = new CertificadosTimer();
    	Timer timer = new Timer();
    	timer.schedule(task, 1000,periodo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}