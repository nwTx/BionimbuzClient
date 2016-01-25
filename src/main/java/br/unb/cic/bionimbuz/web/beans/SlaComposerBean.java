/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.web.beans;

import br.unb.cic.bionimbuz.model.Pair;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author brenokx
 */
public class SlaComposerBean implements Serializable{
     
    private String panel1 = "Hide-Panel1";
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowComposerBean.class);
    private boolean limitation;
    private Pair<Integer,Double> limitationTypeValue;
    
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
     * @return the limitationTypeValue
     */
    public Pair<Integer,Double> getLimitationTypeValue() {
        return limitationTypeValue;
    }

    /**
     * @param limitationTypeValue the limitationTypeValue to set
     */
    public void setLimitationTypeValue(Pair<Integer,Double> limitationTypeValue) {
        this.limitationTypeValue = limitationTypeValue;
    }


}
