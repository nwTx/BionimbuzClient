/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.model;

/**
 * Class that defines the Instancy
 * @author brenokx
 */
public class Instancy {
    private String type;
    private Double valueHour;
    private int quantity;
    private String locality;
    private Double memory;
    private Double cpuHtz;
    private String cpuType;
    private Double hd;
    private String hdType;

    public Instancy(){
        this.type = "vazia";
        this.valueHour = 0.0D;
        this.quantity = 0;
        this.locality = "vazia";
        this.memory = 0.0D;
        this.cpuHtz = 0.0D;
        this.cpuType = "vazia";
        this.hd = 0.0D;
        this.hdType = "vazia";    
    }
    
    /**
     * Constructor
     * @param type
     * @param valueHour
     * @param quantity
     * @param locality 
     * @param memory 
     * @param cpuHtz 
     * @param cpuType 
     * @param hd 
     * @param hdType 
     */
    public Instancy(String type, Double valueHour, int quantity, String locality, Double memory, Double cpuHtz, String cpuType, Double hd, String hdType){
        this.type = type;
        this.valueHour = valueHour;
        this.quantity = quantity;
        this.locality = locality;
        this.memory = memory;
        this.cpuHtz = cpuHtz;
        this.cpuType = cpuType;
        this.hd = hd;
        this.hdType = hdType;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the valueHour
     */
    public Double getValueHour() {
        return valueHour;
    }

    /**
     * @param valueHour the valueHour to set
     */
    public void setValueHour(Double valueHour) {
        this.valueHour = valueHour;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * @param locality the locality to set
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * @return the memory
     */
    public Double getMemory() {
        return memory;
    }

    /**
     * @param memory the memory to set
     */
    public void setMemory(Double memory) {
        this.memory = memory;
    }

    /**
     * @return the cpuHtz
     */
    public Double getCpuHtz() {
        return cpuHtz;
    }

    /**
     * @param cpuHtz the cpuHtz to set
     */
    public void setCpuHtz(Double cpuHtz) {
        this.cpuHtz = cpuHtz;
    }

    /**
     * @return the cpuType
     */
    public String getCpuType() {
        return cpuType;
    }

    /**
     * @param cpuType the cpuType to set
     */
    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * @return the hd
     */
    public Double getHd() {
        return hd;
    }

    /**
     * @param hd the hd to set
     */
    public void setHd(Double hd) {
        this.hd = hd;
    }

    /**
     * @return the hdType
     */
    public String getHdType() {
        return hdType;
    }

    /**
     * @param hdType the hdType to set
     */
    public void setHdType(String hdType) {
        this.hdType = hdType;
    }
    
}
