package com.jornada.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;

@RemoteServiceRelativePath("GWTServiceReserva")
public interface GWTServiceReserva  extends RemoteService {

    
    public String adicionarPeriodoString(Reserva periodo);  
	public ArrayList<Reserva> getReservas();
	public ArrayList<Reserva> getReservas(Date dataReserva, String strTurno);
	public String getReservasExcel(Date dataReserva, String strTurno);
	public Clientes getNumeroClientes(Date dataReserva, String strTurno);
	public boolean deleteRow(int idReserva); 
	public boolean updateRow(Reserva object);
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GWTServiceReservaAsync instance;
		public static GWTServiceReservaAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GWTServiceReserva.class);
			}
			return instance;
		}
	}


    
	
}
