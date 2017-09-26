package resources;

public class StorageInstance {
    private Double hd;
    private Double priceHd;
    private String hdType;
    private String storageType;
    private String provider;

    public StorageInstance() {
    }

    public StorageInstance(Double hd, Double priceHd, String hdType, String storageType, String provider) {
        this.hd = hd;
        this.priceHd = priceHd;
        this.hdType = hdType;
        this.storageType = storageType;
        this.provider = provider;
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public Double getPriceHd() {
        return priceHd;
    }

    public void setPriceHd(Double priceHd) {
        this.priceHd = priceHd;
    }

    public String getHdType() {
        return hdType;
    }

    public void setHdType(String hdType) {
        this.hdType = hdType;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    
}

