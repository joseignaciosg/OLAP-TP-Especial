package olap.olap.project.web.command;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public class UploadXmlForm {


	
	private MultipartFile file;

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	
	
	
}
