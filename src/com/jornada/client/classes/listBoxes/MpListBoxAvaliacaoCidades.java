package com.jornada.client.classes.listBoxes;

import java.util.ArrayList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class MpListBoxAvaliacaoCidades extends MpListBox {	
	
	private AsyncCallback<ArrayList<String>> callBackPopulateComboBox;
	
	public MpListBoxAvaliacaoCidades(){

		
		/***********************Begin Callbacks**********************/
		callBackPopulateComboBox = new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> lista) {
				try {
					finishLoadingListBox();

					addItem(Avaliacao.TODAS,Avaliacao.TODAS);
					for (String object : lista) {
						addItem(object,object);
					}

					setVisibleItemCount(1);

					// DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
					// MpSelectionTipoUsuario.this);
					try {
						DomEvent.fireNativeEvent(Document.get().createChangeEvent(),MpListBoxAvaliacaoCidades.this);
					} catch (Exception ex) {
						logoutAndRefreshPage();
					}
				} catch (Exception ex) {
					logoutAndRefreshPage();
				}
			}

			public void onFailure(Throwable cautch) {
				logoutAndRefreshPage();
//				listBoxCurso.addItem(new Label("Erro popular curso.").getText());
				clear();
				addItem(new Label(ERRO_POPULAR).getText());

			}
		};

		/***********************End Callbacks**********************/

		/******** Begin Populate ********/
		populateComboBox();
		/******** End Populate ********/
		
	}	
	
	public void populateComboBox() {
		startLoadingListBox();
		GWTServiceAvaliacao.Util.getInstance().getCidades(callBackPopulateComboBox);
	}
	
	private void startLoadingListBox(){
		clear();
		addItem(CARREGANDO,Integer.toString(-1));
	}
	
	private void finishLoadingListBox(){
		clear();
	}


}
