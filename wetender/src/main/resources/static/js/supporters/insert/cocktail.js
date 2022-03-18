// HTML 요소의 동적 제어 기능
$(document).ready(function(){

    $("button[type='submit']").on("click", function(e){
        e.preventDefault();
        console.log("submit clicked");

        let formObj = $("form[role='form']");
        let str = "";
        console.dir(formObj);
        $(".uploadResult ul li").each(function(i, obj){
            const jobj = $(obj);
            console.dir(obj);
            str += "<input type='hidden' name='attachList[" + i +"].fileName' ";
            str += "value='" + jobj.data("filename") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uuid' ";
            str += "value='" + jobj.data("uuid") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uploadPath' ";
            str += "value='" + jobj.data("path") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].fileType' ";
            str += "value='" + jobj.data("type") + "'>";
        });
        formObj.append(str).submit();
    });

    // 파일 검증
    const regex = new RegExp("(.*?)\.(jpg|png)$");
    var maxSize = 524880; // 5MB
    function checkExtension(fileName, fileSize){
        if(fileSize >= maxSize){
            alert("파일 크기 초과");
            return false;
        }
        // 이미지 파일이 아니면
        if(regex.test(fileName) === false){
            alert("jpg 와 png 만 올릴 수 있습니다.");
            return false;
        }
        return true;
    }

    // 파일 변경 감지
    $("input[type='file']").change(function(e){
        console.log(e);
        const formData = new FormData();
        const inputFile = $("input[name='uploadFile']");
        const files = inputFile[0].files;
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
    });

    // 이미지 보여주기
    function showUploadResult(uploadResultArr){
        if(!uploadResultArr || uploadResultArr.length ==0){
            return;
        }

        const uploadUl = $(".uploadResult ul");
        var str = "";

        $(uploadResultArr).each(function(i, obj){
            //image type
            const fileCellPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
            str += "<li data-path='" + obj.uploadPath + "' data-uuid='" +obj.uuid + "'";
            str += " data-filename='" +obj.fileName + "' data-type='" +obj.fileType +"'><div>";
            str += "<span>" + obj.fileName + "</span>";
            str += "<button type='button' data-file= '" + fileCellPath + "' data-type='image'";
            str += " class='btn btn-warning btn-circle'>";
            str += "<i class=fa fa-time'></i></button><br>";
            str += "<img src='/supporters/display/cocktail?fileName=" + fileCellPath + "'></div></li>";
        });
        uploadUl.append(str);
    }

    // 이미지 삭제

    $(".uploadResult").on("click","button", function(e){
        console.log("delete file");
        const targetFile = $(this).data("file");
        const type = $(this).data("type");
        const targetLi = $(this).closest("li");
        if(confirm("Remove this file ?")){
            $.ajax({
                url : "/supporters/deleteFile",
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

    let liquorIngredientCnt = 1;
    let cocktailIngredientCnt = 1;

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
        
        const div = document.createElement("div");
        const br = document.createElement("br");

        const inputId = document.createElement("input");
        inputId.id = liquorIngredientCid;
        inputId.type = "hidden";
        inputId.name = "liquorIngredientId";
        inputId.className = "liquorIngredientId";

        const inputContent = document.createElement("input");
        inputContent.id = liquorIngredientCname;
        inputContent.name = liquorIngredientCname;
        inputContent.className = "liquorIngredientContent";
        inputContent.type = "text";
        inputContent.readOnly = true;

        const inputSearch = document.createElement("input");
        inputSearch.name = "liquorIngredientSearch";
        inputSearch.className = "liquorIngredientSearch";
        inputSearch.dataset.id = liquorIngredientCid;
        inputSearch.dataset.name = liquorIngredientCname;
        inputSearch.value = "주류 선택";
        inputSearch.type = "button";

        const inputQty = document.createElement("input");
        inputQty.type = "text";
        inputQty.name = "liquorIngredientQty";
        inputQty.id = liquorIngredientQty;
        inputQty.placeholder = "재료 양";
        inputQty.className = "liquorIngredientQty";

        const inputDelete = document.createElement("input");
        inputDelete.type = "button";
        inputDelete.className = "liquorIngredientBtnRemove";
        inputDelete.name = liquorIngredientCdel;
        inputDelete.id = liquorIngredientCdel;
        inputDelete.value = "삭제";

        div.appendChild(inputId);
        div.appendChild(inputContent);
        div.appendChild(inputSearch);
        div.appendChild(inputQty);
        div.appendChild(inputDelete);
        div.appendChild(br);

        $('#liquorIngredient').append(div);

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
        let cocktailIngredientCdel = 'cocktailIngredientDel'+ cocktailIngredientCnt;

        const div = document.createElement("div");
        const br = document.createElement("br");

        const inputId = document.createElement("input");
        inputId.id = cocktailIngredientCid;
        inputId.type = "hidden";
        inputId.name = "cocktailIngredientId";
        inputId.className = "cocktailIngredientId";

        const inputContent = document.createElement("input");
        inputContent.id = cocktailIngredientCname;
        inputContent.name = cocktailIngredientCname;
        inputContent.className = "cocktailIngredientContent";
        inputContent.type = "text";
        inputContent.readOnly = true;

        const inputSearch = document.createElement("input");
        inputSearch.name = "cocktailIngredientSearch";
        inputSearch.className = "cocktailIngredientSearch";
        inputSearch.dataset.id = cocktailIngredientCid;
        inputSearch.dataset.name = cocktailIngredientCname;
        inputSearch.value = "재료 선택";
        inputSearch.type = "button";

        const inputQty = document.createElement("input");
        inputQty.type = "text";
        inputQty.name = "cocktailIngredientQty";
        inputQty.id = cocktailIngredientQty;
        inputQty.placeholder = "재료 양";
        inputQty.className = "cocktailIngredientQty";

        const inputDelete = document.createElement("input");
        inputDelete.type = "button";
        inputDelete.className = "cocktailIngredientBtnRemove";
        inputDelete.name = cocktailIngredientCdel;
        inputDelete.id = cocktailIngredientCdel;
        inputDelete.value = "삭제";

        div.appendChild(inputId);
        div.appendChild(inputContent);
        div.appendChild(inputSearch);
        div.appendChild(inputQty);
        div.appendChild(inputDelete);
        div.appendChild(br);

        $('#cocktailIngredient').append(div);

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

