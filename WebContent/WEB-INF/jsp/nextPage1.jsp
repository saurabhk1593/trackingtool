<html>
<head>
<script type="text/javascript">
function displayVal()
{
 alert(document.getElementById("testLab").value);
 	if(window.XMLHttpRequest)
		req = new XMLHttpRequest();
	else if(window.ActiveXObject)
		req = new ActiveXObject("Microsoft.XMLHTTP");
	url = "Test.spring?name=Swaran";
	req.onreadystatechange = displayOutput;
	req.open("POST",url,true);
	req.send(null);
}

function displayOutput()
{

if(req.readyState == 4)
	{
	alert('Inside response');
	
	var data = req.responseText;
	alert('data'+data);
	if(data != null && data != "")
		document.getElementById("displayAjax").innerHTML = "Cap: " +data;
		document.getElementById("subbut").disabled = "true";
	}
}

</script>
<link href="css/lindestyle.css" rel="stylesheet" type="text/css" />
</head>

<body>

<h1><table><tr><td>
<%= request.getAttribute("logDetails") %></td></tr>
<tr><td class="bgwhite_bottombordergrey_bold"><label id="testLab" value="k">Test</label></td></tr>
<input type="submit" value="submit" id="subbut" onclick="displayVal();">
<tr><td><div id="displayAjax"></div></td></tr>
</table>
</h1>
</body>

</html>