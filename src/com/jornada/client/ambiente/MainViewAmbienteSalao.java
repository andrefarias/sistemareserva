package com.jornada.client.ambiente;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainView;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;

public class MainViewAmbienteSalao extends Composite {
    
    private static MainViewAmbienteSalao uniqueInstance;
    
    MainView mainView;
    
    public static MainViewAmbienteSalao getInstance(MainView mainView){       
        if(uniqueInstance==null){
            uniqueInstance = new MainViewAmbienteSalao(mainView);
        }       
        return uniqueInstance;
    }

	private MainViewAmbienteSalao(MainView mainView){
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		String strTitle = txtConstants.SalaoAmbiente();
		
		String strImageAddress = "images/professor_128.png";
		
		String strText = "Administração do Salão: \n";
		strText+="- Visualizar Reservas \n";
		strText+="- Controlar Extras \n";
		
		MainViewComponent mainViewComponent = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComponent.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComponent);			
		
	}	
	
	
	private class addClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_SALAO);			
		}		
	}	

}
