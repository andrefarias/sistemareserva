package com.jornada.client.ambiente.administracao.avaliacao.graficos;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.options.MpPieChartOptions;
import com.jornada.client.classes.widgets.label.MpLabelCenter;
import com.jornada.client.classes.widgets.label.MpLabelCenterBold;
import com.jornada.client.classes.widgets.label.MpLabelLeftAvaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class GraficoTortaAtendentes {
    
    private static String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private static PieChart chartTortaAtendentes;
    

    public static void drawChart(FlexTable tableChart, ArrayList<String> arrayList, int intMostrarItens) {

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                
                if (chartTortaAtendentes == null) {
                    chartTortaAtendentes = new PieChart();  
                }

                
                FlexTable tabelaDados = new FlexTable();
                tabelaDados.setCellSpacing(2);
                tabelaDados.setCellPadding(2);
                tabelaDados.setBorderWidth(0);
                tabelaDados.setWidget(0, 0, new MpLabelCenterBold("Satisfação"));
                tabelaDados.setWidget(0, 1, new MpLabelCenterBold("Quant."));
                
                FlexTable flexTableCharts = new FlexTable();
                flexTableCharts.setCellSpacing(2);
                flexTableCharts.setCellPadding(2);
                flexTableCharts.setBorderWidth(0);
                flexTableCharts.setWidget(0, 0, chartTortaAtendentes);
                flexTableCharts.setWidget(0, 1, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(0, 2, tabelaDados);
          
                
                tableChart.clear();
                tableChart.setWidget(0, 0, flexTableCharts);
                tableChart.setWidget(0, 1, new InlineHTML(strInLineSpace));     
                
               
                ArrayList<String> list = new ArrayList<String>();
                
                //Preparing Atendentes
                  MpPieChartOptions optionsAtendentes = new MpPieChartOptions("Como foi o Atendimento?");
                  list = arrayList;           
                  int intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens;  
                  
                  String[] strColorsAtendentes = {"gray", "gray", "gray", "gray"};
                  
                  // Prepare the data
                  DataTable dataTable = DataTable.create();               

                  dataTable.addColumn(ColumnType.STRING, "Como foi o Atendimento?");
                  dataTable.addColumn(ColumnType.NUMBER, "Número");
                  dataTable.addRows(intMostrarApenas);
                  for (int row = 0; row < intMostrarApenas; row++) {
                      String cell = list.get(row);

                      String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                      strColorsAtendentes = getColors(strColorsAtendentes, strData[0], row);
                      dataTable.setValue(row, 0, strData[0]);
                      dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                      tabelaDados.setWidget(row+1, 0, new MpLabelLeftAvaliacao(strData[0]));
                      tabelaDados.setWidget(row+1, 1, new MpLabelCenter(strData[1]));

                  }
                  optionsAtendentes.getOptions().setColors(strColorsAtendentes);
                  
                  chartTortaAtendentes.draw(dataTable, optionsAtendentes.getOptions());
                  chartTortaAtendentes.onResize();
                  chartTortaAtendentes.redraw();                 

            }
        });

    }
    
    
    public static String[] getColors(String[] strColors, String strText, int cv){
        if(strText.equals(Avaliacao.STR_NOTA_EXCELENTE)){
            strColors[cv]="#4caf4e";
        }else if (strText.equals(Avaliacao.STR_NOTA_BOM)){
            strColors[cv]="#6ec870";
        }else if(strText.equals(Avaliacao.STR_NOTA_REGULAR)){
            strColors[cv]="#fff9c4";
        }else if(strText.equals(Avaliacao.STR_NOTA_RUIM)){
            strColors[cv]="#e53935";
        }else{
            strColors[cv]="lightGray";
        }
        return strColors;
    }

}
