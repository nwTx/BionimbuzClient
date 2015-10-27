package br.unb.cic.bionimbuz.configuration;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.filechooser.FileSystemView;

/**
 * Class that acts as a repository for all client Configuration File by using Dependency Injection
 * 
 * +---------------------------------------------------------------------+ |
 * Config File 					| 	Content	 			|
 * +---------------------------------------------------------------------+ | |
 * Contains informations about: | 						|	 	| - Uploaded files direcotory | |
 * applicationConfiguration 	| - Bionimbuz Server IP | | | - Application IP |
 * +---------------------------------------------------------------------+ | |
 * Contains informations about the | | programConfiguration | programs stored at
 * BioNimbuZ | | | server |
 * +---------------------------------------------------------------------+
 * 
 * @author Vinicius
 */
@Named
public class ConfigurationRepository implements ServletContextListener {
	public  static final String UPLOADED_FILES_DIRECTORY = FileSystemView.getFileSystemView().getHomeDirectory() + "/BionimbuzClient/uploaded-files/";
	private static final String CONFIGURATION_FOLDER 	 = FileSystemView.getFileSystemView().getHomeDirectory() + "/BionimbuzClient/conf/";
	private static Configuration applicationConfiguration;
	private static Configuration parallelProgramList;
	private static Configuration sequentialProgramList;

	/**
	 * Called on Application Server start
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		System.out.println("Initializing client application...");
		System.out.println("Loading configurations...");
		
		applicationConfiguration = ConfigurationLoader.readConfiguration(
				CONFIGURATION_FOLDER + "config.json",
				ApplicationConfiguration.class);

		sequentialProgramList = ConfigurationLoader.readConfiguration(
				CONFIGURATION_FOLDER + "sequential_programs.json",
				SequentialProgramList.class);

		parallelProgramList = ConfigurationLoader.readConfiguration(
				CONFIGURATION_FOLDER + "parallel_programs.json",
				ParallelProgramList.class);

		System.out.println("Application Configuration loaded: "
				+ applicationConfiguration);
		System.out.println("Sequential Programs loaded: "
				+ sequentialProgramList);
		System.out.println("Parallel Programs loaded: " + parallelProgramList);

	}

	/**
	 * Called on Application Server stop
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("Finishing client application...");
	}

	public static ApplicationConfiguration getApplicationConfiguration() {
		return (ApplicationConfiguration) applicationConfiguration;
	}

	public static ParallelProgramList getParallelProgramList() {
		return (ParallelProgramList) parallelProgramList;
	}

	public static SequentialProgramList getSequentialProgramList() {
		return (SequentialProgramList) sequentialProgramList;
	}

}
