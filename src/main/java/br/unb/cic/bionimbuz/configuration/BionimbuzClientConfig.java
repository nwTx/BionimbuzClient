package br.unb.cic.bionimbuz.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vinicius
 */
public class BionimbuzClientConfig {

    @JsonProperty("root-path")
    private String rootPath;

    @JsonProperty("tmp-workflow-folder")
    private String temporaryWorkflowFolder;

    @JsonProperty("address")
    private String address;

    @JsonProperty("bionimbuz-address")
    private String bionimbuzAddress;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getTemporaryWorkflowFolder() {
        return temporaryWorkflowFolder;
    }

    public void setTemporaryWorkflowFolder(String temporaryWorkflowFolder) {
        this.temporaryWorkflowFolder = temporaryWorkflowFolder;
    }

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

    public void log() {
        Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

        LOGGER.info("Web Application configurations:");
        LOGGER.info(" - address=" + address);
        LOGGER.info(" - bionimbuzAddress=" + bionimbuzAddress);
        LOGGER.info(" - tmpWorkflowFolder=" + temporaryWorkflowFolder);
        LOGGER.info(" - rootPath=" + rootPath);

        LOGGER.info("========================================");
    }

}
