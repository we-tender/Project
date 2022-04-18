// HTML 요소의 동적 제어 기능
$(document).ready(function(){
    $("button[type='submit']").on("click", function(e){
        e.preventDefault();

        let formObj = $("form[role='form']");
        let str = "";
        console.dir(formObj);
        const items = $(".uploadResult li");
        for (let i = 0; i < items.length - 1; i++) {
            const jobj = $(items[i]);
            console.dir(jobj);
            str += "<input type='hidden' name='attachList[" + i +"].fileName' ";
            str += "value='" + jobj.data("filename") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uuid' ";
            str += "value='" + jobj.data("uuid") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uploadPath' ";
            str += "value='" + jobj.data("path") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].fileType' ";
            str += "value='" + jobj.data("type") + "'>";
        }
        formObj.append(str).submit();
    });

    // 이미지 삭제
    $(".uploadResult").on("click","button", function(e){
        console.log("delete file");

        if(confirm("Remove this file ?")){
            const targetFile = $(this).data("file");
            const type = $(this).data("type");
            const targetLi = $(this).closest("li");

            $.ajax({
                url : "/supporters/deleteCocktailFile",
                data : {fileName : targetFile, type : type},
                dataType : "text",
                type : 'POST',
                beforeSend: function (jqXHR, settings) {
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var token = $("meta[name='_csrf']").attr("content");
                    jqXHR.setRequestHeader(header, token);
                },
                success : function(result){
                    alert(result);
                    targetLi.remove();
                }
            });
        }
    });

    let cocktailLiquorCnt = Number($('input[name="liquorsCnt"]').val());
    let cocktailIngredientCnt = Number($('input[name="ingredientsCnt"]').val());
    let sequencesCnt = Number($('input[name="sequencesCnt"]').val());


    //초기 데이터 함수 걸기*****************************************************************************************

    $('.liquorIngredientBtnRemove').click(function(){
        $(this).parent().remove();
    });

    $('.cocktailIngredientBtnRemove').click(function(){
        $(this).parent().remove();
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

});

// 파일 검증
function checkExtension(fileName, fileSize){
    const regex = new RegExp("(.*?)\.(jpg|png|jpeg)$");
    var maxSize = 524880; // 5MB

    if(fileSize >= maxSize){
        alert("파일 크기 초과");
        return false;
    }
    // 이미지 파일이 아니면
    if(regex.test(fileName) === false){
        alert("jpg, jpeg, png 만 올릴 수 있습니다.");
        return false;
    }
    return true;
}

function showUploadResult(uploadResultArr){
    if(!uploadResultArr || uploadResultArr.length ==0){
        return;
    }

    const uploadUl = $(".uploadResult");
    var str = "";

    $(uploadResultArr).each(function(i, obj){
        //image type
        const fileCellPath = obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;
        str += "<li data-path='" + obj.uploadPath + "' data-uuid='" +obj.uuid + "'";
        str += " data-filename='" +obj.fileName + "' data-type='" +obj.fileType +"'>";
        str += "<figure><figcaption>";
        str += "<button type='button' data-file= '" + fileCellPath + "' data-type='image'";
        str += " class='btn-img-del'></button></figcaption>";
        str += "<img src='/supporters/display/cocktail/" + fileCellPath + "'></figure>";
        str += "</li>";
    });
    // 파일등록 버튼 uploadResult li 마지막에 만들어두기
    $(".input-file").parent().remove();
    str += "<li class='contents-flex flex-row-center'>";
    str += "<div class='input-file display-flex flex-row-center-center'>";
    str += "<label for='uploadFile'><div></div></label>";
    str += "<input type='file' id='uploadFile' name='uploadFile' onchange='inputChange(this)' multiple='multiple'></div>";
    str += "</li>";
    uploadUl.append(str);
}

// 파일 변경 감지
function inputChange(obj) {
    const formData = new FormData();
    const files = obj.files;
    for(let i = 0; i < files.length; i++){
        if(!checkExtension(files[i].name, files[i].size)){
            return false;
        }
        formData.append("uploadFile",files[i]);
    }

    $.ajax({
        url : "/supporters/upload/image/cocktail",
        processData : false,
        contentType : false,
        data : formData,
        type: "POST",
        dataType : 'json',
        beforeSend: function (jqXHR, settings) {
            var header = $("meta[name='_csrf_header']").attr("content");
            var token = $("meta[name='_csrf']").attr("content");
            jqXHR.setRequestHeader(header, token);
        },
        success : function(result){
            console.log(result);
            showUploadResult(result); // 업로드 결과 처리 함수
        }
    });
};

// 칵테일 순서 추가
function sequenceAdd() {
    var cnt = document.getElementById("sequencesCnt");
    const sequencesCnt = cnt.value;
    cnt.value = cnt.value * 1 + 1;

    let str = "";
    str += "<li class='position-rel''><input type='text' class='width-100p-2rem' id='sequences[" + sequencesCnt + "].content' ";
    str += "name='sequences[" + sequencesCnt + "].content' placeholder='다음 순서를 입력하세요.'> ";
    str += "<label for='sequenceRemove' onclick='sequenceRemove(this)'><div class='btn-del-x'></div></label>";
    str += "<button type='button' class='hidden-item' id='sequenceRemove'></button></li>";

    $('#sequenceUl').append(str);
};

// 칵테일 순서 삭제
function sequenceRemove(obj) {
    const parent = obj.parentNode;
    const curId = parent.children[0].id;
    const sIdx = curId.indexOf('[') + 1;
    const eIdx = curId.indexOf(']');

    const cur = curId.substring(sIdx, eIdx) * 1;
    const cnt = document.getElementById("sequencesCnt");

    for (let i = cur + 1, j = cur; i < cnt.value; i++, j++) {
        const node = document.getElementById("sequences[" + i + "].content");
        node.id = "sequences[" + j + "].content";
        node.name = "sequences[" + j + "].content";
    }

    $(obj).parent().remove();
    cnt.value = cnt.value * 1 - 1;
};

// 주류 추가
function cocktailLiquorAdd() {
    var cnt = document.getElementById("liquorsCnt");
    const cocktailLiquorCnt = cnt.value;
    cnt.value = cnt.value * 1 + 1;

    let cocktailLiquorId = "liquors[" + cocktailLiquorCnt + "].id";
    let cocktailLiquorName = "liquors[" + cocktailLiquorCnt + "].name";
    let cocktailLiquorQty = "liquors[" + cocktailLiquorCnt + "].quantity";

    let str = "<li class='position-rel'>";
    str += "<input type='hidden' id='" + cocktailLiquorId + "' ";
    str += "name='" + cocktailLiquorId + "'>";
//    const inputId = document.createElement("input");
//    inputId.id = cocktailLiquorId;
//    inputId.type = "hidden";
//    inputId.name = cocktailLiquorId;

    str += "<button type='button' class='btn-1' ";
    str += "data-id='" + cocktailLiquorId + "' data-name='" + cocktailLiquorName;
    str += "' onclick='cocktailLiquorSearch(this)'>선택</button> ";
//    const inputSearch = document.createElement("input");
//    inputSearch.className = "cocktailLiquorSearch";
//    inputSearch.dataset.id = cocktailLiquorCnt;
//    inputSearch.value = "주류 선택";
//    inputSearch.type = "button";

    str += "<input type='text' id='" + cocktailLiquorName + "' ";
    str += "name='" + cocktailLiquorName + "' placeholder='주류를 선택하세요.' readonly='readonly'> ";
//    const inputName = document.createElement("input");
//    inputName.id = cocktailLiquorName;
//    inputName.name = cocktailLiquorName;
//    inputName.type = "text";
//    inputName.readOnly = true;

    str += "<input type='text' id='" + cocktailLiquorQty;
    str += "' name='" + cocktailLiquorQty + "' class='width-10rem' placeholder='주류 양'> ";
//    const inputQty = document.createElement("input");
//    inputQty.type = "text";
//    inputQty.name = cocktailLiquorQty;
//    inputQty.placeholder = "재료 양";

    str += "<label for='cocktailLiquorRemove' onclick='cocktailLiquorRemove(this)'><div class='btn-del-x'></div></label>";
    str += "<button type='button' class='hidden-item' id='cocktailLiquorRemove'></button></li>";
//    const inputDelete = document.createElement("input");
//    inputDelete.type = "button";
//    inputDelete.className = "cocktailLiquorBtnRemove";
//    inputDelete.value = "삭제";

//    div.appendChild(inputId);
//    div.appendChild(inputName);
//    div.appendChild(inputSearch);
//    div.appendChild(inputQty);
//    div.appendChild(inputDelete);

    $('#cocktailLiquorUl').append(str);
};

// 주류 선택 버튼
function cocktailLiquorSearch(obj) {
//    obj.preventDefault();

    const parent = obj.parentNode;
    let idStr = parent.children[0].id;
    let sIdx = idStr.indexOf('[') + 1;
    let eIdx = idStr.indexOf(']');
    let liquorBtnId = idStr.substring(sIdx, eIdx);
    console.log(liquorBtnId);

    let popUrl = "/supporters/pop/liquorPop" +"?id=" + liquorBtnId;
    let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

    window.open(popUrl,"주류 찾기",popOption);
};

// 주류 삭제
function cocktailLiquorRemove(obj) {
    const parent = obj.parentNode;
    const curId = parent.children[0].id;
    const sIdx = curId.indexOf('[') + 1;
    const eIdx = curId.indexOf(']');

    const cur = curId.substring(sIdx, eIdx) * 1;
    const cnt = document.getElementById("liquorsCnt");

    for (let i = cur + 1, j = cur; i < cnt.value; i++, j++) {
        let node = document.getElementById("liquors[" + i + "].id");
        node.id = "liquors[" + j + "].id";
        node.name = "liquors[" + j + "].id";

        node = node.nextElementSibling;
        node.dataset.id = "liquors[" + j + "].id";
        node.dataset.name = "liquors[" + j + "].name";

        node = node.nextElementSibling;
        node.id = "liquors[" + j + "].name";
        node.name = "liquors[" + j + "].name";

        node = node.nextElementSibling;
        node.id = "liquors[" + j + "].quantity";
        node.name = "liquors[" + j + "].quantity";
    }

    $(obj).parent().remove();
    cnt.value = cnt.value * 1 - 1;
};

// 재료 추가
function cocktailIngredientAdd() {
    var cnt = document.getElementById("ingredientsCnt");
    const cocktailIngredientCnt = cnt.value;
    cnt.value = cnt.value * 1 + 1;

    let cocktailIngredientId = "ingredients[" + cocktailIngredientCnt + "].id";
    let cocktailIngredientName = "ingredients[" + cocktailIngredientCnt + "].name";
    let cocktailIngredientQty = "ingredients[" + cocktailIngredientCnt + "].quantity";

    let str = "<li>";
    str += "<input type='hidden' id='" + cocktailIngredientId + "' ";
    str += "name='" + cocktailIngredientId + "'>";
//    const inputId = document.createElement("input");
//    inputId.id = cocktailIngredientId;
//    inputId.type = "hidden";
//    inputId.name = cocktailIngredientId;

    str += "<button type='button' class='cocktailIngredientSearch btn-1' ";
    str += "data-id='" + cocktailIngredientId + "' data-name='" + cocktailIngredientName;
    str += "' onclick='cocktailIngredientSearch(this)'>선택</button> ";
//    const inputSearch = document.createElement("input");
//    inputSearch.className = "cocktailIngredientSearch";
//    inputSearch.dataset.id = cocktailIngredientCnt;
//    inputSearch.value = "재료 선택";
//    inputSearch.type = "button";

    str += "<input type='text' id='" + cocktailIngredientName + "' ";
    str += "name='" + cocktailIngredientName + "' placeholder='재료를 선택하세요.' readonly='readonly'> ";
//    const inputName = document.createElement("input");
//    inputName.id = cocktailIngredientName;
//    inputName.name = cocktailIngredientName;
//    inputName.type = "text";
//    inputName.readOnly = true;

    str += "<input type='text' id='" + cocktailIngredientQty;
    str += "' name='" + cocktailIngredientQty + "' class='width-10rem' placeholder='재료 양'> ";
//    const inputQty = document.createElement("input");
//    inputQty.type = "text";
//    inputQty.name = cocktailIngredientQty;
//    inputQty.placeholder = "재료 양";

    str += "<label for='cocktailLiquorRemove' onclick='cocktailIngredientRemove(this)'><div class='btn-del-x'></div></label>";
    str += "<button type='button' class='hidden-item' id='cocktailIngredientRemove'></button></li>";
//    const inputDelete = document.createElement("input");
//    inputDelete.type = "button";
//    inputDelete.className = "cocktailIngredientBtnRemove";
//    inputDelete.value = "삭제";

//    div.appendChild(inputId);
//    div.appendChild(inputName);
//    div.appendChild(inputSearch);
//    div.appendChild(inputQty);
//    div.appendChild(inputDelete);

    $('#cocktailIngredientUl').append(str);
};

// 재료 선택 버튼
function cocktailIngredientSearch(obj) {
//    obj.preventDefault();

    const parent = obj.parentNode;
    let idStr = parent.children[0].id;
    let sIdx = idStr.indexOf('[') + 1;
    let eIdx = idStr.indexOf(']');
    let ingredientBtnId = idStr.substring(sIdx, eIdx);
    console.log(ingredientBtnId);

    let popUrl = "/supporters/pop/ingredientPop" +"?id=" + ingredientBtnId;
    let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

    window.open(popUrl,"식재료 찾기",popOption);
};

// 재료 삭제
function cocktailIngredientRemove(obj) {
    const parent = obj.parentNode;
    const curId = parent.children[0].id;
    const sIdx = curId.indexOf('[') + 1;
    const eIdx = curId.indexOf(']');

    const cur = curId.substring(sIdx, eIdx) * 1;
    const cnt = document.getElementById("ingredientsCnt");

    for (let i = cur + 1, j = cur; i < cnt.value; i++, j++) {
        let node = document.getElementById("ingredients[" + i + "].id");
        node.id = "ingredients[" + j + "].id";
        node.name = "ingredients[" + j + "].id";

        node = node.nextElementSibling;
        node.dataset.id = "ingredients[" + j + "].id";
        node.dataset.name = "ingredients[" + j + "].name";

        node = node.nextElementSibling;
        node.id = "ingredients[" + j + "].name";
        node.name = "ingredients[" + j + "].name";

        node = node.nextElementSibling;
        node.id = "ingredients[" + j + "].quantity";
        node.name = "ingredients[" + j + "].quantity";
    }

    $(obj).parent().remove();
    cnt.value = cnt.value * 1 - 1;
};