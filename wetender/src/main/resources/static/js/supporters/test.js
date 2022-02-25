let sequences = "[[${form.sequences}]]";

function addSequenceInput(event){
    const sequenceDiv = document.getElementById("sequence");

    const li = document.createElement("li");

    const input = document.createElement("input");
    input.type = Text;
    input.placeholder = "순서";

    const removeButton = document.createElement("button");
    removeButton.innerText = "삭제";
    removeButton.addEventListener("click", deleteSeqeunce);
    li.appendChild(input);
    li.appendChild(removeButton);
    sequenceDiv.appendChild(li);
}

function deleteSeqeunce(event){
    const li = event.target.parentElement;
    li.remove();
}

const sequenceAddBtn = document.getElementById("sequenceAddBtn");
sequenceAddBtn.addEventListener("click",addSequenceInput);