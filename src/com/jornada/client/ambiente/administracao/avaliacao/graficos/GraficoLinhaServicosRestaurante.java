package com.jornada.client.ambiente.administracao.avaliacao.graficos;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.jornada.client.ambiente.administracao.avaliacao.graficos.options.MpLineCharOption;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;
import com.jornada.shared.classes.pesquisasatisfacao.MediaServico;

public class GraficoLinhaServicosRestaurante {
    
    private static String strInLineSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private static LineChart  chartLineServicos;
    private static LineChart  chartLineGeral;
    
    

    public static void drawChart(FlexTable tableChart, ArrayList<ArrayList<MediaServico>> arrayList, String strEscala) {

        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {
            @Override
            public void run() {
                
                if (chartLineServicos == null) {
                    chartLineServicos = new LineChart(); 
                }
                if(chartLineGeral==null){
                    chartLineGeral = new LineChart();  
                }

                
                FlexTable flexTableCharts = new FlexTable();
                flexTableCharts.setCellSpacing(2);
                flexTableCharts.setCellPadding(2);
                flexTableCharts.setBorderWidth(0);
                flexTableCharts.setWidget(0, 0, chartLineServicos);
                flexTableCharts.setWidget(1, 0, new InlineHTML(strInLineSpace));
                flexTableCharts.setWidget(2, 0, chartLineGeral);

                
          
                
                tableChart.clear();
                tableChart.setWidget(0, 0, flexTableCharts);
                tableChart.setWidget(0, 1, new InlineHTML(strInLineSpace));     
                
               
                ArrayList<MediaServico> listMedia = new ArrayList<MediaServico>();
                listMedia.addAll(arrayList.get(0));

                // Prepare the data
                DataTable dataTable = DataTable.create();
                dataTable.addColumn(ColumnType.STRING, "Escala");

                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_REST_MOQUEM);
                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_AMBIENTE);
                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_ATENDIMENTO);
                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_QUALIDADE);
                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_COL_ESPACO_KIDS);
                dataTable.addColumn(ColumnType.NUMBER, Avaliacao.STR_COZINHA);

                
                dataTable.addRows(listMedia.size());
                for (int i = 0; i < listMedia.size(); i++) {
                    dataTable.setValue(i, 0, String.valueOf(listMedia.get(i).getStrData()));
                }
                
               
                for (int col = 0; col < arrayList.size(); col++) {
                    ArrayList<MediaServico> listRow = arrayList.get(col);
                    for (int row = 0; row < listRow.size(); row++) {
                        if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_REST_MOQUEM)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaRestMoquem());    
                        }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_AMBIENTE)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaAmbiente());
                        }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_ATENDIMENTO)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaAtendimento());
                        }else  if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_QUALIDADE)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaQualidade());
                        }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_ESPACO_KIDS)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaEspaçoKids());
                        }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_COZINHA)){
                            dataTable.setValue(row, col + 1, listRow.get(row).getMediaCozinha());
                        }
                    }
                }
                
                
                
                MpLineCharOption servOptions = new MpLineCharOption(strEscala);

                  
                  chartLineServicos.draw(dataTable, servOptions.getOptions());
                  chartLineServicos.onResize();
                  chartLineServicos.redraw();
                  
    
                  
                  DataTable dataTableGeral = DataTable.create();
                  dataTableGeral.addColumn(ColumnType.STRING, "Escala");
                  dataTableGeral.addColumn(ColumnType.NUMBER, "Média Geral");

                  double doubleMediaGeral=0;
                  int countGeral=0;   
                  
                  dataTableGeral.addRows(listMedia.size());
                  for (int i = 0; i < listMedia.size(); i++) {
                      dataTableGeral.setValue(i, 0, String.valueOf(listMedia.get(i).getStrData()));
                      
                      for (int col = 0; col < arrayList.size(); col++) {
                          ArrayList<MediaServico> listRow = arrayList.get(col);
                          for (int row = 0; row < listRow.size(); row++) {
                              
                              if(listMedia.get(i).getStrData().equals(listRow.get(row).getStrData())){
                                  countGeral++;
                                  if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_REST_MOQUEM)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaRestMoquem();
                                  }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_AMBIENTE)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaAmbiente();
                                  }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_ATENDIMENTO)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaAtendimento();
                                  }else  if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_QUALIDADE)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaQualidade();
                                  }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_ESPACO_KIDS)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaEspaçoKids();
                                  }else if(listRow.get(row).getStrServico().equals(Avaliacao.STR_COL_COZINHA)){
                                      doubleMediaGeral = doubleMediaGeral+listRow.get(row).getMediaCozinha();
                                  }                                                                    
                              }                              
                          }
                      }
                      
                      dataTableGeral.setValue(i, 1, (doubleMediaGeral/countGeral));
                      doubleMediaGeral=0;
                      countGeral=0;
                  }
                  
                  MpLineCharOption geralOptions = new MpLineCharOption(strEscala);
                  geralOptions.getOptions().setTitle("Evolução da Satisfação Geral do Restaurante (Escala de 1 a 4)");
                  
                  chartLineGeral.draw(dataTableGeral, geralOptions.getOptions());
                  chartLineGeral.onResize();
                  chartLineGeral.redraw();           

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
