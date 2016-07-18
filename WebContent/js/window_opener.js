// JavaScript Document
function win_opener(open_url,w,h)
{
t=(screen.height-h)/2;
l=(screen.width-w)/2;
window.open(open_url,'','width='+w+', height='+h+', top='+t+', left='+l+'');
}

function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

function win_opener_withScroll(open_url,w,h)
{
t=(screen.height-h)/2;
l=(screen.width-w)/2;
window.open(open_url,'','scrollbars=yes'+', width='+w+', height='+h+', top='+t+', left='+l+'');
}

function textarea_enable()
{
	if(document.getElementById('status_dd').selectedIndex!=0)
	{
	document.getElementById('reason').disabled=0; 
	document.getElementById('comment').disabled=0;
	}
	else
	{
	document.getElementById('reason').disabled=1; 
	document.getElementById('comment').disabled=1;
	document.getElementById('reason').value=""; 
	document.getElementById('comment').value="";
	}
}

function delete_reason(i)
{
	var count = 4;
	for(j=1;j<=4;j++)
	{		
		if(document.getElementById('checkbox'+j).checked)
		{
		document.getElementById('reason'+j).disabled=0;
		}
	else if(!document.getElementById('checkbox'+j).checked)
		{
		document.getElementById('reason'+j).disabled=1;
		document.getElementById('reason'+j).value="";
		count = count-1;
		}
	}
	
	if(count<=0){
		document.getElementById('delete_btn').disabled=1;
		document.getElementById('cancel_btn').disabled=1;
	}
	else if(document.getElementById('checkbox2').checked)
	{
		document.getElementById('delete_btn').disabled=1;
		document.getElementById('cancel_btn').disabled=0;
	}
	else
	{
		document.getElementById('delete_btn').disabled=0;
		document.getElementById('cancel_btn').disabled=0;
	}
		
}

function defer_reason()
{
	for(j=1;j<=3;j++)
	{		
		if(document.getElementById('checkbox'+j).checked)
		{
		document.getElementById('reason'+j).disabled=0;
		}
	else if(!document.getElementById('checkbox'+j).checked)
		{
		document.getElementById('reason'+j).disabled=1;
		document.getElementById('reason'+j).selectedIndex=0;
		}
	}
}
	