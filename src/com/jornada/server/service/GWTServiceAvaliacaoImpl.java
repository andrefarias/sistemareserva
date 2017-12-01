/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jornada.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.server.classes.AvaliacaoServer;
import com.jornada.server.classes.ReservaServer;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.DadoServicoTrend;
import com.jornada.shared.classes.pesquisasatisfacao.MediaServico;

public class GWTServiceAvaliacaoImpl extends RemoteServiceServlet implements GWTServiceAvaliacao {

    private static final long serialVersionUID = -6095757898073067239L;

    @Override
    public String adicionarAvaliacao(Avaliacao avaliacao) {
        return AvaliacaoServer.AdicionarAvaliacao(avaliacao);
    }

    @Override
    public ArrayList<String> getGraficoColunaCidade(String strCidades, Date dataInicial, Date dataFinal) {
        
        if (strCidades.equals(Avaliacao.TODAS)) {
            return AvaliacaoServer.getGraficoColunaCidade(dataInicial, dataFinal);
        } else {
            return AvaliacaoServer.getGraficoColunaCidade(strCidades, dataInicial, dataFinal);
        }
        
    }

    @Override
    public ArrayList<String> getGraficoColunaObs(String strCidades, Date dataInicial, Date dataFinal) {
        
        if (strCidades.equals(Avaliacao.TODAS)) {
            return AvaliacaoServer.getGraficoColunaObs(dataInicial, dataFinal);
        } else {
            return AvaliacaoServer.getGraficoColunaObs(strCidades, dataInicial, dataFinal);
        }
        
    }


    @Override
    public ArrayList<ArrayList<String>> getGraficoSobreRestaurante(String strCidades, Date dataInicial, Date dataFinal) {
        ArrayList<ArrayList<String>> listArray = new  ArrayList<ArrayList<String>>();

        if (strCidades.equals(Avaliacao.TODAS)) {
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("como_conheceu_rest", AvaliacaoServer.DB_SELECT_GRAFICO_COMO_CONHECEU_REST, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("recomendaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_RECOMENDARIA_REST, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("voltaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_VOLTARIA_REST, dataInicial, dataFinal));
        } else {
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("como_conheceu_rest", AvaliacaoServer.DB_SELECT_GRAFICO_COMO_CONHECEU_REST_FILTRO_CIDADE, strCidades, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("recomendaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_RECOMENDARIA_REST_FILTRO_CIDADE, strCidades, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("voltaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_VOLTARIA_REST_FILTRO_CIDADE, strCidades, dataInicial, dataFinal));
        }
        return listArray;
    }

    @Override
    public ArrayList<ArrayList<String>> getGraficoPesquisaSatisfacao(String strCidade, Date dataInicial, Date dataFinal) {
        // TODO Auto-generated method stub
        
        ArrayList<ArrayList<String>> listArray = new  ArrayList<ArrayList<String>>();
        if (strCidade.equals(Avaliacao.TODAS)) {
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("rest_moquem", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("ambiente", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("atendimento", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("qualidade", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("espaco_kids", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("cozinha", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA, dataInicial, dataFinal));
        } else {
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("rest_moquem", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("ambiente", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("atendimento", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("qualidade", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("espaco_kids", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("cozinha", AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
        }
        return listArray;
    }

    @Override
    public ArrayList<String> getCidades() {
        return AvaliacaoServer.getCidades();
    }

    @Override
    public ArrayList<String> getGraficoAtendentes(String strCidade, String strAtendente, Date dataInicial, Date dataFinal) {
        if (strCidade.equals(Avaliacao.TODAS)) {
            return AvaliacaoServer.getGraficoAtendentes(strAtendente, dataInicial, dataFinal);
        } else {
            return AvaliacaoServer.getGraficoAtendentes(strCidade, strAtendente, dataInicial, dataFinal);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public ArrayList<ArrayList<MediaServico>> getGraficoServicos(String strCidade, String strEscala, Date dataInicial, Date dataFinal) {

        
        ArrayList<ArrayList<MediaServico>> listMediaServico = new  ArrayList<ArrayList<MediaServico>>();
        
        if (strCidade.equals(Avaliacao.TODAS)) {
            
            for (int iColumns = 0; iColumns < Avaliacao.getListColumnsServicos().length; iColumns++) {
                String strColuna = Avaliacao.getListColumnsServicos()[iColumns];
                ArrayList<ArrayList<DadoServicoTrend>> listArray = new  ArrayList<ArrayList<DadoServicoTrend>>();

                for (int iNotas = 0; iNotas < Avaliacao.getListNotas().length; iNotas++) {
                    String strNota = Avaliacao.getListNotas()[iNotas];

                    listArray.add(AvaliacaoServer.getGraficoServicos(strColuna, strNota, strEscala, dataInicial, dataFinal));
                }

                if(strColuna.equals(Avaliacao.STR_COL_REST_MOQUEM)){
                    ArrayList<MediaServico> listMediaServicoRestMoquem = new  ArrayList<MediaServico>();
                    listMediaServicoRestMoquem.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoRestMoquem);
                }else if(strColuna.equals(Avaliacao.STR_COL_AMBIENTE)){
                    ArrayList<MediaServico> listMediaServicoAmbiente = new  ArrayList<MediaServico>();
                    listMediaServicoAmbiente.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoAmbiente);
                }else if(strColuna.equals(Avaliacao.STR_COL_ATENDIMENTO)){
                    ArrayList<MediaServico> listMediaServicoAtendimento = new  ArrayList<MediaServico>();
                    listMediaServicoAtendimento.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoAtendimento);
                }else if(strColuna.equals(Avaliacao.STR_COL_QUALIDADE)){
                    ArrayList<MediaServico> listMediaServicoQualidade = new  ArrayList<MediaServico>();
                    listMediaServicoQualidade.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoQualidade);
                }else if(strColuna.equals(Avaliacao.STR_COL_ESPACO_KIDS)){
                    ArrayList<MediaServico> listMediaServicoKids = new  ArrayList<MediaServico>();
                    listMediaServicoKids.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoKids);
                }else if(strColuna.equals(Avaliacao.STR_COL_COZINHA)){
                    ArrayList<MediaServico> listMediaServicoCozinha = new  ArrayList<MediaServico>();
                    listMediaServicoCozinha.addAll(getMediaService(listArray));
                    listMediaServico.add(listMediaServicoCozinha);
                }                
            }         
            
        } else {
            ArrayList<ArrayList<DadoServicoTrend>> listArray = new  ArrayList<ArrayList<DadoServicoTrend>>();
            listArray.add(AvaliacaoServer.getGraficoServicos("rest_moquem", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_REST_MOQUEM_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoServicos("ambiente", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_AMBIENTE_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoServicos("atendimento", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_ATENDIMENTO_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoServicos("qualidade", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_QUALIDADE_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoServicos("espaco_kids", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_ESPACOKIDS_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
            listArray.add(AvaliacaoServer.getGraficoServicos("cozinha", AvaliacaoServer.DB_SELECT_GRAFICO_SERVICOS_COZINHA_FILTRO_CIDADE, strCidade, dataInicial, dataFinal));
        }
        String test="1";
        return listMediaServico;
    }
    
    
    private ArrayList<MediaServico> getMediaService(ArrayList<ArrayList<DadoServicoTrend>> listArray){
        
        
        ArrayList<MediaServico> listMediaServico = new ArrayList<MediaServico>();
        ArrayList<DadoServicoTrend> newList = new ArrayList<DadoServicoTrend>();
//        int iLinhasData=0;
        
        for(int iNota=0;iNota<listArray.size();iNota++){
            ArrayList<DadoServicoTrend> listTrend = listArray.get(iNota);
//            iLinhasData = listTrend.size();
            for(int iData=0;iData<listTrend.size();iData++){
                newList.add(listTrend.get(iData));
            }


        }
        Collections.sort(newList, new Comparator<DadoServicoTrend>() {
            @Override
            public int compare(DadoServicoTrend trend1, DadoServicoTrend trend2)
            {
                return trend1.getData().compareTo(trend2.getData());
            }
        });
        
      int intExcelente=0;
      int intBom=0;
      int intRegular=0;
      int intRuim=0;
      int intDenominador=0;
      double media=0; 
      int count=0;
        
        for(int row=0;row<newList.size();row++){
            
            count++;
            
            DadoServicoTrend trend = newList.get(row);


                if(trend.getStrNota().equals(Avaliacao.STR_NOTA_EXCELENTE)){                    
                    intExcelente = trend.getContador() * Avaliacao.INT_PESO_NOTA_EXCELENTE;
                }else if(trend.getStrNota().equals(Avaliacao.STR_NOTA_BOM)){                    
                    intBom = trend.getContador() * Avaliacao.INT_PESO_NOTA_BOM;                    
                } else if(trend.getStrNota().equals(Avaliacao.STR_NOTA_REGULAR)){                    
                    intRegular = trend.getContador() * Avaliacao.INT_PESO_NOTA_REGULAR;                    
                }else if(trend.getStrNota().equals(Avaliacao.STR_NOTA_RUIM)){                    
                    intRuim = trend.getContador() * Avaliacao.INT_PESO_NOTA_RUIM;                    
                }
                intDenominador = intDenominador + trend.getContador();
                if(count==4){
                    
                    media = ((double)(intExcelente+intBom+intRegular+intRuim))/((double)intDenominador);
                    MediaServico mediaServ = new MediaServico();
                    
                    if(trend.getStrServico().equals(Avaliacao.STR_COL_REST_MOQUEM)){
                        mediaServ.setMediaRestMoquem(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_REST_MOQUEM);
                    }else if(trend.getStrServico().equals(Avaliacao.STR_COL_AMBIENTE)){
                        mediaServ.setMediaAmbiente(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_AMBIENTE);
                    }else if(trend.getStrServico().equals(Avaliacao.STR_COL_ATENDIMENTO)){
                        mediaServ.setMediaAtendimento(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_ATENDIMENTO);
                    }else if(trend.getStrServico().equals(Avaliacao.STR_COL_QUALIDADE)){
                        mediaServ.setMediaQualidade(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_QUALIDADE);
                    }else if(trend.getStrServico().equals(Avaliacao.STR_COL_ESPACO_KIDS)){
                        mediaServ.setMediaEspaçoKids(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_ESPACO_KIDS);
                    }else if(trend.getStrServico().equals(Avaliacao.STR_COL_COZINHA)){
                        mediaServ.setMediaCozinha(media);
                        mediaServ.setStrServico(Avaliacao.STR_COL_COZINHA);
                    }

                    mediaServ.setStrData(trend.getData());
                    listMediaServico.add(mediaServ);
                    
                    intExcelente=0;  
                    intBom=0;
                    intRegular=0;
                    intRuim=0;
                    intDenominador=0;
                    media=0; 
                    count=0;
                    
                }             
           
        

        }
        
        @SuppressWarnings("unused")
        String strTest="teste";
        
        return listMediaServico;
    }

    @Override
    public ArrayList<Avaliacao> getAvaliacoes(Date dataInicial, Date dataFinal, String strObs) {

        return AvaliacaoServer.getAvaliacoes(dataInicial, dataFinal, strObs);
    }

    @Override
    public boolean updateRow(Avaliacao object) {
        return AvaliacaoServer.updateRow(object);
    }

    @Override
    public boolean deleteRow(int idReserva) {
        // TODO Auto-generated method stub
        return ReservaServer.deleteRow(idReserva);
    }

}
