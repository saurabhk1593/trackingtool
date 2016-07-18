package com.techm.trackingtool.admin.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.AddApplicationForm;
import com.techm.trackingtool.admin.form.UploadTTForm;
import com.techm.trackingtool.admin.form.UploadVivaldiForm;
import com.techm.trackingtool.services.TicketInfoServices;
import com.techm.trackingtool.services.VivaldiInfoService;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.csvReader.opencsv.bean.CsvToBean;

@Controller
public class UploadVivaldiInfoController {
	
	
	
	
	

	static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(UploadVivaldiInfoController.class.getName());
		
		@Autowired
		VivaldiInfoService vivaldiinfoservices;
		
	  
		@RequestMapping(value = { "/uploadVivaldiInfo.spring" }, method = RequestMethod.GET)
		public ModelAndView applicationPage(HttpServletRequest req,
				HttpServletResponse res, AddApplicationForm addApplicationForm)
			{
			
			
			lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
			
			return new ModelAndView("uploadVivaldi");
			
		}
		

		@RequestMapping(value = { "/uploadVivaldiInfo.spring" }, method = RequestMethod.POST)
		public ModelAndView onSubmit(HttpServletRequest request,
		        HttpServletResponse response,UploadVivaldiForm uploadVivaldiForm,@RequestParam("file") MultipartFile file) {
			 	lindeLogMgr.logMessage("INFO","onSubmit");
				//System.out.println("hi");
				HttpSession userSession = request.getSession(true);
				User user=(User)userSession.getAttribute("userInformation");
				
	    	//	UploadTTForm bean = uploadTTForm;
	    	//	String file1 = bean.getFl_Upload();
	    		
	    	  //  String uploadDir = request.getContextPath().getRealPath("/upload/");
	    	    String uploadDir ="E://Examples//";
	    	    // Create the directory if it doesn't exist
	    	    File dirPath = new File(uploadDir);
	    	    if (!dirPath.exists()) 
	    	      dirPath.mkdirs();

	    	    String sep = System.getProperty("file.separator");

	    	    File uploadedFile = new File(uploadDir + "/" + file.getOriginalFilename());
	    	    try {
					FileCopyUtils.copy(file.getBytes(), uploadedFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      
	         //   if(file1 == null) {
	            	
	         //   	System.out.println("Inside -->iiii "+bean.getFl_Upload());

	        //    }
	        //   String columns[] = {"Project","Activity_ID","Activity_Desc","BuPo","BuPo Type","Number","BuPo Text","Date","Effort [h]","System","Comment","Reason (Actual > Planned)","User"}; 
	    	    
	    	    String columns[] = {"Project","Activity ID","Activity Desc","BuPo","BuPo Type","Number","BuPo Text","Date","System","Comment","User"};
	    	    
	           List list=null;
	           String status = "";
	     	    String errormessage = "";
			try {
				System.out.println("before csvtobean");
				list = getCSVBeans(uploadedFile.toString(),"com.techm.trackingtool.admin.bean.VivaldiInfo",columns);
		        status = vivaldiinfoservices.uploadInfo(list,user);
	  	    }
	  	    catch(Exception e)
	  	    {
	          errormessage = e.getMessage();
	          return new ModelAndView("uploaded_records", "message","Error in input");
	  	    }

	       System.out.println("Upload Status:"+status);     
		
	            return new ModelAndView("Info_Saved", "message","Data saved successfully");
	     
		}


	/*    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	    throws ServletException {

	    	 System.out.println("Call inside Binder --> ");
	    //binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	    binder.registerCustomEditor(String.class,
	    	            new StringMultipartFileEditor());
	    // now Spring knows how to handle multipart object and convert them
	    System.out.println("Call inside Binder 2 --> ");
	}*/
	    
		public static List getCSVBeans(String  fileName,String beanName,String []columns) throws Exception
		{
			  System.out.println("Inside the method");
			  //TicketInfo ttInfo = new TicketInfo();
			  Class obj = Class.forName(beanName);
			  System.out.println("After class.fornamer method");
			 // System.out.println("Inside the method-->"+obj);
		       // the fields to bind do in your JavaBean
		      
		      CsvToBean csv = new CsvToBean();
		      System.out.println("Inside getCSVBeans method");
		      List list = csv.vivaldiparse(obj,new FileReader(fileName),columns);
		      System.out.println("List:"+list.size()); 
		      return list;
		}
	    
	    

	}



