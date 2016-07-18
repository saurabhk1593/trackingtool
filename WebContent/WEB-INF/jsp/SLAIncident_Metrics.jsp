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
	
    document.HomePageForm.action="login.spring?excel=true";
	document.HomePageForm.submit();
}
function homePageReport()
{

document.HomePageForm.action="archivedData.spring?task=homePageReportCurrentSelection";
	document.HomePageForm.submit();
	
}

</script>
<%}
if (_flag.equals("false")) {
		response.setContentType ("text/html") ;
    	}
	else{
	response.setContentType("application/vnd.ms-excel"); 
	response.setHeader("Content-Disposition", "attachment;filename=HomePage_Report.xls");
	 }%>
</head>

<body>

<form method="post" name="HomePageForm" action="?task=getDetails">
<c:set var="ttSummary" value="${homeVO.homePageMap.ttSummary}" />

<c:set var="userInfo" value="${homeVO.homePageMap.userInfo}" />
<c:set var="curDate" value="${homeVO.homePageMap.currDate}" />
<c:set var="metrics" value="${homeVO.homePageMap.metricsVO}" />

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
								
								<div class="treesubmenudiv"><a href="home.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />CR Dashboard</a></div>
								
								<div class="treesubmenudiv"><a href="archivedData_home.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Incident Dashboard</a></div>
								
								<div class="treesubmenudiv"><a href="uploadTTInfo.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Upload TT
								Information</a></div>
								<div class="treesubmenudiv"><a href="uploadCRInfo.spring"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Upload CR
								Information</a></div>
								<div class="treesubmenudiv"><a
									href="addModule.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Add Module</a></div>
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
								User/Application</a></div>
								<div class="treesubmenudiv"><a
									href="configureEmailFrequency.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Configure E-mail settings</a></div>
								<!--  <div class="treesubmenudiv"><a
									href="configureEmailApplication.spring" class="treesubmenu"><img
									src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
									align="absmiddle" />Application E-mail settings</a></div>-->
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
					<%-- Added for Incident SLA Metrics -- Start --%>
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
							align="absmiddle" />CR Metrics</a>
							
						
						</div>
						
						<div class="treesubmenudiv">
							<a href="slaIncidentMetrics.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Incident Metrics</a>
						</div>
						
						</div>
						</td>
					</tr>	
					<tr>
						<td valign="top" class="menudivider"><img
							src="images/spacer.gif" alt="spacer" width="1" height="7" /></td>
					</tr>		
					<%-- Added for Incident SLA Metrics -- End --%>			
				</table>
				</td>
				<td width="2%" class="rightborder"></td>
				<td width="80%">
				<table><%}%>
					<tr valign="top">
						<td valign="middle" align="left"><span
							class="breadcrum_red_txt">Incident SLA Metrics</span></td>
				
							<td colspan=2 border=0 align=right>
				<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<a  href="javascript:download(this.form);" align=right style="text-decoration:none" title="Download to Excel"> 
				<%if(_flag.equals("false")){%><IMG SRC="images/excel.gif" WIDTH="18" HEIGHT="18" BORDER=0 ALT="download to excel"  /><%}%></a>
<%}%>		</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
		<c:if test="${checkRole=='PGM'}">			
					<tr>
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<td width="70%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								<%if(_flag.equals("false")){%><td>
								<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
									class="boxtable_greyborder_padding">
									<tr><%} %>
										<td class="headerboldtxt_greybg_greyborder" colspan="7">Volume of the Tickets</td>
									</tr>
									<tr>
										<td class="headerboldtxt_greybg_greyborder">
										<div align="left">Month</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Resolved/Closed</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Assigned</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Pending</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Suspended</div>
										</td>	
										<td class="bggrey_bottombordergrey">
										<div align="left">Work in Progress</div>
										</td>											
									</tr>
							<c:forEach items="${metrics.asOnDateTicketVolume}" var="asOnDateTicketVolumeList">
									<tr>
										<td class="bggrey_bottombordergrey">
										<div align="left"><c:out value="${asOnDateTicketVolumeList.month}" /></div>
										</td>										
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
											<center>
												<%if(_flag.equals("false")){%>
												<a href="homepagereport.spring?severity=Severity3" class="linknormal"><%}%>
												<c:out	value="${asOnDateTicketVolumeList.resolvedCount}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>		
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${asOnDateTicketVolumeList.assignedCount}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${asOnDateTicketVolumeList.pendingCount}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>						
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${asOnDateTicketVolumeList.suspendedCount}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${asOnDateTicketVolumeList.wipCount}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
									</tr>									
								</c:forEach>
								</table>
								</td>
							
							<%if(_flag.equals("true")){%>
							<td class="bggrey_bottombordergrey">
							&nbsp</td><%}%>
							
							</tr>
						</table>
						</td>
					</tr>
						<%} %>
			</c:if>
					<tr>

						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<td width="70%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								<%if(_flag.equals("false")){%><td>
								<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
									class="boxtable_greyborder_padding">
									<tr><%} %>
										<td class="headerboldtxt_greybg_greyborder" colspan="6">Acknowledgement SLA Adherence</td>
									</tr>
									<tr>
										<td class="headerboldtxt_greybg_greyborder">
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
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
										<c:out	value="${metrics.incidentAckSlaMap.ackpriority1met}" />
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
											<center>
												<%if(_flag.equals("false")){%>
												<a href="homepagereport.spring?severity=Severity3" class="linknormal"><%}%>
												<c:out	value="${metrics.incidentAckSlaMap.ackpriority2met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>		
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentAckSlaMap.ackpriority3met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentAckSlaMap.ackpriority4met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>						
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">Not Met</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=Severity2" class="linknormal"><%}%>
										<c:out	value="${metrics.incidentAckSlaMap.ackpriority1notmet}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.ackpriority2notmet}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.ackpriority3notmet}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.ackpriority4notmet}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
									<%if(_flag.equals("false")){%></tr>
								</table>
								</td><%} %>
							
							<%if(_flag.equals("true")){%>
							<td class="bggrey_bottombordergrey">
							&nbsp</td><%}%>
							
							</tr>
						</table>
						</td>
					</tr>
						<%} %>
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="6">Acknowledgement SLA Adherence in %</td>
							</tr>
<tr>
										<td class="headerboldtxt_greybg_greyborder">
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
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
										<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_met_percent}" />%
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
											<center>
												<%if(_flag.equals("false")){%>
												<a href="homepagereport.spring?severity=Severity3" class="linknormal"><%}%>
												<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>		
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentAckSlaMap.priority_3_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentAckSlaMap.priority_4_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>						
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">Not Met</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=Severity2" class="linknormal"><%}%>
										<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_not_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center></td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_not_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.priority_3_sla_not_met_percent}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentAckSlaMap.priority_4_sla_not_met_percent}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>							
									</tr>
						</table>
						</td><%} %>
					</tr>
					
					<c:if test="${checkRole=='PGM'}">
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="10">Acknowledgment SLA Adherence - Onsite vs Offshore</td>
							</tr>
							<tr>
										<td class="headerboldtxt_greybg_greyborder" rowspan="2">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 4</div>
										</td>	
									</tr>
									<tr>										
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>	
									</tr>									
									<tr>
										<td class="bggrey_bottombordergrey">
										<div align="left">Offshore</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority1met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority1notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority2met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority2notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority3met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority3notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority4met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority4notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>					
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">OnShore</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority1met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority1notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority2met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority2notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority3met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority3notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority4met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.ackpriority4notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>				
									</tr>
						</table>
						</td><%} %>
					</tr>
	
							<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="10">Acknowledgment SLA Adherence - Onsite vs Offshore in %</td>
							</tr>
							<tr>
										<td class="headerboldtxt_greybg_greyborder" rowspan="2">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 4</div>
										</td>	
									</tr>
									<tr>										
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>	
									</tr>									
									<tr>
										<td class="bggrey_bottombordergrey">
										<div align="left">Offshore</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_3_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_3_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_4_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_4_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>					
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">OnShore</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_1_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_2_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_3_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_3_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_4_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentAckSlaMap.priority_4_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>				
									</tr>
						</table>
						</td><%} %>
					</tr>		
				</c:if>						
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>

						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<td width="70%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								<%if(_flag.equals("false")){%><td>
								<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
									class="boxtable_greyborder_padding">
									<tr><%} %>
										<td class="headerboldtxt_greybg_greyborder" colspan="6">Resolution SLA Adherence</td>
									</tr>
									<tr>
										<td class="headerboldtxt_greybg_greyborder">
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
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
										<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1met}" />
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
											<center>
												<%if(_flag.equals("false")){%>
												<a href="homepagereport.spring?severity=Severity3" class="linknormal"><%}%>
												<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>		
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentResolutionSlaMap.resolutionpriority3met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentResolutionSlaMap.resolutionpriority4met}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>						
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">Not Met</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=Severity2" class="linknormal"><%}%>
										<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1notmet}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2notmet}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority3notmet}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority4notmet}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
									<%if(_flag.equals("false")){%></tr>
								</table>
								</td><%} %>
							
							<%if(_flag.equals("true")){%>
							<td class="bggrey_bottombordergrey">
							&nbsp</td><%}%>
							
							</tr>
						</table>
						</td>
					</tr>
						<%} %>
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="6">Resolution SLA Adherence in %</td>
							</tr>
<tr>
										<td class="headerboldtxt_greybg_greyborder">
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
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
										<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_met_percent}" />%
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
											<center>
												<%if(_flag.equals("false")){%>
												<a href="homepagereport.spring?severity=Severity3" class="linknormal"><%}%>
												<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>		
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a href="homepagereport.spring?severity=Severity5" class="linknormal"><%}%>
											<c:out value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>						
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">Not Met</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=Severity2" class="linknormal"><%}%>
										<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_not_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center></td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_not_met_percent}" />%
											<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_not_met_percent}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>										
										<td class="bgwhite_bottombordergrey_number">
											<center><%if(_flag.equals("false")){%>
											<a id="sev1" href="homepagereport.spring?severity=Severity4" class="linknormal"><%}%>
											<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_not_met_percent}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>							
									</tr>
						</table>
						</td><%} %>
					</tr>
			<c:if test="${checkRole=='PGM'}">
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="10">Resolution SLA Adherence - Onsite vs Offshore</td>
							</tr>
							<tr>
										<td class="headerboldtxt_greybg_greyborder" rowspan="2">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 4</div>
										</td>	
									</tr>
									<tr>										
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>	
									</tr>									
									<tr>
										<td class="bggrey_bottombordergrey">
										<div align="left">Offshore</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority3met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority3notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority4met_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority4notmet_offshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>					
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">OnShore</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority1notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority2notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority3met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority3notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority4met_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolutionpriority4notmet_onshore}" />
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>				
									</tr>
						</table>
						</td><%} %>
					</tr>
	
							<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
					<tr>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="10">Resolution SLA Adherence - Onsite vs Offshore in %</td>
							</tr>
							<tr>
										<td class="headerboldtxt_greybg_greyborder" rowspan="2">
										<div align="left">SLA</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 1</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 2</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 3</div>
										</td>
										<td class="bggrey_bottombordergrey" colspan="2">
										<div align="left">Priority 4</div>
										</td>	
									</tr>
									<tr>										
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Met</div>
										</td>
										<td class="bggrey_bottombordergrey">
										<div align="left">Not Met</div>
										</td>	
									</tr>									
									<tr>
										<td class="bggrey_bottombordergrey">
										<div align="left">Offshore</div>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_not_met_percent_offshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>					
									</tr>
									<tr>
										<td class="bggrey_bottombordergrey">
											<div align="left">OnShore</div>
										</td>	
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity1" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_1_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity2" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_2_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity3" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_3_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>
										<td class="bgwhite_bottombordergrey_number">
											<center>
												<%if(_flag.equals("false")){%>
													<a id="sev1" class="linknormal" href="homepagereport.spring?severity=Severity4" ><%}%>
													<c:out	value="${metrics.incidentResolutionSlaMap.resolution_priority_4_sla_not_met_percent_onshore}" />%
												<%if(_flag.equals("false")){%></a><%}%></center>
										</td>				
									</tr>
						</table>
						</td><%} %>
					</tr>	
				</c:if>												
				</table>


				</td>




				<td width="1%" align="left" class="rightborder"><img
					src="images/spacer.gif" width="15" height="6" /></td>
			</tr>
		</table>

		</td>


	</tr>

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
