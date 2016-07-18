<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Add User</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>






<script type="text/javascript">
    function ValidateInfo() {
         
         if(document.forms[0].userid.value=='' || document.forms[0].userid.value==null)
         {
         alert("Please enter User ID");
         document.forms[0].elements["userid"].focus();
         return false;
         }
         else if(document.forms[0].firstname.value=='' || document.forms[0].firstname.value==null)
         {
         alert("Please enter First Name");
         document.forms[0].elements["firstname"].focus();
         return false;
         }
    		
    		
    	     		
    		
    	 else if(document.forms[0].mailid1.value=='' || document.forms[0].mailid1.value==null)
         {
         alert("Please enter Mail ID");
         document.forms[0].elements["mailid1"].focus();
         return false;
         }
    	
    	else if(document.forms[0].role.value=='' || document.forms[0].role.value==null|| document.forms[0].role.value=='0')
         {
         alert("Please select Role");
         document.forms[0].elements["role"].focus();
         return false;
         }
    		else
    		{
    		var emailid=document.forms[0].mailid1.value;
    		//alert(emailid);
    		var status=echeck(emailid);
    		var mailid=document.forms[0].mailid1.value;
    		//alert(mailid);
    		document.forms[0].mailid.value=mailid+'@satyam.com';
    		if(status){
    		document.forms[0].submit();
    		}
    		}
    }
     function echeck(str) {

		var at="@";
		var dot=".";
		var under="_";
		var lat=str.indexOf(under);
		var lstr=str.length;
	
		if (str.indexOf(under)==-1 || str.indexOf(under)==0 || str.indexOf(under)==lstr){
		   alert("Please enter Valid E-mail ID");
		   document.forms[0].elements["mailid1"].focus();
		   return false;
		}

		 if (str.indexOf(under,(lat+1))!=-1){
		    alert("Please enter Valid E-mail ID");
		    document.forms[0].elements["mailid1"].focus();
		    return false;
		 }

		 if (str.indexOf(dot)!=-1 || str.indexOf(at)!=-1){
		    alert("Please enter Valid E-mail ID");
		    document.forms[0].elements["mailid1"].focus();
		    return false;
		}
		/*
		if (str.indexOf(at)==-1){
		   alert("Please enter Valid E-mail ID");
		   return false;
		}
		
		if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Please enter Valid E-mail ID");
		    document.forms[0].elements["mailid1"].focus();
		    return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Please enter Valid E-mail ID");
		    document.forms[0].elements["mailid1"].focus();
		    return false;
		 }*/
		
		 if (str.indexOf(" ")!=-1){
		    alert("Please enter Valid E-mail ID");
		    document.forms[0].elements["mailid1"].focus();
		    return false;
		 }

 		 return true;			
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
<form method="post" name="AddUserToolForm" action="?task=addUserTool">
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">


<tr>
<td>	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>

<td width="98%" valign="top">
<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home</span><span class="bodytxt_grey">&gt;&gt; Add Internal Task</span></td></tr>
<tr>
<table width="90%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="5">Add Internal Task</td>
</tr>
<tr>
<td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>

<tr>
<%int taskno=0;%>
<td align="left" width="20%">
<table ><tr align=left>
  <td width="3%" align="left" class="padding_left10_right3">
<input type="checkbox" name="task_<%=user.getUserID()%>_<%=taskno%>" value="Y" /></td>

<td align="right" class=bodytxt_black><input type="text" size="20" name="activity_<%=user.getUserID()%>_<%=taskno%>"/></td></tr></table>
</td>
<td align="left" width="20%">
<table ><tr align=left>
<td align="right" class=bodytxt_black><input type="text" size="20" name="description_<%=user.getUserID()%>_<%=taskno%>"/></td></tr></table>
</td>
<td align="left" width="20%">
<table ><tr align=left>
<td align="right" class=bodytxt_black><input type="text" size="20" name="effort_<%=user.getUserID()%>_<%=taskno%>"/></td></tr></table>
</td>
<td align="left" width="20%">
<table ><tr align=left>
<td align="right" class=bodytxt_black><input type="text" size="20" name="date_<%=user.getUserID()%>_<%=taskno%>"/></td></tr></table>
</td>
</tr>
<tr>
<td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>

				
								
              <tr>
                <td align="center" valign="top" colspan="4">
                
                <input name="Button" type="button" class="redbutton1"  value="Save" onclick="ValidateInfo();"/>
				<input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
              </tr>	
			  				  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                   </tr>	
							
</table>
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

</td></tr>
</table>
<input type="hidden" name="mailid" value=""/>
</body>
</html>
