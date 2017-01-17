/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.model;

//import br.unb.cic.bionimbuz.web.beans.SlaComposerBean;
import br.unb.cic.bionimbuz.web.beans.WorkflowComposerBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Breno Rodrigues
 */
public class SLA {
    
    private String id="SLA" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "-" + UUID.randomUUID().toString().substring(0, 13);
    private User user;
    private String provider;
    private Integer objective;
    private Long period;
    private List <PluginService> services;
    private List<Instance> instances;
    private Double value;
    private Long time;
    private Integer limitationType;
    private Long limitationValueExecutionTime;
    private Double limitationValueExecutionCost;
    
    
    public SLA(){
        
        this.user=null;
        this.provider=null;
        this.objective= 0;
        this.period = 0l;
        this.instances=null;
        this.services=null;
        this.time=0l;
        
    }

    public SLA(User user, String provider, Integer objective, Long period, List<PluginService> services, List<Instance> instances, Double value, Long time, Integer limitationType, Long limitationValueExecutionTime, Double limitationValueExecutionCost) {
        this.user = user;
        this.provider = provider;
        this.objective = objective;
        this.period = period;
        this.services = services;
        this.instances = instances;
        this.value = value;
        this.time = time;
        this.limitationType = limitationType;
        this.limitationValueExecutionTime = limitationValueExecutionTime;
        this.limitationValueExecutionCost = limitationValueExecutionCost;
    }
    
   
    public SLA(SLA sla){
        
        this.user=sla.getUser();
        this.provider=sla.getProvider();
        this.objective= sla.getObjective();
        this.period = sla.getPeriod();
        this.instances=sla.getInstances();
        this.services=sla.getServices();
        this.time=sla.getTime();
        
    }
    public SLA(WorkflowComposerBean slacomp,User user, String provider, List<PluginService> services){
        
        this.user=user;
        this.provider=provider;
        this.objective= slacomp.getObjective();
        System.out.println("objective constructor:"+slacomp.getObjective());
        this.period = 2l;
        this.instances=slacomp.getSelectedInstancies();
        this.services= services;
        this.limitationType= slacomp.getLimitationType();
        this.limitationValueExecutionCost=slacomp.getLimitationValueExecutionCost();
        this.limitationValueExecutionTime=slacomp.getLimitationValueExecutionTime();
    }
    
    public SLA(WorkflowComposerBean slacomp,User user, String provider, List<PluginService> service,Long time, Double value){
     
        this.user=user;
        this.provider=provider;
        this.objective= slacomp.getObjective();
        this.period = 2l;
        this.instances=slacomp.getSelectedInstancies();
        this.services=service;
        this.time= time;
        this.value=value;
    }
    
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return the objective
     */
    public Integer getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(Integer objective) {
        this.objective = objective;
    }

    /**
     * @return the period
     */
    public Long getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Long period) {
        this.period = period;
    }

    /**
     * @return the services
     */
    public List <PluginService> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List <PluginService> services) {
        this.services = services;
    }

    /**
     * @return the instances
     */
    
    public List<Instance> getInstances() {
        return instances;
    }

    /**
     * @param instances the instances to set
     */
    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * @return the time
     */
    public Long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the limitationType
     */
    public Integer getLimitationType() {
        return limitationType;
    }

    /**
     * @param limitationType the limitationType to set
     */
    public void setLimitationType(Integer limitationType) {
        this.limitationType = limitationType;
    }

    /**
     * @return the limitationValueExecutionTime
     */
    public Long getLimitationValueExecutionTime() {
        return limitationValueExecutionTime;
    }

    /**
     * @param limitationValueExecutionTime the limitationValueExecutionTime to set
     */
    public void setLimitationValueExecutionTime(Long limitationValueExecutionTime) {
        this.limitationValueExecutionTime = limitationValueExecutionTime;
    }

    /**
     * @return the limitationValueExecutionCost
     */
    public Double getLimitationValueExecutionCost() {
        return limitationValueExecutionCost;
    }

    /**
     * @param limitationValueExecutionCost the limitationValueExecutionCost to set
     */
    public void setLimitationValueExecutionCost(Double limitationValueExecutionCost) {
        this.limitationValueExecutionCost = limitationValueExecutionCost;
    }
    
}
