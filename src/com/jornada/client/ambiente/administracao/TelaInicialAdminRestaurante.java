package com.jornada.client.ambiente.administracao;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;

public class TelaInicialAdminRestaurante extends Composite {

	ComponenteAmbienteEscritorio componenteAmbienteEscritorio; 
	ComponenteAvaliacao componenteAvaliacao;
//	ComponenteUsuario componenteUsuarios;	
//	ComponenteComunicados componenteComunicados;
//	ComponenteOcorrencia componenteOcorrencia;
//	ComponenteVisualizarCurso componenteVisualizarCurso;
//	ComponenteDiarioCoordenador componenteDiarioCoordenador;
//	ComponentePresencaCoordenador componentePresencaCoordenador;
//	ComponenteRelatorios componenteRelatorios;


	VerticalPanel verticalPanel;
	HorizontalPanel horizontalPanel_1;
	HorizontalPanel horizontalPanel_2;
	HorizontalPanel horizontalPanel_3;	
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialAdminRestaurante uniqueInstance;
	public static TelaInicialAdminRestaurante getInstance(MainView mainView){		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialAdminRestaurante(mainView);
		}		
		return uniqueInstance;
	}	

	private TelaInicialAdminRestaurante(MainView mainView) {
		
		this.mainView = mainView;
		
		componenteAmbienteEscritorio = ComponenteAmbienteEscritorio.getInstance(mainView);
		componenteAvaliacao = ComponenteAvaliacao.getInstance(mainView);				
//		componenteComunicados = ComponenteComunicados.getInstance(mainView);
//		componenteOcorrencia = ComponenteOcorrencia.getInstance(mainView);
//		componenteVisualizarCurso = new ComponenteVisualizarCurso();
//		componenteDiarioCoordenador = ComponenteDiarioCoordenador.getInstance();
//		componentePresencaCoordenador = ComponentePresencaCoordenador.getInstance();
//		componenteRelatorios = ComponenteRelatorios.getInstance(mainView);

	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
		grid = new Grid(3, 3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
//		grid.setWidget(0, 0, componenteCurso);
//		grid.setWidget(0, 1, componentePeriodo);
//		grid.setWidget(0, 2, componenteDisciplina);
//		grid.setWidget(1, 0, componenteConteudoProgramatico);
//		grid.setWidget(1, 1, componenteTopico);
		grid.setWidget(0, 0, componenteAmbienteEscritorio);
		grid.setWidget(0, 1, componenteAvaliacao);
//		grid.setWidget(0, 2, componenteComunicados);
//		grid.setWidget(1, 0, componenteOcorrencia);
//		grid.setWidget(1, 1, componenteDiarioCoordenador);
//		grid.setWidget(1, 1, componentePresencaCoordenador);
//		grid.setWidget(1, 2, componenteRelatorios);
//		grid.setWidget(2, 0, componenteVisualizarCurso);
		
		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
