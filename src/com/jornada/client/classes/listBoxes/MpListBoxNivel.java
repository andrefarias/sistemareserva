package com.jornada.client.classes.listBoxes;

import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;



public class MpListBoxNivel extends MpListBox {
    
    public MpListBoxNivel(){
        
        
        setStyleName("design_list_boxes_avaliacao");
        
        addItem("Selecionar uma Opção","Não Avaliou");
        addItem(Avaliacao.STR_NOTA_EXCELENTE,Avaliacao.STR_NOTA_EXCELENTE);
        addItem(Avaliacao.STR_NOTA_BOM,Avaliacao.STR_NOTA_BOM);
        addItem(Avaliacao.STR_NOTA_REGULAR,Avaliacao.STR_NOTA_REGULAR);
        addItem(Avaliacao.STR_NOTA_RUIM,Avaliacao.STR_NOTA_RUIM);
        
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(5);
        this.setSize("180px", "100px");
        
        
    }
    
}