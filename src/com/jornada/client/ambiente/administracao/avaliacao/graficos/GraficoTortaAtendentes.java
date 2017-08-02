package com.jornada.client.ambiente.administracao.avaliacao.graficos;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class GraficoTortaAtendentes {
    
    private static String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private static PieChart chartTortaComoConheceuRest;
    private static PieChart chartTortaRecomendariaRest;
    private static PieChart chartTortaVoltariaRest;

    public static void drawChart(FlexTable tableChart, ArrayList<ArrayList<String>> arrayList, int intMostrarItens) {

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                
                if (chartTortaComoConheceuRest == null) {
                    chartTortaComoConheceuRest = new PieChart();                   
                }
                if (chartTortaRecomendariaRest == null) {
                    chartTortaRecomendariaRest = new PieChart();                   
                }
                if (chartTortaVoltariaRest == null) {
                    chartTortaVoltariaRest = new PieChart();                   
                }
                
                FlexTable flexTableCharts = new FlexTable();
                flexTableCharts.setCellSpacing(2);
                flexTableCharts.setCellPadding(2);
                flexTableCharts.setBorderWidth(0);
                flexTableCharts.setWidget(0, 0, chartTortaComoConheceuRest);
                flexTableCharts.setWidget(0, 1, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(0, 2, chartTortaRecomendariaRest);
                flexTableCharts.setWidget(0, 3, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(0, 4, chartTortaVoltariaRest);
                flexTableCharts.setWidget(0, 5, new InlineHTML(strInLineSpace));                
                
                tableChart.clear();
                tableChart.setWidget(0, 0, flexTableCharts);
                
               
                ArrayList<String> list = new ArrayList<String>();
                
                //Preparing Como conheceu o Restaurante
                  MpPieChartOptions optionsComoConheceuRest = new MpPieChartOptions("Como conheceu o restauntante?");
                  list = arrayList.get(0);                
                  int intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens;  
                  
                  // Prepare the data
                  DataTable dataTable = DataTable.create();               

                  dataTable.addColumn(ColumnType.STRING, "Como conheceu o restauntante?");
                  dataTable.addColumn(ColumnType.NUMBER, "Número");
                  dataTable.addRows(intMostrarApenas);
                  for (int row = 0; row < intMostrarApenas; row++) {
                      String cell = list.get(row);

                      String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                      dataTable.setValue(row, 0, strData[0]);
                      dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                  }

                  chartTortaComoConheceuRest.draw(dataTable, optionsComoConheceuRest.getOptions());
                  chartTortaComoConheceuRest.onResize();
                  chartTortaComoConheceuRest.redraw();
                  
                  
                  //Preparing Recomendaria o restaurante
                  MpPieChartOptions optionsRecomendariaRest = new MpPieChartOptions("Recomendaria o restaurante?");
                  list = arrayList.get(1);
                  intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                  
                  String[] strColorsRecomendariaRest = {"gray", "gray", "gray", "gray"};
                  
                  dataTable = DataTable.create();             

                  dataTable.addColumn(ColumnType.STRING, "Recomendaria o restaurante?");
                  dataTable.addColumn(ColumnType.NUMBER, "Número");
                  dataTable.addRows(intMostrarApenas);
                  for (int row = 0; row < intMostrarApenas; row++) {
                      String cell = list.get(row);
                      String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                      
                      String strText = strData[0];
                      strColorsRecomendariaRest = getColors(strColorsRecomendariaRest, strText, row);

                      dataTable.setValue(row, 0, strText);
                      dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                  }
                  
                  optionsRecomendariaRest.getOptions().setColors(strColorsRecomendariaRest);

                  // Draw the chart
                  chartTortaRecomendariaRest.draw(dataTable, optionsRecomendariaRest.getOptions());
                  chartTortaRecomendariaRest.onResize();
                  chartTortaRecomendariaRest.redraw();
                  
                  
                  //Preparing Voltaria ao restaurante
                  MpPieChartOptions optionsVoltariaRest = new MpPieChartOptions("Voltaria ao restaurante?");
                  
                  list = arrayList.get(2);
                  intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                  
                  String[] strColorsVoltariaRest = {"gray", "gray", "gray", "gray"};
                  
                  // Prepare the data
                  dataTable = DataTable.create();             

                  dataTable.addColumn(ColumnType.STRING, "Voltaria ao restaurante?");
                  dataTable.addColumn(ColumnType.NUMBER, "Número");
                  dataTable.addRows(intMostrarApenas);
                  for (int row = 0; row < intMostrarApenas; row++) {
                      String cell = list.get(row);

                      String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                      
                      String strText = strData[0];
                      
                      strColorsVoltariaRest = getColors(strColorsVoltariaRest, strText, row);
                      
                      dataTable.setValue(row, 0, strText);
                      dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                  }

                  // Draw the chart
                  optionsVoltariaRest.getOptions().setColors(strColorsVoltariaRest);
                  chartTortaVoltariaRest.draw(dataTable, optionsVoltariaRest.getOptions());
                  chartTortaVoltariaRest.onResize();
                  chartTortaVoltariaRest.redraw();

            }
        });

    }
    
    
    public static String[] getColors(String[] strColors, String strText, int cv){
        if(strText.equals("Sim")){
            strColors[cv]="#4caf4e";
        }else if (strText.equals("Não")){
            strColors[cv]="#e53935";
        }else if(strText.equals("Talvez")){
            strColors[cv]="#fff9c4";
        }else{
            strColors[cv]="#ffab91";
        }
        return strColors;
    }

}
