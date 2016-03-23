package my.vaadin.app;

import java.util.ArrayList;
import java.util.List;

public class PinList {
	private String name = "";
	private List<PinCard> pinCards = new ArrayList<PinCard>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PinCard> getPinCards() {
		return pinCards;
	}
	public void setPinCards(List<PinCard> pinCards) {
		this.pinCards = pinCards;
	}
}
