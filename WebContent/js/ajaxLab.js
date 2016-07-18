/*function init()
{

	document.getElementById("application").onkeyup= getInfo;
	document.getElementById("lookupBTN").onclick= displayInformation;
	document.getElementById("playersDIV").onmouseover= getPopulated;

}*/

var request;
function getInfo(applicationname)
{
	var rootNode = document.getElementById("reviewreport");
	var temp;
	//alert("node name is "+applicationname);
	
	if (applicationname.nodeName=='INPUT') {
		for (var i = 0; i < applicationname.attributes.length; i++) {
			if (applicationname.attributes[i].name.toUpperCase() == 'NAME') {
			temp=	applicationname.attributes[i].value ;
			}
		}
	} 
	
	var url = "FetchApplicationName?appname="+applicationname.value;
	
	if(window.ActiveXObject) {//IE
			request= new ActiveXObject("Microsoft.XMLHTTP");			
	}
	else if(window.XMLHttpRequest) {//Firefox
			request=new XMLHttpRequest();
	}
	else
	{
	}
	
	request.onreadystatechange = function displayInfo()
{
	if(request.readyState == 4)
	{
		var response = request.responseText;
		eval(response);
		var divtag;
		for(var count=0;count<applicationname.parentNode.childNodes.length;count++){
		
		if(applicationname.parentNode.childNodes[count].nodeName=='DIV'){
			divtag=	applicationname.parentNode.childNodes[count];
			//alert("divtag - ");
			}
		}
		
			
				
		var playernames=document.createTextNode(appname.name);
		//alert("playernames - "+appname);
		divtag.innerHTML = "";
		
		divtag.appendChild(playernames);
		
	}
};
	//alert("url is "+url);
	request.open("POST",url,true);
	
	request.send();
	//alert("url is "+url);
}

