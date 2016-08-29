package br.unb.cic.bionimbuz.configuration;

import br.unb.bionimbuz.storage.bucket.PeriodicCheckerBuckets;
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
    
    @JsonProperty("buckets-folder")
    private String bucketsFolder;
    
    @JsonProperty("buckets-auth-folder")
    private String bucketsAuthFolder;
    
    @JsonProperty("gcloud-folder")
    private String gcloudFolder;
    
    @JsonProperty("storage-mode")
    private String storageMode;

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

    public String getBucketsFolder() {
        return bucketsFolder;
    }

    public void setBucketsFolder(String bucketsFolder) {
        this.bucketsFolder = bucketsFolder;
    }

    public String getBucketsAuthFolder() {
        return bucketsAuthFolder;
    }

    public void setBucketsAuthFolder(String bucketsAuthFolder) {
        this.bucketsAuthFolder = bucketsAuthFolder;
    }

    public String getGcloudFolder() {
        return gcloudFolder;
    }

    public void setGcloudFolder(String gcloudFolder) {
        this.gcloudFolder = gcloudFolder;
    }

    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
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
