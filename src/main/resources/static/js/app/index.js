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
        var _this = this;
        var data = {
            title:$('#title').val(),
            author:$('#author').val(),
            content:$('#content').val()
        };
        if(_this.validation_check(data) == false){
            return;
        }
        $.ajax({
            type:'POST'
            ,url:'/api/v1/posts'
            ,dataType:'json'
            ,contentType:'application/json; charset=utf-8'
            ,data:JSON.stringify(data)
        })
        .done(function(){
            alert('등록되었습니다.');
            window.location.href='/';
        })
        .fail(function(error){
            alert(JSON.stringify(error));
        })
    },
    update : function(){
        var _this = this;
        var id  = $('#id').val();
        var data = {
            title:$('#title').val(),
            content:$('#content').val()
        };
        if(_this.validation_check(data) == false){
            return;
        }
        $.ajax({
            type:'PUT'
            ,url:'/api/v1/posts/'+id
            ,dataType:'json'
            ,contentType:'application/json; charset=utf-8'
            ,data:JSON.stringify(data)
        })
        .done(function(){
            alert('수정되었습니다.');
            location.href='/';
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
        .done(function(){
            alert('삭제되었습니다.');
            location.href='/';
        })
        .fail(function(error){
            alert(JSON.stringify(error));
        })
    },
    validation_check : function (data){
        if(data.title == ''){
            alert('제목을 입력하세요');
            return false;
        }
        if(data.content == ''){
            alert('내용을 입력하세요');
            return false;
        }
        return true;
    }
} //index

index.init();