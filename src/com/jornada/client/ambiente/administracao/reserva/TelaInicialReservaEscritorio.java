package com.jornada.client.ambiente.administracao.reserva;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialReservaEscritorio extends Composite {

    public static final int intWidthTable = 1400;
    public static final int intHeightTable = 500;

    public AdicionarReserva adicionarReserva;
//    private EditarReserva editarReserva;

    TextConstants txtConstants;
    
    VerticalPanel vPanelBody;

    private static TelaInicialReservaEscritorio uniqueInstance;

    public static TelaInicialReservaEscritorio getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TelaInicialReservaEscritorio();
        } else {
//            uniqueInstance.adicionarPeriodo.updateClientData();
//            uniqueInstance.editarReserva.updateClientData();
//            uniqueInstance.populateGrid();
        }
        return uniqueInstance;
    }

    private TelaInicialReservaEscritorio() {

        txtConstants = GWT.create(TextConstants.class);

        adicionarReserva = AdicionarReserva.getInstance(this);
//        editarReserva = EditarReserva.getInstance(this);

        TabPanel tabLayoutPanel = new TabPanel();
        tabLayoutPanel.setWidth("100%");
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialReservaEscritorio.intHeightTable) + "px");
        tabLayoutPanel.setAnimationEnabled(true);
        
        vPanelBody = new VerticalPanel();
//        vPanelBody.setWidth("100%");
        vPanelBody.add(adicionarReserva);
//        vPanelBody.add(editarReserva);

        tabLayoutPanel.add(vPanelBody, new MpHeaderWidget("Adicionar/Alterar Reserva", "images/plus-circle.png"));
//        tabLayoutPanel.add(editarReserva, new MpHeaderWidget("Alterar Reserva", "images/comment_edit.png"));
        tabLayoutPanel.selectTab(0);


        initWidget(tabLayoutPanel);

    }

    public void updateMessageAndGrid() {
//        editarReserva.populateGrid();
        adicionarReserva.updateMessageAndGrid();
    }

}
