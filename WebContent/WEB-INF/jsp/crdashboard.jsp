<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
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
archivalyear="2016";

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
	
    document.CrForm.action="./excel.spring?excel=true";
	document.CrForm.submit();
}
function homePageReport()
{

document.CrForm.action="./login.spring";
	document.CrForm.submit();
	
}
function logout()
{

document.CrForm.action="./login.spring";
document.CrForm.submit();
	
}

function submitMonthlyCRdata()
{
document.forms[0].method="post";
document.CrForm.action="./home.spring";
	document.CrForm.submit();
	
}

</script>
<%}
if (_flag.equals("false")) {
		response.setContentType ("text/html") ;
    	}
	else{
	response.setContentType("application/vnd.ms-excel"); 
	response.setHeader("Content-Disposition", "attachment;filename=crdashboard_Report.xls");
	 }%>
	 
	 
	 <%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);
User user=(User)session.getAttribute("userInformation");

List<String> monthsList = new ArrayList<String>();
Calendar cal = Calendar.getInstance();
int year = cal.get(cal.YEAR);
String[] months = new DateFormatSymbols().getMonths();        
for (int i = 0; i < months.length-1; i++) {
   String month = months[i];      
   month = month.substring(0, 3);
   monthsList.add(month+"-"+year);
}
request.setAttribute("monthsList",monthsList);

%>
</head>

<body>

<form method="post" name="CrForm" action="/trackingtool/crsummaryreport.spring">
<c:set var="ttSummary" value="${homeVO.homePageMap.ttSummary}" />

<c:set var="userInfo" value="${homeVO.homePageMap.userInfo}" />
<c:set var="curDate" value="${homeVO.homePageMap.currDate}" />
<c:set var="selectedMonth" value="${homeVO.homePageMap.selectedMonth}" />
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
							class="linknormal">Home</a> | <a href="JavaScript:logout();"
							class="linknormal">Logout</a> </div>
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
								
								<div class="treesubmenudiv"><a href="archivedData_home.spring?month=ALL"
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
							href="moduleMappings.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Module Mappings</a></div>
						<div class="treesubmenudiv"><a
							href="applicationMap.spring" class="treesubmenu"><img
							src="images/submenu_arrow.gif" border="0px" alt="submenu arrow"
							align="absmiddle" />Module with no or 1 Owner</a></div>
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
				</table>
				</td>
				<td width="2%" class="rightborder"></td>
				<td width="80%">
				<table><%}%>
					<tr valign="top">
						<td valign="middle" align="left"><span
							class="breadcrum_red_txt">Tracking Tool Home</span></td>
				
							<td colspan=2 border=0 align=middle>
				<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<a  href="javascript:download(this.form);" align=right style="text-decoration:none" title="Download to Excel"> 
				<%if(_flag.equals("false")){%><IMG SRC="images/excel.gif" WIDTH="18" HEIGHT="18" BORDER=0 ALT="download to excel"  /><%}%></a>
<%}%>		</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					
					<tr>
					<td width="3%" class="headerboldtxt_greybg_greyborder" colspan="1" height="10"> Please select a Month: &nbsp;&nbsp;<select id="selectedMonth" name="selectedMonth" onchange="javascript:submitMonthlyCRdata();">
                           <option value="ALL">ALL</option>
                           <c:forEach var="monthList" items="${monthsList}">
                                          <option value="${monthList}" ${monthList == selectedMonth ? 'selected="selected"' : ''}>${monthList}</option>
                                  </c:forEach>
                           </select>
                           
                           <input type="hidden" name="selectedMonth" value="${selectedMonth}"/>
                           </td>
                           
					
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					
					<tr>

						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								<%if(_flag.equals("false")){%>
								<td>
								 <table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
									class="boxtable_greyborder_padding">
									<tr><%} %>
										<td class="headerboldtxt_greybg_greyborder" colspan="6">Open
										CRs by Priority</td>
									</tr>
									<tr>
									<td class="bggrey_bottombordergrey" width="20%">
										<div align="left"></div>
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
										<div align="left">Total</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
											<center>
											<c:out value="${ttSummary.byPriority.OffShore.Location}"></c:out>	
											</center>								
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="crsummaryreport.spring?priority=p1" ><%}%>
										<c:out	value="${ttSummary.byPriority.OffShore.P1}" />
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=p2" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OffShore.P2}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=p3" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OffShore.P3}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a id="sev1" href="crsummaryreport.spring?priority=p4" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OffShore.P4}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=ALL" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OffShore.Total}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>

									</tr>
									  <%
									  
									  String rolevalue = (String) pageContext.getAttribute("checkRole");
									//  System.out.println(" rolevalue rolevalue "+rolevalue);
									  if( rolevalue == null)
									  {
									   rolevalue = (String)session.getAttribute("checkRole");
									  }
									  
									  if(rolevalue != null && rolevalue.equals("PGM")){
									    session.setAttribute("checkRole",rolevalue);
									  %> 
							
									
									<tr>
										<td class="bgwhite_bottombordergrey_number">
											<center>
											<c:out value="${ttSummary.byPriority.OnShore.Location}"></c:out>	
											</center>								
										</td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="crsummaryreport.spring?priority=p1" ><%}%>
										<c:out	value="${ttSummary.byPriority.OnShore.P1}" />
										<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=p2" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OnShore.P2}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=p3" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OnShore.P3}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a id="sev1" href="crsummaryreport.spring?priority=p4" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OnShore.P4}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?priority=ALL" class="linknormal"><%}%>
										<c:out	value="${ttSummary.byPriority.OnShore.Total}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
									
									
									</tr>
								  
								  
									<% } if(_flag.equals("false")){%>
									
								</table>
								</td>
								
								<%} %>
							
							<%if(_flag.equals("true")){%>
							<td class="bggrey_bottombordergrey">
							&nbsp</td><%}%>
							
							</tr>
						</table>
						</td>
						<%} %>
						</tr>
						
						<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
						<tr>
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<%if(_flag.equals("false")){%><%}%>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
							class="boxtable_greyborder_padding">
							<tr>
								<td class="headerboldtxt_greybg_greyborder" colspan="6">Open CRs by Age</td>
							</tr>
							<tr>
								<td class="bggrey_bottombordergrey" width="20%">
								<div align="left">         </div>
								</td>
								<td class="bggrey_bottombordergrey">
								<div align="left">&gt;2wk &lt;=3wk</div>
								</td>
								<td class="bggrey_bottombordergrey">
								<div align="left">&gt;3wk &lt;=8wk</div>
								</td>
								<td class="bggrey_bottombordergrey">
								<div align="left">&gt;8wk</div>
								</td>
								<td class="bggrey_bottombordergrey">
								<div align="left">Total</div>
								</td>
							</tr>
							<tr>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.Location}">
										<center>										
										<c:out value="${ttSummary.byAge.OffShore.Location}" /></center>
									</c:when>
									
								</c:choose></td>
								
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_1}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_1" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.byAge.OffShore.GRP_1}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_2}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_2" class="linknormal"><%}%>
										<c:out value="${ttSummary.byAge.OffShore.GRP_2}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_3}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_3" class="linkred" ><%}%>
										<c:out value="${ttSummary.byAge.OffShore.GRP_3}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=ALL" class="linknormal"><%}%>
								<c:out	value="${ttSummary.byAge.OffShore.Total}" />
									<%if(_flag.equals("false")){%></a><%}%></center>
									</td>
							</tr>
							<c:choose>
								<c:when test="${checkRole =='PGM'}">
							<tr>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OnShore.Location}">
										<center>										
										<c:out value="${ttSummary.byAge.OnShore.Location}" /></center>
									</c:when>
									
								</c:choose></td>
								
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OnShore.GRP_1}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_1" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.byAge.OnShore.GRP_1}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OnShore.GRP_2}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_2" class="linknormal"><%}%>
										<c:out value="${ttSummary.byAge.OnShore.GRP_2}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OnShore.GRP_3}">
										<center><%if(_flag.equals("false")){%>
										<a href="crsummaryreport.spring?age=GRP_3" class="linkred" ><%}%>
										<c:out value="${ttSummary.byAge.OnShore.GRP_3}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=ALL" class="linknormal"><%}%>
								<c:out	value="${ttSummary.byAge.OnShore.Total}" />
									<%if(_flag.equals("false")){%></a><%}%></center>
									</td>
							</tr>
							</c:when>
							</c:choose>
						</table>
						</td><%} %>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<%String width1="width='100%'";
					String width2="width='100%'";
					String width3="width='65%'";
					if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ 
					width1="";
					width2="";
					width3="width='100%'";
					%>	
						<%}%>
						
						
						<tr <%=width1%>>
						<td <%=width2%> align="center">
						<table <%=width3%> border="<%=border%>" cellspacing="0" cellpadding="0"
							class="boxtable_greyborder_padding">
							<tr>
							
							<c:choose>
								<c:when test="${checkRole =='PGM'}">									
								      <td colspan="13" class="headerboldtxt_greybg_greyborder">Summary	of CRs</td>
								</c:when>
								<c:otherwise>
									<td colspan="7" class="headerboldtxt_greybg_greyborder">Summary	of CRs</td>
                               </c:otherwise>
							</c:choose>
							</tr>
							<tr>
								<%String colspan="1";
								if(_flag.equals("true")){
								colspan="2";%>
								<%}%>
								<td width="40%" class="headerboldtxt_greybg_greyborder"
									rowspan="2" colspan="<%=colspan%>">CR Status</td>
								<td width="48%" class="headerboldtxt_greybg_greyborder"
									colspan="6" align="center"><center><c:out
									value="${ttSummary.byStatus.OffShore.Location}" /></center>
								</td>
								<c:choose>
								  <c:when test="${checkRole !='TM'}">
									<c:if test="${checkRole=='PGM'}">
										<td width="48%" class="headerboldtxt_greybg_greyborder"
											colspan="14" align="center"><center><c:out
											value="${ttSummary.byStatus.OnShore.Location}" /></center>
										</td>
									</c:if>	
								</c:when>
							</c:choose>
							</tr>
							
							<tr>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Accept RfC & Prepare Approval</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Validate & Assign</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Approval</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Implementation</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Post Implementation Review</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Closed</td>
								
								
							<c:choose>
								  <c:when test="${checkRole !='TM'}">
									<c:if test="${checkRole=='PGM'}">

									<td width="16%" class="headerboldtxt_greybg_greyborder">Accept RfC & Prepare Approval</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Validate & Assign</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Approval</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Implementation</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Post Implementation Review</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Closed</td>
								
							
							</c:if>	
								  </c:when>
							    </c:choose>
							</tr>
							
							
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">ABAP</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.ABAP}" />
									<%if(_flag.equals("false")){%></a><%}%></center></td>
																
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ABAP" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									
								<c:choose>
									<c:when test="${checkRole =='PGM'}">	

									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.ABAP}" />
									<%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ABAP" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							</c:when>
						</c:choose>	
					</tr>
							
					<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">MM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							
							<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
							<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=MM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=MM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
							
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=MM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=MM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								  </c:when>
						   </c:choose>	
						</tr>								
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">SD</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=SD" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=SD" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								  <td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=SD" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=SD" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
						   </c:choose>
								</tr>
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
						
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PP" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PP" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PP" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

									
									
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">						
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PP" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PP" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number"><center>
								<%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PP" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
						</c:when>
						   </c:choose>
								</tr>
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PI</td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
							</c:when>
						   </c:choose>
						</tr>

							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">FI</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=FI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=FI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=FI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=FI" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">CO</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=CO" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=CO" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=CO" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=CO" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">TSIM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=TSIM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=TSIM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=TSIM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=TSIM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">Transport</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=Transport" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=Transport" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=Transport" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=Transport" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">WM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=WM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=WM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=WM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=WM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">ITS Mobile</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ITSMobile" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ITSMobile" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ITSMobile" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ITSMobile" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">QM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=QM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=QM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=QM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=QM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=PM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=PM" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">Total</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.AccRfcAndPrepApp.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.ValidAndAssign.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Approval.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Implementation.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ALL" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.PostImpReview.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ALL" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OffShore.Closed.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=AccRfcAndPrepApp&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.AccRfcAndPrepApp.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=ValidAndAssign&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.ValidAndAssign.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Approval&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Approval.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Implementation&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Implementation.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=PostImpReview&module=ALL" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.PostImpReview.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?phase=Closed&module=ALL" class="linknormal"><%}%><c:out
									value="${ttSummary.byStatus.OnShore.Closed.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								</c:when>
						   </c:choose>
						</tr>
						</tr>
						<tr>
                                         <td colspan="3">&nbsp;</td>
                                  </tr>
								  </table>
								  <table  >
						<tr>
						<td width="1%" align="left" class="rightborder"><img
					src="images/spacer.gif" width="15" height="12" /></td>
			
						<tr>
						</tr>	
							<tr>
							<table width="100%" border="0px" cellspacing="0" cellpadding="0" class="boxtable_greyborder_padding" align="left">
							<tr>
							<c:choose>
								<c:when test="${checkRole =='PGM'}">
								<td colspan="7" class="headerboldtxt_greybg_greyborder">Open CRs by Age - Module Wise</td>
							</c:when>
							<c:otherwise>
							    <td colspan="4" class="headerboldtxt_greybg_greyborder">Open CRs by Age - Module Wise</td>
							</c:otherwise>
							</c:choose>	
							</tr>
							<tr>
								<td width="20%" class="headerboldtxt_greybg_greyborder"
									rowspan="2">Age</td>
								<td width="48%" class="headerboldtxt_greybg_greyborder"
									colspan="3"><center><c:out	value="${ttSummary.byAgeModuleWise.OffShore.Location}" /></center></td>
								
							   <c:choose>
								<c:when test="${checkRole =='PGM'}">	
									<td width="48%" class="headerboldtxt_greybg_greyborder"
									colspan="3"><center><c:out	value="${ttSummary.byAgeModuleWise.OnShore.Location}" /></center></td>								
								</c:when>
							</c:choose>	

							</tr>
							
							<tr>
								<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;2wk &lt;=3wk</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;3wk &lt;=8wk</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;8wk</td>
							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
									<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;2wk &lt;=3wk</td>
									<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;3wk &lt;=8wk</td>
									<td width="16%" class="headerboldtxt_greybg_greyborder">&gt;8wk</td>								
								</c:when>
							</c:choose>	
								
							</tr>
							
							<tr>
								<td width="20%" class="bggrey_bottombordergrey">ABAP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ABAP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

								</c:when>
							</c:choose>	
								
							</tr>	

							<tr>
								<td width="20%" class="bggrey_bottombordergrey">MM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=MM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">SD</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">PP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PP" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">PI</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">FI</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=FI" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">CO</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=CO" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">TSIM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=TSIM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">Transport</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=Transport" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">WM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=WM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">ITS Mobile</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ITSMobile" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">QM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=QM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">PM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=PM" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">Total</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_1.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_2.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OffShore.GRP_3.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_1&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_1.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_2&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_2.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="crsummaryreport.spring?age=GRP_3&module=ALL" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeModuleWise.OnShore.GRP_3.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						</table>
						</tr>
					
				</table>


				</td>




				<td width="1%" align="left" class="rightborder"><img
					src="images/spacer.gif" width="15" height="6" /></td>
			</tr>
		</table>

		</td>


	</tr>

<%if(_flag.equals("false")){%>
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="1%" align="left" class="leftborder"><img
					src="images/spacer.gif" width="15" height="10%" /></td>
				<td width="85%">
				<table width="100%" height="113" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3" id="double_line"><img
							src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
					</tr>
					<%if(!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>
					<tr>
						<td height="86">&nbsp;</td>
						<td>
						
						</td>
					</tr>
					
					<tr>
						<td height="15" colspan="3" id="banner_grey"><img
							src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
					</tr>
				</table>
				</td>
				<td width="1%" align="left" class="rightborder"><img
					src="images/spacer.gif" width="15" height="30%" /></td>
			</tr><% }%>
		</table>
		</td>
	</tr>


<%}%>
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
