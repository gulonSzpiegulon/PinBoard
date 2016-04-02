package my.vaadin.app;


import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class BoardView extends VerticalLayout implements View {
	
	private final HorizontalLayout navbarLayout = new HorizontalLayout();
	private final Image pinBoardLogoImage = new Image();
	private final HorizontalLayout userNameLabelAndLogoutButtonLayout = new HorizontalLayout();	//userNameLabel and logoutButton have to be
	//placed in this layout of size undefined as we want to have only 2 elements in navbarLayout instead of 3 - it will be easier to place
	//all components (image, label and button) in right places
	private final Label userNameLabel = new Label();
	private final Button logoutButton = new Button();
	private final Panel pinBoardPanel = new Panel();
	private PinBoardLayout pinBoardLayout = null;	
	
	public BoardView() {
		setUpBoardView();
		setUpNavbarLayout();
		setUpPinBoardLogoImage();
		setUpUserNameLabelAndLogutButtonLayout();
		setUpUserNameLabel();
		setUpLogoutButton();
		setUpPinBoardBackgroundPanel();
	}

	private void setUpBoardView() {
		setSizeFull();
		addComponents(navbarLayout, pinBoardPanel);
		setExpandRatio(pinBoardPanel, 1.0f);	//it is needed if we want to have 
		//pinBoardBackgroundPanel taking all the place available - the rest of BoardView 
		//that is not occupied by navbarLayout
		setStyleName("BoardView");
		//of pinBoardBackgroundLayout and it has to be on bottom of the page
	}
	
	private void setUpNavbarLayout() {
		navbarLayout.setWidth("100%");
		navbarLayout.setHeight("40px");
		navbarLayout.addStyleName("BoardView-navbarLayout");
		navbarLayout.addComponents(pinBoardLogoImage, userNameLabelAndLogoutButtonLayout);
		navbarLayout.setComponentAlignment(pinBoardLogoImage, Alignment.MIDDLE_LEFT);
		navbarLayout.setComponentAlignment(userNameLabelAndLogoutButtonLayout, Alignment.MIDDLE_RIGHT);
	}
	
	private void setUpPinBoardLogoImage() {
		pinBoardLogoImage.setWidth("93px");
		pinBoardLogoImage.setHeight("40px");
		FileResource resourceOfPinBoardLogo = new FileResource(new File(VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath() +
                "/VAADIN/themes/mytheme/IMAGES/pinBoardLogoFat.png"));
		pinBoardLogoImage.setSource(resourceOfPinBoardLogo);
		pinBoardLogoImage.setCaption("");
		pinBoardLogoImage.addStyleName("BoardView-pinBoardLogoImage");
	}
	
	private void setUpUserNameLabelAndLogutButtonLayout() {
		userNameLabelAndLogoutButtonLayout.setSizeUndefined();
		userNameLabelAndLogoutButtonLayout.addStyleName("BoardView-userNameLabelAndLogoutButtonLayout");
		userNameLabelAndLogoutButtonLayout.addComponents(userNameLabel, logoutButton);
	}

	private void setUpUserNameLabel() {
		userNameLabel.setHeight("40px");
		userNameLabel.setWidth("300px");
		userNameLabel.addStyleName("BoardView-userNameLabel");
		//note that value of userNameLabel is set up in enter function
	}
	
	private void setUpLogoutButton() {
		logoutButton.setWidth("41px");
		logoutButton.setHeight("40px");
		logoutButton.setStyleName(BaseTheme.BUTTON_LINK);
		FileResource resourceOfLogoutLogo = new FileResource(new File(VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath() +
                "/VAADIN/themes/mytheme/IMAGES/logoutLogo.png"));
		logoutButton.setIcon(resourceOfLogoutLogo);
		logoutButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getSession().setAttribute("user", null);
            	getUI().getNavigator().navigateTo("");
            }
		});
	}

	private void setUpPinBoardBackgroundPanel() {
		pinBoardPanel.setSizeFull();	//it is needed for making this component expanding
		pinBoardPanel.addStyleName("BoardView-pinBoardPanel");
		//we are forced to use panel here as it supports scrollbar and layout does not
		//note that we can add pinBoardLayout to pinBoardPanel only after constructing it (the pinBoardLayout - and this
		//can be done only in enter method so we adding it also in this method)
	}

	@Override
	public void enter(ViewChangeEvent event) {
       userNameLabel.setValue(String.valueOf(getSession().getAttribute("user")));	//unfortunatelly it has to be here - it cannot 
       //be in constructor so userNameLabel has to be sut up also here
       pinBoardLayout = new PinBoardLayout(userNameLabel.getValue());	//this also as it uses the attribute from the session that is provided only in this method
       pinBoardPanel.setContent(pinBoardLayout);	//and of course this as we cannot add null object to pinBoardPanel 
	}
	
}
