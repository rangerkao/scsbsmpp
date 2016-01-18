<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>SCSBsmpp</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <!-- <link rel="stylesheet" type="text/css" href="css/head.css" />-->
  <!-- modernizr enables HTML5 elements and feature detects -->
  <!-- <script type="text/javascript" src="js/modernizr-1.5.min.js"></script> -->
 
</head>

<body>
	
    <div id="scroll">
      <a title="Scroll to the top" class="top" href="#"><img src="/scsbsmpp/images/top.png" alt="top" /></a>
    </div>
    <footer>
      <p><a href="/scsbsmpp/index.jsp">Home</a></p>
      
    </footer>
  </div>
  <!-- javascript at the bottom for fast page loading -->
  
  <!-- <script type="text/javascript" src="js/jquery.easing-sooper.js"></script>-->
  <!-- <script type="text/javascript" src="js/jquery.sooperfish.js"></script>-->
  <script type="text/javascript">
    $(document).ready(function() {
     // $('ul.sf-menu').sooperfish();
      $('.top').click(function() {$('html, body').animate({scrollTop:0}, 'fast'); return false;});
    });
  </script>
</body>
</html>