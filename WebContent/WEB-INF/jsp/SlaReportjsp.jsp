<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.techm.trackingtool.admin.vo.HomePageVO"%>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Home Page</title>

<script language="javascript" src="js/menuopener.js"></script>
<% 
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server


String _flag = ((String)request.getParameter("excel")!=null)?(String)request.getParameter("excel"):"false"; 
HttpSession httpSession=request.getSession(false);
String  archivalyear = ((String)httpSession.getAttribute("archivalyear")!=null)?(String)httpSession.getAttribute("archivalyear"):"2009-2010";

	String border="0";
	if (_flag.equals("false")) 
	{ 
	%>
       <link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
	<% }
	%>
<%if(_flag.equalsIgnoreCase("false")) { %>
<script language="javascript">
function submitSelection()
{
var sev=document.getElementById('sev1');
alert("HI"+sev.value);
}
function download() {
	
	 document.CrForm.action="./crsla.spring?excel=true";
		document.CrForm.submit();
}
function homePageReport()
{

	document.CrForm.action="./login.spring";
	document.CrForm.action="./excel.spring?excel=false";
	document.CrForm.submit();
	
}

</script>
<%}
if (_flag.equals("false")) {
		response.setContentType ("text/html") ;
    	}
	else{
	response.setContentType("application/vnd.ms-excel"); 
	response.setHeader("Content-Disposition", "attachment;filename=crslametrics_Report.xls");
	 }%>
</head>

<body>

<form method="post" name="CrForm" action="?task=getDetails">
<c:set var="ttSummary" value="${homeVO.homePageMap.ttSummary}" />

<c:set var="userInfo" value="${homeVO.homePageMap.userInfo}" />
<c:set var="curDate" value="${homeVO.homePageMap.currDate}" />
<c:set var="userRole" value="${userInfo.roleId}" />
<%if(_flag.equals("true")){%>	
<table cellSpacing=0 cellPadding=0 border=1 align="center"   id="basetable"  width="100%">
<%border="1"; }
else{ %>
<table cellSpacing=0 cellPadding=0 class="tablestyle"  align="center"   id="basetable" width="100%">
<%}%>

<%if(_flag.equals("false")){%>	
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			id="menutable">
			<tr>
				<td width="1%" align="left" class="leftborder"><img
					src="images/spacer.gif" width="15" height="6" /></td>
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
						<td width="33%" valign="middle" class="welcome_txt">Welcome,
						<c:out value="${userInfo.firstName}" /></td>
						<td width="33%" align="center" valign="middle" class="welcome_txt"><c:out
							value="${curDate}" /></td>
						<td width="34%" valign="middle" class="welcome_txt" align="right">
						<div align="right"><img src="images/spacer.gif" alt="spacer"
							width="1" height="10" /><a href="JavaScript:homePageReport();"
							class="linknormal">Home</a> | <a href="login.spring"
							class="linknormal">Change View</a> | <a href="submitFeedback.spring"
							class="linknormal">Feedback</a></div>
						</td>
					</tr>
					<tr>
						<td colspan="100%" id="double_line"><img
							src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
					</tr>
				</table>
				</td>
				<td width="1%" align="left" class="rightborder"><img
					src="images/spacer.gif" width="15" height="6" /></td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td valign="top">

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			id="menutable">
			<tr>
				<td width="1%" align="left" class="leftborder"><img
					src="images/spacer.gif" width="15" height="6" /></td>


				<td width="18%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="menutable">
					<tr valign="top">
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td valign="top" class="menudivider"><img
							src="images/spacer.gif" alt="spacer" width="1" height="7" /></td>
					</tr>
					<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>
					<c:set var="checkRole" value="${userInfo.userRole.roleName}" />
					<c:choose>
						<c:when test="${checkRole !='TM'}">
							<tr>
								<td valign="top">
								<a href="javascript:void(0);" onclick="main1_opener();"
									class="treemainmenu">
								   <img src="images/menu_plus.gif"
									border="0px" align="absmiddle" />
									Admin</a>
								<div id="main1_sub" style="display: none">
								<div class="treesubmenudiv"><a href="uploadTTInfo.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Upload TT
								Information</a></div>
								<div class="treesubmenudiv"><a href="uploadCRInfo.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Upload CR
								Information</a></div>
								<div class="treesubmenudiv"><a
									href="addApplication.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Add Application</a></div>
								<div class="treesubmenudiv"><a
									href="addUser.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Add User</a></div>
								<c:if test="${checkRole=='PM'}"><div class="treesubmenudiv"><a
									href="approveUser.spring?task=getUsers" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Approve User</a></div></c:if>
								<div class="treesubmenudiv"><a href="mapUser.spring?task=getUserDetail"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Map
								Module Owners</a></div>
								<div class="treesubmenudiv"><a href="deleteUser.spring?task=getDetails"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Delete
								User/Module</a></div>
								

								<div class="treesubmenudiv"><a
									href="configureEmailFrequency.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Configure E-mail settings</a></div>
								  <div class="treesubmenudiv"><a
									href="crconfigureEmailFrequency.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Configure CR E-mail settings</a></div>
								
								<div class="treesubmenudiv"><a
									href="Leave.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Leave Plans</a></div>	
									
								<div class="treesubmenudiv"><a
									href="Holiday.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Holiday</a></div>
								</div>
								</td>
							</tr>
							
							<tr>
								<td valign="top" class="menudivider"><img
									src="images/spacer.gif" alt="spacer" width="1" height="7" /></td>

							</tr>
						</c:when>
					</c:choose>
					<%} %>
					<tr>
						<td valign="top">
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>
						<a href="javascript:void(0);" onclick="main3_opener();"
							class="treemainmenu"><img src="images/menu_plus.gif"
							border="0px" align="absmiddle" />Reports</a>
						<div id="main3_sub" style="display: none">
						<div class="treesubmenudiv">
						<%}%>
						<a
							href="reviewReport.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Review Report</a>
							
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						</div>
						<%}%>
						
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>
						<div class="treesubmenudiv"><a
							href="applicationMappings.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Application Mappings</a></div>
						<div class="treesubmenudiv"><a
							href="applicationMap.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Applications with no or 1 Owner</a></div>
						</div>
						<%} %>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="menudivider"><img
							src="images/spacer.gif" alt="spacer" width="1" height="7" /></td>
					</tr>
					<tr>
						<td valign="top">
							<a href="javascript:void(0);" onclick="main4_opener();"
							class="treemainmenu"><img src="images/menu_plus.gif"
							border="0px" align="absmiddle" />SLA Metrics</a>
						<div id="main4_sub" style="display: none">
						<div class="treesubmenudiv">
						<a
							href="crsla.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />CR SLA Metrics</a>
							
						
						</div>
						
						</div>
						
						
						</td>
					</tr>
					<tr>
						<td valign="top" class="menudivider"><img
							src="images/spacer.gif" alt="spacer" width="1" height="7" /></td>
					</tr>
				</table>
				</td>
				<td width="2%" class="rightborder"></td>
				<td width="80%">
				<table><%}%>
					<tr valign="top">
						<td valign="middle" align="left"><span
							class="breadcrum_red_txt">Tracking Tool Home</span></td>
				
							<td colspan=2 border=0 align=right>
				<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<a  href="javascript:download(this.form);" align=right style="text-decoration:none" title="Download to Excel"> 
				<%if(_flag.equals("false")){%><IMG SRC="images/excel.gif" WIDTH="18" HEIGHT="18" BORDER=0 ALT="download to excel"  /><%}%></a>
<%}%>		</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					
					<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="8" align="left">Volumes of CR</td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">Month</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Resolved/Closed</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Accept RfC & Prepare Approval</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Validate & Assign</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Approval</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Implementation</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Post-Implementation</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>
									</tr>
									
									<c:set var="crDetails" value="${ttSummary.byCRDetails}" />
									<c:forEach var="crDetailsMap" items="${crDetails}">
									 
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">${crDetailsMap.key}</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="closed" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=Closed" >
										<c:out
										    value="${crDetailsMap.value.Closed}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="acceptrfc" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=AcceptRfCPrepareApp" >
										<c:out
											value="${crDetailsMap.value.AcceptRfCPrepareApp}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="validate" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=ValidateAss" >
										<c:out
											value="${crDetailsMap.value.ValidateAss}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="Approval" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=Approval" >
										<c:out
											value="${crDetailsMap.value.Approval}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="Implementation" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=Implementation" >
										<c:out
											value="${crDetailsMap.value.Implementation}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="PostImplementation" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=PostImplementation" >
										<c:out
											value="${crDetailsMap.value.PostImplementation}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="Total" class="linknormal" href="crsladetailreport.spring?month=${crDetailsMap.key}&phase=Total" >
										<c:out
											value="${crDetailsMap.value.Total}" /></a>
										</td>
										
									</tr>
									   
									</c:forEach>
									
									
								</table>
								</td>
							</tr>
						
						<c:choose>
						<c:when test="${userRole==2 || userRole==1 }">
					<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="6"><center>SLA Adherence - Open CR Count</center></td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Met</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="mprio1" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=m" >
										<c:out
										    value="${ttSummary.byOpenSlaDetails.mprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="mprio2" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=m" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.mprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="mprio3" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=m" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.mprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="mprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=m" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.mprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> NotMet</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio1" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=nm" >
										<c:out
										    value="${ttSummary.byOpenSlaDetails.nmprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio2" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=nm" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio3" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=nm" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=nm" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> Total</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio1" class="linknormal" href="crsladetailreport.spring?priority=p1&phaseopen&sla=all" >
										<c:out
										    value="${ttSummary.byOpenSlaDetails.nmprio1+ttSummary.byOpenSlaDetails.mprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio1" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=all" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio2+ttSummary.byOpenSlaDetails.mprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio1" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=all" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio3+ttSummary.byOpenSlaDetails.mprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio1" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=all" >
										<c:out
											value="${ttSummary.byOpenSlaDetails.nmprio4+ttSummary.byOpenSlaDetails.mprio4}" /></a>
										</td>
									</tr>
									
								</table>
								</td>
							</tr>
							</c:when>
						</c:choose>	
							<c:choose>
							<c:when test="${userRole==2 || userRole==1 }">
							<c:if test="${userRole==2}">
							<tr>
							<td width="48%">
							<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="6"><center>SLA Adherence - Open CR Count in %</center></td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Toatl</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Met</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byOpenSlaDetails.perprio1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byOpenSlaDetails.perprio2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.perprio3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.perprio4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.perprio2+ttSummary.byOpenSlaDetails.perprio3+ttSummary.byOpenSlaDetails.perprio4}%" />
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> NotMet</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byOpenSlaDetails.nperprio1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byOpenSlaDetails.nperprio2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.nperprio3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.nperprio4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byOpenSlaDetails.nperprio2+ttSummary.byOpenSlaDetails.nperprio3}%" />
										</td>
									</tr>
										</table>
								</td>
							</tr>
							</c:if>
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="6"><center>SLA Adherence - Implementation / Post Implementation</center></td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Met</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=impl&sla=m" >
										<c:out
										    value="${ttSummary.byImpSlaDetails.imprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=impl&sla=m" >
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=impl&sla=m" >
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=impl&sla=m" >
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> NotMet</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=impl&sla=nm" >
										<c:out
										    value="${ttSummary.byImpSlaDetails.inmprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=impl&sla=nm" >
										<c:out
											value="${ttSummary.byImpSlaDetails.inmprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=impl&sla=nm" >
										<c:out
											value="${ttSummary.byImpSlaDetails.inmprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=impl&sla=nm" >
										<c:out
											value="${ttSummary.byImpSlaDetails.inmprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> Total</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byImpSlaDetails.imprio1+ttSummary.byImpSlaDetails.inmprio1}" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio2+ttSummary.byImpSlaDetails.inmprio2}" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio3+ttSummary.byImpSlaDetails.inmprio3}" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byImpSlaDetails.imprio4+ttSummary.byImpSlaDetails.inmprio4}" />
										</td>
									</tr>
									
								</table>
								</td>
							</tr>
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="6"><center>Resolution SLA Adherence</center></td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Met</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=m" >
										<c:out
										    value="${ttSummary.byClosedSlaDetails.clmprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=m" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=m" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=m" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> NotMet</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=nm" >
										<c:out
										    value="${ttSummary.byClosedSlaDetails.clnmprio1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=nm" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clnmprio2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=nm" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clnmprio3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=nm" >
										<c:out
											value="${ttSummary.byClosedSlaDetails.clnmprio4}" /></a>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> Total</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byClosedSlaDetails.clmprio1+ttSummary.byClosedSlaDetails.clnmprio1}" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio2+ttSummary.byClosedSlaDetails.clnmprio2}" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio3+ttSummary.byClosedSlaDetails.clnmprio3}" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.clmprio4+ttSummary.byClosedSlaDetails.clnmprio4}" />
										</td>
									</tr>
									
								</table>
								</td>
							</tr>
							<c:if test="${userRole==2}">
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="6"><center>Resoultion SLA Adherence in %</center></td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Toatl</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Met</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byClosedSlaDetails.perprio1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byClosedSlaDetails.perprio2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.perprio3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.perprio4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.perprio2+ttSummary.byClosedSlaDetails.perprio3+ttSummary.byClosedSlaDetails.perprio4}%" />
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left"> NotMet</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byClosedSlaDetails.nperprio1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byClosedSlaDetails.nperprio2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.nperprio3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.nperprio4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byClosedSlaDetails.nperprio2+ttSummary.byClosedSlaDetails.nperprio3+ttSummary.byClosedSlaDetails.nperprio4}%" />
										</td>
									</tr>
									</tr>
									
									</c:if>
								</table>
								</td>
							</tr>
									</c:when>
						</c:choose>	
						<c:choose>
							<c:when test="${userRole==3}">
									<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="12" align="left"> Estimation SLA - Onsite/Offshore</td>
									</tr>
									<tr rowspan="2">
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td  colspan="2"class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>
										
									</tr>
									<tr>
									<td  class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td>
									<td  class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td  c class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">OffShore</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=m&loc=Offshore" >
										<c:out
										    value="${ttSummary.byPMOpenSlaDetails.Offshore1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshore2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshore3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshore4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=open&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshore5}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=open&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren5}" /></a>
										</td>
										</tr>
										<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Onsite</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=m&loc=Onsite" >
										<c:out
										    value="${ttSummary.byPMOnOpenSlaDetails.Onsite1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=open&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsite2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=open&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsite3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=open&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsite4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=open&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=open&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsite5}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=open&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten5}" /></a>
										</td>
										</tr>
									
									
								</table>
								</td>
							</tr>
									
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="11" align="left"> Estimation SLA - Onsite/Offshore in %</td>
									</tr>
									<tr rowspan="2">
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td  colspan="2"class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
									<!--	<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>-->
										
									</tr>
									<tr>
									<td  class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td>
									<td  class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td  c class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
									<!--	<td class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td> -->
										
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">OffShore</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byPMOpenSlaDetails.Offshorep1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorenp1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorep2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorenp2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorep3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorenp3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorep4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshorenp4}%" />
										</td>
									<!--	<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOpenSlaDetails.Offshoren5}%" />
										</td> -->
										</tr>
										<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">OnShore</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byPMOnOpenSlaDetails.Onsitep1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitenp1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitep2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitenp2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitep3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitenp3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitep4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsitenp4}%" />
										</td>
									<!--	<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnOpenSlaDetails.Onsiten5}%" />
										</td> -->
										</tr>
									
									
								</table>
								</td>
							</tr>
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="12" align="left"> Resolution SLA - Onsite vs Offshore </td>
									</tr>
									<tr rowspan="2">
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td  colspan="2"class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>
										
									</tr>
									<tr>
									<td  class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td>
									<td  class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td  c class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">OffShore</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=m&loc=Offshore" >
										<c:out
										    value="${ttSummary.byPMClosedSlaDetails.Offshorecl1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorecl2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorecl3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorecl4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=closed&sla=m&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorecl5}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=closed&sla=nm&loc=Offshore" >
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl5}" /></a>
										</td>
										</tr>
										<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Onsite</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=m&loc=Onsite" >
										<c:out
										    value="${ttSummary.byPMOnClosedSlaDetails.Onsitecl1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p1&phase=closed&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl1}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitecl2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p2&phase=closed&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl2}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitecl3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p3&phase=closed&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl3}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitecl4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=p4&phase=closed&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl4}" /></a>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=closed&sla=m&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitecl5}" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<a id="nmprio4" class="linknormal" href="crsladetailreport.spring?priority=ALL&phase=closed&sla=nm&loc=Onsite" >
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl5}" />
										</td>
										</tr>
									
									
								</table>
								</td>
							</tr>
							<tr>
							<tr>
					<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								
									<td class="headerboldtxt_greybg_greyborder" colspan="11" align="left"> Resolution SLA - Onsite vs Offshore in %	</td>
									</tr>
									<tr rowspan="2">
									<td class="bggrey_bottombordergrey">
										<div align="left">SLA</div>
										</td>
										<td colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 1</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 2</div>
										</td>
										<td  colspan="2" class="bggrey_bottombordergrey">
										<div align="left">Priority 3</div>
										</td>
										<td  colspan="2"class="bggrey_bottombordergrey">
										<div align="left">Priority 4</div>
										</td>
									<!--	<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>-->
										
									</tr>
									<tr>
									<td  class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td>
									<td  class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td  c class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td   class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td  class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
									<!--	<td class="bggrey_bottombordergrey">
										<div align="left"></div>
										</td> -->
										
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">OffShore</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byPMClosedSlaDetails.Offshorepcl1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepncl1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepcl2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepncl2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepcl3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepncl3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepcl4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorepncl4}%" />
										</td>
									<!--	<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMClosedSlaDetails.Offshorencl5}%" />
										</td> -->
										</tr>
										<tr>
										<td class="bgwhite_bottombordergrey_number">
										<div align="left">Onsite</div>
									    </td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
										    value="${ttSummary.byPMOnClosedSlaDetails.Onsitepcl1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepncl1}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepcl2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepncl2}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepcl3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepncl3}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitepcl4}%" />
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitenpcl4}%" />
										</td>
									<!--	<td class="bgwhite_bottombordergrey_number">
										<c:out
											value="${ttSummary.byPMOnClosedSlaDetails.Onsitencl5}%" />
										</td> -->
										</tr>
									
									
								</table>
								</td>
							</tr>
							</c:when>
						</c:choose>	
						
							
						</table>
						
						
						
</table></table>
<%
//HomePageVO homePageVO=(HomePageVO)request.getAttribute("homeVO");
request.removeAttribute("homeVO");
//homePageVO=(HomePageVO)request.getAttribute("homeVO");
//homePageVO.setHomePageMap(null);
//out.println(" banu "+homePageVO.getHomePageMap());

%>
<input type="hidden" name="excel" value="false"/>
</form>


</body>
<%request.removeAttribute("homeVO");
request.setAttribute("homeVO",null);
//out.println("model is "+request.getAttribute("model"));
%>
</html>
