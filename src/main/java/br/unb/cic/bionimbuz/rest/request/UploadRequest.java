package br.unb.cic.bionimbuz.rest.request;

import br.unb.cic.bionimbuz.model.UploadedFileInfo;

public class UploadRequest implements RequestInfo {
	private UploadedFileInfo fileInfo;

	public UploadRequest() {
	}

	public UploadRequest(UploadedFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public UploadedFileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(UploadedFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

}
