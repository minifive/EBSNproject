$("#main").on("pageshow",function(){
		//get eventID
		var ID = getUrlParam("ID");
		console.log(ID);
		if(ID!=""){
			$.ajax({
				url:'geteventimf.action',
				type:'get',
				data:{ID:ID},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert("查无此活动！");
						history.go(-1);
					}
					else{
						var event = result.event;
						$('#event-name').text(event.eventName);
						$('#start-date').text(dateDecode(event.starttime,"all"));
						$('#end-date').text(dateDecode(event.endtime,"all"));
						$('#event-info').text(event.introduce);
						$('#attend-num').text(event.exstnum);
						$('#all-num').text(event.maxnum);
						$('#img-box img').attr('src',"./img/poster/"+event.poster).attr('alt',event.poster);
						$('#members-btn').attr('event-id',event.eventId);

						//map setting
						var map = new AMap.Map('location',{
				            resizeEnable: true,
				            zoom: 16,
				            center: event.coordinate.split(",")
				    	});

				    	//根据坐标，显示地点
					 	 AMap.service('AMap.Geocoder',function(){//回调函数
					        //实例化Geocoder
					        geocoder = new AMap.Geocoder({
					            city: "南京市"//城市
					        });
					        //marker定位对象
					        var marker = new AMap.Marker({
					            map:map,
					            bubble:false
					        });
					        //点击显示具体地址
					        marker.on('click',function(e){
					          geocoder.getAddress(e.lnglat, function(status, result){
					            if (status === 'complete' && result.info === 'OK') {
					               //获得了有效的地址信息:
					               console.log(result);
					               $('#locate-point').text(event.address);//result无法拿到详细地址，对应坐标需要另存一个文字地址
					            }else{
					               //获取地址失败
					               alert('error!');
					            }
					        });
					            
					  	});
						});

					 	//comments
					 	var comments = result.eventcomments;
					 	console.log(comments);
					 	$(".comments li").hide();
					 	for(var k in comments){
					 		var li = $("<li class='ui-li-static ui-body-inherit'></li>")

					 		var cont = "<div class='user' user-id='"+comments[k].userid+"'><img alt='user-head' src='./img/avator/"+comments[k].headport+"'><span>"+comments[k].username+"</span></div><div class='text'><p>"+comments[k].content+"</p><span>评论于<i class='comment-date'>"+dateDecode(comments[k].publishtime,"all")+"</i></span></div>";

							li.html($.parseHTML(cont));
							$(".comments").append(li);

					 	}

					 	console.log("权限:"+result.signup);
						//判断权限:1->游客 2->未报名 3->已报名 4->创建者 5->管理员
						var footCont;
						switch(result.signup){
							case "1":
								$('#footer').hide();
								$('#members-btn').hide();
								break;
							case "2":
								footCont = "<ul class=ui-grid-solo><li class=ui-block-a><a href='javascript:void(0);' id='join-btn' class='ui-link ui-btn ui-icon-heart ui-btn-icon-top' data-icon='heart' data-iconpos='bottom' event-id='"+event.eventId+"'>报名</a></li></ul>";
								$('#members-btn').hide();
								break;
							case "3":
								footCont = "<ul class=ui-grid-a><li class=ui-block-a><a href='javascript:void(0);' id='exit-btn' class='ui-link ui-btn ui-icon-alert ui-btn-icon-top' data-icon='alert' data-iconpos='bottom' event-id='"+event.eventId+"'>退出</a></li><li class=ui-block-b><a href='#edit' id='edit-btn' class='ui-link ui-btn ui-icon-comment ui-btn-icon-top' data-icon='comment' data-iconpos='bottom' event-id='"+event.eventId+"'>发表评论</a></li></ul>";
								$('#members-btn').show();
								break;
							case "4":
								footCont = "<ul class=ui-grid-solo><li class=ui-block-a><a href='#edit' id='edit-btn' class='ui-link ui-btn ui-icon-comment ui-btn-icon-top' data-icon='comment' data-iconpos='bottom' event-id='"+event.eventId+"'>发表评论</a></li></ul>";
								$('#members-btn').show().text("管理成员");
								$('#del-btn').css("display","inline-block");
								break;
							case "5":
							alert("管理员！");
								$('#footer').hide();
								$('#members-btn').hide();
								$('#del-btn').css("display","inline-block");
								break;
							default:
								alert("权限错误！");
								history.go(-1);
						}
						$('#footer div').html($.parseHTML(footCont));
					}



				},
				error:function(result){
					alert("connect error!");
				}
			});

		}else{
			alert("无活动信息！");
		}

		//members-display
		$('#members-btn').on('tap',function(){
			var id = $(this).attr('event-id');
			location.href = "event-members.html?ID="+id;
		});

		//user head -> user display
		$('#main').on('tap','.user',function(){

			var ID = $(this).attr('user-id');
			location.href = "user-display.html?ID="+ID;
			
		})

		//delete event
		$('#main').on('tap','#del-btn',function(){
			if(confirm("确定删除此活动么？")){
				$.ajax({
					url:'deleteevent.action',
					type:'get',
					data:{msgid:"0",eventid:ID},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert('登录失效，正在返回主页');
							location.href="main.html";
						}else{
							alert("删除成功，正在返回主页");
							location.href="main.html";
						}
					},
					error:function(result){
						alert("connect error!");
					}
				})
			}
		});
		//join
		$('#main').on('tap','#join-btn',function(){

			if (confirm("确定报名么？")){
				var ID = $(this).attr('event-id');
			$.ajax({
				url:'addeumap.action',
				type:'get',
				data:{ID:ID},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert('登录失效,正在返回主页');
						location.href="main.html";
					}else if(result.returncode=="2"){
						alert('当前人数已满');
					}else{
						alert('报名成功');
						location.href="event.html?ID="+ID;
					}
					
				},
				error:function(result){
					alert("connect error!");
				}
			});
			};		
			
		});

		//exit
		$('#main').on('tap','#exit-btn',function(){
			if(confirm("确定退出么？")){
			var ID = $(this).attr('event-id');
			$.ajax({
				url:'deleumap.action',
				type:'get',
				data:{ID:ID},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert("登陆失效，正在返回主页");
						location.href="main.html";
					}else{
						alert('已退出活动');
						location.href="event.html?ID="+ID;
					}
					
				},
				error:function(result){
					alert("connect error!");
				}
			});	
			};

			
		});
	    

});