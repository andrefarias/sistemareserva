package com.jornada.shared.classes.pesquisasatisfacao;

import java.io.Serializable;

public class MediaServico implements Serializable {

    private static final long serialVersionUID = -5423299351175540207L;

    
    private double mediaRestMoquem;
    private double mediaAmbiente;
    private double mediaAtendimento;
    private double mediaQualidade;
    private double mediaEspaçoKids;
    private double mediaCozinha;
    
    private String strServico;
    private String strEscala;
    private String strData;
    
    public MediaServico() {
	}

    public double getMediaRestMoquem() {
        return mediaRestMoquem;
    }

    public void setMediaRestMoquem(double mediaRestMoquem) {
        this.mediaRestMoquem = mediaRestMoquem;
    }

    public double getMediaAmbiente() {
        return mediaAmbiente;
    }

    public void setMediaAmbiente(double mediaAmbiente) {
        this.mediaAmbiente = mediaAmbiente;
    }

    public double getMediaAtendimento() {
        return mediaAtendimento;
    }

    public void setMediaAtendimento(double mediaAtendimento) {
        this.mediaAtendimento = mediaAtendimento;
    }

    public double getMediaQualidade() {
        return mediaQualidade;
    }

    public void setMediaQualidade(double mediaQualidade) {
        this.mediaQualidade = mediaQualidade;
    }

    public double getMediaEspaçoKids() {
        return mediaEspaçoKids;
    }

    public void setMediaEspaçoKids(double mediaEspaçoKids) {
        this.mediaEspaçoKids = mediaEspaçoKids;
    }

    public double getMediaCozinha() {
        return mediaCozinha;
    }

    public void setMediaCozinha(double mediaCozinha) {
        this.mediaCozinha = mediaCozinha;
    }

    public String getStrEscala() {
        return strEscala;
    }

    public void setStrEscala(String strEscala) {
        this.strEscala = strEscala;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public String getStrServico() {
        return strServico;
    }

    public void setStrServico(String strServico) {
        this.strServico = strServico;
    }


}
