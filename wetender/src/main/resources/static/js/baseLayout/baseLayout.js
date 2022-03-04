const bars = document.querySelectorAll(".nav-item");
const clickedClass = "active";

console.log(bars);

bars.forEach((bar) => {
    console.log(bar);
    bar.addEventListener("click",activeToggle);
});

function activeToggle(click){
    console.log(click);
    bars.forEach((bar) =>{
        activeRemove(bar);
    });
    click.target.classList.add(clickedClass); // 해당 클래스 이름 추가하기
}

function activeRemove(bar){
    console.log(bar);
    if(bar.classList.contains(clickedClass)){ //class내에 있는지 확인 boolean타입 반환
        bar.classList.remove(clickedClass); // 해당 클래스 이름 지우기
    }
}