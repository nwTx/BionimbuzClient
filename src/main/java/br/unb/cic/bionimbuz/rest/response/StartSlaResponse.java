/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.rest.response;

/**
 *
 * @author biolabid2
 */
public class StartSlaResponse implements ResponseInfo {
    
    private boolean SlaDone;

    public StartSlaResponse() {
    
    }

    public StartSlaResponse(boolean SlaDone) {
        this.SlaDone = SlaDone;
    }
    
    /**
     * @return the SlaDone
     */
    public boolean isSlaDone() {
        return SlaDone;
    }

    /**
     * @param SlaDone the SlaDone to set
     */
    public void setSlaDone(boolean SlaDone) {
        this.SlaDone = SlaDone;
    }
    
}
