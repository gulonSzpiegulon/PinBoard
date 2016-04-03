package my.vaadin.app;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddPinListLayout extends VerticalLayout {
	Button clickToAddPinList = new Button("Add a list...");
	TextField typeNameOfNewPinList = new TextField();
	Label pinListNameWrongFormatWarning = new Label();
	HorizontalLayout saveOrDeclineButtons = new HorizontalLayout();
	Button savePinList = new Button("Save");
	Button declinePinList = new Button("X");
	
	AddPinListLayout(PinBoardLayout pinBoardLayout, PinBoard pinBoard, Panel pinBoardPanel) {
		addComponent(clickToAddPinList);
		setSizeUndefined();
		addStyleName("AddPinListLayout");
		
		clickToAddPinList.setWidth("200px");
		clickToAddPinList.setHeight("40px");
		clickToAddPinList.addStyleName("AddPinListLayout-clickToAddPinList");
		clickToAddPinList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				removeComponent(clickToAddPinList);
				addComponents(typeNameOfNewPinList, pinListNameWrongFormatWarning, saveOrDeclineButtons);
			}
		});
		
		typeNameOfNewPinList.setValue("Add a list...");
		typeNameOfNewPinList.setWidth("200px");
		typeNameOfNewPinList.setHeight("40px");
		typeNameOfNewPinList.addStyleName("AddPinListLayout-typeNameOfNewPinList");
		typeNameOfNewPinList.addFocusListener(new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				typeNameOfNewPinList.setValue("");	
			}
		});
		
		pinListNameWrongFormatWarning.setWidth("200px");
		pinListNameWrongFormatWarning.setHeight("40px");
		pinListNameWrongFormatWarning.addStyleName("AddPinListLayout-pinListNameWrongFormatWarning");
		
		saveOrDeclineButtons.setWidth("200px");
		saveOrDeclineButtons.setHeight("0%");
		saveOrDeclineButtons.setStyleName("AddPinListLayout-saveOrDeclineButtons");
		saveOrDeclineButtons.addComponents(savePinList, declinePinList);
		saveOrDeclineButtons.setComponentAlignment(savePinList, Alignment.MIDDLE_LEFT);
		saveOrDeclineButtons.setComponentAlignment(declinePinList, Alignment.MIDDLE_RIGHT);
		
		savePinList.setWidth("80px");
		savePinList.setHeight("40px");
		savePinList.addStyleName("AddPinListLayout-savePinList");
		savePinList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String typedNameForList = typeNameOfNewPinList.getValue();
				if ( !(typedNameForList.equals("Add a list...") 			//the initial value of textField has to be modified
						|| typedNameForList.trim().equals("")) ) {			//as well as it cannot be blank or even with any whitespaces
					if (!typedNameForList.contains(" : ")) {	//and of course typed name can't contain " : " string as it is a break string used in writing to and reading from txt file
						//if all above conditions were fulfilled it is possible to add a pinList to the board
						pinBoard.addPinList(typedNameForList);	//in order to do that, firstly we add pinList to the pinBoard in database
						pinBoardLayout.setUpLayoutAccordingToDataBase(pinBoardPanel);	//and then we refresh the display screen
					} else {	//if typed name contains forbidden string " : " the warning is displayed
						pinListNameWrongFormatWarning.setValue("Name of the list cannot contain \" : \" string!");		
						typeNameOfNewPinList.focus();	//after every mistake done textField is gained focus
					}
				} else {
					pinListNameWrongFormatWarning.setValue("");	//if firstly we had written name containing " : " and later on we made other mistake there should not be warning about improper format anymore 
					typeNameOfNewPinList.focus();
				}
			}
		});
		
		declinePinList.setWidth("40px");
		declinePinList.setHeight("40px");
		declinePinList.addStyleName("AddPinListLayout-declinePinList");
		declinePinList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				typeNameOfNewPinList.setValue("Add a list...");	//if we don't want to add this list, for next addition the initial text in the text field should be "Add a list...", otherwise we have there rubish from this addition
				pinListNameWrongFormatWarning.setValue("");	//similar situation with the label
				removeAllComponents();	//and we have to do opposite action to click button action of the clickToAddPinList so we have to remove all the components from layout 
				addComponent(clickToAddPinList);	//in the way that only clickToAddPinList is present in it
			}
		});
		
		
	}
}
