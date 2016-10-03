package br.unb.cic.bionimbuz.elasticity;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
public class InstanceView implements Serializable {
     
    private List<Instance> instances;
     
    private Instance selectedInstance;
     
    @ManagedProperty("#{instanceService}")
    private InstanceService service;
 
    @PostConstruct
    public void init() {        
        instances = service.getInstances();
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    public Instance getSelectedInstance() {
        return selectedInstance;
    }

    public void setSelectedInstance(Instance selectedInstance) {
        this.selectedInstance = selectedInstance;
    }

    public InstanceService getService() {
        return service;
    }

    public void setService(InstanceService service) {
        this.service = service;
    }

    
    

}