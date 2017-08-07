	package com.jornada.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;

public interface GWTServiceUsuarioAsync {

	public void AdicionarUsuario(Usuario usuario, AsyncCallback<String> callback);	
	public void updateUsuarioRow(Usuario usuario, AsyncCallback<String> callback);	
	public void atualizarSenha(int idUsuario, String senha, boolean forcarPrimeiroLogin, AsyncCallback<Boolean> callback);
	public void deleteUsuarioRow(int id_usuario, AsyncCallback<Boolean> callback);
//	public void gerarExcelUsuario(AsyncCallback<String> asyncCallback);
	public void getUsuarios(AsyncCallback<ArrayList<Usuario>> callback);	
	public void getUsuarioPeloId(int idUsuario, AsyncCallback<Usuario> callback);
	public void getUsuarios(String strFilter, AsyncCallback<ArrayList<Usuario>> callback);
//	public void getUsuarios(String strDBField, String strFilter, AsyncCallback<ArrayList<Usuario>> callback);		
	public void getTipoUsuarios(AsyncCallback<ArrayList<TipoUsuario>> callbackGetTipoUsuarios);
    public void getTodosUsuarios(AsyncCallback<ArrayList<Usuario>> callback);
    public void getAtendentes(AsyncCallback<ArrayList<String>> callback);
		

}
