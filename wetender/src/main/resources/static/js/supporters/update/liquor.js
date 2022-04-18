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
            console.log(jobj);
            //const jobj = $(obj);
            str += "<input type='hidden' name='attachList[" + i +"].fileName' ";
            str += "value='" + jobj.data("filename") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uuid' ";
            str += "value='" + jobj.data("uuid") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].uploadPath' ";
            str += "value='" + jobj.data("path") + "'>";
            str += "<input type='hidden' name='attachList[" + i +"].fileType' ";
            str += "value='" + jobj.data("type") + "'>";
        };
        formObj.append(str).submit();
    });

    // 이미지 삭제
    $(".uploadResult").on("click","button", function(e){
            console.log("delete file");
            const targetFile = $(this).data("file");
            const type = $(this).data("type");
            const targetLi = $(this).closest("li");
            if(confirm("Remove this file ?")){
                $.ajax({
                    url : "/supporters/deleteLiquorFile",
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
    //                    var uploadNum = $('#uploadNum').val();
    //                    uploadNum *= 1; // String -> int 변환
    //                    uploadNum -= 1;
    //                    $('#uploadNum').val(uploadNum);
    //                    $('.upload-state').val("파일 " + uploadNum + "개 선택");
                    }
                });
            }
        });

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
        str += "<img src='/supporters/display/liquor/" + fileCellPath + "'></figure>";
        str += "</li>";
    });
    // 파일등록 버튼 uploadResult li 마지막에 만들어두기
    $(".input-file").parent().remove();
    str += "<li class='contents-flex flex-row-center'>";
    str += "<div class='input-file display-flex flex-row-center-center'>";
    str += "<label for='uploadFile' class='btn-add'><div></div></label>";
    str += "<input type='file' id='uploadFile' name='uploadFile' onchange='inputChange(this)' multiple></div>";
    str += "</li>";
    uploadUl.append(str);
}

function inputChange(obj) {
    console.log(obj);
    const formData = new FormData();
    const files = obj.files;
    console.log(files);
    for(let i = 0; i < files.length; i++){
        if(!checkExtension(files[i].name, files[i].size)){
            return false;
        }
        formData.append("uploadFile",files[i]);
    }

    $.ajax({
        url : "/supporters/upload/image/liquor",
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
}

