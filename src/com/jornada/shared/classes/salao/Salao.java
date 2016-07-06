package com.jornada.shared.classes.salao;

import java.io.Serializable;

public class Salao implements Serializable {

    private static final long serialVersionUID = 1434171353793443708L;
    
    private String nomeSalao;
    private int limiteSalao;
    private int quantidadeAdultos;
    private int quantidadeCriancas;
    
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

    public int getQuantidadeAdultos() {
        return quantidadeAdultos;
    }

    public void setQuantidadeAdultos(int quantidadeAdultos) {
        this.quantidadeAdultos = quantidadeAdultos;
    }

    public int getQuantidadeCriancas() {
        return quantidadeCriancas;
    }
    
    public void setQuantidadeCriancas(int quantidadeCriancas) {
        this.quantidadeCriancas = quantidadeCriancas;
    }
    
    
    
    

}
