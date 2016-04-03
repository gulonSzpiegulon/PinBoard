package my.vaadin.app;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class AddPinCardLayout extends VerticalLayout {	//this class together with addPinListLayout could be the one class but so much names have to be changed 
	//in the addPinListLayout (as it was created earlier) that I resigned and made 2 separate classes but in future if case that this application is to be developed, 
	//it would be wise to make single class out of them - the name should be sth like "addPinComponent"
	//however css style names should probably different in these two cases so maybe it is good that we have 2 separate classes...
	Button clickToAddPinCard = new Button("Add a card...");
	TextField typeNameOfNewPinCard = new TextField();
	Label pinCardNameWrongFormatWarning = new Label();
	HorizontalLayout saveOrDeclineButtons = new HorizontalLayout();
	Button savePinCard = new Button("Save");
	Button declinePinCard = new Button("X");
	
	AddPinCardLayout(PinBoardLayout pinBoardLayout, PinList pinList, Panel pinBoardPanel) {
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
				addComponents(typeNameOfNewPinCard, pinCardNameWrongFormatWarning, saveOrDeclineButtons);
			}
		});
		
		typeNameOfNewPinCard.setValue("Add a card...");
		typeNameOfNewPinCard.setWidth("200px");
		typeNameOfNewPinCard.setHeight("40px");
		typeNameOfNewPinCard.addStyleName("AddPinCardLayout-typeNameOfNewPinCard");
		typeNameOfNewPinCard.addFocusListener(new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				typeNameOfNewPinCard.setValue("");	
			}
		});
		
		pinCardNameWrongFormatWarning.setWidth("200px");
		pinCardNameWrongFormatWarning.setHeight("40px");
		pinCardNameWrongFormatWarning.addStyleName("AddPinCardLayout-pinCardNameWrongFormatWarning");
		
		saveOrDeclineButtons.setWidth("200px");
		saveOrDeclineButtons.setHeight("0%");
		saveOrDeclineButtons.setStyleName("AddPinCardLayout-saveOrDeclineButtons");
		saveOrDeclineButtons.addComponents(savePinCard, declinePinCard);
		saveOrDeclineButtons.setComponentAlignment(savePinCard, Alignment.MIDDLE_LEFT);
		saveOrDeclineButtons.setComponentAlignment(declinePinCard, Alignment.MIDDLE_RIGHT);
		
		savePinCard.setWidth("80px");
		savePinCard.setHeight("40px");
		savePinCard.addStyleName("AddPinCardLayout-savePinCard");
		savePinCard.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String typedNameForCard = typeNameOfNewPinCard.getValue();
				if ( !(typedNameForCard.equals("Add a card...") 			//the initial value of textField has to be modified
						|| typedNameForCard.trim().equals("")) ) {			//as well as it cannot be blank or even with any whitespaces
					if (!typedNameForCard.contains(" | ")) {	//and of course typed name can't contain " | " string as it is a break string used in writing to and reading from txt file
						//if all above conditions were fulfilled it is possible to add a pinCard to the pinList
						pinList.addPinCard(typedNameForCard);	//in order to do that, firstly we add pinCard to the pinList in database
						pinBoardLayout.setUpLayoutAccordingToDataBase(pinBoardPanel);	//and then we refresh the display screen
					} else {	//if typed name contains forbidden string " : " the warning is displayed
						pinCardNameWrongFormatWarning.setValue("Name of the list cannot contain \" | \" string!");		
						typeNameOfNewPinCard.focus();	//after every mistake done textField is gained focus
					}
				} else {
					pinCardNameWrongFormatWarning.setValue("");	//if firstly we had written name containing " | " and later on we made other mistake there should not be warning about improper format anymore 
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
				typeNameOfNewPinCard.setValue("Add a card...");	//if we don't want to add this card, for next addition the initial text in the text field should be "Add a card...", otherwise we have there rubish from this addition
				pinCardNameWrongFormatWarning.setValue("");	//similar situation with the label
				removeAllComponents();	//and we have to do opposite action to click button action of the clickToAddPinCard so we have to remove all the components from layout 
				addComponent(clickToAddPinCard);	//in the way that only clickToAddPinCard is present in it
			}
		});
		
		
	}
}

