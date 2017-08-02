package com.jornada.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public interface GWTServiceAvaliacaoAsync {
    

    public void adicionarAvaliacao(Avaliacao avaliacao, AsyncCallback<String> callback);

    public void getGraficoColunaCidade(Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<String>> callback);
    
    public void getGraficoSobreRestaurante(Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<ArrayList<String>>> callback);

    void getGraficoPesquisaSatisfacao(Date dataInicial, Date dataFinal, AsyncCallback<ArrayList<ArrayList<String>>> callback);

}
