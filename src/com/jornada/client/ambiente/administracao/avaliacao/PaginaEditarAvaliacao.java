package com.jornada.client.ambiente.administracao.avaliacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelLeftAvaliacao;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceAvaliacao;
//import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.utility.MpUtilClient;
//import com.jornada.client.classes.listBoxes.MpListBoxS;
//import com.jornada.client.ambiente.administracao.avaliacao.PaginaGraficosAvaliacao.ClickHandlerGerarGrafico;

public class PaginaEditarAvaliacao extends VerticalPanel {

    private AsyncCallback<Boolean> callbackUpdateRow;
    private AsyncCallback<Boolean> callbackDeleteAvaliacao;
    
    public MpDateBoxWithImage mpDateBoxDataInicio;
    public MpDateBoxWithImage mpDateBoxDataFinal;
    
    private TextBox txtSearch;
    
    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";  

    private CellTable<Avaliacao> cellTable;
    private ListDataProvider<Avaliacao> dataProvider = new ListDataProvider<Avaliacao>();
    private Column<Avaliacao, String> columnIdAvaliacao;
    private Column<Avaliacao, Date> columnData;
    private Column<Avaliacao, String> columnObs;
    private Column<Avaliacao, String> columnRecGrupoPrimario;
    private Column<Avaliacao, String> columnRecGrupoSecundario;

    
//    private LinkedHashMap<String, String> listNivel = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> listRecGrupoPrimario = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> listRecGrupoSecundario = new LinkedHashMap<String, String>();
    
//    public MpListBox listBoxOrdemConsulta;

//    private TextBox txtSearch;
    ArrayList<Avaliacao> arrayListBackup = new ArrayList<Avaliacao>();

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    TextConstants txtConstants;
    

    public Date dataReserva;
    public String strTurno;
    
    final SelectionModel<Avaliacao> selectionModel;

    public PaginaAdicionarAvaliacao adicionarReserva;
    private static PaginaEditarAvaliacao uniqueInstance;

    public static PaginaEditarAvaliacao getInstance(final TelaInicialAvaliacaoEscritorio telaInicialPeriodo) {

        if (uniqueInstance == null) {
            uniqueInstance = new PaginaEditarAvaliacao(telaInicialPeriodo);
        }

        return uniqueInstance;

    }


    @SuppressWarnings("deprecation")
    private PaginaEditarAvaliacao(TelaInicialAvaliacaoEscritorio telaInicialPeriodo) {

        txtConstants = GWT.create(TextConstants.class);
        

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);
        
        
        Date data = new Date();
        int month_6 = 1000 * 60 * 60 * 24 * 30 * 6;
        data.setTime(data.getTime() - month_6);
        
        mpDateBoxDataInicio = new MpDateBoxWithImage();
        mpDateBoxDataInicio.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));
        mpDateBoxDataInicio.getDate().setValue(data);
        
        mpDateBoxDataFinal = new MpDateBoxWithImage();
        mpDateBoxDataFinal.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));   
        mpDateBoxDataFinal.getDate().setValue(new Date());
        
//        listBoxOrdemConsulta = new MpListBox();
//        listBoxOrdemConsulta.addItem("Ascendente","ASC");
//        listBoxOrdemConsulta.addItem("Descrecente","DESC");
//        listBoxOrdemConsulta.setSelectItem("DESC");
        
        
        txtSearch = new TextBox();
        txtSearch.setStyleName("design_text_boxes");


        Label lblEmpty = new Label("Nenhuma avaliação associada a esta Data ou turno.");

        cellTable = new CellTable<Avaliacao>(5, GWT.<CellTableStyle> create(CellTableStyle.class));

        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);

        dataProvider.addDataDisplay(cellTable);

        selectionModel = new MultiSelectionModel<Avaliacao>(Avaliacao.KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Avaliacao> createCheckboxManager());

        initTableColumns(selectionModel);
        
        MpLabelLeftAvaliacao lblDataInicial = new MpLabelLeftAvaliacao("Data Inicial");
        MpLabelLeftAvaliacao lblDataFinal = new MpLabelLeftAvaliacao("Data Final");
//        MpLabelLeftAvaliacao lblOrdem = new MpLabelLeftAvaliacao("Ordem");
        MpLabelLeftAvaliacao lblObs = new MpLabelLeftAvaliacao("Filtro Obs.");
//        MpLabelLeftAvaliacao lblFiltroTexto = new MpLabelLeftAvaliacao("Filtro Texto");
        
        MpImageButton btnSave = new MpImageButton("Gerar Tabela", "images/categorycheck.png");        
        btnSave.addClickHandler(new ClickHandlerGerarGrafico());

        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(500);
        
        
        HorizontalPanel hPanelSavelButton = new HorizontalPanel();
        hPanelSavelButton.add(btnSave);
        hPanelSavelButton.add(new InlineHTML(strInLineSpace));
        hPanelSavelButton.add(mpPanelLoading);



        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(3);
        flexTableFiltrar.setCellPadding(3);
        flexTableFiltrar.setBorderWidth(0);
        
        flexTableFiltrar.setWidget(0, 0, lblDataInicial);        
        flexTableFiltrar.setWidget(1, 0, mpDateBoxDataInicio);
        flexTableFiltrar.setWidget(0, 1, new InlineHTML(strInLineSpace));   
        
        flexTableFiltrar.setWidget(0, 2, lblDataFinal);        
        flexTableFiltrar.setWidget(1, 2, mpDateBoxDataFinal);
        flexTableFiltrar.setWidget(0, 3, new InlineHTML(strInLineSpace));     
        
//        flexTableFiltrar.setWidget(0, 4, lblOrdem);        
//        flexTableFiltrar.setWidget(1, 4, listBoxOrdemConsulta);
//        flexTableFiltrar.setWidget(0, 5, new InlineHTML(strInLineSpace)); 
        
        flexTableFiltrar.setWidget(0, 4, lblObs);        
        flexTableFiltrar.setWidget(1, 4, txtSearch);
        flexTableFiltrar.setWidget(0, 5, new InlineHTML(strInLineSpace)); 

        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");
//        
        VerticalPanel vFormPanel = new VerticalPanel();
        vFormPanel.setWidth("100%");
        vFormPanel.add(flexTableFiltrar);
        vFormPanel.add(new InlineHTML(strInLineSpace));
        vFormPanel.add(hPanelSavelButton);        
        vFormPanel.add(new InlineHTML(strInLineSpace));
        vFormPanel.add(mpSpaceVerticalPanel);
        vFormPanel.add(mpPager);
//        vFormPanel.add(flexTableGrafico);
        
        
        FlexTable flexTableMenu = new FlexTable();
        flexTableMenu.setCellPadding(0);
        flexTableMenu.setCellSpacing(0);
        flexTableMenu.setBorderWidth(0);
        flexTableMenu.setWidth("100%");
        flexTableMenu.setWidget(0, 0, vFormPanel);
//        flexTableMenu.setWidget(0, 1, flexTableImg);
//        flexTableMenu.getCellFormatter().setWidth(0, 0, "70%");
        flexTableMenu.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_BOTTOM);

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(cellTable);

        VerticalPanel vPanelEditGrid = new VerticalPanel();
        vPanelEditGrid.add(flexTableMenu);
        vPanelEditGrid.add(scrollPanel);
        vPanelEditGrid.setWidth("100%");

        /************************* Begin Callback's *************************/

        callbackUpdateRow = new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean success) {
                mpPanelLoading.setVisible(false);
                if (success == false) {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo()) + " " + txtConstants.geralRegarregarPagina());
                    mpDialogBoxWarning.showDialog();
                }
//                uniqueInstance.adicionarReserva.updateMessage();
            }

            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo()));
                mpDialogBoxWarning.showDialog();
            }
        };

        callbackDeleteAvaliacao = new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean success) {
                mpPanelLoading.setVisible(false);
                if (success == true) {
//                    populateGrid(uniqueInstance.dataReserva, uniqueInstance.strTurno);
//                    telaInicialReserva.populateGrid();  
//                	fghf
//                	populateGrid(dataReserva, strTurno);
//                	uniqueInstance.
//                    uniqueInstance.adicionarReserva.updateMessageAndGrid();
                    
                } else {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover("a avaliação."));
                    mpDialogBoxWarning.showDialog();
                }

            }

            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover(txtConstants.periodo()));
                mpDialogBoxWarning.showDialog();

            }
        };

        /*********************** End Callbacks **********************/


//        populateGrid(dataReserva, strTurno);
//        populateComboBoxSaloes();
        
        setWidth("100%");
        super.add(vPanelEditGrid);

    }

    private class MyImageCell extends ImageCell {

        @Override
        public Set<String> getConsumedEvents() {
            Set<String> consumedEvents = new HashSet<String>();
            consumedEvents.add("click");
            return consumedEvents;
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
            switch (DOM.eventGetType((Event) event)) {
            case Event.ONCLICK:
                final Avaliacao per = (Avaliacao) context.getKey();
                @SuppressWarnings("unused")
                CloseHandler<PopupPanel> closeHandler;

                MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(txtConstants.geralRemover(), "Deseja Remover a avaliação?", txtConstants.geralSim(), txtConstants.geralNao(),

                closeHandler = new CloseHandler<PopupPanel>() {

                    public void onClose(CloseEvent<PopupPanel> event) {

                        MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

                        if (x.primaryActionFired()) {

                            GWTServiceAvaliacao.Util.getInstance().deleteRow(per.getIdAvaliacao(), callbackDeleteAvaliacao);

                        }
                    }
                }

                );
                confirmationDialog.paint();
                break;

            default:
                Window.alert("Test default");
                break;
            }
        }

    }

    public void populateGrid() {
        mpPanelLoading.setVisible(true);

        Date dataInicial = mpDateBoxDataInicio.getDate().getValue(); 
        Date dataFinal = mpDateBoxDataFinal.getDate().getValue(); 
        String strObs = txtSearch.getText();
        
        GWTServiceAvaliacao.Util.getInstance().getAvaliacoes(dataInicial, dataFinal, strObs,  new AsyncCallback<ArrayList<Avaliacao>>() {

            @Override
            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregar(txtConstants.periodo()));
            }

            @Override
            public void onSuccess(ArrayList<Avaliacao> list) {
                MpUtilClient.isRefreshRequired(list);
                mpPanelLoading.setVisible(false);
                dataProvider.getList().clear();
                arrayListBackup.clear();
                cellTable.setRowCount(0);
                for (int i = 0; i < list.size(); i++) {
                    dataProvider.getList().add(list.get(i));
                    arrayListBackup.add(list.get(i));
                }
                addCellTableData(dataProvider);
                cellTable.redraw();

            }
        });
    }



    private void addCellTableData(ListDataProvider<Avaliacao> dataProvider) {

        ListHandler<Avaliacao> sortHandler = new ListHandler<Avaliacao>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }

    private void initTableColumns(final SelectionModel<Avaliacao> selectionModel){
        
        
        columnIdAvaliacao = new Column<Avaliacao, String>(new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return Integer.toString(object.getIdAvaliacao());              
            }

        };
        
        
        columnData = new Column<Avaliacao, Date>(new MpDatePickerCell()) {
            @Override
            public Date getValue(Avaliacao object) {
                return object.getData();
            }
        };
        
        columnObs = new Column<Avaliacao, String>(new TextCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return object.getObs();
            }

        };
        

        
    
//        MpListBoxS listBoxSimNao = new MpListBoxS();
        ArrayList<String> listGrupoPrimario = new ArrayList<String>();
        listGrupoPrimario.add("Selecionar uma Opção");
       
        listGrupoPrimario.add("Ambiente");
        listGrupoPrimario.add("Atendimento");
        listGrupoPrimario.add("Bar");
        listGrupoPrimario.add("Cardápio");
        listGrupoPrimario.add("Churrasqueira");
        listGrupoPrimario.add("Cozinha");
        listGrupoPrimario.add("Espaço Kids");
        listGrupoPrimario.add("Qualidade");
        listGrupoPrimario.add("RestMoquem");

        
        for(int i=0;i<listGrupoPrimario.size();i++){
            listRecGrupoPrimario.put(listGrupoPrimario.get(i),listGrupoPrimario.get(i));
        }
        columnRecGrupoPrimario = new Column<Avaliacao, String>(new MpStyledSelectionCell(listRecGrupoPrimario, "design_text_boxes")) {
            @Override
            public String getValue(Avaliacao object) {                
                return object.getRecGrupoPrimario();
            }

        };
        columnRecGrupoPrimario.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
            @Override
            public void update(int index, Avaliacao object, String value) {
                object.setRecGrupoPrimario(value);
                GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
            }
        });  
        
        
        
        
//      MpListBoxS listBoxSimNao = new MpListBoxS();
      ArrayList<String> listGrupoSecundario = new ArrayList<String>();
      listGrupoSecundario.add("Selecionar uma Opção");

//        listGrupoSecundario.add("Agilidade");
        listGrupoSecundario.add("Demora");
        listGrupoSecundario.add("Elogio");
        listGrupoSecundario.add("Elogio Comida");
        listGrupoSecundario.add("Gerencia");
        listGrupoSecundario.add("Mosquitos/Moscas");
        listGrupoSecundario.add("Organização");
        listGrupoSecundario.add("Pombos");
        listGrupoSecundario.add("Pouco Marketing");
        listGrupoSecundario.add("Pouco Funcionário");
        listGrupoSecundario.add("Reclamação Atendimento");
        listGrupoSecundario.add("Reclamação Bebidas");
        listGrupoSecundario.add("Reclamação Comida");    
        listGrupoSecundario.add("Reclamação Quantidade");
        listGrupoSecundario.add("Reclamação Restaurante");
        listGrupoSecundario.add("Redes");
        listGrupoSecundario.add("Sugestão");
        listGrupoSecundario.add("Treinamento");
        listGrupoSecundario.add("Ventiladores");

      
//
//      listGrupoSecundario.add("Espaço Kids");
//      listGrupoSecundario.add("Cozinha");
      for(int i=0;i<listGrupoSecundario.size();i++){
          listRecGrupoSecundario.put(listGrupoSecundario.get(i),listGrupoSecundario.get(i));
      }
      columnRecGrupoSecundario = new Column<Avaliacao, String>(new MpStyledSelectionCell(listRecGrupoSecundario, "design_text_boxes")) {
          @Override
          public String getValue(Avaliacao object) {                
              return object.getRecGrupoSecundario();
          }

      };
      columnRecGrupoSecundario.setFieldUpdater(new FieldUpdater<Avaliacao, String>() {
          @Override
          public void update(int index, Avaliacao object, String value) {
              object.setRecGrupoSecundario(value);
              GWTServiceAvaliacao.Util.getInstance().updateRow(object, callbackUpdateRow);
          }
      });  
        

      

        Column<Avaliacao, String> removeColumn = new Column<Avaliacao, String>(new MyImageCell()) {
            @Override
            public String getValue(Avaliacao object) {
                return "images/delete.png";
            }
        };

        cellTable.addColumn(columnIdAvaliacao, "Id");
        cellTable.addColumn(columnData, "Data");
        cellTable.addColumn(columnObs, "Observação");
        cellTable.addColumn(columnRecGrupoPrimario, "Rec_Grupo_Primario");
        cellTable.addColumn(columnRecGrupoSecundario, "Rec_Grupo_Secundario");
    
        cellTable.addColumn(removeColumn, txtConstants.geralRemover());

        
        
        cellTable.getColumn(cellTable.getColumnIndex(columnObs)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(columnData)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(columnRecGrupoPrimario)).setCellStyleNames("edit-cell");  
        cellTable.getColumn(cellTable.getColumnIndex(columnRecGrupoSecundario)).setCellStyleNames("edit-cell");  

        cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");

    }

 
    public void initSortHandler(ListHandler<Avaliacao> sortHandler) {
        
        

        columnRecGrupoPrimario.setSortable(true);
        sortHandler.setComparator(columnRecGrupoPrimario, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getRecGrupoPrimario().compareTo(o2.getRecGrupoPrimario());
          }
        });     
        
        columnRecGrupoSecundario.setSortable(true);
        sortHandler.setComparator(columnRecGrupoSecundario, new Comparator<Avaliacao>() {
          @Override
          public int compare(Avaliacao o1, Avaliacao o2) {
            return o1.getRecGrupoSecundario().compareTo(o2.getRecGrupoSecundario());
          }
        });    
    }
    


    
    


    
    private class ClickHandlerGerarGrafico implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            populateGrid();        
        }
        
    }


    
}
