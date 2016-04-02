package my.vaadin.app;

import com.vaadin.ui.Button;

public class DeletePinListButton extends Button {
	
	
	//ogolnie to najlepiej wstawic te klase jako prywatna klase wewnetrzna pinBoardLayoutu
	//i wtedy odznaczyc z setUpLayoutAccordingToDataBase public - ma byc private
	
	
//	final private PinBoard pinBoard;
//	final private PinList pinList;
//	final private PinBoardLayout pinBoardLayout;
	
	public DeletePinListButton(PinBoardLayout pinBoardLayout, PinBoard pinBoard, PinList pinList) {
//		pinBoard = pinBoardFromWhichToDelete;
//		pinList = pinListToDelete;
		
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				pinBoard.deletePinList(pinList);
				pinBoardLayout.setUpLayoutAccordingToDataBase();
			}
		});
		setWidth("40px");
		setHeight("40px");
		setCaption("X");
		setStyleName("DeleteListButton");
	}
	
	
	//it would look out better if we added an icon to it however remember that v-icon css is set up already 
	//so it can be difficult a little bit but if we do it by Icon icon = new Icon it will be ok as we can add style to it with our name
	//Icon icon = new Icon();    <--------     later on!!
}
