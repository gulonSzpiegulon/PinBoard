package my.vaadin.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class PinBoardLayout extends HorizontalLayout {
	private PinBoard pinBoard = new PinBoard();
	private final String username; 
	
	public PinBoardLayout(String username) {
		setSizeUndefined();
		setStyleName("PinBoardLayout");
		this.username = username;
		readFromFileAndSetUpDataBase();
		setUpLayoutAccordingToDataBase();
	}

	private void readFromFileAndSetUpDataBase() {
		String pathToTheFile = "pinBoardDataBases" + File.separator + username + "-pinBoard.txt";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathToTheFile));
			String line = null;
			while( (line = reader.readLine()) != null ) {
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
	
	public void setUpLayoutAccordingToDataBase() {	//this function is used not only during constructor
		//execution just after reading from file but also when any change is made (so with every button 
		//click that adds/ modifies or deletes any of compoments of this layout)
		
		removeAllComponents();
		
		
		for (int i = 0; i < pinBoard.getPinListsSize(); i++) {
			Label pinListNameLabel = new Label(pinBoard.getPinList(i).getName());
			pinListNameLabel.setStyleName("PinBoardLayout-pinListNameLabel");
		
			DeletePinListButton deletePinListButton = new DeletePinListButton(this, pinBoard, pinBoard.getPinList(i));	
			
			HorizontalLayout pinListNameAndDeletePinListButtonLayout = new HorizontalLayout();
			pinListNameAndDeletePinListButtonLayout.setStyleName("PinBoardLayout-pinListNameAndPinListDeleteButtonLayout");
			pinListNameAndDeletePinListButtonLayout.addComponents(pinListNameLabel, deletePinListButton);
			
			VerticalLayout pinListLayout = new VerticalLayout();
			pinListLayout.setSizeUndefined();
			pinListLayout.addComponent(pinListNameAndDeletePinListButtonLayout);
			
			Panel pinListPanel = new Panel();
			pinListPanel.addStyleName("PinBoardLayout-pinListPanel");
			pinListPanel.setWidth("350px");
			pinListPanel.setContent(pinListLayout);
			
			addComponent(pinListPanel);
		}
		
	}
}
