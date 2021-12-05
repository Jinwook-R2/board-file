package aloha.domain;

import java.util.Date;
import lombok.Data;

@Data
public class FileInfo {
	
	private int fileNo;
	private String fileName;
	private String fullName;
	private int refNo;
	private Date regDate;
	
	
	
}
