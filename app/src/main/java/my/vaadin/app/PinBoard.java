package my.vaadin.app;

import java.util.ArrayList;
import java.util.List;

public class PinBoard {
	private String name = "";
	private List<PinList> pinLists = new ArrayList<PinList>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PinList> getPinLists() {
		return pinLists;
	}
	public void setPinLists(List<PinList> pinLists) {
		this.pinLists = pinLists;
	}
}
