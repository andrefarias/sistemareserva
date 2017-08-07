package com.jornada.client.ambiente.administracao.avaliacao;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoColunaCidade;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoLinhaServicosRestaurante;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoTortaAtendentes;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoTortaPesquisaSatisfacao;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.GraficoTortaSobreRest;
import com.jornada.client.classes.listBoxes.MpListBox;
import com.jornada.client.classes.listBoxes.MpListBoxAtendentes;
import com.jornada.client.classes.listBoxes.MpListBoxAvaliacaoCidades;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeftAvaliacao;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.MediaServico;

public class PaginaGraficosAvaliacao extends VerticalPanel {

    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";
    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpLoading = new MpPanelLoading("images/radar.gif");

    private FlexTable flexTableGrafico;
    private FlexTable flexTableFilter;
    
    public MpListBox listBoxTipoDeGraficos;
    public MpListBox listBoxMostarApenas;    
    public MpListBoxAtendentes listBoxAtendentes;
    public MpListBoxAvaliacaoCidades listBoxAvalicaoCidades;
    public MpListBox listBoxEscala;


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
        
        listBoxAtendentes = new MpListBoxAtendentes();
        listBoxAvalicaoCidades = new MpListBoxAvaliacaoCidades();

        listBoxTipoDeGraficos = new MpListBox();
        listBoxTipoDeGraficos.setWidth("200px");    
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_CIDADE_COLUNA);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_SOBRE_RESTAURANTE_TORTA);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_PESQUISA_SATISFACAO_TORTA);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_ATENDENTES);
        listBoxTipoDeGraficos.addItem(Avaliacao.STR_GRAFICO_LINHA_SERVICOS_REST);        
        listBoxTipoDeGraficos.addChangeHandler(new MpTipoDeGraficosChangeHandler());

        listBoxMostarApenas = new MpListBox();
        listBoxMostarApenas.addItem("10", "10");
        listBoxMostarApenas.addItem("20", "20");
        listBoxMostarApenas.addItem("30", "30");
        listBoxMostarApenas.addItem("40", "40");
        listBoxMostarApenas.addItem("50", "50");
        
        listBoxEscala = new MpListBox();
        listBoxEscala.addItem("Semana", "week");
        listBoxEscala.addItem("Mês", "month");
        listBoxEscala.addItem("Ano", "year");
        listBoxEscala.setSelectItem("month");

        MpSpaceVerticalPanel mpSpaceVerticalPanel1 = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel1.setBorderWidth(1);
        mpSpaceVerticalPanel1.setWidth("100%");

        MpLabelLeftAvaliacao lblGraficos = new MpLabelLeftAvaliacao("Gráficos");
        MpLabelLeftAvaliacao lblDataInicial = new MpLabelLeftAvaliacao("Data Inicial");
        MpLabelLeftAvaliacao lblDataFinal = new MpLabelLeftAvaliacao("Data Final");
        MpLabelLeftAvaliacao lblMostrarApenas = new MpLabelLeftAvaliacao("Mostrar Apenas");
        MpLabelLeftAvaliacao lblCidades = new MpLabelLeftAvaliacao("Cidades");
        MpLabelLeftAvaliacao lblAtendentes = new MpLabelLeftAvaliacao("Atendentes");
        MpLabelLeftAvaliacao lblEscala = new MpLabelLeftAvaliacao("Escala");

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

        flexTableFilter = new FlexTable();
        flexTableFilter.setCellSpacing(2);
        flexTableFilter.setCellPadding(2);
        flexTableFilter.setBorderWidth(0);

//        flexTableFilter.setWidget(row, 0, lblGraficos);
//        flexTableFilter.setWidget(row, 1, listBoxTipoDeGraficos);
//        flexTableFilter.setWidget(row, 2, new InlineHTML(strInLineSpace));
//        flexTableFilter.setWidget(row, 3, lblDataInicial);
//        flexTableFilter.setWidget(row, 4, mpDateBoxDataInicial);
//        flexTableFilter.setWidget(row, 5, new InlineHTML(strInLineSpace));
//        flexTableFilter.setWidget(row, 6, lblDataFinal);
//        flexTableFilter.setWidget(row, 7, mpDateBoxDataFinal);
//        flexTableFilter.setWidget(row, 8, new InlineHTML(strInLineSpace));
//        flexTableFilter.setWidget(row, 9, lblMostrarApenas);
//        flexTableFilter.setWidget(row, 10, listBoxMostarApenas);
//        flexTableFilter.setWidget(row, 11, new InlineHTML(strInLineSpace));
//        flexTableFilter.setWidget(row, 12, mpLoading);
        
        
      flexTableFilter.setWidget(row, 0, lblGraficos);
      flexTableFilter.setWidget(row+1, 0, listBoxTipoDeGraficos);
      flexTableFilter.setWidget(row, 1, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 2, lblDataInicial);
      flexTableFilter.setWidget(row+1, 2, mpDateBoxDataInicial);
      flexTableFilter.setWidget(row, 3, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 4, lblDataFinal);
      flexTableFilter.setWidget(row+1, 4, mpDateBoxDataFinal);
      flexTableFilter.setWidget(row, 5, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 6, lblMostrarApenas);
      flexTableFilter.setWidget(row+1, 6, listBoxMostarApenas);
      flexTableFilter.setWidget(row, 7, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 8, lblCidades);
      flexTableFilter.setWidget(row+1, 8, listBoxAvalicaoCidades);
      flexTableFilter.setWidget(row, 9, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 10, lblAtendentes);
      flexTableFilter.setWidget(row+1, 10, listBoxAtendentes);
      flexTableFilter.setWidget(row, 11, new InlineHTML(strInLineSpace));
      flexTableFilter.setWidget(row, 12, lblEscala);
      flexTableFilter.setWidget(row+1, 12, listBoxEscala);
      flexTableFilter.setWidget(row, 13, new InlineHTML(strInLineSpace));
//      flexTableFilter.setWidget(row+1, 12, mpLoading);
      
      flexTableFilter.getWidget(1, 10).setVisible(false);
      flexTableFilter.getWidget(2, 10).setVisible(false);
      flexTableFilter.getWidget(1, 11).setVisible(false);
      flexTableFilter.getWidget(1, 12).setVisible(false);
      flexTableFilter.getWidget(2, 12).setVisible(false);
      flexTableFilter.getWidget(1, 13).setVisible(false);
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");
        
        VerticalPanel vFormPanel = new VerticalPanel();
        vFormPanel.setWidth("100%");
        
        HorizontalPanel hPanelSavelButton = new HorizontalPanel();
        hPanelSavelButton.add(btnSave);
        hPanelSavelButton.add(new InlineHTML(strInLineSpace));
        hPanelSavelButton.add(mpLoading);
        
        
        vFormPanel.add(flexTableFilter);
        vFormPanel.add(new InlineHTML(strInLineSpace));
        vFormPanel.add(hPanelSavelButton);        
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
        String strCidade = listBoxAvalicaoCidades.getSelectedValue();
        
        Date dataInicial = mpDateBoxDataInicial.getDate().getValue();
        Date dataFinal = mpDateBoxDataFinal.getDate().getValue();
        
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_CIDADE_COLUNA)) {
            GWTServiceAvaliacao.Util.getInstance().getGraficoColunaCidade(strCidade, dataInicial, dataFinal, new callbackGraficosColunaCidade());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_SOBRE_RESTAURANTE_TORTA)) {            
            GWTServiceAvaliacao.Util.getInstance().getGraficoSobreRestaurante(strCidade, dataInicial, dataFinal, new callbackGraficosTortaComoConheceuRest());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_PESQUISA_SATISFACAO_TORTA)) {            
            GWTServiceAvaliacao.Util.getInstance().getGraficoPesquisaSatisfacao(strCidade, dataInicial, dataFinal, new callbackGraficosTortaPesquisaSatisfacao());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_ATENDENTES)) {  
            String strAtendente = listBoxAtendentes.getSelectedValue();
            GWTServiceAvaliacao.Util.getInstance().getGraficoAtendentes(strCidade, strAtendente, dataInicial, dataFinal, new callbackGraficosAtendentes());
        }
        if (strGrafico.equals(Avaliacao.STR_GRAFICO_LINHA_SERVICOS_REST)) { 
            String strEscala = listBoxEscala.getSelectedValue();
            GWTServiceAvaliacao.Util.getInstance().getGraficoServicos(strCidade, strEscala, dataInicial, dataFinal, new callbackGraficosLinhaServicos());
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
    
    private class callbackGraficosAtendentes implements AsyncCallback<ArrayList<String>> {

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
            GraficoTortaAtendentes.drawChart(getFlexTableGrafico(), result, intMostrarItens);
        }

    }    
    
    private class callbackGraficosLinhaServicos implements AsyncCallback<ArrayList<ArrayList<MediaServico>>> {

        public void onFailure(Throwable caught) {
            mpLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Gráfico não pode ser carregado corretamente");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(ArrayList<ArrayList<MediaServico>> result) {
            mpLoading.setVisible(false);
//            int intMostrarItens = Integer.parseInt(listBoxMostarApenas.getSelectedValue());
            GraficoLinhaServicosRestaurante.drawChart(getFlexTableGrafico(), result, listBoxEscala.getSelectedItemText());
        }

    }    
    

    public FlexTable getFlexTableGrafico() {
        return flexTableGrafico;
    }
    
    private class ClickHandlerGerarGrafico implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            populateGrafico();            
        }
        
    }
    
    private class MpTipoDeGraficosChangeHandler implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            
            flexTableFilter.getWidget(1, 10).setVisible(false);
            flexTableFilter.getWidget(2, 10).setVisible(false);
            flexTableFilter.getWidget(1, 11).setVisible(false);
            flexTableFilter.getWidget(1, 12).setVisible(false);
            flexTableFilter.getWidget(2, 12).setVisible(false);
            flexTableFilter.getWidget(1, 13).setVisible(false);
            
            String strGrafico = listBoxTipoDeGraficos.getSelectedValue();
            
            if (strGrafico.equals(Avaliacao.STR_GRAFICO_CIDADE_COLUNA)) {
            }
            if (strGrafico.equals(Avaliacao.STR_GRAFICO_SOBRE_RESTAURANTE_TORTA)) {            
            }
            if (strGrafico.equals(Avaliacao.STR_GRAFICO_PESQUISA_SATISFACAO_TORTA)) {            
            }
            if (strGrafico.equals(Avaliacao.STR_GRAFICO_ATENDENTES)) {  
                flexTableFilter.getWidget(1, 10).setVisible(true);
                flexTableFilter.getWidget(2, 10).setVisible(true);
                flexTableFilter.getWidget(1, 11).setVisible(true);
            }
            if (strGrafico.equals(Avaliacao.STR_GRAFICO_LINHA_SERVICOS_REST)) {  
                flexTableFilter.getWidget(1, 12).setVisible(true);
                flexTableFilter.getWidget(2, 12).setVisible(true);
                flexTableFilter.getWidget(1, 13).setVisible(true);
            }

        }  
    }

}
