package com.jornada.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.MediaServico;

public interface GWTServiceAvaliacaoAsync {
    

    public void adicionarAvaliacao(Avaliacao avaliacao, AsyncCallback<String> callback);

    public void getGraficoColunaCidade(String strCidade, Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<String>> callback);
    
    public void getGraficoSobreRestaurante(String strCidade, Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<ArrayList<String>>> callback);

    public void getGraficoPesquisaSatisfacao(String strCidade, Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<ArrayList<String>>> callback);

    public void getCidades(AsyncCallback<ArrayList<String>> callback);

    public void getGraficoAtendentes(String strCidade, String strAtendente, Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<String>> callback);

    public void getGraficoServicos(String strCidade, String strEscala, Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<ArrayList<MediaServico>>> callback);

}
