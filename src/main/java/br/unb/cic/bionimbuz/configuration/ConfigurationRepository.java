package br.unb.cic.bionimbuz.configuration;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 	Class that acts as a repository for all client Configuration File
 * 		
 * 	+---------------------------------------------------------------------+
 *  |			Config File	 		|				Content				  |
 * 	+---------------------------------------------------------------------+
 * 	|								|	Contains informations about:	  |									
 * 	|								|		- Uploaded files direcotory	  |									
 * 	|	applicationConfiguration	|		- Bionimbuz Server IP		  |					
 * 	|								|		- Application IP			  |
 * 	+---------------------------------------------------------------------+
 * 	|								|  Contains informations about the    |   												
 * 	|	programConfiguration		|  programs stored at BioNimbuZ 	  |	 							
 * 	|								|  server							  |		
 * 	+---------------------------------------------------------------------+
 * 		
 * 	@author Vinicius
 */
@Named
public class ConfigurationRepository implements ServletContextListener {
	private static Configuration applicationConfiguration;
	private static Configuration programConfiguration;

	/**
	 * Called on Application Server start
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		System.out.println("Initializing client application...");
		System.out.println("Loading configurations...");

		applicationConfiguration = ConfigurationLoader
				.readConfiguration(
						"/Users/usuario/Documents/BioNimbuz/projetos/BionimbuzClient/conf/config.json",
						ApplicationConfiguration.class);

		programConfiguration = ConfigurationLoader
				.readConfiguration(
						"/Users/usuario/Documents/BioNimbuz/projetos/BionimbuzClient/conf/programs.json",
						ProgramConfiguration.class);

		System.out.println("Application Configuration loaded: " + applicationConfiguration);
		System.out.println("Programs Configuration loaded: " + programConfiguration);

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

	public static ProgramConfiguration getProgramConfiguration() {
		return (ProgramConfiguration) programConfiguration;
	}
}
