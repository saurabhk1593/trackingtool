<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Message</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
     
   function homePageReport()
{
document.forms[0].action="home.spring";
document.forms[0].submit();	
}
</script>
</head>

<body>
<form method="post" action="?task=submitUserMapping"  name="userMapped" >
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

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
<tr>
<td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
      <tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
        <td width="85%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="10%" rowspan="2"><div align="center">
            <img src="images/satyam.gif" alt="Satyam" width="50" height="50" /></div></td>
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

<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Map Module Owners</span></td>
                </tr>
              
              <tr>
                <td valign="top" class="pageheadertxt_maroon">Map Module Owners</td>
                </tr>
              <tr>
                <td height="10"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="97%" border="0" cellpadding="0" cellspacing="0" class="boxtable_greyborder_padding">
                  
                  <tr>
                    <td width="20%" height="30" align="center" class="bodytxt_black"><p>The Module owners have been mapped successfully.</p></td>
                    </tr>
                  
                  <tr>
                    <td height="10"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
                </table></td>
                </tr>
              <tr>
                <td valign="top"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
                </tr>
              
              <tr>
                <td align="center" class="padding_left10_right3">
                  <input name="Button2" type="button" class="redbutton1" onclick="JavaScript:homePageReport();" value="Ok" /></td>
              </tr>
              <tr>
                <td valign="top"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
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
</table>
</table>
</form>
</body>
</html>
