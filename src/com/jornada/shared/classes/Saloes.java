package com.jornada.shared.classes;

import java.io.Serializable;

public class Saloes implements Serializable{    
    
    private static final long serialVersionUID = -9075501184472606101L;
    
    private Salao salaoInterno;
    private Salao salaoExternoCoberto;
    private Salao salaoExternoAberto;
    private Salao salaoChurrasqueira;
    
    public Saloes(){
        
        salaoInterno = new Salao();
        salaoExternoCoberto = new Salao();
        salaoExternoAberto = new Salao();
        salaoChurrasqueira = new Salao();
        
    }

    public Salao getSalaoInterno() {
        return salaoInterno;
    }

    public void setSalaoInterno(Salao salaoInterno) {
        this.salaoInterno = salaoInterno;
    }

    public Salao getSalaoExternoCoberto() {
        return salaoExternoCoberto;
    }

    public void setSalaoExternoCoberto(Salao salaoExternoCoberto) {
        this.salaoExternoCoberto = salaoExternoCoberto;
    }

    public Salao getSalaoExternoAberto() {
        return salaoExternoAberto;
    }

    public void setSalaoExternoAberto(Salao salaoExternoAberto) {
        this.salaoExternoAberto = salaoExternoAberto;
    }

    public Salao getSalaoChurrasqueira() {
        return salaoChurrasqueira;
    }

    public void setSalaoChurrasqueira(Salao salaoChurrasqueira) {
        this.salaoChurrasqueira = salaoChurrasqueira;
    }
    
    
    
    
}