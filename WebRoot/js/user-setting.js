$(document).ready(function(){

		//image preview
		$(document).on('change','#userHead',function(){
	    	console.log($(this).val());
	    	//hide other images
	    	$(this).parent().find('.preview-img').remove();
	    	var nImg = $("<img class=preview-img />"); 	
	    	var nurl = getUrl(this.files[0]);
	    	nImg.attr('src',nurl);
	    	nImg.insertAfter('#userHead');
	    	$('#upload-btn').show();
	    });
		//image upload
		$('#upload-btn').on('tap',function(event){
			event.preventDefault();
			var _this = $(this);
			//ajaxFileUpload -> success
			$.ajaxFileUpload({
		        url: "uploadimg.action",
		        secureuri : false,  
		        fileElementId : 'userHead',// 上传控件的id 
		        dataType : 'json',
		        data: {},//uid可为空，后台根据session获取
		        complete:function(){},
		        success:function (data) { 
		        			alert('上传成功!');
							//init
							$('#head-box').find('.head-img,.preview-img').remove();
							//display
							var nImg = $("<img class=head-img />");
							var nurl = './img/avator/'+data.headimg;	
							nImg.attr('src',nurl).attr('image',data.headimg);
					    	nImg.insertAfter(_this);

					    	$('#upload-btn').hide();
		        },
		        error: function (data) {
		    		alert('connect error!!');
		        }
    		});
		
		});

		$('#password').bind('input propertychange',function(){
    		if($('#password').val().length<6){
    			$('#pw-tips').text('密码长度不足6位').css('color','red');
    		}else{
    			$('#pw-tips').text('密码格式正确').css('color','green');
    		}
    	});

		$('#re-password').bind('input propertychange',function(){
    		if($('#password').val()==$(this).val()){
    			$('#re-pw-tips').text('密码一致！').css('color','green');
    		}else{
    			$('#re-pw-tips').text('密码不一致！').css('color','red');
    		}
    	});


	    //confirm
	    $('#submit-btn').on('tap',function(){

	    	var uInfo = {
	    		name:$('#userName').val(),
	    		opassword:$.md5($('#o-password').val()),
	    		npassword:$.md5($('#password').val()),
	    		phone:$('#phone').val(),
	    		image:$('.head-img').attr('image')
	    	};
	    	console.log(uInfo);
	    	if(uInfo.name==""){
	    		alert('昵称不能为空！');
	    	}else if(uInfo.npassword==""||uInfo.opassword==""||$('#re-password')==""){
	    		alert('密码不能为空！');
	    	}else if(uInfo.phone==""){
	    		alert('请输入联系方式');
	    	}else{
	    		//alert('更改设置');
	    		
	    		$.ajax({
	    			url:'updateimf.action',
	    			type:'post',
	    			data:uInfo,
	    			dataType:'json',
	    			success:function(result){
	    				if(result.returncode=="1"){
	    					alert("登录失效，跳转首页");
	    					location.href="main.html";
	    				}else if(result.returncode=="2"){
	    					alert("旧密码错误，请重新输入");
	    				}else{
	    					alert("信息设置成功！");
	    					location.href="main.html";

	    				}
	    			},
	    			error:function(result){
	    				alert("connect error!");
	    			}
	    		});
	    	}

	    })

});


//--------------------------------------------------------------------------------------------------------
//拿到上传文件的本地路径
function getUrl(file){
    		var url = null;
    		if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url;
}