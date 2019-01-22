(function(w,d,u){
	var form = util.get('form');
	if(!form){
		return;
	}
	//form
	var title = form['title'];
	var summary = form['summary'];
	var image = form['image'];
	var detail = form['detail'];
	var price = form['price'];
	var quantity = form['quantity'];

	var uploadInput = form['file'];
	var isSubmiting = false;
	var imgpre = util.get('imgpre');
	var loading = new Loading();
	var imageUrl;
	var imageMode = "urlUpload";

	var page = {
		init:function(){
			var $ = function(id){
				return document.getElementById(id);
			};
			//上传方式选择
			$('uploadType').onclick = function(e){
				e = window.event || e;
				o = e.srcElement || e.target;
				if(o.nodeName==="INPUT"){
					var s,h;//show , hide
					o.value==='url'?(s='urlUpload',h='fileUpload'):(s='fileUpload',h='urlUpload');
					imageMode = o.value==='url'?"urlUpload":"fileUpload";
					image.classList.remove("z-err");
					uploadInput.classList.remove("z-err");
					$(s).style.display='block';
					$(h).style.display='none';
				}
			};
            //图片链接地址输入完成事件
            image.onblur = function(){
                var value = image.value.trim();
                if(value !== ''){
                    imgpre.src = value;
                }
            };
			//选择本地图片文件
			$('fileUp').onchange = function (e) {
                var maxAllowedSize = 1000000; //图片大小限制1Mb
				var file = uploadInput.files[0];
				//判断文件类型
                if((file.type).indexOf('image/')===-1){
                    alert("请选择图片上传");
                    this.value = null;
                    return;
                }
                //判断图片大小
                if(file.size > maxAllowedSize) {
                    alert("超过文件上传大小限制");
                    this.value = null;
                }
            };
			//上传本地图片文件
			$('upload').addEventListener('click', function (){
				if(uploadInput.files.length === 0){
					alert("请先选择文件");
				}else{
                    var file = uploadInput.files[0];
                    var imgForm = new FormData();
                    var token = document.querySelector("meta[name='_csrf']").getAttribute("content");
                    imgForm.append('file', file, file.name);
                    //使用'multipart/form-data'，将文件以二进制形式上传，这样可以实现多种类型的文件上传
                    imgForm.enctype = "multipart/form-data";
                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "/api/upload", true);
                    xhr.setRequestHeader("X-CSRF-TOKEN",token); //请求头添加CSRF token
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            var o = JSON.parse(xhr.responseText);
                            if(o.state === 0){
                                imageUrl = o && o.data;
                                image.value = imageUrl; //重置表单数据
                                imgpre.src = imageUrl;  //更新预览图片路径并显示
                                alert("文件上传成功");
							}else{
                            	alert('Error:'+o.message);
							}
                        } else {
                            alert('An error occurred!');
                        }
                    };
                    //发送请求
                    xhr.send(imgForm);
				}
			});
			form.addEventListener('submit',function(e){
				if(!isSubmiting && this.check()){
					price.value = Number(price.value);
					isSubmiting = true;
					form.submit();
				}
			}.bind(this),false);

			[title,summary,image,detail,price,quantity].forEach(function(item){
				item.addEventListener('input',function(e){
					item.classList.remove('z-err');
				}.bind(this),false);
			}.bind(this));
		},
		//检查表单内容合法性
		check:function(){
			var result = true;
			[
				[title,function(value){return value.length<2 || value.length>80}],
				[summary,function(value){return value.length<2 || value.length>140}],
				[image,function(value){return imageMode === "urlUpload" && value === ''}],
				[detail,function(value){return value.length<2 || value.length>1000}],
				[price,function(value){return value === '' || !Number(value)}],
				[quantity,function(value){return !(value==0) &&( value<0 || value>9999999 || !Number(value))}]
			].forEach(function(item){
				var value = item[0].value.trim();
				if(item[1](value)){
					item[0].classList.add('z-err');
					result = false;
				}
				item[0].value = value;
			});
			if(imageMode === "fileUpload" && !imageUrl){
				uploadInput.classList.add('z-err');
				result = false;
			}
			return result;
		}
	};
	page.init();
})(window,document);
