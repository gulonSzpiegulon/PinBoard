package my.vaadin.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PinList {
	private String name = "";
	private List<PinCard> pinCards = new ArrayList<PinCard>();
	
	public PinList(String pinListName) {
		name = pinListName;
	}

	public void addPinCard(String nameOfPinCard) {
		pinCards.add(new PinCard(nameOfPinCard));
	}

	public String getName() {
		return name;
	}

	public String print() {
		StringBuilder stringBuilder = new StringBuilder(name + " : ");
		for (int i = 0; i < pinCards.size(); i++) {
			stringBuilder.append(pinCards.get(i).print());
			if (i != (pinCards.size() - 1)) {
				stringBuilder.append(" | ");
			}
		}
		return stringBuilder.toString();
	}

	public int getPinCardsSize() {
		return pinCards.size();
	}

	public PinCard getPinCard(int index) {
		if (index < pinCards.size() && index >= 0) {
			return pinCards.get(index);
		} else {
			return null;
		}
	}

	public void deletePinList(PinCard pinCard) {
		pinCards.remove(pinCard);
	}
	
	
}
