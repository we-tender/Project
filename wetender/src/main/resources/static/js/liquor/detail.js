
//삭제 확인
function deleteConfirm(id){

	if(!confirm("삭제 하시겠습니까?")){
		return false;
	}else{
		location.href="/supporters/liquor/delete/"+id;
	}
}

// 댓글 저장
function replyInsert() {
    var liquorIdData = $("#liquorId").val();
    var liquorReplyContentData = $("#liquorReplyContent").val();
    var liquorReplyInsertDto = {
        liquorId:liquorIdData,
        liquorReplyContent:liquorReplyContentData
    }
    $.ajax({
        url: "/liquorReply/insert",
        data: liquorReplyInsertDto,
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
function replyDelete(liquorReplyId) {

    var liquorId = $("#liquorId").val();
    var liquorReplyDeleteDto = {
        liquorId:liquorId,
        liquorReplyId:liquorReplyId
    };

    $.ajax({
        url: "/liquorReply/delete",
        data: liquorReplyDeleteDto,
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
function replyEditFormOpen(liquorReplyId) {
    var reply = "reply".concat(liquorReplyId);
    var replyEdit = "replyEdit".concat(liquorReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "none";
    replyEditElement.style.display = "block";
}

// 댓글 수정 양식 닫기
function replyEditFormClose(liquorReplyId) {
    var reply = "reply".concat(liquorReplyId);
    var replyEdit = "replyEdit".concat(liquorReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "block";
    replyEditElement.style.display = "none";
}

// 댓글 수정 저장
function replyEdit(liquorReplyId) {

    var replyContentElement = "#replyContentEdit".concat(liquorReplyId);
    var liquorId = $("#liquorId").val();
    var liquorReplyContent = $(replyContentElement).val();

    var liquorReplyEditDto = {
        liquorId:liquorId,
        liquorReplyId:liquorReplyId,
        liquorReplyContent:liquorReplyContent
    }

    $.ajax({
        url: "/liquorReply/edit",
        data: liquorReplyEditDto,
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

// 좋아요 추가 혹은 삭제
function liquorLikesInsertOrDelete() {

    var liquorId = $("#liquorId").val();
    var memberName = $("#memberName").val();

    var liquorLikesInsertOrDeleteDto = {
        liquorId:liquorId,
        memberName:memberName
    };

    $.ajax({
        url: "/liquorLikes/InsertOrDelete",
        data: liquorLikesInsertOrDeleteDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }
    }).done(function (fragment) {
        $("#likes").replaceWith(fragment);
    })

}