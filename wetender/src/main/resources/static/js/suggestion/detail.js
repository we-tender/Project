



// 댓글 저장
function replyInsert() {

    var suggestionId = $("#suggestionId").val();
    var suggestionReplyContent = $("#suggestionReplyContent").val();
    var suggestionReplyInsertDto = {
        suggestionId:suggestionId,
        suggestionReplyContent:suggestionReplyContent
    }
    $.ajax({
        url: "/suggestionReply/insert",
        data: suggestionReplyInsertDto,
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
function replyDelete(suggestionReplyId) {

    var suggestionId = $("#suggestionId").val();
    var suggestionReplyDeleteDto = {
        suggestionId:suggestionId,
        suggestionReplyId:suggestionReplyId
    };

    $.ajax({
        url: "/suggestionReply/delete",
        data: suggestionReplyDeleteDto,
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
function replyEditFormOpen(suggestionReplyId) {
    var reply = "reply".concat(suggestionReplyId);
    var replyEdit = "replyEdit".concat(suggestionReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "none";
    replyEditElement.style.display = "block";
}

// 댓글 수정 양식 닫기
function replyEditFormClose(suggestionReplyId) {
    var reply = "reply".concat(suggestionReplyId);
    var replyEdit = "replyEdit".concat(suggestionReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "block";
    replyEditElement.style.display = "none";
}

// 댓글 수정 저장
function replyEdit(suggestionReplyId) {

    var replyContentElement = "#replyContentEdit".concat(suggestionReplyId);
    var suggestionId = $("#suggestionId").val();
    var suggestionReplyContent = $(replyContentElement).val();

    var suggestionReplyEditDto = {
        suggestionId:suggestionId,
        suggestionReplyId:suggestionReplyId,
        suggestionReplyContent:suggestionReplyContent
    };

    $.ajax({
        url: "/suggestionReply/edit",
        data: suggestionReplyEditDto,
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