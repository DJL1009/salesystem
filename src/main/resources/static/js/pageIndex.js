(function(w,d,u){
	var plist = util.get('plist');
	if(!plist){
		return;
	}
	var layer = new Layer();
	var loading = new Loading();

	var page = {
		init:function(){
			plist.addEventListener('click',function(e){
				var ele = e.target;
				var delId = ele.dataset && ele.dataset.del;
				if(delId){
					this.ondel(delId);
				}
			}.bind(this),false);
		},
		ondel:function(id){
			layer.reset({
				content:'确定要删除该内容吗？',
				onconfirm:function(){
					layer.hide();
					loading.show();
					ajax({
						url:'/api/delete',
						data:{
							id:id
						},
						success:function(json){
							this.delItemNode(id);
							loading.result('删除成功');
						}.bind(this),
						fail:function () {
							loading.result('删除失败');
                        }.bind(this),
						error:function (e) {
							console.log(e);
							loading.result('删除失败')
                        }.bind(this)
					});
				}.bind(this)
			}).show();
		},
		//删除对应商品展示
		delItemNode:function(id){
			var item = util.get('p-'+id);
			if(item && item.parentNode){
				item.parentNode.removeChild(item);
			}
		}
	};
	page.init();

})(window,document);

