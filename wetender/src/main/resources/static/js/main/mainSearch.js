

// 페이징 버튼
// 조회 POST 보다 빠른 GET 방식 사용
function cocktailMovePage(pageNumber) {

    var keyword = $("#keyword").val();
    var pageNumber = pageNumber;
    var path = "/mainSearch/cocktailMovePage?page=".concat(pageNumber) + "&keyword=".concat(keyword);

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
            $("#cocktailLikes").replaceWith(fragment);
        });
}

// 페이징 버튼
// 조회 POST 보다 빠른 GET 방식 사용
function liquorMovePage(pageNumber) {

    var keyword = $("#keyword").val();
    var pageNumber = pageNumber;
    var path = "/mainSearch/liquorMovePage?page=".concat(pageNumber) + "&keyword=".concat(keyword);

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
            $("#liquorLikes").replaceWith(fragment);
        });

}