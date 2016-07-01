package com.jornada.client.classes.listBoxes.ambiente.professor;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.Usuario;

public class MpListBoxPeriodoAmbienteProfessor extends MpSelection {

//	private AsyncCallback<ArrayList<Reserva>> callBackPopulateComboBox;
	
//	private Usuario usuario;

	public MpListBoxPeriodoAmbienteProfessor(Usuario usuario) {
		
//		this.usuario = usuario;


//		/*********************** Begin Callbacks **********************/
//		callBackPopulateComboBox = new AsyncCallback<ArrayList<Reserva>>() {
//			public void onSuccess(ArrayList<Reserva> lista) {
//				try {
//					finishLoadingListBox();
//
//					for (Reserva object : lista) {
////						addItem(object.getNomePeriodo(),Integer.toString(object.getIdPeriodo()));
//					}
//
//					setVisibleItemCount(1);
//
//					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpSelectionPeriodoAmbienteProfessor.this);
//					try {
//						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpListBoxPeriodoAmbienteProfessor.this);
//					} catch (Exception ex) {
//						logoutAndRefreshPage();
//					}
//				} catch (Exception ex) {
//					logoutAndRefreshPage();
//				}
//				
//			}
//
//			public void onFailure(Throwable cautch) {
//				logoutAndRefreshPage();
//				clear();
//				addItem(new Label(ERRO_POPULAR).getText());
//
//			}
//		};

		/*********************** End Callbacks **********************/

	}

	public void populateComboBox(int idCurso) {
		startLoadingListBox();
//		GWTServiceReserva.Util.getInstance().getPeriodosPeloCursoAmbienteProfessor(this.usuario, idCurso,callBackPopulateComboBox);
	}

	private void startLoadingListBox() {
		clear();
		addItem(CARREGANDO, Integer.toString(-1));
	}

//	private void finishLoadingListBox() {
//		clear();
//	}

}
