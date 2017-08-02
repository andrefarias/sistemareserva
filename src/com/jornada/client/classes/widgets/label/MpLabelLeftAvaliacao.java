package com.jornada.client.classes.widgets.label;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class MpLabelLeftAvaliacao extends MpLabel{
	
	public MpLabelLeftAvaliacao(String text){
		setText(text);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);	
		
		setStyleName("design_label_avaliacao");
	}

}
