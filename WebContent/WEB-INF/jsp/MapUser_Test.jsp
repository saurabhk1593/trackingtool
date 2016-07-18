<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.techm.trackingtool.util.LindeMap"%>
<%@page import="com.techm.trackingtool.admin.bean.Application"%>
<%@page import="java.util.Date"%>

<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<title>Map User to Application</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />

<script language="javascript" src="js/window_opener.js"></script>


<script type="text/javascript">
   
    
    function selectUserID()
    {
    	
    	var optsLeft = document.forms[0].userId.options;
    	
    	for(var i=1; i<optsLeft.length;i++)
    	{
   			if(optsLeft[i].value!='0')
   			{
    		if(optsLeft[i].selected)
    			{
		    	var tempLeft=optsLeft[i];
    			var optsRight = document.forms[0].userid.options;
	    		if(optsRight.length==1)
	    		{
	    		document.forms[0].userid.appendChild(tempLeft);
    			document.forms[0].userId.removeChild(tempLeft);
	    		}
    			else
    			{
    			var rightIndex=0;
    			for(var j=0; j<optsRight.length;j++)
	    		{
				var tempRight=optsRight[j];
    			
	    		if(tempRight.label!='0')
	    		{
	    		if(tempLeft.label>tempRight.label)
	   			{
	   			rightIndex++;
    	    	}
    	    	}
    	    	}
    			if(rightIndex+1==optsRight.length){
    	    	alert("rightIndex equals rights opts length and rightIndex is: "+rightIndex);
    	    	document.forms[0].userid.appendChild(tempLeft);
	    		document.forms[0].userId.removeChild(tempLeft);
	    		}
	    		else
	    		{
	    		document.forms[0].userid.insertBefore(tempLeft,document.forms[0].userid.options[rightIndex]);	
	    		document.forms[0].userId.removeChild(tempLeft);
	    		}
	    		
    			}
    	
    	}
    	}
    }
    }
    
    
    
    function deSelectUserID()
    {
    	var optsRight = document.forms[0].userid.options;
    	for(var i=0; i<optsRight.length;i++)
    	{
   			if(optsRight[i].label!='0')
   			{
	    	if(optsRight[i].selected)
	    	{
	    	tempRight=optsRight[i];
	    	var optsLeft = document.forms[0].userId.options;
	    	if(optsLeft.length==1)
	    		{
	    		document.forms[0].userId.appendChild(tempRight);
    			document.forms[0].userid.removeChild(tempRight);
	    		}
	    		
	    	else
	    	{	
	    	var leftIndex=-1;
    		for(var j=0; j<optsLeft.length;j++)
	    	{
			var tempLeft=optsLeft[j];
	    	if(tempRight.label<tempLeft.label)
	   			{
	   			leftIndex++;
	   			}
    		}
    		if(leftIndex+1==optsRight.length){
    	    	alert("left Index equals rights opts length and rightIndex is: "+leftIndex);
    	    	document.forms[0].userId.appendChild(tempRight);
	    		document.forms[0].userid.removeChild(tempRight);
	    		}
	    		else
	    		{
	    		document.forms[0].userId.insertBefore(tempRight,document.forms[0].userId.options[leftIndex]);	
	    		document.forms[0].userid.removeChild(tempRight);}
    		}
	    	}
    	}
    }
    }
    
    
     function saveData() {
                 alert("hi");
        //         document.forms[0].action="/lindetool/mapUser.spring?task=submitUserMapping";
     	//document.forms[0].submit();
    
    }
    
     function cancelData() {
                 document.forms[0].action="/lindetool/login.spring";
     	document.forms[0].submit();
    
    }
    
    
    function selectUserDetails()
    {
        document.forms[0].action="/lindetool/mapUser.spring?task=getUserInfo";
     	document.forms[0].submit();
    
    }
    
    
   
    function ValidateInfo() {
         
         var opts = document.forms[0].userid.options;
         var tempcount=0;
         var usercount=opts.length-1;
         
        for(var i=0; i<opts.length;i++){
   		if(opts[i].value!='0'){
    	if(opts[i].selected)
    	{
    	 	tempcount++;
    	}
    	}
    	}
    	
    	 if(document.forms[0].applicationid.value=='' || document.forms[0].applicationid.value==null||document.forms[0].applicationid.value=='0')
         {
         alert("Please Select the Application");
         document.forms[0].elements["applicationid"].focus();
         return false;
         }
         
         else if(document.forms[0].userid.value=='' || document.forms[0].userid.value==null)
         {
         alert("Please Select the User Name");
         document.forms[0].elements["userid"].focus();
         return false;
         }
    	 
    	 else if(usercount>tempcount)
    	 {
    	 alert("Please Select All the Users from the Right Side");
    	 }
    
    	 else
    	 {
    	 document.forms[0].submit();
    	 }
    }
     
    </script>
    
    
</head>

<body>
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
User user=(User)session.getAttribute("userInformation");
//LindeMap model=(LindeMap)request.getAttribute("model");
//Integer applicationid=(Integer)model.get("appid");
//int appid=applicationid.intValue();
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

%>
<form method="post" name="MapUserForm" action="?task=submitUserMapping">
<c:set var="applicationList" value="${model.applicationList}" />
<c:set var="userList" value="${model.userList}" />


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
<tr>
<td valign="top">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
    <tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" />
        </td>
        <td width="85%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="10%" rowspan="2">
            <div align="center"><img src="images/satyam.gif" alt="Satyam" width="50" height="50" />
            </div></td>
            <td width="60%" id="banner_grey"></td>
            <td width="30%" valign="top"></td>
          </tr>
          <tr>
            <td id="right_logo"><div align="center"></div></td>
			<td height="44">&nbsp;</td>
          </tr>
		  <tr>
             <td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
          <tr>
		    <td width="33%" valign="middle" class="welcome_txt">Welcome, <%=user.getFirstName()%></td>
            <td width="33%" align="center" valign="middle" class="welcome_txt"><%=currentDate%></td>
            <td width="34%" valign="middle" class="welcome_txt" align="right"><div align="right"><img src="images/spacer.gif" alt="spacer" width="1" height="10" />
            <a href="/lindetool/login.spring" class="linknormal">Home</a> | <a href="submitFeedback.spring" class="linknormal">Feedback</a> </div></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table></td>
</tr>


<tr>
<td>	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>


<td width="98%" valign="top">
<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home
</span><span class="bodytxt_grey">&gt;&gt; Map Application Owners</span></td>
</tr>
<tr><td>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Map Owners to Application</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="25%" align="left">Application Name</td>
         <%//LindeMap lindeMap=(LindeMap)request.getAttribute("model");
         		//LindeMap userMap=(LindeMap)lindeMap.get("applicationMap");	   
         		//Application app=(Application)userMap.get(1);
         		
         	//System.out.println(" in jsp mapuser.page "+" useerMap "+userMap+" "+userMap.get(1));
         	 // System.out.println("app name "+app.getApplicationname());
         	  
       String a="4";  %>
         
           
         
         <td align="left" class="padding_left10_right3" width="20%">
         
         <select name="applicationid" class="dropdown" onchange="selectUserDetails();" >
							<option value="0">--------------------Select Application Name-------------------</option>
							
							<c:forEach var="applicationList" items="${model.applicationList}">
							<%String sel="";%>
							<c:if test="${applicationList.applicationid ==model.appid}">
							<%sel="selected";%>		
							</c:if>
                      		
                      		<option label="${applicationList.applicationname}" value="${applicationList.applicationid}" <%=sel%>></option>
                      		
                      		</c:forEach>
							</select>	
							


							
                    </td>
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>

              
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					
                  <tr>
                    <td height="60" width="25%" align="left" valign="top" class="bodytxt_black">Application Owners</td>
                    <td align="left" valign="middle" class="padding_left10_right3" width="55%">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="35%" valign="top">
                        
                      
                        <select name="userId" multiple="multiple" class="dropdown" size="8" >
							<option value="0">---------Select---------</option>
							<c:forEach var="userList" items="${model.userList}">
                      		<option label="${userList.firstName}" value="${userList.userID}" ></option>
							</c:forEach>
						</select>	
							
					              
                                  
                        <td width="30%" align="center" valign="middle">
                        <input name="Button2" type="button" class="redbutton1" value="Select &gt;&gt;" onclick="selectUserID();"/>
                        <input name="Button2" type="button" class="redbutton1" value="&lt;&lt; Remove" onclick="deSelectUserID();"/></td>
							<td>&nbsp;</td>
                        <td width="35%" valign="top">
                        <select name="userid" size="8" multiple="multiple" class="dropdown" >
                                              <option value="0">---------Select---------</option>
                                              <c:forEach var="usersList" items="${model.usersList}">
                      		<option label="${usersList.firstName}" value="${usersList.userID}" selected="selected"></option>
							</c:forEach>
                                              </select></td>
                      </tr>
                    </table>                      </td>
                  </tr>	
				  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>	
					
								
              <tr>
                <td align="center" valign="top" colspan="4">
                
                <input name="submitUserMapping" type="button" class="redbutton1" value="Save" onclick="ValidateInfo();"/>
                <input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="cancelData();" />
                </td>
              </tr>	
			  				  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>	
							
</table></td>
</tr>

</table>
</td>


<td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
</tr>
</table>
</td>
</tr>




<tr>
<td>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
		        <td width="85%"><table width="100%" height="130" cellpadding="0" cellspacing="0">
				<tr>             <td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				<tr><td height="91" align="center">
			</td>
				</tr>
				<tr><td colspan="3" id="banner_grey" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				</table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
</tr>
</table>
</td>
</tr>

</table>
</form></body><%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
