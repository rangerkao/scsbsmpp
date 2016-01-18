<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 9]>
<script type="text/javascript" src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<html>
<head>
  <meta charset="utf-8">
  <title>jQuery UI 測試</title>
  <link href="../css/jquery-ui.css" rel="stylesheet">
  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
  <link href="../css/jquery-ui-timepicker-addon.css" rel='stylesheet'>
  <script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
  <script type='text/javascript' src='../js/jquery-ui-sliderAccess.js'></script>
  <style>
    article,aside,figure,figcaption,footer,header,hgroup,menu,nav,section {display:block;}
    body {font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
  </style>
</head>
<body>
   <input id="req_schedule" type="text" />
  <script language="JavaScript">
    $(document).ready(function(){ 
      var opt={dateFormat: 'yy-mm-dd',
	       showSecond: true,
               timeFormat: 'HH:mm:ss'
               };
      $('#req_schedule').datetimepicker(opt);
      });
  </script>
</body>
</html>