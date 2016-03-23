package my.vaadin.app;

public class BoardUser {
	private String name = "";
	private String surname = "";
	private String mail = "";
	private String password = "";
	private PinBoard boardLayout = new PinBoard();	//moze sie tak uda	ewentualnie String boardLayout (moze tak po prostu bedzie prościej - 
											//z niewidocznym polem tekstowym przez ktore bedzie sie ustawiać nową wartość
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PinBoard getBoardLayout() {
		return boardLayout;
	}
	public void setBoardLayout(PinBoard boardLayout) {
		this.boardLayout = boardLayout;
	}
	
}
