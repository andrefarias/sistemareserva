package com.jornada.client.ambiente.administracao.avaliacao.graficos;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;

public class GraficoLinhaCidade {
    
    private static LineChart  chartLineCidades;
    
    
    public static void drawChart(FlexTable tableChart, ArrayList<ArrayList<String>> list, int intMostrarItens) {       

      
      ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
      chartLoader.loadApi(new Runnable() {
          @Override
          public void run() {
              if (chartLineCidades == null) {    
                  chartLineCidades = new LineChart();
                  tableChart.setWidget(0, 0, chartLineCidades);
              }
                  chartLineCidades.setVisible(true);

                  // Prepare the data
                  DataTable dataTable = DataTable.create();

                  dataTable.addColumn(ColumnType.STRING, "Cidades");
                  dataTable.addRows(1);
                  
                   
                  int intMostrarApenas = (intMostrarItens>list.size())?list.size():intMostrarItens;
                  
                  for (int row = 0; row < intMostrarApenas; row++) {
//                      ArrayList<String> cell = list.get(row);

                      
//                      dataTable.addColumn(ColumnType.NUMBER, strData[0]);
                  }

                  // Set options
                  LineChartOptions options = LineChartOptions.create();
                  options.setFontName("Tahoma");
//                  options.setTitle("Mesas x Cidades");

                  options.setHAxis(HAxis.create("Cidades"));

                  VAxis vaxis = VAxis.create("#Mesas");
                  vaxis.setMaxValue(10.0);
                  vaxis.setMinValue(0.0);
                  options.setVAxis(vaxis);

                  VAxis.create().setMaxValue(10.0);
                  VAxis.create().setMaxValue(10.0);

                  Animation animation = Animation.create();
                  animation.setDuration(500);
                  animation.setEasing(AnimationEasing.OUT);
                  Legend legend = Legend.create(LegendPosition.RIGHT);
                  // legend.setPosition(LegendPosition.);

                  options.setAnimation(animation);
                  options.setLegend(legend);
                  options.setWidth(600);
                  options.setHeight(350);

                  // Draw the chart
                  chartLineCidades.draw(dataTable, options);

                  chartLineCidades.onResize();
                  chartLineCidades.redraw();
                  
              
          }
      });

    

  }

}
