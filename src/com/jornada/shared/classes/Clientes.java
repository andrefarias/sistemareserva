package com.jornada.shared.classes;

import java.io.Serializable;

public class Clientes implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int numeroTotalAdultos=0;
    private int numeroTotalCriancas=0;
    private int numeroTotalClientes=0;
    
    public Clientes(){
        
    }
    
    public int getNumeroTotalAdultos() {
        return numeroTotalAdultos;
    }
    public void setNumeroTotalAdultos(int numeroTotalAdultos) {
        this.numeroTotalAdultos = numeroTotalAdultos;
    }
    public int getNumeroTotalCriancas() {
        return numeroTotalCriancas;
    }
    public void setNumeroTotalCriancas(int numeroTotalCriancas) {
        this.numeroTotalCriancas = numeroTotalCriancas;
    }

    public int getNumeroTotalClientes() {
        return numeroTotalClientes;
    }
    public void setNumeroTotalClientes(int numeroTotalClientes) {
        this.numeroTotalClientes = numeroTotalClientes;
    }
    
    

}
