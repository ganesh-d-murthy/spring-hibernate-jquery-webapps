<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<link rel="stylesheet" href="resources/1270839.css">
	<title>Customer Management</title>
  	<link rel="stylesheet" href="resources/jquery-ui.css">
	<link href="resources/jtable.css" rel="stylesheet" type="text/css" />
	<link href="resources/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />  
  	<script src="resources/jquery-1.9.1.js"></script>
  	<script src="resources/jquery-ui.js"></script>
	<script src="resources/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
	<script src="resources/jquery.jtable.js" type="text/javascript"></script>  		
 <script>
  $(function() {
    $( "#tabs" ).tabs({
      beforeLoad: function( event, ui ) {
        ui.jqXHR.error(function() {
          ui.panel.html(
            "Couldn't load this tab. We'll try to fix this as soon as possible. " +
            "If this wouldn't be a demo." );
        });
      }
    });
    $('#PersonTableContainer').jtable({
        title: 'Table of people',
        paging: true, //Enable paging
        pageSize: 25, //Set page size (default: 10)           
        actions: {
            listAction: 'getallcustomers',
            createAction:'createcustomer',
            updateAction: 'updatecustomer',
            deleteAction: 'deletecustomer'
        },
        fields: {
            id: {
            	title:'S.NO',
                key: true,
                list: true,
                create:true
            },
            firstName: {
                title: 'First Name',
                width: '30%',
                edit:false
            },
            lastName: {
                title: 'Last Name',
                width: '30%',
                edit:true
            },
            doj: {
                title: 'Joined Date',
                width: '20%',
                edit: true
            }                
        }
    });
    $('#PersonTableContainer').jtable('load');    
  });
  </script>
</head>
<body>
<div class="wrapper">

	<div class="header">
		A Plus Driving School
	</div><!-- .header-->

<div class="content">
		<div id="tabs">
		  <ul>
		    <li><a href="#tabs-1">Search</a></li>
		  </ul>
		  <div id="tabs-1">
		    <p>Search Content</p>
		    <div id="PersonTableContainer"></div>
		  </div>
		</div>
</div>

</div><!-- .wrapper -->

<!-- <div class="footer">
	
</div> --><!-- .footer -->

</body>
</html>
