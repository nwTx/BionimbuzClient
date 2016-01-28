package br.unb.cic.bionimbuz.configuration;

import javax.ejb.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It is the data contained in config.json
 *
 * @author Vinicius
 */
@Singleton
public class ApplicationConfiguration implements Configuration {

    private String address;
    
    private String bionimbuzAddress;
    
    private String uploadedFilesDirectory;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBionimbuzAddress() {
        return bionimbuzAddress;
    }

    public void setBionimbuzAddress(String bionimbuzAddress) {
        this.bionimbuzAddress = bionimbuzAddress;
    }

    public String getUploadedFilesDirectory() {
        return uploadedFilesDirectory;
    }

    public void setUploadedFilesDirectory(String uploadedFilesDirectory) {
        this.uploadedFilesDirectory = uploadedFilesDirectory;
    }

    public void log() {
        Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);
        
        LOGGER.info("Web Application configurations:");
        LOGGER.info(" - address=" + address);
        LOGGER.info(" - bionimbuzAddress=" + bionimbuzAddress);
        LOGGER.info(" - uploadedFilesDirectory=" + uploadedFilesDirectory);
        
        LOGGER.info("========================================");
    }

}
