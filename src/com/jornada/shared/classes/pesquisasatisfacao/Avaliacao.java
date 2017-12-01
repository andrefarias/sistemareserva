package com.jornada.shared.classes.pesquisasatisfacao;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;
import com.jornada.shared.classes.Reserva;

public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1434171353793443708L;
    
    public static final String  STR_INTERNET =  "Internet";
    public static final String  STR_AMIGOS_FAMILIARES =  "Amigos/Familiares";
    public static final String  STR_JORNAL =  "Jornal";
    public static final String  STR_OUTROS =  "Outros";
    
    public static final String STR_SIM="Sim";
    public static final String STR_NAO="Não";
    public static final String STR_TALVEZ="Talvez";
    
    public static final String STR_REST_MOQUEM = "Restaurante Moquem";
    public static final String STR_AMBIENTE = "Ambiente";
    public static final String STR_ATENDIMENTO = "Atendimento";
    public static final String STR_QUALIDADE = "Qualidade";
    public static final String STR_ESPACO_KIDS = "Espaço Kids / Recreação";    
    public static final String STR_COZINHA = "Cozinha";    
    
    public static final String STR_COL_REST_MOQUEM = "rest_moquem";
    public static final String STR_COL_AMBIENTE = "ambiente";
    public static final String STR_COL_ATENDIMENTO = "atendimento";
    public static final String STR_COL_QUALIDADE = "qualidade";
    public static final String STR_COL_ESPACO_KIDS = "espaco_kids";    
    public static final String STR_COL_COZINHA = "cozinha";   
    
    public static final String[] listColumnsServicos = {STR_COL_REST_MOQUEM,STR_COL_AMBIENTE, STR_COL_ATENDIMENTO, STR_COL_QUALIDADE, STR_COL_ESPACO_KIDS, STR_COL_COZINHA};
   
    public static final String STR_NOTA_EXCELENTE = "Excelente";
    public static final String STR_NOTA_BOM = "Bom";
    public static final String STR_NOTA_REGULAR = "Regular";
    public static final String STR_NOTA_RUIM = "Ruim";
    
    public static final int INT_PESO_NOTA_EXCELENTE = 4;
    public static final int INT_PESO_NOTA_BOM = 3;
    public static final int INT_PESO_NOTA_REGULAR = 2;
    public static final int INT_PESO_NOTA_RUIM = 1;
    
    public static final String[] listNotas = {STR_NOTA_EXCELENTE,STR_NOTA_BOM, STR_NOTA_REGULAR, STR_NOTA_RUIM};    
    
    public static final String STR_GRAFICO_CIDADE_COLUNA="Cidades - Gráfico Coluna";
    public static final String STR_GRAFICO_OBS_COLUNA="Observação - Gráfico Coluna";
    public static final String STR_GRAFICO_OBS_TORTA="Observação - Gráfico Torta";
    public static final String STR_GRAFICO_CIDADE_LINHA="Cidades - Gráfico Tendência";
    public static final String STR_GRAFICO_SOBRE_RESTAURANTE_TORTA="Sobre o Restaurante - Gráfico Torta";
    public static final String STR_GRAFICO_PESQUISA_SATISFACAO_TORTA="Pesquisa Satisfação - Gráfico Torta";
    public static final String STR_GRAFICO_ATENDENTES="Atendentes - Gráfico Torta";
    public static final String STR_GRAFICO_LINHA_SERVICOS_REST="Serviços - Gráfico Tendência";  

    
    public static final String SEPARATE_DATA = "<separate-data>";
    public static final String TODAS = "Todas";
    
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
//    private String sugestao;
    private String atendente;
    private String Obs;
    private String recGrupoPrimario;
    private String recGrupoSecundario;
    
    public Avaliacao(){        
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }
    
    

    public String getObs() {
        return Obs;
    }

    public void setObs(String obs) {
        Obs = obs;
    }
    
    

    public String getRecGrupoPrimario() {
        return recGrupoPrimario;
    }

    public void setRecGrupoPrimario(String recGrupoPrimario) {
        this.recGrupoPrimario = recGrupoPrimario;
    }

    public String getRecGrupoSecundario() {
        return recGrupoSecundario;
    }

    public void setRecGrupoSecundario(String recGrupoSecundario) {
        this.recGrupoSecundario = recGrupoSecundario;
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

//    public String getSugestao() {
//        return sugestao;
//    }
//
//    public void setSugestao(String sugestao) {
//        this.sugestao = sugestao;
//    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public static String[] getListNotas() {
        return listNotas;
    }

    public static String[] getListColumnsServicos() {
        return listColumnsServicos;
    }
    
 
    public static final ProvidesKey<Avaliacao> KEY_PROVIDER = new ProvidesKey<Avaliacao>() {
        @Override
        public Object getKey(Avaliacao item) {
            return item == null ? null : Integer.toString(item.getIdAvaliacao());
        }
    };
    
}
