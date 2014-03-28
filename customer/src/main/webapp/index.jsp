<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Customer Management</title>
  	<link rel="stylesheet" href="resources/jquery-ui.css" type="text/css"> 
  	<script src="resources/jquery-1.8.2.js"></script>
  	<script src="resources/jquery-ui.js"></script>
	<link href="resources/themes/metro/jtable_metro_base.css" rel="stylesheet" type="text/css" />  		
	<link href="resources/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
	<script src="resources/jquery.jtable.min.js" type="text/javascript"></script>
	<!-- Import CSS file for validation engine (in Head section of HTML) -->
	<link href="resources/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
	 
	<!-- Import Javascript files for validation engine (in Head section of HTML) -->
	<script type="text/javascript" src="resources/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="resources/jquery.validationEngine-en.js"></script>
	<script src="resources/jquery.form.js" type="text/javascript"></script>		
    <style>
        div.filtering
        {
            border: 1px solid #999;
            margin-bottom: 5px;
            padding: 10px;
            background-color: #EEE;
        }
		div.site-container {
		    margin: auto;
		    width: 760px;
		}		
		div.main-header {
		    background-color: #2D89EF;
		    color: #FFFFFF;
		    margin-bottom: 5px;
		    padding: 10px;
		}		        
    </style>
</head>
<body>
	<div class="site-container">
		<div class="main-header" style="position: relative">
			<h1>A PLUS DRIVING SCHOOL</h1>	
		</div>
		
		<div class="filtering">
			    <form id="filterForm">
			        <label>FirstName: </label><input type="text" name="firstName" id="firstName" />
			        <label>Year of Joining: </label><input type="text" id="datepicker"/>
			        <button type="submit" id="LoadRecordsButton">Load records</button>
			    </form>
		</div>
		<div id="PersonTableContainer"></div>
	</div>
	
 <script>
  $(function() {
      $('.upload').live('change', function()          { 
          var id=this.id;
              $("#preview"+id).html('');
           $("#preview"+id).html('<img src="resources/images/loader.gif" alt="Uploading...."/>');
        $("#form"+id).ajaxForm({
                   target: '#preview'+id
       }).submit();

       });
    $("#datepicker" ).datepicker({ dateFormat: 'yy' });  
    $('#PersonTableContainer').jtable({
        title: 'Customer List',
        paging: true, //Enable paging
        sorting: true,
        pageSize: 10, //Set page size (default: 10)           
        actions: {
            listAction: 'getallcustomers.html',
            createAction:'createcustomer.html',
            updateAction: 'updatecustomer.html',
            deleteAction: 'deletecustomer.html'
        },
        fields: {
            id: {
            	title:'S.NO',
                key: true,
                list: true,
                create:false
            },
            firstName: {
                title: 'First Name',
                width: '30%',
                edit: true,
                create:true,
                inputClass:'validate[required]'
            },
            lastName: {
                title: 'Last Name',
                width: '30%',
                edit: true,
                inputClass:'validate[required]'
            },
            doj: {
                title: 'Joined Date',
                width: '20%',
                edit: true,
                create: true,
                list: true,
                type: 'date'/*,
                displayFormat: 'yy'*/
            },
            fileName: {
            	title: '',
            	create: false,
            	list: true,
            	edit: true,
            	display: function (data) {
            	    if (data.record.fileName) {
            	    	var img = $('<a href="#" id="' + data.record.id + '" ><img src="resources/images/filetype_pdf_32.png" title="View File" /><span>'+ data.record.fileName +'</span></a>');
                		img.click(function () {
                			console.log("here");
                			window.open("filedownload.html?id="+data.record.id, "yyyyy", "width=800,height=800,resizable=yes,toolbar=yes,menubar=no,location=no,status=no");
                			return false;
                		});
                		return img;            	    	
	           	    }
            	}            	
            	
            	/*,
            	display: function (data) {
            		//var $img = $('<img src="resources/images/filetype_pdf_32.png" title="View File" />' + ' data.record.fileName');
            		var $img = data.record.fileName;
            		$img.click(function () {
            			
            		});
            		return $img;
            	}*/
            },
            file: {
                title: 'File',
                list: false,
                create: false,
                edit: true,
                input: function(data) {
                    return '<form target="iframeTarget" class="formUploadFile" action="fileupload.html" method="post" enctype="multipart/form-data"> <input type="file" onchange="this.form.submit()" name="file"/> <input type="hidden" value="'+ data.record.id +'" name="id"/> </form> <iframe class="upload-iframe" style="display: none;" src="#" name="iframeTarget"></iframe>';
                }
            	/*display: function (data) {
            		return '<div id='+ data.record.id + '><form id="form'+ data.record.id +'" method="post" enctype="multipart/form-data" action="fileupload.html"><input type="file" name="file" id="'+ data.record.id +'" class="upload"/><input type="hidden"  value="'+ data.record.id +'"/></form></div><div id="preview'+ data.record.id +'"></div>';
            	}*/
            }/*
            file:{
            	title: 'File',
            	list: true,
            	create: true,
            	edit: true,
            	input: function (data) {
            	return '<div id="FileUpload" name="FileUpload"></div>';
            	}
            }*/            
        },
        //Validate form when it is being submitted
        formSubmitting: function (event, data) {
            return data.form.validationEngine('validate');
        }
    });
    
    //Re-load records when user click 'load records' button.
    $('#LoadRecordsButton').click(function (e) {
        e.preventDefault();
        $('#PersonTableContainer').jtable('load', {
        	firstName: $('#firstName').val(),
            year: $('#datepicker').val()
        });
    });
 
    $('#PersonTableContainer').jtable('load');    
  });
  </script>	
</body>
</html>
