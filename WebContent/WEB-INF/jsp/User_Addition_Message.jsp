<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<title>Add User</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js">
</script>
<script  src='/WEB-INF/com/linde/lindeTool/reports/StationFinder.js'></script>
<script>
/*function getUserInfo()
{
var userId = document.getElementById("selectUser").value; 
//alert(userId);
 	if(window.XMLHttpRequest)
		req = new XMLHttpRequest();
	else if(window.ActiveXObject)
		req = new ActiveXObject("Microsoft.XMLHTTP");
	url = "getUserDetails.spring?userId="+userId;
	url = "addUser.spring?test=getUserDetail";
	//alert(url);
	req.onreadystatechange = displayOutput;
	req.open("POST",url,true);
	req.send();
}


function displayOutput()
{

if(req.readyState == 4)
	{
	if (req.status == 200) {
	
		
	alert('Inside response');
	alert(data);
		response = req.responseXML;
		strArray = response.split('#');
		alert(strArray[0]);
		alert(strArray[1]);
				document.getElementById("ajaxRefId").innerHTML = strArray[0];
		

	}		
			
			//responseFN(response);
	}
}*/

</script>
    <script type="text/javascript">
/*        function getUserInfo() {
            var search = dwr.util.getValue("stationName");
            
            if (search.length < 2) {
            	clearTable();
            	return;
            }
            
            StationFinder.findStationsByName(search, function(stations) {
				clearTable();

                 Create a new set cloned from the pattern row
                //var station, id;
                //for (var i = 0; i < stations.length; i++) {
                  //  station = stations[i];
                    //id = i.toString();
                    dwr.util.cloneNode("pattern", { idSuffix:id });
                    dwr.util.setValue("stationName" + id, station);
                    $("pattern" + id).style.display = "table-row";
                }
            });*/
</script>
    <script type="text/javascript">
    function getUserInfo() {
         document.forms[0].action="/lindetool/addUser.spring?task=getUserDetail";
     	document.forms[0].submit();
    
    }
    
        function approveUser() {
        alert('Hi');         document.forms[0].action="/lindetool/addUser.spring?task=approveUser";
     	document.forms[0].submit();
    
    }
    function homePageReport()
{

document.forms[0].action="archivedData.spring?task=homePageReportCurrentSelection";
document.forms[0].submit();	
} </script>

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

<form method="post" name="addUserForm" action="?task=approveUser">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
	<tr>
		<td valign="top">
		  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
			<tr>
				<td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
				<td width="85%">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
					</table>
				</td>
			<td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table>
	</td>
</tr>



<tr>
	<td>	
	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
		<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>

<!-- Inner Table-->
		<td width="98%" valign="top">
		<table width="100%">
			<tr>
				<td>
				<span class="breadcrum_red_txt">Linde Home</span><span class="bodytxt_grey">&gt;&gt; Approve User</span>
				</td>
			</tr>
			<tr>
				<td>
					<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">

					<tr>
						<td height="10" colspan="6"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					<tr>

						<td align="center" class="bodytxt_black" width="20%">
                      			<c:out value="${message.messageCont}"/>
						</td>
						
					</tr>
		
				</table>
			</td></tr>
	
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
					<table width="100%" height="130" cellpadding="0" cellspacing="0">
					<tr><td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
					<tr><td height="91" align="center"></td></tr>
					<tr><td colspan="3" id="banner_grey" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
					</table>
					</td>
			 <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
	</tr>
	</table>
</td>
</tr>

</table>
</form>

</body><%request.removeAttribute("message");%>
</html>

