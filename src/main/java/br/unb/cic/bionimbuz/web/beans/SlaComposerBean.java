/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.web.beans;

import br.unb.cic.bionimbuz.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author brenokx
 */
@Named
@SessionScoped
public class SlaComposerBean implements Serializable{
    
    @Inject
    private SessionBean sessionBean;
    private String panel1 = "Hide-Panel1";
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowComposerBean.class);
    private boolean limitation;
    private String limitationType;
    private String limitationValue;
    private Map<String, Integer> instanciesTypeQtd;

    private String[] selectedInstancies;
// Logged user
    private User loggedUser;
    
    @PostConstruct
    public void init() {
        loggedUser = sessionBean.getLoggedUser();
        setInstanciesTypeQtd(new ConcurrentHashMap<>());
        getInstanciesTypeQtd().put("Micro",10);
        getInstanciesTypeQtd().put("Macro",20);
        getInstanciesTypeQtd().put("Large",30);
    }
    public void setPanel1(String panel1){
    this.panel1 = panel1;
    }

    public String getPanel1(){
    return this.panel1;
    }

    /**
     * @return the limitation
     */
    public boolean isLimitation() {
        return limitation;
    }

    /**
     * @param limitation the limitation to set
     */
    public void setLimitation(boolean limitation) {
        this.limitation = limitation;
    }

    /**
     * @return the selectedInstancies
     */
    public String[] getSelectedInstancies() {
        return selectedInstancies;
    }

    /**
     * @param selectedInstancies the selectedInstancies to set
     */
    public void setSelectedInstancies(String[] selectedInstancies) {
        this.selectedInstancies = selectedInstancies;
    }


    /**
     * @return the instanciesTypeQtd
     */
    public Map<String, Integer> getInstanciesTypeQtd() {
        return instanciesTypeQtd;
    }

    /**
     * @param instanciesTypeQtd the instanciesTypeQtd to set
     */
    public void setInstanciesTypeQtd(Map<String, Integer> instanciesTypeQtd) {
        this.instanciesTypeQtd = instanciesTypeQtd;
    }

    public List getkeyListInstancyType(){
        return new ArrayList(instanciesTypeQtd.keySet());
    }

    /**
     * @return the limitationType
     */
    public String getLimitationType() {
        return limitationType;
    }

    /**
     * @param limitationType the limitationType to set
     */
    public void setLimitationType(String limitationType) {
        this.limitationType = limitationType;
    }

    /**
     * @return the limitationValue
     */
    public String getLimitationValue() {
        return limitationValue;
    }

    /**
     * @param limitationValue the limitationValue to set
     */
    public void setLimitationValue(String limitationValue) {
        this.limitationValue = limitationValue;
    }

}
