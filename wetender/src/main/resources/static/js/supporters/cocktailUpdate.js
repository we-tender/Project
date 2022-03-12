
// HTML 요소의 동적 제어 기능
$(document).ready(function(){

    let liquorIngredientCnt = Number($('input[name="liquorIngredientCnt"]').val());
    let cocktailIngredientCnt = Number($('input[name="cocktailIngredientCnt"]').val());


    //초기 데이터 함수 걸기*****************************************************************************************
    $('.sequenceBtnRemove').click(function(){
        $(this).parent("div").remove();
    });

    $('.liquorIngredientBtnRemove').click(function(){
        $(this).parent("div").remove();
    });

    $('.cocktailIngredientBtnRemove').click(function(){
        $(this).parent("div").remove();
    });

    /* 주류 선택 버튼 */
    $('.liquorIngredientSearch').click(function(e){

        e.preventDefault();

        let liquorBtnId = $(this).data('id');
        let liquorBtnName = $(this).data('name');
        let popUrl = "/supporters/pop/liquorPop" +"?id=" + liquorBtnId + "&name=" + liquorBtnName;
        let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

        window.open(popUrl,"주류 찾기",popOption);

    });

    /* 재료 선택 버튼 */
    $('.cocktailIngredientSearch').click(function(e){

        e.preventDefault();

        let ingredientBtnId = $(this).data('id');
        let ingredientBtnName = $(this).data('name');
        let popUrl = "/supporters/pop/ingredientPop" +"?id=" + ingredientBtnId + "&name=" + ingredientBtnName;
        let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

        window.open(popUrl,"식재료 찾기",popOption);

    });
    //*****************************************************************************************

    // 칵테일 순서 추가
    $('.sequenceBtnAdd').click(function(){

        let sequenceCname = 'sequenceContent';
        let sequenceCdel = 'sequenceDel';


        $('#sequence').append(
            `   <div>
                    <input type="text" class="sequenceContent" name="sequenceContent" placeholder="순서">
                    <input type="button" class="sequenceBtnRemove" name="sequenceDel" value="삭제"><br>
                </div>
            `
        );

        $('.sequenceBtnRemove').click(function(){
            $(this).parent("div").remove();
        });
    });

    // 주류재료 추가
    $('.liquorIngredientBtnAdd').click(function(){

        liquorIngredientCnt = liquorIngredientCnt +1;

        let liquorIngredientCname = 'liquorIngredientContent'+ liquorIngredientCnt;
        let liquorIngredientCid = 'liquorIngredientId'+ liquorIngredientCnt;
        let liquorIngredientQty = 'liquorIngredientQty'+ liquorIngredientCnt;
        let liquorIngredientSearch = 'liquorIngredientSearch' + liquorIngredientCnt;
        let liquorIngredientCdel = 'liquorIngredientDel'+ liquorIngredientCnt;

        $('#liquorIngredient').append(
            `   <div>
                <input type="hidden" class="liquorIngredientId" name="liquorIngredientId">
                <input type="text" class="liquorIngredientContent" name="liquorIngredientContent" readonly="readonly">
                <input type="button" class="liquorIngredientSearch" name="liquorIngredientSearch" value="주류 선택">
                <input type="text" class="liquorIngredientQty" name="liquorIngredientQty" placeholder="재료 양">
                <input type="button" class="liquorIngredientBtnRemove" name="liquorIngredientDel" value="삭제"><br>
                </div>
            `
        );

        $('.liquorIngredientId').eq(liquorIngredientCnt-1).attr({'id':liquorIngredientCid});
        $('.liquorIngredientContent').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientCname,'id':liquorIngredientCname});
        $('.liquorIngredientQty').eq(liquorIngredientCnt-1).attr({'id':liquorIngredientQty});
        $('.liquorIngredientBtnRemove').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientCdel,'id':liquorIngredientCdel});
        $('.liquorIngredientSearch').eq(liquorIngredientCnt-1).attr({'data-name':liquorIngredientCname,'data-id':liquorIngredientCid});
        $('input[name="liquorIngredientCnt"]').val(liquorIngredientCnt);

        // HTML 요소 삭제
        $('.liquorIngredientBtnRemove').click(function(){
            $(this).parent("div").remove();
        });

        /* 주류 선택 버튼 */
        $('.liquorIngredientSearch').click(function(e){

            e.preventDefault();

            let liquorBtnId = $(this).data('id');
            let liquorBtnName = $(this).data('name');
            let popUrl = "/supporters/pop/liquorPop" +"?id=" + liquorBtnId + "&name=" + liquorBtnName;
            let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

            window.open(popUrl,"주류 찾기",popOption);

        });
    });

    // 재료 추가
    $('.cocktailIngredientBtnAdd').click(function(){

        cocktailIngredientCnt = cocktailIngredientCnt + 1;

        let cocktailIngredientCname = 'cocktailIngredientContent'+ cocktailIngredientCnt;
        let cocktailIngredientCid = 'cocktailIngredientId'+ cocktailIngredientCnt;
        let cocktailIngredientQty = 'cocktailIngredientQty'+ cocktailIngredientCnt;
        let cocktailIngredientSearch = 'cocktailIngredientSearch' + cocktailIngredientCnt;
        let cocktailIngredientCdel = 'cocktailIngredientDel'+ cocktailIngredientCnt;

        $('#cocktailIngredient').append(
            `   <div>
                <input type="hidden" class="cocktailIngredientId" id="cocktailIngredientId" name="cocktailIngredientId">
                <input type="text" class="cocktailIngredientContent" name="cocktailIngredientContent" readonly="readonly">
                <input type="button" class="cocktailIngredientSearch" name="cocktailIngredientSearch" value="재료 선택">
                <input type="text" class="cocktailIngredientQty" name="cocktailIngredientQty" placeholder="재료 양">
                <input type="button" class="cocktailIngredientBtnRemove" name="cocktailIngredientDel" value="삭제"><br>
                </div>
            `
        );

        $('.cocktailIngredientId').eq(cocktailIngredientCnt-1).attr({'id':cocktailIngredientCid});
        $('.cocktailIngredientContent').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientCname,'id':cocktailIngredientCname});
        $('.cocktailIngredientQty').eq(cocktailIngredientCnt-1).attr({'id':cocktailIngredientQty});
        $('.cocktailIngredientBtnRemove').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientCdel,'id':cocktailIngredientCdel});
        $('.cocktailIngredientSearch').eq(cocktailIngredientCnt-1).data({'name':cocktailIngredientCname,'id':cocktailIngredientCid});
        $('input[name="cocktailIngredientCnt"]').val(cocktailIngredientCnt);

        // HTML 요소 삭제
        $('.cocktailIngredientBtnRemove').click(function(){
            $(this).parent("div").remove();
        });

        /* 재료 선택 버튼 */
        $('.cocktailIngredientSearch').click(function(e){

            e.preventDefault();

            let ingredientBtnId = $(this).data('id');
            let ingredientBtnName = $(this).data('name');
            let popUrl = "/supporters/pop/ingredientPop" +"?id=" + ingredientBtnId + "&name=" + ingredientBtnName;
            let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

            window.open(popUrl,"식재료 찾기",popOption);

        });
    });

});

