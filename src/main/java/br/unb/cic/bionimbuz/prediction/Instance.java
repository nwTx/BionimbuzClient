/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unb.cic.bionimbuz.prediction;

/**
 *
 * @author guilherme
 */
public class Instance {
    
    public String id;
    public String programa;
    public int memoria;
    public int cpu;
    public int preco;
    public String provider;

    public Instance(){}
    
    public Instance(String id, String programa, int memoria, int cpu, int preco, String provider) {
        this.id = id;
        this.programa = programa;
        this.memoria = memoria;
        this.cpu = cpu;
        this.preco = preco;
        this.provider = provider;
    }

    public Instance(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

       
}
