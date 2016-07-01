package com.jornada.client.ambiente.salao.reserva;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialReservaSalao extends Composite {

    public static final int intWidthTable = 1400;
    public static final int intHeightTable = 500;

//    public AdicionarReserva adicionarReserva;
    private VisualizarReserva editarReserva;

    TextConstants txtConstants;
    
    VerticalPanel vPanelBody;

    private static TelaInicialReservaSalao uniqueInstance;

    public static TelaInicialReservaSalao getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TelaInicialReservaSalao();
        } else {
//            uniqueInstance.adicionarPeriodo.updateClientData();
//            uniqueInstance.editarReserva.updateClientData();
            uniqueInstance.populateGrid();
        }
        return uniqueInstance;
    }

    private TelaInicialReservaSalao() {

        txtConstants = GWT.create(TextConstants.class);

//        adicionarReserva = AdicionarReserva.getInstance(this);
        editarReserva = VisualizarReserva.getInstance();

        TabPanel tabLayoutPanel = new TabPanel();
        tabLayoutPanel.setWidth("100%");
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialReservaSalao.intHeightTable) + "px");
        tabLayoutPanel.setAnimationEnabled(true);
        
        vPanelBody = new VerticalPanel();
//        vPanelBody.setWidth("100%");
//        vPanelBody.add(adicionarReserva);
        vPanelBody.add(editarReserva);

        tabLayoutPanel.add(vPanelBody, new MpHeaderWidget("Visualizar Reserva", "images/analysis-icon-16.png"));
//        tabLayoutPanel.add(editarReserva, new MpHeaderWidget("Alterar Reserva", "images/comment_edit.png"));
        tabLayoutPanel.selectTab(0);


        initWidget(tabLayoutPanel);

    }

    public void populateGrid() {
        editarReserva.populateGrid();
//        adicionarReserva.updateMessageAndGrid();
    }

}
