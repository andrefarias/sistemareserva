package com.jornada.client.ambiente.administracao.usuario;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.classes.listBoxes.MpSelectionAluno;
import com.jornada.client.classes.listBoxes.suggestbox.MpListBoxPanelHelper;
import com.jornada.client.classes.widgets.button.MpImageButton;
import com.jornada.client.classes.widgets.dialog.MpDialogBox;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;
import com.jornada.client.classes.widgets.panel.MpPanelLoading;
import com.jornada.client.classes.widgets.panel.MpPanelPageMainView;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.client.service.GWTServiceUsuario;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.utility.MpUtilClient;

public class AssociarPaisAlunos extends VerticalPanel{
	
	
//	private AsyncCallback<ArrayList<Curso>> callbackGetAlunosFiltro;
	private AsyncCallback<ArrayList<Usuario>> callbackGetPaisFiltro;
	private AsyncCallback<ArrayList<Usuario>> callbackGetPaisAssociados;	
//	private AsyncCallback<Boolean> callbackAssociarAlunoAosPais;
	
	
	MpDialogBox mpDialogBoxConfirm = new MpDialogBox();
	MpDialogBox mpDialogBoxWarning = new MpDialogBox();
	MpPanelLoading mpPanelAlunoLoading = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelPaisLoading = new MpPanelLoading("images/radar.gif");
	MpPanelLoading mpPanelAssociandoLoading = new MpPanelLoading("images/radar.gif");
	
	private TextBox txtFiltroAlunos;	
	private TextBox txtFiltroPais;
	
	MpListBoxPanelHelper mpHelperAluno = new  MpListBoxPanelHelper();
	
	private MpSelectionAluno listBoxAlunos;
	private ListBox multiBoxPaisFiltrado;
	private ListBox multiBoxPaisAssociado;
	
	TextConstants txtConstants;
	
	public AssociarPaisAlunos(){
		
		txtConstants = GWT.create(TextConstants.class);
		
		mpDialogBoxConfirm.setTYPE_MESSAGE(MpDialogBox.TYPE_CONFIRMATION);
		mpDialogBoxWarning.setTYPE_MESSAGE(MpDialogBox.TYPE_WARNING);
		
		mpPanelAlunoLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelAlunoLoading.show();
		mpPanelAlunoLoading.setVisible(false);
		
		mpPanelPaisLoading.setTxtLoading(txtConstants.geralCarregando());
		mpPanelPaisLoading.show();
		mpPanelPaisLoading.setVisible(false);

		mpPanelAssociandoLoading.setTxtLoading("");
		mpPanelAssociandoLoading.show();
		mpPanelAssociandoLoading.setVisible(false);
		
		
		VerticalPanel vBodyPanel = new VerticalPanel();
		vBodyPanel.setWidth("100%");
		
		
		vBodyPanel.add(drawPassoUmSelecioneAluno());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
		vBodyPanel.add(drawPassoDoisSelecionePais());
		vBodyPanel.add(new InlineHTML("&nbsp;"));
//		vBodyPanel.add(drawPassoTresSubmeterAssociacao());		
		
		setWidth("100%");
		super.add(vBodyPanel);			
		
	}
		
	
	public MpPanelPageMainView drawPassoUmSelecioneAluno(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecioneAluno(), "images/elementary_school_16.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		Grid gridFiltrar = new Grid(1,5);		
		gridFiltrar.setCellSpacing(3);
		gridFiltrar.setCellPadding(3);
		gridFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarAluno = new Label(txtConstants.alunoNome());
		txtFiltroAlunos = new TextBox();		
		txtFiltroAlunos.addKeyUpHandler(new EnterKeyUpHandlerFiltrarAluno());
		MpImageButton btnFiltrarAluno = new MpImageButton(txtConstants.usuarioFiltrarListaAlunos(), "images/magnifier.png");
		btnFiltrarAluno.addClickHandler(new ClickHandlerFiltrarAluno());		
		
		lblFiltrarAluno.setStyleName("design_label");	
		lblFiltrarAluno.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		txtFiltroAlunos.setStyleName("design_text_boxes");		
		
		listBoxAlunos = new MpSelectionAluno();
		listBoxAlunos.setStyleName("design_text_boxes");
		listBoxAlunos.setWidth("350px");
		listBoxAlunos.addChangeHandler(new ChangeHandlerPopularPaisAssociados());		
		
		gridFiltrar.setWidget(0, 0, lblFiltrarAluno);
		gridFiltrar.setWidget(0, 1, listBoxAlunos);
//		gridFiltrar.setWidget(0, 2, txtFiltroAlunos);
//		gridFiltrar.setWidget(0, 3, btnFiltrarAluno);
		gridFiltrar.setWidget(0, 2, mpHelperAluno);
		gridFiltrar.setWidget(0, 3, mpPanelAlunoLoading);	
		
		mpPanel.add(gridFiltrar);
		
		return mpPanel;
		
	}
	
	
	@SuppressWarnings("deprecation")
    public MpPanelPageMainView drawPassoDoisSelecionePais(){
		
		MpPanelPageMainView mpPanel = new MpPanelPageMainView(txtConstants.usuarioSelecionaPais(), "images/people.png");
//		mpPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");
		mpPanel.setWidth("100%");
		
		FlexTable flexTableFiltrar = new FlexTable();		
		flexTableFiltrar.setCellSpacing(3);
		flexTableFiltrar.setCellPadding(3);
		flexTableFiltrar.setBorderWidth(0);		
		
		Label lblFiltrarPais = new Label(txtConstants.usuarioNomePais());
		txtFiltroPais = new TextBox();		
		txtFiltroPais.addKeyUpHandler(new EnterKeyUpHandlerFiltrarPais());
		MpImageButton btnFiltrar = new MpImageButton(txtConstants.geralFiltrar(), "images/magnifier.png");
		btnFiltrar.addClickHandler(new ClickHandlerFiltrarPais());		
		
		lblFiltrarPais.setStyleName("design_label");	
		lblFiltrarPais.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);		
		txtFiltroPais.setStyleName("design_text_boxes");		
		
		flexTableFiltrar.setWidget(0, 0, lblFiltrarPais);
		flexTableFiltrar.setWidget(0, 1, txtFiltroPais);
		flexTableFiltrar.setWidget(0, 2, btnFiltrar);
		flexTableFiltrar.setWidget(0, 3, mpPanelPaisLoading);
		
		
		FlexTable flexTableBotoes = new FlexTable();
		flexTableBotoes.setCellSpacing(3);
		flexTableBotoes.setCellPadding(3);
		flexTableBotoes.setBorderWidth(0);
		
		MpImageButton mpButtonParaEsquerda = new MpImageButton("", "images/resultset_previous.png");
		MpImageButton mpButtonParaDireita = new MpImageButton("", "images/resultset_next.png");
		
		mpButtonParaDireita.addClickHandler(new ClickHandlerPaisParaDireita());
		mpButtonParaEsquerda.addClickHandler(new ClickHandlerPaisParaEsquerda());		
		
		flexTableBotoes.setWidget(0, 0, mpButtonParaDireita);
		flexTableBotoes.setWidget(1, 0, mpButtonParaEsquerda);		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);		

		Label lblPaisAssociados = new Label(txtConstants.usuarioPaisAssociados());
		lblPaisAssociados.setStyleName("design_label");
		
		multiBoxPaisFiltrado = new ListBox(true);
	    multiBoxPaisFiltrado.setWidth("450px");
	    multiBoxPaisFiltrado.setHeight("130px");
	    multiBoxPaisFiltrado.setVisibleItemCount(10);	
	    multiBoxPaisFiltrado.setStyleName("design_text_boxes");
	    
		multiBoxPaisAssociado = new ListBox(true);
	    multiBoxPaisAssociado.setWidth("450px");
	    multiBoxPaisAssociado.setHeight("130px");
	    multiBoxPaisAssociado.setVisibleItemCount(10);	
	    multiBoxPaisAssociado.setStyleName("design_text_boxes");
	    
	    flexTable.setWidget(0, 0, flexTableFiltrar);
	    flexTable.setWidget(0, 1, new InlineHTML("&nbsp;"));
	    flexTable.setWidget(0, 2, lblPaisAssociados);
	    flexTable.setWidget(1, 0, multiBoxPaisFiltrado);
	    flexTable.setWidget(1, 1, flexTableBotoes);
	    flexTable.setWidget(1, 2, multiBoxPaisAssociado);	 
	    flexTable.setWidget(2,0,new InlineHTML("&nbsp;"));
	    flexTable.setWidget(3,0,drawPassoTresSubmeterAssociacao());
	    flexTable.getFlexCellFormatter().setColSpan(3, 0, 3);
	    flexTable.getFlexCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		
	    mpPanel.add(flexTable);
	    
		callbackGetPaisFiltro = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelPaisLoading.setVisible(false);				
				
				//Begin Cleaning fields
				multiBoxPaisFiltrado.clear();

				//End Cleaning fields
				
				for(int i=0;i<list.size();i++){
					Usuario usuario = list.get(i);
					multiBoxPaisFiltrado.addItem(usuario.getPrimeiroNome() +" "+usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
				}


			}
			
			public void onFailure(Throwable caught) {
				mpPanelPaisLoading.setVisible(false);	
				MpDialogBoxRefreshPage mpDialogBoxRefreshPage = new MpDialogBoxRefreshPage();
				mpDialogBoxRefreshPage.showDialog();
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroBuscarPais());
//				mpDialogBoxWarning.showDialog();

			}
		};
		
		
		callbackGetPaisAssociados = new AsyncCallback<ArrayList<Usuario>>() {

			public void onSuccess(ArrayList<Usuario> list) {
				MpUtilClient.isRefreshRequired(list);
				mpPanelAlunoLoading.setVisible(false);				
				
				//Begin Cleaning fields
				multiBoxPaisAssociado.clear();

				//End Cleaning fields
				
				for(int i=0;i<list.size();i++){
					Usuario usuario = list.get(i);
					multiBoxPaisAssociado.addItem(usuario.getPrimeiroNome() +" "+usuario.getSobreNome(), Integer.toString(usuario.getIdUsuario()));
				}

			}
			
			public void onFailure(Throwable caught) {
				mpPanelAlunoLoading.setVisible(false);
				MpDialogBoxRefreshPage mpDialogBoxRefreshPage = new MpDialogBoxRefreshPage();
				mpDialogBoxRefreshPage.showDialog();
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroBuscarPais());
//				mpDialogBoxWarning.showDialog();

			}
		};		
		
		return mpPanel;
	}
	
	
	public VerticalPanel drawPassoTresSubmeterAssociacao(){

		FlexTable flexTable = new FlexTable();	
		flexTable.setCellSpacing(3);
		flexTable.setCellPadding(3);
		flexTable.setBorderWidth(0);		

		
		VerticalPanel vPanel = new VerticalPanel();		
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setWidth("100");
//		vPanel.setWidth(Integer.toString(TelaInicialUsuario.intWidthTable)+"px");

		MpImageButton btnSubmeterAssociacao = new MpImageButton(txtConstants.usuarioAssociarPaisAluno(), "images/image002.png");
		btnSubmeterAssociacao.addClickHandler(new ClickHandlerSubmeterAssociarPaisAoAluno());		


		flexTable.setWidget(0, 0, btnSubmeterAssociacao);
		flexTable.setWidget(0, 1, mpPanelAssociandoLoading);
//		flexTable.setWidget(0, 2, new InlineHTML("&nbsp;"));

		vPanel.add(flexTable);
		vPanel.add(new InlineHTML("&nbsp;"));
		
		
//		// Callback para adicionar Curso.
//		callbackAssociarAlunoAosPais = new AsyncCallback<Boolean>() {
//
//			public void onFailure(Throwable caught) {
//				mpPanelAssociandoLoading.setVisible(false);
//				mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//				mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAssociarPaisAluno());
//				mpDialogBoxWarning.showDialog();
//			}
//
//			@Override
//			public void onSuccess(Boolean result) {
//				
//				mpPanelAssociandoLoading.setVisible(false);
//				if(result.booleanValue()){
//				mpDialogBoxConfirm.setTitle(txtConstants.geralConfirmacao());
//				mpDialogBoxConfirm.setBodyText(txtConstants.usuarioPaisAlunoAssociadosComSucesso());
//				mpDialogBoxConfirm.showDialog();
//				}
//				else
//				{
//					mpDialogBoxWarning.setTitle(txtConstants.geralAviso());
//					mpDialogBoxWarning.setBodyText(txtConstants.usuarioErroAssociarPaisAluno());
//					mpDialogBoxWarning.showDialog();
//					
//				}
//			}
//		};		
	
//		mpPanel.add(vPanel);
		
		return vPanel;
	}
	
//	private void popularOnLoadListBoxCursos(){
//		mpPanelAlunoLoading.setVisible(true);				
//		GWTServiceCurso.Util.getInstance().getCursos("%" + txtFiltroAlunos.getText() + "%", callbackGetAlunosFiltro);
//	}
	
	private void popularPaisAssociados(){
		if (listBoxAlunos.getSelectedIndex() == -1) {
			mpPanelAlunoLoading.setVisible(false);
		} else {
			mpPanelAlunoLoading.setVisible(true);
			int id_aluno = Integer.parseInt(listBoxAlunos.getValue(listBoxAlunos.getSelectedIndex()));
			GWTServiceUsuario.Util.getInstance().getTodosOsPaisDoAluno(id_aluno, callbackGetPaisAssociados);
		}
	}
	
	
	private void eventoFiltrarAluno(){
        multiBoxPaisFiltrado.clear();
        txtFiltroPais.setText("");
        listBoxAlunos.filterComboBox(txtFiltroAlunos.getText());
        popularPaisAssociados();
	}	
	
	private class ClickHandlerFiltrarAluno implements ClickHandler {
		public void onClick(ClickEvent event) {
		    eventoFiltrarAluno();
		}
	}
	
	private class EnterKeyUpHandlerFiltrarAluno implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			    eventoFiltrarAluno();
			}
		}
	}	
	
	private class ClickHandlerFiltrarPais implements ClickHandler {
		public void onClick(ClickEvent event) {
				mpPanelPaisLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PAIS, "%" + txtFiltroPais.getText() + "%", callbackGetPaisFiltro);
		}
	}	
	
	private class EnterKeyUpHandlerFiltrarPais implements KeyUpHandler{
		public void onKeyUp(KeyUpEvent event){
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				mpPanelPaisLoading.setVisible(true);				
				GWTServiceUsuario.Util.getInstance().getUsuariosPorTipoUsuario(TipoUsuario.PAIS, "%" +  txtFiltroPais.getText() + "%", callbackGetPaisFiltro);
			}
		}
	}	
	
	private class ClickHandlerPaisParaDireita implements ClickHandler {
		public void onClick(ClickEvent event) {

			int i=0;
			while(i<multiBoxPaisFiltrado.getItemCount()){			
			
				if(multiBoxPaisFiltrado.isItemSelected(i)){
					String value = multiBoxPaisFiltrado.getValue(multiBoxPaisFiltrado.getSelectedIndex());
					String item = multiBoxPaisFiltrado.getItemText(multiBoxPaisFiltrado.getSelectedIndex());
					if(!containsItem(multiBoxPaisAssociado, item)){
						multiBoxPaisAssociado.addItem(item, value);
					}
					multiBoxPaisFiltrado.removeItem(multiBoxPaisFiltrado.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}
	
	
	private class ClickHandlerPaisParaEsquerda implements ClickHandler {
		public void onClick(ClickEvent event) {			

			int i=0;
			while(i<multiBoxPaisAssociado.getItemCount()){			
				if (multiBoxPaisAssociado.isItemSelected(i)) {
					String value = multiBoxPaisAssociado.getValue(multiBoxPaisAssociado.getSelectedIndex());
					String item = multiBoxPaisAssociado.getItemText(multiBoxPaisAssociado.getSelectedIndex());
					if (!containsItem(multiBoxPaisFiltrado, item)) {
						multiBoxPaisFiltrado.addItem(item, value);
					}
					multiBoxPaisAssociado.removeItem(multiBoxPaisAssociado.getSelectedIndex());
					i=0;
					continue;
				}
				i++;
			}
		}
	}

	
	private class ChangeHandlerPopularPaisAssociados implements ChangeHandler{
		public void onChange(ChangeEvent event){
		    mpHelperAluno.populateSuggestBox(listBoxAlunos);
			popularPaisAssociados();
		}	
	}
	
	
	
	private class ClickHandlerSubmeterAssociarPaisAoAluno implements ClickHandler{
		
		public void onClick(ClickEvent event){
			
			mpPanelAssociandoLoading.setVisible(true);
			
			ArrayList<String> list_id_pais = new ArrayList<String>();
			for(int i=0;i<multiBoxPaisAssociado.getItemCount();i++){
				list_id_pais.add(multiBoxPaisAssociado.getValue(i));
			}
//			int id_aluno = Integer.parseInt(listBoxAlunos.getValue(listBoxAlunos.getSelectedIndex()));
//			GWTServiceUsuario.Util.getInstance().associarPaisAoAluno(id_aluno, list_id_pais, callbackAssociarAlunoAosPais);
			
		}
	}
	
	
	private boolean containsItem(ListBox listBox, String item){
		boolean contain = false;
		
		for(int i=0;i<listBox.getItemCount();i++){
			String strItem = listBox.getItemText(i);
			if(strItem.equals(item)){
				contain=true;
				break;
			}
		}			
		return contain;
	}
	
	public void updateClientData(){
		listBoxAlunos.populateComboBox();
	}
	
	

}

