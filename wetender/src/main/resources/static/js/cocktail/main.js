
// 정렬 검색 기능
function searchSortBy(sortBy) {

    var keyword = $("#keyword").val();

    var cocktailSearchSortByDto = {
        keyword:keyword,
        sortBy:sortBy
    }

    $.ajax({
        url: "/cocktail/searchSortBy",
        data: cocktailSearchSortByDto,
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