//iscroll
	var myscroll;
	var pullDownEl,pullDownOffset,pullUpEl;
	var cPage=0;

	function pullDownAction(){
		console.log("下拉刷新");
		//ajaxj 请求获取刷新
		/*$.ajax({
			url:'getevents.action',
			type:'get',
			data:{page:0},
			dataType:'json',
			success:function(result){

			},
			error:function(result){
				alert("connect error!");
			}
		});*/
		history.go(0);
	};

	function pullUpAction(){
		console.log("上拉发送请求");
		//ajax 请求下一页数据
		$.ajax({
			url:'getevents.action',
			type:'get',
			data:{page:cPage+1},
			dataType:'json',
			success:function(result){
				if(result.returncode=="0"){
					alert("已经是最后一页！");

				}else{
					cPage = result.page;

					for(var k in result.eventList){
						var event = result.eventList[k];
						var start = dateDecode(event.starttime,"day");
						var end = dateDecode(event.endtime,"day");
						var li = $('<li></li>');

				var cont = "<a data-ajax='false' href='javascript:void(0);' class='ui-btn ui-btn-icon-right ui-icon-carat-r' eventid='"+event.eventId+"'>查看详情&gt;&gt;</a><h1>"+event.eventName+"</h1><span class='event-info'>"+event.introduce+"</span><span class='event-date'>"+start+"-"+end+"</span>";
						
						li.html($.parseHTML(cont)).css('background-image','url(./img/poster/'+event.poster+')');

						$("#eventlist").append(li);
					};
				}
			},
			error:function(result){
				alert("connect error!");
			}
		});
		//myscroll.refresh();
	};

	function loaded(){
		pullDownEl = document.getElementById('pullDown');
        pullDownOffset = pullDownEl.offsetHeight;
        pullUpEl = document.getElementById('pullUp');
        pullUpOffset = pullUpEl.offsetHeight;

		myscroll = new iScroll(
			'wrapper',{
				useTransition : true,
				topOffset: pullDownOffset,
				onRefresh:function(){
					console.log("refresh");
                        if (pullDownEl.className.match('loading')) {
                                pullDownEl.className = '';
                                pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉获取上一页数据';
                        } else if (pullUpEl.className.match('loading')) {
                                pullUpEl.className = '';
                                pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载下一页数据';
                        }
				},
				onScrollMove:function(){
					//console.log(this.y);
					    if (this.y > 5 && !pullDownEl.className.match('flip')) {
                                pullDownEl.className = 'flip';
                                pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开来刷新活动';
                                this.minScrollY = 0;
                        } else if (this.y < 5 && pullDownEl.className.match('flip')) {
                                pullDownEl.className = '';
                                pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉获取上一页数据';
                                this.minScrollY = -pullDownOffset;
                        } else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                                pullUpEl.className = 'flip';
                                pullUpEl.querySelector('.pullUpLabel').innerHTML = '松开来加载下一页活动';
                                //this.maxScrollY = this.maxScrollY;
                        } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                                pullUpEl.className = '';
                                pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载下一页数据';
                                this.maxScrollY = pullUpOffset;
                        }
				},
				onScrollEnd:function(){
                        if (pullDownEl.className.match('flip')) {
                                pullDownEl.className = 'loading';
                                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';                               
                                        
                                pullDownAction();
                                setTimeout('myscroll.refresh()',1000);

                        } else if (pullUpEl.className.match('flip')) {
                                pullUpEl.className = 'loading';
                                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';                       
                                pullUpAction();
                              	 setTimeout('myscroll.refresh()',1000);
                        }
				}
			})
	}

	/*document.addEventListener('touchmove', function(e) {
        e.preventDefault();
    }, false);*/
 
    document.addEventListener('DOMContentLoaded', function() {
        setTimeout(loaded, 200);
    }, false);
