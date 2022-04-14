
// HTML 요소의 동적 제어 기능
$(document).ready(function(){

    $("button[type='submit']").on("click", function(e){
        e.preventDefault();
        let formObj = $("form[role='form']");
        let str = "";
        console.dir(formObj);
        $(".uploadResult ul li").each(function(i, obj){
            const jobj = $(obj);
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
    const regex = new RegExp("(.*?)\.(jpg|png|jpeg)$");
    var maxSize = 524880; // 5MB
    function checkExtension(fileName, fileSize){
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

    function showUploadResult(uploadResultArr){
        if(!uploadResultArr || uploadResultArr.length ==0){
            return;
        }

        const uploadUl = $(".uploadResult ul");
        var str = "";

        $(uploadResultArr).each(function(i, obj){
            //image type
            const fileCellPath = obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName;
            str += "<li data-path='" + obj.uploadPath + "' data-uuid='" +obj.uuid + "'";
            str += " data-filename='" +obj.fileName + "' data-type='" +obj.fileType +"'><div>";
            str += "<span>" + obj.fileName + "</span>";
            str += "<button type='button' data-file= '" + fileCellPath + "' data-type='image'";
            str += " class='btn btn-warning btn-circle'>";
            str += "<i class=fa fa-time'></i></button><br>";
            str += "<img src='/supporters/display/cocktail/" + fileCellPath + "'></div></li>";
        });
        uploadUl.append(str);
    }

    let cocktailLiquorCnt = Number($('input[name="liquorsCnt"]').val());
    let cocktailIngredientCnt = Number($('input[name="ingredientsCnt"]').val());
    let sequencesCnt = Number($('input[name="sequencesCnt"]').val());


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

           sequencesCnt = sequencesCnt + 1;

           const div = document.createElement("div");
           const br = document.createElement("br");

           const sequenceContent = document.createElement("input");
           sequenceContent.type = "text";
           sequenceContent.name = "sequences[" + sequencesCnt + "].content";

           const sequenceDelete = document.createElement("input");
           sequenceDelete.type = "button";
           sequenceDelete.className = "sequenceBtnRemove";
           sequenceDelete.value = "삭제";

           div.appendChild(sequenceContent);
           div.appendChild(sequenceDelete);
           div.appendChild(br);

           $('#sequenceDiv').append(div);

           $('.sequenceBtnRemove').click(function(){
               $(this).parent("div").remove();
           });
       });

       // 주류 추가
       $('.cocktailLiquorBtnAdd').click(function(){

           cocktailLiquorCnt = cocktailLiquorCnt +1;

           let cocktailLiquorId = "liquors[" + cocktailLiquorCnt + "].id";
           let cocktailLiquorName = "liquors[" + cocktailLiquorCnt + "].name";
           let cocktailLiquorQty = "liquors[" + cocktailLiquorCnt + "].quantity";

           const div = document.createElement("div");
           const br = document.createElement("br");

           const inputId = document.createElement("input");
           inputId.id = cocktailLiquorId;
           inputId.type = "hidden";
           inputId.name = cocktailLiquorId;

           const inputName = document.createElement("input");
           inputName.id = cocktailLiquorName;
           inputName.name = cocktailLiquorName;
           inputName.type = "text";
           inputName.readOnly = true;

           const inputSearch = document.createElement("input");
           inputSearch.className = "cocktailLiquorSearch";
           inputSearch.dataset.id = cocktailLiquorCnt;
           inputSearch.value = "주류 선택";
           inputSearch.type = "button";

           const inputQty = document.createElement("input");
           inputQty.type = "text";
           inputQty.name = cocktailLiquorQty;
           inputQty.placeholder = "재료 양";

           const inputDelete = document.createElement("input");
           inputDelete.type = "button";
           inputDelete.className = "cocktailLiquorBtnRemove";
           inputDelete.value = "삭제";

           div.appendChild(inputId);
           div.appendChild(inputName);
           div.appendChild(inputSearch);
           div.appendChild(inputQty);
           div.appendChild(inputDelete);
           div.appendChild(br);

           $('#cocktailLiquor').append(div);

           // HTML 요소 삭제
           $('.cocktailLiquorBtnRemove').click(function(){
               $(this).parent("div").remove();
           });

           /* 주류 선택 버튼 */
           $('.cocktailLiquorSearch').click(function(e){

               e.preventDefault();

               let liquorBtnId = $(this).data('id');
               let popUrl = "/supporters/pop/liquorPop" +"?id=" + liquorBtnId;
               let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

               window.open(popUrl,"주류 찾기",popOption);

           });
       });

       // 재료 추가
       $('.cocktailIngredientBtnAdd').click(function(){

           cocktailIngredientCnt = cocktailIngredientCnt + 1;

           let cocktailIngredientId = "ingredients[" + cocktailIngredientCnt + "].id";
           let cocktailIngredientName = "ingredients[" + cocktailIngredientCnt + "].name";
           let cocktailIngredientQty = "ingredients[" + cocktailIngredientCnt + "].quantity";

           const div = document.createElement("div");
           const br = document.createElement("br");

           const inputId = document.createElement("input");
           inputId.id = cocktailIngredientId;
           inputId.type = "hidden";
           inputId.name = cocktailIngredientId;

           const inputName = document.createElement("input");
           inputName.id = cocktailIngredientName;
           inputName.name = cocktailIngredientName;
           inputName.type = "text";
           inputName.readOnly = true;

           const inputSearch = document.createElement("input");
           inputSearch.className = "cocktailIngredientSearch";
           inputSearch.dataset.id = cocktailIngredientCnt;
           inputSearch.value = "재료 선택";
           inputSearch.type = "button";

           const inputQty = document.createElement("input");
           inputQty.type = "text";
           inputQty.name = cocktailIngredientQty;
           inputQty.placeholder = "재료 양";

           const inputDelete = document.createElement("input");
           inputDelete.type = "button";
           inputDelete.className = "cocktailIngredientBtnRemove";
           inputDelete.value = "삭제";

           div.appendChild(inputId);
           div.appendChild(inputName);
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
               let popUrl = "/supporters/pop/ingredientPop" +"?id=" + ingredientBtnId;
               let popOption = "width = 650px, height=550px, top=300px, left=300px, scrollbars=yes";

               window.open(popUrl,"식재료 찾기",popOption);

           });
       });
});

