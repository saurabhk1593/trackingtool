<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Application with no or 1 user</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<% String _flag = ((String)request.getParameter("excel")!=null)?(String)request.getParameter("excel"):"false"; 
	String border="0";
	if (_flag.equals("false")) 
	{ 
	%>
       <link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
	<% }
	%>
<%if(_flag.equalsIgnoreCase("false")) { %>
<script language="javascript">

function download() {
		document.ApplicationWithOneUser.excel.value="true";
    document.ApplicationWithOneUser.action="?task=getAppInfo";
	document.ApplicationWithOneUser.submit();
}

function homePageReport()
{

document.forms[0].action="archivedData.spring?task=homePageReportCurrentSelection";
document.forms[0].submit();	
}

</script>

<%}
if (_flag.equals("false")) {
		response.setContentType ("text/html") ;
    	}
	else{
	response.setContentType("application/vnd.ms-excel"); 
	response.setHeader("Content-Disposition", "attachment;filename=ApplicationWithOneOrZeroUser.xls");
	 }%>


</head>

<body>
<form method="post" name="ApplicationWithOneUser" >
<c:set var="applicationList" value="${model.applicationlist}" />
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
<%if(_flag.equals("true")){%>	
<table cellSpacing=0 cellPadding=0 border=1 align="center"   id="basetable"  width="100%">
<%border="1"; }
else{ %>
<table cellSpacing=0 cellPadding=0 class="tablestyle"  align="center"   id="basetable" width="100%">
<%}%>



<%if(_flag.equals("false")){%>
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
            <td width="34%" valign="middle" class="welcome_txt" align="right"><div align="right">
            <img src="images/spacer.gif" alt="spacer" width="1" height="10" />
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


<td width="98%" valign="top"><%}%>

<table width="100%" border="<%=border%>" ><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Linde Home</span><span class="bodytxt_grey">&gt;&gt; Application with No or 1 user</span></td></tr>
<tr>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0" border="<%=border%>">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="1">Application Mappings Report</td>
<td colspan=1  align=right class=headerboldtxt_greybg_greyborder>
		<a  href="javascript:download(this.form);" align=right style="text-decoration:none" title="Download to Excel"> 
		<%if(_flag.equals("false")){%>
		<IMG SRC="images/excel.gif" WIDTH="18" HEIGHT="18" BORDER=0 ALT="download to excel"  />
		<%}%></a>
		</td></tr>
<tr>
<td class=headerboldtxt_greybg_greyborder width="30%" border="<%=border%>">Application Name</td>
<td class=headerboldtxt_greybg_greyborder border="<%=border%>">Mapped Owners</td>
</tr>



<c:forEach var="applicationList" items="${model.applicationlist}">
							
    <tr>
<td class=bgwhite_bottombordergrey width="30%" border="<%=border%>">${applicationList.applicationBean.modulename}</td>
<td class=bgwhite_bottombordergrey border="<%=border%>">${applicationList.userName}</td>

</tr>

    
                      		
</c:forEach>






</table>
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
<input type="hidden" name="excel" value=""/>
</table>
</table>
</table>
</form>
</body><%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>

</html>