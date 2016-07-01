package com.jornada.client.ambiente.administracao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class ComponenteReserva extends Composite {
	
	VerticalPanel vPanelMain = new VerticalPanel(); 
	VerticalPanel vPanel1 = new VerticalPanel(); 
	VerticalPanel vPanel2 = new VerticalPanel(); 
	MainView mainView;
	
	
	private static ComponenteReserva uniqueInstance;

	public static ComponenteReserva getInstance(MainView mainView) {
		if (uniqueInstance == null) {
			uniqueInstance = new ComponenteReserva(mainView);
		}
		return uniqueInstance;
	}	
	
	private ComponenteReserva(MainView mainView) {		
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		this.mainView=mainView;

		
		String strTitle = txtConstants.coordenadorAmbientePeriodo();
		String strImageAddress = "images/my_projects_folder_128.png";
		String strText = txtConstants.periodoCompText1();
		strText+= txtConstants.periodoCompText2();
		strText+= txtConstants.periodoCompText3();
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle, strImageAddress, strText);	
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
	
		initWidget(mainViewComponent);

	}
	

	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
//			mainView.openCadastroPeriodo();
			MainMenu.isFirstEventFire=true;
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_RESERVA);
			
			
		}
		
	}
	
	
	
	
	

	
	
	

}
