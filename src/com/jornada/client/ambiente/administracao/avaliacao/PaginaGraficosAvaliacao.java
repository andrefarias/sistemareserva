package com.jornada.client.ambiente.administracao.avaliacao;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoColunaCidade;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoTortaSobreRest;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoTortaPesquisaSatisfacao;
import com.jornada.client.classes.listBoxes.MpListBox;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeftAvaliacao;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class PaginaGraficosAvaliacao extends VerticalPanel {

    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";
    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

    private FlexTable flexTableGrafico;
    
    public MpListBox listBoxTipoDeGraficos;

    public MpListBox listBoxMostarApenas;

    public MpDateBoxWithImage mpDateBoxDataInicial;
    public MpDateBoxWithImage mpDateBoxDataFinal;

    @SuppressWarnings("unused")
    private TelaInicialAvaliacaoEscritorio telaInicialReserva;

    TextConstants txtConstants;

    private static PaginaGraficosAvaliacao uniqueInstance;

    public static PaginaGraficosAvaliacao getInstance(final TelaInicialAvaliacaoEscritorio telaInicialPeriodo) {

        if (uniqueInstance == null) {
            uniqueInstance = new PaginaGraficosAvaliacao(telaInicialPeriodo);
        }

        return uniqueInstance;

    }

    private PaginaGraficosAvaliacao(final TelaInicialAvaliacaoEscritorio telaInicialAvaliacao) {

        txtConstants = GWT.create(TextConstants.class);

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpLoading.setTxtLoading(txtConstants.geralCarregando());
        mpLoading.show();
        mpLoading.setVisible(false);

        listBoxTipoDeGraficos = new MpListBox();
        listBoxTipoDeGraficos.setWidth("200px");    
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_CIDADE_COLUNA);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_SOBRE_RESTAURANTE_TORTA);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_PESQUISA_SATISFACAO_TORTA);

        listBoxMostarApenas = new MpListBox();
        listBoxMostarApenas.addItem("10", "10");
        listBoxMostarApenas.addItem("20", "20");
        listBoxMostarApenas.addItem("30", "30");
        listBoxMostarApenas.addItem("40", "40");
        listBoxMostarApenas.addItem("50", "50");

        MpSpaceVerticalPanel mpSpaceVerticalPanel1 = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel1.setBorderWidth(1);
        mpSpaceVerticalPanel1.setWidth("100%");

        MpLabelLeftAvaliacao lblGraficos = new MpLabelLeftAvaliacao("Gráficos");
        MpLabelLeftAvaliacao lblDataInicial = new MpLabelLeftAvaliacao("Data Inicial");
        MpLabelLeftAvaliacao lblDataFinal = new MpLabelLeftAvaliacao("Data Final");
        MpLabelLeftAvaliacao lblMostrarApenas = new MpLabelLeftAvaliacao("Mostrar Apenas");

        Date data = new Date();
        int month_6 = 1000 * 60 * 60 * 24 * 30 * 6;

        data.setTime(data.getTime() - month_6);
        mpDateBoxDataInicial = new MpDateBoxWithImage();
        mpDateBoxDataInicial.getDate().setValue(data);

        data.setTime(data.getTime() + month_6);
        mpDateBoxDataFinal = new MpDateBoxWithImage();
        mpDateBoxDataFinal.getDate().setValue(data);
        
        MpImageButton btnSave = new MpImageButton("Gerar Gráfico", "images/categorycheck.png");        
        btnSave.addClickHandler(new ClickHandlerGerarGrafico());

        int row = 1;

        flexTableGrafico = new FlexTable();
        flexTableGrafico.setCellSpacing(2);
        flexTableGrafico.setCellPadding(2);
        flexTableGrafico.setBorderWidth(0);

        FlexTable flexTableFilter = new FlexTable();
        flexTableFilter.setCellSpacing(2);
        flexTableFilter.setCellPadding(2);
        flexTableFilter.setBorderWidth(0);

        flexTableFilter.setWidget(row, 0, lblGraficos);
        flexTableFilter.setWidget(row, 1, listBoxTipoDeGraficos);
        flexTableFilter.setWidget(row, 2, new InlineHTML(strInLineSpace));
        flexTableFilter.setWidget(row, 3, lblDataInicial);
        flexTableFilter.setWidget(row, 4, mpDateBoxDataInicial);
        flexTableFilter.setWidget(row, 5, new InlineHTML(strInLineSpace));
        flexTableFilter.setWidget(row, 6, lblDataFinal);
        flexTableFilter.setWidget(row, 7, mpDateBoxDataFinal);
        flexTableFilter.setWidget(row, 8, new InlineHTML(strInLineSpace));
        flexTableFilter.setWidget(row, 9, lblMostrarApenas);
        flexTableFilter.setWidget(row, 10, listBoxMostarApenas);
        flexTableFilter.setWidget(row, 11, new InlineHTML(strInLineSpace));
        flexTableFilter.setWidget(row, 12, mpLoading);
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");
        
        VerticalPanel vFormPanel = new VerticalPanel();
        vFormPanel.setWidth("100%");
        
        vFormPanel.add(flexTableFilter);
        vFormPanel.add(new InlineHTML(strInLineSpace));
        vFormPanel.add(btnSave);        
        vFormPanel.add(new InlineHTML(strInLineSpace));
        vFormPanel.add(mpSpaceVerticalPanel);
        vFormPanel.add(flexTableGrafico);

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(vFormPanel);
        scrollPanel.setWidth("100%");

        super.setWidth("100%");

        super.add(scrollPanel);

    }


    protected void populateGrafico() {
        mpLoading.setVisible(true);

        String strGrafico = listBoxTipoDeGraficos.getSelectedValue();
        
        Date dataInicial = mpDateBoxDataInicial.getDate().getValue();
        Date dataFinal = mpDateBoxDataFinal.getDate().getValue();
//        getFlexTableGrafico().clear();
        
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_CIDADE_COLUNA)) {
            GWTServiceAvaliacao.Util.getInstance().getGraficoColunaCidade(dataInicial, dataFinal, new callbackGraficosColunaCidade());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_SOBRE_RESTAURANTE_TORTA)) {            
            GWTServiceAvaliacao.Util.getInstance().getGraficoSobreRestaurante(dataInicial, dataFinal, new callbackGraficosTortaComoConheceuRest());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_PESQUISA_SATISFACAO_TORTA)) {            
            GWTServiceAvaliacao.Util.getInstance().getGraficoPesquisaSatisfacao(dataInicial, dataFinal, new callbackGraficosTortaPesquisaSatisfacao());
        }
        
    }

    private class callbackGraficosColunaCidade implements AsyncCallback<ArrayList<String>> {

        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Gráfico não pode ser carregado corretamente");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<String> result) {
            mpLoading.setVisible(false);
            int intMostrarItens = Integer.parseInt(listBoxMostarApenas.getSelectedValue());
            GraficoColunaCidade.drawChart(getFlexTableGrafico(), result, intMostrarItens);
        }

    }
    
    private class callbackGraficosTortaComoConheceuRest implements AsyncCallback<ArrayList<ArrayList<String>>> {

        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Gráfico não pode ser carregado corretamente");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<ArrayList<String>> result) {
            mpLoading.setVisible(false);
            int intMostrarItens = Integer.parseInt(listBoxMostarApenas.getSelectedValue());
            GraficoTortaSobreRest.drawChart(getFlexTableGrafico(), result, intMostrarItens);
        }

    }
    
    private class callbackGraficosTortaPesquisaSatisfacao implements AsyncCallback<ArrayList<ArrayList<String>>> {

        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Gráfico não pode ser carregado corretamente");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<ArrayList<String>> result) {
            mpLoading.setVisible(false);
            int intMostrarItens = Integer.parseInt(listBoxMostarApenas.getSelectedValue());
            GraficoTortaPesquisaSatisfacao.drawChart(getFlexTableGrafico(), result, intMostrarItens);
        }

    }
        
    
//    private class callbackGraficosLinhaCidade implements AsyncCallback<ArrayList<ArrayList<String>>> {
//
//        public void onFailure(Throwable caught) {
//            mpLoading.setVisible(false);
//            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//            mpDialogBoxWarning.setBodyText("Gráfico não pode ser carregado corretamente");
//            mpDialogBoxWarning.showDialog();
//        }
//
//        @Override
//        public void onSuccess(ArrayList<ArrayList<String>> result) {
//            mpLoading.setVisible(false);
//            int intMostrarItens = Integer.parseInt(listBoxMostarApenas.getSelectedValue());
//            GraficoLinhaCidade.drawChart(getFlexTableGrafico(), result, intMostrarItens);
//        }
//
//    }

    public FlexTable getFlexTableGrafico() {
        return flexTableGrafico;
    }
    
    private class ClickHandlerGerarGrafico implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            populateGrafico();            
        }
        
    }

}
