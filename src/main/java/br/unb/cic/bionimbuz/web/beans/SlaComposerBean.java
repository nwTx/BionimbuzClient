/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.web.beans;

import br.unb.cic.bionimbuz.model.Instancy;
import br.unb.cic.bionimbuz.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
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
    private List<Instancy> instancies;
    private List<Instancy> selectedInstancies;

// Logged user
    private User loggedUser;
    
    @PostConstruct
    public void init() {
        loggedUser = sessionBean.getLoggedUser();
        Instancy instancyMicro=new Instancy("Micro",0.03,10,"Brazil",1.0,3.3,"Xeon",20.0,"sata");
        instancies= new ArrayList<>();
        instancies.add(instancyMicro);
        Instancy instancyMacro=new Instancy("Macro",0.24,5,"us-west",2.0,3.3,"Xeon",120.0,"sata");
        instancies.add(instancyMacro);
        Instancy instancyLarge=new Instancy("Large",0.41,3,"us-west",8.0,3.3,"Xeon",240.0,"sata");
        instancies.add(instancyLarge);

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

    /**
     * @return the instancies
     */
    public List<Instancy> getInstancies() {
        return instancies;
    }

    /**
     * @param instancies the instancies to set
     */
    public void setInstancies(List<Instancy> instancies) {
        this.instancies = instancies;
    }

    /**
     * @return the selectedInstancies
     */
    public List<Instancy> getSelectedInstancies() {
        return selectedInstancies;
    }

    /**
     * @param selectedInstancies the selectedInstancies to set
     */
    public void setSelectedInstancies(List<Instancy> selectedInstancies) {
        this.selectedInstancies = selectedInstancies;
    }

}
