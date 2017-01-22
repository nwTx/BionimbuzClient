package br.unb.cic.bionimbuz.configuration;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads configuration files
 *
 * @author Vinicius
 */
public class ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);
    /**
     * Static method that reads config.json file
     *
     * @param path
     * @param c
     * @return Configuration
     */
    public static Configuration readConfiguration(String path, Class<? extends Configuration> c) {
        ObjectMapper mapper = new ObjectMapper();
        Configuration config = null;
        try {
            config = mapper.readValue(new File(path), c);
        } catch (JsonParseException e) {
            LOGGER.error("(JsonParseException) Error loading configuration: " + e.getMessage());
        } catch (JsonMappingException e) {
            LOGGER.error("(JsonMappingException) Error loading configuration: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("(IOException) Error loading configuration: " + e.getMessage());
        }

        return config;
    }
}
