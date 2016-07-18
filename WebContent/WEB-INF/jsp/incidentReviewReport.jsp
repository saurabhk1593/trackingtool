<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Detailed Report</title>
<script language="javascript" src="js/engine.js"></script>
<script language="javascript" src="js/util.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
 
HttpSession httpSession=request.getSession();
String  archivalyear = ((String)httpSession.getAttribute("archivalyear")!=null)?(String)httpSession.getAttribute("archivalyear"):"2009-2010";

String _flag = ((String)request.getParameter("excel")!=null)?(String)request.getParameter("excel"):"false"; 
String ordertype=((String)request.getParameter("ordertype")!=null)?(String)request.getParameter("ordertype"):"ascending";

String image1=((String)request.getParameter("image1")!=null)?(String)request.getParameter("image1"):"images/up.gif";
String image2=((String)request.getParameter("image2")!=null)?(String)request.getParameter("image2"):"images/up.gif";
String image3=((String)request.getParameter("image3")!=null)?(String)request.getParameter("image3"):"images/up.gif";
String image4=((String)request.getParameter("image4")!=null)?(String)request.getParameter("image4"):"images/up.gif";
String image5=((String)request.getParameter("image5")!=null)?(String)request.getParameter("image5"):"images/up.gif";
String image6=((String)request.getParameter("image6")!=null)?(String)request.getParameter("image6"):"images/up.gif";

String border="0";
	String urlparameters="";
	String severity=request.getParameter("severity");
	
	System.out.println("Value of severity is::::::"+severity);
	String age=request.getParameter("age");
	String status=request.getParameter("status");
	String module=request.getParameter("module");
	User user=(User)session.getAttribute("userInformation");	
	if(request.getParameter("severity")==null){severity="";}
	else severity=request.getParameter("severity");
		
	if(request.getParameter("age")==null){age="";}
	else age=request.getParameter("age");
	
	
	if(request.getParameter("status")==null){status="";}
	else status=request.getParameter("status");
	
	if(request.getParameter("module")==null){module="";}
	else module=request.getParameter("module");
	
	if(!severity.equals("")){urlparameters="severity="+severity;}
	else if(!age.equals("")){
		urlparameters="age="+age;
		if(!module.equals(""))
		{
			urlparameters="age="+age+"&module="+module;
		}
		}
	else if(!status.equals("")){urlparameters="status="+status+"&module="+module;}
	else{urlparameters="age="+age+"&module="+module;}
	
	
	
	if (_flag.equals("false")) 
	{ 
	%>
       <link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
	<% }
	%>
<%if(_flag.equalsIgnoreCase("false")) { %>
<script language="JavaScript1.2"> 

//CSS stylesheet for menu bar
//Customize background color (#FFF2BF) and link color (black):
var cssdefinition='<style>\n.menuitems{\nborder:2.5px solid #f5deb5;\n}\n\n.menuitems a{\ntext-decoration:none;\ncolor:696969;\n}\n<\/style>';

if (document.all||document.getElementById)
document.write(cssdefinition);

function over_effect(e,state, bgcolor){
if (document.all)
source4=event.srcElement;
else if (document.getElementById)
source4=e.target;
if (source4.className=="menuitems"){
source4.style.borderStyle=state;
source4.style.backgroundColor=bgcolor;
}
else{
while(source4.tagName!="DIV"){
source4=document.getElementById? source4.parentNode : source4.parentElement;
if (source4.className=="menuitems"){
source4.style.borderStyle=state;
source4.style.backgroundColor=bgcolor;
}
}
}
}



function download() {
	var url=document.HomePageForm.urlparameter.value;
	document.HomePageForm.excel.value="true";
	document.HomePageForm.action="./download1.spring";
	//alert("homepagereport.spring?"+url);
	document.HomePageForm.submit();
}

function submitRemarks() {
	var url=document.HomePageForm.urlparameter.value;
	document.HomePageForm.action="?task=submitTTRemarks&"+url;
	//alert("homepagereport.spring?"+url);
	document.HomePageForm.submit();
}

function addEffort(no)
{
	var fieldname=no.name;
	var temp;
	var netmeeting;
	var ktdoc;
	var discussion;
	var gsd;

	if(fieldname.substring(0,11)=='effort_gsd_')
	{
	temp=fieldname.substring(11);
	if(isNaN(document.forms[0].elements['effort_gsd_'+temp].value))
		{	
				alert("Please enter numeric characters only");
				document.forms[0].elements['effort_total_'+temp].value="";
				return false
		}		
	}

	if(fieldname.substring(0,11)=='effort_netm')
	{
	temp=fieldname.substring(18);
	if(isNaN(document.forms[0].elements['effort_netmeeting_'+temp].value))
		{	
				alert("Please enter numeric characters only");
				document.forms[0].elements['effort_total_'+temp].value="";
					return false
		}		
	}

	if(fieldname.substring(0,11)=='effort_disc')
	{
	temp=fieldname.substring(18);
	if(isNaN(document.forms[0].elements['effort_discussion_'+temp].value))
		{	
				alert("Please enter numeric characters only");
				document.forms[0].elements['effort_total_'+temp].value="";
				return false
		}
	}

	if(fieldname.substring(0,11)=='effort_ktdo')
		{
	temp=fieldname.substring(13);
	if(isNaN(document.forms[0].elements['effort_ktdoc_'+temp].value))
		{	
				alert("Please enter numeric characters only");
				document.forms[0].elements['effort_total_'+temp].value="";
				return false
		}
	}

	if(fieldname.substring(0,11)=='effort_tota')
	{
	temp=fieldname.substring(13);
	if(isNaN(document.forms[0].elements['effort_total_'+temp].value))
		{	
				alert("Please enter numeric characters only");
				document.forms[0].elements['effort_total_'+temp].value="";
				return false
		}
	}
	netmeeting=parseFloat(document.forms[0].elements["effort_netmeeting_"+temp].value*1);
	discussion=parseFloat(document.forms[0].elements['effort_discussion_'+temp].value*1);
	gsd=parseFloat(document.forms[0].elements['effort_gsd_'+temp].value*1);
	ktdoc=parseFloat(document.forms[0].elements['effort_ktdoc_'+temp].value*1);
	
	//alert(document.forms[0].elements['effort_netmeeting_'+temp].value);
	//alert(document.forms[0].elements['effort_discussion_'+temp].value);
	//alert(document.forms[0].elements['effort_gsd_'+temp].value);
	//alert(document.forms[0].elements['effort_ktdoc_'+temp].value);
	

	document.forms[0].elements['effort_total_'+temp].value=netmeeting+discussion+gsd+ktdoc;
	//alert(document.forms[0].elements['effort_total_'+temp].value);
	
	if(isNaN(document.forms[0].elements['effort_total_'+temp].value))
	{
	document.forms[0].elements['effort_total_'+temp].value="";
	}
	var sum=netmeeting+discussion+gsd+ktdoc;
	var efforttotal=parseFloat(document.forms[0].elements['effort_total_'+temp].value*1);

	if(efforttotal!=sum)
	{
		alert("The total efforts should be sum of the individual efforts");
	return false;
	}

}

function getSortedData(field)
{
	var url=document.HomePageForm.urlparameter.value;
	//alert(document.HomePageForm.ordertype.value);
	var existingorder=document.HomePageForm.ordertype.value;
	url=url+"&field="+field;
	var temp;
	var image1=document.getElementById("image1");
	var image2=document.getElementById("image2");
	var image3=document.getElementById("image3");
	var image4=document.getElementById("image4");
	var image5=document.getElementById("image5");
	var image6=document.getElementById("image6");
	
	
	
	//alert(image);
	if(field=='casetype'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image1.attributes.length; i++) {
			if (image1.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image1.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image1.attributes.length; i++) {
			if (image1.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image1.value="images/up.gif";
			}
		}
	}
	}
	
	
	if(field=='application'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image2.attributes.length; i++) {
			if (image2.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image2.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image2.attributes.length; i++) {
			if (image2.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image2.value="images/up.gif";
			}
		}
	}
	}
	
	
	
	if(field=='ttno'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image3.attributes.length; i++) {
			if (image3.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image3.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image3.attributes.length; i++) {
			if (image3.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image3.value="images/up.gif";
			}
		}
	}
	}
	
	
	if(field=='status'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image4.attributes.length; i++) {
			if (image4.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image4.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image4.attributes.length; i++) {
			if (image4.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image4.value="images/up.gif";
			}
		}
	}
	}
	
	
	
	if(field=='severity'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image5.attributes.length; i++) {
			if (image5.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image5.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image5.attributes.length; i++) {
			if (image5.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image5.value="images/up.gif";
			}
		}
	}
	}
	
	
	if(field=='ageing'){
	if(existingorder=="descending"){
	document.HomePageForm.ordertype.value="ascending";
	for (var i = 0; i < image6.attributes.length; i++) {
			if (image6.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image6.value="images/down.gif";
			}
			}
		
			}		
	if(existingorder=="ascending"){
	document.HomePageForm.ordertype.value="descending";
	for (var i = 0; i < image6.attributes.length; i++) {
			if (image6.attributes[i].name.toUpperCase() == 'SRC') {
			document.HomePageForm.image6.value="images/up.gif";
			}
		}
	}
	}
	
	
	url=url+"&type="+document.HomePageForm.ordertype.value;
	//alert(url);
	document.HomePageForm.action="?task=getSortedTickets&"+url;
	document.HomePageForm.submit();
	
}

function homePageReport()
{
document.forms[0].action="home.spring";
document.forms[0].submit();	
}

//Ajax script for time calculation
window.onload=function()
{
    dwr.engine.setActiveReverseAjax(true);
    //dwr.engine.setErrorHandler(errorHandler);
    //dwr.engine.setPollStatusHandler(updatePollStatus);
    //updatePollStatus(true);
    //Tabs.init('tabList', 'tabContents');       
}
	  
//function errorHandler(message, ex) 
//{
//   dwr.util.setValue("error", "Cannot connect to server. Initializing retry logic.", {escapeHtml:false});
//	setTimeout(function() { dwr.util.setValue("error", ""); }, 5000)
//}
	  
//function updatePollStatus(pollStatus) 
//{
//   dwr.util.setValue("pollStatus", pollStatus ? "Online" : "Offline", {escapeHtml:false});
//}
	  
//function enableUpdates(enabled)
//{
//    if (!enabled) 
//	{
//	    dwr.util.setValue("clockDisplay", "This tab/window does not have updates enabled.");
//	}  
//   Clock.setEnabledAttribute(enabled);
//}

function enableUpdates(ttNo)
{
 alert(document.forms[0].elements['status_'+ttNo].value);	
}
//function setClockStatus(clockStatus) {
//    dwr.util.setValue("clockStatus", clockStatus ? "Clock started" : "Clock stopped");
//}
// end of Ajay script for time calculation
</script>
<%}
if (_flag.equals("false")) {
		response.setContentType ("text/html") ;
    	}
	else{
	response.setContentType("application/vnd.ms-excel"); 
	response.setHeader("Content-Disposition","attachment;filename=HomePageDetailed_Report.xls");
	 }%>


</head>

<body>
<form name="HomePageForm" method="post" action="?task=submitTTRemarks">
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
String header="";
String colspan="9";
%>

<c:set var="ttInfo" value="${model.incidentList}" />
<c:set var="ttselectedflds" value="${model.selectedfields}" />

<%if(_flag.equals("false")){%>
<table cellSpacing=0 cellPadding=0 class="tablestyle"  align="center"   id="basetable" width="100%">


<tr>
<td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
      <tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
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
							class="linknormal">Home</a> | <a href="home.spring"
							class="linknormal">Change View</a> | <a href="submitFeedback.spring"
							class="linknormal">Feedback</a>
							</div></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line"><img src="images/spacer.gif" alt="spacer" width="1" height="6" />
            </td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table></td>
</tr>

<tr>
<td>	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" />
</td>



<td width="98%"><%}%>

<%if(_flag.equals("true")){border="1";} %>
<table width="100%" border="<%=border%>" align="center" cellpadding="0" cellspacing="0">
<tr>

<td width="98%" valign="top">

<table width="100%"><tr><td height="30" valign="middle" >
<span class="breadcrum_red_txt">Tracking Tool Home</span><span class="bodytxt_grey">&gt;&gt; Incident Review Report</span>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<%if(_flag.equals("false")){%><c:if test="${!empty model.message}"><span class="breadcrum_red_txt">Message:</span>
<span class="bodytxt_grey"><b>${model.message}</b></span></c:if><%}%></td></tr></table></td>
</tr>
<tr>
<td>
<table width="98%" border="<%=border%>" align=center cellpadding=0 cellspacing=0 class=boxtable_greyborder_padding>
<tr><td class="headerboldtxt_greybg_greyborder" colspan="14">List of <c:out value="${model.urlInfo}" /> Tickets as on <%=currentDate%></td>
<%if(_flag.equals("true")){colspan="1";} %><td colspan=<%=colspan%>  align="center" class=headerboldtxt_greybg_greyborder>
		<a  href="javascript:download(this.form);" align=right style="text-decoration:none" title="Download to Excel"> 
		<%if(_flag.equals("false")){%>
		<IMG SRC="images/excel.gif" WIDTH="18" HEIGHT="18" BORDER=0 ALT="download to excel"  />
		<%}%></a>
		</td></tr>
<tr><td class=headerboldtxt_greybg_greyborder width="3%">S.No.</td>
<c:if test="${ttselectedflds.IncidentID =='IncidentID'}">
<td class=headerboldtxt_greybg_greyborder width="5%">Incident ID</td>
</c:if>
<c:if test="${ttselectedflds.IssueDes =='IssueDes'}">
<td class=headerboldtxt_greybg_greyborder width="12%" >Issue Description</td>
</c:if>
<c:if test="${ttselectedflds.Prioirity =='Prioirity'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Prioirity</td>
</c:if>
<c:if test="${ttselectedflds.IncidentOpTime =='IncidentOpTime'}">

<td class=headerboldtxt_greybg_greyborder width="8%">Incident Open Time</td>
</c:if>
<c:if test="${ttselectedflds.IncStatus =='IncStatus'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Incident Status</td>
</c:if>
<c:if test="${ttselectedflds.ReOpenTime =='ReOpenTime'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Re-Open Time</td>
</c:if>
<c:if test="${ttselectedflds.AssigneeName =='AssigneeName'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Assignee Name</td>
</c:if>
<c:if test="${ttselectedflds.UpdateTime =='UpdateTime'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Update Time</td>
</c:if>
<c:if test="${ttselectedflds.CloseTime =='CloseTime'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Close Time</td>
</c:if>
<c:if test="${ttselectedflds.ModuleName =='ModuleName'}">
<td class=headerboldtxt_greybg_greyborder width="8%">Module Name (In Days)</td>
</c:if>
<td class=headerboldtxt_greybg_greyborder width="8%">Target Date</td>

<td class=headerboldtxt_greybg_greyborder width="8%">Aging (In Days)</td>




<%if(_flag.equals("false")){%>
<td class=headerboldtxt_greybg_greyborder width=8%>Mail
<%}%>

</td>



</tr>


<%int i=1; %>
<c:forEach var="ttInfo" items="${model.incidentList}">
							
<tr >
<td class=bggrey_bottombordergrey width=3%><%=i++%></td>
<c:if test="${ttselectedflds.IncidentID =='IncidentID'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.incidentID}</td>
</c:if>
<c:if test="${ttselectedflds.IssueDes =='IssueDes'}">
<td class=bggrey_bottombordergrey width=150 nowrap="nowrap">${ttInfo.title}</td>
</c:if>
<c:if test="${ttselectedflds.Prioirity =='Prioirity'}">
<td class=bggrey_bottombordergrey width=8%>${ttInfo.priority}</td>
</c:if>
<c:if test="${ttselectedflds.IncidentOpTime =='IncidentOpTime'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.openTime}</td>
</c:if>
<c:if test="${ttselectedflds.IncStatus =='IncStatus'}">
<td class="bggrey_bottombordergrey" width="20%">
<%String sel1="";
String sel2="";
String sel3="";
String sel4="";
String sel5="";
String sel6="";
%>
 					  <c:if test="${ttInfo.status=='Work In Progress'}">
                      <%sel1="selected";
                      %>
                      </c:if>
                      <c:if test="${ttInfo.status=='Pending Customer'}">
                      <%sel2="selected";%>
                      </c:if>
                      <c:if test="${ttInfo.status=='Assigned'}">
                      <%sel5="selected";%>
                      </c:if>
                      <c:if test="${ttInfo.status=='Closed'}">
                      <%sel6="selected";%>
                      </c:if>
		
	<%if(_flag.equals("false")){%>
	<%if(!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){%>
	
	<select name="status_${ttInfo.incidentID}" class="dropdown_corrected">
                      <option value="Assigned" <%=sel5%> onchange="javascript:enableUpdates(${ttInfo.incidentID});">Assigned</option>
                      <option value="Work In Progress" <%=sel1%>>WIP</option>
                      <option value="Pending Customer" <%=sel2%>>Pending</option>
					  <option value="Closed" <%=sel6%>>Closed</option>
       		</select>
       		
       		<%}else{%>
       		${ttInfo.status}
       		<%}}else{ %>${ttInfo.status}<%} %>

</td>
</c:if>
<c:if test="${ttselectedflds.ReOpenTime =='ReOpenTime'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.reopenTime}</td>
</c:if>
<c:if test="${ttselectedflds.AssigneeName =='AssigneeName'}">
<td class=bggrey_bottombordergrey width=8%>${ttInfo.assigneeFullname}</td>
</c:if>
<c:if test="${ttselectedflds.UpdateTime =='UpdateTime'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.updateTime}</td>
</c:if>
<c:if test="${ttselectedflds.CloseTime =='CloseTime'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.closeTime}</td>
</c:if>

<c:if test="${ttselectedflds.ModuleName =='ModuleName'}">
<td class=bggrey_bottombordergrey width=5%>${ttInfo.module}</td>
</c:if>
<td class=bggrey_bottombordergrey width=5%>${ttInfo.targetdate}</td>


<td class=bggrey_bottombordergrey width=5%>${ttInfo.aging}</td>

</c:forEach>
<!-- 
<%if(_flag.equals("false")){%>
<tr>
                    <td height="10" colspan="2">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					           
					           
				<%if(!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){
				%>
					              <tr>
              <td align="center" valign="top" colspan="12">
                
                <input name="Button" type="button" class="redbutton1" value="Submit" onclick="submitRemarks();"/>
				<input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
              </td>
              
              </tr>
              <%}%>
			  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
 -->

<tr>
</tr><tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" />
                    </td>
                    </tr>

					
			  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" />
                    </td>
                    </tr><%}%>
</table>
</td>
</tr>
</table>
<%if(_flag.equals("false")){%>
</td>

<td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
</tr>
</table>
</td></tr>



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
<%}%>
<input type="hidden" name="urlparameter" value="<%=urlparameters%>"/>
<input type="hidden" name="excel" value="false"/>
<input type="hidden" name="ordertype" value="<%=ordertype%>"/>
<input type="hidden" name="image1" value="<%=image1%>"/>
<input type="hidden" name="image2" value="<%=image2%>"/>
<input type="hidden" name="image3" value="<%=image3%>"/>
<input type="hidden" name="image4" value="<%=image4%>"/>
<input type="hidden" name="image5" value="<%=image5%>"/>
<input type="hidden" name="image6" value="<%=image6%>"/>
</form>
</body>

<%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
