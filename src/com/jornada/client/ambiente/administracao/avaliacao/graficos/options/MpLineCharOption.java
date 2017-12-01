package com.jornada.client.ambiente.administracao.avaliacao.graficos.options;

import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class MpLineCharOption {
    
    private LineChartOptions options;
    
    public MpLineCharOption(String strEscala){
        
        options = LineChartOptions.create();
        VAxis vAxis = VAxis.create("Média");
        vAxis.setMinValue(3);
        vAxis.setMaxValue(3);
        HAxis hAxis = HAxis.create(strEscala);
        
        options.setFontName("Tahoma");
        options.setTitle("Evolução da Satisfação do Restaurante por Serviço (Escala de 1 a 4)");
        options.setHAxis(hAxis);
        options.setVAxis(vAxis);
        options.setWidth(1200);
        options.setHeight(300);
        options.setEnableInteractivity(true);
        options.setPointSize(5);

    }

    public LineChartOptions getOptions() {
        return options;
    }


    
    

}
