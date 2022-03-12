// 1. 넘어온 데이터가 있으면, 데이터에 맞게 화면 표시
// 2. 빈 리스트가 넘어오면, 초기엔 아무것도 없음.
// 3. 

// HTML 요소의 동적 제어 기능
$(document).ready(function(){
    let sequenceCnt = document.querySelectorAll(".sequenceContent").length;

    // 칵테일 순서 추가
    $('.sequenceBtnAdd').click(function(){
        // 변수 선언
        sequenceCnt = sequenceCnt +1;
        // let sequenceCnt = Number($('input[name="sequenceCnt"]').val()) + 1;

        let sequenceId = 'sequence'+sequenceCnt;
        let sequencedelId = 'sequenceDel'+sequenceCnt;

        // 클로저 구현
        let decrease = (function () {

            // 클로저를 반환
           return function() {
                return --sequenceCnt;
            };
        }());

        $('#sequence').append(
            `   <div>
                <input type="text" class="sequenceContent" name="sequence" placeholder="순서">
                <input type="button" class="sequenceBtnRemove" name="sequenceDel" value="삭제"><br>
                </div>
            `
        );

        $('.sequenceContent').eq(sequenceCnt-1).attr('id',sequenceId);
        $('.sequenceBtnRemove').eq(sequenceCnt-1).attr('id', sequencedelId);
        let check = document.querySelectorAll('.sequenceContent');
        console.dir(check);

        // HTML 요소 삭제
        $('.sequenceBtnRemove').click(function(){

            $(this).prev().remove();
            $(this).prev().remove();
            $(this).next().remove();
            $(this).next().remove();
            $(this).remove();
        });
    });

});

