package my.vaadin.app;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class BoardView extends VerticalLayout implements View {
	
	private final Label helloTextLabel;
	private final Button logOutButton;
	
	public BoardView() {
		setSizeFull();
		
		logOutButton = new Button("Log Out!",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getSession().setAttribute("user", null);
            	getUI().getNavigator().navigateTo("");
            }
        });
		
		helloTextLabel = new Label("");
		
		addComponents(logOutButton, helloTextLabel);
        setComponentAlignment(logOutButton, Alignment.MIDDLE_CENTER);
        setComponentAlignment(helloTextLabel, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
        helloTextLabel.setValue("Hello " + String.valueOf(getSession().getAttribute("user")));	//to musi byc w enter inaczej null pointer exeption
	}
	
}
