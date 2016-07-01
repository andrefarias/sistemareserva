package com.jornada.client.ambiente.administracao.reserva.dialog;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jornada.client.classes.widgets.label.MpLabelError;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceReserva;
//public class MpDialogBoxExcelReserva extends DecoratedPopupPanel {


@SuppressWarnings("deprecation")
public class MpDialogBoxExcelReserva extends DecoratedPopupPanel implements ClickListener {

    static TextConstants txtConstants = GWT.create(TextConstants.class);
    
    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");
    
    private static MpDialogBoxExcelReserva uniqueInstance;
    
    Grid grid;
    Anchor anchor;
    
    
    public static MpDialogBoxExcelReserva getInstance(Date dataReserva, String strTurno) {

        if (uniqueInstance == null) {
            uniqueInstance = new MpDialogBoxExcelReserva(dataReserva, strTurno);
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelParaReservas(dataReserva, strTurno);
        } else {
            uniqueInstance.showDialog();
            uniqueInstance.generateExcelParaReservas(dataReserva, strTurno);
        }
        return uniqueInstance;
    }
    


    private MpDialogBoxExcelReserva(Date dataReserva, String strTurno) {

        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);
    }

    private void showDialog() {


        mpPanelLoading.setVisible(true);

        Button closeButton = new Button(txtConstants.geralFecharJanela(), this);
        closeButton.addKeyUpHandler(new EnterKeyUpHandler());
        closeButton.setFocus(true);
        closeButton.setStyleName("cw-Button");

        DockPanel dock = new DockPanel();
        dock.setSpacing(4);

        dock.add(closeButton, DockPanel.SOUTH);

        dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
        dock.setWidth("100%");
        
        VerticalPanel vBody = new VerticalPanel();
        vBody.setStyleName("dialogVPanelWhite");

        vBody.add(dock);
        setWidget(vBody);

//      setWidget(dock);
        
        grid = new Grid(4,1);       
        grid.setCellSpacing(3);
        grid.setCellPadding(3);
        grid.setBorderWidth(0); 
        
        Label lblBaixarArquivo = new Label(txtConstants.usuarioBaixarPlanilhaExcel());
        
        anchor = new Anchor(txtConstants.usuarioCliqueAquiParaBaixar());
        
        MpLabelError lblErro = new MpLabelError(); 
        
        grid.setWidget(0, 0, mpPanelLoading);
        grid.setWidget(1, 0, lblBaixarArquivo);
        grid.setWidget(2, 0, anchor);
        grid.setWidget(3, 0, lblErro);
        
        grid.getRowFormatter().setVisible(0, true);
        grid.getRowFormatter().setVisible(1, false);
        grid.getRowFormatter().setVisible(2, false);
        grid.getRowFormatter().setVisible(3, false);

        
        dock.add(grid, DockPanel.SOUTH);

        
        

        super.setGlassEnabled(true);
        super.setAnimationEnabled(true);
        center();
        show();
        closeButton.setFocus(true);
    }

    public void onClick(Widget sender) {
         hide();
    }

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                 hide();
            }
        }
    }
    
    
    private void generateExcelParaReservas(Date dataReserva, String strTurno) {
        GWTServiceReserva.Util.getInstance().getReservasExcel(dataReserva, strTurno,  new CallBackExcel());
    }
    
  
    
    
    private class CallBackExcel implements AsyncCallback<String> {

        @Override
        public void onFailure(Throwable caught) {
            mpPanelLoading.setVisible(false);
            grid.getRowFormatter().setVisible(0, false);
            grid.getRowFormatter().setVisible(1, false);
            grid.getRowFormatter().setVisible(2, false);
            grid.getRowFormatter().setVisible(3, true);
        }

        @Override
        public void onSuccess(String result) {
            
            result = result.replace("//", "/");
            anchor.setHref(GWT.getHostPageBaseURL()+result);
            mpPanelLoading.setVisible(false);
            grid.getRowFormatter().setVisible(0, false);
            grid.getRowFormatter().setVisible(1, true);
            grid.getRowFormatter().setVisible(2, true);
            grid.getRowFormatter().setVisible(3, false);
        }
    }
    
      
}
