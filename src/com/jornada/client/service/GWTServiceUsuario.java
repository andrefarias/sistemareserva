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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
//import com.jornada.shared.classes.list.UsuarioErroImportar;
//import com.jornada.shared.classes.relatorio.usuario.ProfessorDisciplinaRelatorio;

@RemoteServiceRelativePath("GWTServiceUsuario")
public interface GWTServiceUsuario extends RemoteService {
	
	
	public String AdicionarUsuario(Usuario usuario);	
	public String updateUsuarioRow(Usuario usuario);	
	public boolean atualizarSenha(int idUsuario, String senha, boolean forcarPrimeiroLogin);	
	public boolean deleteUsuarioRow(int id_usuario);	
	public String gerarExcelUsuario();
//	public ArrayList<UsuarioErroImportar> importarUsuariosUsandoExcel(String strFileName);
	public ArrayList<Usuario> getUsuarios();	
	public Usuario getUsuarioPeloId(int idUsuario);
	public ArrayList<Usuario> getUsuarios(String strFilter);
	public ArrayList<Usuario> getUsuarios(String strDBField, String strFilter);	
	public ArrayList<Usuario> getAlunosPorCurso(int idCurso, String strFiltroUsuario);	
	public ArrayList<Usuario> getAlunosPorCurso(int idCurso);	
//	public ArrayList<UsuarioNomeID> getAlunosTodosOuPorCurso(int idCurso, int idUnidade, boolean showAluno, boolean showPais, boolean showProfessor);
	public ArrayList<Usuario> getUsuariosPorCursoAmbientePai(Usuario usuarioPai, int idCurso);	
	public ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario, String strFilter);	
	public ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario);
	public ArrayList<Usuario> getFilhoDoPaiAmbientePais(Usuario usuarioPai);	
	public ArrayList<TipoUsuario> getTipoUsuarios();	
//	public boolean associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais);
	public ArrayList<Usuario> getTodosOsPaisDoAluno(int id_aluno);	
	public ArrayList<Usuario> getPaisPorCurso(int idCurso, String strFilterResp, String strFilterName);
	public ArrayList<Usuario> getTodosPais(String strFilterResp, String strFilterName);
//	public ArrayList<UsuarioNomeID> getCoordenadoresAdministradoresNomeId(int idUnidade);
	public ArrayList<Usuario> getTodosUsuarios();
//	public ArrayList<ProfessorDisciplinaRelatorio> getProfessoresDisciplinas();
	public String getExcelProfessoresDisciplinas();
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceUsuarioAsync instance;
		public static GWTServiceUsuarioAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceUsuario.class);
			}
			return instance;
		}
	}



}
