package my.vaadin.app;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;

public class DeletePinCardButton extends Button {
	public DeletePinCardButton(PinBoardLayout pinBoardLayout, PinList pinList, PinCard pinCard) {
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				pinList.deletePinList(pinCard);	//deleting from database
				pinBoardLayout.setUpLayoutAccordingToDataBase(false, null);	//and refreshing the display
			}
		});
		setHeight("30px");
		setCaption("x");
		addStyleName("DeletePinCardButton");
	}
}
