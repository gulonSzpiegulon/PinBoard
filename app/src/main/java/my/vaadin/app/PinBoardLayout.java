package my.vaadin.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PinBoardLayout extends HorizontalLayout {
	private PinBoard pinBoard = new PinBoard();
	private Panel pinBoardPanel;
	private final String username; 
	
	public PinBoardLayout(String username, Panel pinBoardPanel) {
		setSizeUndefined();
		setStyleName("PinBoardLayout");
		this.username = username;
		readFromFileAndSetUpDataBase();
		setUpLayoutAccordingToDataBase(false, null);
	}

	private void readFromFileAndSetUpDataBase() {
		String pathToTheFile = "pinBoardDataBases" + File.separator + username + "-pinBoard.txt";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathToTheFile));
			String line = null;
			while( (line = reader.readLine()) != null ) {
				System.out.println("|" + line + "|");
				String pinListName = line.substring(0, line.indexOf(" : "));	//pinListName is always on the beginning of the line and 
				//ends with " : " breake string which is not put into substring
				pinBoard.addPinList(pinListName);	//new list is added to database
				int indexOfFirstLetterOfCardName = line.indexOf(" : ") + 3;
				int indexOfBreakStringAfterCardName;
				while( (indexOfBreakStringAfterCardName = line.indexOf(" | ", indexOfFirstLetterOfCardName)) != -1 ) {	//we are looking up here for index of 
					//next " | " break string every time substring that we are searching in is smaller as indexOfFirstLatterOfCardName is bigger, and if the substring 
					//at end is zero-length function indexOf should return -1 and while loop is being broken
					String pinCardName = line.substring(indexOfFirstLetterOfCardName, indexOfBreakStringAfterCardName);
					pinBoard.getActualPinList().addPinCard(pinCardName);	//the last pinlist added is added new pinCard
					indexOfFirstLetterOfCardName = indexOfBreakStringAfterCardName + 3;
				}
				if (indexOfFirstLetterOfCardName != line.length()) {	//if there are no cardsnames more to read, indexOfFirstLetterOfCardName will always be 
					//equal to line's length(), in other case: there is one more cards name to read 
					String pinCardName = line.substring(indexOfFirstLetterOfCardName);
					pinBoard.getActualPinList().addPinCard(pinCardName);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException during execution of readFromFileAndSetUpDataBase method of PinBoardLayout class!!!");
			System.out.println("A new file of name " + username + "-pinBoard has been created in pinBoardDataBases folder.");
			//it means that a file wasn't found and this is possible only if user opened his pinBoard for the first time 
			//and in this situation we have to create an empty file for him
			File file = new File(pathToTheFile);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				System.out.println("IOException during execution of readFromFileAndSetUpDataBase method of PinBoardLayout class!!!");
			}
		} catch (IOException e) {
			System.out.println("IOException during execution of readFromFileAndSetUpDataBase method of PinBoardLayout class!!!");
		}
		//for checking if everything was read properly
		//System.out.println(pinBoard.print());
	}
	
	public void setUpLayoutAccordingToDataBase(boolean wasJustPinListAdded, PinList pinListWhichWasAddedAPinCard) {	//this function is used not only during constructor
		//execution just after reading from file but also when any change is made (so with every button 
		//click that adds/ modifies or deletes any of compoments of this layout)
		removeAllComponents();	//first of all we have to remove from the display every pinList and pinCard
		
		for (int i = 0; i < pinBoard.getPinListsSize(); i++) {	//here we add each pinList that can be found in database
			Label pinListNameLabel = new Label(pinBoard.getPinList(i).getName());
			pinListNameLabel.setStyleName("PinBoardLayout-pinListNameLabel");
			pinListNameLabel.setWidth("280px");
		
			DeletePinListButton deletePinListButton = new DeletePinListButton(this, pinBoard, pinBoard.getPinList(i));	
			
			HorizontalLayout pinListNameLabelAndDeletePinListButtonLayout = new HorizontalLayout();
			pinListNameLabelAndDeletePinListButtonLayout.setStyleName("PinBoardLayout-pinListNameLabelAndDeletePinListButtonLayout");
			pinListNameLabelAndDeletePinListButtonLayout.addComponents(pinListNameLabel, deletePinListButton);
			pinListNameLabelAndDeletePinListButtonLayout.setWidth("100%");
			pinListNameLabelAndDeletePinListButtonLayout.setComponentAlignment(pinListNameLabel, Alignment.TOP_LEFT);
			pinListNameLabelAndDeletePinListButtonLayout.setComponentAlignment(deletePinListButton, Alignment.TOP_RIGHT);
			
			VerticalLayout pinCardsOfPinListLayout = new VerticalLayout();
			pinCardsOfPinListLayout.setSizeUndefined();
			pinCardsOfPinListLayout.addStyleName("PinBoardLayout-pinCardsOfPinListLayout");
			
			Panel pinListPanel = new Panel();
			pinListPanel.addStyleName("PinBoardLayout-pinListPanel");
			pinListPanel.setSizeFull();
			pinListPanel.setContent(pinCardsOfPinListLayout);
			if (pinListWhichWasAddedAPinCard != null && pinListWhichWasAddedAPinCard.equals(pinBoard.getPinList(i))) {	//if addPinCardLayout calls this function with a parameter of not null pinList, here it's checked which 
				//pinList is that one and the found one is the pinList that was lastly added a pinCard so we have to move its scrollbar to down
				pinListPanel.setScrollTop(10000);
			}
			
			VerticalLayout pinListPanelContainerLayout = new VerticalLayout();	//it was needed to setSizeFull for pinListPanel - otherwise there wasn't any vertical scrollbar
			pinListPanelContainerLayout.addStyleName("PinBoardLayout-pinListPanelContainerLayout");
			pinListPanelContainerLayout.setWidth("350px");
			pinListPanelContainerLayout.setHeight("800px");	//unfortunatelly for proper working of panel this layout has to have fixed size - we cannot use %
			pinListPanelContainerLayout.addComponents(pinListNameLabelAndDeletePinListButtonLayout, pinListPanel);
			pinListPanelContainerLayout.setExpandRatio(pinListPanel, 1.0f);
			
			addComponent(pinListPanelContainerLayout);
			
			//and every pinCard of actual pinList
			for (int j = 0; j < pinBoard.getPinList(i).getPinCardsSize(); j++) {
				Label pinCardNameLabel = new Label(pinBoard.getPinList(i).getPinCard(j).getName());	
				pinCardNameLabel.addStyleName("PinBoardLayout-pinCardNameLabel");
				pinCardNameLabel.setWidth("270px");
				
				DeletePinCardButton deletePinCardButton = new DeletePinCardButton(this, pinBoard.getPinList(i), pinBoard.getPinList(i).getPinCard(j));
				
				HorizontalLayout pinCardNameLabelAndDeletePinCardButtonLayout = new HorizontalLayout();
				pinCardNameLabelAndDeletePinCardButtonLayout.addStyleName("PinBoardLayout-pinCardNameLabelAndDeletePinCardButtonLayout");
				pinCardNameLabelAndDeletePinCardButtonLayout.addComponents(pinCardNameLabel, deletePinCardButton);
				pinCardNameLabelAndDeletePinCardButtonLayout.setWidth("320px");
				pinCardNameLabelAndDeletePinCardButtonLayout.setComponentAlignment(pinCardNameLabel, Alignment.MIDDLE_LEFT);
				pinCardNameLabelAndDeletePinCardButtonLayout.setComponentAlignment(deletePinCardButton, Alignment.TOP_RIGHT);
			
				pinCardsOfPinListLayout.addComponent(pinCardNameLabelAndDeletePinCardButtonLayout);
			}
			
			//after adding all pinCards that are in the pinList to the pinListPanel we have to add a layout that's responsible for adding new pinCard
			AddPinCardLayout addPinCardLayout = new AddPinCardLayout(this, pinBoard.getPinList(i), pinListPanel);
			pinCardsOfPinListLayout.addComponent(addPinCardLayout);
		}
		
		//after adding all pinLists to the display we have to add special layout that allow us to add new pinList
		AddPinListLayout addPinListLayout = new AddPinListLayout(this, pinBoard); //pinBoardPanel just have to be sent here
		addComponent(addPinListLayout);
		
		//however setScrollLeft to the right should only be done after addition of new pinList
		//as well as setScrollTop to the bottom after addition of new pinCard
		//and setUpLayoutAcc.... executes also when list or card is deleted and then scrolbar should stay as it stayed - soooo it should be repaired
		writeToTheFile();	//it is not needed when this function is called from the constructor of this class but during every other call it is so... it is here not in other place
	}
	
	private void writeToTheFile() {
		String pathToTheFile = "pinBoardDataBases" + File.separator + username + "-pinBoard.txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTheFile));
			for(int i = 0; i < pinBoard.getPinListsSize(); i++) {
				StringBuilder line = new StringBuilder();
				line.append(pinBoard.getPinList(i).getName());
				line.append(" : ");
				int j;	//we have to to this to have 	list : card | card | card 
							//and not to have				list : card | card | card |
				for (j = 0; j < pinBoard.getPinList(i).getPinCardsSize() - 1; j++) {
					line.append(pinBoard.getPinList(i).getPinCard(j).getName());
					line.append(" | ");
				}
				if (pinBoard.getPinList(i).getPinCardsSize() > 0) {	//it is needed
					line.append(pinBoard.getPinList(i).getPinCard(j).getName());	//as we have to do this in this situation list : card | card but not int this list : 
				}
				writer.write(line.toString());
				if (i < pinBoard.getPinListsSize() - 1) {	//it has to be as reader couldn't read from file which has an newLine character at the end
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("IOException during writeToFile() method of pinBoardLayout class!!!");
		}
		
	}
}
