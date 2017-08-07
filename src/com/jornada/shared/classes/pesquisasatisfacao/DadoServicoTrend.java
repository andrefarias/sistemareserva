package com.jornada.shared.classes.pesquisasatisfacao;

import java.io.Serializable;

public class DadoServicoTrend implements Serializable {

    private static final long serialVersionUID = -4722100587438955295L;

    private String strServico;
    private String strNota;
    private String data;
    private int contador;
    
    public DadoServicoTrend() {
	}

    public String getStrServico() {
        return strServico;
    }

    public void setStrServico(String strServico) {
        this.strServico = strServico;
    }

    public String getStrNota() {
        return strNota;
    }

    public void setStrNota(String strNota) {
        this.strNota = strNota;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    

}
