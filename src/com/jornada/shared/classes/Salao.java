package com.jornada.shared.classes;

import java.io.Serializable;

public class Salao implements Serializable {

    private static final long serialVersionUID = 1434171353793443708L;
    
    private String nomeSalao;
    private int limiteSalao;
    
    public Salao(){        
    }
    
    public String getNomeSalao() {
        return nomeSalao;
    }
    public void setNomeSalao(String nomeSalao) {
        this.nomeSalao = nomeSalao;
    }
    public int getLimiteSalao() {
        return limiteSalao;
    }
    public void setLimiteSalao(int limiteSalao) {
        this.limiteSalao = limiteSalao;
    }
    
    
    

}
