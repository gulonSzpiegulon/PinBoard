package my.vaadin.app;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MyLoginForm extends VerticalLayout {
	private final TextField emailField;
	private final PasswordField passwordField;
	private final HorizontalLayout logInButtonAndWrongPasswordOrEmailLabelLayout;
	private final Button logInButton;
	private final Label wrongPasswordOrEmailLabel;
	
	public MyLoginForm(UserDataBase userDataBase) {
		setSizeUndefined();
		
		emailField = new TextField("E-mail:");
		emailField.setWidth("330px");	
		emailField.focus();
		emailField.addStyleName("MyLoginForm-emailField");
		emailField.addFocusListener(new FieldEvents.FocusListener() {
			@Override
			public void focus(FocusEvent event) {
					wrongPasswordOrEmailLabel.setValue("");			
			}
		});
		
		passwordField = new PasswordField("Password:");
		passwordField.setWidth("330px");
		passwordField.setValue("");
		passwordField.addStyleName("MyLoginForm-passwordField");
		passwordField.addFocusListener(new FieldEvents.FocusListener() {
			@Override
			public void focus(FocusEvent event) {
					wrongPasswordOrEmailLabel.setValue("");			
			}
		});
		
		logInButtonAndWrongPasswordOrEmailLabelLayout = new HorizontalLayout();
		logInButtonAndWrongPasswordOrEmailLabelLayout.setSizeUndefined();
		
		logInButton = new Button("Log In");
		logInButton.setWidth("100px");
		logInButton.setHeight("40px");
		logInButton.addStyleName("MyLoginForm-logInButton");
		logInButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (userDataBase.checkIfUsernameAndPasswordMatches(emailField.getValue(), passwordField.getValue())) {
					getSession().setAttribute("user", emailField.getValue());
					getUI().getNavigator().navigateTo("Board");
				} else {
					wrongPasswordOrEmailLabel.setValue("Incorrect password or e-mail!");
				}
	        }
		});
	    
		wrongPasswordOrEmailLabel = new Label("");
		wrongPasswordOrEmailLabel.setWidth("250px");
		wrongPasswordOrEmailLabel.addStyleName("MyLoginForm-wrongPasswordOrEmailLabel");
		
		logInButtonAndWrongPasswordOrEmailLabelLayout.addComponents(logInButton, wrongPasswordOrEmailLabel);
		addComponents(emailField, passwordField, logInButtonAndWrongPasswordOrEmailLabelLayout);
	}
}
