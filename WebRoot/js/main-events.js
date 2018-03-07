$("#main").on('pageshow',function(){
			//toorbar hide&show
			var barWidth = "-"+$('#toolbar').width()+"px";
			$('#toolbar').css("left",barWidth);

			$('#main').on('tap','#toolbar-btn',function(){
				if($('#toolbar').css('left')<=barWidth){
	    			$('#toolbar').css('left','0px');
	    		}else{
	    			$('#toolbar').css('left',barWidth);
	    		}
			});
			$('#main').on('swiperight',function(){
				if($('#toolbar').css('left')<=barWidth){
	    			$('#toolbar').css('left','0px');
	    		}
			});
			$('#main').on('swipeleft',function(){
				if($('#toolbar').css('left')=="0px"){
	    			$('#toolbar').css('left',barWidth);
	    		}
			});	


			//获取权限，拿到对应数据
		$.ajax({
			url:'getevents.action',
			type:'get',
			data:{page:0},
			dataType:'json',
			success:function(result){
				console.log(result);
				if(result.authority=="0"){
					//游客权限
					$('#toolbar-btn').hide();
					$('#toolbar').hide();
					$('#footer').hide();

				}else{
					//用户权限
					//管理员区分显示
					if(result.authority=="1"){
						$('.bar-content ul li:eq(0) a').text("管理所有活动");
						$('.bar-content ul li:eq(1) a').text("管理所有群组");
					}
					else{
						$('.bar-content ul li:eq(0) a').text("我参与的活动");
						$('.bar-content ul li:eq(1) a').text("我参与的群组");
					}
					//toolbar
						$('#toolbar .username span').text(result.user.username);
						$('#toolbar #user-head img').attr('alt',result.user.userid).attr('src','./img/avator/'+result.user.headport)
						.parent().attr('href','user-display.html?ID='+result.user.userid);

					
					//logout
					$('#log-group').html('<ul><li><a class=ui-link href="javascript:void(0);" id="logout-btn">注销</a></li></ul>').css('bottom','-34px');

					//system message
					if(result.inform!="0"){
			    		$('.inform-num').css('background-color','#fff56b');
			    	}
			    	$('.inform-num').text(result.inform);
					
				}
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


			},
			error:function(XMLHttpRequest, textStatus){
				//console.log(XMLHttpRequest);
				alert('connect error!');
			}
		});

		//logout
    	$('#main').on('tap','#logout-btn',function(){

    		if(confirm("确定登出么？")){
    			$.ajax({
    			url:'logout.action',
    			type:'get',
    			data:{},
    			dataType:'json',
    			success:function(result){
    				alert('登出');
    				location.href="main.html";
    			},
    			error:function(result){
    				alert('connect error!');
    			}

    		})
    		};
    		
    	});

    	//event-》event.html
    	$('#main').on('tap','#eventlist li a',function(){
    		var id = $(this).attr('eventid');
    		location.href = "event.html?ID="+id;
    	});

    	
	
});