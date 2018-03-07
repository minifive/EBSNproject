$('#inform').on('pageinit',function(){
	
		$.ajax({
			url:'getsysmsg.action',
			type:'get',
			data:{},
			dataType:'json',
			success:function(result){
				if(result.returncode=="1"){
    				alert("登录失效");
				}else{
					if(result.msgList.length==0){
						//alert(111);
						var tips = $("<h1>当前没有系统信息</h1>");
						$('#inform-list').remove("h1").append(tips);

					}else{
						for(var k in result.msgList){
						var msg = result.msgList[k];

						var obj = $("<li class='ui-li-static ui-body-inherit'></li>");
						if(k==result.msgList.length-1){
							obj = $("<li class='ui-li-static ui-body-inherit ui-last-child'></li>");
						}
						if(k==0){
							obj = $("<li class='ui-li-static ui-body-inherit ui-first-child'></li>");
						}
						//HTML结构
						var serial = parseInt(k)+1;
						var cont = "<span class='inform-serial'>"+serial+"</span><p class='inform-content'>"+msg.content+"</p><span class='inform-date'>"+dateDecode(msg.sendtime,"all")+"</span>";

			    		//处理信息框readbox
			    		var readBox = messageDecode(msg.sendid,msg.type,msg.state);
			    		//cont整合,添加属性msgid
			    		cont+=readBox;
			    		obj.html($.parseHTML(cont)).attr('msg-id',msg.msgid);

			    		$('#inform-list').append(obj);
			    		
			    	}
					}
					
				}

			},
			error:function(reslut){
				//console.log(result);
				alert("connect error!");
			}
		})

		//已阅交互
		$('#inform').on('tap','.read-btn',function(){
			var _this = $(this);
			var msgid = _this.parent().parent().attr("msg-id");
			//console.log(msgid);
			$.ajax({
				url:'setmsgstate.action',
				type:'get',
				data:{msgid:msgid},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert("登录失效，跳转首页");
						location.href="main.html";
					}else{
						_this.attr('disabled','true').text("已阅").next().find('a').hide();						
					}
				},
				error:function(result){
					alert("connect error!");
				}
			})
		});
		//删除交互
		$('#inform').on('tap','.del-btn',function(){
			var _this = $(this);
			var msgid = $(this).parent().parent().attr('msg-id');
			if(confirm("确定删除么?")){
			$.ajax({
				url:'delsysmsg.action',
				type:'get',
				data:{msgid:msgid},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert("登录失效，跳转首页");
						location.href="main.html";
					}else if(result.returncode=="0"){
						alert("网络延迟，操作错误");
					}else{
						alert("删除成功");
						//_this.parent().parent().remove();
						history.go(0);
					}
				}
			})				
			}

		});

		//接受交互
		$('#inform').on('tap','.accept-btn',function(){
			var msgid = $(this).parent().parent().attr('msg-id');
			var groupid = $(this).attr('group-id');
			var eventid = $(this).attr('event-id');

			if(confirm("确定接受么?")){
			//判断group or event
			if(groupid){
				$.ajax({
					url:'acceptgroup.action',
					type:'get',
					data:{msgid:msgid,groupid:groupid},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}else{
							alert("已加入群组,可通过“参加的群组”来查看");
							history.go(0);
						}
					},
					error:function(result){
						alert("connect error!");
					}

				});
			}
			if(eventid){
					$.ajax({
					url:'acceptevent.action',
					type:'get',
					data:{msgid:msgid,eventid:eventid},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}else{
							alert("已加入活动,可通过“参加的活动”来查看");
							history.go(0);
						}
					},
					error:function(result){
						alert("connect error!");
					}

				});
			}	
		}
			
			
		});
		
		//拒绝
		$('#inform').on('tap','.refuse-btn',function(){
			var msgid = $(this).parent().parent().attr('msg-id');
			if(confirm("确定拒绝么?")){
				$.ajax({
					url:'setmsgstate.action',
					type:'get',
					data:{msgid:msgid},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}else{
							history.go(0);							
						}
					},
					error:function(result){
						alert("connect error!");
					}
				})
			}
		});

		//administrator
		//同意申请
		$('#inform').on('tap','.agree-btn',function(){
			var msgid = $(this).parent().parent().attr('msg-id');
			//var userid = $(this).attr('user-id');
			var groupid = $(this).attr('group-id');
			var eventid = $(this).attr('event-id');

			if(confirm("确定同意么?")){
			//判断group or event
			if(groupid){
				$.ajax({
					url:'agreegroup.action',
					type:'get',
					data:{msgid:msgid,groupid:groupid},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}else{
							alert("该群组已通过审核，可以加入");
							history.go(0);
						}
					},
					error:function(result){
						alert("connect error!");
					}

				});
			}
			if(eventid){
					$.ajax({
					url:'agreeevent.action',
					type:'get',
					data:{msgid:msgid,eventid:eventid},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}else{
							alert("该活动已通过审核，可以报名");
							history.go(0);
						}
					},
					error:function(result){
						alert("connect error!");
					}

				});
			}
			}
		});

		//不允许申请
		$('#inform').on('tap','.disagree-btn',function(){
			var msgid = $(this).parent().parent().attr('msg-id');
			var groupid = $(this).attr('group-id');
			var eventid = $(this).attr('event-id');
			
			if(confirm("确定不允许么?")){
				if(groupid){
					$.ajax({
						url:'deletegroup.action',
						type:'get',
						data:{msgid:msgid,groupid:groupid},
						dataType:'json',
						success:function(result){
							if(result.returncode=="1"){
								alert("登录失效，跳转首页");
								location.href="main.html";
							}else{
								alert("群组已删除");
								history.go(0);							
							}
						},
						error:function(result){
							alert("connect error!");
						}
					});
				}
				if(eventid){
					$.ajax({
						url:'deleteevent.action',
						type:'get',
						data:{msgid:msgid,eventid:eventid},
						dataType:'json',
						success:function(result){
							if(result.returncode=="1"){
								alert("登录失效，跳转首页");
								location.href="main.html";
							}else{
								alert("活动已删除");
								history.go(0);							
							}
						},
						error:function(result){
							alert("connect error!");
						}
					});
				}

			}
		});
	})