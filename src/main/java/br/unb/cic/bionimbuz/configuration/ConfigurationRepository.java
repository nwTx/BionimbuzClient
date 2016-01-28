package br.unb.cic.bionimbuz.configuration;

import br.unb.cic.bionimbuz.model.PluginService;
import br.unb.cic.bionimbuz.rest.service.RestService;
import java.util.ArrayList;
import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.filechooser.FileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that acts as a repository for all Configuration Files.
 *
 * Files: config.json and programs.json
 *
 * @author Vinicius
 */
@Named
public class ConfigurationRepository implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationRepository.class);
    
    // File's path
    private static final String ROOT_PATH = FileSystemView.getFileSystemView().getHomeDirectory() + "/";    
    public static final String TEMPORARY_WORKFLOW_PATH = ROOT_PATH + "BionimbuzClient/temp/";
    public static final String UPLOADED_FILES_PATH = ROOT_PATH + "BionimbuzClient/uploaded-files/";
    private static final String CONFIGURATION_PATH = ROOT_PATH + "BionimbuzClient/conf/";
    
    private static Configuration applicationConfiguration;
    private static ArrayList<PluginService> supportedServices;

    /**
     * Called on Application Server start
     *
     * @param servletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        boolean serverOnline = false;
        int connectTries = 3;
        RestService restService = new RestService();

        LOGGER.info("========================================");
        LOGGER.info("========> Starting client application...");
        LOGGER.info("========================================");

        applicationConfiguration = ConfigurationLoader.readConfiguration(CONFIGURATION_PATH + "config.json",
                ApplicationConfiguration.class);

        // Log configurations
        ((ApplicationConfiguration) applicationConfiguration).log();
        
        // Send request to the server
        while (serverOnline != true) {
            try {
                supportedServices = (ArrayList<PluginService>) restService.getServices();

            } catch (Exception e) {
                if (connectTries <= 0) {
                    LOGGER.error("=====> Server Offline... Terminating Client <=====");
                }

                LOGGER.error("**** Server seems to be offline. " + connectTries-- + " more connect tries ****");
                e.printStackTrace();

                // Wait 5 seconds to try again
                try {
                    Thread.sleep(5000l);

                } catch (InterruptedException ex) {
                    LOGGER.error("[InterruptedException] " + ex.getMessage());
                }
            }

            serverOnline = (supportedServices != null);
        }

        LOGGER.info(supportedServices.size() + " Supported Services");
        LOGGER.info("Supported Services fetched from server: ");

        for (PluginService p : supportedServices) {
            LOGGER.info(" - Program: " + p.getName());
        }

    }

    /**
     * Called on Application Server stop
     *
     * @param servletContext
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
        LOGGER.info("========================================");
        LOGGER.info("========> Stopping client application...");
        LOGGER.info("========================================");
    }

    public static ApplicationConfiguration getApplicationConfiguration() {
        return (ApplicationConfiguration) applicationConfiguration;
    }

    public static ArrayList<PluginService> getSupportedServices() {
        return supportedServices;
    }

}
