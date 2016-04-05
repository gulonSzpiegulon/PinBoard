package my.vaadin.app;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class AddPinListLayout extends VerticalLayout {
	Button clickToAddPinList = new Button("Add a list...");
	TextArea typeNameOfNewPinList = new TextArea();
	HorizontalLayout saveOrDeclineButtons = new HorizontalLayout();
	Button savePinList = new Button("Save");
	Button declinePinList = new Button("X");
	
	AddPinListLayout(PinBoardLayout pinBoardLayout, PinBoard pinBoard) {
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
				addComponents(typeNameOfNewPinList, saveOrDeclineButtons);
				typeNameOfNewPinList.focus();
			}
		});
		
		typeNameOfNewPinList.setValue("");
		typeNameOfNewPinList.setWidth("320px");
		typeNameOfNewPinList.setHeight("160px");
		typeNameOfNewPinList.addStyleName("AddPinListLayout-typeNameOfNewPinList");
		typeNameOfNewPinList.addFocusListener(new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				typeNameOfNewPinList.setValue("");	
			}
		});
		
		saveOrDeclineButtons.setSizeUndefined();
		saveOrDeclineButtons.setStyleName("AddPinListLayout-saveOrDeclineButtons");
		saveOrDeclineButtons.addComponents(savePinList, declinePinList);
		
		savePinList.setWidth("80px");
		savePinList.setHeight("40px");
		savePinList.addStyleName("AddPinListLayout-savePinList");
		savePinList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String typedNameForList = typeNameOfNewPinList.getValue();
				if (!typedNameForList.trim().equals("") && !typedNameForList.contains(" : ")) {
					pinBoard.addPinList(typedNameForList);	
					pinBoardLayout.setUpLayoutAccordingToDataBase(true, null);	
				} else {
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
				typeNameOfNewPinList.setValue("");	//if we don't want to add this list, for next addition the initial text in the text field should be "Add a list...", otherwise we have there rubish from this addition
				removeAllComponents();	//and we have to do opposite action to click button action of the clickToAddPinList so we have to remove all the components from layout 
				addComponent(clickToAddPinList);	//in the way that only clickToAddPinList is present in it
			}
		});
		
		
	}
}
