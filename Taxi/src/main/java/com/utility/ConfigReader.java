package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties property = new Properties();
	private String configFilename ;
	
	public ConfigReader(){
		configFilename = "config";
	}
	 public String get(String key) {
	    	String value = " ";
	    	if(key != " ") {
	    		loadProperties();
	    		try {
	    			if(!property.getProperty(key).trim().isEmpty()) {
	    				
	    				value = property.getProperty(key).trim();
	    			}
	    		}catch(Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
	    	return value;
	    }
	 public void loadProperties() {
	    	FileInputStream fis ;
	    	try {
	    	fis = new FileInputStream(getConfigFilePath(configFilename));
	    	property.load(fis);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	public String getConfigFilePath(String file) {
		String configpath;
		configpath = System.getProperty("user.dir")+File.separator+file+".properties";
		return configpath;
	}
   
   
}
