<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Configure Email Settings</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
function checkType()
{

var selType = document.getElementById("severity").value;

var userInfo=document.getElementById("1");
userInfo.style.display = "";
var userInfo=document.getElementById("1");
userInfo.style.visibility = "visible";



document.forms[0].method="post";
if(selType == '4 - Low')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Priority 4";
	document.forms[0].action="?task=getEmailSetting&severity=4 - Low";
	document.forms[0].submit();
}

if(selType == '3 - Medium')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Priority 3";
	document.forms[0].action="?task=getEmailSetting&severity=3 - Medium";
	document.forms[0].submit();
}
if(selType == '2 - High')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Priority 2";
	document.forms[0].action="?task=getEmailSetting&severity=2 - High";
	document.forms[0].submit();
}
if(selType == '1 - Critical')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Priority 1";
	document.forms[0].action="?task=getEmailSetting&severity=1 - Critical";
	document.forms[0].submit();
}


}

function ValidateInfo() {
         
         
		
		if(document.forms[0].severity.value=='' || document.forms[0].severity.value==null || document.forms[0].severity.value=='0')
         {
         alert("Please select the Priority");
         document.forms[0].elements["severity"].focus();
         return false;
         }
         
    		else
    		{
    		document.forms[0].submit();
    		}
    	
         
		
    }
     



 function homePageReport()
{

document.forms[0].action="./home.spring";
document.forms[0].submit();	
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
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<form method="post" name="EmailFrequencyForm" action="configEmailFrequency.spring?task=configureEmail" >
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
							class="linknormal">Home</a> | <a href="login.spring"
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

<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Configure E-mail settings</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Configure E-mail settings</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="20%" align="left">Select Priority</td>
                    <td align="left" class="padding_left10_right3" width="20%">
                    
                    <%String sev1="";
                    String sev2="";
                    String sev3="";
                    String sev4="";
                   %>
                    <select name="severity" id="severity" class="dropdown" onchange="checkType();" >
                    <c:if test="${model.severity=='1 - Critical'}">
                    <%sev1="selected"; %>
                    </c:if>
                    
                    <c:if test="${model.severity=='2 - High'}">
                    <%sev2="selected"; %>
                    </c:if>
                    
                    <c:if test="${model.severity=='3 - Medium'}">
                    <%sev3="selected"; %>
                    </c:if>
                    
                    <c:if test="${model.severity=='4 - Low'}">
                    <%sev4="selected"; %>
                    </c:if>
                    
                    
                      <option value="0">---------Select---------</option>
                      <option value="1 - Critical" <%=sev1%>>Priority 1</option>
					  <option value="2 - High" <%=sev2%>>Priority 2</option>
					  <option value="3 - Medium" <%=sev3%>>Priority 3</option>
					  <option value="4 - Low" <%=sev4%>>Priority 4</option>
					
                    </select></td>
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr><td>&nbsp;</td></tr>



	
			 <%
	String monday_email="";
	String tuesday_email="";
	String wednesday_email="";
	String thursday_email="";
	String friday_email="";
	
	String pm_cc="";
	String tm_cc="";
	String pgm_cc="";
	
	
	%>		
	
	
	<c:if test="${model.emailFrequency.monday_email =='Y'}">
     <%
     monday_email="checked";%>
    </c:if>   
    <c:if test="${model.emailFrequency.tuesday_email =='Y'}">
     <%
     tuesday_email="checked";%>
    </c:if> 
    <c:if test="${model.emailFrequency.wednesday_email =='Y'}">
     <%
     wednesday_email="checked";%>
    </c:if> 
    <c:if test="${model.emailFrequency.thursday_email =='Y'}">
     <%
     thursday_email="checked";%>
    </c:if> 
    <c:if test="${model.emailFrequency.friday_email =='Y'}">
     <%
     friday_email="checked";%>
    </c:if> 
     
    <c:if test="${model.emailFrequency.pm_cc =='Y'}">
     <%
     pm_cc="checked";%>
    </c:if> 
    <c:if test="${model.emailFrequency.tm_cc =='Y'}">
     <%
     tm_cc="checked";%>
    </c:if> 
    <c:if test="${model.emailFrequency.pgm_cc =='Y'}">
     <%
     pgm_cc="checked";%>
    </c:if> 			
					<div id="1" style="visibility">
					<table width="80%" align="center" class="boxtable_greyborder_padding" cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="12">E-mail Attributes</td>
</tr>
                  <tr>
                    <td height="10" colspan="12">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="8%" align="left"><label id="sevType">${model.severity}</label></td>
                    <td align="left" class="bodytxt_black" width="10%">Email Frequency</td>
                    <td width="3%" height="25" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="frequency1" value="Y" <%=monday_email%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Monday</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="frequency2" value="Y" <%=tuesday_email%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Tuesday</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="frequency3" value="Y" <%=wednesday_email%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Wednesday</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="frequency4" value="Y" <%=thursday_email%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Thursday</td>
					<td width="3%" align="left" class="padding_left10_right3">
					<input type="checkbox" name="frequency5" value="Y" <%=friday_email%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Friday</td>

</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					
					<tr>
<td class=bodytxt_black width="8%" align="left"><label id="appName">&nbsp;</td>
                    <td align="left" class="bodytxt_black" width="10%">Email CC List</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="cclist1" value="Y"  <%=tm_cc%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Team Member</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="cclist2" value="Y" <%=pm_cc%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Project Manager</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="cclist3" value="Y" <%=pgm_cc%>/></td>
                    <td width="8%" align="left" class="bodytxt_black">Program Manager</td>
<tr>
                    <td height="10" colspan="2">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					              <tr>
                <td align="center" valign="top" colspan="12">
                <input name="Button" type="button" class="redbutton1" value="Update" onclick="ValidateInfo();"/>
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
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="30%" />
        </td>
		        <td width="85%"><table width="100%" height="130" cellpadding="0" cellspacing="0">
				<tr>             <td colspan="3" id="double_line" >
				<img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				<tr><td height="91" align="center">
			</td>
				</tr>
				<tr><td colspan="3" id="banner_grey" >
				<img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				</table></td>
       <td width="1%" align="left" class="rightborder">
       <img src="images/spacer.gif" width="15" height="30%" /></td>
</tr>
</table>
</td>
</tr>

</table>

</body>
<%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
