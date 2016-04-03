package my.vaadin.app;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

public class DeletePinCardButton extends Button {
	public DeletePinCardButton(PinBoardLayout pinBoardLayout, PinList pinList, PinCard pinCard, Panel pinBoardPanel) {
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				pinList.deletePinList(pinCard);	//deleting from database
				pinBoardLayout.setUpLayoutAccordingToDataBase(pinBoardPanel);	//and refreshing the display
			}
		});
		setWidth("40px");
		setHeight("40px");
		setCaption("X");
		setStyleName("DeletePinCardButton");
	}
}
