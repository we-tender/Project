

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
    })


    // 화면 상에서만 +1, -1 을 실행하는 부분
    const resultElement = document.getElementById('noticeBoardLikes');
    let number = resultElement.innerText;

    const flagElement = document.getElementById('likesFlag');
    let flag = flagElement.innerText;

    if( parseInt(number) === parseInt(flag) ) {
        number = parseInt(number) + 1;
    }
    else if( parseInt(number) !==  parseInt(flag) ) {
        number = parseInt(number) - 1;
    }

    resultElement.innerText = number;

}


