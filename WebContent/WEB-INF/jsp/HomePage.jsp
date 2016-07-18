<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormatSymbols"%>

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
	
    document.HomePageForm.action="archivedData_home.spring?excel=true";
	document.HomePageForm.submit();
}
function homePageReport()
{

document.HomePageForm.action="archivedData.spring?task=homePageReportCurrentSelection";
	document.HomePageForm.submit();
	
}

function submitDropDown()
{
       document.forms[0].method="get";
document.forms[0].action="archivedData_home.spring";
document.forms[0].submit(); 
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

<form method="post" name="ArchivedDataForm" action="?task=getDetails">
<c:set var="ttSummary" value="${homeVO.homePageMap.ttSummary}" />

<c:set var="userInfo" value="${homeVO.homePageMap.userInfo}" />
<c:set var="curDate" value="${homeVO.homePageMap.currDate}" />
<c:set var="selectedMonth1" value="${homeVO.homePageMap.selectedMonth}" />


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
							alt="Satyam" width="130" height="100" /></div>
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
									border="0px" alt="submenu arrow" align="absmiddle" />Upload Incident
								Information</a></div>
								<div class="treesubmenudiv"><a
									href="addApplication.spring" class="treesubmenu"><img
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
								Application Owners</a></div>
								<div class="treesubmenudiv"><a href="deleteUser.spring?task=getDetails"
									class="treesubmenu"><img src="images/submenu_arrow.gif"
									border="0px" alt="submenu arrow" align="absmiddle" />Delete
								User/Application</a></div>
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

						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<td width="48%">
						<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0">
							<tr>
								<tr>
                <td width="3%" class="headerboldtxt_greybg_greyborder" colspan="1" height="10"> Please select a Month: &nbsp;&nbsp;<select id="selectedMonth" name="selectedMonth" onchange="javascript:submitDropDown();">
                           <option value="ALL">ALL</option>
                           <c:forEach var="monthList" items="${monthsList}">
                                          <option value="${monthList}" ${monthList == selectedMonth1 ? 'selected="selected"' : ''}>${monthList}</option>
                                  </c:forEach>
                                  		
                           </select></td>
                           
                     
                
                                </tr>
								<tr><td>&nbsp;</td></tr>
							
								<%if(_flag.equals("false")){%><td>
								<table width="100%" border="<%=border%>" cellpadding="0" cellspacing="0"
									class="boxtable_greyborder_padding">
									<tr><%} %>
										<td class="headerboldtxt_greybg_greyborder" colspan="6">Open
										Incidents by Priority</td>
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
										<td class="bggrey_bottombordergrey">
										<div align="left">Total</div>
										</td>
									</tr>
									<tr>
										<td class="bgwhite_bottombordergrey_number">
                                             <center>
                                             <c:out value="${ttSummary.bySeverity.OffShore.Location}"></c:out>    
                                             </center>                                                    
                                        </td>

										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a id="sev1" class="linknormal" href="homepagereport.spring?severity=p1&month=${selectedMonth1}" ><%}%>
										<c:out
											value="${ttSummary.bySeverity.OffShore.P1}" />
									   <%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center>
										<%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=p2&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.bySeverity.OffShore.P2}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number" class="linknormal">
										<center><%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=p3&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.bySeverity.OffShore.P3}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a id="sev1" href="homepagereport.spring?severity=p4&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.bySeverity.OffShore.P4}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
										
										<td class="bgwhite_bottombordergrey_number">
										<center><%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?severity=ALL&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.bySeverity.OffShore.Total}" />
											<%if(_flag.equals("false")){%></a><%}%></center></td>
											
											
									</tr>
                                    <%
                                    	String rolevalue = (String) pageContext.getAttribute("checkRole");
                                        System.out.println(" rolevalue rolevalue "+rolevalue);
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
                                                                           <c:out value="${ttSummary.bySeverity.OnShore.Location}"></c:out>     
                                                                           </center>                                                    
                                                                     </td>
                                                                     <td class="bgwhite_bottombordergrey_number">
                                                                     <center>
                                                                     <%if(_flag.equals("false")){%>
                                                                     <a id="sev1" class="linknormal" href="homepagereport.spring?severity=p1&month=${selectedMonth1}" ><%}%>
                                                                     <c:out       value="${ttSummary.bySeverity.OnShore.P1}" />
                                                                     <%if(_flag.equals("false")){%></a><%}%></center></td>
                                                                     <td class="bgwhite_bottombordergrey_number">
                                                                     <center>
                                                                     <%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?severity=p2&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out       value="${ttSummary.bySeverity.OnShore.P2}" />
                                                                           <%if(_flag.equals("false")){%></a><%}%></center></td>
                                                                     <td class="bgwhite_bottombordergrey_number" class="linknormal">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?severity=p3&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out       value="${ttSummary.bySeverity.OnShore.P3}" />
                                                                           <%if(_flag.equals("false")){%></a><%}%></center></td>
                                                                     <td class="bgwhite_bottombordergrey_number">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a id="sev1" href="homepagereport.spring?severity=p4&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out       value="${ttSummary.bySeverity.OnShore.P4}" />
                                                                           <%if(_flag.equals("false")){%></a><%}%></center></td>
                                                                     
                                                                     <td class="bgwhite_bottombordergrey_number">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?severity=ALL&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out       value="${ttSummary.bySeverity.OnShore.Total}" />
                                                                           <%if(_flag.equals("false")){%></a><%}%></center></td>
                                                              
                                                              
                                                              </tr>		
											
											
											
									
									<%}if(_flag.equals("false")){%></tr>
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
								<td class="headerboldtxt_greybg_greyborder" colspan="6">Open
								Incidents by Age</td>
							</tr>
							<tr>
							
								<td class="bggrey_bottombordergrey" width="20%">
                                                       <div align="left">         </div>
                                                       </td>
								<td class="bggrey_bottombordergrey">
								<div align="left">&lt;=1wk</div>
								</td>
								<td class="bggrey_bottombordergrey">
								<div align="left">&gt;1wk &lt;=3wk</div>
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
										<a href="homepagereport.spring?age=GRP_1&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out value="${ttSummary.byAge.OffShore.GRP_1}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_2}">
										<center><%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?age=GRP_2&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out
											value="${ttSummary.byAge.OffShore.GRP_2}" />
											<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_3}">
										<center><%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?age=GRP_3&month=${selectedMonth1}" class="linknormal"><%}%>
										<c:out value="${ttSummary.byAge.OffShore.GRP_3}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number"><c:choose>
									<c:when test="${!empty ttSummary.byAge.OffShore.GRP_4}">
										<center><%if(_flag.equals("false")){%>
										<a href="homepagereport.spring?age=GRP_4&month=${selectedMonth1}" class="linkred" ><%}%>
										<c:out value="${ttSummary.byAge.OffShore.GRP_4}" />
										<%if(_flag.equals("false")){%></a><%}%></center>
									</c:when>
									<c:otherwise><center>0</center></c:otherwise>
								</c:choose></td>
								<td class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=ALL&month=${selectedMonth1}" class="linknormal"><%}%>
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
                                                                     <a href="homepagereport.spring?age=GRP_1&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out
                                                                            value="${ttSummary.byAge.OnShore.GRP_1}" />
                                                                           <%if(_flag.equals("false")){%></a><%}%></center>
                                                              </c:when>
                                                              <c:otherwise><center>0</center></c:otherwise>
                                                       </c:choose></td>
                                                       <td class="bgwhite_bottombordergrey_number"><c:choose>
                                                              <c:when test="${!empty ttSummary.byAge.OnShore.GRP_2}">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?age=GRP_2&month=${selectedMonth1}" class="linknormal"><%}%>
                                                                     <c:out value="${ttSummary.byAge.OnShore.GRP_2}" />
                                                                     <%if(_flag.equals("false")){%></a><%}%></center>
                                                              </c:when>
                                                              <c:otherwise><center>0</center></c:otherwise>
                                                       </c:choose></td>
                                                       <td class="bgwhite_bottombordergrey_number"><c:choose>
                                                              <c:when test="${!empty ttSummary.byAge.OnShore.GRP_3}">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?age=GRP_3&month=${selectedMonth1}" class="linkred" ><%}%>
                                                                     <c:out value="${ttSummary.byAge.OnShore.GRP_3}" />
                                                                     <%if(_flag.equals("false")){%></a><%}%></center>
                                                              </c:when>
                                                              <c:otherwise><center>0</center></c:otherwise>
                                                       </c:choose></td>
                                                       
                                                       <td class="bgwhite_bottombordergrey_number"><c:choose>
                                                              <c:when test="${!empty ttSummary.byAge.OnShore.GRP_4}">
                                                                     <center><%if(_flag.equals("false")){%>
                                                                     <a href="homepagereport.spring?age=GRP_4&month=${selectedMonth1}" class="linkred" ><%}%>
                                                                     <c:out value="${ttSummary.byAge.OnShore.GRP_4}" />
                                                                     <%if(_flag.equals("false")){%></a><%}%></center>
                                                              </c:when>
                                                              <c:otherwise><center>0</center></c:otherwise>
                                                       </c:choose></td>
                                                       
                                                       <td class="bgwhite_bottombordergrey_number">
                                                       <center><%if(_flag.equals("false")){%>
                                                       <a href="homepagereport.spring?age=ALL" class="linknormal"><%}%>
                                                       <c:out value="${ttSummary.byAge.OnShore.Total}" />
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
								      <td colspan="13" class="headerboldtxt_greybg_greyborder">Summary of Incidents</td>
								</c:when>
								<c:otherwise>
									<td colspan="7" class="headerboldtxt_greybg_greyborder">Summary	of Incidents</td>
                               </c:otherwise>
							</c:choose>
							</tr>
							<tr>
								<%String colspan="1";
								if(_flag.equals("true")){
								colspan="2";%>
								<%}%>
								<td width="40%" class="headerboldtxt_greybg_greyborder"
									rowspan="2" colspan="<%=colspan%>">Incident Status</td>
								<td width="48%" class="headerboldtxt_greybg_greyborder"
									colspan="6" align="center"><center><c:out
									value="${ttSummary.byStatus.OffShore.Location}" /></center>
								</td>
								<c:choose>
								  <c:when test="${checkRole !='TM'}">
									<c:if test="${checkRole=='PGM'}">
										<td width="48%" class="headerboldtxt_greybg_greyborder"
											colspan="6" align="center"><center><c:out
											value="${ttSummary.byStatus.OnShore.Location}" /></center>
										</td>
									</c:if>	
								</c:when>
							</c:choose>
							</tr>
							
							<tr>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Assigned</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Closed</td>
								<td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">Pending Customer</td>
								<td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">Work in Progress</td>
															
								
							<c:choose>
								  <c:when test="${checkRole !='TM'}">
									<c:if test="${checkRole=='PGM'}">

									<td width="16%" class="headerboldtxt_greybg_greyborder">Assigned</td>
								<td width="16%" class="headerboldtxt_greybg_greyborder">Closed</td>
								<td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">Pending Customer</td>
								<td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">Work in Progress</td>
								
							
							</c:if>	
								  </c:when>
							    </c:choose>
							</tr>
							
								
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">ABAP</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.ABAP}" />
									<%if(_flag.equals("false")){%></a><%}%></center></td>
																
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
																							
								<c:choose>
									<c:when test="${checkRole =='PGM'}">	

									
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.ABAP}" />
									<%if(_flag.equals("false")){%></a><%}%></center></td>
																
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							</c:when>
						</c:choose>	
					</tr>
						
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">MM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							
							<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
							<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							
							
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
							
							<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
							<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

								  </c:when>
						   </c:choose>	
						
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">SD</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
									
								<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								  
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								</c:when>
						   </c:choose>
								</tr>
								
								
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
						
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								

									
									
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">						
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
						
									<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>								
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
						</c:when>
						   </c:choose>
								</tr>
								
								
								
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PI</td>

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								

								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
									
							</c:when>
						   </c:choose>
						</tr>
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">FI</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
							
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
								value="${ttSummary.byStatus.OnShore.Assigned.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
						
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">CO</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								</c:when>
						   </c:choose>
						</tr>
						
							
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">TSIM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2"  class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
							
							
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">Transport</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								</c:when>
						   </c:choose>
						</tr>
						
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">WM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
						
						
						<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">ITS Mobile</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								</c:when>
						   </c:choose>
						</tr>
						
						
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">QM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
							
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">PM</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								

							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
							
							
							<tr>
								<td width="52%" class="bggrey_bottombordergrey" colspan="<%=colspan%>">Total</td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Assigned.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Closed.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.Pending.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OffShore.WIP.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
							<c:choose>
								  <c:when test="${checkRole =='PGM'}">
								
								
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=assigned&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Assigned.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=closed&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Closed.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=pending&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.Pending.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?status=WIP&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byStatus.OnShore.WIP.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
						   </c:choose>
						</tr>
						
							
							
						
						</table>
						</td>
						
						</tr>                                         
                                         <tr>
                                         <td colspan="3">&nbsp;</td>
                                  </tr>
                        <tr>
						
						<%if(!archivalyear.equals("2009-2010")&&!archivalyear.equals("2008-2009")&&!archivalyear.equals("2007-2008")){ %>	
						<%if(_flag.equals("false")){%><%}%>
						<td width="40%">
						<table width="100%" border="<%=border%>" cellspacing="0" cellpadding="0"
							class="boxtable_greyborder_padding">
							<tr>
                                                <c:choose>
                                                       <c:when test="${checkRole =='PGM'}">
                                                       <td colspan="13" class="headerboldtxt_greybg_greyborder">Open Incidents by Age - Module Wise</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td colspan="7" class="headerboldtxt_greybg_greyborder">Open Incidents by Age - Module Wise</td>
                                                </c:otherwise>
                                                </c:choose>   
                                                </tr>
                                                <tr>
                                                       <td width="40%" class="headerboldtxt_greybg_greyborder"
                                                              rowspan="2">Age</td>
                                                       <td width="48%" class="headerboldtxt_greybg_greyborder"
                                                              colspan="6"><center><c:out       value="${ttSummary.byAgeRegionWise.OffShore.Location}" /></center></td>
                                                       
                                                <c:choose>
                                                       <c:when test="${checkRole =='PGM'}">     
                                                              <td width="48%" class="headerboldtxt_greybg_greyborder"
                                                              colspan="6"><center><c:out       value="${ttSummary.byAgeRegionWise.OnShore.Location}" /></center></td>                                                     
                                                       </c:when>
                                                </c:choose>   

                                                </tr>
                                                <tr>
                                                       <td width="16%" class="headerboldtxt_greybg_greyborder">&lt;=1wk</td>
                                                       <td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">&gt;1wk &lt;=3wk</td>
                                                       <td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">&gt;3wk &lt;=8wk</td>
                                                       <td width="16%" class="headerboldtxt_greybg_greyborder">&gt;8wk</td>
                                                       
                                                       
                                                       <c:choose>
                                                       <c:when test="${checkRole =='PGM'}">     
                                                       <td width="16%" class="headerboldtxt_greybg_greyborder">&lt;=1wk</td>
                                                       <td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">&gt;1wk &lt;=3wk</td>
                                                       <td width="16%" colspan="2" class="headerboldtxt_greybg_greyborder">&gt;3wk &lt;=8wk</td>
                                                       <td width="16%" class="headerboldtxt_greybg_greyborder">&gt;8wk</td>
                                                                                                           
                                                       </c:when>
                                                </c:choose>   
                                                       
                                                </tr>
                                <tr>
								<td width="20%" class="bggrey_bottombordergrey">ABAP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
									
									<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=ABAP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.ABAP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								</c:when>
							</c:choose>	
								
							</tr>
							<tr>
								<td width="20%" class="bggrey_bottombordergrey">MM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=MM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.MM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>		
								
								
								</c:when>
							</c:choose>	
						</tr>
							<tr>
								<td width="20%" class="bggrey_bottombordergrey">SD</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=SD" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=SD&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.SD}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
							<tr>
								<td width="20%" class="bggrey_bottombordergrey">PP</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PP&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.PP}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">PI</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.PI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">FI</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=FI&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.FI}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">CO</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%"  class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
										
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=CO&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.CO}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">TSIM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=TSIM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.TSIM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">Transport</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=Transport&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.Transport}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">WM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=WM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.WM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">ITS Mobile</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=ITSMobile&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.ITSMobile}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						
							<tr>
								<td width="20%" class="bggrey_bottombordergrey">QM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=QM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.QM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">PM</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
									
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=PM&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_4.PM}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						
						<tr>
								<td width="20%" class="bggrey_bottombordergrey">Total</td>
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_1.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_2.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_3.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	

							<c:choose>
								<c:when test="${checkRole =='PGM'}">	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_1&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_1.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_2&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_2.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								<td width="16%" colspan="2" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_3&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OnShore.GRP_3.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								<td width="16%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%>
								<a href="homepagereport.spring?age=GRP_4&module=total&month=${selectedMonth1}" class="linknormal"><%}%>
								<c:out
									value="${ttSummary.byAgeRegionWise.OffShore.GRP_4.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>	
								
								</c:when>
							</c:choose>	
						</tr>
						<!--<tr>
								<td width="52%" class="bgwhite_bottombordergrey_bold"
									colspan="6" align="right">Total</td>
								<td width="12%" class="bgwhite_bottombordergrey_number">
								<center><%if(_flag.equals("false")){%><a href="homepagereport.spring?age=all&module=total" class="linknormal"><%}%><c:out
									value="${ttSummary.byAgeRegionWise.OffShore.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
								
								<c:choose>
                                                       <c:when test="${checkRole =='PGM'}">            
                                                              <td width="52%" class="bgwhite_bottombordergrey_bold"
                                                              colspan="5" align="right">Total</td>
                                                       <td width="12%" class="bgwhite_bottombordergrey_number">
                                                       <center><%if(_flag.equals("false")){%><a href="homepagereport.spring?age=all&module=total" class="linknormal"><%}%><c:out
                                                              value="${ttSummary.byAgeRegionWise.OnShore.Total}" /><%if(_flag.equals("false")){%></a><%}%></center></td>
                                                              
                                                              </c:when>
                                                </c:choose>   	
								
							</tr>-->
						</table>
						</tr>
						
						
						
						
						
						
						
						
						
						
						
							
						</table>
						</td><%}%>
					</tr>
					<tr>
						<td height="55" colspan="3" align="center"
							class="bodytxt_red_center">The above status is based on data
						uploaded on <c:out value="${ttSummary.uploadDate}" /></td>
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
						<table width="27%" align="left"
							class="boxtable_greyborder_padding">
							<c:choose>
								<c:when test="${checkRole !='TM'}">
									<c:set var="adminInfo" value="${homeVO.homePageMap.adminInfo}" />
									<tr>
										<td align="left" width="88%" class="bodytxt_black">No. of
										users awaiting approval</td>
										<td width="12%" align="left" class="bodytxt_black">
										<c:choose>
									<c:when test="${checkRole =='PM'}"><a
							href="approveUser.spring?task=getUsers" class="linknormal"><c:out
											value="${adminInfo.usersAwaitingApproval}" /></a>
											
											</c:when>
									<c:otherwise><c:out value="${adminInfo.usersAwaitingApproval}"/></c:otherwise>
									</c:choose></td>
									</tr>
									<tr>
										<td align="left" width="88%" class="bodytxt_black">No. of
										Applications with no or 1 owner</td>
										<td width="12%" align="left" class="bodytxt_black">
										
										<a href="applicationMap.spring" class="linknormal">
										<c:out value="${adminInfo.appWithMinUser}"/></a>
									
									</td>
									</tr>
								</c:when>
							</c:choose>
						</table>
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
