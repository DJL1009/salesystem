
	var $ = function(id){
		return document.getElementById(id);
	};
	
	$('plusNum').onclick = function(e){
		e = window.event || e;
		o = e.srcElement || e.target;
		var num = $('allNum').textContent;
		if(num > 1){
			num --;
			$('allNum').innerHTML = num;
		}else{
		 alert("不能再减了，多少买点啊！");
		}
	};

	$('addNum').onclick = function(e){
		e = window.event || e;
		o = e.srcElement || e.target;
		var num = $('allNum').textContent;
		var quantity = $('quantity').textContent;
		quantity = Number(quantity);
		if(num < quantity){
            num ++;
            $('allNum').innerHTML = num;
		}else{
			alert("不能再多啦！这真不是我不想卖。。")
		}
	};
	
	var loading = new Loading();
	var layer = new Layer();

	$('buy').onclick = function (e) {
        //匿名用户跳转至登录页面
        var isLogin = $('isLogin');
        if(!isLogin){
            location.href = "/login";
            return;
        }

        var ele = e.target;
        var id = ele && ele.dataset.id;
        var num = $('allNum').innerHTML;
        var stock = ele && ele.dataset.stock;
        if(stock == 0){
            oosMessage();
            return;
        }
        layer.reset({
            content:"确定购买？",
            onconfirm:function(){
                layer.hide();
                loading.show();

				var newProduct = [{"id":id,"number":num}];
                var data = JSON.stringify(newProduct);
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function(){
                    if(xhr.readyState === 4){
                        var status = xhr.status;
                        if(status >= 200 && status < 300 || status === 304){
                            var json = JSON.parse(xhr.responseText);
                            if(json && json.state === 0){
                                loading.result('购买成功',function(){location.href = '/account';});
                            }else{
                                alert(json.message);
                            }
                        }else{
                            loading.result(message||'购买失败');
                        }
                    }
                };
                xhr.open('post','/api/buy');
                var token = document.querySelector("meta[name='_csrf']").getAttribute("content");
                xhr.setRequestHeader("X-CSRF-TOKEN",token);
                xhr.setRequestHeader('Content-Type','application/json');
                xhr.send(data);

                loading.result('购买成功');
            }.bind(this)
        }).show();
	};

	//添加商品到购物车
	$('add').onclick = function(e){
		//匿名用户跳转至登录页面
		var isLogin = $('isLogin');
		if(!isLogin){
			location.href = "/login";
			return;
		}
		var ele = e.target;
		var id = ele && ele.dataset.id;
		var title = ele && ele.dataset.title;
		var price = ele && ele.dataset.price;
        var stock = ele && ele.dataset.stock;
        if(stock == 0){
            oosMessage();
            return;
        }
		var num = $('allNum').innerHTML;
		var productDetail = {'id':id,'price':price,'title':title,'num':num};
		var name = 'products';
		var productList = util.getCookie(name);

		layer.reset({
			content:'确认加入购物车吗？',
			onconfirm:function(){
                if(productList === "" || productList == null){
                    var productList1 = new Array;
                    productList1.push(productDetail);
                    util.setCookie(name,productList1);
                }else if(util.findOne(productList,id)){
                    util.modifyTwo(productList,id,num);
                    util.setCookie(name,productList);
                }else{
                    productList.push(productDetail);
                    util.setCookie(name,productList);
                }
                console.log(document.cookie);
				layer.hide();
				loading.show();
				loading.result('添加购物车成功');
			}.bind(this)
		}).show();
	};
	//out of stock 无库存提示
    function oosMessage() {
        layer.reset({
            content:'该商品暂时缺货！！',
            onconfirm:function(){
                layer.hide();
                location.reload();
            }.bind(this)
        }).show();
    }


