<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.TicketInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.techm.trackingtool.util.LindeMap"%>
<%@page import="com.techm.trackingtool.util.LindeList"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Review Report</title>
<link href="css/calendar.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
function init() {
	calendar.set("from_dt");
	calendar.set("to_dt");
}

</script>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>
<script language="javascript" src="js/calender_linde.js"></script>
<script>
function checkType()
{

var selType = document.getElementById("severity").value;

var userInfo=document.getElementById("1");
userInfo.style.display = "";
var userInfo=document.getElementById("1");
userInfo.style.visibility = "visible";

if(selType == '3')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Severity 3";
}
if(selType == '2')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Severity 2";
}
if(selType == '1')
{
	var lab = document.getElementById("sevType");
	lab.innerText = "Severity 1";
}


}
var checked=false;
function checkIncidentFlds()
{
	if(checked==false)
    {
     checked=true;
    }
  else
    {
    checked=false;
    }
//	alert("checkIncidentFlds");
	var incfieldsList = document.forms[0].incidentfields;
//	alert("incfieldsList "+incfieldsList.length);	
   for(var i=0;i<incfieldsList.length;i++)
	{
	//alert("incfieldsList 1");
      incfieldsList[i].checked=checked;
	}
}
function selectReport()
{
//alert("selectReport");
	var repType = document.getElementById("reportType").value;
//	alert("repType"+repType);
	if(repType=='CR')
	 {
	 document.getElementById("displayCR1").innerHTML = document.getElementById("displayCR").innerHTML;
		document.getElementById("displayCR").style.visibility = 'hidden';
	 	document.getElementById("displayIncident").style.visibility='hidden';
	 }
	else
	{
	document.getElementById("displayCR1").innerHTML = document.getElementById("displayIncident").innerHTML;
		document.getElementById("displayIncident").style.visibility='hidden';
	 	document.getElementById("displayCR").style.visibility='hidden';
		
	}

}




function selectAll()
{
	alert("hi");
	
		if(checked==false)
          {
           checked=true;
          }
        else
          {
          checked=false;
          }
	
	alert("hi");
	document.forms[0].casetype.checked=checked;
	document.forms[0].application.checked=checked;
	document.forms[0].description.checked=checked;
	document.forms[0].assigneddate.checked=checked;
	document.forms[0].createddate.checked=checked;
	document.forms[0].resolveddate.checked=checked;
	document.forms[0].ttno.checked=checked;
	document.forms[0].remarks.checked=checked;
	document.forms[0].ageing.checked=checked;
	document.forms[0].individual.checked=checked;
	document.forms[0].region.checked=checked;
	document.forms[0].status.checked=checked;
	document.forms[0].severity.checked=checked;
	document.forms[0].classification.checked=checked;
	document.forms[0].efforts.checked=checked;
	document.forms[0].resolution.checked=checked;
		
      }
function selectAll1()
{
	//alert("hi"+checked);
		if(checked==false)
          {
           checked=true;
          }
        else
          {
          checked=false;
          }
	
	//alert("hi");	
	var crfieldsList = document.forms[0].crfields;
//	alert("crfieldsList "+crfieldsList.length);	
   for(var i=0;i<crfieldsList.length;i++)
	{
      crfieldsList[i].checked=checked;
	}
	
		}

function ValidateInfo() {
         
        var currDate = new Date();		
	var todaysmon = currDate.getMonth() + 1;
	var todaysdate = currDate.getDate();
	if (todaysdate < 10)
	   todaysdate = "0" + todaysdate;
	if (todaysmon < 10)
		todaysmon = "0" + todaysmon;
	/*var archivalyear=document.forms[0].archivalyear.value;
	var archivalyearlowerlimit="06/01/2008";
	var archivalyearupperlimit="06/30/2009";*/
	
	var todaysdate = todaysmon+"/" + todaysdate+"/" + currDate.getFullYear(); 
	
		if(document.forms[0].from_dt.value!='' || document.forms[0].to_dt.value!='')
        {
         
        
		if((strDate_compare(document.forms[0].from_dt.value,todaysdate)) > 0)
		{
		alert("From Date cannot be greater than Today's Date");
		return false;
		}
        if((strDate_compare(document.forms[0].to_dt.value,todaysdate)) > 0)
		{
		alert("To Date cannot be greater than Today's Date");
		return false;
		}
         
        if(document.forms[0].from_dt.value!='' && document.forms[0].to_dt.value!='')
        {
        if((strDate_compare(document.forms[0].from_dt.value,document.forms[0].to_dt.value)) > 0)
		{
		alert("From Date cannot be more than To Date");
		return false;
		}
        }
      var crfieldsList = document.forms[0].crfields;
	  var incidentfieldsList = document.forms[0].incidentfields;
	  
	  var checkedValue;
	//   alert("b4 checkedValue :: "+checkedValue);
	//  alert("crfieldsList.length :: "+crfieldsList.length);
	var repType = document.getElementById("reportType").value;
	//alert("repType"+repType);
	if(repType=='CR')
	 {
	  for(var i=0;i<crfieldsList.length;i++)
	{
	// alert("crfieldsList[i].checked :: "+crfieldsList[i].checked);
      if(!crfieldsList[i].checked)
	  {
	    checkedValue=false;
	  }
	  else
	  {
	   checkedValue=true;
	   break;
	  }
	  
	}
	}
	
	if(repType=='Incident')
	 {
	  for(var i=0;i<incidentfieldsList.length;i++)
	{
	// alert("crfieldsList[i].checked :: "+crfieldsList[i].checked);
      if(!incidentfieldsList[i].checked)
	  {
	    checkedValue=false;
	  }
	  else
	  {
	   checkedValue=true;
	   break;
	  }
	  
	}
	}
	
	//alert("after checkedValue :: "+checkedValue);
    if(!checkedValue)
    {
         alert("Please select atleast one Field");
         return false; 
    }
    }
    else
	{
         alert("Please select From Date and To Date");
         return false;
    }
         
         document.forms[0].action="./generateReport.spring";
    	 document.forms[0].submit();
  }

function homePageReport()
{
document.forms[0].action="./home.spring";
document.forms[0].submit();	
}
</script>
</head>

<body>
<form method="post" name="ReviewReportForm" action="./reviewReport.spring" modelAttribute="reviewReportForm">
<%
HttpSession httpSession=request.getSession();
String  archivalyear = ((String)httpSession.getAttribute("archivalyear")!=null)?(String)httpSession.getAttribute("archivalyear"):"2009-2010";
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
	<table width="100%" height="338" border="0" cellpadding="0" cellspacing="0" id="menutable">
	<tr>
<td width="1%" height="219" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>

<td width="98%" valign="top">

<table width="100%"><tr><td height="30" valign="middle" ><span class="breadcrum_red_txt">Tracking Tool Home</span>
<span class="bodytxt_grey">&gt;&gt; Report Format</span></td></tr>
<tr><td>
<table width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">
<tr>
<td class=headerboldtxt_greybg_greyborder colspan="4">Select the fields for generating the Review Report</td>
</tr>
               
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	
<table width="80%" align="center" class="boxtable_greyborder_padding" cellpadding="0" cellspacing="0">
<tr class=boxtable_greyborder_padding cellpadding="0" cellspacing="0" width="80%">
<td class=headerboldtxt_greybg_greyborder >Select the Dates</td>

	<td class=headerboldtxt_greybg_greyborder>	<b>From Date 
	<input  type="text" size="10"  id="from_dt" name="from_dt" /></b></td>
		<td class=headerboldtxt_greybg_greyborder ><b>To Date</b> 
		<input type="text" size="10" name="to_dt" id="to_dt"/>	
       </td>
	  

<!-- <td>

 <select name="criteria" class="dropdown" >
		 <option value="">----Select Criteria---</option>
		 <option value="asdt">Assigned Date</option>
		 <option value="crdt">Created Date</option>
		 <option value="rsdt">Resolved Date</option>
           
          
	</select>
</td> -->

</tr>
       
<tr>
                    <td height="10" colspan="1"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
                    
                    <tr>
                    <td class=headerboldtxt_greybg_greyborder style="width:205px">Select the Report</td>
                    <td align="left" >

			<select name="reportType" id="reportType" class="dropdown" onchange="selectReport();" >
			<option value="">-------Select Report------</option>
			<option value="CR" ${reviewReportForm.reportType= 'CR' ? 'selected' : ''}>CR</option>
			<option value="Incident" ${reviewReportForm.reportType= 'Incident' ? 'selected' : ''}>Incident</option>
            
 </select>
</td>
<td></td>
                    
                    
                    </tr>
                    
                    <tr>
                    <td height="10" colspan="1"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>

<!-- 	<table width="80%" align="center" class="boxtable_greyborder_padding" cellpadding="0" cellspacing="0">
<tr class=boxtable_greyborder_padding cellpadding="0" cellspacing="0" width="80%">
<td class=headerboldtxt_greybg_greyborder colspan="4">Select the Uploaded Date</td>
<td>

 <select name="uploaddate" class="dropdown" >
			<option value="ALL">-----------ALL Dates-----------</option>
							
			<c:forEach var="uploadList" items="${model.uploadDateList}">
							
           <option label="${uploadList.uploadeddate.uploaddate}" value="${uploadList.uploadeddate.uploaddate}">${uploadList.uploadeddate.uploaddate}</option>
           </c:forEach>
          
	</select>
</td>
</tr>
       
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>	 -->
				

<table width="80%" align="center"  cellpadding="0" cellspacing="0">
<tr cellpadding="0" cellspacing="0" width="80%">
<td class=headerboldtxt_greybg_greyborder style="width:205px">Select the Module</td>
<td align="left" >

<select name="selectmodule" class="dropdown" >
			<option value="">-------Select Module------</option>
             <c:forEach var="module" items="${reviewReportForm.moduleList}"> 
                <option value="${module.modulename}" ${module.modulename== 'reviewReportForm.selectmodule' ? 'selected' : ''}>${module.modulename}</option>  </c:forEach> 
 </select>
</td>
<td></td>
</tr>
       
<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
</table>		
			
			
					
	<div id="displayCR1"></div>				
		
	<div id="displayCR" 	style='visibility:hidden'>
<table  width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0" >

<tr class=headerboldtxt_greybg_greyborder>
<td colspan="6" >Fields 


<td colspan="7"><input type="checkbox" name="selectAll" onclick="selectAll1();"/>
Select All Fields</td>
</tr>
                  <tr>
                    <td height="10" colspan="14">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
                    
                  
<tr>
<!-- <td class=bodytxt_black width="8%" align="left"><label id="sevType">Severity 1</label></td>-->
                    <td width="3%" height="25" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields"  value="ChangeID" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Change ID</td>
                    <td width="3%" align="left" class="padding_left10_right3">
         <input type="checkbox" name="crfields" value="BriefDes" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Brief Description</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields" value="Assignee" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Assignee</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="crfields" value="ContiAssignedto" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Conti Assigned to</td>
					<td width="3%" align="left" class="padding_left10_right3">
					<input type="checkbox" name="crfields" value="Phase" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Phase</td>
					<td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields" value="Priority" /></td> 
                    <td width="8%" align="left" class="bodytxt_black">Priority</td>
			</tr>
			<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					
					<tr>
					
					<td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields" value="OpenDate" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Open Date</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields" value="RequestedDate" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Requested Date</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="crfields" value="EstimateEffort" /></td>
                    
                    <td width="8%" align="left" class="bodytxt_black">Estimate Effort</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="crfields" value="AffectedCIs" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Module Name</td>
					
                    
                    </tr>
                    
                    
                    
                    <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" />
                    </td>
                    </tr>
                    
                    
                
                    
<tr>
                    <td height="10" colspan="2">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					              <tr>
                <td align="center" valign="top" colspan="12">
                
                  <input name="Button" type="button" class="redbutton1" value="Submit" onclick="ValidateInfo();"/>
                  <input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
              </tr>
			  <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>	
</table>
</div>
	
	

	<div id="displayIncident" style="visibility:hidden;">
<table  width="80%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0" >

<tr class=headerboldtxt_greybg_greyborder>
<td colspan="6" >Fields 


<td colspan="7"><input type="checkbox" name="selectincidents" onclick="checkIncidentFlds();"/>
Select All Fields</td>
</tr>
                  <tr>
                    <td height="10" colspan="14">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
                    
                  
<tr>
<!-- <td class=bodytxt_black width="8%" align="left"><label id="sevType">Severity 1</label></td>-->
                    <td width="3%" height="25" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields"  value="IncidentID" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Incident ID</td>
                    <td width="3%" align="left" class="padding_left10_right3">
         <input type="checkbox" name="incidentfields" value="IssueDes" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Issue  Description</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields" value="Prioirity" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Prioirity</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="incidentfields" value="IncidentOpTime" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Incident Open Time</td>
					<td width="3%" align="left" class="padding_left10_right3">
					<input type="checkbox" name="incidentfields" value="IncStatus" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Incident Status</td>
					<td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields" value="ReOpenTime" /></td> 
                    <td width="8%" align="left" class="bodytxt_black">Re-Open Time</td>
			</tr>
			<tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					
					<tr>
					
					<td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields" value="AssigneeName" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Assignee Name</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields" value="UpdateTime" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Update Time</td>
                    <td width="3%" align="left" class="padding_left10_right3">
                    <input type="checkbox" name="incidentfields" value="CloseTime" /></td>
                    
                    <td width="8%" align="left" class="bodytxt_black">Close Time</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="incidentfields" value="TargetDate" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Target Date</td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="incidentfields" value="Aging " /></td>
                    <td width="8%" align="left" class="bodytxt_black">Aging </td>
					<td width="3%" align="left" class="padding_left10_right3">
					 <input type="checkbox" name="incidentfields" value="ModuleName" /></td>
                    <td width="8%" align="left" class="bodytxt_black">Module Name</td>
					
                    
                    </tr>
                    
                    
                    
                    <tr>
                    <td height="10" colspan="2"><img src="images/spacer.gif" alt="spacer" width="1" height="6" />
                    </td>
                    </tr>
                    
                    
                
                    
<tr>
                    <td height="10" colspan="2">
                    <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                    </tr>
					              <tr>
                <td align="center" valign="top" colspan="12">
                
                  <input name="Button" type="button" class="redbutton1" value="Submit" onclick="ValidateInfo();"/>
                  <input name="Submit222" type="button" class="redbutton1" value="Cancel" onclick="JavaScript:homePageReport();" />
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

</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">




<tr>
<td>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="30%" />
        </td>
		        <td width="85%"><table width="100%" height="130" cellpadding="0" cellspacing="0">
				<tr>             <td colspan="3" id="double_line" >
				<img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				<tr><td height="91" align="center">
			</td>
				</tr>
				<tr><td colspan="3" id="banner_grey" >
				<img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				</table></td>
       <td width="1%" align="left" class="rightborder">
       <img src="images/spacer.gif" width="15" height="30%" /></td>
</tr>
</table>
</td>
</tr>

</table>
</table></table>

<input type="hidden" name="CalFlag" size="10" maxlength="10" value="0"/>
<input type="hidden" value=""/>
<input type="hidden" name="archivalyear" value="<%=archivalyear%>"/>
</form>

<script src="http://openjs.com/js/jsl.js" type="text/javascript"></script>
<script src="http://openjs.com/common.js" type="text/javascript"></script>
<script src="js/calendar.js" type="text/javascript"></script><script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>

</body><%request.removeAttribute("model");
request.setAttribute("model",null);
//out.println("model is "+request.getAttribute("model"));
%>

</html>
