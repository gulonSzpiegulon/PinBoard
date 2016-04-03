package my.vaadin.app;

public class PinCard {
	private String name = "";
	
	public PinCard(String nameOfPinCard) {
		name = nameOfPinCard;
	}

	public String print() {
		return name;
	}

	public String getName() {
		return name;
	}
}
