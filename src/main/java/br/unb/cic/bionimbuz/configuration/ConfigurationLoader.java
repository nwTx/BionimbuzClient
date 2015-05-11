package br.unb.cic.bionimbuz.configuration;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads the Configuration file
 * @author Vinicius
 *
 */
public class ConfigurationLoader {

	/**
	 * Static method that reads config.json file
	 * @return Configuration
	 */
	public static Configuration readConfiguration() {
		ObjectMapper mapper = new ObjectMapper();
		Configuration config = null;
		
		try {
			config = mapper.readValue(
					new File("/Users/usuario/Documents/BioNimbuz/projetos/BionimbuzClient/conf/config.json"), 
					Configuration.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("(JsonParseException) Error loading configuration: " + e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("(JsonMappingException) Error loading configuration: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("(IOException) Error loading configuration: " + e.getMessage());
		}
		
		return config;
	}
}
