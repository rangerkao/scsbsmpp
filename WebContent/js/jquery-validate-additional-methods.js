$.validator.addMethod("alphanumeric", function(value, element) {
	return this.optional(element) || /^\w+$/i.test(value);
}, "只能輸入英文與數字");

$.validator.addMethod("chialpha", function(value, element) { 
	var chinese = /^[\u4e00-\u9fa5]+$/; 
	var alpha = /^([a-zA-Z]+)$/

	return this.optional(element) || (chinese.test(value)) || (alpha.test(value)); 
}, "只能輸入中文或英文"); 
 
$.validator.addMethod("chialphanum", function(value, element) { 
	var chialphanum = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/; 
	return this.optional(element) || (chialphanum.test(value))  ; 
}, "只能輸入中文或英文、數字"); 

$.validator.addMethod("dateV2", function(value, element) { 
	var flag = true;
	var year = value.substring(0,4);
	var month = value.substring(5,7);
	var day = value.substring(8,10);
	var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
		iaMonthDays[1] = 29;
	}
	if (year < 1700 || year > 2500){
		flag = false;
	}
	if(month<1 || month>12){
		flag = false;
	}
	if (day < 1 || day > iaMonthDays[month - 1]){
		flag = false;
	}

	return this.optional(element) || flag ; 
}, "該日期有問題"); 

$.validator.addMethod("mobile", function(value, element) { 
	value = value.replace(/\-/g,"");
	var mobile = /^[09]{2}[0-9]{8}$/;
	return this.optional(element) || (mobile.test(value)); 
}, "手機號碼有問題"); 

$.validator.addMethod("nullPostcode",function(value, element, param){
	var target = $(param);
	return this.optional(element) || !(target.val()==="");
},"請選擇地區");

$.validator.addMethod("positiveNumber", function(value, element) { 
	return this.optional(element) || value>=0  ; 
}, "請輸入正數"); 

$.validator.addMethod("extension", function(value, element, param) {
	param = typeof param === "string" ? param.replace(/,/g, '|') : "png|jpe?g|gif";
	var flag = value.match(new RegExp(".(" + param + ")$", "i"));
	if(flag==null){
		$(element).val("");
	}
	return flag || this.optional(element);
}, "只可上傳png、jpg、gif的圖片");