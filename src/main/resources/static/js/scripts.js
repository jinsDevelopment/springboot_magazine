'use strict';

function boardWrite() {
  let writer = $('#writer').val()
  let title = $('#title').val()
  let contents = $('#contents').val()

  if(!isValidContents(title, contents, writer)){
    return false;
  }

  let data = {
    "writer": writer,
    "title": title,
    "contents": contents
  }

  $.ajax({
    type: "POST",
    url: "/api/board/write", // 요청할 주소
    data: JSON.stringify(data)
    , // 여기에 json을 넣어주면 된다!
    dataType: "json",
    contentType: "application/json",
    success: function (res) {
      if (res['res'] == "success") {
        alert("저장이 완료되었습니다.");
        window.location.href = "/";
      }
    }
  });

}

function boardUpdate(id){
  let writer = $('#writer').val()
  let title = $('#title').val()
  let contents = $('#contents').val()

  if(!isValidContents(title, contents, writer)){
    return false;
  }

  let data = {
    "writer": writer,
    "title": title,
    "contents": contents
  }

  $.ajax({
    type: "PUT",
    url: "/api/board/update/"+id, // 요청할 주소
    data: JSON.stringify(data)
    , // 여기에 json을 넣어주면 된다!
    dataType: "json",
    contentType: "application/json",
    success: function (res) {
      if (res['res'] == "success") {
        alert("수정이 완료되었습니다.");
        window.location.href = "/api/board/" + id;
      }
    }
  });

}

function isValidContents(title, contents, writer) {
  if (title == '') {
    alert('제목을 입력해주세요');
    return false;
  }

  if (writer == '') {
    alert('작성자를 입력해 주세요');
    return false;
  }

  if (contents == '') {
    alert('댓글을 입력해주세요');
    return false;
  }

  if (contents.trim().length > 140) {
    alert('공백 포함 140자 이하로 입력해주세요');
    return false;
  }

  return true;
}

function boardDetailForm(id) {
  window.location.href = "/api/board/" + id;
}
//게시글 수정
function boardUpdateForm(id) {
  window.location.href = "/api/board/updateForm/" + id;
}
//게시글 삭제
function boardDelete(id) {

  $.ajax({
    type: "DELETE",
    url: "/api/board/delete/" + id, // 요청할 주소
    dataType: "json",
    contentType: "application/json",
    success: function (res) {

      alert("정상적으로 삭제되었습니다.");
      window.location.href = "/";

    }
  });
}
