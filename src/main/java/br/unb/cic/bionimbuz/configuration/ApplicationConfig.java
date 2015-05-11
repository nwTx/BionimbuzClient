package br.unb.cic.bionimbuz.configuration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Named
@SessionScoped
public class ApplicationConfig implements ServletContextListener, Serializable {
	private static final long serialVersionUID = 1L;

	private Configuration config = new Configuration();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Initializing client application...");
		System.out.println("Loading configurations...");

		config = ConfigurationLoader.readConfiguration();

		System.out.println("Configurations loaded: " + config);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Finishing client application...");
	}

	public Configuration getConfig() {
		return config;
	}
}
