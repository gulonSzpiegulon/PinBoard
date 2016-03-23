package my.vaadin.app;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class WelcomeView extends VerticalLayout implements View {

	public WelcomeView() {
		setSizeFull();
		setStyleName("WelcomeView");
		
		VerticalLayout frontLayout = new VerticalLayout();
		frontLayout.setWidth("1000px");
		frontLayout.setHeight("800px");
		frontLayout.setStyleName("WelcomeView-frontLayout");
		addComponent(frontLayout);
		setComponentAlignment(frontLayout, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout contentLayout = new HorizontalLayout();
		contentLayout.setSizeUndefined();
		contentLayout.setStyleName("WelcomeView-contentLayout");
		frontLayout.addComponent(contentLayout);
		frontLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
		
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setWidth("499px");
		leftLayout.setHeight("700px");
		leftLayout.addStyleName("WelcomeView-leftLayout");
		
		VerticalLayout centerLayout = new VerticalLayout();
		centerLayout.setWidth("2px");
		centerLayout.setHeight("700px");
		centerLayout.addStyleName("WelcomeView-centerLayout");
		
		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setWidth("499px");
		rightLayout.setHeight("700px");
		rightLayout.addStyleName("WelcomeView-rightLayout");
		
		contentLayout.addComponents(leftLayout, centerLayout, rightLayout);
		
		VerticalLayout leftContentLayout = new VerticalLayout();
		leftContentLayout.setSizeUndefined();
		leftContentLayout.addStyleName("WelcomeView-leftContentLayout");
		leftLayout.addComponent(leftContentLayout);
		
		FileResource resource = new FileResource(new File(VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath() +
                "/images/pinBoardLogoFat.png"));
		Image pinBoardLogoImage = new Image("", resource);
		pinBoardLogoImage.setWidth("175px");
		pinBoardLogoImage.setHeight("75px");
		pinBoardLogoImage.addStyleName("WelcomeView-pinBoardLogoImage");
		
		Label welcomeTextLabel = new Label("Welcome to PinBoard - it's a tool that will help you to organize "
				+ "your work. You can use it together with your friends to deal with a team projects or "
				+ "you can treat it as your own board. It is really simple, flexible and intuitionistic.");
		
		welcomeTextLabel.setWidth("419px");
		welcomeTextLabel.addStyleName("WelcomeView-welcomeTextLabel");
		
		MyLoginForm loginForm = new MyLoginForm();
		loginForm.addStyleName("WelcomeView-loginForm");
		
		leftContentLayout.addComponents(pinBoardLogoImage, welcomeTextLabel, loginForm);
		
		MyRegistrationForm RegistrationForm = new MyRegistrationForm();
		RegistrationForm.addStyleName("WelcomeForm-registrationForm");
		
		rightLayout.addComponent(RegistrationForm);
		
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
}
