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
    
}
