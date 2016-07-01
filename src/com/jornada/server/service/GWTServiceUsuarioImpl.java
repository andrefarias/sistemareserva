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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.shared.classes.TipoUsuario;
//import com.jornada.shared.classes.UnidadeEscola;
import com.jornada.shared.classes.Usuario;
//import com.jornada.shared.classes.list.UsuarioErroImportar;
//import com.jornada.shared.classes.relatorio.usuario.ProfessorDisciplinaRelatorio;


public class GWTServiceUsuarioImpl extends RemoteServiceServlet implements GWTServiceUsuario {


	private static final long serialVersionUID = -1315036586990464191L;

	public String AdicionarUsuario(Usuario usuario) {		
		return UsuarioServer.AdicionarUsuario(usuario);		
	}

	public String updateUsuarioRow(Usuario usuario){		
		return UsuarioServer.updateUsuarioRow(usuario);
	}
	
	public boolean atualizarSenha(int idUsuario, String password, boolean forcarPrimeiroLogin){		
		return UsuarioServer.atualizarSenha(idUsuario, password, forcarPrimeiroLogin);
	}
	
	public boolean deleteUsuarioRow(int id_usuario){		
		return UsuarioServer.deleteUsuarioRow(id_usuario);		
	}	
	
	
	public String gerarExcelUsuario(){
		return UsuarioServer.gerarExcelUsuario();
	}
	
//	public ArrayList<UsuarioErroImportar> importarUsuariosUsandoExcel(String excelFile){
//		return UsuarioServer.importarUsuariosUsandoExcel(excelFile);
//	}
	
	public ArrayList<Usuario> getUsuarios() {		
		return UsuarioServer.getUsuarios();
	}
	
    public ArrayList<Usuario> getTodosUsuarios() {
        return UsuarioServer.getTodosUsuarios();
    }
	
	public ArrayList<Usuario> getUsuarios(String strFilter) {				
		return UsuarioServer.getUsuarios(strFilter);
	}	
	
	public ArrayList<Usuario> getUsuarios(String strDBField, String strFilter) {	
		
		if(strFilter.length()==2){
//			return UsuarioServer.getUsuarios();
		    return UsuarioServer.getTodosUsuarios();
		}else{
//			return UsuarioServer.getUsuarios(strDBField, strFilter);
		    return UsuarioServer.getUsuariosFieldLike(strDBField, strFilter);
		}
		
	}		
	
	public ArrayList<Usuario> getAlunosPorCurso(int idCurso, String strFiltroUsuario) {				
		return UsuarioServer.getAlunosPorCurso(idCurso, strFiltroUsuario);
	}	
	
	
	
 
    

	public ArrayList<Usuario> getAlunosPorCurso(int idCurso) {				
		return UsuarioServer.getAlunosPorCurso(idCurso);
	}	
	

	public ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario, String strFilter) {				
		return UsuarioServer.getUsuariosPorTipoUsuario(id_tipo_usuario, strFilter);
	}		
	
	public ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario) {				
		return UsuarioServer.getUsuariosPorTipoUsuario(id_tipo_usuario);
	}

 
   
	
	
	public ArrayList<TipoUsuario> getTipoUsuarios() {		
		return UsuarioServer.getTipoUsuarios();
	}
	
//	public boolean associarPaisAoAluno(int id_aluno,ArrayList<String> list_id_pais){		
//		return UsuarioServer.associarPaisAoAluno(id_aluno, list_id_pais);
//	}	
	
	public ArrayList<Usuario> getTodosOsPaisDoAluno(int id_aluno){		
		return UsuarioServer.getTodosOsPaisDoAluno(id_aluno);	
	}	
	
	
	public Usuario getUsuarioPeloId(int idUsuario){
		return UsuarioServer.getUsuarioPeloId(idUsuario);
	}
	
	
	public ArrayList<Usuario> getPaisPorCurso(int idCurso, String strFilterResp, String strFilterName){
		
		return UsuarioServer.getPaisPorCurso(idCurso, strFilterResp,strFilterName);
	}
	
	public ArrayList<Usuario> getTodosPais(String strFilterResp, String strFilterName){
	    return UsuarioServer.getTodosPais(strFilterResp, strFilterName);
	}
	
//    public ArrayList<ProfessorDisciplinaRelatorio> getProfessoresDisciplinas() {
//        return UsuarioServer.getProfessoresDisciplinas();
//    }
//    
    
    public String getExcelProfessoresDisciplinas(){
        return UsuarioServer.getExcelProfessoresDisciplinas();
    }

//    @Override
//    public ArrayList<UsuarioNomeID> getAlunosTodosOuPorCurso(int idCurso, int idUnidade, boolean showAluno, boolean showPais, boolean showProfessor) {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    public ArrayList<Usuario> getUsuariosPorCursoAmbientePai(Usuario usuarioPai, int idCurso) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Usuario> getFilhoDoPaiAmbientePais(Usuario usuarioPai) {
        // TODO Auto-generated method stub
        return null;
    }
//
//    @Override
//    public ArrayList<UsuarioNomeID> getCoordenadoresAdministradoresNomeId(int idUnidade) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public boolean associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//    

//    @Override
//    public boolean associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais) {
//        // TODO Auto-generated method stub
//        return false;
//    }
    


}
