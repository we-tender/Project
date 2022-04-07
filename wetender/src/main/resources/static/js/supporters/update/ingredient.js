
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
            const targetLi = $(this).closest("li");
            targetLi.remove();
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
            url : "/supporters/upload/image/ingredient",
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
            str += "<img src='/supporters/display/ingredient/" + fileCellPath + "'></div></li>";
        });
        uploadUl.append(str);
    }

});

