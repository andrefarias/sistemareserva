package com.jornada.client.ambiente.administracao.avaliacao;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpListBoxConheceuRest;
import com.jornada.client.classes.listBoxes.MpListBoxNivel;
import com.jornada.client.classes.listBoxes.MpListBoxSimNao;
import com.jornada.client.classes.listBoxes.suggestbox.MpSuggestBox;
import com.jornada.client.classes.listBoxes.suggestbox.MpSuggestOracleAtendentes;
import com.jornada.client.classes.listBoxes.suggestbox.MpSuggestOracleCidadesSP;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeftAvaliacao;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpTextArea;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class PaginaAdicionarAvaliacao extends VerticalPanel {

//    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";   
    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";   
    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");


    public MpListBoxNivel listBoxRestMoquem;
    public MpListBoxNivel listBoxAmbiente;
    public MpListBoxNivel listBoxAtendimento;
    public MpListBoxNivel listBoxQualidade;
    public MpListBoxNivel listBoxEspacoKids;
    public MpListBoxNivel listBoxCozinha;
    
    public MpListBoxSimNao listBoxRecomendariaRest;    
    public MpListBoxConheceuRest listBoxComoConheceuRest;
    public MpListBoxSimNao listBoxVoltariaRest;  
    

    private MpSuggestBox txtAtendente;
    private MpSuggestBox txtCidade;
    private MpTextBox txtEmail;
    private MpTextBox txtTelefone;
    public MpDateBoxWithImage mpDateBoxDataAvaliacao;
    private MpTextArea txtSugestao;
    

    @SuppressWarnings("unused")
    private TelaInicialAvaliacaoEscritorio telaInicialReserva;

    TextConstants txtConstants;

    private static PaginaAdicionarAvaliacao uniqueInstance;
//    private EditarAvaliacao editarReserva;

    public static PaginaAdicionarAvaliacao getInstance(final TelaInicialAvaliacaoEscritorio telaInicialPeriodo) {

        if (uniqueInstance == null) {
            uniqueInstance = new PaginaAdicionarAvaliacao(telaInicialPeriodo);
        }

        return uniqueInstance;

    }

    @SuppressWarnings("deprecation")
    private PaginaAdicionarAvaliacao(final TelaInicialAvaliacaoEscritorio telaInicialAvaliacao) {

        txtConstants = GWT.create(TextConstants.class);

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        hPanelLoading.show();
        hPanelLoading.setVisible(false);

        listBoxRestMoquem = new MpListBoxNivel();
        listBoxAmbiente = new MpListBoxNivel();
        listBoxAtendimento = new MpListBoxNivel();
        listBoxQualidade = new MpListBoxNivel();
        listBoxEspacoKids = new MpListBoxNivel();
        listBoxCozinha = new MpListBoxNivel();
        
        listBoxRecomendariaRest = new MpListBoxSimNao();
        listBoxComoConheceuRest = new MpListBoxConheceuRest();
        listBoxVoltariaRest = new MpListBoxSimNao();
        
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel1 = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel1.setBorderWidth(1);
        mpSpaceVerticalPanel1.setWidth("100%");
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel2 = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel2.setBorderWidth(1);
        mpSpaceVerticalPanel2.setWidth("100%");

        
        MpSuggestOracleCidadesSP oracleCidadeSP = new MpSuggestOracleCidadesSP();
        MpSuggestOracleAtendentes oracleCidadeAtendentes = new MpSuggestOracleAtendentes();



        txtCidade = new MpSuggestBox(oracleCidadeSP);
        txtEmail = new MpTextBox();
//        txtMesa = new MpTextBox();
        txtTelefone = new MpTextBox();
        txtAtendente = new MpSuggestBox(oracleCidadeAtendentes);
        txtSugestao = new MpTextArea();
        mpDateBoxDataAvaliacao = new MpDateBoxWithImage();
        mpDateBoxDataAvaliacao.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));

        mpDateBoxDataAvaliacao.getDate().setValue(new Date());

        txtTelefone.setTitle("Telefone é um campo obrigatório!");
        mpDateBoxDataAvaliacao.setTitle("Data da Reserva é um campo obrigatório!");


        txtSugestao.setStyleName("design_text_boxes");

        MpLabelLeftAvaliacao lblRestMoquem = new MpLabelLeftAvaliacao("Restaurante Moquém");
        MpLabelLeftAvaliacao lblAmbiente = new MpLabelLeftAvaliacao("Ambiente");
        MpLabelLeftAvaliacao lblAtendimento = new MpLabelLeftAvaliacao("Atendimento");
        MpLabelLeftAvaliacao lblQualidade = new MpLabelLeftAvaliacao("Qualidade");
        MpLabelLeftAvaliacao lblEspacoKids = new MpLabelLeftAvaliacao("Espaço Kids / Recreação");
        MpLabelLeftAvaliacao lblCozinha = new MpLabelLeftAvaliacao("Cozinha");
        MpLabelLeftAvaliacao lblRecomendariaRest = new MpLabelLeftAvaliacao("Recomendaria o Restaurante?");
        MpLabelLeftAvaliacao lblComoConheceuRest = new MpLabelLeftAvaliacao("Como conheceu o Restaurante?");
        MpLabelLeftAvaliacao lblVoceVoltaria = new MpLabelLeftAvaliacao("Você Voltaria?");
        MpLabelLeftAvaliacao lblCidade = new MpLabelLeftAvaliacao("Cidade");
        MpLabelLeftAvaliacao lblData = new MpLabelLeftAvaliacao("Data");
        MpLabelLeftAvaliacao lblEmail = new MpLabelLeftAvaliacao("Email");
        MpLabelLeftAvaliacao lblTelefone = new MpLabelLeftAvaliacao("Telefone");
        MpLabelLeftAvaliacao lblSugestao = new MpLabelLeftAvaliacao("Sugestão");
        MpLabelLeftAvaliacao lblAtendente = new MpLabelLeftAvaliacao("Atendente");
        
//        lblNumeroTotal.setStyleName("label_lotacao_bold_13px");

     
        mpDateBoxDataAvaliacao.getDate().setWidth("150px");

        txtCidade.setWidth("200px");
        txtEmail.setWidth("200px");
        txtTelefone.setWidth("200px");
        txtAtendente.setWidth("200px");

        txtSugestao.setWidth("500px");
        txtSugestao.setHeight("50px");
        
        int row = 1;
        
        FlexTable flexTableCliente  = new FlexTable();
        flexTableCliente.setCellSpacing(2);
        flexTableCliente.setCellPadding(2);
        flexTableCliente.setBorderWidth(0);

        flexTableCliente.setWidget(row, 0, lblCidade);        
        flexTableCliente.setWidget(row, 1, txtCidade);
        flexTableCliente.setWidget(row, 2, new InlineHTML(strInLineSpace));        
        flexTableCliente.setWidget(row, 3, lblData);        
        flexTableCliente.setWidget(row, 4, mpDateBoxDataAvaliacao);
        flexTableCliente.setWidget(row, 5, new InlineHTML(strInLineSpace));
        flexTableCliente.setWidget(row, 6, lblEmail);        
        flexTableCliente.setWidget(row, 7, txtEmail);
        flexTableCliente.setWidget(row, 9, new InlineHTML(strInLineSpace));
        flexTableCliente.setWidget(row, 9, lblTelefone);        
        flexTableCliente.setWidget(row, 10, txtTelefone);
//        flexTableCliente.setWidget(row, 8, new InlineHTML(strInLineSpace));

        
        FlexTable flexTable = new FlexTable();
        flexTable.setCellSpacing(2);
        flexTable.setCellPadding(2);
        flexTable.setBorderWidth(0);
        
        

        flexTable.setWidget(row, 0, lblRestMoquem);
        flexTable.setWidget(row+1, 0, listBoxRestMoquem);
        flexTable.setWidget(row, 1, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 2, lblAmbiente);
        flexTable.setWidget(row+1, 2, listBoxAmbiente);
        flexTable.setWidget(row, 3, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 4, lblAtendimento);
        flexTable.setWidget(row+1, 4, listBoxAtendimento);
        flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 6, lblQualidade);
        flexTable.setWidget(row+1, 6, listBoxQualidade);
        flexTable.setWidget(row, 7, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 8, lblEspacoKids);
        flexTable.setWidget(row+1, 8, listBoxEspacoKids);
        flexTable.setWidget(row, 9, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 10, lblCozinha);
        flexTable.setWidget(row+1, 10, listBoxCozinha);
        
        row++;
        row++;
        row++;
        row++;

        flexTable.setWidget(row, 0, lblRecomendariaRest);
        flexTable.setWidget(row+1, 0, listBoxRecomendariaRest);
        flexTable.setWidget(row, 1, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 2, lblComoConheceuRest);
        flexTable.setWidget(row+1, 2, listBoxComoConheceuRest);
        flexTable.setWidget(row, 3, new InlineHTML(strInLineSpace));
        
        flexTable.setWidget(row, 4, lblVoceVoltaria);
        flexTable.setWidget(row+1, 4, listBoxVoltariaRest);
        flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
        
        
        
        FlexTable flexTableForm = new FlexTable();
        flexTableForm.setCellSpacing(2);
        flexTableForm.setCellPadding(2);
        flexTableForm.setBorderWidth(0);
        
        flexTableForm.setWidget(row, 0, lblAtendente);
        flexTableForm.setWidget(row, 1, new InlineHTML(strInLineSpace));
        flexTableForm.setWidget(row++, 2, txtAtendente);

        flexTableForm.setWidget(row, 0, lblSugestao);
        flexTableForm.setWidget(row, 1, new InlineHTML(strInLineSpace));
        flexTableForm.setWidget(row, 2, txtSugestao);

        MpImageButton btnSave = new MpImageButton(txtConstants.geralSalvar(), "images/save.png");        
        MpImageButton btnClean = new MpImageButton(txtConstants.geralLimpar(), "images/erase.png");
        
        btnSave.addClickHandler(new ClickHandlerSave());
        btnClean.addClickHandler(new ClickHandlerClean());

        VerticalPanel vFormPanel = new VerticalPanel();

        Grid gridSave = new Grid(1, 3);
        gridSave.setCellSpacing(2);
        gridSave.setCellPadding(2);
        {
            int i = 0;
            gridSave.setWidget(0, i++, btnSave);
            gridSave.setWidget(0, i++, btnClean);
            gridSave.setWidget(0, i++, hPanelLoading);
        }
        

        vFormPanel.add(flexTable);
        vFormPanel.add(flexTableForm);
        vFormPanel.add(mpSpaceVerticalPanel2);
        vFormPanel.add(flexTableCliente);
        vFormPanel.add(mpSpaceVerticalPanel1);
        vFormPanel.add(gridSave);
        vFormPanel.add(new InlineHTML("&nbsp"));

        vFormPanel.setWidth("100%");

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(vFormPanel);
        scrollPanel.setWidth("100%");
        

//        updateMessageAndGrid();
        super.setWidth("100%");

        super.add(scrollPanel);

    }



    /**************** Begin Event Handlers *****************/

    private class ClickHandlerSave implements ClickHandler {

        public void onClick(ClickEvent event) {

            if (checkFieldsValidator()) {

                hPanelLoading.setVisible(true);
                
//              "rest_moquem, ambiente, atendimento, qualidade,  "+
//              "espaco_kids, cozinha, recomendaria_rest, como_conheceu_rest,  "+
//              "voltaria_rest, cidade, data, email, telefone, obs

                Avaliacao avaliacao = new Avaliacao();
                
                avaliacao.setRestMoquem(listBoxRestMoquem.getSelectedValue());
                avaliacao.setAmbiente(listBoxAmbiente.getSelectedValue());
                avaliacao.setAtendimento(listBoxAtendimento.getSelectedValue());
                avaliacao.setQualidade(listBoxQualidade.getSelectedValue());
                avaliacao.setEspacoKids(listBoxEspacoKids.getSelectedValue());
                avaliacao.setCozinha(listBoxCozinha.getSelectedValue());
                avaliacao.setRecomendariaRest(listBoxRecomendariaRest.getSelectedValue());
                avaliacao.setComoConheceuRest(listBoxComoConheceuRest.getSelectedValue());
                avaliacao.setVoltariaRest(listBoxVoltariaRest.getSelectedValue());
                avaliacao.setCidade(txtCidade.getText());
                avaliacao.setData(mpDateBoxDataAvaliacao.getDate().getValue());
                avaliacao.setEmail(txtEmail.getText());                
                avaliacao.setTelefone(txtTelefone.getText());
                avaliacao.setObs(txtSugestao.getText());
                avaliacao.setAtendente(txtAtendente.getText());


                GWTServiceAvaliacao.Util.getInstance().adicionarAvaliacao(avaliacao, new callbackAddAvaliacao());

            }

        }
    }

    private class ClickHandlerClean implements ClickHandler {
        public void onClick(ClickEvent event) {
            cleanFields(false);

        }
    }

    /**************** End Event Handlers *****************/

    public boolean checkFieldsValidator() {

        boolean isDataOk = false;

        if (FieldVerifier.isValidDate(mpDateBoxDataAvaliacao.getDate().getTextBox().getValue())) {
            isDataOk = true;
            mpDateBoxDataAvaliacao.setStyleName("design_text_boxes_date_box");
        } else {
            isDataOk = false;
            mpDateBoxDataAvaliacao.setStyleName("design_text_boxes_erro");
        }

        return  isDataOk ;
    }

    private void cleanFields(boolean isAdicionar) {

        mpDateBoxDataAvaliacao.setStyleName("design_text_boxes_date_box");
        
        listBoxRestMoquem.setSelectedIndex(0);
        listBoxAmbiente.setSelectedIndex(0);
        listBoxAtendimento.setSelectedIndex(0);
        listBoxQualidade.setSelectedIndex(0);
        listBoxEspacoKids.setSelectedIndex(0);
        listBoxCozinha.setSelectedIndex(0);        
        
        listBoxRestMoquem.setSelectedIndex(0);
        listBoxAmbiente.setSelectedIndex(0);
        listBoxAtendimento.setSelectedIndex(0);
        listBoxQualidade.setSelectedIndex(0);
        listBoxEspacoKids.setSelectedIndex(0);
        listBoxCozinha.setSelectedIndex(0);
        listBoxRecomendariaRest.setSelectedIndex(0);
        listBoxComoConheceuRest.setSelectedIndex(0);
        listBoxVoltariaRest.setSelectedIndex(0);
        txtCidade.setValue("");
//        mpDateBoxDataAvaliacao.getDate().setValue(new Date());
        txtEmail.setValue("");           
        txtTelefone.setValue("");
        txtSugestao.setValue("");
        txtAtendente.setValue("");


    }


    private class callbackAddAvaliacao implements AsyncCallback<String> {

        public void onFailure(Throwable caught) {
            hPanelLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Avaliação não pode ser salva com sucesso");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(String result) {
            hPanelLoading.setVisible(false);

            if (result.equals("true")) {
                
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText("Avaliacao salva com sucesso");
                mpDialogBoxConfirm.showDialog();
                cleanFields(true);

            } else {
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText("Avaliação não pode ser salva com sucesso");
                mpDialogBoxWarning.showDialog();
            }
        }

    }



}
