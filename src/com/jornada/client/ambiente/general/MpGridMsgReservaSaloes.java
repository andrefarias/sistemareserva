package com.jornada.client.ambiente.general;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.jornada.client.classes.widgets.label.MpLabel;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.classes.salao.Saloes;

public class MpGridMsgReservaSaloes extends Grid{
    
    private Grid gridSalaoInterno;
    private Grid gridSalaoExternoCoberto;
    private Grid gridSalaoExternoAberto;
    private Grid gridSalaoChurrasqueira;
    
    public MpLabel lblNumeroSalaoInterno = new MpLabel("");
    public MpLabel lblNumeroSalaoExternoCoberto = new MpLabel("");
    public MpLabel lblNumeroSalaoExternoAberto = new MpLabel("");
    public MpLabel lblNumeroSalaoChurrasqueira = new MpLabel("");
    
    MpLabel lblNumeroClientes;
    
    public MpGridMsgReservaSaloes(){
        
        super(2, 4);
        this.setStyleName("designMessage");
        this.setCellSpacing(0);
        this.setCellPadding(0);
        this.setBorderWidth(0);
        this.setWidth("100%");
        
//        lblNumeroSalaoInterno.setStyleName("labelReservaSaloes");
//        lblNumeroSalaoExternoCoberto.setStyleName("labelReservaSaloes");
//        lblNumeroSalaoExternoAberto.setStyleName("labelReservaSaloes");
//        lblNumeroSalaoChurrasqueira.setStyleName("labelReservaSaloes");
        
      lblNumeroSalaoInterno.setStyleName("label_lotacao_13px");
      lblNumeroSalaoExternoCoberto.setStyleName("label_lotacao_13px");
      lblNumeroSalaoExternoAberto.setStyleName("label_lotacao_13px");
      lblNumeroSalaoChurrasqueira.setStyleName("label_lotacao_13px");
        
        Label lblInterno = new Label(Saloes.STR_NOME_SALAO_INTERNO +" ["+Saloes.INT_LIMITE_SALAO_INTERNO+"]");        
        Label lblExternoCoberto = new Label(Saloes.STR_NOME_SALAO_EXTERNO_COBERTO +" ["+Saloes.INT_LIMITE_SALAO_EXTERNO_COBERTO+"]");
        Label lblExternoAberto = new Label(Saloes.STR_NOME_SALAO_EXTERNO_ABERTO +" ["+Saloes.INT_LIMITE_SALAO_EXTERNO_ABERTO+"]");
        Label lblChurrasqueira = new Label(Saloes.STR_NOME_SALAO_CHURRASQUEIRA +" ["+Saloes.INT_LIMITE_SALAO_CHURRASQUEIRA+"]");
        
        lblInterno.setStyleName("label_lotacao_bold_13px");
        lblExternoCoberto.setStyleName("label_lotacao_bold_13px");
        lblExternoAberto.setStyleName("label_lotacao_bold_13px");
        lblChurrasqueira.setStyleName("label_lotacao_bold_13px");
        
        lblNumeroSalaoInterno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblNumeroSalaoExternoCoberto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblNumeroSalaoExternoAberto.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblNumeroSalaoChurrasqueira.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        
        
        gridSalaoInterno = new Grid(1, 2);
        gridSalaoInterno.setStyleName("fundo_tabela_lotacao_vazia");
//        gridSalaoInterno.setBorderWidth(1);
        gridSalaoInterno.setWidth("100%");
        gridSalaoInterno.setWidget(0, 0, lblInterno);
        gridSalaoInterno.setWidget(0, 1, lblNumeroSalaoInterno);
        
        gridSalaoExternoCoberto = new Grid(1, 2);
        gridSalaoExternoCoberto.setStyleName("fundo_tabela_lotacao_vazia");
        gridSalaoExternoCoberto.setWidth("100%");
        gridSalaoExternoCoberto.setWidget(0, 0, lblExternoCoberto);
        gridSalaoExternoCoberto.setWidget(0, 1, lblNumeroSalaoExternoCoberto);
        
        gridSalaoExternoAberto = new Grid(1, 2);
        gridSalaoExternoAberto.setStyleName("fundo_tabela_lotacao_vazia");
        gridSalaoExternoAberto.setWidth("100%");
        gridSalaoExternoAberto.setWidget(0, 0, lblExternoAberto);  
        gridSalaoExternoAberto.setWidget(0, 1, lblNumeroSalaoExternoAberto);    
        
        gridSalaoChurrasqueira = new Grid(1, 2);
        gridSalaoChurrasqueira.setStyleName("fundo_tabela_lotacao_vazia");
        gridSalaoChurrasqueira.setWidth("100%");
        gridSalaoChurrasqueira.setWidget(0, 0, lblChurrasqueira);
        gridSalaoChurrasqueira.setWidget(0, 1, lblNumeroSalaoChurrasqueira);        
        
        lblNumeroClientes = new MpLabel();
        lblNumeroClientes.setStyleName("label_lotacao_bold_13px");
        lblNumeroClientes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        
        
      this.setWidget(0, 0, gridSalaoExternoCoberto);
      this.setWidget(0, 1, gridSalaoExternoAberto);
      this.setWidget(0, 2, gridSalaoInterno);
      this.setWidget(0, 3, gridSalaoChurrasqueira);
      this.setWidget(1, 0, lblNumeroClientes);
        
    }
    
    private class callbackSaloes implements AsyncCallback<Saloes> {

        public void onFailure(Throwable caught) {
//            mpPanelLoading.setVisible(false);
//            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//            mpDialogBoxWarning.setBodyText("Reserva não pode ser salva com sucesso");
//            mpDialogBoxWarning.showDialog();
//            Window.alert("");
        }

        public void onSuccess(Saloes saloes) {

//            mpPanelLoading.setVisible(false);
            
            int intInterno = saloes.getSalaoInterno().getQuantidadeAdultos()+saloes.getSalaoInterno().getQuantidadeCriancas();
            int intExternoCoberto = saloes.getSalaoExternoCoberto().getQuantidadeAdultos()+saloes.getSalaoExternoCoberto().getQuantidadeCriancas();
            int intExternoAberto = saloes.getSalaoExternoAberto().getQuantidadeAdultos()+saloes.getSalaoExternoAberto().getQuantidadeCriancas();
            int intChurrasqueira = saloes.getSalaoChurrasqueira().getQuantidadeAdultos()+saloes.getSalaoChurrasqueira().getQuantidadeCriancas();
            
            if( intInterno > Saloes.INT_LIMITE_SALAO_INTERNO){
                gridSalaoInterno.setStyleName("fundo_tabela_lotacao_esgotada");
            }
            if( intExternoCoberto > Saloes.INT_LIMITE_SALAO_EXTERNO_COBERTO){
                gridSalaoExternoCoberto.setStyleName("fundo_tabela_lotacao_esgotada");
            }
            if( intExternoAberto > Saloes.INT_LIMITE_SALAO_EXTERNO_ABERTO){
                gridSalaoExternoAberto.setStyleName("fundo_tabela_lotacao_esgotada");
            }
            if( intChurrasqueira > Saloes.INT_LIMITE_SALAO_CHURRASQUEIRA){
                gridSalaoChurrasqueira.setStyleName("fundo_tabela_lotacao_esgotada");
            }
            
            
            if (intInterno >= Saloes.INT_QUASE_LIMITE_SALAO_INTERNO && intInterno < Saloes.INT_LIMITE_SALAO_INTERNO) {
                gridSalaoInterno.setStyleName("fundo_tabela_lotacao_quase_esgotada");
            }
            if (intExternoCoberto >= Saloes.INT_QUASE_LIMITE_EXTERNO_COBERTO && intExternoCoberto < Saloes.INT_LIMITE_SALAO_EXTERNO_COBERTO) {
                gridSalaoExternoCoberto.setStyleName("fundo_tabela_lotacao_quase_esgotada");
            }
            if (intExternoAberto >= Saloes.INT_QUASE_LIMITE_EXTERNO_ABERTO && intExternoAberto < Saloes.INT_LIMITE_SALAO_EXTERNO_ABERTO) {
                gridSalaoExternoAberto.setStyleName("fundo_tabela_lotacao_quase_esgotada");
            }
            if (intChurrasqueira >= Saloes.INT_QUASE_LIMITE_CHURRASQUEIRA && intChurrasqueira < Saloes.INT_LIMITE_SALAO_CHURRASQUEIRA) {
                gridSalaoChurrasqueira.setStyleName("fundo_tabela_lotacao_quase_esgotada");
            }
            
            if (intInterno < Saloes.INT_QUASE_LIMITE_SALAO_INTERNO) {
                gridSalaoInterno.setStyleName("fundo_tabela_lotacao_vazia");
            }
            if (intExternoCoberto < Saloes.INT_QUASE_LIMITE_EXTERNO_COBERTO) {
                gridSalaoExternoCoberto.setStyleName("fundo_tabela_lotacao_vazia");
            }
            if (intExternoAberto < Saloes.INT_QUASE_LIMITE_EXTERNO_ABERTO) {
                gridSalaoExternoAberto.setStyleName("fundo_tabela_lotacao_vazia");
            }
            if (intChurrasqueira < Saloes.INT_QUASE_LIMITE_CHURRASQUEIRA) {
                gridSalaoChurrasqueira.setStyleName("fundo_tabela_lotacao_vazia");
            }

          String strAdul =   "Adul.: ";
          String strCri =   "Cria.: ";
          String strTot =   "Total: ";
          String strSeparator =   " / ";
          
            
          String strNumeroAdultos = strAdul + Integer.toString(saloes.getSalaoInterno().getQuantidadeAdultos()) + strSeparator;
          String strNumeroCriancas = strCri + Integer.toString(saloes.getSalaoInterno().getQuantidadeCriancas()) + strSeparator;
          String strNumeroTotal = strTot + Integer.toString(intInterno);
          lblNumeroSalaoInterno.setText(strNumeroAdultos+strNumeroCriancas+strNumeroTotal);
          
          strNumeroAdultos = strAdul + Integer.toString(saloes.getSalaoExternoCoberto().getQuantidadeAdultos()) + strSeparator;
          strNumeroCriancas = strCri + Integer.toString(saloes.getSalaoExternoCoberto().getQuantidadeCriancas()) + strSeparator;
          strNumeroTotal = strTot + Integer.toString(intExternoCoberto);
    
          lblNumeroSalaoExternoCoberto.setText(strNumeroAdultos+strNumeroCriancas+strNumeroTotal);       
          
          strNumeroAdultos = strAdul + Integer.toString(saloes.getSalaoExternoAberto().getQuantidadeAdultos()) + strSeparator;
          strNumeroCriancas = strCri + Integer.toString(saloes.getSalaoExternoAberto().getQuantidadeCriancas()) + strSeparator;
          strNumeroTotal = strTot + Integer.toString(intExternoAberto);
          lblNumeroSalaoExternoAberto.setText(strNumeroAdultos+strNumeroCriancas+strNumeroTotal);   
          
          strNumeroAdultos = strAdul + Integer.toString(saloes.getSalaoChurrasqueira().getQuantidadeAdultos()) + strSeparator;
          strNumeroCriancas = strCri + Integer.toString(saloes.getSalaoChurrasqueira().getQuantidadeCriancas()) + strSeparator;
          strNumeroTotal = strTot + Integer.toString(intChurrasqueira);
          lblNumeroSalaoChurrasqueira.setText(strNumeroAdultos+strNumeroCriancas+strNumeroTotal);   
          
          String strNumeroClientes = "";
          
          int intLimite = Saloes.INT_LIMITE_SALAO_INTERNO + Saloes.INT_LIMITE_SALAO_EXTERNO_COBERTO + Saloes.INT_LIMITE_SALAO_EXTERNO_ABERTO + Saloes.INT_LIMITE_SALAO_CHURRASQUEIRA;
          strNumeroClientes = "*Número de Clientes : "+Integer.toString(saloes.getTotalClientes()) + " / Limite : " + intLimite;
          lblNumeroClientes.setText(strNumeroClientes);
//          strNumeroClientes="";
        }

    }    
    
    public void updateMessage(Date date, String strTurno){
        GWTServiceReserva.Util.getInstance().getQuantidadeClientesNosSaloes(date, strTurno, new callbackSaloes());
        
    }

}
