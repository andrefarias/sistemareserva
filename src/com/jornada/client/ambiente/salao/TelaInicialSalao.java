package com.jornada.client.ambiente.salao;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jornada.client.MainView;


public class TelaInicialSalao extends Composite {

	ComponenteSalaoReserva compSalaoReserva;
//	ComponenteNotas compNotas;
//	ComponenteTopico compTopico;
//	ComponenteComunicadoProfessor compComunicadoProfessor;
//	ComponenteOcorrencia compOcorrencia;
//	ComponenteVisualizarCursoProfessor compVisualizarCursoProfessor;
//	ComponenteDiarioProfessor compDiarioProfessor;
//	ComponentePresencaProfessor compPresenca;
//	ComponenteRelatoriosProfessor compRelatoriosProfessor;
//	ComponenteConteudoProgramaticoProfessor compConteudoProgramaticoProfessor;
	
	VerticalPanel verticalPanel;
	
	Grid grid;
	
	MainView mainView;
	
	
	private static TelaInicialSalao uniqueInstance;
	public static TelaInicialSalao getInstance(MainView mainView){
		
		if(uniqueInstance==null){
			uniqueInstance = new TelaInicialSalao(mainView);
		}
		
		return uniqueInstance;
	}	

	private TelaInicialSalao(MainView mainView) {
		
		this.mainView = mainView;
		
		compSalaoReserva = ComponenteSalaoReserva.getInstance(mainView);
//		compNotas = ComponenteNotas.getInstance(mainView);
//		compTopico = ComponenteTopico.getInstance(mainView);
//		compComunicadoProfessor = ComponenteComunicadoProfessor.getInstance();
//		compOcorrencia = ComponenteOcorrencia.getInstance(mainView);
//		compVisualizarCursoProfessor = new ComponenteVisualizarCursoProfessor();
//		compDiarioProfessor = ComponenteDiarioProfessor.getInstance();
//		compPresenca = ComponentePresencaProfessor.getInstance();
//		compRelatoriosProfessor = ComponenteRelatoriosProfessor.getInstance(mainView);
//		compConteudoProgramaticoProfessor = ComponenteConteudoProgramaticoProfessor.getInstance(mainView);
	
		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");

		
		grid = new Grid(3,3);
		grid.setCellSpacing(3);
		grid.setCellPadding(3);
		int row=0;
//		grid.setWidget(row, 0, compComunicadoProfessor); 
//		grid.setWidget(row, 1, compPresenca);
//		grid.setWidget(row, 1, compDiarioProfessor);
//		grid.setWidget(row++, 2, compOcorrencia);
		grid.setWidget(row, 0, compSalaoReserva);
//		grid.setWidget(row, 1, compNotas);
//		grid.setWidget(row++, 2, compRelatoriosProfessor);
//		grid.setWidget(row, 0, compConteudoProgramaticoProfessor);
//		grid.setWidget(row, 1, compTopico);
//		grid.setWidget(row++, 2, compVisualizarCursoProfessor);
		
		

		
		verticalPanel.add(grid);
//		verticalPanel.add(grid);
		
		initWidget(verticalPanel);
		
		
	}

}
