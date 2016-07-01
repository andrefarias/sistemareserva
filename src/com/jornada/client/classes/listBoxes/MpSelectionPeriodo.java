package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.shared.classes.Reserva;

public class MpSelectionPeriodo extends MpSelection {

	public MpSelectionPeriodo() {

		new AsyncCallback<ArrayList<Reserva>>() {
			public void onSuccess(ArrayList<Reserva> lista) {
				
				try {

					finishLoadingListBox();

					for (Reserva object : lista) {
						addItem(object.getNomeReserva(),Integer.toString(object.getIdReserva()));
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionPeriodo.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(), MpSelectionPeriodo.this);
					} catch (Exception ex) {
						logoutAndRefreshPage();
						System.out.println("Error:" + ex.getMessage());
					}
				} catch (Exception ex) {
					logoutAndRefreshPage();
				}

			}

			public void onFailure(Throwable cautch) {
				logoutAndRefreshPage();
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/*********************** End Callbacks **********************/

	}

	public void populateComboBox(int idCurso) {		
		startLoadingListBox();
//		GWTServiceReserva.Util.getInstance().getReservas(idCurso,callBackPopulateComboBox);
	}

	private void startLoadingListBox() {
		clear();
		addItem(CARREGANDO, Integer.toString(-1));
	}

	private void finishLoadingListBox() {
		clear();
	}

}
