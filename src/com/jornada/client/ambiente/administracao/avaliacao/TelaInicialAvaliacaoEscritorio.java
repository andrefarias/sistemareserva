package com.jornada.client.ambiente.administracao.avaliacao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.widgets.header.MpHeaderWidget;
import com.jornada.client.content.i18n.TextConstants;

public class TelaInicialAvaliacaoEscritorio extends Composite {

    public static final int intWidthTable = 1400;
    public static final int intHeightTable = 500;

    private PaginaAdicionarAvaliacao adicionarAvaliacao;
    private PaginaGraficosAvaliacao graficosAvaliacao;
    private PaginaEditarAvaliacao editarAvaliacao;

    TextConstants txtConstants;
    
    VerticalPanel vPanelBody;

    private static TelaInicialAvaliacaoEscritorio uniqueInstance;

    public static TelaInicialAvaliacaoEscritorio getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TelaInicialAvaliacaoEscritorio();
        } else {
//            uniqueInstance.adicionarPeriodo.updateClientData();
//            uniqueInstance.editarReserva.updateClientData();
//            uniqueInstance.adicionarReserva.updateMessageAndGrid();
        }
        return uniqueInstance;
    }

    private TelaInicialAvaliacaoEscritorio() {

        txtConstants = GWT.create(TextConstants.class);

        adicionarAvaliacao = PaginaAdicionarAvaliacao.getInstance(this);
        editarAvaliacao = PaginaEditarAvaliacao.getInstance(this);
        graficosAvaliacao = PaginaGraficosAvaliacao.getInstance(this);
//        editarReserva = EditarReserva.getInstance(this);

        TabPanel tabLayoutPanel = new TabPanel();
        tabLayoutPanel.setWidth("100%");
        tabLayoutPanel.setHeight(Integer.toString(TelaInicialAvaliacaoEscritorio.intHeightTable) + "px");
        tabLayoutPanel.setAnimationEnabled(true);
        
//        vPanelBody = new VerticalPanel();
//        vPanelBody.setWidth("100%");
//        vPanelBody.add(adicionarAvaliacao);
//        vPanelBody.add(graficosAvaliacao);
//        vPanelBody.add(editarReserva);

        tabLayoutPanel.add(adicionarAvaliacao, new MpHeaderWidget("Adicionar Pesquisa Satisfação", "images/plus-circle.png"));
        tabLayoutPanel.add(editarAvaliacao, new MpHeaderWidget("Editar Observações", "images/comment_edit.png"));
        tabLayoutPanel.add(graficosAvaliacao, new MpHeaderWidget("Gráficos Pesquisa Satisfação", "images/chart-icon_16.png"));
//        tabLayoutPanel.add(editarReserva, new MpHeaderWidget("Alterar Reserva", "images/comment_edit.png"));
        tabLayoutPanel.selectTab(0);


        initWidget(tabLayoutPanel);

    }

    public void updateMessageAndGrid() {
//        editarReserva.populateGrid();
//        adicionarReserva.updateMessageAndGrid();
    }

}
