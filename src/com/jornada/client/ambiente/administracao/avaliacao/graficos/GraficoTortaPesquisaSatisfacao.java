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
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class GraficoTortaPesquisaSatisfacao {
    
    private static String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private static PieChart chartTortaRestMoquem;
    private static PieChart chartTortaAmbiente;
    private static PieChart chartTortaAtendimento;
    private static PieChart chartTortaQualidade;
    private static PieChart chartTortaEspacoKids;
    private static PieChart chartTortaCozinha;

    public static void drawChart(FlexTable tableChart, ArrayList<ArrayList<String>> arrayList, int intMostrarItens) {

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                
                if (chartTortaRestMoquem == null) {
                    chartTortaRestMoquem = new PieChart();                   
                }
                if (chartTortaAmbiente == null) {
                    chartTortaAmbiente = new PieChart();                   
                }
                if (chartTortaAtendimento == null) {
                    chartTortaAtendimento = new PieChart();                   
                }
                if (chartTortaQualidade == null) {
                    chartTortaQualidade = new PieChart();                   
                }
                if (chartTortaEspacoKids == null) {
                    chartTortaEspacoKids = new PieChart();                   
                }
                if (chartTortaCozinha == null) {
                    chartTortaCozinha = new PieChart();                   
                }
                
                FlexTable flexTableCharts = new FlexTable();
                flexTableCharts.setCellSpacing(2);
                flexTableCharts.setCellPadding(2);
                flexTableCharts.setBorderWidth(0);
                flexTableCharts.setWidget(0, 0, chartTortaRestMoquem);
                flexTableCharts.setWidget(0, 1, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(0, 2, chartTortaAmbiente);
                flexTableCharts.setWidget(0, 3, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(0, 4, chartTortaAtendimento);
                flexTableCharts.setWidget(0, 5, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(1, 0, chartTortaQualidade);
                flexTableCharts.setWidget(1, 1, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(1, 2, chartTortaEspacoKids);
                flexTableCharts.setWidget(1, 3, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(1, 4, chartTortaCozinha);
                flexTableCharts.setWidget(1, 5, new InlineHTML(strInLineSpace));
                
                tableChart.clear();
                tableChart.setWidget(0, 0, flexTableCharts);
                

                ArrayList<String> list = new ArrayList<String>();
                
              //Preparing Restaurante Moquem
                MpPieChartOptions optionsRestMoquem = new MpPieChartOptions("Restaurante Moquem");
                list = arrayList.get(0);                
                int intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens;  
                
                String[] strColorsRestMoquem = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                DataTable dataTable = DataTable.create();               

                dataTable.addColumn(ColumnType.STRING, "Restaurante Moquem");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    
                    strColorsRestMoquem = getColors(strColorsRestMoquem, strData[0], row);
                    
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                optionsRestMoquem.getOptions().setColors(strColorsRestMoquem);
                chartTortaRestMoquem.draw(dataTable, optionsRestMoquem.getOptions());
                chartTortaRestMoquem.onResize();
                chartTortaRestMoquem.redraw();
                
                
                //Preparing Ambiente
                MpPieChartOptions optionsAmbiente = new MpPieChartOptions("Ambiente");
                list = arrayList.get(1);
                intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                
                String[] strColorsAmbiente = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                dataTable = DataTable.create();             

                dataTable.addColumn(ColumnType.STRING, "Ambiente");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    strColorsAmbiente = getColors(strColorsAmbiente, strData[0], row);
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                // Draw the chart
                optionsAmbiente.getOptions().setColors(strColorsAmbiente);
                chartTortaAmbiente.draw(dataTable, optionsAmbiente.getOptions());
                chartTortaAmbiente.onResize();
                chartTortaAmbiente.redraw();
                
                
                //Preparing Atendimento
                MpPieChartOptions optionsAtendimento = new MpPieChartOptions("Atendimento");
                
                list = arrayList.get(2);
                intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                
                String[] strColorsAtendimento = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                dataTable = DataTable.create();             

                dataTable.addColumn(ColumnType.STRING, "Atendimento");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    strColorsAtendimento = getColors(strColorsAtendimento, strData[0], row);
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                // Draw the chart
                optionsAtendimento.getOptions().setColors(strColorsAtendimento);
                chartTortaAtendimento.draw(dataTable, optionsAtendimento.getOptions());
                chartTortaAtendimento.onResize();
                chartTortaAtendimento.redraw();
                
                //Preparing Qualidade
                MpPieChartOptions optionsQualidade = new MpPieChartOptions("Qualidade");
                list = arrayList.get(3);
                intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                
                String[] strColorsQualidade = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                dataTable = DataTable.create();             

                dataTable.addColumn(ColumnType.STRING, "Qualidade");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    strColorsQualidade = getColors(strColorsQualidade, strData[0], row);
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                // Draw the chart
                optionsQualidade.getOptions().setColors(strColorsQualidade);
                chartTortaQualidade.draw(dataTable, optionsQualidade.getOptions());
                chartTortaQualidade.onResize();
                chartTortaQualidade.redraw();            
                
                //Preparing EspaçoKids
                MpPieChartOptions optionsEspacoKids = new MpPieChartOptions("Espaço Kids / Recreação");
                list = arrayList.get(4);
                intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                
                String[] strColorsEspacoKids = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                dataTable = DataTable.create();             

                dataTable.addColumn(ColumnType.STRING, "Espaço Kids / Recreação");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    strColorsEspacoKids = getColors(strColorsEspacoKids, strData[0], row);
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                // Draw the chart
                optionsEspacoKids.getOptions().setColors(strColorsEspacoKids);
                chartTortaEspacoKids.draw(dataTable, optionsEspacoKids.getOptions());
                chartTortaEspacoKids.onResize();
                chartTortaEspacoKids.redraw();     
                
                //Preparing Cozinha
                MpPieChartOptions optionsCozinha = new MpPieChartOptions("Cozinha");
                list = arrayList.get(5);
                intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens; 
                
                String[] strColorsCozinha = {"gray", "gray", "gray", "gray", "gray"};
                // Prepare the data
                dataTable = DataTable.create();             

                dataTable.addColumn(ColumnType.STRING, "Cozinha");
                dataTable.addColumn(ColumnType.NUMBER, "Número");
                dataTable.addRows(intMostrarApenas);
                for (int row = 0; row < intMostrarApenas; row++) {
                    String cell = list.get(row);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    strColorsCozinha = getColors(strColorsCozinha, strData[0], row);
                    dataTable.setValue(row, 0, strData[0]);
                    dataTable.setValue(row, 1, Double.parseDouble(strData[1]));
                }

                // Draw the chart
                optionsCozinha.getOptions().setColors(strColorsCozinha);
                chartTortaCozinha.draw(dataTable, optionsCozinha.getOptions());
                chartTortaCozinha.onResize();
                chartTortaCozinha.redraw();                      

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
