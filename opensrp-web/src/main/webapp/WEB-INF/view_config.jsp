<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css" type="text/css">
<script src="resources/js/jquery.min.js"></script>
<link href="resources/css/jsoneditor.min.css" rel="stylesheet" type="text/css">
<script src="resources/js/jsoneditor.min.js"></script>

<style type="text/css">

#view_config_table{
width:70%;
margin:auto;
}

</style>
<script type="text/javascript">
$(document).ready(function(){
	$("#jsonEditDiv").hide();
	$.ajax({
        url: "${pageContext.request.contextPath}/rest/viewconfiguration/getAll"
    }).then(function(data) {
    	if(data != null && data.length > 0){
        	var table = document.createElement("table");
	       	table.id = "view_config_table";
	       	table.className = "table table-sm table-bordered";
	       	var thead = document.createElement("thead");
	       	thead.className = "thead-light";
	       	var tr = document.createElement("tr");   
	       	var th = document.createElement("th");
	       	th.className += " text-center"; 
	       	th.innerHTML = "Identifier";
	       	tr.appendChild(th);
	       
	       	var th1 = document.createElement("th");
	       	th1.className = "blue-grey";
	       	th1.className = "text-center";
	       	tr.appendChild(th1);
	       	
	       	thead.appendChild(tr);
	       	table.appendChild(thead);

		    for(var i=0; i<data.length; i++){
	    		var tr = table.insertRow(-1);
	    	   	var firstCell = tr.insertCell(-1);
	    	   	firstCell.innerHTML = data[i].identifier;
	    	   	firstCell.style.padding = "10px";
	    	   	var secondCell = tr.insertCell(-1);
	    	   	secondCell.style.width = "1%";
	    	   	secondCell.style.padding = "10px";
	    	   	var link = document.createElement("a");
	       	   	link.innerHTML = "Edit";
	    	   	link.href = "#";
	    	   	link.className = "anyclass";
	    	   	link.id = ""+i;
	    	   	secondCell.append(link);
       		}
	       var divContainer = document.getElementById("tableDiv");
	       divContainer.appendChild(table);
	       
	       var container = document.getElementById("jsoneditor");
	       var options = {
	    		   "mode": "code", 
	    		   'modes': ['code',"tree","text"],
	    		   "search": true,
	    		   onError: function (err) {
	    			      alert(err.toString());
	    			    },
	    		   };
	       var editor = new JSONEditor(container, options);    
	       var ident;
	       $("#tableDiv").on('click','td a.anyclass',function(){
	    	   ident = this.id;
	    	   editor.set(data[this.id]);
	    	   $("#tableDiv").hide();
	    	   $("#jsonEditDiv").show();
	    	   
	       })
    	}
       $("#saveBtn").click(function(){
    	   try{
	    	   $.ajax({
	    		   url:"${pageContext.request.contextPath}/rest/viewconfiguration/save",
	    		   type:"POST",
	    		   data:JSON.stringify(editor.get()),
	    		   contentType:"application/json; charset=utf-8",
	    		   success: function(returnedData){
	    			   data = returnedData;
	    	    	   $("#"+ident).parent().prev().first().html(returnedData[ident].identifier);
	    			   $("#jsonEditDiv").hide();
	    			   $("#tableDiv").show();
	    		   },
	    		   error: function(textStatus, errorThrown) {
	    			     alert("Bad request. Please ensure that json conforms to the view configuration schema");
	    			  }
	    		 })
    	   }catch(err){
    		   alert(err);
    	   }
       })
       $("#cancelBtn").click(function(){
    	   $("#jsonEditDiv").hide();
    	   $("#tableDiv").show();
       })
    });
})
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Configurations</title>
</head>
<body>

<div class="container">
<div style="margin:50px;" class="page-header text-center">
  <h1>View Configurations</h1>
</div>
<div id="tableDiv"></div>
<div id="jsonEditDiv">
	<div id="jsoneditor" style="width: 900px; height: 400px; margin:auto;"></div>

	<div style="margin:10px;" class="text-center">
		<button id="cancelBtn" class="btn btn-md btn-danger ">Cancel</button>
		&nbsp;
		<button id="saveBtn" class="btn btn-md btn-primary ">Save</button>
	</div>
</div>
</div>
</body>
</html>