package com.jornada.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;
import com.jornada.shared.classes.salao.Saloes;

public interface GWTServiceReservaAsync {

//	public void AdicionarReserva(Reserva periodo, AsyncCallback<Integer> callback);	
    public void adicionarPeriodoString(Reserva periodo, AsyncCallback<String> callback);
	public void getReservas(AsyncCallback<ArrayList<Reserva>> callback);
	public void getReservas(Date dataReserva, String strTurno, AsyncCallback<ArrayList<Reserva>> callback);
	public void getReservasExcel(Date dataReserva, String strTurno, AsyncCallback<String> callback);
	public void getNumeroClientes(Date dataReserva, String strTurno, String strSalao, AsyncCallback<Clientes> callback);
	public void getSaloes(AsyncCallback<Saloes> callback);
	public void deleteRow(int idReserva, AsyncCallback<Boolean> callback);  
	public void updateRow(Reserva periodo, AsyncCallback<Boolean> callback);
	public void getQuantidadeClientesNosSaloes(Date dataReserva, String strTurno, AsyncCallback<Saloes> callback);
//	public void getPeriodosPeloCursoAmbienteProfessor(Usuario usuario, int idCurso, AsyncCallback<ArrayList<Reserva>> callback);	
//	public void getPeriodos(String strFilter, AsyncCallback<ArrayList<Reserva>> callback);	
//	public void getPeriodos(AsyncCallback<ArrayList<Reserva>> callback);	
//	public void getCursos(AsyncCallback<ArrayList<Curso>> callback);	

		
	

}
