package br.unb.cic.bionimbuz.elasticity;

import java.io.Serializable;

public class Instance implements Serializable {
    
    public String id;
    public String name;
    public String state;
    public String provider;
    
    public Instance() {}

//    public Instance(String id, String name, String state, String provider) {
//        this.id = id;
//        this.name = name;
//        this.state = state;
//        this.provider = provider;
//    }
    
    public Instance(String id, String state) {
        this.id = id;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    
}