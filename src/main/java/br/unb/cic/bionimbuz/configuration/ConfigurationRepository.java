package br.unb.cic.bionimbuz.configuration;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.filechooser.FileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that acts as a repository for all Configuration Files Configuration
 * Files: - config.json - parallel_programs.json - sequential_programs.json
 *
 * @author Vinicius
 */
@Named
public class ConfigurationRepository implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationRepository.class);

    public static final String UPLOADED_FILES_PATH = FileSystemView.getFileSystemView().getHomeDirectory() + "/BionimbuzClient/uploaded-files/";
    private static final String CONFIGURATION_PATH = FileSystemView.getFileSystemView().getHomeDirectory() + "/BionimbuzClient/conf/";
    private static Configuration applicationConfiguration;
    private static Configuration programList;

    /**
     * Called on Application Server start
     * @param servletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        LOGGER.info("Initializing client application. Loading configurations...");

        applicationConfiguration = ConfigurationLoader.readConfiguration(CONFIGURATION_PATH + "config.json",
                ApplicationConfiguration.class);

        programList = ConfigurationLoader.readConfiguration(CONFIGURATION_PATH + "programs.json",
                ProgramList.class);

        LOGGER.info("BioNimbuZ Client Application Configuration loaded: " + applicationConfiguration);
        LOGGER.info("BioNimbuZ Programs loaded: " + programList);
    }

    /**
     * Called on Application Server stop
     * @param servletContext
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println("Destroying Client Application Context...");
    }

    public static ApplicationConfiguration getApplicationConfiguration() {
        return (ApplicationConfiguration) applicationConfiguration;
    }

    public static ProgramList getProgramList() {
        return (ProgramList) programList;
    }

}
