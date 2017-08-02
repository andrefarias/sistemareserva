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
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.server.classes.AvaliacaoServer;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class GWTServiceAvaliacaoImpl extends RemoteServiceServlet implements GWTServiceAvaliacao {

    private static final long serialVersionUID = -6095757898073067239L;

    @Override
    public String adicionarAvaliacao(Avaliacao avaliacao) {
        return AvaliacaoServer.AdicionarAvaliacao(avaliacao);
    }

    @Override
    public ArrayList<String> getGraficoColunaCidade(Date dataInicial, Date dataFinal) {
        return AvaliacaoServer.getGraficoColunaCidade(dataInicial, dataFinal);
    }



    @Override
    public ArrayList<ArrayList<String>> getGraficoSobreRestaurante(Date dataInicial, Date dataFinal) {
        ArrayList<ArrayList<String>> listArray = new  ArrayList<ArrayList<String>>();
        
        listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("como_conheceu_rest", AvaliacaoServer.DB_SELECT_GRAFICO_COMO_CONHECEU_REST,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("recomendaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_RECOMENDARIA_REST,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoTortaSobreRestaurante("voltaria_rest", AvaliacaoServer.DB_SELECT_GRAFICO_VOLTARIA_REST,dataInicial, dataFinal));
        
        return listArray;
    }

    @Override
    public ArrayList<ArrayList<String>> getGraficoPesquisaSatisfacao(Date dataInicial, Date dataFinal) {
        // TODO Auto-generated method stub
        
        ArrayList<ArrayList<String>> listArray = new  ArrayList<ArrayList<String>>();
        
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("rest_moquem",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_REST_MOQUEM,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("ambiente",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_AMBIENTE,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("atendimento",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ATENDIMENTO,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("qualidade",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_QUALIDADE,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("espaco_kids",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_ESPACOKIDS,dataInicial, dataFinal));
        listArray.add(AvaliacaoServer.getGraficoPesquisaSatisfacao("cozinha",AvaliacaoServer.DB_SELECT_GRAFICO_PESQUISA_SATIFACAO_COZINHA,dataInicial, dataFinal));
        
        return listArray;
    }
}
