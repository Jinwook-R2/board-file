// 이미지 타입 확인
function checkImageType(fileName) {
	
	const pattern = /jpg|gif|png|jpeg/i;
	
	return fileName.match(pattern);
}

// 첨부파일 가져오기
function getAttachFiles( boardNo ) {
	
	$.getJSON("/board/files?boardNo=" + boardNo, function(list){
		
		$(list).each(function(){
			
			const item = this;
			const fileNo = item.fileNo;
			const fileName = item.fileName;
			let tag = "";
			
			if( checkImageType(fileName)) {
				// 이미지 타입일 경우
				
				tag = "<div><a href='/file/img?fileNo='+fileNo + '>'" + "<img src='/file/img?fileNo="+ fileNo + "'width='200' />" + "</a></div>";	
				
			} else {
				tag = "<div><a href='/file?fileNo=" + fileNo + "'>"
				    + fileName + "</a>"
					+ "</div>";
			}
			$(".attachList").append(tag);
		})		
	})

}
