
//验证码
function createCode(length){
	var code = "";
	var arr = [0,1,2,3,4,5,6,7,8,9,
	'A','B','C','D','E','F','G','H','i','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

	for(var i = 0;i<length;i++){
		var index = Math.floor(Math.random()*35);
		code+=arr[index];
	}
	return code;
}
//时间戳--xxxx年 月 日
function stampToDate(stamp){
    var res = new Date(stamp);
    var output = res.toLocaleDateString().replace('/','年').replace('/','月') + "日";
    return output;
}
//系统消息转码：
//1-群组邀请；2-踢出群组；3-活动邀请；4-踢出活动；
//5-申请创建活动；6-申请创建群组；
//7-活动创建成功；8-群组创建成功；2-活动创建失败；2-群组创建失败
function messageDecode(sendId,type,state){
	var res = "";
	console.log(sendId+"|"+type+"|"+state);
	if(state=="read"){
		res="<a data-inline='true' disabled='true' data-role='button' data-mini='true' class='read-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' href='javascript:void(0);' role='button'>已阅</a>"
		+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
	}else{
		switch (type) {
		case "1":
			res="<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='accept-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' group-id='"+sendId+"'>接受</a>"
			+"<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='refuse-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' group-id='"+sendId+"'>拒绝</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "2":
			res="<a data-inline='true' data-role='button' data-mini='true' class='read-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' href='javascript:void(0);' role='button'>点击已阅</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "3":
			res="<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='accept-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' event-id='"+sendId+"'>接受</a>"
			+"<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='refuse-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' event-id='"+sendId+"'>拒绝</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "4":
			res="<a data-inline='true' data-role='button' data-mini='true' class='read-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' href='javascript:void(0);' role='button'>点击已阅</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "5":
			res="<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='agree-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' event-id='"+sendId+"'>同意</a>"
			+"<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='disagree-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' event-id='"+sendId+"'>拒绝</a>"
			+"<a href='event.html?ID="+sendId+"' target='_top' class='ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini'>查看活动信息</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "6":
			res="<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='agree-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' group-id='"+sendId+"'>同意</a>"
			+"<a href='javascript:void(0);' data-mini='true' data-role='button' data-inline='true' class='disagree-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' group-id='"+sendId+"'>拒绝</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "7":
			res="<a data-inline='true' data-role='button' data-mini='true' class='read-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' href='javascript:void(0);' role='button'>点击已阅</a>"
			+"<a href='event.html?ID="+sendId+"' target='_top' class='ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini'>查看活动信息</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		case "8":
			res="<a data-inline='true' data-role='button' data-mini='true' class='read-btn ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini' href='javascript:void(0);' role='button'>点击已阅</a>"
			+"<a href='group.html?ID="+sendId+"' target='_top' class='ui-link ui-btn ui-btn-inline ui-shadow ui-corner-all ui-mini'>查看群组信息</a>"
			+"<button class='del-btn ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all' data-iconpos='notext' data-corners='true' data-icon='delete' data-inline='true'></button>";
			break;
		default:
			res="error!"// statements_def
			break;
		}
	}
	
	var readbox = "<div class='read-box'>"+res+"</div>";
	return readbox;
}

//根据url拿到参数值
function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
}

//date:2016-4-20 14:53:22.0 
function dateDecode(date,option){
	var res;
	switch(option){
		case "all":
			res = date.substr(0,date.length-2);
			break;
		case "day":
			res = date.split(" ")[0];
			break;
		default:
			res = "option wrong!";
			break;
	}
	return res;
}