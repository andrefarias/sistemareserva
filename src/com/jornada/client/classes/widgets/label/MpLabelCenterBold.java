package com.jornada.client.classes.widgets.label;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class MpLabelCenterBold extends MpLabel{
	
	public MpLabelCenterBold(String text){
		setText(text);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setStyleName("label_comum_bold_12px");
	}

}
