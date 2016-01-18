<%@page import="com.scsb.bean.Smppuser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員個人資料</title>
</head>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
<script type="text/javascript">


$(function(){

	$(".process").hide();

	$(".loading").hide();
	
	$("#imgcode").click(function(){
		var str = "../textBufferedImage.do?timestampt="+new Date().getTime();  
		$("#imgcode").attr("src",str);
	});
	
	$("#address_1").twzipcode({
		zipcodeSel:$("#default_postcode").val(),
		countyName: 'country',
		districtName: 'area',
		zipcodeName: 'postcode',
		css:['addr-country','addr-area','addr-zip']

	});
	
	$("#born").mask("9999/99/99",{placeholder:"-"});
	$("#mobile").mask("9999-999-999",{placeholder:"_"});
	
	$("#editForm").validate({
		rules:{
			password:{
				minlength:6,
				maxlength:32,
				alphanumeric:true
			},
			confirm_password:{
				equalTo:$("#password")
			},
			email:{
				required:true,
				email:true
			},
			gender:{
				required:true
			},
			born:{
				required:true,
				date:true,
				dateV2:true,
				dateISO:true
			},
			mobile:{
				required:true,
				mobile:true
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
			password:{
				minlength:$.format("密碼長度至少為{0}"),		 
	            maxlength: $.format("密碼長度不可超過{0}")
			},
			confirm_password:{
				equalTo:"與設定密碼不一樣"
			},
			email:{
				required:"必須填寫",
				email:"電子信箱格式有問題"
			},
			gender:{
				required:"必須選擇"
			},
			born:{
				required:"必須填寫",
				date:"請輸入正確的日期",
				dateV2:"該日期有問題",
				dateISO:"請參考格式:2013/01/02"
			},
			mobile:{
				required:"必須填寫",
				mobile:"手機號碼有問題"
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

$.validator.addMethod("checkValidCode", function (value, element) {
	$('.loading').show();

    var ok = this.optional(element) || (/^([a-zA-Z0-9_]+)$/.test(value));
    if (ok) 
        $.ajax({
            url: '../checkValidCode.do', //你的验证页面，存在输出1，不存在输出0，不要输出其他的内容
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
	background:url("../images/unchecked.gif") no-repeat 5px ;
	float: none;
	color: red;
	padding-left: 20px;
	vertical-align: top; 
}
label.valid { 
	background:url("../images/checked.gif") no-repeat 5px ;
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
<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content">
<div class="process">更改中</div>
<h1>會員資料</h1>
<form id="editForm" action="../managerPersonal.do" method="post">
	<input type="hidden" name="type" value="smppuser"/>
	<input type="hidden" name="action" value="edit"/>
	<h3>帳號資料</h3>
	<p>
		<label for="username">帳號</label>
		<span><%=smppuser.getUsername()%></span>
	</p>
	<p>
		<label for="password">密碼</label>
		<input type="password" id="password" name="password" value="" />
	</p>
		
	<p>
		<label for="confirm_password">確認密碼</label>
		<input type="password" id="confirm_password" name="confirm_password" value=""/>
	</p>		
	
	<p>
		<label for="email">電子信箱</label>
		<input type="text" id="email" name="email" value="<%=smppuser.getEmail()%>"  />
	</p>			
	
	<h3>個人資料</h3>
	
	<p>
		<label for="company">公司名稱</label>
		<input type="text" id="company" name="company" value="<%=smppuser.getCompany()%>"  />
	<p>
	
	<p>
		<label for="name">連絡人</label>
		<input type="text" id="name" name="name" value="<%=smppuser.getName()%>"  />
	<p>
		<label for="gender">性別</label>
		<input type="radio" name="gender" value="0" <%if (smppuser.getGender().equals("0")) out.println("checked"); %>/><span>先生</span>
		<input type="radio" name="gender" value="1" <%if (smppuser.getGender().equals("1")) out.println("checked"); %> /><span>小姐</span>
				
	</p>
	
	<p>
		<label for="phone">連絡電話</label>
		<input type="text" id="phone" name="phone" value="<%=smppuser.getPhone()%>"  />
	</p>
	
	<p>
		<label for="mobile">手機號碼</label>
		<input type="text" id="mobile" name="mobile" value="<%=smppuser.getMobile()%>"  />
	</p>
	
	<p>
		<label for="fax">傳真電話</label>
		<input type="text" id="fax" name="fax" value="<%=smppuser.getFax()%>"  />
	</p>
	
	<p>
		<label for="rid">統一編號</label>
		<input type="text" id="rid" name="rid" value="<%=smppuser.getRid()%>"  />
	</p>
	
	<p>
		<label for="address">地址</label>	
		<label id="address_1"></label>
		<input type="text" id="address" name="address" value="<%=smppuser.getAddress().split("-")[(smppuser.getAddress().split("-").length-1)]%>"/>
		<input type="hidden" id="default_postcode" name="default_postcode" value="<%=smppuser.getPostcode() %>"/>
	<p>
	
	<p>
		<label for="validcode">驗證碼</label>	
		<input type="text" id="validcode" name="validcode" size="4"/>
		<img id="imgcode" name="imgcode" height="30" width="90" src="../textBufferedImage.do?timestampt="+<%=new java.util.Date().getTime()%> title="點擊重刷" />
		<img class="loading" src="../images/loading_icon.gif">
	<p>
	
	<p>
		<label for="benefit">優惠訊息</label>
		<input type="checkbox" id="benefit" name="benefit" value="2" <%if (smppuser.getStatus().equals("2")) out.println("checked"); %> />
		<label for="benefit">是否接受本網站的優惠訊息</label>
	</p>
	
	<h3>登錄資料</h3>
	
	<p>
		<label for="status">狀態</label>
		<span>
		<%
			int i = Integer.parseInt(smppuser.getStatus());
			if(i==0){
				out.println("暫停使用");
			}else if(i==1){
				out.println("正常，不接受優惠訊息");
			}else if(i==2){
				out.println("正常，接受優惠訊息");
			}
		
		%>
		</span>
	</p>
	
	<p>
		<label for="verify_email">電子信箱</label>
		<span>
		<%
			int j = Integer.parseInt(smppuser.getVerify_email());
			if(j==0){
				out.println("未認證");
			}else if(j==1){
				out.println("已認證");
			}
		
		%>
		</span>
	</p>
		
	<p>
		<label for="create_ip">註冊IP</label>
		<span><%=smppuser.getCreate_ip()%></span>
	</p>
		<p>
		<label for="create_time">註冊時間</label>
		<span><%=smppuser.getCreate_time()%></span>
	</p>
	<p>
		<label for="edit_ip">修改IP</label>
		<span><%=smppuser.getEdit_ip()%></span>
	</p>
	<p>
		<label for="edit_time">修改時間</label>
		<span><%=smppuser.getEdit_time()%></span>
	</p>
	<p>
		<label for="login_ip">上次登錄IP</label>
		<span><%=smppuser.getLogin_ip()%></span>
	</p>
	<p>
		<label for="login_time">上次登錄時間</label>
		<span><%=smppuser.getLogin_time()%></span>
	</p>
	

	
	<input class="submit" type="submit" name="send" value="修改"/>

	
	
</form>
</div>
    </div>
</div>
<%@include file="../foot.jsp" %>
</body>
</html>