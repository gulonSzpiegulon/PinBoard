package my.vaadin.app;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

public class DeletePinListButton extends Button {
	
	public DeletePinListButton(PinBoardLayout pinBoardLayout, PinBoard pinBoard, PinList pinList, Panel pinListPanel) {
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				pinBoard.deletePinList(pinList);	//deleting from database
				pinBoardLayout.setUpLayoutAccordingToDataBase(pinListPanel);	//refreshing the display 
			}
		});
		setWidth("40px");
		setHeight("40px");
		setCaption("X");
		setStyleName("DeletePinListButton");
	}
}
