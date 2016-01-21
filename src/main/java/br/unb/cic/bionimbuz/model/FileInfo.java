package br.unb.cic.bionimbuz.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FileInfo {

    private String id = UUID.randomUUID().toString();

    private String name;

    private long size;

    private long userId;

    private String uploadTimestamp;

    private String hash;

    @JsonProperty("payload")
    private byte[] payload;

    public FileInfo() {
    }

    public FileInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(String uploadedTimestamp) {
        this.uploadTimestamp = uploadedTimestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
