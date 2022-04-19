

// 정렬 검색 기능
// 최신순 조회순 댓글순 좋아요순
function searchSortBy(sortBy) {

    var keyword = $("#keyword").val();

    var liquorSortDto = {
        keyword:keyword,
        sortBy:sortBy
    }

    $.ajax({
        url: "/liquor/sortBy",
        data: liquorSortDto,
        type: "POST",

        beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           if(token && header) {
               jqXHR.setRequestHeader(header, token);
           }
        }

    }).done(function (fragment) {
        $("#main").replaceWith(fragment);
        $("#" + sortBy).addClass("selected-item");
    })
}



// 페이징 버튼
// 조회 POST 보다 빠른 GET 방식 사용
function movePage(pageNumber) {

    var keyword = $("#keyword").val();
    var sortBy = $("#sortBy").val();
    var pageNumber = pageNumber;
    var path = "/liquor/movePage?page=".concat(pageNumber)
                + "&keyword=".concat(keyword) + "&sortBy=".concat(sortBy);

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
            $("#main").replaceWith(fragment);
        });

}

