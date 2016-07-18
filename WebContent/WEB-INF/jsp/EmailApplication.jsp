<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Application E-mail settings</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<script language="javascript" src="js/window_opener.js"></script>
<%String appname="";
String owner="";
String tl="";
String pl="";
String pm="";

%>
<script>
function checkType()
{

var selType = document.forms[0].applicationid.value;
//alert("Hi"+document.forms[0].applicationid.value);
var userInfo=document.getElementById("1");
userInfo.style.display = "";
var userInfo=document.getElementById("1");
userInfo.style.visibility = "visible";


}


function ValidateInfo() {
         
         
		
		if(document.forms[0].applicationid.value=='' || document.forms[0].applicationid.value==null || document.forms[0].applicationid.value=='0')
         {
         alert("Please select Application");
         document.forms[0].elements["applicationid"].focus();
         return false;
         }
         
    		else
    		{
    		document.forms[0].submit();
    		}
    	
         
		
    }
   function homePageReport()
{

document.forms[0].action="archivedData.spring?task=homePageReportCurrentSelection";
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
User user=(User)session.getAttribute("userInformation");%>
<form method="post" name="EmailApplicationForm" action="?task=configureEmailApplication">
<c:set var="applicationList" value="${model.applicationList}" />



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

<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home</span><span class="bodytxt_grey">&gt;&gt; Application E-mail settings</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Application E-mail settings</td>
</tr>
                  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="20%" align="left">Select Application</td>
                    <td align="left" class="padding_left10_right3" width="20%">
               <select name="applicationid" class="dropdown" onchange="checkType()">
							<option value="0">---------Select---------</option>
							
							<c:forEach var="applicationList" items="${model.applicationList}">
							
                      		<option label="${applicationList.applicationname}" value="${applicationList.applicationid}"  ></option>
                      		
                      		</c:forEach>
							</select>
                    </td>
                    
					<td class=bodytxt_black width="25%" align="left">&nbsp;
					
					</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	
				
					<div style="visibility:hidden" id="1">
						<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="12">Application E-mail settings</td>
</tr>
                  <tr>
                    <td height="10" colspan="12"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
<tr>
<td class=bodytxt_black width="8%" align="left"><label id="appname">${model.applicationname}</label></td>
                    <td align="left" class="bodytxt_black" width="10%">Email CC List :</td>
                    <td width="3%" height="25" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="owner" value="Y" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Application Owners</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="tl" value="Y"  /></td>
                    <td width="8%" align="left" class="bodytxt_black">Team Lead</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="pl" value="Y" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Project Lead</td>
					                    <td width="3%" align="left" class="padding_left10_right3">
					                    <input type="checkbox" name="pm" value="Y" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Project Manager</td>

</tr>
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
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

</body><%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
