$(document).on('pageinit',function(){

    //group-limit
    $('#group-limit').change(function(){
        if($(this).val()=="true"){
            $('#group-box').css('display','inline-block');

            //get group info
            $.ajax({
                url:'getusergroups.action',
                type:'get',
                data:{},
                dataType:'json',
                success:function(result){
                    if(result.returncode=="1"){
                        alert("登录失效，跳转首页");
                        location.href="main.html";
                    }else if(result.returncode=="2"){
                        alert("没有加入群组！");
                    }else{
                        var groupList = result.group;
                        for(var k in groupList){
                            var obj = $('<option></option>');
                            obj.val(groupList[k].groupid).text(groupList[k].groupname).attr('num',groupList[k].groupnum);
                            
                            $('#group').append(obj);
                        }
                        //默认选择第一项
                        $("#group").val($("#group option:eq(0)").val());
                        $('#person-num').attr('max',groupList[0].groupnum);
                        
                    }
                },
                error:function(result){
                    alert("connect error!");
                }
            })

        }else{
            $('#group-box').css('display','none');
            $('#person-num').removeAttr('max');
            $('#group').empty();
        }
    });

    //select group 限制参与人数<group 人数
    $(document).on('tap','#group option',function(){
        var num = $(this).attr('num');
        $('#person-num').attr('max',num);
    })

    //person-limit
    $('#person-limit').change(function(){
        if($(this).val()=="true"){
            $('#num-box').css('display','inline-block');
        }else{
             $('#num-box').css('display','none');
        }
    });

    //image preview
  $(document).on('change','#pic',function(){
        console.log($(this).val());
        //hide other images
        $(this).parent().find('img').remove();
        var nImg = $("<img class=preview-img />");  
        var nurl = getUrl(this.files[0]);
        nImg.attr('src',nurl);
        nImg .insertAfter('#pic');
        $('#upload-btn').show();
    });

    //upload image
    $('#upload-btn').on('tap',function(event){
        event.preventDefault();

        var _this = $(this);
        $.ajaxFileUpload({
                url: "uploadposter",
                secureuri : false,  
                fileElementId : 'pic',// 上传控件的id 
                dataType : 'json',
                data: {"requesttype" : "upload"},//uid可为空，后台根据session获取
                complete:function(){},
                success: function (data,status)
                {
                    alert('上传成功！'+data.imgname);
                    console.log(_this);
                    _this.parent().find('.head-img,.preview-img').remove();

                    //display
                    var nImg = $("<img class=head-img />");
                    var nurl = "./img/poster/"+data.imgname;  
                    nImg.attr('src',nurl).attr('image',data.imgname);
                    //alert(111);
                    nImg .insertAfter(_this);
                    $('#upload-btn').hide();
                },
                error: function (data) {
                    alert('connect error!!');
                }
            });
        //init
    });
    
    //info字数限制
    $('#info').bind('input propertychange',function(){
            var all = 200;
            var left = all - $(this).val().length;
            $('#words-limit').text(left);
    });

    //submit result
    $('#complete-btn').on('tap',function(event){
        event.preventDefault();
        var eItem = {
            name:$('#eventname').val(),
            sDate:$('#startDate').val(),
            eDate:$('#endDate').val(),
            //地点名称
            location:$('#locate-list option:selected').text(),
            //地点坐标
            coord:$('#locate-list option:selected').val(),
            gLimit:$('#group-limit').val(),
            group:$('#group').val(),
            pLimit:$('#person-limit').val(),
            num:$('#person-num').val(),
            info:$('#info').val(),
            image:$('.head-img').attr('image')
        }
        console.log(eItem);

        if(eItem.name==""){
            alert('请输入活动主题');
        }
        else if(eItem.sDate==""||eItem.eDate==""){
            alert('请输入时间');
        }
        else if(eItem.location==""||eItem.coord==""){
            alert('请选择地点');
        }
        else if(eItem.pLimit=="true"&&eItem.num==""){
            alert('请输入限制人数');
        }else if(eItem.num>$('#person-num').attr('max')){
            alert('限制人数超过群组人数');
        }else if(eItem.info==""){
            alert('请输入活动介绍');
        }
        else if(!eItem.image){
            alert('请上传海报！');
        }
        else{
            //alert('create succeess!');

            $.ajax({
            url:'createvent.action',
            method:'post',
            data:eItem,
            dataType:'json',
            success:function(result){
                if(result.returncode=="1"){
                    alert("登录失效，跳转首页");
                    location.href="main.html";
                }else{
                    alert('发布成功！请等待系统管理员审核，审核结果会通过系统消息告知');
                    location.href = "main.html";
                }
                
            },
            error:function(result){
                alert('connect error!');
            }
        });
        }

    });


    //map setting
    var map = new AMap.Map('container',{
            resizeEnable: false,
            zoom: 15,
            center: [118.82099,31.887319]//默认 东南大学
    });
    //search & display location
    $('#location').bind('input propertychange',function(){
        var keyword = '东南大学' + $(this).val();
        AMap.service('AMap.PlaceSearch',function(){//回调函数
            //实例化PlaceSearch
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
            pageSize: 5,
            pageIndex: 1,
            city: "南京市"     //方法默认全国

            });
            //关键字查询
            placeSearch.search(keyword, function(status, result) {
               //TODO:开发者使用result自己创建交互面板和地图展示
               console.log("搜索："+keyword);
                if(status!="no_data"){
                   var list = result.poiList.pois;
                if(list.length==0){
                    $('#locate-list').hide();
                    $('#error-tips').show();
                    }else{
                        $('#error-tips').hide();
                        for(var i = 0;i<list.length;i++){
                            var location = {
                                name:list[i].name,
                                coord:[list[i].location.lng,list[i].location.lat]
                            }
                            console.log(location);
                            $('#locate-list').css('display','block');
                            $('#locate-list option:eq('+i+')').text(location.name)
                            .attr('value',location.coord).show();

                        }
                        //默认显示第一个option定位
                        var coord = $('#locate-list option:eq(0)').val().split(",");
                        var map = new AMap.Map('container',{
                            resizeEnable: false,
                            zoom: 18,
                            center:coord
                        });
                        //marker定位中点
                        var marker = new AMap.Marker({
                            map:map,
                            bubble:false
                        });

                    } 
                }
                else{
                    $('#locate-list').hide();
                    $('#error-tips').show().text('格式错误，请重新输入');
                }
  
            });
        });
    });
    
    //选择地点 map上显示
    $(document).on('change','#locate-list',function(){

        var coord = $(this).find('option:selected').val().split(",");
        //console.log(coord);
        var map = new AMap.Map('container',{
            resizeEnable: false,
            zoom: 18,
            center:coord
        });
        //marker定位中点
        var marker = new AMap.Marker({
            map:map,
            bubble:false
        });
    });

    

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