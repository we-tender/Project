

// 정렬 기능
// 최신순 조회순 댓글순 좋아요순
function sortBy(sortBy) {

    var keyword = "";

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
    })
}