package br.unb.cic.bionimbuz.configuration;

import br.unb.cic.bionimbuz.model.Instance;
import br.unb.cic.bionimbuz.model.PluginService;
import br.unb.cic.bionimbuz.rest.response.GetConfigurationsResponse;
import br.unb.cic.bionimbuz.rest.service.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
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

    private static final BionimbuzClientConfig config = loadConfiguration("conf.yaml");

    public static final String ROOT_PATH = config.getRootPath();

    public static final String TEMPORARY_WORKFLOW_PATH = config.getTemporaryWorkflowFolder();

    public static final String ADDRESS = config.getAddress();

    public static final String BIONIMBUZ_ADDRESS = config.getBionimbuzAddress();

    private static ArrayList<PluginService> supportedServices;

    private static ArrayList<String> references;

    private static ArrayList<String> supportedFormats;
    
//    private static ArrayList<Instance> instanceList;

    /**
     * Called on Application Server start
     *
     * @param servletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        boolean serverOnline = false;
        RestService restService = new RestService();

        LOGGER.info("========================================");
        LOGGER.info("========> Starting client application...");
        LOGGER.info("========================================");

        // Log configurations
        config.log();

        // Send request to the server
        do {
            if (restService.ping(config.getBionimbuzAddress())) {
                GetConfigurationsResponse response;

                try {
                    response = restService.getServices();

                    references = (ArrayList<String>) response.getReferences();
                    supportedServices = (ArrayList<PluginService>) response.getServicesList();
                    supportedFormats = (ArrayList<String>) response.getSupportedFormats();
//                    instanceList= (ArrayList<Instance>) response.getInstancesList();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {

                LOGGER.error("===> BioNimbuZ Core Offline... Trying reconnection <===");

                // Wait 10 seconds to try again
                try {
                    Thread.sleep(10000l);

                } catch (InterruptedException ex) {
                    LOGGER.error("[InterruptedException] " + ex.getMessage());
                }
            }

            serverOnline = (supportedServices != null);
        } while (!serverOnline);

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

    public static ArrayList<PluginService> getSupportedServices() {
        return supportedServices;
    }

    public static ArrayList<String> getReferences() {
        return references;
    }

    public static ArrayList<String> getSupportedFormats() {
        return supportedFormats;
    }
//    public static ArrayList<Instance> getInstanceList(){
//        return instanceList;
//    }

    public static BionimbuzClientConfig getConfig() {
        return config;
    }

    /**
     * Loads web application configuration.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    private static BionimbuzClientConfig loadConfiguration(final String filename) {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        BionimbuzClientConfig config = null;

        try {
            config = mapper.readValue(new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/BionimbuzClient/conf/conf.yaml"), BionimbuzClientConfig.class);
        } catch (IOException ex) {
            LOGGER.error("[IOExceptin] - " + ex.getMessage());
        }

        return config;
    }

}
