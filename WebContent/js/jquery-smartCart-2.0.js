/*
 * SmartCart 2.0 plugin
 * jQuery Shopping Cart Plugin
 * by Dipu 
 * 
 * http://www.techlaboratory.net 
 * http://tech-laboratory.blogspot.com
 */
 
(function($){
    $.fn.smartCart = function(options) {
        var options = $.extend({}, $.fn.smartCart.defaults, options);
                
        return this.each(function() {
                var obj = $(this);
                // retrive all products
                var products = $("input[type=hidden]",obj);
                var resultName = options.resultName;
                var cartItemCount = 0;
                var cartProductCount = 0; // count of unique products added
                var subTotal = 0; 
                var toolMaxImageHeight = 200;
                
                // Attribute Settings
                // You can assign the same you have given on the hidden elements
                var attrProductId = "pid";  // Product Id attribute
                var attrProductName = "pname"; // Product Name attribute   
                var attrProductPrice = "pprice"; // Product Price attribute  
                var attrProductImage = "pimage"; // Product Image attribute
                var attrCategoryName = "pcategory";
                var attrCategoryId = "pcategory_id";
                var attrStock = "pstock";
                var attrLength = "plength";
                var attrWidth = "pwidth";
                var attrHeight = "pheight";
                var attrWeight = "pweight";
                var attrDiscount = "pdiscount";
                var attrAvailable_discount = "pavailable_discount";
                var attrSupplier = "psupplier";
                var attrProduce_date = "pproduce_date";
                var attrExpiry_date = "pexpiry_date";
                var attrProduce_area = "pproduce_area";
                var attrOff_shelf = "poff_shelf";
                var	attrDescription = "pdescription";
                
                // Labels & Messages              
                var labelCartMenuName = '我的購物車 (_COUNT_)';  // _COUNT_ will be replaced with cart count
                var labelCartMenuNameTooltip = '購物車 | 購買商品: _PRODUCTCOUNT_ | 購買數量: _ITEMCOUNT_';
                var labelProductDetail = '商品介紹';
                var labelProductMenuName = '商品';
                var labelSearchButton = '查詢';
                var labelSearchText = '關鍵字';
                var labelCategoryText = '種類';
                var labelClearButton = '清除';
                var labelAddToCartButton = '購買'; 
                var labelShowToProductDetailButton ='介紹';
                var labelQuantityText = '數量';
                var labelProducts = '商品';
                var labelPrice = '金額';
                var labelSubtotal = '小計';
                var labelTotal = '總金額';
                var labelRemove = '移除';
                var labelCheckout = '下一步';
                
                var messageConfirmRemove = '確定要移除"_PRODUCTNAME_"商品嗎？'; //  _PRODUCTNAME_ will be replaced with actula product name
                var messageCartEmpty = "您的購物車目前是空的！";
                var messageProductEmpty = "沒有商品";
                var messageProductAddError = "該商品暫時不能購買";
                var messageProductAddStockError = '該商品存庫已剩_STOCK_';
                var messageItemAdded = '商品被加入購物車';
                var messageItemRemoved = '商品已從購物車移除';
                var messageQuantityUpdated = '該商品購買數量已更新';
                var messageQuantityErrorAdd = '購買數量有問題！';
                var messageQuantityErrorUpdate = '購買數量有問題！';
                
                //Create Main Menu
                cartMenu = labelCartMenuName.replace('_COUNT_','0'); // display default
                var btShowCart = $('<a>'+cartMenu+'</a>').attr("href","#scart");
                var btShowProductList = $('<a>'+labelProductMenuName+'</a>').attr("href","#sproducts");
                var btShowProductDetail = $('<a>'+labelProductDetail+'</a>').attr("href","#sproductdetail");
                var msgBox2 = $('<div></div>').addClass("scMessageBar2").hide();
                
                var elmProductMenu = $("<li></li>").append(btShowProductList);
                var elmCartMenu = $("<li></li>").append(btShowCart);
                var elmProductDetail = $("<li></li>").append(btShowProductDetail);
                var elmMsgBox = $("<li></li>").append(msgBox2);
                var elmMenuBar = $('<ul></ul>').addClass("scMenuBar").append(elmProductMenu).append(elmProductDetail).append(elmCartMenu).append(elmMsgBox);
                obj.prepend(elmMenuBar);
                
                // Create Search Elements
                var elmPLSearchPanel = $('<div></div>').addClass("scSearchPanel");
                if(options.enableSearch){
                  var btSearch = $('<a>'+labelSearchButton+'</a>').attr("href","#").addClass("scSearch").attr("title","查詢商品");                
                  $(btSearch).click(function() {
                     var searcStr = $(txtSearch).val();                      
                     populateProducts(searcStr);
                     return false;
                  }); 
                  var btClear = $('<a>'+labelClearButton+'</a>').attr("href","#").addClass("scSearch").attr("title","取消查詢");
                  $(btClear).click(function() {
                     $(txtSearch).val('');                      
                     populateProducts('');
                     return false;
                  });
                  var txtSearch = $('<input type="text" />').attr("value","").addClass("scTxtSearch")
                  $(txtSearch).keyup(function() {
                     var searcStr = $(this).val();                      
                     populateProducts(searcStr);
                  });  
                  var lblSearch = $('<label>'+labelSearchText+':</label>').addClass("scLabelSearch");                                                 
                  elmPLSearchPanel.append(lblSearch).append(txtSearch).append(btSearch).append(btClear);                
                }
                // Create Category filter
                if(options.enableCategoryFilter){
                  var lblCategory = $('<label>'+labelCategoryText+':</label>').addClass("scLabelCategory");
                  var elmCategory = $("<select></select>").addClass("scSelCategory");                
                  elmPLSearchPanel.append(lblCategory).append(elmCategory);                
                  fillCategory();
                }

                // Create Product List                
                var elmPLContainer = $('<div></div>').addClass("scTabs").hide();
                elmPLContainer.prepend(elmPLSearchPanel);
                
                var elmPLProducts = $('<div></div>').addClass("scProductList");
                elmPLContainer.append(elmPLProducts);
                
                // Create Cart
                var elmCartContainer = $('<div></div>').addClass("scTabs").hide();
                var elmCartHeader = $('<div></div>').addClass("scCartHeader");
                var elmCartHeaderTitle1 = $('<label>'+labelProducts+'</label>').addClass("scCartTitle scCartTitle1");
                var elmCartHeaderTitle2 = $('<label>'+labelPrice+'</label>').addClass("scCartTitle scCartTitle2");
                var elmCartHeaderTitle3 = $('<label>'+labelQuantityText+'</label>').addClass("scCartTitle scCartTitle3");
                var elmCartHeaderTitle4 = $('<label>'+labelSubtotal+'</label>').addClass("scCartTitle scCartTitle4");
                var elmCartHeaderTitle5 = $('<label></label>').addClass("scCartTitle scCartTitle5");
                elmCartHeader.append(elmCartHeaderTitle1).append(elmCartHeaderTitle2).append(elmCartHeaderTitle3).append(elmCartHeaderTitle4).append(elmCartHeaderTitle5);
                                
                var elmCartList = $('<div></div>').addClass("scCartList");
                
                elmCartContainer.append(elmCartHeader).append(elmCartList);
                
                // Create Product Detail
                var elmPDContainer = $('<div></div>').addClass("scTabs").hide();
                var elmPDProductDetail = $('<div></div>').addClass("scProductDetail");
                elmPDContainer.append(elmPDProductDetail);
                
                obj.append(elmPLContainer).append(elmPDContainer).append(elmCartContainer);
                

                
                // Create Bottom bar
                var elmBottomBar = $('<div></div>').addClass("scBottomBar");
                var elmBottomSubtotalText = $('<label>'+labelTotal+':</label>').addClass("scLabelSubtotalText");
                var elmBottomSubtotalValue = $('<label>'+getMoneyFormatted(subTotal)+'</label>').addClass("scLabelSubtotalValue");
                var btCheckout = $('<a>'+labelCheckout+'</a>').attr("href","#").addClass("scCheckoutButton");                
                $(btCheckout).click(function() {
                   if($.isFunction(options.onCheckout)) {
                      // calling onCheckout event;
                      options.onCheckout.call(this,elmProductSelected);
                   }else{
                      $(this).parents("form").submit();                   
                   }
                   return false;
                });
                elmBottomBar.append(btCheckout).append(elmBottomSubtotalValue).append(elmBottomSubtotalText);
                obj.append(elmBottomBar);
                
                // Create Tooltip
                var tooltip = $('<div></div>').addClass('tooltip').hide();
                obj.append(tooltip);
                obj.bind("mousemove",function(){
              		tooltip.hide();                    
                  return true;
              	});                
                
                // Create SelectList                                
                var elmProductSelected = $('select[name="'+resultName+'"]',obj);
                if(elmProductSelected.length <= 0){
                   elmProductSelected = $("<select></select>").attr("name",resultName).attr("multiple","multiple").hide();
                   refreshCartValues();
                }else{ 
                   elmProductSelected.attr("multiple","multiple").hide();
                   populateCart(); // pre-populate cart if there are selected items  
                }                 
                obj.append(elmProductSelected);
                
                // prepare the product list
                populateProducts();
                
                //console.log(options.selected);
                if(options.selected == '1'){
                   showCart();
                }else{
                   showProductList();
                }	       

                $(btShowProductList).bind("click", function(e){
                    showProductList();
                    return false;
                }); 
                $(btShowCart).bind("click", function(e){
                    showCart();
                    return false;
                });
                $(btShowProductDetail).bind("click", function(e){
                	showProductDetail();
                	return false;
                });

                function showCart(){  
                     $(btShowProductList).removeClass("sel");
                     $(btShowCart).addClass("sel");
                     $(btShowProductDetail).removeClass("sel");  
                     $(elmPLContainer).hide();
                     $(elmPDContainer).hide();
                     $(elmCartContainer).show();
                }
                function showProductList(){ 
                     $(btShowProductList).addClass("sel");
                     $(btShowCart).removeClass("sel");  
                     $(btShowProductDetail).removeClass("sel");  
                     $(elmCartContainer).hide();
                     $(elmPDContainer).hide();
                     $(elmPLContainer).show();
                }
                function showProductDetail(){
                	$(btShowProductDetail).addClass("sel");
                	$(btShowProductList).removeClass("sel");
                	$(btShowCart).removeClass("sel");  
                	$(elmPLContainer).hide();
                	$(elmCartContainer).hide();
                	$(elmPDContainer).show();
                }
                
                function addToCart(i,qty){
                     var addProduct = products.eq(i);
                     var pId = $(addProduct).attr(attrProductId);
                     var pName = $(addProduct).attr(attrProductName);
                     var pPrice = $(addProduct).attr(attrProductPrice);
                     var pStock = $(addProduct).attr(attrStock);
                     var pDiscount = $(addProduct).attr(attrDiscount);
                     var pAvailable_discount = $(addProduct).attr(attrAvailable_discount);

                     if(pStock==0){
                    	 showHighlightMessage(messageProductAddStockError.replace('_STOCK_','0'));
                     }else if(addProduct.length > 0 ){
                        if($.isFunction(options.onAdd)) {
                          // calling onAdd event; expecting a return value
                          // will start add if returned true and cancel add if returned false
                          if(!options.onAdd.call(this,$(addProduct),qty)){
                            return false;
                          }
                        }
                        
                        if(pDiscount!=100 && (new Date()<new Date(pAvailable_discount))){
                        	pPrice = Math.round(pPrice*pDiscount/100);
                        	
                        }

                        // Check wheater the item is already added
                        var productItem = elmProductSelected.children("option[rel=" + i + "]");
                        if(productItem.length > 0){
                        	
                            // Item already added, update the quantity and total
                            var curPValue =  productItem.attr("value");
                            var valueArray = curPValue.split('|');
                            var prdId = valueArray[0];
                            var prdName = valueArray[1];
                            var prdPrice = valueArray[2];
                            var prdQty = valueArray[3];
                            var nowStock = pStock-prdQty;
                            prdQty = (prdQty-0) +  (qty-0);
                            prdPrice = pPrice;
                            
                            if(prdQty<=pStock){
                                var newPValue =  prdId + '|' + prdName+ '|' + prdPrice+ '|' + prdQty;
                                productItem.attr("value",newPValue).attr('selected', true);    
        
                                var prdTotal = getMoneyFormatted(pPrice * prdQty);
                                // Now go for updating the design
                                var lalQuantity =  $('#lblQuantity'+i).val(prdQty);
                                var lblTotal =  $('#lblTotal'+i).html(prdTotal);
                                // show product quantity updated message
                                showHighlightMessage(messageQuantityUpdated);     
                            }else{
                            	showHighlightMessage(messageProductAddStockError.replace('_STOCK_',nowStock));
                            }
                                                 
                        }else{
                            // This is a new item so create the list
                            var prodStr = pId + '|' + pName + '|' + pPrice + '|' + qty;
                            productItem = $('<option></option>').attr("rel",i).attr("value",prodStr).attr('selected', true).html(pName);
                            elmProductSelected.append(productItem);
                            addCartItemDisplay(addProduct,qty);
                            // show product added message
                            showHighlightMessage(messageItemAdded);                            
                        }
                        // refresh the cart
                        refreshCartValues();
                        // calling onAdded event; not expecting a return value
                        if($.isFunction(options.onAdded)) {
                          options.onAdded.call(this,$(addProduct),qty);
                        }
                     }else{
                        showHighlightMessage(messageProductAddError);
                     }
                }
                
                function showProductDetailDisplay(objProd){
                	elmPDProductDetail.html("");
                    var pId = $(objProd).attr(attrProductId);
                    var pIndex = products.index(objProd);
                    var pName = $(objProd).attr(attrProductName);
                    var pPrice = $(objProd).attr(attrProductPrice);
                    var pWeight = $(objProd).attr(attrWeight);
                    var pProduce_date = $(objProd).attr(attrProduce_date);
                    var pProduce_area = $(objProd).attr(attrProduce_area);
                    var pExpiry_date = $(objProd).attr(attrExpiry_date);
                    var pCategoryName = $(objProd).attr(attrCategoryName);
                    var pDiscount = $(objProd).attr(attrDiscount);
                    var pAvailable_discount = $(objProd).attr(attrAvailable_discount);
                    var pImgSrc = $(objProd).attr(attrProductImage);
                    var pDesc = $(objProd).attr(attrDescription);
                    //console.log(pId);
                    
                   
                    var content = "";
                    
                    content += "<table>";
	                    content += "<tr>";
		                    content += "<td><img src='"+pImgSrc+"' id='scProductDetailImage'/></td>";
		                    
		                    content += "<td><table>";
		                    		content += "<tr>";
		                    			content += "<td style='color:#234112;font-size:16px;font-weight:800'>"+pName+"</td>";
		                    		content += "</tr>";		
		                    		content += "<tr>";
		                    			content += "<td>售價</td><td>"+pPrice+"</td>";
		                    		content += "</tr>";
		                    		
		                    		if(pDiscount!=100 &&  (new Date()<new Date(pAvailable_discount))){
			                    	content += "<tr>";
		                    			content += "<td>優惠價</td><td>"+Math.round(pPrice*pDiscount/100)+"</td>";
		                    		content += "</tr>";		                    			
			                    	content += "<tr>";
	                    				content += "<td>優惠截止</td><td>"+pAvailable_discount+"</td>";
	                    			content += "</tr>";		     		                    			
		                    		}    
		                    		content += "<tr>";
		                    			content += "<td>重量</td><td>"+pWeight+"</td>";
	                    			content += "</tr>";
		                    		content += "<tr>";
		                    			content += "<td>保存期限</td><td>"+pExpiry_date+"</td>";
	                    			content += "</tr>";
		                    		content += "<tr>";
		                    			content += "<td>生產日期</td><td>"+pProduce_date+"</td>";
	                    			content += "</tr>";
	                    			content += "<tr>";
	                    				content += "<td>生產地區</td><td>"+pProduce_area+"</td>";
	                    			content += "</tr>";
	                    			content += "<tr>";
                    					content += "<td>類型</td><td>"+pCategoryName+"</td>";
                    				content += "</tr>";		                    			
		                    content += "</table></td>";
		                    
	                    content += "</tr>";
	                content += "</table>";
	                    
                    var btAddToCart = $('<a>放入購物車</a>').attr("href","#").attr("rel",pIndex).attr("title","放入購物車");
                   
                    $(btAddToCart).bind("click", function(e){
                        var idx = $(this).attr("rel");
                        var qty = $(this).siblings("input").val();
                        if(validateNumber(qty)){
                           addToCart(idx,qty);
                        }else{
                          $(this).siblings("input").val(1);                                                 
                          showHighlightMessage(messageQuantityErrorAdd);
                        }
                        return false;
                    });

                    var inputQty = $('<input type="hidden" value="1" />');  
                    
                    elmPDProductDetail.append(inputQty).append(btAddToCart);
	                
	                content += "<table>";
	                	content += "<tr>";
	                		content += "<td>"+pDesc+"</td>";
	                	content += "</tr>";
	                content += "</table>";
	                
                    elmPDProductDetail.append(content);
                                  
                }                
                
                function addCartItemDisplay(objProd,Quantity){
                    var pId = $(objProd).attr(attrProductId);
                    var pIndex = products.index(objProd);
                    var pName = $(objProd).attr(attrProductName);
                    var pPrice = $(objProd).attr(attrProductPrice);
                    var prodImgSrc = $(objProd).attr(attrProductImage);
                    var pDiscount = $(objProd).attr(attrDiscount);
                    var pAvailable_discount = $(objProd).attr(attrAvailable_discount);
                    var pStock = $(objProd).attr(attrStock);
                    
                    if(pDiscount!=100 && (new Date()<new Date(pAvailable_discount))){
                    	pPrice = Math.round(pPrice*pDiscount/100);
                    }
                    
                    //console.log(pPrice);
                    var pTotal = (pPrice - 0) * (Quantity - 0);
                    pTotal = getMoneyFormatted(pTotal);
                    // Now Go for creating the design stuff
                    
                    $('.scMessageBar',elmCartList).remove();
                    var elmCP = $('<div></div>').attr("id","divCartItem"+pIndex).addClass("scCartItem");
                    
                    var elmCPTitle1 = $('<div></div>').addClass("scCartItemTitle scCartItemTitle1");                            
                    if(prodImgSrc && options.enableImage && prodImgSrc.length>0){
                        var prodImg = $("<img></img>").attr("src",prodImgSrc).addClass("scProductImageSmall");
                        if(prodImg && options.enableImageTooltip){
                          	prodImg.bind("mouseenter mousemove",function(){
                                showTooltip($(this));                    
                              return false;
                          	});
                          	prodImg.bind("mouseleave",function (){
                          		tooltip.hide();
                          		return true;
                          	});
                        }
                        elmCPTitle1.append(prodImg);
                    }

                                    
                    elmCPTitle1.append("<span class='title'>"+pName+"</span>");                        
                    var elmCPTitle2 = $('<label>'+pPrice+'</label>').addClass("scCartItemTitle scCartItemTitle2");
                    var inputQty = $('<input type="text" value="'+Quantity+'" />').attr("id","lblQuantity"+pIndex).attr("rel",pIndex).addClass("scTxtQuantity2");                    
                    $(inputQty).bind("change", function(e){
                        var newQty = $(this).val();
                        var prodIdx = $(this).attr("rel");
                        newQty = newQty - 0;
                        
                        if(validateNumber(newQty) && pStock>=newQty){
                           updateCartQuantity(prodIdx,newQty);
                        }else if(pStock<newQty){
                            var productItem = elmProductSelected.children("option[rel=" + prodIdx + "]");
                            var pValue = $(productItem).attr("value");
                            var valueArray = pValue.split('|'); 
                            var pQty = valueArray[3];
                            $(this).val(pQty);             
                        	showHighlightMessage(messageProductAddStockError.replace('_STOCK_',pStock));
                        }else{
                          var productItem = elmProductSelected.children("option[rel=" + prodIdx + "]");
                          var pValue = $(productItem).attr("value");
                          var valueArray = pValue.split('|'); 
                          var pQty = valueArray[3];
                          $(this).val(pQty);                                                 
                          showHighlightMessage(messageQuantityErrorUpdate);
                        }
                        return true;
                    });
                    
                    var elmCPTitle3 = $('<div></div>').append(inputQty).addClass("scCartItemTitle scCartItemTitle3");

                    var elmCPTitle4 = $('<label>'+pTotal+'</label>').attr("id","lblTotal"+pIndex).addClass("scCartItemTitle scCartItemTitle4");
                    var btRemove = $('<a>'+labelRemove+'</a>').attr("rel",pIndex).attr("href","#").addClass("scRemove").attr("title","Remove from Cart");
                    $(btRemove).bind("click", function(e){
                        var idx = $(this).attr("rel");
                        removeFromCart(idx);
                        return false;
                    });
                    var elmCPTitle5 = $('<div></div>').addClass("scCartItemTitle scCartItemTitle5");
                    elmCPTitle5.append(btRemove);
                    

                    elmCP.append(elmCPTitle1).append(elmCPTitle2).append(elmCPTitle3).append(elmCPTitle4).append(elmCPTitle5);
                    elmCartList.append(elmCP);
                }
                
                function removeFromCart(idx){
                    var pObj = products.eq(idx);
                    var pName = $(pObj).attr(attrProductName)
                    var removeMsg = messageConfirmRemove.replace('_PRODUCTNAME_',pName); // display default
                    if(confirm(removeMsg)){
                        if($.isFunction(options.onRemove)) {
                          // calling onRemove event; expecting a return value
                          // will start remove if returned true and cancel remove if returned false
                          if(!options.onRemove.call(this,$(pObj))){
                            return false;
                          }
                        }
                        var productItem = elmProductSelected.children("option[rel=" + idx + "]");
                        var pValue = $(productItem).attr("value");
                        var valueArray = pValue.split('|');
                        var pQty = valueArray[3];
                        productItem.remove();
                        $("#divCartItem"+idx,elmCartList).slideUp("slow",function(){$(this).remove();
                        showHighlightMessage(messageItemRemoved);
                        //Refresh the cart
                        refreshCartValues();});
                        if($.isFunction(options.onRemoved)) {
                          // calling onRemoved event; not expecting a return value
                          options.onRemoved.call(this,$(pObj));
                        }
                    }
                }
                
                function updateCartQuantity(idx,qty){
                    var pObj = products.eq(idx);
                    var productItem = elmProductSelected.children("option[rel=" + idx + "]");
                    var pPrice = $(pObj).attr(attrProductPrice);  
                    var pDiscount = $(pObj).attr(attrDiscount);
                    var pAvailable_discount = $(pObj).attr(attrAvailable_discount);
                    var pValue = $(productItem).attr("value");
                    var valueArray = pValue.split('|');
                    var prdId = valueArray[0];
                    var prdName = valueArray[1];
                    var prdPrice = valueArray[2];
                    var curQty = valueArray[3];    
                    
                    if(pDiscount!=100 && (new Date()<new Date(pAvailable_discount))){
                    	pPrice = Math.round(pPrice*pDiscount/100);
                    	prdPrice = pPrice;
                    }
                    
                    if($.isFunction(options.onUpdate)) {
                        // calling onUpdate event; expecting a return value
                        // will start Update if returned true and cancel Update if returned false
                        if(!options.onUpdate.call(this,$(pObj),qty)){
                          $('#lblQuantity'+idx).val(curQty);
                          return false;
                        }
                    }


                    var newPValue =  prdId + '|' + prdName + '|' + prdPrice + '|' + qty;
                    $(productItem).attr("value",newPValue).attr('selected', true);    
                    var prdTotal = getMoneyFormatted(pPrice * qty);
                        // Now go for updating the design
                    var lblTotal =  $('#lblTotal'+idx).html(prdTotal); 
                    showHighlightMessage(messageQuantityUpdated);
                    //Refresh the cart
                    refreshCartValues();
                    if($.isFunction(options.onUpdated)){
                        // calling onUpdated event; not expecting a return value
                        options.onUpdated.call(this,$(pObj),qty);
                    }                    
                }
                
                function refreshCartValues(){
                    var sTotal = 0;
                    var cProductCount = 0;
                    var cItemCount = 0;
                    elmProductSelected.children("option").each(function(n) {
                        var pIdx = $(this).attr("rel"); 
                        var pObj = products.eq(pIdx);                     
                        var pValue = $(this).attr("value");
                        var valueArray = pValue.split('|');
                        var prdId = valueArray[0];
                        var prdName = valueArray[1];
                        var pQty = valueArray[3];
                        var pPrice =  $(pObj).attr(attrProductPrice);
                        var pDiscount = $(pObj).attr(attrDiscount);
                        var pAvailable_discount = $(pObj).attr(attrAvailable_discount);
                        if(pDiscount!=100 && (new Date()<new Date(pAvailable_discount))){
                        	pPrice = Math.round(pPrice*pDiscount/100);
                        }
                        sTotal = sTotal + ((pPrice - 0) * (pQty - 0));
                        cProductCount++;
                        cItemCount = cItemCount + (pQty-0);
                    });
                    subTotal = sTotal;
                    cartProductCount = cProductCount;
                    cartItemCount = cItemCount;
                    elmBottomSubtotalValue.html(getMoneyFormatted(subTotal));
                    cartMenu = labelCartMenuName.replace('_COUNT_',cartProductCount);  
                    cartMenuTooltip = labelCartMenuNameTooltip.replace('_PRODUCTCOUNT_',cartProductCount).replace('_ITEMCOUNT_',cartItemCount);
                    btShowCart.html(cartMenu).attr("title",cartMenuTooltip);
                    
                    if(cProductCount <= 0){
                       showMessage(messageCartEmpty,elmCartList);
                    }else{
                       $('.scMessageBar',elmCartList).remove();
                    }
                }
                
                function populateCart(){
                   elmProductSelected.children("option").each(function(n) {
                        var curPValue =  $(this).attr("value");
                        var valueArray = curPValue.split('|');
                        var prdId = valueArray[0];
                        var prdName = valueArray[1];
                        var prdPrice = valueArray[2];
                        var prdQty = valueArray[3];
                        
                        if(!prdQty){
                          prdQty = 1; // if product quantity is not present default to 1
                        }
                        var objProd = jQuery.grep(products, function(n, i){return ($(n).attr(attrProductId) == prdId);});                        
                        var prodIndex = products.index(objProd[0]);
                        var prodName = $(objProd[0]).attr(attrProductName);
                        $(this).attr('selected', true);
                        $(this).attr('rel', prodIndex);
                        $(this).html(prodName);
                        cartItemCount++; 
                        addCartItemDisplay(objProd[0],prdQty);                         
                   });
                   // Reresh the cart
                   refreshCartValues();
                }
                
                function fillCategory(){
                   var catCount = 0;
                   var catItem = $('<option></option>').attr("value",'').attr('selected', true).html('全部');
                   elmCategory.prepend(catItem);                   
                   $(products).each(function(i,n){
                        var pCategory = $(this).attr(attrCategoryName);
                        if(pCategory && pCategory.length>0){
                          var objProd = jQuery.grep(elmCategory.children('option'), function(n, i){return ($(n).val() == pCategory);});
                          if(objProd.length<=0){
                            catCount++;
                            var catItem = $('<option></option>').attr("value",pCategory).html(pCategory);
                            elmCategory.append(catItem);
                          }                        
                        }
                            
                   });
                   if(catCount>0){
                      $(elmCategory).bind("change", function(e){
                        $(txtSearch).val('');
                        populateProducts();
                        return true;
                    });                      
                   }else{
                      elmCategory.hide();
                      lblCategory.hide();
                   }
                }
                
                
                function populateProducts(searchString){
                   var isSearch = false;
                   var productCount = 0;
                   var selectedCategory = $(elmCategory).children(":selected").val();
                   // validate and prepare search string
                   if(searchString){
                      searchString = trim(searchString);
                     if(searchString.length>0){
                         isSearch = true;
                         searchString = searchString.toLowerCase();
                     }                      
                   }
                   // Clear the current items on product list
                   elmPLProducts.html('');
                   // Lets go for dispalying the products
                   $(products).each(function(i,n){
                	  var product = $(this);
                	  var productId = $(this).attr(attrProductId);
                      var productName = $(this).attr(attrProductName);
                      var productCategory = $(this).attr(attrCategoryName);
                      var productPrice = $(this).attr(attrProductPrice); 
                      var prodImgSrc = $(this).attr(attrProductImage);
                      var productDiscount = $(this).attr(attrDiscount);
                      var productAvailable_discount = $(this).attr(attrAvailable_discount);
                      
                      var isValid = true;
                      var isCategoryValid = true;
                      if(isSearch){
                        if(productName.toLowerCase().indexOf(searchString) == -1) {
                          isValid = false;
                        }else{
                          isValid = true;
                        }
                      }
                      // Category filter
                      if(selectedCategory && selectedCategory.length>0){
                        selectedCategory = selectedCategory.toLowerCase();
                        if(productCategory.toLowerCase().indexOf(selectedCategory) == -1) {
                          isCategoryValid = false;
                        }else{
                          isCategoryValid = true;
                        }
                      }

                      if(isValid && isCategoryValid) {
                          productCount++; 

                          var elmProdDiv1 = $('<div></div>').addClass("scPDiv1");
                          if(prodImgSrc && options.enableImage && prodImgSrc.length>0){
                              var prodImg = $("<img></img>").attr("src",prodImgSrc).addClass("scProductImage");                      
                              if(prodImg && options.enableImageTooltip){
                              	prodImg.bind("mouseenter mousemove",function(){
                                    showTooltip($(this));                    
                                  return false;
                              	});
                              	prodImg.bind("mouseleave",function (){
                              		tooltip.hide();
                              		return true;
                              	});
                              }
                              elmProdDiv1.append(prodImg);
                          }
                          var elmProdDiv2 = $('<div></div>').addClass("scPDiv2"); // for product name, desc & price
                          var div2Content = "<span style='color:blue;font-size:110%'>"+productName+"</span></br>";
                          //console.log(productDiscount+" "+productAvailable_discount+"  "+(new Date()<new Date(productAvailable_discount)));
                          if(productDiscount==100 || (new Date()>new Date(productAvailable_discount))){
                        	  div2Content += "<string>售價：</string><span>"+productPrice+"</span>";
                          }else{
                        	  div2Content += "<string style='color:red'>特價：</string><span style='text-decoration:line-through;font-size:smaller'>"+productPrice+"</span>"+" <span style='color:red;font-size:small'>"+Math.round(productPrice*productDiscount/100)+"</span>";
                          }
                          
                          elmProdDiv2.html(div2Content);                      
                          
                          var elmProdDiv3 = $('<div></div>').addClass("scPDiv3"); // for button & qty    
                          var btAddToCart = $('<a>'+labelAddToCartButton+'</a>').attr("href","#").attr("rel",i).attr("title",labelAddToCartButton).addClass("scAddToCart");
                          var btShowToProductDetail = $('<a>'+labelShowToProductDetailButton+'</a>').attr("href","#").attr("rel",i).attr("title",labelShowToProductDetailButton).addClass("scProductToDetail");
                          
                          $(btAddToCart).bind("click", function(e){
                              var idx = $(this).attr("rel");
                              var qty = $(this).siblings("input").val();
                              if(validateNumber(qty)){
                                 addToCart(idx,qty);
                              }else{
                                $(this).siblings("input").val(1);                                                 
                                showHighlightMessage(messageQuantityErrorAdd);
                              }
                              return false;
                          });
                          
                          $(btShowToProductDetail).bind("click", function(e){
                              var id = $(this).attr("rel");
                              //console.log(id);
                              showProductDetailDisplay(product);
                              showProductDetail();
                              return false;
                          });
                          
                          var inputQty = $('<input type="hidden" value="1" />').addClass("scTxtQuantity");  
                          //var labelQty = $('<label>'+labelQuantityText+':</label>').addClass("scLabelQuantity");                    
                          elmProdDiv3.append(inputQty).append(btAddToCart).append(btShowToProductDetail);                  
    
                          var elmProds = $('<div></div>').addClass("scProducts");
    
                          elmProds.append(elmProdDiv1).append(elmProdDiv2).append(elmProdDiv3);
                          elmPLProducts.append(elmProds);
                      }                                                        
                   });
                   
                   if(productCount <= 0){
                       showMessage(messageProductEmpty,elmPLProducts);
                   }
                }
                
                // Display message
                function showMessage(msg, elm){
                  var elmMessage = $('<div></div>').addClass("scMessageBar").hide();
                  elmMessage.html(msg);                  
                  if(elm){
                     elm.append(elmMessage);
                     elmMessage.show();
                  }
                }
                
                function showHighlightMessage(msg){
                  msgBox2.html(msg);
          				msgBox2.fadeIn("fast", function() {
          					setTimeout(function() { msgBox2.fadeOut("fast"); }, 2200); 
          				}); 
                }

                // Show Image tooltip
                function showTooltip(img) {
            		  var height = img.height();
            		  var width = img.height();
                  var imgOffsetTop = img.offset().top;
                  jQuery.log(img.offset());                
                  jQuery.log(img.position());
                  jQuery.log("--------------");
                  tooltip.html('');
                  var tImage = $("<img></img>").attr('src',$(img).attr('src')); 
                  tImage.height(toolMaxImageHeight);
                  tooltip.append(tImage);
              		var top = imgOffsetTop - height ;
              		var left = width + 10;
                  tooltip.css({'top':top, 'left':left});	
                  tooltip.show("fast");                                              
                }
                
                function validateNumber(num){
                  var ret = false;
                  if(num){
                    num = num - 0;
                    if(num && num > 0){
                       ret = true;
                    }
                  }
                  return ret;
                }
                
                // Get the money formatted for display
                function getMoneyFormatted(val){
                  return val.toFixed(2);
                }
                // Trims the blankspace
                function trim(s){
                	var l=0; var r=s.length -1;
                	while(l < s.length && s[l] == ' ')
                	{	l++; }
                	while(r > l && s[r] == ' ')
                	{	r-=1;	}
                	return s.substring(l, r+1);
                }
                // format the product template
                function formatTemplate(str, objItem){
                  resStr =str.split("<%=");
                  var finalStr = '';
                  for(i=0;i<resStr.length;i++){
                    var tmpStr = resStr[i];
                    valRef = tmpStr.substring(0, tmpStr.indexOf("%>")); 
                    if(valRef!='' || valRef!=null){
                      var valRep = objItem.attr(valRef); //n[valRef]; 
                      if(valRep == null || valRep == 'undefined'){
                         valRep = '';
                      }
                      tmpStr = tmpStr.replace(valRef+'%>',valRep);
                      finalStr += tmpStr;
                    }else{
                      finalStr += tmpStr;
                    }
                  }
                  return finalStr;
                }

        });  
    };  
 
    // Default options
    $.fn.smartCart.defaults = {
          selected: 0,  // 0 = produts list, 1 = cart   
          resultName: 'products_selected[]', 
          enableImage: true,
          enableImageTooltip: false,
          enableSearch: true,
          enableCategoryFilter: true,
          productItemTemplate:'',
          cartItemTemplate:'',
          // Events
          onAdd: null,      // function(pObj,quantity){ return true; }
          onAdded: null,    // function(pObj,quantity){ }
          onRemove: null,   // function(pObj){return true;}
          onRemoved: null,  // function(pObj){ } 
          onUpdate: null,   // function(pObj,quantity){ return true; }
          onUpdated: null,  // function(pObj,quantity){ } 
          onCheckout: function(Obj){ 
        	 var have = false;
        	 var msg = "您購買清單如下:\n";
        	 var url = "member/order1.jsp?";
              Obj.children("option").each(function(n) {  
            	  have = true;
                  var pValue = $(this).attr("value");
                  var valueArray = pValue.split('|');
                  var prdId = valueArray[0];
                  var prdName = valueArray[1];
                  var prdPrice = valueArray[2];
                  var pQty = valueArray[3];       
                  msg+=prdName+" 單價:"+ prdPrice +" 數量:"+ pQty +"\n";
                  url+="id="+prdId+"&price="+prdPrice+"&quantity="+pQty+"&";
              });
              if(!have){
            	  alert("您還未選擇購買商品！");
              }else{
            	  if(confirm(msg)){
            		  window.location = url;
            	  }
              }
              
        	  
          } 
    };
    
    jQuery.log = function(message) {
      /*
      if(window.console) {
         console.debug(message);
      }
      */
    };

    
})(jQuery);
