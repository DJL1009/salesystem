
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
	
	$('add').onclick = function(e){
		//匿名用户跳转至登录页面
		var isLogin = $('isLogin');
		if(!isLogin){
			location.href = "/login";
		}
		var ele = e.target;
		var id = ele && ele.dataset.id;
		var title = ele && ele.dataset.title;
		var price = ele && ele.dataset.price;
		var num = $('allNum').innerHTML;
		var productDetail = {'id':id,'price':price,'title':title,'num':num};
		var name = 'products';
		var productList1 = new Array;
		var productList = util.getCookie(name);
		if(productList === "" || productList == null){
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
//		util.deleteCookie(name);
		e == window.event || e;
		layer.reset({
			content:'确认加入购物车吗？',
			onconfirm:function(){
				layer.hide();
				loading.show();
				loading.result('添加购物车成功');
			}.bind(this)
		}).show();
		return;
	};




