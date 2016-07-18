<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.techm.trackingtool.admin.bean.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>LindeTool</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/window_opener.js"></script>






<script type="text/javascript">
    function ValidateInfo() {
      
       if (document.forms[0].yearSelection[0].checked) {
         document.forms[0].year.value="2011";
       }
       if (document.forms[0].yearSelection[1].checked) {
         document.forms[0].year.value="2009-2010";
       }
       if (document.forms[0].yearSelection[2].checked) {
         document.forms[0].year.value="2008-2009";
       }
       if(document.forms[0].yearSelection[0].checked==false && document.forms[0].yearSelection[1].checked==false
       	&& document.forms[0].yearSelection[2].checked==false){
       alert("Please select the View Year");
       document.forms[0].yearSelection.focus();
       return false;
       }
       document.forms[0].action="archivedData_home.spring?task=homePageReport";
       document.forms[0].submit();
    	
    }
     </script>






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
<form method="post" name="ArchivedDataForm" action="archivedData.spring?task=homePageReport">
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
			<td width="33%" align="center" valign="middle" class="welcome_txt">&nbsp</td>
		  </tr>
          <tr>
            <td colspan="100%" id="double_line"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" /></td>
      </tr>
    </table></td>
</tr>


<tr>
<td>
<table width="70%" border="0" cellpadding="0" cellspacing="0" align="center">
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<table width="60%" align="center" class=boxtable_greyborder_padding cellpadding="0" cellspacing="0">

<tr>
<td class=headerboldtxt_greybg_greyborder colspan="2" align="center" width="50%"><center><br>Data has been archived in Linde Tool. <br>  <br>Please Select the Year for which you want to View the Data and click on Continue. <br><br></center></td>
</tr>
<tr><td>&nbsp</td></tr>

<tr>
<td align="right" class=bodytxt_black width="50%" align="right">Data View<font color="red">&nbsp *</font>
</td>
<td align="left" width="50%">
<table >
	<tr align=left>
		<td class=bodytxt_black >                
                <label> 
                	<input type="radio" name="yearSelection" value="2011" checked="checked">Current Year (From Aug 15, 2011 onwards)</input>
                </label>
        </td>
     </tr>
	<tr align=left>
		<td class=bodytxt_black >                
                <label> 
                	<input type="radio" name="yearSelection" value="2009-2010">Archieved Year (July 1, 2009 - Sep 2010)</input>
                </label>
        </td>
     </tr>
	<tr align=left>
		<td class=bodytxt_black >                
                <label> 
                	<input type="radio" name="yearSelection" value="2008-2009">Archieved Year (July 1, 2008 - June 30, 2009)</input>
                </label>
		</td>
	</tr>



</tr>
</table>
</td>
<tr><td>&nbsp</td></tr>
					
								
              	
							
</table>
<tr>
<tr><td>&nbsp</td></tr>
<tr>
                <td align="center" valign="top" colspan="4">
                
                  <input name="Button" type="button" class="redbutton1" value="Continue" onclick="ValidateInfo();"/>
				
              </tr>	
			  				  <tr>
                    <td height="10" colspan="4"><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
                   </tr>
<input type="hidden" name="year" value="2009-2010"/>
</body>
</html>
