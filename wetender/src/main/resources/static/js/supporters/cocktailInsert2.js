// 1. 넘어온 데이터가 있으면, 데이터에 맞게 화면 표시
// 2. 빈 리스트가 넘어오면, 초기엔 아무것도 없음.
// 3. 

// HTML 요소의 동적 제어 기능
$(document).ready(function(){
    // 칵테일 순서 추가
    $('.sequenceBtnAdd').click(function(){
        // 변수 선언
        let sequenceCnt = Number($('input[name="sequenceCnt"]').val()) + 1;

        let sequenceCname = 'sequenceContent'+sequenceCnt;
        let sequenceCdel = 'sequenceDel'+sequenceCnt;

        // 클로저 구현
        let decrease = (function () {

            // 클로저를 반환
           return function() {
                return --sequenceCnt;
            };
        }());

        $('#sequence').append(
            `   <input type="text" class="sequenceContent" name="sequenceContent" placeholder="순서">
                <input type="button" class="sequenceBtnRemove" name="sequenceDel" value="삭제"><br>
            `
        );

        $('.sequenceContent').eq(sequenceCnt-1).attr('name',sequenceCname);
        $('.sequenceBtnRemove').eq(sequenceCnt-1).attr('name', sequenceCdel);
        $('input[name="sequenceCnt"]').val(sequenceCnt);

       // HTML 요소 삭제
       $('.sequenceBtnRemove').click(function(){

            $(this).prev().remove();
            $(this).next().remove();
            $(this).remove();

            $('input[name="sequenceCnt"]').val(decrease());
       });
    });

    // 주류재료 추가
    $('.liquorIngredientBtnAdd').click(function(){
        // 변수 선언
        let liquorIngredientCnt = Number($('input[name="liquorIngredientCnt"]').val()) + 1;

        let liquorIngredientCname = 'liquorIngredientContent'+ liquorIngredientCnt;
        let liquorIngredientCid = 'liquorIngredientId'+ liquorIngredientCnt;
        let liquorIngredientQty = 'liquorIngredientQty'+ liquorIngredientCnt;
        let liquorIngredientSearch = 'liquorIngredientSearch' + liquorIngredientCnt;
        let liquorIngredientCdel = 'liquorIngredientDel'+ liquorIngredientCnt;

        console.log(liquorIngredientCid);
        console.log(liquorIngredientCname);
        // 클로저 구현
        let decrease = (function () {

            // 클로저를 반환
           return function() {
                return --liquorIngredientCnt;
            };
        }());

        $('#liquorIngredient').append(
            `   <input type="hidden" class="liquorIngredientId" id="liquorIngredientId" name="liquorIngredientId">
                <input type="text" class="liquorIngredientContent" name="liquorIngredientContent" readonly="readonly">
                <input type="button" class="liquorIngredientSearch" name="liquorIngredientSearch" value="주류 선택">
                <input type="text" class="liquorIngredientQty" name="liquorIngredientQty" placeholder="재료 양">
                <input type="button" class="liquorIngredientBtnRemove" name="liquorIngredientDel" value="삭제"><br>
            `
        );

        $('.liquorIngredientId').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientCid,'id':liquorIngredientCid});
        $('.liquorIngredientContent').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientCname,'id':liquorIngredientCname});
        $('.liquorIngredientQty').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientQty,'id':liquorIngredientQty});
        $('.liquorIngredientBtnRemove').eq(liquorIngredientCnt-1).attr({'name':liquorIngredientCdel,'id':liquorIngredientCdel});
        $('.liquorIngredientSearch').eq(liquorIngredientCnt-1).data({'name':liquorIngredientCname,'id':liquorIngredientCid});
        $('input[name="liquorIngredientCnt"]').val(liquorIngredientCnt);

        // HTML 요소 삭제
        $('.liquorIngredientBtnRemove').click(function(){

            $(this).prev().remove();
            $(this).prev().remove();
            $(this).prev().remove();
            $(this).prev().remove();
            $(this).next().remove();
            $(this).remove();

            $('input[name="liquorIngredientCnt"]').val(decrease());
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
        // 변수 선언
        let cocktailIngredientCnt = Number($('input[name="cocktailIngredientCnt"]').val()) + 1;

        let cocktailIngredientCname = 'cocktailIngredientContent'+ cocktailIngredientCnt;
        let cocktailIngredientCid = 'cocktailIngredientId'+ cocktailIngredientCnt;
        let cocktailIngredientQty = 'cocktailIngredientQty'+ cocktailIngredientCnt;
        let cocktailIngredientSearch = 'cocktailIngredientSearch' + cocktailIngredientCnt;
        let cocktailIngredientCdel = 'cocktailIngredientDel'+ cocktailIngredientCnt;

        // 클로저 구현
        let decrease = (function () {

            // 클로저를 반환
           return function() {
                return --cocktailIngredientCnt;
            };
        }());

        $('#cocktailIngredient').append(
            `   <input type="hidden" class="cocktailIngredientId" id="cocktailIngredientId" name="cocktailIngredientId">
                <input type="text" class="cocktailIngredientContent" name="cocktailIngredientContent" readonly="readonly">
                <input type="button" class="cocktailIngredientSearch" name="cocktailIngredientSearch" value="재료 선택">
                <input type="text" class="cocktailIngredientQty" name="cocktailIngredientQty" placeholder="재료 양">
                <input type="button" class="cocktailIngredientBtnRemove" name="cocktailIngredientDel" value="삭제"><br>
            `
        );

        $('.cocktailIngredientId').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientCid,'id':cocktailIngredientCid});
        $('.cocktailIngredientContent').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientCname,'id':cocktailIngredientCname});
        $('.cocktailIngredientQty').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientQty,'id':cocktailIngredientQty});
        $('.cocktailIngredientBtnRemove').eq(cocktailIngredientCnt-1).attr({'name':cocktailIngredientCdel,'id':cocktailIngredientCdel});
        $('.cocktailIngredientSearch').eq(cocktailIngredientCnt-1).data({'name':cocktailIngredientCname,'id':cocktailIngredientCid});
        $('input[name="cocktailIngredientCnt"]').val(cocktailIngredientCnt);

        // HTML 요소 삭제
        $('.cocktailIngredientBtnRemove').click(function(){

            $(this).prev().remove();
            $(this).prev().remove();
            $(this).prev().remove();
            $(this).prev().remove();
            $(this).next().remove();
            $(this).remove();

            $('input[name="cocktailIngredientCnt"]').val(decrease());
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
    
});

