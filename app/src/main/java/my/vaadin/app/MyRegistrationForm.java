package my.vaadin.app;

import javax.activation.MailcapCommandMap;

import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MyRegistrationForm extends VerticalLayout {
	private final Label registrationTitleLabel;
	private final Label registrationTextLabel;
	private final TextField nameField;
	private final Label wrongNameFormatLabel;
	private final TextField surnameField;
	private final Label wrongSurnameFormatLabel;
	private final TextField mailField;
	private final Label wrongMailFormatLabel;
	private final PasswordField passwordField;
	private final Label wrongPasswordFormatLabel;
	private final Button signUpButton;
	private final Label signUpFailedLabel;
	
	private final class NameValidator implements Validator {
		private boolean isValid = false; 
		@Override
	    public void validate(Object value) throws InvalidValueException {
			String trimmedValue = value.toString().trim();
			if (!trimmedValue.isEmpty()) {	//bledy moga wyswietlac sie tylko wowcza gdy cos jest wpisane w pole (ogolnie nie trzbabylo 
											//tego robic poza jednym przypadkiem gdy wpiszemy cos w pole usuniemy to i klikniemy tab lub enter
											//wtedy pojawial sie blad wiec trzeba bylo)
				if (trimmedValue.length() < 3) {		
					wrongNameFormatLabel.setValue("Name is too short!");
					isValid = false;
		        } else {								
		        	boolean isValueEnteredContainingLettersOnly = true;		
					char singleCharacter;
			        for (int i = 0; i < trimmedValue.length(); i++) {
			        	singleCharacter = trimmedValue.toLowerCase().charAt(i);
			        	if (singleCharacter < 'a' || singleCharacter > 'z') {
			        		isValueEnteredContainingLettersOnly = false;
			        		break;
			        	}
			        }
			        if (isValueEnteredContainingLettersOnly == false) {	
			        	wrongNameFormatLabel.setValue("Name must contain letters only!");
			        	isValid = false;
			        } else if (trimmedValue.charAt(0) < 'A' || trimmedValue.charAt(0) > 'Z') {
			        	wrongNameFormatLabel.setValue("Name must start with capital letter!");
			        	isValid = false;
			        } else {
			        	wrongNameFormatLabel.setValue("");	//jesli uzytkownik poprawi blad to ostrzerzenie powinno zniknąc
			        	isValid = true;
			        }
		        }
			} else {
				wrongNameFormatLabel.setValue("");	//jesli tego nie byloby i wpisalibysmy np 123 klikneli ENTER (nie tab), zrobilby sie blad 
													//ze tylko litery, kasujemy tekst i klikamy tab, to blad by zostal mimo ze pole bylo by puste
				isValid = true; //ponieważ puste pola so walidowane wewnatrz click listenera sign up buttona
			}
		}
	}
	
	private final class SurnameValidator implements Validator {
		private boolean isValid = false; 
		@Override
	    public void validate(Object value) throws InvalidValueException {
			String trimmedValue = value.toString().trim();
			if (!trimmedValue.isEmpty()) {	//bledy moga wyswietlac sie tylko wowcza gdy cos jest wpisane w pole (ogolnie nie trzbabylo tego robic
											//poza jednym przypadkiem gdy wpiszemy cos w pole usuniemy to i klikniemy tab lub enter
											//wtedy pojawial sie blad wiec trzeba bylo)
				if (trimmedValue.length() < 3) {		
					wrongSurnameFormatLabel.setValue("Surame is too short!");
					isValid = false;
		        } else {								
		        	boolean isValueEnteredContainingLettersOnly = true;		
					char singleCharacter;
			        for (int i = 0; i < trimmedValue.length(); i++) {
			        	singleCharacter = trimmedValue.toLowerCase().charAt(i);
			        	if (singleCharacter < 'a' || singleCharacter > 'z') {
			        		isValueEnteredContainingLettersOnly = false;
			        		break;
			        	}
			        }
			        if (isValueEnteredContainingLettersOnly == false) {	
			        	wrongSurnameFormatLabel.setValue("Surame must contain letters only!");
			        	isValid = false;
			        } else if (trimmedValue.charAt(0) < 'A' || trimmedValue.charAt(0) > 'Z') {
			        	wrongSurnameFormatLabel.setValue("Surame must start with capital letter!");
			        	isValid = false;
			        } else {
			        	wrongSurnameFormatLabel.setValue("");	//jesli uzytkownik poprawi blad to ostrzerzenie powinno zniknąc
			        	isValid = true;
			        }
		        }
			} else {
				wrongSurnameFormatLabel.setValue("");	//jesli tego nie byloby i wpisalibysmy np 123 klikneli ENTER (nie tab), zrobilby sie blad ze 
														//tylko litery, kasujemy tekst i klikamy tab, to blad by zostal mimo ze pole bylo by pust
				isValid = true; //ponieważ puste pola so walidowane wewnatrz click listenera sign up buttona
			}
		}
	}
	
	private final class EmailValidator implements Validator {
		private boolean isValid = false; 
		@Override
		public void validate(Object value) throws InvalidValueException {
			String trimmedValue = value.toString().trim();
			if (!trimmedValue.isEmpty() 
					&& !trimmedValue.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:"
							+ "[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
				wrongMailFormatLabel.setValue("Wrong email format!");
				isValid = false;
			} else {
				wrongMailFormatLabel.setValue("");
				isValid = true;
			}
		}
	}
	
	
	private final class PasswordValidator implements Validator {
		private boolean isValid = false; 
		@Override
	    public void validate(Object value) throws InvalidValueException {
			String stringValue = value.toString();
			if (!stringValue.isEmpty() && !stringValue.matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})")) {
				 //znaczy sie ze ma byc przynajmniej jedna litera mala, jedna duza, znak specjalny i liczba, od 8 do 20 znakow
				wrongPasswordFormatLabel.setValue("Length 8 - 20, one of each: a, A, 1, $");	
				isValid = false;
			} else {
	        	wrongPasswordFormatLabel.setValue("");
	        	isValid = true;
	        }
	    }
	}
	
	MyRegistrationForm(UserDataBase userDataBase) {
		setSizeUndefined();
		
		registrationTitleLabel = new Label("Don't have an accont yet?");
		registrationTitleLabel.addStyleName("MyRegistrationForm-registrationTitleLabel");
		
		registrationTextLabel = new Label("You can sign up here. It's free and always will be!");
		registrationTextLabel.setWidth("419px");
		registrationTextLabel.addStyleName("MyRegistrationForm-registrationTextLabel");
		
		nameField = new TextField("Name:");
		nameField.setWidth("330px");	
		nameField.setRequired(true);
		NameValidator nameValidator = new NameValidator();
		nameField.addValidator(nameValidator);
		nameField.setInvalidAllowed(false);	 		//jesli tego nie ma to po wklepaniu blednego formatu imienia kliknieciu enter badz tab pojawia sie blad, po powrocie
													//do tego pola i usunieciu wszystkiego klinieciu enter blad nie znika, takze to musi byc
		nameField.setImmediate(true);
		nameField.addStyleName("MyRegistrationForm-nameField");
		wrongNameFormatLabel = new Label("");
		wrongNameFormatLabel.addStyleName("MyRegistrationForm-wrongNameFormatLabel");
		wrongNameFormatLabel.setWidth("300px");
		HorizontalLayout nameLayout = new HorizontalLayout(nameField, wrongNameFormatLabel);
		nameLayout.setSizeUndefined();
		nameLayout.addStyleName("MyRegistrationForm-nameLayout");
		
		surnameField = new TextField("Surname:");
		surnameField.setWidth("330px");	
		surnameField.setRequired(true);
		SurnameValidator surnameValidator = new SurnameValidator();
		surnameField.addValidator(surnameValidator);
		surnameField.setInvalidAllowed(false);
		surnameField.addStyleName("MyRegistrationForm-surnameField");
		wrongSurnameFormatLabel = new Label("");
		wrongSurnameFormatLabel.addStyleName("MyRegistrationForm-wrongSurnameFormatLabel");
		wrongSurnameFormatLabel.setWidth("300px");
		HorizontalLayout surnameLayout = new HorizontalLayout(surnameField, wrongSurnameFormatLabel);
		surnameLayout.setSizeUndefined();
		surnameLayout.addStyleName("MyRegistrationForm-surnameLayout");
		
		mailField = new TextField("E-mail:");
		mailField.setWidth("330px");	
		mailField.setRequired(true);
		EmailValidator emailValidator = new EmailValidator();
		mailField.addValidator(emailValidator);
		mailField.setInvalidAllowed(false);
		mailField.addStyleName("MyRegistrationForm-mailField");
		wrongMailFormatLabel = new Label("");
		wrongMailFormatLabel.addStyleName("MyRegistrationForm-wrongMailFormatLabel");
		wrongMailFormatLabel.setWidth("300px");
		HorizontalLayout mailLayout = new HorizontalLayout(mailField, wrongMailFormatLabel);
		mailLayout.setSizeUndefined();
		mailLayout.addStyleName("MyRegistrationForm-mailLayout");
		
		passwordField = new PasswordField("Password:");
		passwordField.setWidth("330px");	
		passwordField.setRequired(true);
		PasswordValidator passwordValidator = new PasswordValidator();
		passwordField.addValidator(passwordValidator);
		passwordField.setInvalidAllowed(false);
		passwordField.addStyleName("MyRegistrationForm-passwordField");
		wrongPasswordFormatLabel = new Label("");
		wrongPasswordFormatLabel.addStyleName("MyRegistrationForm-wrongPasswordFormatLabel");
		wrongPasswordFormatLabel.setWidth("300px");
		HorizontalLayout passwordLayout = new HorizontalLayout(passwordField, wrongPasswordFormatLabel);
		passwordLayout.setSizeUndefined();
		passwordLayout.addStyleName("MyRegistrationForm-passwordLayout");
		
		signUpButton = new Button("Sign Up!");
		signUpButton.setWidth("100px");
		signUpButton.setHeight("40px");
		signUpButton.addStyleName("MyRegistrationForm-signUpButton");
		signUpButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (nameField.getValue().isEmpty() || surnameField.getValue().isEmpty() || mailField.getValue().isEmpty() || passwordField.getValue().isEmpty()) {
					signUpFailedLabel.setValue("At least one of the fields is empty!");
				} else if (!nameValidator.isValid || !surnameValidator.isValid || !emailValidator.isValid || !passwordValidator.isValid) {
					signUpFailedLabel.setValue("At least one of the fields is incorrect!");
				} else {
					if (userDataBase.contains(mailField.getValue())) {
						wrongMailFormatLabel.setValue("This e-mail is occupied already!");
						signUpFailedLabel.setValue("Try another e-mail address!");
					} else {
						userDataBase.addNewUser(nameField.getValue(), surnameField.getValue(), mailField.getValue(), passwordField.getValue());
						signUpFailedLabel.setValue("You've registered succesfully!");
					}
				}
			}
		});
		signUpButton.addBlurListener(new BlurListener() {
			@Override
			public void blur(BlurEvent event) {
				signUpFailedLabel.setValue("");
			}
		});
		signUpFailedLabel = new Label("");
		signUpFailedLabel.setWidth("260px");
		signUpFailedLabel.addStyleName("MyRegistratnioForm-signUpFailedLabel");
		HorizontalLayout signUpLayout = new HorizontalLayout();
		signUpLayout.setSizeUndefined();
		signUpLayout.addStyleName("MyRegistratationForm-signUpLayout");
		signUpLayout.addComponents(signUpButton, signUpFailedLabel);
		
		addComponents(registrationTitleLabel, registrationTextLabel, nameLayout, surnameLayout, mailLayout, passwordLayout, signUpLayout);
	}
}
