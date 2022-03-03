var index = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click',function(){
            _this.save();
        });
        $('#btn-update').on('click',function(){
            _this.update();
        });
        $('#btn-delete').on('click',function(){
            _this.delete();
        });
    },
    save : function(){
        var data = {
            title:$('#title').val()
            ,author:$('#author').val()
            ,content:$('#content').val()
        };
        if(data.title == ''){
            alert('제목을 입력하세요');
             return;
         }
        if(data.content == ''){
            alert('내용을 입력하세요');
            return;
        }
        $.ajax({
            type:'POST'
            ,url:'/api/v1/posts'
            ,dataType:'json'
            ,contentType:'application/json; charset=utf-8'
            ,data:JSON.stringify(data)
        })
        .done(function(res){
            //console.log(res);
            alert('등록되었습니다.');
            window.location.href='/';
        })
        .fail(function(error){
            alert(JSON.stringify(error));
        })
    },
    update : function(){
        var id  = $('#id').val();
        var data = {
            title:$('#title').val()
            ,content:$('#content').val()
        };
        if(data.title == ''){
            alert('제목을 입력하세요');
             return;
        }
        if(data.content == ''){
            alert('내용을 입력하세요');
            return;
        }
        $.ajax({
            type:'PUT'
            ,url:'/api/v1/posts/'+id
            ,dataType:'json'
            ,contentType:'application/json; charset=utf-8'
            ,data:JSON.stringify(data)
        })
        .done(function(res){
            console.log(res);
            if(res < 0){
                alert('자신의 글만 수정할 수 있습니다.')
            }else{
                alert('수정되었습니다.');
                location.href='/';
            }
        })
        .fail(function(error){
            alert(JSON.stringify(error));
        })
    },
    delete : function(){
        var id  = $('#id').val();
        $.ajax({
            type:'DELETE'
            ,url:'/api/v1/posts/'+id
            ,dataType:'json'
            ,contentType:'application/json; charset=utf-8'
        })
        .done(function(res){
            console.log(res);
            if(res < 0){
                alert('자신의 글만 삭제할 수 있습니다.')
            }else{
                alert('삭제되었습니다.');
                location.href='/';
            }
        })
        .fail(function(error){
            alert(JSON.stringify(error));
        })
    }
} //index

index.init();