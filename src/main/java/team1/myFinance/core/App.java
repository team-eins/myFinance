package team1.myFinance.core;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("api")
public class App extends ResourceConfig {
    public App(){
        packages("team1.myFinance.core", "team1.myFinance.web");
        System.out.println("Juchu! Ich wurde instanziert!");
    }
}
