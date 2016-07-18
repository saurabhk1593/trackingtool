<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<%@page import="java.util.Date"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Add Application</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>






<script type="text/javascript">
    function ValidateInfo() {
         
         if(document.forms[0].modulename.value=='' || document.forms[0].modulename.value==null)
         {
         alert("Please enter the Module Name");
         document.forms[0].elements["modulename"].focus();
         return false;
         }
         else if(document.forms[0].description.value=='' || document.forms[0].description.value==null)
         {
         alert("Please enter Description");
         document.forms[0].elements["description"].focus();
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
<form method="post" name="ModuleForm" action="./addModule.spring" modelAttribute="moduleForm">
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
							class="linknormal">Home</a> | <a href="home.spring"
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
<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Add Module</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Add Module</td>
</tr>
<tr>
<td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>

<tr>
<td class=bodytxt_black width="5%" align="left">Module Name<font color="red">&nbsp *</font></td>
<td align="left" width="20%"><input type="text" width="10" name="modulename" value="${moduleForm.modulename}"/></td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
</tr>
<tr>
<td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>
					

<tr>
<td class=bodytxt_black width="25%" align="left">Priority</td>
                    <td align="left" width="20%"><select name="priority" class="dropdown" >
                       <option value="0">-----------Select------------</option>
                      <option value="1" ${moduleForm.priority== '1' ? 'selected' : ''}>Priority1</option>
					  <option value="2" ${moduleForm.priority== '2' ? 'selected' : ''}>Priority2</option>
					  <option value="3" ${moduleForm.priority== '3' ? 'selected' : ''}>Priority3</option>
					  <option value="4" ${moduleForm.priority== '4' ? 'selected' : ''}>Priority4</option>
                    </select></td>

</tr>              
<tr>
<td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
 </tr>

	

<tr>
<td class=bodytxt_black width="25%" align="left">Description<font color="red">&nbsp *</font></td>
<td align="left" width="40%" colspan="2"><input type="text" name="description" value="${moduleForm.description}"/></td>
<td class=bodytxt_black width="25%" align="left">&nbsp</td>
</tr>

<tr>
<td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>

			
<tr>
<td class=bodytxt_black width="5%" align="left">CR Volume/Month</td>
<td align="left" width="20%"><input type="text" width="10" name="ticketspermonth" value="${moduleForm.crspermonth}"/></td>
</tr>

<tr>
<td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
</tr>	

<tr>
<td class=bodytxt_black width="5%" align="left">Documentation Availability</td>
<td align="left" width="20%"><input type="text" width="10" name="docavailability" value="${moduleForm.docavailability}"/></td>

</tr>

<tr>
<td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
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
</body>
</html>
