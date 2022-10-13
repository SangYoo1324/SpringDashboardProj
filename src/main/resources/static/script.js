console.log("자바스크립트 실행");
//댓글 생성 버튼
const commentCreateBtn = document.querySelector("#comment-create-btn");
// 버튼 클릭 이벤트 감지
commentCreateBtn.addEventListener("click",()=>{
//새 댓글 객체 생성
const comment = {
nickname:document.querySelector("#new-comment-nickname").value,
body:document.querySelector("#new-comment-body").value,
articleId:document.querySelector("#new-comment-articleId").value
};
//댓글 객체 출력
console.log(comment);

//Fetch RestAPI 리소스 보내준다
const url = "/api/articles/"+comment.articleId+"/comments";
fetch(url,{
method: "POST",
body: JSON.stringify(comment), //comment 자바스크립트 객체를 JSON으로 변경하여 보냄
headers:{
"Content-Type": "application/json"
}
}).then(response =>{
//http응답 코드에 따른 메세지 출력
const msg = (response.ok)? "comment posted":"Something went wrong";
alert(msg);
//페이지 새로고침
window.location.reload();
});

});


//Modal 이벤트 처리

//모달 요소 선택(modal 창 자체)  edit 버튼이 아님
const commentsEditModal = document.querySelector("#comment-edit-modal");

commentsEditModal.addEventListener('show.bs.modal',(e)=>{
//e = show.bs.modal이란 이벤트
console.log("modal이 열렸습니다");

//트리거 버튼 선택(modal 이 열리는 이벤트를 트리거한 객체(버튼))
//edit 버튼
const triggerBtn = e.relatedTarget;

//데이터 가져오기
console.log(triggerBtn);
const id  =  triggerBtn.getAttribute("data-bs-id");
const nickname  =  triggerBtn.getAttribute("data-bs-nickname");
const body =  triggerBtn.getAttribute("data-bs-body");
const articleId  =  triggerBtn.getAttribute("data-bs-articleId");
console.log(id+","+nickname);
//데이터를 반영
document.querySelector("#edit-comment-id").value = id;
document.querySelector("#edit-comment-nickname").value = nickname;
document.querySelector("#edit-comment-body").value = body;
document.querySelector("#edit-comment-articleId").value = articleId;



});

//수정 완료 버튼
const commentsUpdateBtn= document.querySelector("#comment-update-btn");

commentsUpdateBtn.addEventListener("click",()=>{
//수정 댓글 객체 생성
const comment ={
id: document.querySelector("#edit-comment-id").value,
nickname: document.querySelector("#edit-comment-nickname").value,
body: document.querySelector("#edit-comment-body").value,
articleId: document.querySelector("#edit-comment-articleId").value
};
console.log(comment);
//수정 RestAPI 호출 -fetch
const url = "/api/comments/"+comment.id;
fetch(url,{
method: "PATCH",
body: JSON.stringify(comment),
headers: {
"Content-Type" : "application/json"
}
}).then(response=>{
const msg = (response.ok)? "Comment edited" : "Something went wrong";
alert(msg);
window.location.reload();
});

});


//댓글 삭제
const commentsDeleteBtns = document.querySelectorAll(".delete-btn");
commentsDeleteBtns.forEach(btn=>btn.addEventListener("click",(e)=>{
console.log("삭제 버튼이 클릭되었습니다");
//이벤트 발생한 버튼을 가져옴
const commentsDeleteBtn = e.target;
console.log(commentsDeleteBtn);
//삭제 댓글 id 가져오기
const commentId = commentsDeleteBtn.getAttribute("data-comments-id");
console.log(commentId+"번 댓글");
//삭제 api 호출
const url = `/api/comments/${commentId}`;

fetch(url, {
method:"DELETE",
}).then(response=>{
if(!response.ok){
alert("댓글 삭제 실패");
return;
}
const deleteTarget = document.querySelector(`#comments-${commentId}`);
deleteTarget.remove(); // html요소 지움

})
}));
