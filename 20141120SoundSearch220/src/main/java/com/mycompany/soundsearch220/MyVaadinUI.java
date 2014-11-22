package com.mycompany.soundsearch220;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.google.gwt.user.client.ui.AbsolutePanel; 
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.Audio;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbsoluteLayout;
import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.ExternalResource;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

//    MyVaadinUI(MyVaadinUI primary) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.mycompany.soundsearch220.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        
        
        AbsoluteLayout primary = new AbsoluteLayout();
        
        

        primary = songresultpage.drawPage(primary);
        setContent(primary);
    }

}
