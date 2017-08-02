package com.jornada.client;

//import com.allen_sauer.gwt.log.client.Log;

//import java.util.logging.Level;
//import java.util.logging.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.ambiente.administracao.TelaInicialAdminEscritorio;
import com.jornada.client.ambiente.administracao.TelaInicialAdminRestaurante;
import com.jornada.client.ambiente.administracao.avaliacao.TelaInicialAvaliacaoEscritorio;
import com.jornada.client.ambiente.administracao.reserva.TelaInicialReservaEscritorio;
import com.jornada.client.ambiente.salao.TelaInicialSalao;
import com.jornada.client.ambiente.salao.reserva.TelaInicialReservaSalao;
import com.jornada.client.classes.widgets.popup.MpPopupLoading;
import com.jornada.client.content.i18n.TextConstants;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;



@SuppressWarnings("deprecation")
public class MainView extends Composite implements HistoryListener{
	
	private MainTitle mainTitle;
	private MainMenu mainMenu;
	private MainBody mainBody;
	private MainFooter mainFooter = new MainFooter();		

    VerticalPanel vPanelTitle = new VerticalPanel();
	VerticalPanel vPanelMenu = new VerticalPanel();
	VerticalPanel vPanelBody = new VerticalPanel();
	VerticalPanel vPanelFooter = new VerticalPanel();
	
	VerticalPanel vPanelPrincipal = new VerticalPanel();
	
	private Usuario usuarioLogado;
	
	TextConstants txtConstants = GWT.create(TextConstants.class);
	
	MpPopupLoading mpPopupLoading = new MpPopupLoading(txtConstants.geralCarregando(), "");

	public MainView(Usuario usuarioLogado) {	
		
		this.usuarioLogado = usuarioLogado;

				
		mainMenu = new MainMenu(this);
		mainTitle = MainTitle.getInstance(this);
		mainBody = MainBody.getInstance(this);
		
        HorizontalPanel vPanelBlankTop = new HorizontalPanel();
//        vPanelBlankTop.setBorderWidth(2);
        vPanelBlankTop.setSize("15px", "40px");
	
		vPanelTitle.add(mainTitle);		
		vPanelMenu.add(mainMenu);		
		vPanelBody.add(mainBody);
		vPanelBody.add(new InlineHTML("HTML"));
		vPanelFooter.add(mainFooter);
		
		vPanelPrincipal.add(vPanelTitle);
		vPanelPrincipal.add(vPanelMenu);
		vPanelPrincipal.add(vPanelBody);
		vPanelPrincipal.add(vPanelBlankTop);
		vPanelPrincipal.add(vPanelFooter);
		
		vPanelTitle.setSize("100%", "100%");
		vPanelBody.setSize("100%", "100%");
		vPanelFooter.setSize("100%", "100%");
		vPanelPrincipal.setSize("99%", "100%");
		
		
	    Log.debug("This is a 'DEBUG' test message");
	    Log.info("This is a 'INFO' test message");
	    Log.warn("This is a 'WARN' test message");
	    Log.error("This is a 'ERROR' test message");
	    Log.fatal("This is a 'FATAL' test message");
//
//	    Log.fatal("This is what an exception might look like", new RuntimeException("2 + 2 = 5"));
//
//	    Log.debug("foo.bar.baz", "Using logging categories", (Exception) null);
		
//        Logger logger = Logger.getLogger("NameOfYourLogger");
//        logger.log(Level.SEVERE, "this message should get logged");
//        Log.
		

		openMainView();
		
		//new ElementFader().fade(vPanelBody.getElement(), 0, 1, 1500);
		
		vPanelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);		
		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelFooter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanelPrincipal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);	
		
//		Label labelTest = new Label("Testing");
//		vPanelPrincipal.add(labelTest);
		
		vPanelPrincipal.setSpacing(2);	
		
		initWidget(vPanelPrincipal);		
		
	}
	
//	public void displayLoginPage(){		
//		this.vPanelPrincipal.clear();
//		mainEntryPoint.displayLoginPage();
//	}
	
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}


	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}



	public void initHistorySupport() {
        // add the MainPanel as a history listener
        History.addHistoryListener(this);
        
        String INIT_STATE = Window.Location.getParameter("page");

        // check to see if there are any tokens passed at startup via the browser's URI
        String token = History.getToken();
        if (token.length() == 0) {
            if ((INIT_STATE != null) && (!INIT_STATE.isEmpty())) {
                onHistoryChanged(INIT_STATE);
            } else {
            	
                onHistoryChanged("initstate");
            }
        } else {
            onHistoryChanged(token);
        }
    }   
	
	
	public void checkUserPermission(){
		
		String strUrl = History.getToken();
		if(strUrl.equals(MainMenu.MENU_TOKEN_SAIR)){
			History.newItem(MainMenu.MENU_TOKEN_PRINCIPAL);	
		}
//		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ALUNO){
//			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO)){
//				History.newItem(strUrl);
//			}else{ 
//				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_ALUNO);	
//			}					
//		}
		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.SALAO){
			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_SALAO)){
				History.newItem(strUrl);
			}else{ 
				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_SALAO);	
			}	
		}
//		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.PAIS){
//			if(strUrl.startsWith(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS)){
//				History.newItem(strUrl);
//			}else{ 
//				History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_PAIS);	
//			}	
//		}		
		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.COORDENADOR){
		}

		else if(usuarioLogado.getIdTipoUsuario()==TipoUsuario.ADMINISTRADOR){			
		}
		else{
			History.newItem(MainMenu.MENU_TOKEN_PRINCIPAL);
		}		
	}

    @Override
    public void onHistoryChanged(String historyToken) {
        mainMenu.changePage(historyToken);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }	
    


	public void openMainView(){

	
		this.vPanelBody.clear();
		
		checkUserPermission();
		

		vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		mainBody = MainBody.getInstance(this);	

		this.vPanelBody.add(mainBody);
		this.vPanelMenu.setVisible(true);
	}	
	
    public void openAdminRestaurante(String strToken) {

        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (TipoUsuario.isPermissionEscritorio(idTipoUser)){
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();
                    TelaInicialAdminRestaurante telaInicialAdminEscola = TelaInicialAdminRestaurante.getInstance(MainView.this);
                    vPanelBody.add(telaInicialAdminEscola);
                    vPanelMenu.setVisible(true);
                }
            });
        }
    }
	
    public void openAdminRestauranteEscritorio(String strToken) {

        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (TipoUsuario.isPermissionEscritorio(idTipoUser)){
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_CURSO_ADMIN);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();

                    TelaInicialAdminEscritorio telaInicialAdminEscolaCurso = TelaInicialAdminEscritorio.getInstance(MainView.this);

                    vPanelBody.add(telaInicialAdminEscolaCurso);
                    vPanelMenu.setVisible(true);
                }
            });

        }
    }
	

		
    public void openReservaEscritorio(String strToken) {
        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (TipoUsuario.isPermissionEscritorio(idTipoUser)){
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_PERIODO);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();
                    vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                    TelaInicialReservaEscritorio telaInicialPeriodo = TelaInicialReservaEscritorio.getInstance();
                    vPanelBody.add(telaInicialPeriodo);
                    vPanelMenu.setVisible(true);
                }
            });
        }
    }
	
	
	
	
    public void openFerramentaSalao(String strToken) {
        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (TipoUsuario.isPermissionSalao(idTipoUser)){
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_Salao);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();
                    vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

                    TelaInicialSalao telaInicialSalao = TelaInicialSalao.getInstance(MainView.this);
                    vPanelBody.add(telaInicialSalao);
                    vPanelMenu.setVisible(true);
                }
            });
        }
    }
	
    public void openFerramentaSalaoReserva(String strToken) {
        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (idTipoUser == TipoUsuario.SALAO || idTipoUser == TipoUsuario.ADMINISTRADOR || idTipoUser == TipoUsuario.COORDENADOR) {
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_Salao_AVALIACAO);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();
                    vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                    TelaInicialReservaSalao telaInicialAvaliacao = TelaInicialReservaSalao.getInstance();
                    vPanelBody.add(telaInicialAvaliacao);
                    vPanelMenu.setVisible(true);
                }
            });
        }
    }


	
    public void openAvaliacaoDeClientes(String strToken) {
        int idTipoUser = getUsuarioLogado().getIdTipoUsuario();
        if (idTipoUser == TipoUsuario.SALAO || idTipoUser == TipoUsuario.ADMINISTRADOR || idTipoUser == TipoUsuario.COORDENADOR) {
            // History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_Salao_AVALIACAO);
            History.newItem(strToken);

            mpPopupLoading.show();
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    mpPopupLoading.hide();
                    Window.alert("Code download failed");
                }

                public void onSuccess() {
                    mpPopupLoading.hide();
                    vPanelBody.clear();
                    vPanelBody.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                    TelaInicialAvaliacaoEscritorio telaInicialAvaliacaoEscritorio = TelaInicialAvaliacaoEscritorio.getInstance();
                    vPanelBody.add(telaInicialAvaliacaoEscritorio);
                    vPanelMenu.setVisible(true);
                }
            });
        }
    }
	

	
	public MainMenu getMainMenu() {
		return mainMenu;
	}


	

	
}

