package com.jornada.shared.classes.pesquisasatisfacao;

import java.io.Serializable;
import java.util.Date;

public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1434171353793443708L;
    
    
    public static final String STR_GRAFICO_CIDADE_COLUNA="Cidades - Gráfico Coluna";
    public static final String STR_GRAFICO_CIDADE_LINHA="Cidades - Gráfico Tendência";
    public static final String STR_GRAFICO_SOBRE_RESTAURANTE_TORTA="Sobre o Restaurante - Gráfico Torta";
    public static final String STR_GRAFICO_PESQUISA_SATISFACAO_TORTA="Pesquisa Satisfação - Gráfico Torta";
    public static final String STR_GRAFICO_ATENDENTES="Pesquisa Satisfação - Gráfico Torta";
    
    
    public static final String SEPARATE_DATA = "<separate-data>";

    
    private int idAvaliacao; 
    private String restMoquem;    
    private String ambiente;
    private String atendimento;
    private String qualidade;
    private String espacoKids;
    private String cozinha;
    private String recomendariaRest;
    private String comoConheceuRest;
    private String voltariaRest;
    private String cidade;
    private Date data;
    private String email;
    private String telefone;
    private String sugestao;
    private String atendente;
    
    public Avaliacao(){        
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public String getRestMoquem() {
        return restMoquem;
    }

    public void setRestMoquem(String restMoquem) {
        this.restMoquem = restMoquem;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(String atendimento) {
        this.atendimento = atendimento;
    }

    public String getQualidade() {
        return qualidade;
    }

    public void setQualidade(String qualidade) {
        this.qualidade = qualidade;
    }

    public String getEspacoKids() {
        return espacoKids;
    }

    public void setEspacoKids(String espacoKids) {
        this.espacoKids = espacoKids;
    }

    public String getCozinha() {
        return cozinha;
    }

    public void setCozinha(String cozinha) {
        this.cozinha = cozinha;
    }

    public String getRecomendariaRest() {
        return recomendariaRest;
    }

    public void setRecomendariaRest(String recomendariaRest) {
        this.recomendariaRest = recomendariaRest;
    }

    public String getComoConheceuRest() {
        return comoConheceuRest;
    }

    public void setComoConheceuRest(String comoConheceuRest) {
        this.comoConheceuRest = comoConheceuRest;
    }

    public String getVoltariaRest() {
        return voltariaRest;
    }

    public void setVoltariaRest(String voltariaRest) {
        this.voltariaRest = voltariaRest;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }
    
 

}
