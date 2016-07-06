package com.jornada.client.classes.listBoxes;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.classes.salao.Saloes;


public class CopyOfMpListBoxSaloes extends MpSelection {
    
    private AsyncCallback<Saloes> callBackPopulateComboBox;
    
    public CopyOfMpListBoxSaloes(){
        
        /***********************Begin Callbacks**********************/
        callBackPopulateComboBox = new AsyncCallback<Saloes>() {
            public void onSuccess(Saloes object) {
                try {
                    finishLoadingListBox();

                    
                    addItem(object.getSalaoInterno().getNomeSalao(),object.getSalaoInterno().getNomeSalao());
                    addItem(object.getSalaoExternoAberto().getNomeSalao(),object.getSalaoExternoAberto().getNomeSalao());
                    addItem(object.getSalaoExternoCoberto().getNomeSalao(),object.getSalaoExternoCoberto().getNomeSalao());
                    addItem(object.getSalaoChurrasqueira().getNomeSalao(),object.getSalaoChurrasqueira().getNomeSalao());
                    
                    setVisibleItemCount(1);

                    // DomEvent.fireNativeEvent(Document.get().createChangeEvent(),
                    // MpSelectionTipoUsuario.this);
                    try {
                        DomEvent.fireNativeEvent(Document.get().createChangeEvent(),CopyOfMpListBoxSaloes.this);
                    } catch (Exception ex) {
                        logoutAndRefreshPage();
                    }
                } catch (Exception ex) {
                    logoutAndRefreshPage();
                }
            }

            public void onFailure(Throwable cautch) {
                logoutAndRefreshPage();
//              listBoxCurso.addItem(new Label("Erro popular curso.").getText());
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
        GWTServiceReserva.Util.getInstance().getSaloes(callBackPopulateComboBox);
    }
    
    private void startLoadingListBox(){
        clear();
        addItem(CARREGANDO,Integer.toString(-1));
    }
    
    private void finishLoadingListBox(){
        clear();
    }
    
}