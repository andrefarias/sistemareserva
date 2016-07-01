package com.jornada.server.service;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.server.classes.ReservaServer;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;

public class GWTServiceReservaImpl extends RemoteServiceServlet implements	GWTServiceReserva {

	private static final long serialVersionUID = 3912070639094359182L;

	
    @Override
    public String adicionarPeriodoString(Reserva object) {
        return ReservaServer.AdicionarReservaString(object);
    }
	   	
	public ArrayList<Reserva> getReservas() {		
		return ReservaServer.getReservas();
	}	
		
	public Clientes getNumeroClientes(Date dataReserva, String strTurno){
	    return ReservaServer.getNumeroClientes(dataReserva, strTurno);
	}
	
	public boolean deleteRow(int idReserva) {
		return ReservaServer.deleteRow(idReserva);
	}
	
	public boolean updateRow(Reserva periodo) {
		return ReservaServer.updateRow(periodo);
	}
	
	public ArrayList<Reserva> getReservas(Date dataReserva, String strTurno){
	    return ReservaServer.getReservas(dataReserva, strTurno);
	}
	
    @Override
    public String getReservasExcel(Date dataReserva, String strTurno) {

        return ReservaServer.getReservasExcel(dataReserva, strTurno);
    }

    


	
	/**
	 * Quick fix to this is in the RPC implementation at server side override method checkPermutationStrongName() with empty implementation.
	 */
	@Override
	protected void checkPermutationStrongName() throws SecurityException {
	    return;
	}





}
