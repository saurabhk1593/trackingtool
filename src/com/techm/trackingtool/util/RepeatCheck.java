/*package com.techm.trackingtool.util;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.techm.trackingtool.util.LindeList;
import com.techm.trackingtool.util.RepeatCheck;
import com.techm.trackingtool.admin.bean.EmailFrequency;
import com.techm.trackingtool.admin.dao.EmailDAO;
import com.techm.trackingtool.admin.dao.TicketInfoDAO;


public class RepeatCheck implements Runnable {

	Thread runner;
	public static Logger logger = (Logger) Logger.getLogger(RepeatCheck.class);
	public static long sleepInterval=3600*1000;
	
	
	public RepeatCheck() {
	}
	public RepeatCheck(String threadName) {
		runner = new Thread(this, threadName); // (1) Create a new thread.
		//sleepInterval=millis;
		sleepInterval=Long.parseLong("3600000");
		System.out.println("sleepInterval "+sleepInterval);
		//System.out.println(runner.getName());
		runner.start(); // (2) Start the thread.
	}
	public void run() {
	
		boolean mailSend=true;
		logger.info("I AM HERE");
		System.out.println("Inside RepeatCheck run method");
		EmailDAO emailDAO=new EmailDAO();
		while (true) {
			try {
				try {
					System.out.println("Inside RepeatCheck run method - try block");
					LindeList mailList=emailDAO.MailToBeSend();
					EmailFrequency emailFrequency=(EmailFrequency)mailList.get(0);
					System.out.println("Monday email --> "+emailFrequency.getMonday_email());
					emailDAO.sendEMails(mailList);
					//dao.updateSentMailStatus(bookingIdVec);
				} catch (Exception e3) {
					System.out.println("I am here4"+e3.getMessage());
					e3.printStackTrace();
				}
				System.out.println("Thread sleeping ");
				Thread.sleep(sleepInterval);
				System.out.println("Thread Awake now ");
				
			} catch (InterruptedException ex) {

			}
		}

	}

	
	
}
*/