package com.phyous.apiweb;

import com.phyous.apiweb.controller.HeadlineController;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;

public class ApiWebDemo {
    
    public static void main(String[] args) {
        Pippo pippo = new Pippo(new ApiWebApp());
        pippo.getServer().getSettings().port(8081);
        pippo.start();
    }
    
    static class ApiWebApp extends ControllerApplication {
        @Override
        protected void onInit() {
            GET("/headlines", HeadlineController.class, "getHeadlines");
        }
    }
}
