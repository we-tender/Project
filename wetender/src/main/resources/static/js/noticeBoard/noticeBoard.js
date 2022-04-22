

function dataSend() {
    var data=$("#input").val();
    var messageDTO={
        result:data
    };
    $.ajax({
        url: "/dataSend",
        data: messageDTO,
        type:"POST",
        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
        },

        success: function(result) {
                  if (result) {
                      alert("완료");
                  } else {
                      alert("전송된 값 없음");
                  }
              },
        error: function() {
            alert("에러 발생");
        }

    }).done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
    });
}

// 좋아요 기능
function noticeBoardLikes() {
    var noticeBoardIdLikesData=$("#noticeBoardIdLikes").val();
    var memberNameLikesData=$("#memberNameLikes").val();

    var noticeBoardLikesInsertDto = {
        noticeBoardIdLikes:noticeBoardIdLikesData,
        memberNameLikes:memberNameLikesData
    };
    $.ajax({
        url: "/noticeBoard/likesInsert",
        data: noticeBoardLikesInsertDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }
    }).done(function (fragment) {
        $("#likesResult").replaceWith(fragment);
    })


}


// 정렬 기능
function noticeBoardSortBy(sortBy) {

    var sortByData="";
    var keywordData="";

    if(sortBy === 'views') {
        sortByData = "views";
    }
    else if(sortBy === 'createdBy') {
        sortByData = "createdBy";
    }
    else if(sortBy === 'likes') {
        sortByData = "likes";
    }
    else if(sortBy === 'replies') {
        sortByData = "replies";
    }


    var noticeBoardKeywordSortDto = {
        keyword:keywordData,
        sortBy:sortByData
    }

    $.ajax({
        url: "/noticeBoard/sortBy",
        data: noticeBoardKeywordSortDto,
        type: "POST",


        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment)  {
        $("#noticeBoardResult").replaceWith(fragment);
    })
}

// 댓글 삭제
function replyDelete(noticeBoardReplyId) {

    var noticeBoardReplyIdData = noticeBoardReplyId;
    var noticeBoardIdData = $("#noticeBoardId").val();

    var noticeBoardReplyDeleteDto = {
        noticeBoardId:noticeBoardIdData,
        noticeBoardReplyId:noticeBoardReplyIdData
    };

    $.ajax({
        url: "/noticeBoard/replyDeleteAjax",
        data: noticeBoardReplyDeleteDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
        $("#replyResult").replaceWith(fragment);
    });
}


// 댓글 저장
function replyInsert() {
    var noticeBoardIdData = $("#noticeBoardId").val();
    var noticeBoardReplyContentData = $("#noticeBoardReplyContent").val();

    var noticeBoardReplyInsertDto = {
        noticeBoardId:noticeBoardIdData,
        noticeBoardReplyContent:noticeBoardReplyContentData
    };

    $.ajax({
        url: "/noticeBoard/replyInsertAjax",
        data: noticeBoardReplyInsertDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
       $("#replyResult").replaceWith(fragment);
   });
}

// 댓글 수정 양식 출현
function replyEditForm(noticeBoardReplyId) {
// 아이디에 맞게 가져오는 법을 알아야한다.

    var reply = "reply".concat(noticeBoardReplyId);
    var replyEdit = "replyEdit".concat(noticeBoardReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "none";
    replyEditElement.style.display = "block";

}

// 댓글 수정 양식 닫기
function replyEditFormCancel(noticeBoardReplyId) {

    var reply = "reply".concat(noticeBoardReplyId);
    var replyEdit = "replyEdit".concat(noticeBoardReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "block";
    replyEditElement.style.display = "none";

}

// 댓글 수정 저장
function replyEditSave(noticeBoardReplyId) {

    var replyContentElement = "#replyContentEdit".concat(noticeBoardReplyId);

    var noticeBoardIdData = $("#noticeBoardId").val();
    var noticeBoardReplyContentEditData = $(replyContentElement).val();

    var noticeBoardReplyInsertDto = {
        noticeBoardId:noticeBoardIdData,
        noticeBoardReplyId:noticeBoardReplyId,
        noticeBoardReplyContent:noticeBoardReplyContentEditData
    };


    var reply = "reply".concat(noticeBoardReplyId);
    var replyEdit = "replyEdit".concat(noticeBoardReplyId);

    const replyElement = document.getElementById(reply);
    const replyEditElement = document.getElementById(replyEdit);

    replyElement.style.display = "block";
    replyEditElement.style.display = "none";

    $.ajax({
        url: "/noticeBoard/replyEditAjax",
        data: noticeBoardReplyInsertDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
        $("#replyResult").replaceWith(fragment);
    });
}

// 게시글 페이지 움직이기 GET 방식
function movePage(pageNumber) {

    var noticeBoardId = $("#noticeBoardId").val();
    var pageNumber = pageNumber;
    var path = "/noticeBoard/movePage?noticeBoardId=".concat(noticeBoardId)
                + "&page=".concat(pageNumber);

    $.ajax({
        url: path,
        type: "GET",

       beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
        $("#noticeBoardDetailList").replaceWith(fragment);
    });
}

// 게시글 페이지 움직이기 POST 방식
function pageMove(pageNumber) {

    var noticeBoardId = $("#noticeBoardId").val();
    var pageNumber = pageNumber;
    var noticeBoardMovePageDto = {
        noticeBoardId:noticeBoardId,
        pageNumber:pageNumber
    }

    $.ajax({
        url: "/noticeBoard/pageMove",
        data: noticeBoardMovePageDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done( function(fragment) {
        $("#noticeBoardDetailList").replaceWith(fragment);
    });

}

// 드롭다운메뉴 클릭 시 이벤트
$(function() {
    $(".dropdown").click(function() {
        if (this.dataset.toggle == "close") {
            console.log(this.dataset.index + " close to open");
            this.dataset.toggle = "open";
            this.classList.remove("dropdown-close");
            this.classList.add("dropdown-open");

            const curIdx = this.dataset.index;
            const menu = document.querySelectorAll('.dropdown');
            for (let i = 0; i < menu.length; i++) {
                let dropdownMenu = menu.item(i);
                if (i != curIdx && dropdownMenu.dataset.toggle == "open") {
                    dropdownMenu.classList.remove("dropdown-open");
                    dropdownMenu.classList.add("dropdown-close");
                    dropdownMenu.dataset.toggle = "close";
                }
            }
        } else {
            console.log(this.dataset.index + " open to close");
            this.dataset.toggle = "close";
            this.classList.remove("dropdown-open");
            this.classList.add("dropdown-close");
        }
    });
});
// none-dropdown 클릭 시 이벤트 : dropdown 메뉴 닫기
$(function() {
    $(".none-dropdown").click(function() {
        const menu = document.querySelectorAll('.dropdown');
        for (let i = 0; i < menu.length; i++) {
            let dropdownMenu = menu.item(i);
            if (dropdownMenu.dataset.toggle == "open") {
                dropdownMenu.classList.remove("dropdown-open");
                dropdownMenu.classList.add("dropdown-close");
                dropdownMenu.dataset.toggle = "close";
                console.log(dropdownMenu.dataset.index + " open to close");
            }
        }
    });
});