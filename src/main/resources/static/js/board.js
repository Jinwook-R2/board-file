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
	
	// 파일 삭제 버튼 클릭 이벤트
	$(".fileDelete").on("click", function(){
		
		const fileNo = $(this).attr("data")
		
		if(!confirm("정말로 삭제하시겠습니까?")) return;
		
		$.ajax({
			url  :  "/file",
			type : "delete",
			data : {"fileNo": fileNo},
			// 정상적으로 응답 성공 시, 실행되는 함수
			success : function(res) {
				alert(res);
				
				if(res === 'success')
					location.reload();
					
				if(res === 'fail')
					alert('삭제에 실패했습니다');
					
			},
			error: function() {
				alert("파일 삭제 시, 문제가 발생하였습니다.");
			},
		})
		
	});
	
})


