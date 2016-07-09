package com.jornada.client.ambiente.salao.reserva;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.EditTextCell;
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
import com.jornada.client.ambiente.general.MpGridMsgReservaSaloes;
import com.jornada.client.classes.listBoxes.MpListBoxSaloes;
import com.jornada.client.classes.listBoxes.MpListBoxTurno;
import com.jornada.client.classes.resources.CellTableStyle;
import com.jornada.client.classes.widgets.cells.MpDatePickerCell;
import com.jornada.client.classes.widgets.cells.MpSimplePager;
import com.jornada.client.classes.widgets.cells.MpStyledSelectionCell;
import com.jornada.client.classes.widgets.datebox.MpDateBoxWithImage;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.label.MpLabelRight;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpSpaceVerticalPanel;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceReserva;
import com.jornada.shared.classes.Reserva;
import com.jornada.shared.classes.salao.Saloes;
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
//    private Column<Reserva, String> chegouColumn;
    private Column<Reserva, Date> dataReservaColumn;
    private Column<Reserva, String> salaoColumn;
    private Column<Reserva, String> mesaColumn;
    
    private MpGridMsgReservaSaloes gridMsgReservaSaloes;
    
    private TextBox txtSearch;
    ArrayList<Reserva> arrayListBackup = new ArrayList<Reserva>();
    
//    private LinkedHashMap<String, String> listaChegou = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> listaSaloes = new LinkedHashMap<String, String>();

    MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
    MpDialogBox mpDialogBoxWarning = new MpDialogBox();
    MpPanelLoading mpPanelLoading = new MpPanelLoading("images/radar.gif");

    TextConstants txtConstants;
    
    private String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    
    private MpDateBoxWithImage mpDateBoxDataAgenda;
    MpListBoxTurno listBoxTurno;
    MpListBoxSaloes listBoxSaloes;
    
    public Date dataReserva;
    public String strTurno;


    private static VisualizarReserva uniqueInstance;
    
    final SelectionModel<Reserva> selectionModel;

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
        
        listBoxSaloes = new MpListBoxSaloes();
        
        listBoxTurno = new MpListBoxTurno();
        listBoxTurno.addChangeHandler(new MpCursoSelectionChangeHandler());
        
        mpDateBoxDataAgenda.getDate().setWidth("150px");
        listBoxTurno.setWidth("120px");

        Label lblEmpty = new Label("Nenhuma reserva associada a esta Data ou turno.");
        
        gridMsgReservaSaloes = new MpGridMsgReservaSaloes();
        
        MpSpaceVerticalPanel mpSpaceVerticalPanel = new MpSpaceVerticalPanel();
        mpSpaceVerticalPanel.setBorderWidth(1);
        mpSpaceVerticalPanel.setWidth("100%");

        cellTable = new CellTable<Reserva>(5, GWT.<CellTableStyle> create(CellTableStyle.class));

        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        cellTable.setEmptyTableWidget(lblEmpty);

        dataProvider.addDataDisplay(cellTable);

        selectionModel = new MultiSelectionModel<Reserva>(Reserva.KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Reserva> createCheckboxManager());

        initTableColumns(selectionModel);

        MpSimplePager mpPager = new MpSimplePager();
        mpPager.setDisplay(cellTable);
        mpPager.setPageSize(50);

        if (txtSearch == null) {
            txtSearch = new TextBox();
            txtSearch.setStyleName("design_text_boxes");
        }

        txtSearch.addKeyUpHandler(new EnterKeyUpHandler());
        
        
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
        flexTableFiltrar.setWidget(0, 2, txtSearch);

        
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

        VerticalPanel vPanelVisualizarGrid = new VerticalPanel();
        vPanelVisualizarGrid.add(gridMsgReservaSaloes);
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
                uniqueInstance.updateMessage();
            }

            public void onFailure(Throwable caught) {
                mpPanelLoading.setVisible(false);
                mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
                mpDialogBoxWarning.setBodyText(txtConstants.geralErroAtualizar(txtConstants.periodo()));
                mpDialogBoxWarning.showDialog();
            }
        };

        /*********************** End Callbacks **********************/


        updateMessageAndGrid();        
        
        setWidth("100%");
        super.add(vPanelVisualizarGrid);

    }


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
        
        
//        MpListBoxReservaChegou mpListBoxSimNao = new MpListBoxReservaChegou();
//        for(int i=0;i<mpListBoxSimNao.getItemCount();i++){
//            listaChegou.put(mpListBoxSimNao.getValue(i), mpListBoxSimNao.getItemText(i));
//        }     
//        
//        MpStyledSelectionCell chegouCell = new MpStyledSelectionCell(listaChegou,"design_text_boxes");
//        
//        chegouColumn = new Column<Reserva, String>(chegouCell) {
//            @Override
//            public String getValue(Reserva object) {
//                // String strHora = MpUtilClient.add_AM_PM(object.getHora());
//                return object.getChegou();
//            }
//        };
//        chegouColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
//            @Override
//            public void update(int index, Reserva object, String value) {
//                object.setChegou(value);
//                GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
//            }
//        });    
        
        
        
        
//        MpStyledSelectionCell saloesCell = new MpStyledSelectionCell(listaSaloes,"design_text_boxes");
        
//        salaoColumn = new Column<Reserva, String>(new TextCell()) {
//            @Override
//            public String getValue(Reserva object) {
//                return object.getSalao();
//            }
//        };
//        salaoColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
//            @Override
//            public void update(int index, Reserva object, String value) {
//                object.setSalao(value);
//                GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
//            }
//        });       
        
        Saloes saloes = new Saloes();
        listaSaloes.put(saloes.getSalaoInterno().getNomeSalao(), saloes.getSalaoInterno().getNomeSalao());
        listaSaloes.put(saloes.getSalaoExternoAberto().getNomeSalao(), saloes.getSalaoExternoAberto().getNomeSalao());
        listaSaloes.put(saloes.getSalaoExternoCoberto().getNomeSalao(), saloes.getSalaoExternoCoberto().getNomeSalao());
        listaSaloes.put(saloes.getSalaoChurrasqueira().getNomeSalao(), saloes.getSalaoChurrasqueira().getNomeSalao());

        MpStyledSelectionCell saloesCell = new MpStyledSelectionCell(listaSaloes, "design_text_boxes");

        salaoColumn = new Column<Reserva, String>(saloesCell) {
            @Override
            public String getValue(Reserva object) {

                String strSalao = object.getSalao();
                return strSalao;
            }
        };
        salaoColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                object.setSalao(value);
                GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
            }
        });
        
        
        mesaColumn = new Column<Reserva, String>(new EditTextCell()) {
            @Override
            public String getValue(Reserva object) {
                return object.getMesa();              
            }
        };
        mesaColumn.setFieldUpdater(new FieldUpdater<Reserva, String>() {
            @Override
            public void update(int index, Reserva object, String value) {
                    object.setMesa(value);
                    GWTServiceReserva.Util.getInstance().updateRow(object, callbackUpdateRow);
            }
        });
        
        observacaoColumn = new Column<Reserva, String>(new TextCell()) {
            @Override
            public String getValue(Reserva object) {
                return object.getObservacao();
            }
        };
        
        cellTable.addColumn(dataReservaColumn, "Data");
        cellTable.addColumn(turnoColumn, "Turno");
        cellTable.addColumn(horarioColumn, "Horário");
        cellTable.addColumn(salaoColumn, "*Salão");
        cellTable.addColumn(nomeReservaColumn, "Nome");
        cellTable.addColumn(numeroAdultosColumn, "Adultos");
        cellTable.addColumn(numeroCriancasColumn, "Crianças");
        cellTable.addColumn(mesaColumn, "*Mesa");
        cellTable.addColumn(cidadeColumn, "Cidade");
        cellTable.addColumn(telefoneColumn, "Telefone");
//        cellTable.addColumn(chegouColumn, "*Chegou");
        cellTable.addColumn(observacaoColumn, "Observação");        

        
        cellTable.getColumn(cellTable.getColumnIndex(turnoColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(horarioColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(mesaColumn)).setCellStyleNames("edit-cell");
        cellTable.getColumn(cellTable.getColumnIndex(salaoColumn)).setCellStyleNames("edit-cell");
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

//        chegouColumn.setSortable(true);
//        sortHandler.setComparator(chegouColumn, new Comparator<Reserva>() {
//            @Override
//            public int compare(Reserva o1, Reserva o2) {
//                return o1.getChegou().compareTo(o2.getChegou());
//            }
//        });
        
        observacaoColumn.setSortable(true);
        sortHandler.setComparator(observacaoColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getObservacao().compareTo(o2.getObservacao());
            }
        });      
        
        salaoColumn.setSortable(true);
        sortHandler.setComparator(salaoColumn, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                return o1.getSalao().compareTo(o2.getSalao());
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
        
        
        mesaColumn.setSortable(true);
        sortHandler.setComparator(mesaColumn, new Comparator<Reserva>() {
          @Override
          public int compare(Reserva o1, Reserva o2) {              
              return o1.getMesa().compareTo(o2.getMesa());
          }
        });  



    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/
    /*************************************** BEGIN Filterting CellTable ***************************************/

    private class EnterKeyUpHandler implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
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
    
    
    public void updateMessageAndGrid() {
        updateMessage();
        populateGrid();        
    }
    
    
    public void updateMessage(){
        gridMsgReservaSaloes.updateMessage(mpDateBoxDataAgenda.getDate().getValue(), listBoxTurno.getSelectedValue());
    }

}
