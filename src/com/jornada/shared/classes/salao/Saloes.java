package com.jornada.shared.classes.salao;

import java.io.Serializable;

public class Saloes implements Serializable{    
    
    private static final long serialVersionUID = -9075501184472606101L;
    
    public static final String STR_NOME_SALAO_INTERNO = "Interno";
    public static final int INT_LIMITE_SALAO_INTERNO = 20;
    public static final int INT_QUASE_LIMITE_SALAO_INTERNO = INT_LIMITE_SALAO_INTERNO-10;
    
    public static final String STR_NOME_SALAO_EXTERNO_COBERTO = "Externo Coberto";
    public static final int INT_LIMITE_SALAO_EXTERNO_COBERTO = 70;
    public static final int INT_QUASE_LIMITE_EXTERNO_COBERTO = INT_LIMITE_SALAO_EXTERNO_COBERTO-10;
    
    public static final String STR_NOME_SALAO_EXTERNO_ABERTO = "Externo Aberto";
    public static final int INT_LIMITE_SALAO_EXTERNO_ABERTO = 29;
    public static final int INT_QUASE_LIMITE_EXTERNO_ABERTO = INT_LIMITE_SALAO_EXTERNO_ABERTO-10;
    
    public static final String STR_NOME_SALAO_CHURRASQUEIRA = "Churrasqueira";
    public static final int INT_LIMITE_SALAO_CHURRASQUEIRA = 19;
    public static final int INT_QUASE_LIMITE_CHURRASQUEIRA = INT_LIMITE_SALAO_CHURRASQUEIRA-10;
    
    public static final int INT_LIMITE_TOTAL_CLIENTES = 
            INT_LIMITE_SALAO_INTERNO + 
            INT_LIMITE_SALAO_EXTERNO_COBERTO + 
            INT_LIMITE_SALAO_EXTERNO_ABERTO + 
            INT_LIMITE_SALAO_CHURRASQUEIRA;
    
    private Salao salaoInterno;
    private Salao salaoExternoCoberto;
    private Salao salaoExternoAberto;
    private Salao salaoChurrasqueira;
    
    private int totalClientes;
    private int totalAdultos;
    private int totalCriancas;
    
//    ConfigClient clientConfig = GWT.create(ConfigClient.class);
    
    public Saloes(){
        
        salaoInterno = new Salao();
        salaoInterno.setNomeSalao(STR_NOME_SALAO_INTERNO);
        salaoInterno.setLimiteSalao(INT_LIMITE_SALAO_INTERNO);
        
        salaoExternoCoberto = new Salao();
        salaoExternoCoberto.setNomeSalao(STR_NOME_SALAO_EXTERNO_COBERTO);
        salaoExternoCoberto.setLimiteSalao(INT_LIMITE_SALAO_EXTERNO_COBERTO);
        
        salaoExternoAberto = new Salao();
        salaoExternoAberto.setNomeSalao(STR_NOME_SALAO_EXTERNO_ABERTO);
        salaoExternoAberto.setLimiteSalao(INT_LIMITE_SALAO_EXTERNO_ABERTO);
        
        salaoChurrasqueira = new Salao();     
        salaoChurrasqueira.setNomeSalao(STR_NOME_SALAO_CHURRASQUEIRA);
        salaoChurrasqueira.setLimiteSalao(INT_LIMITE_SALAO_CHURRASQUEIRA);
      
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

    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public int getTotalAdultos() {
        return totalAdultos;
    }

    public void setTotalAdultos(int totalAdultos) {
        this.totalAdultos = totalAdultos;
    }

    public int getTotalCriancas() {
        return totalCriancas;
    }

    public void setTotalCriancas(int totalCriancas) {
        this.totalCriancas = totalCriancas;
    }
    
    
    
}