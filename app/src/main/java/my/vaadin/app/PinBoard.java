package my.vaadin.app;

import java.util.ArrayList;
import java.util.List;

public class PinBoard {
	private List<PinList> pinLists = new ArrayList<PinList>();
	
	public void addPinList(String pinListName) {
		pinLists.add(new PinList(pinListName));
	}
	
	public PinList getActualPinList() {
		if (!pinLists.isEmpty()) {
			return pinLists.get(pinLists.size() - 1);
		} else {
			return null;
		}
	}

	public int getPinListsSize() {
		return pinLists.size();
	}
	
	public PinList getPinList(int index) {
		if (index < pinLists.size() && index >= 0) {
			return pinLists.get(index);
		} else {
			return null;
		}
	}

	public void deletePinList(PinList pinList) {
		pinLists.remove(pinList);
	}
	
	public String print() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < pinLists.size(); i++) {
			stringBuilder.append(pinLists.get(i).print());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
