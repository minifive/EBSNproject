$(document).ready(function(){

		//toggle log-button
        $('#main').on('tap',function(e){
        var target = $(e.target);
            if(target.attr('id')=="btn-group"){
                $('#log-group').fadeIn(300);
            }else{
                $('#log-group').fadeOut(300);
            }
        });

    	//login
    	$('#login-btn').on('tap',function(){
    		var data = {
                ID:$('#loginID').val(),
                password:$.md5($('#loginPW').val())
            }
            console.log(data);
            if(data.ID==""||data.password==""){
                alert("信息不全！请检查");
            }else{
                $.ajax({
                    url:'login.action',
                    type:'post',
                    data:data,
                    dataType:'json',
                    success:function(result){
                        if(result.returncode=="error"){
                            alert("用户名或密码有误，请重新输入");
                            return false;
                        }else{
                            alert("登陆成功！正在跳转至主页");
                            location.href="main.html";
                        }
                        
                    },
                    error:function(result){
                        alert("connect error!");
                    }
                })
            }
    		//form submit
    	})

    	//register
    	
    	$('#register-btn').on('tap',function(){
    		var uItem = {
    			ID:$('#ID').val(),
    			name:$('#username').val(),
    			password:$.md5($('#password').val())
    		}

    		if(uItem.ID==""||uItem.name==""||uItem.password==""){
    			alert('信息不全！请检查');
    		}else{
    			if($('#password').val() == $('#re-password').val()){
	    			if($('#register-code').val()!=$('#code-box-r').text()){
	    				alert("验证码错误，请重新输入");
	    				$('.code-box').text(createCode(4));
	    			}else{
	    				console.log(uItem);
	    				//ajax
	    				$.ajax({
	    					url:'regist.action',
	    					type:'get',
	    					dataType:'json',
	    					data:uItem,
	    					success:function(result){
	    						if(result.returnCode == '1'){
	    							alert('该学号已被使用，请重新注册');
	    							location.href = 'main.html#register';
	    						}else{
	    							alert('注册成功！跳转到登录页~~');
	    							location.href = 'main.html#login';
	    						}
	    						
	    					},
	    					error:function(result){
	    						alert(" connect error!");
	    					}
	    				});
	    				
	    			}
	    		}else{
	    			alert('密码不一致！')
	    		}
    		}
    		
    	})

    	//error-tips
    	//学号限制 8位数字
    	$('#ID').bind('input propertychange',function(){
    		var val = $(this).val();
    		if(val.length!=8){
    			$('#id-tips').text('长度不为8位请修改').css('color','red');
    		}
    		else if(!/^\d+$/.test(val)){
    			$('#id-tips').text('不允许出现字母或其它符号').css('color','red');
    		}
    		else{
    			$('#id-tips').text('格式正确').css('color','green');
    		}
    	});
    	//密码最少六位
    	$('#password').bind('input propertychange',function(){
    		if($('#password').val().length<6){
    			$('#pw-tips').text('密码长度不足6位！').css('color','red');
    		}else{
    			$('#pw-tips').text('密码格式正确').css('color','green');
    		}
    	});
    	//重复密码判断
    	$('#re-password').bind('input propertychange',function(){
    		if($('#password').val()==$(this).val()){
    			$('#re-pw-tips').text('密码一致！').css('color','green');
    		}else{
    			$('#re-pw-tips').text('密码不一致！').css('color','red');
    		}
    	})
    	

    	

    	//toolbar user-head
    	$('#user-head').on('tap',function(){
    		var ID = $(this).find('img').attr('alt');
    		/*$.mobile.changePage("user-display.html",{
				type:"get",
				data:{ID:ID}//拿到ID，传到user-display
			});*/
    		//location.href = "user-display.html?ID="+ID;
    	});
    	//identifying code
    	$('.code-box').text(createCode(4));
    	$('.code-box').on('tap',function(event){	//tap-refresh code
    		event.preventDefault();
    		$(this).text(createCode(4));
    	});

    	//系统信息


	});

//------------------------------------------------------
