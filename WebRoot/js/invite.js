
		$('#invite').on('pageshow',function(){
			var ID = getUrlParam("ID");

			//display group list
			$.ajax({
				url:"getusergroups.action",
				type:'get',
				data:{},
				dataType:'json',
				success:function(result){
					if(result.returncode=="1"){
						alert("登录失效，跳转首页");
                        location.href="main.html";
					}else if(result.returncode=="2"){
						var tips = "<h3>您当前没有加入任何群组</h3>";
						$('.groups-info').html(tips);
						$('.members-info').hide();
					}else{

						var cont = "";
						for(var k in result.group){
							var group = result.group[k];
							var option = "<option value='"+group.groupid+"'>"+
							group.groupname+"</option>";

							cont+=option;
						}
						$('#group-list').html(cont);

					}
				},
				error:function(result){
					alert("connect error!");
				}
			});

			//选择列表项，显示群组成员
			$('#invite').on('change','#group-list',function(){
				var ID = $(this).find('option:selected').val();
				console.log(ID);
				$.ajax({
					url:'groupusers.action',
					type:'get',
					data:{groupid:ID},
					dataType:'json',
					success:function(result){
						if(result.returncode=="1"){
							alert("登录失效，跳转首页");
                        	location.href="main.html";
						}else{

							var cont ="";
							for(var k in result.users){
								var user = result.users[k];
								var li = "<li class='ui-child'><a data-role='none' class='member ui-btn ui-btn-icon-right ui-icon-carat-r' href='javascript:void(0);'><h1 class=name>"+user.username+"</h1><h4 class='tel'>"+user.userid+"</h4></a></li>";
								cont+=li;
							}
							$('.members-info ul').html(cont);
						}
					},
					error:function(result){
						alert("connect error!");
					}

				});
			})

			$('#invite').on('tap','.member',function(){
				var id = $(this).find('h4').text();
				if(confirm("确定邀请"+id+"么？")){
					$.ajax({
					url:'ivteventmsg.action',
						type:'get',
						data:{eventid:ID,inviteid:id},
						dataType:'json',
						success:function(result){
							if(result.returncode=="0"){
								alert("查无此用户，请重新输入");
							}else if(result.returncode=="1"){
								alert("登录失效，跳转首页");
								location.href="main.html";
							}else if(result.returncode=="2"){
								alert("该用户已在活动中");
							}else if(result.returncode=="3"){
								alert("人数已满，无法邀请");
							}else{
								alert("已发送邀请信息");

							}
						},
						error:function(result){
							alert("connect error!");
						}
					})
				}
				
			});

			$('#invite-btn').on('tap',function(){
				var id = $('#invite-id').val();
				console.log(ID+"|"+id);
				if(id==""){
					alert("请输入邀请用户学号或教职工号");
				}else{

					$.ajax({
						url:'ivteventmsg.action',
						type:'get',
						data:{eventid:ID,inviteid:id},
						dataType:'json',
						success:function(result){
							if(result.returncode=="0"){
								alert("查无此用户，请重新输入");
							}else if(result.returncode=="1"){
								alert("登录失效，跳转首页");
								location.href="main.html";
							}else if(result.returncode=="2"){
								alert("该用户已在活动中");
							}else if(result.returncode=="3"){
								alert("人数已满，无法邀请");
							}else{
								alert("已发送邀请信息");

							}
						},
						error:function(result){
							alert("connect error!");
						}
					})
				}

			})

		});
