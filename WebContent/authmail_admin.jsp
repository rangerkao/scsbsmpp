<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email驗證</title>
<script type="text/javascript" src="js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="js/jquery-validate.js"></script>
<script type="text/javascript" src="js/jquery-validate-additional-methods.js"></script>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/head_admin.css" />
</head>

<%
	String type = request.getParameter("type")!=null?request.getParameter("type").toString():"";
	String action = request.getParameter("action")!=null?request.getParameter("action").toString():"";
	if(action.equals("reg")){
		out.println("您已成功註冊，請至信箱收取認證信並啟動您的帳號才可登入！");
	} else if(action.equals("edit")){
		out.println("您的email已經過更改，請至信箱收取認證信並啟動您的帳號才可登入！");
	} else if(action.equals("verify")){
		out.println("您的email尚未認證，請至信箱收取認證信並啟動您的帳號才可進行其他功能！");
	} else if(action.equals("failed")){
		out.println("您的email認證失敗，請檢察您連結的網址或重新發送認證信，或與我們絡聯！");
	}
	
%>
<script type="text/javascript">


$(function(){

	$(".process").hide();


	$(".loading").hide();
	

	$("#sendMailForm").validate({
		rules:{
			account:{
				required:true,
				minlength:4,
				maxlength:20,
				alphanumeric:true,
				checkIsAccount:true
			},
			password:{
				required:true,
				minlength:6,
				maxlength:20,
				alphanumeric:true
			}
		},
		messages:{
			account:{
				required:"必須填寫",
				minlength:$.format("帳號長度至少為{0}"),		 
                maxlength: $.format("帳號長度不可超過{0}"),
                checkIsAccount:"沒有這帳號"
			},
			password:{
				required:"必須填寫",
				minlength:$.format("密碼長度至少為{0}"),		 
	            maxlength: $.format("密碼長度不可超過{0}")
			},
			confirm_password:{
				required:"必須填寫",
				equalTo:"與設定密碼不一樣"
			}
		},
		errorPlacement: function (error, element) {
		     //console.log(element.attr('name'));
		     if (element.is(':radio') || element.is(':checkbox')) {
		         var eid = element.attr('name');
		         $('input[name=' + eid + ']:last').next().after(error);
		     } else if(element.attr('name')==="validcode"){
		    	 $('img[name=imgcode]').next().after(error);
		     } else {
		         error.insertAfter(element);
		     }
		 },
		highlight: function( element, errorClass, validClass ) {
			//console.log(element.name+" "+element.type+" "+element.value);
			if ( element.type === "radio" ) {
				this.findByName(element.name).addClass(errorClass).removeClass(validClass);
			} else if( element.type === "select-one" || element.name ==="postcode") {
			} else {
				$(element).addClass(errorClass).removeClass(validClass);
			}
		},
		unhighlight: function( element, errorClass, validClass ) {

			if ( element.type === "radio" ) {
				this.findByName(element.name).removeClass(errorClass).addClass(validClass);
			}else if( element.type === "select-one" || element.name ==="postcode") {
			} else {
				$(element).removeClass(errorClass).addClass(validClass);
			}
		},
		success: function(label) {  
			label.addClass("valid").text("Ok!");      
		},          
        submitHandler: function(form){
           // if(confirm('Are you sure?'))form.submit(); //PROBLEM var has no such method
        	$(".process").fadeIn(1000);
            form.submit();

        }
	});	
	
	$("#changeMailForm").validate({
		rules:{
			account:{
				required:true,
				minlength:4,
				maxlength:20,
				alphanumeric:true,
				checkIsAccount:true
			},
			password:{
				required:true,
				minlength:6,
				maxlength:20,
				alphanumeric:true
			},
			email:{
				required:true,
				email:true
			}
			
		},
		messages:{
			account:{
				required:"必須填寫",
				minlength:$.format("帳號長度至少為{0}"),		 
                maxlength: $.format("帳號長度不可超過{0}"),
                checkIsAccount:"沒有這帳號"
			},
			password:{
				required:"必須填寫",
				minlength:$.format("密碼長度至少為{0}"),		 
	            maxlength: $.format("密碼長度不可超過{0}")
			},
			confirm_password:{
				required:"必須填寫",
				equalTo:"與設定密碼不一樣"
			},
			email:{
				required:"必須填寫",
				email:"電子信箱格式有問題"
			}
		},
		errorPlacement: function (error, element) {
		     //console.log(element.attr('name'));
		     if (element.is(':radio') || element.is(':checkbox')) {
		         var eid = element.attr('name');
		         $('input[name=' + eid + ']:last').next().after(error);
		     } else if(element.attr('name')==="validcode"){
		    	 $('img[name=imgcode]').next().after(error);
		     } else {
		         error.insertAfter(element);
		     }
		 },
		highlight: function( element, errorClass, validClass ) {
			//console.log(element.name+" "+element.type+" "+element.value);
			if ( element.type === "radio" ) {
				this.findByName(element.name).addClass(errorClass).removeClass(validClass);
			} else if( element.type === "select-one" || element.name ==="postcode") {
			} else {
				$(element).addClass(errorClass).removeClass(validClass);
			}
		},
		unhighlight: function( element, errorClass, validClass ) {

			if ( element.type === "radio" ) {
				this.findByName(element.name).removeClass(errorClass).addClass(validClass);
			}else if( element.type === "select-one" || element.name ==="postcode") {
			} else {
				$(element).removeClass(errorClass).addClass(validClass);
			}
		},
		success: function(label) {  
			label.addClass("valid").text("Ok!");      
		},          
        submitHandler: function(form){
           // if(confirm('Are you sure?'))form.submit(); //PROBLEM var has no such method
        	$(".process").fadeIn(1000);
            form.submit();

        }
	});
});



$.validator.addMethod("checkIsAccount", function (value, element) {
	$('.loading').show();
    var ok = this.optional(element) || (/^([a-zA-Z0-9_]+)$/.test(value));
    if (ok) 
        $.ajax({
            url: 'checkAccount.do', //你的验证页面，存在输出1，不存在输出0，不要输出其他的内容
            data: 'type=<%=type%>&account=' + encodeURIComponent(value),
            async: false, /////////關鍵，設為同步
            type: 'post',
            dataType: 'json',
            success: function (data) {
            	$('.loading').hide();
                data = parseInt(data);
                ok = data == 0 ? false : true;
            },
            error: function (xhr) {
                alert('ajax有問題！\n' + xhr.responseText);
                ok = false;
            }
        });

    return ok;

}, "沒有這帳號");



</script>
<style type="text/css">



label.error { 
	background:url("images/unchecked.gif") no-repeat 5px ;
	float: none;
	color: red;
	padding-left: 20px;
	vertical-align: top; 
}
label.valid { 
	background:url("images/checked.gif") no-repeat 5px ;
	float: none;
	color: blue;
	padding-left: 20px;
	vertical-align: top; 
}

input.error,
select.error,
textarea.error {
    border: 3px solid red;
}

input.valid,
select.valid,
textarea.valid {
    border: 3px solid green;
}

.process {
	font-size:100px;
	background-color:#D0D0D0 ;
	width:50%;
	height:50%;
	position: absolute;
	z-index:100;
	filter:Alpha(Opacity=80, FinishOpacity=80, Style=2);
	-moz-opacity:0.5;
	opacity: 0.5;	
}

</style>
<body>
<div id="wrap">
	<div style="float:right;"><%@include file="login_admin.jsp" %></div>
	<div id="content">
  	<%@include file="head_admin-left.jsp" %>
  	<div class="right folat-r">
      <div id="right-content">
        <div id="top">
        </div>
        <div class="section">
          <h2></h2>
          <form id="sendMailForm" action="managerMail.do" method="post">
		<h4>重新發送認證信</h4>
		<input type="hidden" name="type" value="<%=type%>"/>
		<input type="hidden" name="method" value="send"/>
		<p>
			<label for="account">帳號</label>
			<input type="text" id="account" name="account" value="" />
			<img class="loading" src="images/loading_icon.gif">
		</p>
		<p>
			<label for="password">密碼</label>
			<input type="password" id="password" name="password" value="" />
		</p>
		<input type="submit" value="發送"/>
	</form>
	
	<form id="changeMailForm" action="managerMail.do" method="post">
		<h4>更改email</h4>
		<input type="hidden" name="type" value="<%=type%>"/>
		<input type="hidden" name="method" value="change"/>
		<p>
			<label for="account">帳號</label>
			<input type="text" id="account" name="account" value="" />
			<img class="loading" src="images/loading_icon.gif">
		</p>
		<p>
			<label for="password">密碼</label>
			<input type="password" id="password" name="password" value="" />
		</p>
		<p>
			<label for="email">電子信箱</label>
			<input type="text" id="email" name="email" value=""  />
		</p>			
			
		<input type="submit" name="send" value="更改" />
	</form>
        </div>
      </div>
    </div>
  	</div>
  </div>
</body>
</html>