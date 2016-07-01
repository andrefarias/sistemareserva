package com.jornada.client.ambiente.administracao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.jornada.client.MainMenu;
import com.jornada.client.MainViewComponent;
import com.jornada.client.content.i18n.TextConstants;


public class ComponenteVisualizarCurso extends Composite{

	
	public ComponenteVisualizarCurso(){
		
		TextConstants txtConstants = GWT.create(TextConstants.class);
		
		String strTitle = txtConstants.SalaoAmbienteHierarquia();
		
		String strImageAddress = "images/catalog-icon-128.png";
		
		String strText = txtConstants.SalaoAmbienteHierarquiaCompText1();
		strText+=txtConstants.SalaoAmbienteHierarquiaCompText2();

		
		MainViewComponent mainViewComp = new MainViewComponent(strTitle,strImageAddress,strText);
		mainViewComp.flexTable.addClickHandler(new addClickHandler());		
		
		initWidget(mainViewComp);	
		
		
	}
	
	private class addClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			History.newItem(MainMenu.MENU_TOKEN_FERRAMENTA_COORDENADOR_HIERARQUIA);
			
		}
		
	}	


}
