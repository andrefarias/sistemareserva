package com.jornada.client.ambiente.administracao.avaliacao.graficos.options;

//import com.google.gwt.visualization.client.visualizations.corechart.TextStyle;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendAlignment;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.TextStyle;

public class MpPieChartOptions {
    
    private PieChartOptions options;
    
    public MpPieChartOptions(String strLegend){
        // Set options
        options = PieChartOptions.create();  
        String[] data = {"#4caf4e", "#6ec870", "#fff9c4", "#ffab91","#e53935"};
//        String[] data = {"#4caf4e", "#add581", "#fff9c4", "#ffab91","#e53935"};
        options.setColors(data);
        options.setFontName("Tahoma");
        options.setIs3D(false);
        Animation animation = Animation.create();
        animation.setDuration(500);
        animation.setEasing(AnimationEasing.OUT);
        Legend legend = Legend.create(LegendPosition.RIGHT);
        legend.setAligment(LegendAlignment.START);
        TextStyle textStyle = TextStyle.create();
        textStyle.setColor("#000");

        legend.setTextStyle(textStyle);
//        legend.setPosition(LegendPosition.BOTTOM);
        options.setPieSliceTextStyle(textStyle);
        options.setIs3D(false);
        options.setLegend(legend);
        options.setWidth(380);
        options.setHeight(240);
        options.setTitle(strLegend);
    }

    public PieChartOptions getOptions() {
        return options;
    }
    
    
    
    


}
