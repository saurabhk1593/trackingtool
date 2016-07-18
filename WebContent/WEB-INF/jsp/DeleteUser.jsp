<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.techm.trackingtool.util.LindeMap"%>
<%@page import="com.techm.trackingtool.admin.bean.Application"%>
<%@page import="com.techm.trackingtool.util.LindeList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Delete User/Application</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>
<script>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>


function checkType()
{

var selType = document.getElementById("category").value;
if(selType == 'username'){
var userInfo=document.getElementById("username");
userInfo.style.display = "";
var userInfo=document.getElementById("username");
userInfo.style.visibility = "visible";
var userInfo=document.getElementById("applicationname");
userInfo.style.visibility = "hidden";
}
else if(selType == 'applicationid')
{
var userInfo=document.getElementById("username");
userInfo.style.display = "none";
var appInfo=document.getElementById("applicationname");
appInfo.style.visibility = "visible";
}
}

function checkTicketInfo()
{
var associateid=document.forms[0].username.value;
//var body = document.getElementById("deleteUser");
var body = document.getElementById("deleteUser");
var status1= document.getElementById(associateid);
status=status1.value;

return status;

}


 function ValidateInfo() {
         
         var selType = document.getElementById("category").value;
		if(selType == 'username'){
		if(document.forms[0].username.value=='' || document.forms[0].username.value==null || document.forms[0].username.value=='0')
         {
         alert("User Name is empty");
         document.forms[0].elements["username"].focus();
         return false;
         }
         if(checkTicketInfo()=='NO')
         {
         alert("User cannot be deleted as he/she is mapped to CRs/Incidents");
         document.forms[0].elements["username"].focus();
         return false;
         }
    		else
    		{
    		//alert("Hi");
    		document.forms[0].submit();
    		}
    	}
         
		else if(selType == 'applicationid')
		{
		if(document.forms[0].application.value=='' || document.forms[0].application.value==null||document.forms[0].application.value=='0')
         {
         alert("Module is empty");
         document.forms[0].elements["application"].focus();
         return false;
         }
         
    		else
    		{
    		document.forms[0].submit();
    		}
	
		}
    }
     
   function homePageReport()
{
document.forms[0].action="./home.spring";
document.forms[0].submit();	
}

</script>
</head>

<body id="deleteUser">

<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
User user=(User)session.getAttribute("userInformation");
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

%>

<form method="post" name="DeleteUserForm" action="?task=deleteUser" >
<c:set var="applicationlist" value="${model.applicationlist}" />
<c:set var="userlist" value="${model.userlist}" />

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
<tr>
<td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
      <tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
        <td width="85%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="10%" rowspan="2"><div align="center"><img src="images/satyam.gif" alt="Satyam" width="50" height="50" /></div></td>
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
             <a href="JavaScript:homePageReport();"
							class="linknormal">Home</a> | <a href="./home.spring"
							class="linknormal">Change View</a> | <a href="submitFeedback.spring"
							class="linknormal">Feedback</a>            
            </div></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table></td>
</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">


<tr>
<td>	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>

<td width="98%" valign="top">

<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Delete User/Module</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Delete User/Module</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="20%" align="left">Select Deletion Category</td>
                    <td align="left" class="padding_left10_right3" width="20%">
                    
                    <select name="category" id="category" class="dropdown" onchange="checkType();" >
                      <option>---------Select---------</option>
                      <option value="username">User</option>
					  <option value="applicationid">Module</option>
                    </select>
                    
                    </td>
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	
				
					<div style="visibility:hidden" id="username">
						<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Delete User</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="20%" align="left">Select User</td>
                    <td align="left" class="padding_left10_right3" width="20%">
                    
                        <select name="username" class="dropdown" >
							<option value="0">---------Select---------</option>
							<c:forEach var="userlist" items="${model.userlist}">
                      		
                      		<option label="${userlist.userDetails.user.lastName} ${userlist.userDetails.user.firstName}" 
                      		value="${userlist.userDetails.user.userID}">
                      		${userlist.userDetails.user.lastName} ${userlist.userDetails.user.firstName}</option>
							
							</c:forEach>
							
						</select>
							<c:forEach var="userlist" items="${model.userlist}">
							<input type="hidden" id="${userlist.userDetails.user.userID}" name="${userlist.userDetails.user.userID}" value="${userlist.userDetails.deletionStatus}"/>
							</c:forEach>	
							
	
                    </td>
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					              <tr>
                <td align="center" valign="top" colspan="4">
                  <input name="Button" type="button" class="redbutton1"  value="Delete" onclick="ValidateInfo();"/>
              <input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
              </tr>
			  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>	
</table>
					</div>
					
					<div style="visibility:hidden" id="applicationname">
						<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Delete Module</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="20%" align="left">Select Module</td>
                    <td align="left" class="padding_left10_right3" width="20%">
                    <select name="application" class="dropdown">
							<option value="0">---------Select---------</option>
							
							<c:forEach var="applicationList" items="${model.applicationlist}">
							
                      		<option label="${applicationList.modulename}" value="${applicationList.moduleid}">${applicationList.modulename}</option>
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
                <td align="center" valign="top" colspan="4">
                
                  <input name="Button" type="button" class="redbutton1"  value="Delete" onclick="ValidateInfo();"/>
				<input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
	              </tr>
			  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>	
</table>
					</div>



</tr>

</table>
</td>


<td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
</tr>
</table>
</td>
</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">




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

</table></table></table>${model.deletionStatus}
<input type="hidden" name="deletionStatus" value="${model.deletionStatus}"/>
</form>
</body><%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
