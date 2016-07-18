<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Login Page</title>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>



<script type="text/javascript">
    function ValidateInfo() {
         
    	alert("Please select the file for uploading");
    	return true;
         
    }
</script>


</head>

<body>
<form method="post" name="loginform" action="./login.spring">
<%
Date sysDate = new Date();
DateFormat formatter=DateFormat.getDateInstance();
formatter.format(formatter.LONG);
String currentDate = formatter.format(sysDate);%>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="basetable">
<tr><td><table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
      <tr>
        <td width="1%" align="left" class="leftborder">
        <img src="images/spacer.gif" width="15" height="6" />
        </td>
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
		    <td width="33%" valign="middle" class="welcome_txt">Welcome to Tracking Tool</td>
            <td width="33%" align="center" valign="middle" class="welcome_txt"><%=currentDate%></td>
            <td width="34%" valign="middle" class="welcome_txt" align="right"></td>
          </tr>
          <tr>
            <td colspan="100%" id="double_line">
            <img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td>
          </tr>
        </table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="6" />
       </td>
      </tr>
    </table></td>
</tr>
<tr>
<td>	
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="menutable">
<tr>
<td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="6" /></td>
<td width="98%" valign="top">

<table width="100%" border="0" cellspacing="0" cellpadding="0" >

<tr>
                <td><img src="images/spacer.gif" alt="spacer" width="1" height="100" /></td>
              </tr>
              
			  
              
              <tr>
                <td valign="top" class="pageheadertxt_maroon" align=center valign=center>Login Page</td>
                </tr>
                <tr>
                <td><img src="images/spacer.gif" alt="spacer" width="1" height="20" /></td>
              </tr>
                <tr>
              <td  valign="middle" align="center">
             <c:if test="${!empty message}">
               <span class="bodytxt_grey"><b>${message}</b></span></c:if></td>
               </tr>
                
               <tr>
              <td  valign="middle" align="center">
              <table>
               <tr>
               <td>Login Id    : </td><td> <input type="text" name="loginId" value=""></td>
               
               </tr>
              <tr>
               <td>Password    : </td><td> <input type="password" name="password" value=""></td>
               
               </tr>
              
              </table>
              </td>
               </tr>
              
              <tr>
                <td><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
              </tr>
              
              <tr>
                <td align="center" valign="top">
			<input name="submitFile" type="submit" class="redbutton1" value="Submit" />
            
                  </td>
              </tr>
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<tr><td>

</td>
</tr>           

              <tr>
                <td height="240" valign="top"><img src="images/spacer.gif" alt="spacer" width="1" height="10" /></td>
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
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
        <td width="1%" align="left" class="leftborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
		        <td width="85%">
		        <table width="100%" cellpadding="0" cellspacing="0">
				<tr><td colspan="3" id="double_line" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				<tr><td height="91" align="center"></td></tr>
				<tr><td colspan="3" id="banner_grey" ><img src="images/spacer.gif" alt="spacer" width="1" height="6" /></td></tr>
				</table></td>
       <td width="1%" align="left" class="rightborder"><img src="images/spacer.gif" width="15" height="30%" /></td>
</tr>
</table>
</td>
</tr>


</table>
 </form> 
</body>
</html>
