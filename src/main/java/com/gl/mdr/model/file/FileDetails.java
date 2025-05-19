package com.gl.mdr.model.file;

/**
 * @author Ravi
 *
 */
public class FileDetails {
	private String fileName;
	private String filePath;
	private String url;
	public FileDetails( String fileName, String filePath, String url ) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "FileDetails [fileName=" + fileName + ", filePath=" + filePath + ", url=" + url + "]";
	}
	
}
