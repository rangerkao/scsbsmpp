<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員註冊</title>
</head>

<script type="text/javascript" src="js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="js/jquery-validate.js"></script>
<script type="text/javascript" src="js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="js/jquery-maskedinput.min.js"></script>
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">

<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/head_admin.css" />
<script type="text/javascript">


$(function(){

	$(".process").hide();

	$(".loading").hide();
	
	$("#imgcode").click(function(){
		var str = "textBufferedImage.do?timestampt="+new Date().getTime();  
		$("#imgcode").attr("src",str);
	});
	
	$("#address_1").twzipcode({
		countyName: 'country',
		districtName: 'area',
		zipcodeName: 'postcode',
		css:['addr-country','addr-area','addr-zip']

	});

	
	$("#registerForm").validate({
		rules:{
			username:{
				required:true,
				minlength:6,
				maxlength:20,
				alphanumeric:true,
				checkAccount:true
			},
			password:{
				required:true,
				minlength:6,
				maxlength:20,
				alphanumeric:true
			},
			confirm_password:{
				required:true,
				equalTo:$("#password")
			},
			email:{
				required:true
			},
			name:{
				required:true,
				minlength:2,
				maxlength:20,
				chialpha:true
			},
			gender:{
				required:true
			},
			address:{
				required:true,
				chialphanum:true,
				nullPostcode:$("#postcode")
			},
			validcode:{
				checkValidCode:true
			}
			
		},
		messages:{
			username:{
				required:"必須填寫",
				minlength:$.format("帳號長度至少為{0}"),		 
                maxlength: $.format("帳號長度不可超過{0}"),
                checkAccount:"帳號已經有人使用"
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
				required:"必須填寫"
			},
			name:{
				required:"必須填寫",
				minlength:$.format("姓名長度至少為{0}"),		 
                maxlength:$.format("姓名長度不可超過{0}"),
                chialpha:"只能輸入中文或英文"
			},
			gender:{
				required:"必須選擇"
			},
			address:{
				required:"必須填寫",
				chialphanum:"只能輸入中文或英文、數字",
				nullPostcode:"請選擇地區"
			},
			validcode:{
				checkValidCode:"驗證碼錯誤"
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

$.validator.addMethod("checkAccount", function (value, element) {
	$('.loading').show();
    var ok = this.optional(element) || (/^([a-zA-Z0-9_]+)$/.test(value));
    if (ok) 
        $.ajax({
            url: 'checkAccount.do', //你的验证页面，存在输出1，不存在输出0，不要输出其他的内容
            data: 'type=smppuser&username=' + encodeURIComponent(value),
            async: false, /////////關鍵，設為同步
            type: 'post',
            dataType: 'json',
            success: function (data) {
            	$('.loading').hide();
                data = parseInt(data);
                ok = data == 1 ? false : true;
            },
            error: function (xhr) {
                alert('ajax有問題！\n' + xhr.responseText);
                ok = false;
            }
        });

    return ok;

}, "這帳號已經有人使用");

$.validator.addMethod("checkValidCode", function (value, element) {
	$('.loading').show();

    var ok = this.optional(element) || (/^([a-zA-Z0-9_]+)$/.test(value));
    if (ok) 
        $.ajax({
            url: 'checkValidCode.do', //你的验证页面，存在输出1，不存在输出0，不要输出其他的内容
            data: 'validcode=' + encodeURIComponent(value),
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

}, "驗證碼錯誤");


</script>

<style type="text/css">


.addr-country{width:70px}
.addr-area{width:80px}
.addr-zip{border:1px solid #666;width:30px}



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
<div class="process">註冊中</div>
<h1>會員註冊</h1>
<form id="registerForm" action="managerPersonal.do" method="post">
	<input type="hidden" name="type" value="smppuser"/>
	<input type="hidden" name="action" value="reg"/>
	<h3>帳號設定</h3>
	<p>
		<label for="username">帳號</label>
		<input type="text" id="username" name="username" value="test123" />
		<img class="loading" src="images/loading_icon.gif">
	</p>
	<p>
		<label for="password">密碼</label>
		<input type="password" id="password" name="password" value="123456" />
	</p>
		
	<p>
		<label for="confirm_password">確認密碼</label>
		<input type="password" id="confirm_password" name="confirm_password" value="123456"/>
	</p>		
	
	<p>
		<label for="email">電子信箱</label>
		<input type="text" id="email" name="email" value="請修改"  />
	</p>			
	
	<h3>個人資料</h3>
	<p>
		<label for="company">公司名稱</label>
		<input type="text" id="company" name="company" value="請修改"/>
	</p>
	<p>
		<label for="name">連絡人</label>
		<input type="text" id="name" name="name" value="請修改"/>
	</p>
	
	<p>
		<label for="gender">性別</label>
		<input type="radio" name="gender" value="0" checked/><span>先生</span>
		<input type="radio" name="gender" value="1"/><span>小姐</span>
				
	</p>
	
	<p>
		<label for="phone">連絡電話</label>
		<input type="text" id="phone" name="phone" value="請修改"/>
	</p>
	
	<p>
		<label for="mobile">手機號碼</label>
		<input type="text" id="mobile" name="mobile" value="請修改"/>
	</p>
	
	<p>
		<label for="fax">傳真電話</label>
		<input type="text" id="fax" name="fax" value="請修改"/>
	</p>
	
	<p>
		<label for="rid">統一編號</label>
		<input type="text" id="rid" name="rid" value="請修改"/>
	</p>
	
	<p>
		<label for="address">地址</label>	
		<label id="address_1"></label>
		<input type="text" id="address" name="address" value="請修改"/>
	<p>
	
	<p>
		<label for="validcode">驗證碼</label>	
		<input type="text" id="validcode" name="validcode" size="4"/>
		<img id="imgcode" name="imgcode" height="30" width="90" src="textBufferedImage.do?timestampt="+<%=new java.util.Date().getTime()%> title="點擊重刷" />
		<img class="loading" src="images/loading_icon.gif">
	<p>
	
	
		<input class="submit" type="submit" name="send" value="註冊"/>

	</form>
</div>
      </div>
    </div>
  	</div>
  </div>
</body>
</html>