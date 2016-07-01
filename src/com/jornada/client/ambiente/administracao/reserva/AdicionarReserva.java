package com.jornada.client.ambiente.administracao.reserva;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.jornada.client.classes.listBoxes.MpListBoxTurno;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabel;
import com.jornada.client.classes.widgets.label.MpLabelLeft;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.textbox.MpTextBox;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.config.ConfigClient;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;

public class AdicionarReserva extends VerticalPanel {

    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";   
    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading hPanelLoading = new MpPanelLoading("images/radar.gif");


    public MpListBoxTurno listBoxTurno;

    ConfigClient clientConfig = GWT.create(ConfigClient.class);
    private int LIMITE_MAXIMO = Integer.parseInt(clientConfig.lotacaoMaxima());
    private int LIMITE_QUASE_MAXIMO = Integer.parseInt(clientConfig.lotacaoQuaseMaxima());

    private MpTextBox txtNomeReserva;
    private MpTextBox txtNumeroAdultos;
    private MpTextBox txtNumeroCriancas;
    private MpTextBox txtCidade;
    private MpTextBox txtTelefone;
    public MpDateBoxWithImage mpDateBoxDataAgenda;
    private MpTimePicker mpTimePicker;
    private MpTextBox txtObservacao;
    
    public Grid gridInformacao;


    public MpLabel lblNumeroTotal = new MpLabel("");
    

    @SuppressWarnings("unused")
    private TelaInicialReservaEscritorio telaInicialReserva;

    TextConstants txtConstants;

    private static AdicionarReserva uniqueInstance;
    private EditarReserva editarReserva;

    public static AdicionarReserva getInstance(final TelaInicialReservaEscritorio telaInicialPeriodo) {

        if (uniqueInstance == null) {
            uniqueInstance = new AdicionarReserva(telaInicialPeriodo);
        }

        return uniqueInstance;

    }

    @SuppressWarnings("deprecation")
    private AdicionarReserva(final TelaInicialReservaEscritorio telaInicialReserva) {

        txtConstants = GWT.create(TextConstants.class);

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        hPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        hPanelLoading.show();
        hPanelLoading.setVisible(false);

        FlexTable flexTable = new FlexTable();
        flexTable.setCellSpacing(2);
        flexTable.setCellPadding(2);
        flexTable.setBorderWidth(0);

        listBoxTurno = new MpListBoxTurno();
        listBoxTurno.addChangeHandler(new MpCursoSelectionChangeHandler());
        
        txtNomeReserva = new MpTextBox();       
        txtNumeroAdultos = new MpTextBox();     
        txtNumeroCriancas = new MpTextBox();
        txtCidade = new MpTextBox();
        txtTelefone = new MpTextBox();
        txtObservacao = new MpTextBox();
        mpDateBoxDataAgenda = new MpDateBoxWithImage();
        mpDateBoxDataAgenda.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));

        mpDateBoxDataAgenda.getDate().setValue(new Date());
        mpDateBoxDataAgenda.getDate().addValueChangeHandler(new MyDateValueChangeHandler());
        
        
        txtNomeReserva.setTitle("Campo 'Nome Reserva' é de preenchimento obrigatório!");
        txtNumeroAdultos.setTitle("Número Adultos é obrigatório e deve conter apenas valor númerico!");
        txtNumeroCriancas.setTitle("Número Adultos é obrigatório e deve conter apenas valor númerico!");
        txtTelefone.setTitle("Telefone é um campo obrigatório!");
        mpDateBoxDataAgenda.setTitle("Data da Reserva é um campo obrigatório!");

        mpTimePicker = new MpTimePicker(11, 22);
        txtNumeroAdultos.setStyleName("design_text_boxes");
        txtNumeroCriancas.setStyleName("design_text_boxes");
        txtObservacao.setStyleName("design_text_boxes");

        MpLabelLeft lblNomeReserva = new MpLabelLeft("Nome da Reserva");
        MpLabelLeft lblTurno = new MpLabelLeft("Turno");
        MpLabelLeft lblNumeroAdultos = new MpLabelLeft("Número Adultos");
        MpLabelLeft lblNumeroCriancas = new MpLabelLeft("Número Crianças");
        MpLabelLeft lblCidade = new MpLabelLeft("Cidade");
        MpLabelLeft lblTelefone = new MpLabelLeft("Telefone");
        MpLabelLeft lblDateInicial = new MpLabelLeft("Data Reserva");
        MpLabelLeft lblHorario = new MpLabelLeft("Horário Chegada");
        MpLabelLeft lblObservacao = new MpLabelLeft("Observação");
        
        lblNumeroTotal.setStyleName("label_lotacao_bold_13px");

        txtNomeReserva.setWidth("250px");
        txtCidade.setWidth("250px");
        txtTelefone.setWidth("250px");
        txtNumeroAdultos.setWidth("50px");
        txtNumeroAdultos.setMaxLength(3);
        txtNumeroCriancas.setWidth("50px");
        txtNumeroCriancas.setMaxLength(3);
        mpDateBoxDataAgenda.getDate().setWidth("150px");
        mpTimePicker.setWidth("120px");
        listBoxTurno.setWidth("120px");
        txtObservacao.setWidth("250px");

        int row = 1;

        flexTable.setWidget(row, 0, lblDateInicial);
        flexTable.setWidget(row, 1, mpDateBoxDataAgenda);
        flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 3, lblTurno);
        flexTable.setWidget(row, 4, listBoxTurno);
        flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 6, lblHorario);
        flexTable.setWidget(row++, 7, mpTimePicker);

        flexTable.setWidget(row, 0, lblNomeReserva);
        flexTable.setWidget(row, 1, txtNomeReserva);
        flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 3, lblNumeroAdultos);
        flexTable.setWidget(row, 4, txtNumeroAdultos);
        flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 6, lblNumeroCriancas);
        flexTable.setWidget(row++, 7, txtNumeroCriancas);

        flexTable.setWidget(row, 0, lblCidade);
        flexTable.setWidget(row, 1, txtCidade);
        flexTable.setWidget(row, 2, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 3, lblTelefone);
        flexTable.setWidget(row, 4, txtTelefone);
        flexTable.setWidget(row, 5, new InlineHTML(strInLineSpace));
        flexTable.setWidget(row, 6, lblObservacao);
        flexTable.setWidget(row++, 7, txtObservacao);

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
        
        
        MpLabel lblLimiteMaximo = new MpLabel("*Limite Máximo de Clientes : " + clientConfig.lotacaoMaxima());
        lblLimiteMaximo.setStyleName("label_lotacao_bold_13px");


        gridInformacao = new Grid(1, 1);
        gridInformacao.setStyleName("fundo_tabela_lotacao_vazia");
        gridInformacao.setCellSpacing(2);
        gridInformacao.setCellPadding(2);
        {
            gridInformacao.setWidget(0, 0, lblNumeroTotal);
        }
        gridInformacao.setWidth("100%");
//        gridInformacao.setBorderWidth(1);
        lblNumeroTotal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");

        editarReserva = EditarReserva.getInstance(this, mpDateBoxDataAgenda.getDate().getValue(), listBoxTurno.getSelectedValue());

//        vFormPanel.add(gridLimiteMaximo);
//        vFormPanel.add(new InlineHTML("&nbsp"));
        vFormPanel.add(gridInformacao);
//        vFormPanel.add(new InlineHTML("&nbsp"));
        vFormPanel.add(lblLimiteMaximo);
//        vFormPanel.add(mpSpaceVerticalPanel);
        vFormPanel.add(flexTable);
        vFormPanel.add(gridSave);
        vFormPanel.add(new InlineHTML("&nbsp"));
        
        vFormPanel.add(mpSpaceVerticalPanel);
        vFormPanel.add(editarReserva);
//        vFormPanel.setBorderWidth(1);
        vFormPanel.setWidth("100%");

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(vFormPanel);
        scrollPanel.setWidth("100%");
        

        updateMessageAndGrid();
        super.setWidth("100%");
//        super.setBorderWidth(1);

        super.add(scrollPanel);

    }



    /**************** Begin Event Handlers *****************/

    private class ClickHandlerSave implements ClickHandler {

        public void onClick(ClickEvent event) {

            if (checkFieldsValidator()) {

                hPanelLoading.setVisible(true);

                String strHora = mpTimePicker.getValue(mpTimePicker.getSelectedIndex());
                String turno = listBoxTurno.getValue(listBoxTurno.getSelectedIndex());

                Reserva reserva = new Reserva();
                reserva.setNomeReserva(txtNomeReserva.getText());
                reserva.setNumeroAdultos(Integer.parseInt(txtNumeroAdultos.getText()));
                reserva.setNumeroCriancas(Integer.parseInt(txtNumeroCriancas.getText()));
                reserva.setDataReserva(mpDateBoxDataAgenda.getDate().getValue());
                reserva.setHorario(strHora);
                reserva.setCidade(txtCidade.getText());
                reserva.setTelefone(txtTelefone.getText());
                reserva.setTurno(turno);
                reserva.setObservacao(txtObservacao.getText());

                GWTServiceReserva.Util.getInstance().adicionarPeriodoString(reserva, new callbackAddReserva());

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
        boolean isNomeOk = false;
        boolean isNumeroAdultoOk = false;
        boolean isNumeroCriancaOk = false;
        boolean isDataOk = false;
        boolean isTelefoneOk = false;

        if (FieldVerifier.isValidName(txtNomeReserva.getText())) {
            isNomeOk = true;
            // lblErroNomeReserva.hideErroMessage();[
            txtNomeReserva.setStyleName("design_text_boxes");

        } else {
            isNomeOk = false;
            // lblErroNomeReserva.showErrorMessage(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
            txtNomeReserva.setStyleName("design_text_boxes_erro");
        }

        if (FieldVerifier.isValidName(txtNumeroAdultos.getText())) {
            isNumeroAdultoOk = true;
            // lblErroNumeroAdultos.hideErroMessage();
            txtNumeroAdultos.setStyleName("design_text_boxes");
        } else {
            isNumeroAdultoOk = false;
            // lblErroNumeroAdultos.showErrorMessage("Número Adultos deve ser um valor númerico");
            txtNumeroAdultos.setStyleName("design_text_boxes_erro");
        }

        if (FieldVerifier.isValidName(txtNumeroCriancas.getText())) {
            isNumeroCriancaOk = true;
            // lblErroNumeroCriancas.hideErroMessage();
            txtNumeroCriancas.setStyleName("design_text_boxes");
        } else {
            isNumeroCriancaOk = false;
            // lblErroNumeroCriancas.showErrorMessage("Número Crianças deve ser um valor númerico");
            txtNumeroCriancas.setStyleName("design_text_boxes_erro");
        }

        if (FieldVerifier.isValidDate(mpDateBoxDataAgenda.getDate().getTextBox().getValue())) {
            isDataOk = true;
            // lblErroDataReserva.hideErroMessage();
            mpDateBoxDataAgenda.setStyleName("design_text_boxes_date_box");
        } else {
            isDataOk = false;
            // lblErroDataReserva.showErrorMessage("Data da Reserva é um campo obrigatório!!!");
            mpDateBoxDataAgenda.setStyleName("design_text_boxes_erro");
        }

        if (FieldVerifier.isValidName(txtTelefone.getText())) {
            isTelefoneOk = true;
            // lblErroTelefone.hideErroMessage();
            txtTelefone.setStyleName("design_text_boxes");

        } else {
            isTelefoneOk = false;
            // lblErroTelefone.showErrorMessage("Telefone é um campo obrigatório!!!");
            txtTelefone.setStyleName("design_text_boxes_erro");
        }

        return isNomeOk && isNumeroCriancaOk && isNumeroAdultoOk && isDataOk && isTelefoneOk;
    }

    private void cleanFields(boolean isAdicionar) {
        // lblErroNomeReserva.hideErroMessage();
        // lblErroNumeroAdultos.hideErroMessage();
        // lblErroNumeroCriancas.hideErroMessage();
        // lblErroDataReserva.hideErroMessage();
        // lblErroTelefone.hideErroMessage();
        txtNomeReserva.setStyleName("design_text_boxes");
        txtNumeroAdultos.setStyleName("design_text_boxes");
        txtNumeroCriancas.setStyleName("design_text_boxes");
        mpDateBoxDataAgenda.setStyleName("design_text_boxes_date_box");
        txtTelefone.setStyleName("design_text_boxes");
        
//        listBoxTurno.clear();

        txtNomeReserva.setValue("");
        txtNumeroAdultos.setValue("");
        txtNumeroCriancas.setValue("");
        txtCidade.setValue("");
        txtTelefone.setValue("");
        txtObservacao.setValue("");
        if (isAdicionar == false) {
            mpDateBoxDataAgenda.getDate().setValue(new Date());
            listBoxTurno.setSelectedIndex(0);
        }
        mpTimePicker.setSelectedIndex(0);

    }

    public class MyDateValueChangeHandler implements ValueChangeHandler<Date> {

        public MyDateValueChangeHandler() {
        }

        public void onValueChange(ValueChangeEvent<Date> event) {

            updateMessageAndGrid();
        }
    }

    private class MpCursoSelectionChangeHandler implements ChangeHandler {

        public void onChange(ChangeEvent event) {
            if (mpDateBoxDataAgenda.getDate().getValue() == null) {
                mpDateBoxDataAgenda.getDate().setValue(new Date());
            }
            updateMessageAndGrid();
        }
    }

    
    public void updateMessageAndGrid() {
        updateMessage();
        updateGrigEditarReserva();
    }
    
    public void updateMessage(){
        GWTServiceReserva.Util.getInstance().getNumeroClientes(mpDateBoxDataAgenda.getDate().getValue(), listBoxTurno.getSelectedValue(), new callbackClientes());
    }
    
    public void updateGrigEditarReserva() {
        editarReserva.populateGrid(mpDateBoxDataAgenda.getDate().getValue(), listBoxTurno.getSelectedValue());
    }

    private class callbackAddReserva implements AsyncCallback<String> {

        public void onFailure(Throwable caught) {
            hPanelLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Reserva não pode ser salva com sucesso");
            mpDialogBoxWarning.showDialog();
        }

        @Override
        public void onSuccess(String result) {
            hPanelLoading.setVisible(false);

            if (result.equals("true")) {
                cleanFields(true);
                mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
                mpDialogBoxConfirm.setBodyText("Reserva salva com sucesso");
                mpDialogBoxConfirm.showDialog();
                // telaInicialReserva.populateGrid();
                // updateGrigEditarReserva();
                updateMessageAndGrid();

            } else if (result.contains(Reserva.DB_UNIQUE_KEY)) {
                String strPeriodo = result.substring(result.indexOf("=(") + 2);
                strPeriodo = strPeriodo.substring(strPeriodo.indexOf(",") + 1);
                strPeriodo = strPeriodo.substring(0, strPeriodo.indexOf(")"));
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.periodoErroSalvar() + " " + txtConstants.periodoDuplicado((strPeriodo)));
                mpDialogBoxWarning.showDialog();
            } else {
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText("Reserva não pode ser salva com sucesso");
                mpDialogBoxWarning.showDialog();
            }
        }

    }

    private class callbackClientes implements AsyncCallback<Clientes> {

        public void onFailure(Throwable caught) {
            hPanelLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Reserva não pode ser salva com sucesso");
            mpDialogBoxWarning.showDialog();
        }

        public void onSuccess(Clientes result) {

            hPanelLoading.setVisible(false);

            String strNumeroAdultos = "Adultos : " + Integer.toString(result.getNumeroTotalAdultos()) + " | ";
            String strNumeroCriancas = "Crianças : " + Integer.toString(result.getNumeroTotalCriancas()) + " | ";

            int total = result.getNumeroTotalClientes();

            String strNumeroTotal = "Total : " + total;

            if (total > LIMITE_MAXIMO) {
//                lblNumeroTotal.setStyleName("erro_lotacao");
                gridInformacao.setStyleName("fundo_tabela_lotacao_esgotada");
            } else if (total >= LIMITE_QUASE_MAXIMO && total < LIMITE_MAXIMO) {
//                lblNumeroTotal.setStyleName("erro_quase_lotacao");
                gridInformacao.setStyleName("fundo_tabela_lotacao_quase_esgotada");
            } else {
//                lblNumeroTotal.setStyleName("lotacao_vazia");
                gridInformacao.setStyleName("fundo_tabela_lotacao_vazia");
            }

//            lblNumeroTotal.setText("Limite Máximo de Clientes : "+clientConfig.lotacaoMaxima()+" - "+strNumeroAdultos + strNumeroCriancas + strNumeroTotal);
            lblNumeroTotal.setText(strNumeroAdultos + strNumeroCriancas + strNumeroTotal);
        }

    }

}
