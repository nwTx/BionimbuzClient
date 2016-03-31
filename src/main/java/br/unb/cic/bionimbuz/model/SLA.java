/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.model;

import java.util.List;

/**
 *
 * @author Breno Rodri
 */
public class SLA {
    private User user;
    private User provider;
    private int objective;
    private Long period;
    private List <PluginService> services;
    private List<Instance> instances;

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
    public User getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(User provider) {
        this.provider = provider;
    }

    /**
     * @return the objective
     */
    public int getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(int objective) {
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
    
}
