<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Upload Ticket Details</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>



<script type="text/javascript">
    function ValidateInfo() {
         
         if(document.uploadTTForm.file.value=='')
         {
         alert("Please select the file for uploading");
        document.forms[0].elements["file"].focus();
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
<form method="post" enctype="multipart/form-data" name="uploadTTForm">
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
User user=(User)session.getAttribute("userInformation");%>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
<tr><td><table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
      <tr>
        <td width="1%" align="left" class="leftborder">
        <img src="images/spacer.gif" width="15" height="6" />
        </td>
        <td width="85%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
						<td width="10%" rowspan="2">
						<div align="center"><img src="images/satyam.gif"
							alt="Satyam" width="50" height="50" /></div>
						</td>
						<td width="60%" id="banner_grey"></td>
						<td width="30%" valign="top"></td>
					</tr>
					<tr>
						<td id="right_logo">
						<div align="center"></div>
						</td>
						<td height="44">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" id="double_line"><img
							src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
					</tr>
          <tr>
		    <td width="33%" valign="middle" class="welcome_txt">Welcome, <%=user.getFirstName()%></td>
            <td width="33%" align="center" valign="middle" class="welcome_txt"><%=currentDate%></td>
            <td width="34%" valign="middle" class="welcome_txt" align="right"><div align="right">
            <img src="images/spacer.gif" alt="spacer" width="1" height="10" />
             <a href="JavaScript:homePageReport();"
							class="linknormal">Home</a> | <a href="login.spring"
							class="linknormal">Change View</a> | <a href="submitFeedback.spring"
							class="linknormal">Feedback</a>
            </div></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line">
            <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" />
       </td>
      </tr>
    </table></td>
</tr>
<tr>
<td>	
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
<tr>
<td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
<td width="98%" valign="top">

<table width="100%" border="0" cellspacing="0" cellpadding="0">

              <tr>
                <td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home</span>
                <span class="bodytxt_grey">&gt;&gt; Upload TT Information</span></td>
              </tr>
              
              <tr>
                <td valign="top" class="pageheadertxt_maroon" align="center">Upload TT Information</td>
                </tr>
              <tr>
                <td height="10"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
              </tr>
			                <tr>
                <td height="10"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
              </tr>
              
              <tr>
             
                <td align="center" valign="middle">
                   <input name="file" id="file" type="file"/>
                </td>
              </tr>
              
              <tr>
                <td><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
              </tr>
              
              <tr>
                <td align="center" valign="top">
			<input name="submitFile" type="button" class="redbutton1" value="Upload" onclick="ValidateInfo();"/>
            <input name="cancelUpload" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
                  </td>
              </tr>
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<tr><td>
<table width="95%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">

<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4" width="25%"><b>Please read the below instructions before Uploading the file:</b></td>
<tr><td>&nbsp</td></tr>
<tr>
<td class=bgwhite_bottombordergrey width="25%" valign="middle">1) Please <font color="red">Upload CSV file only</font>.
<br>&nbsp
</td>

</tr>
<tr><td>&nbsp</td></tr>
<tr>
<td class=bgwhite_bottombordergrey width="25%" valign="middle">
2) Data in the CSV file should be in the following order:<br><br> 
&nbsp&nbsp&nbsp&nbspCaseType - Description - Item - CaseID - Assigned - CreateDate - Status - 
Resolved - Individual - Severity - Region - Group Name
<br>&nbsp</td>
</tr>
<tr><td>&nbsp</td></tr>
<tr>
<td class=bgwhite_bottombordergrey width="25%" valign="middle">3) The Columns - <font color="red">Assigned, Create Date and Resolved Date</font> should be date fields in the format <font color="red">mm/dd/yyyy hh:mi:ss</font>
<br>&nbsp</td>
</tr>
<tr><td>&nbsp</td></tr>
<tr>
<td class=bgwhite_bottombordergrey width="25%" valign="middle">4) The data in the Columns - Case Type, Description, CaseID, Group should not be blank. 
<br>&nbsp</td>
</tr>
</table>
</td>
</tr>           

              <tr>
                <td height="240" valign="top"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
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
		        <td width="85%">
		        <table width="100%" cellpadding="0" cellspacing="0">
				<tr><td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				<tr><td height="91" align="center"></td></tr>
				<tr><td colspan="3" id="banner_grey" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				</table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
</tr>
</table>
</td>
</tr>


</table>
 </form> 
</body>
</html>
