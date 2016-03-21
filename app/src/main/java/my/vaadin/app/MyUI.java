package my.vaadin.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@Widgetset("my.vaadin.app.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	final Navigator navigator = new Navigator(this, this);
    	
    	navigator.addView("", WelcomeView.class);
        navigator.addView("Board", BoardView.class);
    	//navigator.navigateTo("");
        navigator.addViewChangeListener(new ViewChangeListener () {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				
				//taki trik zeby zawsze przekierowac uzytkownika jesli sie nie zalogowal na WelcomeView
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isWelcomeView = event.getNewView() instanceof WelcomeView;	//sprawdza czy aktualny View to WelcomeView
				
				if (!isLoggedIn && !isWelcomeView) {
					navigator.navigateTo("");
					return false;
				} else if (isLoggedIn && isWelcomeView) {	//ani zeby nie mogł przejsc na WelcomeView jesli jest zalogowany
					return false;
				}
				//ogolnie chyba chodzi tylko o to zeby koles majstrujac coś w polu gdzei sie adres strony 
				//wpisuje nie przeszedł tam gdzie nie powinien, ale moze jeszcze o cos... 
				//no jeszcze np. o odswiezanie strony chodzi - wtedy też nic sie nie zepsuje
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				
			}
        	
        });
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
