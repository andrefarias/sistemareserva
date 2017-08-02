package com.jornada.client.classes.listBoxes;



public class MpListBoxNivel extends MpListBox {
    
    public MpListBoxNivel(){
        
        
        setStyleName("design_list_boxes_avaliacao");
        
        addItem("Selecionar uma Opção","Não Avaliou");
        addItem("Excelente","Excelente");
        addItem("Bom","Bom");
        addItem("Regular","Regular");
        addItem("Ruim","Ruim");
        
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(5);
        this.setSize("180px", "100px");
        
        
    }
    
}