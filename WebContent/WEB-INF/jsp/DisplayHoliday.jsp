<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<%@page import="java.util.Calendar"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Holiday Plans</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
</script>
</head>

<body>
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
int year = (Calendar.getInstance().get(Calendar.YEAR));
User user=(User)session.getAttribute("userInformation");
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<form method="post" name="" action="" >
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

<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home</span><span class="bodytxt_grey">&gt;&gt; Display Holiday</span></td></tr>
<tr>
<table width="30%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Public Holiday <%=year%></td>
</tr>
                  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>

<tr>
<td class=bodytxt_black width="5%" colspan="1"><b>Date</b></td>
<td class=bodytxt_black width="5%" colspan="1"><b>Description</b></td>
</tr>
<tr>
<td>
  					
  						<c:forEach var="holidayList" items="${model.Holiday}">
  						<tr>
  							<td class=bodytxt_black width="5%"colspan="1">${holidayList.key}</td>
  							<td class=bodytxt_black width="5%" colspan="1">${holidayList.value}</td>
  						</tr>
  						</c:forEach>
  					
  					
</tr>
                    
  					</td>
  					</tr>
  					
  					
  					
  					
  					
  					
					<td class=bodytxt_black width="25%" align="left">&nbsp;</td>
<td class=bodytxt_black width="25%" align="left">&nbsp;</td>

<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	
				
					
		
			 
<tr>
                    <td height="10" colspan="2">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					              <tr>
                <td align="center" valign="top" colspan="12">
               
                
                  
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

</td>
</tr>

</table>

</body>
<%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
