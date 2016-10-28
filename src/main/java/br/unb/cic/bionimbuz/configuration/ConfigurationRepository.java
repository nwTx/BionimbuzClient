package br.unb.cic.bionimbuz.configuration;

import br.unb.cic.bionimbuz.model.Instance;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import br.unb.cic.bionimbuz.model.PluginService;
import br.unb.cic.bionimbuz.rest.response.GetConfigurationsResponse;
import br.unb.cic.bionimbuz.rest.service.RestService;

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
    private static ArrayList<PluginService> supportedServices;
    private static ArrayList<String> references;
    private static ArrayList<String> supportedFormats;
    private static ArrayList<Instance> instances;
    public static String BIONIMBUZ_ADDRESS;
    public static String TEMPORARY_WORKFLOW_PATH;
    
    /**
     * Called on Application Server start
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOGGER.info("========================================");
        LOGGER.info("========> Starting client application...");
        LOGGER.info("========================================");
        boolean serverOnline = false;
        getConfig().log();
        
        // Send request to the server
        while (!serverOnline) {
            if (RestService.ping()) {
                final RestService restService = new RestService();
                GetConfigurationsResponse response;
                try {
                    response = restService.getServices();
                    references = (ArrayList<String>) response.getReferences();
                    supportedServices = (ArrayList<PluginService>) response.getServicesList();
                    supportedFormats = (ArrayList<String>) response.getSupportedFormats();
                    instances= (ArrayList<Instance>) response.getInstances();
                } catch (final Exception ex) {
                    LOGGER.error("Error trying to get the supported services list", ex);
                }
            } else {
                LOGGER.error("===> BioNimbuZ Core Offline... Trying reconnection <===");
                try {
                    // Wait 30 seconds to try again
                    Thread.sleep(30000l);
                } catch (final InterruptedException ex) {
                    LOGGER.error("[InterruptedException] " + ex.getMessage());
                }
            }
            serverOnline = supportedServices != null;
        }
        LOGGER.info(supportedServices.size() + " Supported Services");
        LOGGER.info("Supported Services fetched from server: ");
        for (final PluginService p : supportedServices) {
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
    
    public static BionimbuzClientConfig getConfig() {
        return loadConfiguration();
    }
    
    public static ArrayList<Instance> getInstances() {
        return instances;
    }

    public static void setInstances(ArrayList<Instance> aInstances) {
        instances = aInstances;
    }
    
    /**
     * Loads web application configuration.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    private static BionimbuzClientConfig loadConfiguration() {
        BionimbuzClientConfig config = null;
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final String defaultConfigPathname = System.getProperty("user.home") + "/BionimbuzClient/conf/conf.yaml";
            config = mapper.readValue(new File(defaultConfigPathname), BionimbuzClientConfig.class);
        } catch (final IOException ex) {
            LOGGER.error("[IOException] - " + ex.getMessage());
        }
        
        return config;
    }
    
}
