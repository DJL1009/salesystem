(function(w,d,u){
    var $ = function(id){
        return document.getElementById(id);
    };
	var settleAccount = util.get('settleAccount');
	if(!settleAccount){
		return;
	}
	var name = 'products';
	var products = util.getCookie(name);

	var str;
    if(products !== null && products !== "" && products.length !== 0){
    	str = "<tr>" +
            "<th>" + '商品' + "</th>" +
            "<th>" + '数量' + "</th>" +
            "<th>" + '价格' + "</th>" +
            "<th>" + '操作' + "</th>" +
            "</tr>";
        for(var i = 0; i < products.length; i++){
            str = str +
                "<tr id=tr-"+i+">" +
                "<td>" + products[i].title  + "</td>" +
                "<td>" +
                "<span class=\"lessNum\">"+ "-" + "</span>" +
                "<span class=\"totalNum\" id=\"allNum\">" + products[i].num + "</span>" +
                "<span id=\"thisId\">" + products[i].id + "</span>" +
                "<span class=\"moreNum\">"+ "+" + "</span>" +
                "</td>" +
                "<td>" + products[i].price + "</td>" +
                "<td>" +
                "<span class=\"u-btn u-btn-normal n-plist.del u-btn-xs\" data-del="+i+" >删除</span>" +
                "</td>" +
                "</tr>";
        }
        $("newTable").innerHTML = str;
    }else{
        $("newTable").hidden;
        str = "<div class=\"n-result\" >" +
                    "<h3>反正亦是空空空空如也</h3>" +
			      "</div>";
		$("nullProducts").innerHTML = str;
	}

	var loading = new Loading();
	var layer = new Layer();

	$('Account').onclick = function(e){
	    //购物车为空
	    if(products == null || products == "" || products.length == 0){
            layer.reset({
                content:"请先添加商品到购物车！！",
                onconfirm:function(){
                    layer.hide();
                }.bind(this)
            }).show();
            return;
        }
        var newProducts = products.map(function(arr){return {'id':arr.id,'number':arr.num};});
        layer.reset({
            content:'确认购买吗？',
            onconfirm:function(){
                layer.hide();
                loading.show();

                var xhr = new XMLHttpRequest();
                var data = JSON.stringify(newProducts);
                xhr.onreadystatechange = function(){
                    if(xhr.readyState === 4){
                        var status = xhr.status;
                        if(status >= 200 && status < 300 || status === 304){
                            var json = JSON.parse(xhr.responseText);
                            if(json && json.state === 0){
                                console.log(json.data);
                                loading.result('购买成功',function(){location.href = '/account';});//
                                util.deleteCookie(name);
                            }else{
                                alert(json.message);
                                loading.result("购买失败");
                            }
                        }else{
                            loading.result(message||'购买失败');
                        }
                    }
                };
                xhr.open('post','/api/buy');
                var token = document.querySelector("meta[name='_csrf']").getAttribute("content");
                //POST请求头设置csrf token，解决403问题；也可在data域添加_csrf解决该问题
                xhr.setRequestHeader("X-CSRF-TOKEN",token);
                xhr.setRequestHeader('Content-Type','application/json');
                xhr.send(data);
            }.bind(this)
        }).show();
	};

	//退出
	$('back').onclick = function(){
		window.history.back();
		return false;
	};

	//购物车操作
    var page = {
        init:function(){
            $('newTable').addEventListener('click',function(e){
            	//删除购物车内容
                var ele = e.target;
                var delId = ele.dataset && ele.dataset.del;
                if(delId){
                    this.ondel(delId);
                    return;
                }
				//修改商品数量
                var ee = arguments[0] || window.event;
                target = ee.srcElement ? ee.srcElement : ee.target;
                if(target.nodeName === "SPAN" && target.className === "moreNum"){
                    var num = target.parentElement.children[1].textContent;
                    var id = target.parentElement.children[2].textContent;
                    num ++;
                    target.parentElement.children[1].textContent = num;
                    util.modifyOne(products,id,num);
                }else if(target.nodeName === "SPAN" && target.className === "lessNum"){
                    var num = target.parentElement.children[1].textContent;
                    var id = target.parentElement.children[2].textContent;
                    num --;
                    if(num < 0){
                        alert("既然来了，客官多少还是买点吧！");
                    }else{
                        target.parentElement.children[1].textContent = num;
                        util.modifyOne(products,id,num);
                    }
                }
            }.bind(this),false);
        },
		//删除购物车内容
        ondel:function(id){
            layer.reset({
                content:'确定要删除该内容吗？',
                onconfirm:function(){
                    layer.hide();
                    products.splice(id,1);             //删除指定id的记录
                    util.setCookie(name,products);     //重置cookie
					window.location.href = "/settleAccount";
                }.bind(this)
            }).show();
        },
        //删除首页对应商品展示
        delItemNode:function(id){
            var item = util.get('p-'+id);
            if(item && item.parentNode){
                item.parentNode.removeChild(item);
            }
        }
    };
    page.init();
})(window,document);