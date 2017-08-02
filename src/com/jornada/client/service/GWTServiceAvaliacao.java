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
package com.jornada.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

@RemoteServiceRelativePath("GWTServiceAvaliacao")
public interface GWTServiceAvaliacao extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
    
    public String adicionarAvaliacao(Avaliacao avaliacao);
    
    public ArrayList<String> getGraficoColunaCidade(Date dataInicial, Date dataFinal);
    public ArrayList<ArrayList<String>> getGraficoSobreRestaurante(Date dataInicial, Date dataFinal);
    public ArrayList<ArrayList<String>> getGraficoPesquisaSatisfacao(Date dataInicial, Date dataFinal);
    
	public static class Util {
		private static GWTServiceAvaliacaoAsync instance;
		public static GWTServiceAvaliacaoAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceAvaliacao.class);
			}
			return instance;
		}
	}
}
