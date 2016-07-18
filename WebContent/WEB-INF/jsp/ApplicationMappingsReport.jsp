<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Application Mappings Report</title>
       <link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script>

function checkType()
{

var selType = document.getElementById("category").value;
if(selType == 'user'){

var userInfo=document.getElementById("user");
userInfo.style.display = "";
userInfo.style.visibility = "visible";
var userInfo=document.getElementById("application");
userInfo.style.display = "none";
}
else if(selType == 'App')
{
var userInfo=document.getElementById("user");
userInfo.style.display = "none";
var appInfo=document.getElementById("application");
appInfo.style.display = "";
appInfo.style.visibility = "visible";
}

}

function tt(){
var userInfo=document.getElementById("user");
userInfo.style.display = "none";
var appInfo=document.getElementById("application");
appInfo.style.display = "none";
}

function homePageReport()
{

document.forms[0].action="home.spring";
document.forms[0].submit();	
}
</script>
</head>

<body >
<c:set var="applicationList" value="${model.applicationList}" />
<c:set var="userList" value="${model.userList}" />
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
<form method="post" name="ApplicationMappingsForm" >
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
            <td width="34%" valign="middle" class="welcome_txt" align="right">
            <div align="right"><img src="images/spacer.gif" alt="spacer" width="1" height="10" />
            <a href="JavaScript:homePageReport();"
							class="linknormal">Home</a> | <a href="home.spring"
							class="linknormal">Change View</a> | <a href="submitFeedback.spring"
							class="linknormal">Feedback</a>
            </div></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table>
        </td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table></td>
</tr>



<tr>
<td>	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>


<td width="98%" valign="top">
<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Module Mappings Report</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Module Mappings Report</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>

<td class=bodytxt_black width="20%" align="left">Select Category</td>
                    <td align="left" class="padding_left10_right3" width="20%">
                    <select name="category" id="category" class="dropdown" onchange="checkType();" >
                      <option>---------Select---------</option>
                      <option value="user">User</option>
					  <option value="App">Module</option>
                    </select></td>
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	
				
					<div style="visibility:hidden" id="user">
						<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">User wise Report</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td>
<table width="95%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder width="30%">User Name</td>
<td class=headerboldtxt_greybg_greyborder>Module(s) Assigned</td>

</tr>

<c:forEach var="userList" items="${model.userlist}">
							
    <tr>
<td class=bgwhite_bottombordergrey width="30%">${userList.userBean.firstName}</td>
<td class=bgwhite_bottombordergrey width="30%">
<c:choose>
									
<c:when test="${!empty userList.applicationName}">
${userList.applicationName}
</c:when>
<c:otherwise>&nbsp</c:otherwise>
</c:choose></td>
</tr>

    
                     		
</c:forEach>
                      		
                      		
                      		
</table>
</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
	
</table>
					</div>
					
					<div style="visibility:hidden" id="application" >
						<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Module wise Report</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr><td>
<table width="95%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder width="30%">Module Name</td>
<td class=headerboldtxt_greybg_greyborder>Mapped Owners</td>
</tr>



<c:forEach var="applicationList" items="${model.applicationlist}">
							
    <tr>
<td class="bgwhite_bottombordergrey" width="30%">${applicationList.applicationBean.modulename}</td>

<td class=bgwhite_bottombordergrey width="30%">
<c:choose>
									
<c:when test="${!empty applicationList.userName}">
${applicationList.userName}</c:when>
<c:otherwise>&nbsp</c:otherwise>
</c:choose>
</td>
</tr>

    
                      		
</c:forEach>



</table></td>
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
				<tr><td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
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

</table></table>
</form>
</body>
<%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%></html>
