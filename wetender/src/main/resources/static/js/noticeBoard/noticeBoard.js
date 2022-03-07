




function block() {
    document.getElementById("Divid").style.display="none";
}
function display() {
    document.getElementById("Divid").style.display="block";
}


function setDisplay(){
    if($('input:radio[id="aaa"]').is(':checked')){
        $('#divId').hide();
    }else{
        $('#divId').show();
    }
}



