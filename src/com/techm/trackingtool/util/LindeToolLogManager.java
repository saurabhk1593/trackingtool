package com.techm.trackingtool.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.techm.trackingtool.constants.LindeToolConstants;

public class LindeToolLogManager {
	
	String className;
    Logger objLog;
   
    static
      {
        PropertyConfigurator propConfig = new PropertyConfigurator();   
        System.out.println("Inside Mgr");
        InputStream logProps = LindeToolLogManager.class.getClassLoader().
        						getResourceAsStream("LindeToolLog.properties");
         System.out.println("Inside Mgr:logProps"+logProps);
        
        try
          {
          	Properties prop = new Properties();
          	prop.load(logProps);
          	 System.out.println("After Load props Mgr");
            propConfig.configure(prop);
            System.out.println("After Configure props Mgr");
          }
        catch (Exception e)
          {
            System.out.println("error in  LindeToolLogManager" + e.getMessage());
          }
      }   

    /**
     *Creates an instance of the Logger class
     *
     * @param className Obtains the classname from where Log object is called
     */
    public LindeToolLogManager(String className)
      {
        this.className = className;
        objLog = Logger.getLogger(className);
      }

    /**
     * Does the actual Logging
     *
     * @param strLevel Logging Levels DEBUG,WARN,INFO,FATAL,ERROR
     * @param Message Actual Message to be logged
     */
    public void logMessage(String strLevel, String Message)
      {
      	
        if (strLevel.equals(LindeToolConstants.DEBUG))
          {
            objLog.log(Level.DEBUG, Message);
          }
        else if (strLevel.equals(LindeToolConstants.INFO))
          {
            objLog.log(Level.INFO, Message);
          }
        else if (strLevel.equals(LindeToolConstants.WARN))
          {
            objLog.log(Level.WARN, Message);
          }
        else if (strLevel.equals(LindeToolConstants.ERROR))
          {
            objLog.log(Level.ERROR, Message);
          }
        else if (strLevel.equals(LindeToolConstants.FATAL))
          {
            objLog.log(Level.FATAL, Message);
          }

        /*
         * Default is o log at DEBUG level
         */
        else
          {
            objLog.log(Level.DEBUG, Message);
          }
      }
	public void logStackTrace(String strLevel,String Message,Throwable throwable)
	{
//		objLog.debug()		
      if (strLevel.equals(LindeToolConstants.DEBUG))
      {
        objLog.debug(Message,throwable);
      }
    else if (strLevel.equals(LindeToolConstants.INFO))
      {
		objLog.info(Message,throwable);
      }
    else if (strLevel.equals(LindeToolConstants.WARN))
      {
		objLog.warn(Message,throwable);
      }
    else if (strLevel.equals(LindeToolConstants.ERROR))
      {
		objLog.error(Message,throwable);
      }
    else if (strLevel.equals(LindeToolConstants.FATAL))
      {
		objLog.fatal(Message,throwable);
      }

    
    else
      {
   		objLog.debug(Message,throwable);
      }

	}	

}
