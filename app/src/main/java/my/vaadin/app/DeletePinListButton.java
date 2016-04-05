package my.vaadin.app;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;

public class DeletePinListButton extends Button {
	
	public DeletePinListButton(PinBoardLayout pinBoardLayout, PinBoard pinBoard, PinList pinList) {
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				pinBoard.deletePinList(pinList);	//deleting from database
				pinBoardLayout.setUpLayoutAccordingToDataBase(false, null);	//refreshing the display 
			}
		});
		setHeight("30px");
		setCaption("x");
		addStyleName("DeletePinListButton");
	}
}
