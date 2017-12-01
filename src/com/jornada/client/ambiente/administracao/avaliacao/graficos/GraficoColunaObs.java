package com.jornada.client.ambiente.administracao.avaliacao.graficos;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;

public class GraficoColunaObs {

    private static ColumnChart chartBarGrupoObs;

    public static void drawChart(FlexTable tableChart, ArrayList<String> list, int intMostrarItens) {

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                
                if (chartBarGrupoObs == null) {
                    chartBarGrupoObs = new ColumnChart();                   
                }
                tableChart.clear();
                tableChart.setWidget(0, 0, chartBarGrupoObs);
                
                chartBarGrupoObs.setVisible(true);

                // Prepare the data
                DataTable dataTable = DataTable.create();

                dataTable.addColumn(ColumnType.STRING, "Observações");
                dataTable.addRows(1);

                int intMostrarApenas = (intMostrarItens > list.size()) ? list.size() : intMostrarItens;

                for (int column = 0; column < intMostrarApenas; column++) {
                    String cell = list.get(column);

                    String[] strData = cell.split(Avaliacao.SEPARATE_DATA);
                    dataTable.addColumn(ColumnType.NUMBER, strData[0]);
                    dataTable.setValue(0, column + 1, strData[1]);
                }

                // Set options
                ColumnChartOptions options = ColumnChartOptions.create();
                options.setFontName("Tahoma");
                options.setTitle("Mesas x Cidades");

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
                chartBarGrupoObs.draw(dataTable, options);

                chartBarGrupoObs.onResize();
                chartBarGrupoObs.redraw();

            }
        });

    }

}
