package com.jornada.client.ambiente.administracao.reserva;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.jornada.client.ambiente.administracao.reserva.dialog.MpDialogBoxExcelReserva;
import com.jornada.client.classes.listBoxes.MpListBoxTurno;
import com.jornada.client.classes.listBoxes.ambiente.general.MpListBoxSimNao;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.cells.MpTextAreaEditCell;
import com.jornada.client.classes.widgets.dialog.MpConfirmDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.classes.widgets.timepicker.MpTimePicker;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.FieldVerifier;
import com.jornada.shared.classes.Reserva;
import com.jornada.shared.classes.utility.MpUtilClient;

public class EditarReserva extends VerticalPanel {

    private AsyncCallback<Boolean> callbackUpdateRow;
    private AsyncCallback<Boolean> callbackDeletePeriodo;

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
    private LinkedHashMap<String, String> listTurno = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> listaHora = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> listaChegou = new LinkedHashMap<String, String>();
    
//    private MpTimePicker mpTimePicker;


    private TextBox txtSearch;
    ArrayList<Reserva> arrayListBackup = new ArrayList<Reserva>();

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    TextConstants txtConstants;
    
//    private MpDateBoxWithImage mpDateBoxDataAgenda;
//    MpListBoxTurno listBoxTurno;
    
    public Date dataReserva;
    public String strTurno;
    


//    public TelaInicialReserva telaInicialReserva;
    public AdicionarReserva adicionarReserva;
    private static EditarReserva uniqueInstance;

    public static EditarReserva getInstance(AdicionarReserva adicionarReserva, Date dataReserva, String strTurno) {

        if (uniqueInstance == null) {
            uniqueInstance = new EditarReserva(adicionarReserva, dataReserva, strTurno);
        }

        return uniqueInstance;
    }


    private EditarReserva(AdicionarReserva adicionarReserva, Date dataReserva, String strTurno) {

        txtConstants = GWT.create(TextConstants.class);
        
        this.adicionarReserva = adicionarReserva;
        
        this.dataReserva = dataReserva;
        this.strTurno = strTurno;

        mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
        mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
        mpPanelLoading.setTxtLoading(txtConstants.geralCarregando());
        mpPanelLoading.show();
        mpPanelLoading.setVisible(false);

        Label lblEmpty = new Label("Nenhuma reserva associada a esta Data ou turno.");

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

        FlexTable flexTableFiltrar = new FlexTable();
        flexTableFiltrar.setCellSpacing(3);
        flexTableFiltrar.setCellPadding(3);
        flexTableFiltrar.setBorderWidth(0);
        flexTableFiltrar.setWidget(0, 0, mpPager);
        flexTableFiltrar.setWidget(0, 1, new MpSpaceVerticalPanel());
        flexTableFiltrar.setWidget(0, 2, txtSearch);
//        flexTableFiltrar.setWidget(0, 3, btnFiltrar);
//        flexTableFiltrar.setWidget(0, 4, new MpSpaceVerticalPanel());
        flexTableFiltrar.setWidget(0, 3, mpPanelLoading);
        
        
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
                uniqueInstance.adicionarReserva.updateMessage();
            }

            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo()));
                mpDialogBoxWarning.showDialog();
            }
        };

        callbackDeletePeriodo = new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean success) {
                mpPanelLoading.setVisible(false);
                if (success == true) {
//                    populateGrid(uniqueInstance.dataReserva, uniqueInstance.strTurno);
//                    telaInicialReserva.populateGrid();  
                    uniqueInstance.adicionarReserva.updateMessage();
                    
                } else {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroRemover("a reserva."));
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


        populateGrid(dataReserva, strTurno);
        
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
                final Reserva per = (Reserva) context.getKey();
                @SuppressWarnings("unused")
                CloseHandler<PopupPanel> closeHandler;

                MpConfirmDialogBox confirmationDialog = new MpConfirmDialogBox(txtConstants.geralRemover(), "Deseja Remover a reserva no nome de "+(per.getNomeReserva())+"?", txtConstants.geralSim(), txtConstants.geralNao(),

                closeHandler = new CloseHandler<PopupPanel>() {

                    public void onClose(CloseEvent<PopupPanel> event) {

                        MpConfirmDialogBox x = (MpConfirmDialogBox) event.getSource();

                        if (x.primaryActionFired()) {

                            GWTServiceReserva.Util.getInstance().deleteRow(per.getIdReserva(), callbackDeletePeriodo);

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

    public void populateGrid(Date dataReserva,  String strTurno) {
        mpPanelLoading.setVisible(true);

//        Date dataReserva = mpDateBoxDataAgenda.getDate().getValue(); 
//        String strTurno = listBoxTurno.getSelectedValue();
        
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
        dataReservaColumn.setFieldUpdater(new FieldUpdater<Reserva, Date>() {
            @Override
            public void update(int index, Reserva periodo, Date value) {
                // Called when the user changes the value.
                periodo.setDataReserva(value);
                GWTServiceReserva.Util.getInstance().updateRow(periodo, callbackUpdateRow);
//                adicionarReserva.updateMessage();
            }
        });        
        
        
        MpListBoxTurno listBoxTurnoInterno = new MpListBoxTurno();
        
        for(int i=0;i<listBoxTurnoInterno.getItemCount();i++){
            listTurno.put(listBoxTurnoInterno.getItemText(i),listBoxTurnoInterno.getItemText(i));
        }

        turnoColumn = new Column<Reserva, String>(new MpStyledSelectionCell(listTurno, "design_text_boxes")) {
            @Override
            public String getValue(Reserva object) {
                
                return object.getTurno();
            }

        };
        turnoColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                // Called when the user changes the value.
                object.setTurno(value);
                GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
//                adicionarReserva.updateMessage();
            }
        });        
        
        
        
        MpTimePicker mpTimePicker = new MpTimePicker(11, 23);
        for(int i=0;i<mpTimePicker.getItemCount();i++){
            listaHora.put(mpTimePicker.getValue(i), mpTimePicker.getItemText(i));
        }       
        
        MpStyledSelectionCell horaCell = new MpStyledSelectionCell(listaHora,"design_text_boxes");
        
        horarioColumn = new Column<Reserva, String>(horaCell) {
            @Override
              public String getValue(Reserva object) {
//                  String strHora = MpUtilClient.add_AM_PM(object.getHora());
                  return object.getHorario();
              }
          };
          horarioColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
              @Override
              public void update(int index, Reserva object, String value) {
                  object.setHorario(value);
                  GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
              }
          });

        
        nomeReservaColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva periodo) {
                return periodo.getNomeReserva();
            }

        };
        nomeReservaColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva periodo, String value) {
                // Called when the user changes the value.
                if (FieldVerifier.isValidName(value)) {
                    if (!value.contains("[") && !value.contains("]")) {
                        periodo.setNomeReserva(value);
                        GWTServiceReserva.Util.getInstance().updateRow(periodo, callbackUpdateRow);
                    } else {
                        mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                        mpDialogBoxWarning.setBodyText(txtConstants.geralErroCaracterColchete());
                        mpDialogBoxWarning.showDialog();
                    }
                } else {
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralCampoObrigatorio(txtConstants.periodoNome()));
                    mpDialogBoxWarning.showDialog();

                }
            }
        });
        
        
        numeroAdultosColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva object) {
                return Integer.toString(object.getNumeroAdultos());              
            }

        };
        numeroAdultosColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                int intNumeroAdultos=0;              
                if (FieldVerifier.isValidInteger(value)) {
                    intNumeroAdultos = Integer.parseInt(value);
                    object.setNumeroAdultos(intNumeroAdultos);
                    GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
                    
                }
                else{
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroTipo(txtConstants.geralNumeroInteiro()));
                    mpDialogBoxWarning.showDialog();
                }

            }
        });
        
        numeroCriancasColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva object) {
                return Integer.toString(object.getNumeroCriancas());              
            }

        };
        numeroCriancasColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                int intNumeroCriancas=0;              
                if (FieldVerifier.isValidInteger(value)) {
                    intNumeroCriancas = Integer.parseInt(value);
                    object.setNumeroCriancas(intNumeroCriancas);
                    GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
//                    adicionarReserva.updateMessage();
                }
                else{
                    mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                    mpDialogBoxWarning.setBodyText(txtConstants.geralErroTipo(txtConstants.geralNumeroInteiro()));
                    mpDialogBoxWarning.showDialog();
                }
            }
        });        
        
        cidadeColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva object) {
                return object.getCidade();
            }
        };
        
       cidadeColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva periodo, String value) {
                // Called when the user changes the value.
                periodo.setCidade(value);
                GWTServiceReserva.Util.getInstance().updateRow(periodo, callbackUpdateRow);
            }
        });

        
        telefoneColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva periodo) {
                return periodo.getTelefone();
            }
        };
        telefoneColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva periodo, String value) {
                // Called when the user changes the value.
                periodo.setTelefone(value);
                GWTServiceReserva.Util.getInstance().updateRow(periodo, callbackUpdateRow);
            }
        });
        
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
        
        observacaoColumn = new Column<Reserva, String>(new MpTextAreaEditCell(3,50)) {
            @Override
            public String getValue(Reserva object) {
                return object.getObservacao();
            }
        };
        observacaoColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                // Called when the user changes the value.
                object.setObservacao(value);
                GWTServiceReserva.Util.getInstance().updateRow(object,callbackUpdateRow);
            }
        });

        Column<Reserva, String> removeColumn = new Column<Reserva, String>(new MyImageCell()) {
            @Override
            public String getValue(Reserva object) {
                return "images/delete.png";
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
        cellTable.addColumn(removeColumn, txtConstants.geralRemover());

        
        cellTable.getColumn(cellTable.getColumnIndex(turnoColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(horarioColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(nomeReservaColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(numeroAdultosColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(numeroCriancasColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(cidadeColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(telefoneColumn)).setCellStyleNames("edit-cell");    
        cellTable.getColumn(cellTable.getColumnIndex(observacaoColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(removeColumn)).setCellStyleNames("hand-over");

    }

//    public void initSortHandler(ListHandler<Reserva> sortHandler) {
//        nomeReservaColumn.setSortable(true);
//        sortHandler.setComparator(nomeReservaColumn, new Comparator<Reserva>() {
//            @Override
//            public int compare(Reserva o1, Reserva o2) {
//                return o1.getNomeReserva().compareTo(o2.getNomeReserva());
//            }
//        });
//
//        telefoneColumn.setSortable(true);
//        sortHandler.setComparator(telefoneColumn, new Comparator<Reserva>() {
//            @Override
//            public int compare(Reserva o1, Reserva o2) {
//                return o1.getTelefone().compareTo(o2.getTelefone());
//            }
//        });
//
//        cidadeColumn.setSortable(true);
//        sortHandler.setComparator(cidadeColumn, new Comparator<Reserva>() {
//            @Override
//            public int compare(Reserva o1, Reserva o2) {
//                return o1.getCidade().compareTo(o2.getCidade());
//            }
//        });
//
//        // pesoColumn.setSortable(true);
//        // sortHandler.setComparator(pesoColumn, new Comparator<Reserva>() {
//        // @Override
//        // public int compare(Reserva o1, Reserva o2) {
//        // return o1.getPeso().compareTo(o2.getPeso());
//        // }
//        // });
//
//        dataReservaColumn.setSortable(true);
//        sortHandler.setComparator(dataReservaColumn, new Comparator<Reserva>() {
//            @Override
//            public int compare(Reserva o1, Reserva o2) {
//                return o1.getDataReserva().compareTo(o2.getDataReserva());
//            }
//        });
//
//        // dataFinalColumn.setSortable(true);
//        // sortHandler.setComparator(dataFinalColumn, new Comparator<Reserva>()
//        // {
//        // @Override
//        // public int compare(Reserva o1, Reserva o2) {
//        // return o1.getDataFinal().compareTo(o2.getDataFinal());
//        // }
//        // });
//
//    }
    
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

            Date data = uniqueInstance.adicionarReserva.mpDateBoxDataAgenda.getDate().getValue();
            String strTurnoInterno = uniqueInstance.adicionarReserva.listBoxTurno.getSelectedValue();
            MpDialogBoxExcelReserva.getInstance(data, strTurnoInterno);
        }
    }


}
