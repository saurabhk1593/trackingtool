package com.techm.trackingtool.admin.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.techm.trackingtool.admin.bean.CRinfo;
import com.techm.trackingtool.admin.bean.TicketInfo;
import com.techm.trackingtool.admin.bean.User;
import com.techm.trackingtool.admin.form.UploadCRForm;
import com.techm.trackingtool.services.CRInfoService;
import com.techm.trackingtool.services.UserdetailsService;
import com.techm.trackingtool.util.LindeToolLogManager;
import com.techm.trackingtool.util.csvReader.opencsv.bean.CsvToBean;

@Controller
public class UploadCRInfoController {
	
	static LindeToolLogManager lindeLogMgr = new LindeToolLogManager(UploadCRInfoController.class.getName());
	
	@Autowired
	CRInfoService crinfoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = { "/uploadCRInfo.spring" }, method = RequestMethod.GET)
	public ModelAndView applicationPage(HttpServletRequest req,
			HttpServletResponse res, UploadCRForm addApplicationForm)
		{
		
		
		lindeLogMgr.logMessage("DEBUG", "Inside applicationPage");
		
		return new ModelAndView("uploadcrdata");
		
	}
	

	@RequestMapping(value = { "/uploadCRInfo.spring" }, method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request,
	        HttpServletResponse response,UploadCRForm uploadCRForm,@RequestParam("file") MultipartFile file) {

		   lindeLogMgr.logMessage("INFO","onSubmit");
			HttpSession userSession = request.getSession(false);
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
      
           String columns[] = {"Change ID", "Brief Description", "Assignee", "Conti Assigned to", "Phase", "Priority", "Open Date", "Requested Date","Estimate Effort","Affected CIs"}; // "Planned Start", "Planned End", 
           List list=null;
           String status = "";
     	    String errormessage = "";
		try {
			list = getCSVBeans(uploadedFile.toString(),"com.techm.trackingtool.admin.bean.CRinfo",columns);
	
            
  	      for(int i=0;i<list.size();i++)
	      {   
	      CRinfo add= (CRinfo)list.get(i);
	      lindeLogMgr.logMessage("DEBUG", "add.getChangeId"+add.getChangeId());
	      lindeLogMgr.logMessage("DEBUG", "add.getAssignee"+add.getAssignee());
	      lindeLogMgr.logMessage("DEBUG", "add.getBrief_desc"+add.getBriefDescription());
	      
	      }
  	      status = crinfoService.uploadInfo(list,user);
  	  //  System.out.println("Upload Status:"+messageSource.getMessage("msg.success", null,null));   
  	    }
  	    catch(Exception e)
  	    {
          errormessage = e.getMessage(); 
          lindeLogMgr.logStackTrace("ERROR", errormessage, e);
          return new ModelAndView("uploadcrdata", "message","Error in input");
  	    }

          return new ModelAndView("uploadcrdata", "message",messageSource.getMessage("msg.success", null,null));
     
	}
	
	public static List getCSVBeans(String  fileName,String beanName,String []columns) throws Exception
	{
		lindeLogMgr.logMessage("INFO","Inside the method"+fileName);
		 Class obj = Class.forName(beanName);
		
	      CsvToBean csv = new CsvToBean();
	    //  List list = csv.parse(obj,new FileReader(fileName),columns);
	      
	      List list = csv.crdataparse(obj,new FileReader(fileName),columns);
	      
	     // System.out.println("List:"+list.size()); 
	      return list;
	}

}
