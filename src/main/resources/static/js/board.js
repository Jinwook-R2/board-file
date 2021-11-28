/**
 * 
 */

$(function() {
	
	let form = $("#board");
	let boardNo = $("#boardNo").val();
	
			
	// 등록 버튼 클릭 이벤트
	$("#btnInsert").on("click", function() {
		form.attr("action", "/board/insert");
		form.attr("method", "post");
		form.submit(); 
	});
	
	$("#btnUpdateMove").on("click", function() {
		self.location = "/board/update?boardNo=" + boardNo;
	});
	
	// 수정 버튼 클릭 이벤트
	$("#btnUpdate").on("click", function() {
		form.attr("action", "/board/update");
		form.attr("method", "post");
		form.submit(); 
	});
	
	// 삭제 버튼 클릭 이벤트
	$("#btnDelete").on("click", function() {
		form.attr("action", "/board/delete");
		form.attr("method", "post");
		form.submit(); 
	});
	
	// 목록 버튼 클릭 이벤트
	$("#btnList").on("click", function() {
		self.location = "/board/list";
	});

})