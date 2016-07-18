<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Template Page</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />

<script language="JavaScript1.2"><!-- 

//CSS stylesheet for menu bar
//Customize background color (#FFF2BF) and link color (black):
var cssdefinition='<style>\n.menuitems{\nborder:2.5px solid #f5deb5;\n}\n\n.menuitems a{\ntext-decoration:none;\ncolor:696969;\n}\n<\/style>'

if (document.all||document.getElementById)
document.write(cssdefinition)

function over_effect(e,state, bgcolor){
if (document.all)
source4=event.srcElement
else if (document.getElementById)
source4=e.target
if (source4.className=="menuitems"){
source4.style.borderStyle=state
source4.style.backgroundColor=bgcolor
}
else{
while(source4.tagName!="DIV"){
source4=document.getElementById? source4.parentNode : source4.parentElement
if (source4.className=="menuitems"){
source4.style.borderStyle=state
source4.style.backgroundColor=bgcolor
}
}
}
}

function homePageReport()
{
document.forms[0].action="archivedData.spring?task=homePageReportCurrentSelection";
document.forms[0].submit();	
}


//--></script>



</head>

<body>
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
User user=(User)session.getAttribute("userInformation");%>
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



<td width="98%">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td>
<table width=98% border=0 align=center cellpadding=0 cellspacing=0 class=boxtable_greyborder_padding>
<tr><td class=headerboldtxt_greybg_greyborder colspan=10>List of Severity 2 Tickets as on Dec 20 2007</td></tr>
<tr><td class=headerboldtxt_greybg_greyborder width=3%>S.No.</td>
<td class=headerboldtxt_greybg_greyborder width=25%>Issue Description</td>
<td class=headerboldtxt_greybg_greyborder width=5%>
Enhancement> 2 Day's / Enhancement < 2 Day's / Bug Fix / System ADMIN</td>
<td class=headerboldtxt_greybg_greyborder width=9%>App</td><td class=headerboldtxt_greybg_greyborder width=11%>
Remedy TT #</td><td class=headerboldtxt_greybg_greyborder width=8%>TT Creation Date</td>
<td class=headerboldtxt_greybg_greyborder width=8%>TT Status</td>
<td class=headerboldtxt_greybg_greyborder width=3%>Region</td>
<td class=headerboldtxt_greybg_greyborder width=8%>Severity</td>
<td class=headerboldtxt_greybg_greyborder width=20%>Remarks</td>
</tr><tr><td class=bgwhite_bottombordergrey width=3%>1</td>
<td class=bgwhite_bottombordergrey width=25%>User and Alias settings in CRM</td>
<td class=bgwhite_bottombordergrey width=5%>&nbsp;</td><td class=bgwhite_bottombordergrey width=9%>CRM</td>
<td class=bgwhite_bottombordergrey width=11%>PR228084</td>
<td class=bgwhite_bottombordergrey width=8%>24-Aug-07</td>
<td class=bgwhite_bottombordergrey width=8%>Pending</td>
<td class=bgwhite_bottombordergrey width=3%>AUS</td>
<td class=bgwhite_bottombordergrey width=8%>Severity 3</td>
<td class=bgwhite_bottombordergrey width=20%>
The documentation has been submitted to Ciaran . Awaiting confirmation from Ciaran.</td>
</tr><tr><td class=bggrey_bottombordergrey width=3%>2</td>
<td class=bggrey_bottombordergrey width=25%>TMT - What if's not showing correct vessel Size and Standing Order
</td><td class=bggrey_bottombordergrey width=5%>&nbsp;</td><td class=bggrey_bottombordergrey width=9%>TMT</td>
<td class=bggrey_bottombordergrey width=11%>PR1127401</td><td class=bggrey_bottombordergrey width=8%>3-Sep-07</td>
<td class=bggrey_bottombordergrey width=8%>Pending</td><td class=bggrey_bottombordergrey width=3%>UK</td>
<td class=bggrey_bottombordergrey width=8%>Severity 3</td>
<td class=bggrey_bottombordergrey width=20%>
Its seems to be an Enhancement. Awaiting the detail input from GCE</td></tr></table>
</td>
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

</body>
</html>
