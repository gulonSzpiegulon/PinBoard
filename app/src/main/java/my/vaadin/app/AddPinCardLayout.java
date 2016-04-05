package my.vaadin.app;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class AddPinCardLayout extends VerticalLayout {	//this class together with addPinListLayout could be the one class but so much names have to be changed 
	//in the addPinListLayout (as it was created earlier) that I resigned and made 2 separate classes but in future if case that this application is to be developed, 
	//it would be wise to make single class out of them - the name should be sth like "addPinComponent"
	//however css style names should probably different in these two cases so maybe it is good that we have 2 separate classes...
	Button clickToAddPinCard = new Button("Add a card...");
	TextArea typeNameOfNewPinCard = new TextArea();
	HorizontalLayout saveOrDeclineButtons = new HorizontalLayout();
	Button savePinCard = new Button("Save");
	Button declinePinCard = new Button("X");
	
	AddPinCardLayout(PinBoardLayout pinBoardLayout, PinList pinList, Panel pinListPanel) {
		addComponent(clickToAddPinCard);
		setSizeUndefined();
		addStyleName("AddPinCardLayout");
		
		clickToAddPinCard.setWidth("200px");
		clickToAddPinCard.setHeight("40px");
		clickToAddPinCard.addStyleName("AddPinCardLayout-clickToAddPinCard");
		clickToAddPinCard.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				removeComponent(clickToAddPinCard);
				addComponents(typeNameOfNewPinCard, saveOrDeclineButtons);
				pinListPanel.setScrollTop(10000);
				typeNameOfNewPinCard.focus();
			}
		});
		
		typeNameOfNewPinCard.setValue("");
		typeNameOfNewPinCard.setWidth("320px");
		typeNameOfNewPinCard.setHeight("160px");
		typeNameOfNewPinCard.addStyleName("AddPinCardLayout-typeNameOfNewPinCard");
		typeNameOfNewPinCard.addFocusListener(new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				typeNameOfNewPinCard.setValue("");	
			}
		});
		
		saveOrDeclineButtons.setSizeUndefined();
		saveOrDeclineButtons.setStyleName("AddPinCardLayout-saveOrDeclineButtons");
		saveOrDeclineButtons.addComponents(savePinCard, declinePinCard);
		
		savePinCard.setWidth("80px");
		savePinCard.setHeight("40px");
		savePinCard.addStyleName("AddPinCardLayout-savePinCard");
		savePinCard.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String typedNameForCard = typeNameOfNewPinCard.getValue();
				if (!typedNameForCard.trim().equals("") && !typedNameForCard.contains(" | ")) {
					pinList.addPinCard(typedNameForCard);	
					pinBoardLayout.setUpLayoutAccordingToDataBase(false, pinList);	
				} else {
					typeNameOfNewPinCard.focus();
				}
			}
		});
		
		declinePinCard.setWidth("40px");
		declinePinCard.setHeight("40px");
		declinePinCard.addStyleName("AddPinCardLayout-declinePinCard");
		declinePinCard.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				typeNameOfNewPinCard.setValue("");	
				removeAllComponents();	
				addComponent(clickToAddPinCard);
			}
		});
		
	}
}

