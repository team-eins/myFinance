package main.java.team1.myFinance.core;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import main.java.data.handler.DataHandler;
import main.java.team1.myFinance.contracts.IDataHandler;

public class ServiceLocator {

    private static final String lock = "This is my lock object for syncronized blocks";
    private static Logger _logger;
    private static IDataHandler _dataHandler;


    public static IDataHandler getDataHandler(){
        if(_dataHandler == null){
            synchronized (lock){
                if(_dataHandler == null){
                    _dataHandler = new DataHandler();
                }
            }
        }

        return _dataHandler;
    }

    public static Logger getLogger(){
        if(_logger == null){
            synchronized (lock){
                if(_logger == null){
                    _logger = LogManager.getLogger(App.class);
                }
            }
        }

        return _logger;
    }
}
