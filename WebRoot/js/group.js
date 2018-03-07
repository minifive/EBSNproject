$('#main').on('pageinit',function(){
			var ID = getUrlParam("ID");
			if(ID==""){
				alert("参数错误！");
				history.go(-1);
			}else{
				$.ajax({
					url:'getgroupimf.action',
					type:'get',
					data:{ID:ID},
					dataType:'json',
					success:function(result){
						if(result.returncode=="0"){
							alert("查无此群组");
							history.go(-1);
						}else if(result.returncode=="1"){
							alert("登录失效，跳转首页");
							location.href="main.html";
						}
						else{

							//display group's info
							var group = result.group;
							$('#group-name').text(group.groupname);
							$('#builder').text(result.builder.username);
							$('#exist').text(group.extnum);
							$('#all').text(group.maxnum);
							
							//display events
							for(var k in result.eventList){
								console.log(result.eventList[k]);
								var event = result.eventList[k];
								var start = dateDecode(event.starttime,"day");
								var end = dateDecode(event.endtime,"day");

								$('#eventlist li:eq('+k+')').show()
								.css('background-image','url(./img/poster/'+event.poster+')')
								.find('h1').text(event.eventName)
								.parent().find('.event-info').text(event.introduce)
								.parent().find('.event-date').text(start+"-"+end)
								.parent().find('a').attr('eventid',event.eventId);
							};

							//display builder
							$('#memberlist li .builder .name').text("发起人："+result.builder.username);
							$('#memberlist li .builder .tel').text(result.builder.phone);
							$('#memberlist li .builder').attr("user-id",result.builder.userid);

							//display members list
							var memberList = result.userList;
							$('#memberlist .member').parent().remove();
							for(var k in memberList){
								var obj = $('<li></li>');
								obj.html($.parseHTML("<a href='javascript:void(0);' class='member ui-btn ui-btn-icon-right ui-icon-carat-r' user-id='"+memberList[k].userid+"'><h1 class='name'>"+memberList[k].username+"</h1><h4 class='tel'>联系方式："+memberList[k].phone+"</h4></a><button data-role='none' class='kick-btn'>踢出</button>"));

								$('#memberlist').append(obj);
								
							}
							//若为发起人可进行踢出成员操作,但无法退出
							if(result.authority=="1"){
								$('.kick-btn').show();
								$('#footer ul').removeClass().addClass('ui-grid-solo')
								.html($.parseHTML("<li class='ui-block-a'><a data-icon='plus' href='#invite' class='ui-link ui-btn ui-icon-plus ui-btn-icon-top'>邀请用户</a></li>"));
								$('#del-btn').css('display','inline-block');
							}else{
								//alert('normal user');
								$('.kick-btn').hide();
							}

						}
					},
					error:function(reuslt){
						alert("connect error!");
					}
				});

				//event ->event.html
				$('#main').on('tap','#eventlist li a',function(){
		    		var id = $(this).attr('eventid');
		    		location.href = "event.html?ID="+id;
		    	});

				//user-display
				$(document).on('tap','#memberlist li a',function(){
					var userId = $(this).attr('user-id');
					location.href = "user-display.html?ID="+userId;
				});

				//kick-btn
				$(document).on('tap','.kick-btn',function(){
					var userId = $(this).parent().find('a').attr('user-id');
					var userName = $(this).parent().find('a .name').text();
					//var eventId = 1;
					console.log(userId+"|"+ID+"|"+userName);
					if (confirm("确定踢出吗？")){  
						$.ajax({
							url:'delgumapo.action',
							type:'get',
							data:{userid:userId,groupid:ID},
							dataType:'json',
							success:function(result){
								if(result.returncode=="1"){
									alert("登录失效，跳转首页");
									location.href="main.html";
								}
								alert("已将"+userName+"踢出");
								location.href = "group.html?ID="+ID;
							},
							error:function(result){
								alert("connect error!");
							}
						})
			        }  
				});

				//exit-btn
				$(document).on('tap','#exit-btn',function(){
					if(confirm("确定退出群组么？")){
						$.ajax({
							url:'delgumap.action',
							type:'get',
							data:{ID:ID},
							dataType:'json',
							success:function(result){
								if(result.returncode=="1"){
									alert("登录失效，跳转首页");
									location.href="main.html";
								}else{
									alert("已退出群组");
									location.href="main.html";
								}
							},
							error:function(result){
								alert("connect error!");
							}
						});
					}
				});

				//del-btn
				$(document).on('tap','#del-btn',function(){
					if(confirm("确定删除群组么？")){
						$.ajax({
							url:'deletegroup.action',
							type:'get',
							data:{msgid:"0",groupid:ID},
							dataType:'json',
							success:function(result){
								if(result.returncode=="1"){
									alert("登录失效，跳转首页");
									location.href="main.html";
								}else{
									alert("已删除群组,正在返回主页");
									location.href="main.html";
								}
							},
							error:function(result){
								alert("connect error!");
							}
						});
					}
				})
			}

		})