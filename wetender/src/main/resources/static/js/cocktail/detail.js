



// 댓글 저장
function replyInsert() {1

    var cocktailId = $("#cocktailId").val();
    var cocktailReplyContent = $("#cocktailReplyContent").val();
    var cocktailReplyInsertDto = {
        cocktailId:cocktailId,
        cocktailReplyContent:cocktailReplyContent
    }
    $.ajax({
        url: "/cocktailReply/insert",
        data: cocktailReplyInsertDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }
    }).done(function (fragment) {
        $("#reply").replaceWith(fragment);
    });
}

// 댓글 삭제
function replyDelete(cocktailReplyId) {

    var cocktailId = $("#cocktailId").val();
    var cocktailReplyDeleteDto = {
        cocktailId:cocktailId,
        cocktailReplyId:cocktailReplyId
    };

    $.ajax({
        url: "/cocktailReply/delete",
        data: cocktailReplyDeleteDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }
    }).done(function (fragment) {
        $("#reply").replaceWith(fragment);
    });
}

// 댓글 수정 양식 열기
function replyEditFormOpen(cocktailReplyId) {
    var reply = "reply".concat(cocktailReplyId);
    var replyEdit = "replyEdit".concat(cocktailReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "none";
    replyEditElement.style.display = "block";
}

// 댓글 수정 양식 닫기
function replyEditFormClose(cocktailReplyId) {
    var reply = "reply".concat(cocktailReplyId);
    var replyEdit = "replyEdit".concat(cocktailReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "block";
    replyEditElement.style.display = "none";
}

// 댓글 수정 저장
function replyEdit(cocktailReplyId) {

    var replyContentElement = "#replyContentEdit".concat(cocktailReplyId);
    var cocktailId = $("#cocktailId").val();
    var cocktailReplyContent = $(replyContentElement).val();

    var cocktailReplyEditDto = {
        cocktailId:cocktailId,
        cocktailReplyId:cocktailReplyId,
        cocktailReplyContent:cocktailReplyContent
    }

    $.ajax({
        url: "/cocktailReply/edit",
        data: cocktailReplyEditDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
        $("#reply").replaceWith(fragment);
    });
}