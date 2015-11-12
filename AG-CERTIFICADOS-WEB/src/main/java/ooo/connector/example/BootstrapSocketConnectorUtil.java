package ooo.connector.example;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.connection.NoConnectException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.util.List;
import java.util.ResourceBundle;
import ooo.connector.BootstrapSocketConnector;
import ooo.connector.server.OOoServer;

public class BootstrapSocketConnectorUtil {
    
    private static final String OOO_EXEC_FOLDER;
    
    static{
        
        ResourceBundle r = ResourceBundle.getBundle("appconfig");
        OOO_EXEC_FOLDER = r.getString("OOO_EXEC_FOLDER"); 
    }
    

    public static void convertWithStaticConnector(String loadUrl, String storeUrl) throws Exception, IllegalArgumentException, IOException, BootstrapException {
        // Connect to OOo
        XComponentContext remoteContext = BootstrapSocketConnector.bootstrap(OOO_EXEC_FOLDER);
        // Convert text document to PDF
        convert(loadUrl, storeUrl, remoteContext);
    }

    public static void convertWithConnector(String loadUrl, String storeUrl) throws Exception, IllegalArgumentException, IOException, BootstrapException {
        // Create OOo server with additional -nofirststartwizard option
        List oooOptions = OOoServer.getDefaultOOoOptions();
        oooOptions.add("-nofirststartwizard");
        OOoServer oooServer = new OOoServer(OOO_EXEC_FOLDER, oooOptions);
        // Connect to OOo
        BootstrapSocketConnector bootstrapSocketConnector = new BootstrapSocketConnector(oooServer);
        XComponentContext remoteContext = bootstrapSocketConnector.connect();
        // Convert text document to PDF
        convert(loadUrl, storeUrl, remoteContext);
        // Disconnect and terminate OOo server
        bootstrapSocketConnector.disconnect();
    }

    protected static void convert(String loadUrl, String storeUrl, XComponentContext remoteContext) throws IllegalArgumentException, IOException, Exception {
        XComponentLoader xcomponentloader = getComponentLoader(remoteContext);
        PropertyValue[] loaderValues = new PropertyValue[1];
        loaderValues[0] = new PropertyValue();
        loaderValues[0].Name = "Hidden";
        loaderValues[0].Value = new Boolean(true);
        Object objectDocumentToStore = xcomponentloader.loadComponentFromURL(loadUrl, "_blank", 0, loaderValues);
        PropertyValue[] conversionProperties = new PropertyValue[1];
        conversionProperties[0] = new PropertyValue();
        conversionProperties[0].Name = "FilterName";
        conversionProperties[0].Value = "writer_pdf_Export";
        XStorable xstorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,objectDocumentToStore);
        xstorable.storeToURL(storeUrl, conversionProperties);
    }

    private static XComponentLoader getComponentLoader(XComponentContext remoteContext) throws Exception {
        XMultiComponentFactory remoteServiceManager = remoteContext.getServiceManager();
        Object desktop = remoteServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", remoteContext);
        XComponentLoader xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,desktop);
        return xcomponentloader;
    }
}
