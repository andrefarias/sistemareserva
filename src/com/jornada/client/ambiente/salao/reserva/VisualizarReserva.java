package com.jornada.client.ambiente.salao.reserva;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
//import com.jornada.client.ambiente.coordenador.reserva.AdicionarReserva.callbackClientes;
import com.jornada.client.ambiente.administracao.reserva.dialog.MpDialogBoxExcelReserva;
import com.jornada.client.classes.listBoxes.MpListBoxTurno;
import com.jornada.client.classes.listBoxes.ambiente.general.MpListBoxSimNao;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabel;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.config.ConfigClient;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.classes.Clientes;
import com.jornada.shared.classes.Reserva;
import com.jornada.shared.classes.utility.MpUtilClient;

public class VisualizarReserva extends VerticalPanel {

    private AsyncCallback<Boolean> callbackUpdateRow;

    private CellTable<Reserva> cellTable;
    private ListDataProvider<Reserva> dataProvider = new ListDataProvider<Reserva>();
    private Column<Reserva, String> nomeReservaColumn;
    private Column<Reserva, String> telefoneColumn;
    private Column<Reserva, String> cidadeColumn;
    private Column<Reserva, String> turnoColumn;
    private Column<Reserva, String> numeroAdultosColumn;
    private Column<Reserva, String> numeroCriancasColumn;
    private Column<Reserva, String> horarioColumn;
    private Column<Reserva, String> observacaoColumn;
    private Column<Reserva, String> chegouColumn;
    private Column<Reserva, Date> dataReservaColumn;

    public Grid gridInformacao;
    
    ConfigClient clientConfig = GWT.create(ConfigClient.class);
    private int LIMITE_MAXIMO = Integer.parseInt(clientConfig.lotacaoMaxima());
    private int LIMITE_QUASE_MAXIMO = Integer.parseInt(clientConfig.lotacaoQuaseMaxima());

    private TextBox txtSearch;
    ArrayList<Reserva> arrayListBackup = new ArrayList<Reserva>();
    
    private LinkedHashMap<String, String> listaChegou = new LinkedHashMap<String, String>();

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    TextConstants txtConstants;
    
    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    
    private MpDateBoxWithImage mpDateBoxDataAgenda;
    MpListBoxTurno listBoxTurno;
    
    public Date dataReserva;
    public String strTurno;
    
    public MpLabel lblNumeroTotal = new MpLabel("");

    private static VisualizarReserva uniqueInstance;

    public static VisualizarReserva getInstance() {

        if (uniqueInstance == null) {
            uniqueInstance = new VisualizarReserva();
        }

        return uniqueInstance;
    }


    @SuppressWarnings("deprecation")
    private VisualizarReserva() {

        txtConstants = GWT.create(TextConstants.class);
        
        MpLabelRight lblDateInicial = new MpLabelRight("Data Reserva");
        MpLabelRight lblTurno = new MpLabelRight("Turno");

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);
        
        mpDateBoxDataAgenda = new MpDateBoxWithImage();
        mpDateBoxDataAgenda.getDate().setFormat(new DefaultFormat(DateTimeFormat.getFullDateFormat()));

        mpDateBoxDataAgenda.getDate().setValue(new Date());
        mpDateBoxDataAgenda.getDate().addValueChangeHandler(new MyDateValueChangeHandler());
        
        listBoxTurno = new MpListBoxTurno();
        listBoxTurno.addChangeHandler(new MpCursoSelectionChangeHandler());
        
        mpDateBoxDataAgenda.getDate().setWidth("150px");
        listBoxTurno.setWidth("120px");

        Label lblEmpty = new Label("Nenhuma reserva associada a esta Data ou turno.");
        
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
        lblNumeroTotal.setStyleName("label_lotacao_bold_13px");
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");

        cellTable = new CellTable<Reserva>(5, GWT.<CellTableStyle> create(CellTableStyle.class));

        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);

        dataProvider.addDataDisplay(cellTable);

        final SelectionModel<Reserva> selectionModel = new MultiSelectionModel<Reserva>(Reserva.KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Reserva> createCheckboxManager());

        initTableColumns(selectionModel);

        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(50);

        MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");

        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }

        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
        btnFiltrar.addClickHandler(new ClickHandlerFiltrar());
        
        
        FlexTable flexTableDate = new FlexTable();
        flexTableDate.setCellSpacing(3);
        flexTableDate.setCellPadding(3);
        flexTableDate.setBorderWidth(0);
        flexTableDate.setWidget(0, 0, lblDateInicial);
        flexTableDate.setWidget(0, 1, mpDateBoxDataAgenda);
        flexTableDate.setWidget(0, 2, new InlineHTML(strInLineSpace));
        flexTableDate.setWidget(0, 3, lblTurno);
        flexTableDate.setWidget(0, 4, listBoxTurno);
        flexTableDate.setWidget(0, 5, new MpSpaceVerticalPanel());
        flexTableDate.setWidget(0, 6, mpPanelLoading);

        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(3);
        flexTableFiltrar.setCellPadding(3);
        flexTableFiltrar.setBorderWidth(0);
        flexTableFiltrar.setWidget(0, 0, mpPager);
        flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
//        flexTableFiltrar.setWidget(0, 2, lblDateInicial);
//        flexTableFiltrar.setWidget(0, 3, mpDateBoxDataAgenda);
//        flexTableFiltrar.setWidget(0, 4, new InlineHTML(strInLineSpace));
//        flexTableFiltrar.setWidget(0, 5, lblTurno);
//        flexTableFiltrar.setWidget(0, 6, listBoxTurno);
//        flexTableFiltrar.setWidget(0, 7, new InlineHTML(strInLineSpace));
        flexTableFiltrar.setWidget(0, 2, txtSearch);
        flexTableFiltrar.setWidget(0, 3, btnFiltrar);

        
        
        Image imgExcel = new Image("images/excel.24.png");
        imgExcel.addClickHandler(new ClickHandlerExcel());
        imgExcel.setStyleName("hand-over");
        imgExcel.setTitle(txtConstants.geralExcel());
        
        
        FlexTable flexTableImg = new FlexTable();
        flexTableImg.setCellPadding(2);
        flexTableImg.setCellSpacing(2);
        flexTableImg.setWidget(0, 0, imgExcel);
        flexTableImg.setBorderWidth(0);
        
        
        FlexTable flexTableMenu = new FlexTable();
        flexTableMenu.setCellPadding(0);
        flexTableMenu.setCellSpacing(0);
        flexTableMenu.setBorderWidth(0);
        flexTableMenu.setWidth("100%");
        flexTableMenu.setWidget(0, 0, flexTableFiltrar);
        flexTableMenu.setWidget(0, 1, flexTableImg);
        flexTableMenu.getCellFormatter().setWidth(0, 0, "70%");
        flexTableMenu.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
        flexTableMenu.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_BOTTOM);

        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setWidth("100%");
        scrollPanel.setAlwaysShowScrollBars(false);
        scrollPanel.add(cellTable);
        
        MpLabel lblLimiteMaximo = new MpLabel("*Limite Máximo de Clientes : " + clientConfig.lotacaoMaxima());
        lblLimiteMaximo.setStyleName("label_lotacao_bold_13px");

        VerticalPanel vPanelVisualizarGrid = new VerticalPanel();
        vPanelVisualizarGrid.add(gridInformacao);
        vPanelVisualizarGrid.add(lblLimiteMaximo);
        vPanelVisualizarGrid.add(flexTableDate);
        vPanelVisualizarGrid.add(mpSpaceVerticalPanel);
        vPanelVisualizarGrid.add(flexTableMenu);
        vPanelVisualizarGrid.add(scrollPanel);
        vPanelVisualizarGrid.setWidth("100%");

        /************************* Begin Callback's *************************/
//
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

//        callbackDeletePeriodo = new AsyncCallback<Boolean>() {
//
//            public void onSuccess(Boolean success) {
//                mpPanelLoading.setVisible(false);
//                if (success == true) {
////                    populateGrid(uniqueInstance.dataReserva, uniqueInstance.strTurno);
////                    telaInicialReserva.populateGrid();  
////                    uniqueInstance.adicionarReserva.updateMessage();
//                    
//                } else {
//                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover("a reserva."));
//                    mpDialogBoxWarning.showDialog();
//                }
//
//            }
//
//            public void onFailure(Throwable caught) {
//                mpPanelLoading.setVisible(false);
//                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//                mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover(txtConstants.periodo()));
//                mpDialogBoxWarning.showDialog();
//
//            }
//        };

        /*********************** End Callbacks **********************/


//        populateGrid();
        updateMessageAndGrid();
        
        setWidth("100%");
        super.add(vPanelVisualizarGrid);

    }

//    private class MyImageCell extends ImageCell {
//
//        @Override
//        public Set<String> getConsumedEvents() {
//            Set<String> consumedEvents = new HashSet<String>();
//            consumedEvents.add("click");
//            return consumedEvents;
//        }
//
//        @Override
//        public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
//            switch (DOM.eventGetType((Event) event)) {
//            case Event.ONCLICK:
//                final Reserva per = (Reserva) context.getKey();
//                @SuppressWarnings("unused")
//                CloseHandler<PopupPanel> closeHandler;
//
//                MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(txtConstants.geralRemover(), "Deseja Remover a reserva no nome de "+(per.getNomeReserva())+"?", txtConstants.geralSim(), txtConstants.geralNao(),
//
//                closeHandler = new CloseHandler<PopupPanel>() {
//
//                    public void onClose(CloseEvent<PopupPanel> event) {
//
//                        MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();
//
//                        if (x.primaryActionFired()) {
//
//                            GWTServiceReserva.Util.getInstance().deleteRow(per.getIdReserva(), callbackDeletePeriodo);
//
//                        }
//                    }
//                }
//
//                );
//                confirmationDialog.paint();
//                break;
//
//            default:
//                Window.alert("Test default");
//                break;
//            }
//        }
//
//    }

    public void populateGrid() {
        mpPanelLoading.setVisible(true);

        Date dataReserva = mpDateBoxDataAgenda.getDate().getValue(); 
        String strTurno = listBoxTurno.getSelectedValue();
        
        GWTServiceReserva.Util.getInstance().getReservas(dataReserva, strTurno,
        new AsyncCallback<ArrayList<Reserva>>() {

            @Override
            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroCarregar(txtConstants.periodo()));
            }

            @Override
            public void onSuccess(ArrayList<Reserva> list) {
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



    private void addCellTableData(ListDataProvider<Reserva> dataProvider) {

        ListHandler<Reserva> sortHandler = new ListHandler<Reserva>(dataProvider.getList());

        cellTable.addColumnSortHandler(sortHandler);

        initSortHandler(sortHandler);

    }

    private void initTableColumns(final SelectionModel<Reserva> selectionModel) {
        
        dataReservaColumn = new Column<Reserva, Date>(new MpDatePickerCell()) {
            @Override
            public Date getValue(Reserva object) {
                return object.getDataReserva();
            }
        };

        turnoColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                
                return object.getTurno();
            }

        };

        
        horarioColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
              public String getValue(Reserva object) {
                  return object.getHorario();
              }
          };


        
        nomeReservaColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva periodo) {
                return periodo.getNomeReserva();
            }

        };
        
        numeroAdultosColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                return Integer.toString(object.getNumeroAdultos());              
            }

        };

        
        numeroCriancasColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                return Integer.toString(object.getNumeroCriancas());              
            }

        };
    
        
        cidadeColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                return object.getCidade();
            }
        };


        
        telefoneColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva periodo) {
                return periodo.getTelefone();
            }
        };
        
        
        MpListBoxSimNao mpListBoxSimNao = new MpListBoxSimNao();
        for(int i=0;i<mpListBoxSimNao.getItemCount();i++){
            listaChegou.put(mpListBoxSimNao.getValue(i), mpListBoxSimNao.getItemText(i));
        }     
        
        MpStyledSelectionCell chegouCell = new MpStyledSelectionCell(listaChegou,"design_text_boxes");
        
        chegouColumn = new Column<Reserva, String>(chegouCell) {
            @Override
            public String getValue(Reserva object) {
                // String strHora = MpUtilClient.add_AM_PM(object.getHora());
                return object.getChegou();
            }
        };
        chegouColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                object.setChegou(value);
                GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
            }
        });        

        
        observacaoColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                return object.getObservacao();
            }
        };

        
        cellTable.addColumn(dataReservaColumn, "Data Reserva");
        cellTable.addColumn(turnoColumn, "Turno");
        cellTable.addColumn(horarioColumn, "Horario Chegada");
        cellTable.addColumn(nomeReservaColumn, "Nome Reserva");
        cellTable.addColumn(numeroAdultosColumn, "Número Adultos");
        cellTable.addColumn(numeroCriancasColumn, "Número Crianças");
        cellTable.addColumn(cidadeColumn, "Cidade");
        cellTable.addColumn(telefoneColumn, "Telefone");
        cellTable.addColumn(chegouColumn, "Reserva Chegou");
        cellTable.addColumn(observacaoColumn, "Observação");        

        
        cellTable.getColumn(cellTable.getColumnIndex(turnoColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(horarioColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(nomeReservaColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(numeroAdultosColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(numeroCriancasColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(cidadeColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(telefoneColumn)).setCellStyleNames("edit-cell");    
        cellTable.getColumn(cellTable.getColumnIndex(observacaoColumn)).setCellStyleNames("edit-cell");


    }

    public void initSortHandler(ListHandler<Reserva> sortHandler) {
        
        
        dataReservaColumn.setSortable(true);
        sortHandler.setComparator(dataReservaColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getDataReserva().compareTo(o2.getDataReserva());
            }
        });
        
        turnoColumn.setSortable(true);
        sortHandler.setComparator(turnoColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getTurno().compareTo(o2.getTurno());
            }
        });
        
        horarioColumn.setSortable(true);
        sortHandler.setComparator(horarioColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getHorario().compareTo(o2.getHorario());
            }
        });
                
        
        nomeReservaColumn.setSortable(true);
        sortHandler.setComparator(nomeReservaColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getNomeReserva().compareTo(o2.getNomeReserva());
            }
        });
        


        telefoneColumn.setSortable(true);
        sortHandler.setComparator(telefoneColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getTelefone().compareTo(o2.getTelefone());
            }
        });

        cidadeColumn.setSortable(true);
        sortHandler.setComparator(cidadeColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getCidade().compareTo(o2.getCidade());
            }
        });

        chegouColumn.setSortable(true);
        sortHandler.setComparator(chegouColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getChegou().compareTo(o2.getChegou());
            }
        });
        
        observacaoColumn.setSortable(true);
        sortHandler.setComparator(observacaoColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getObservacao().compareTo(o2.getObservacao());
            }
        });      
        
        numeroAdultosColumn.setSortable(true);
        sortHandler.setComparator(numeroAdultosColumn, new Comparator<Reserva>() {
          @Override
          public int compare(Reserva o1, Reserva o2) {              
              int primitive1 = o1.getNumeroAdultos();
              int primitive2 = o2.getNumeroAdultos();
              Integer a = new Integer(primitive1);
              Integer b = new Integer(primitive2);
              return a.compareTo(b);
          }
        });  
        
        numeroCriancasColumn.setSortable(true);
        sortHandler.setComparator(numeroCriancasColumn, new Comparator<Reserva>() {
          @Override
          public int compare(Reserva o1, Reserva o2) {              
              int primitive1 = o1.getNumeroCriancas();
              int primitive2 = o2.getNumeroCriancas();
              Integer a = new Integer(primitive1);
              Integer b = new Integer(primitive2);
              return a.compareTo(b);
          }
        });          



        // dataFinalColumn.setSortable(true);
        // sortHandler.setComparator(dataFinalColumn, new Comparator<Reserva>()
        // {
        // @Override
        // public int compare(Reserva o1, Reserva o2) {
        // return o1.getDataFinal().compareTo(o2.getDataFinal());
        // }
        // });

    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** BEGIN Filterting CellTable ***************************************/

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
//            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                filtrarCellTable(txtSearch.getText());
//            }
        }
    }

    private class ClickHandlerFiltrar implements ClickHandler {
        public void onClick(ClickEvent event) {
            filtrarCellTable(txtSearch.getText());
        }
    }

    public void filtrarCellTable(String strFiltro) {

        removeCellTableFilter();

        strFiltro = strFiltro.toUpperCase();

        if (!strFiltro.isEmpty()) {

            int i = 0;
            while (i < dataProvider.getList().size()) {

//                 strNome = dataProvider.getList().get(i).getNomeReserva();
                String strNome  = (dataProvider.getList().get(i).getNomeReserva()==null?"": dataProvider.getList().get(i).getNomeReserva());
                String strTelefone = (dataProvider.getList().get(i).getTelefone()==null?"": dataProvider.getList().get(i).getTelefone());
                String strCidade = (dataProvider.getList().get(i).getCidade()==null?"": dataProvider.getList().get(i).getCidade());
                String strNumeroAdultos = Integer.toString(dataProvider.getList().get(i).getNumeroAdultos());
                String strNumeroCriancas = Integer.toString(dataProvider.getList().get(i).getNumeroCriancas());
                String strObservacao = (dataProvider.getList().get(i).getObservacao()==null?"": dataProvider.getList().get(i).getObservacao());
                String strDataInicial = MpUtilClient.convertDateToString(dataProvider.getList().get(i).getDataReserva(), "EEEE, MMMM dd, yyyy");
                
                
                
                String strJuntaTexto = strNome.toUpperCase() + " " + strTelefone.toUpperCase() + " " + strCidade.toUpperCase();
                strJuntaTexto += " " + strDataInicial.toUpperCase()+ " " + strNumeroAdultos.toUpperCase()+ " " + strNumeroCriancas.toUpperCase()+ " " + strObservacao.toUpperCase();


                if (!strJuntaTexto.contains(strFiltro)) {
                    dataProvider.getList().remove(i);
                    i = 0;
                    continue;
                }

                i++;
            }

        }

    }

    public void removeCellTableFilter() {

        dataProvider.getList().clear();

        for (int i = 0; i < arrayListBackup.size(); i++) {
            dataProvider.getList().add(arrayListBackup.get(i));
        }
        cellTable.setPageStart(0);
    }
    
    
    private class ClickHandlerExcel implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {

            Date data = mpDateBoxDataAgenda.getDate().getValue();
            String strTurnoInterno = listBoxTurno.getSelectedValue();
            MpDialogBoxExcelReserva.getInstance(data, strTurnoInterno);
        }
    }

    public class MyDateValueChangeHandler implements ValueChangeHandler<Date> {

        public MyDateValueChangeHandler() {
        }

        public void onValueChange(ValueChangeEvent<Date> event) {

            updateMessageAndGrid();
//            populateGrid();
        }
    }
    
    
    private class MpCursoSelectionChangeHandler implements ChangeHandler {

        public void onChange(ChangeEvent event) {
            if (mpDateBoxDataAgenda.getDate().getValue() == null) {
                mpDateBoxDataAgenda.getDate().setValue(new Date());
            }
//            populateGrid();
            updateMessageAndGrid();
        }
    }
    
    
    private class callbackClientes implements AsyncCallback<Clientes> {

        public void onFailure(Throwable caught) {
            mpPanelLoading.setVisible(false);
            mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
            mpDialogBoxWarning.setBodyText("Reserva não pode ser salva com sucesso");
            mpDialogBoxWarning.showDialog();
        }

        public void onSuccess(Clientes result) {

            mpPanelLoading.setVisible(false);

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
    
    public void updateMessageAndGrid() {
        updateMessage();
        populateGrid();        
    }
    
    
    public void updateMessage(){
        GWTServiceReserva.Util.getInstance().getNumeroClientes(mpDateBoxDataAgenda.getDate().getValue(), listBoxTurno.getSelectedValue(), new callbackClientes());
    }

}
