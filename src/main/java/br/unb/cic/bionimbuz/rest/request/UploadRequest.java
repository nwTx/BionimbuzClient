package br.unb.cic.bionimbuz.rest.request;

import br.unb.cic.bionimbuz.model.FileInfo;

public class UploadRequest implements RequestInfo {

    private FileInfo uploadedFileInfo;

    public UploadRequest() {
    }

    public UploadRequest(FileInfo fileInfo) {
        this.uploadedFileInfo = fileInfo;
    }

    public FileInfo getUploadedFileInfo() {
        return uploadedFileInfo;
    }

    public void setUploadedFileInfo(FileInfo uploadedFileInfo) {
        this.uploadedFileInfo = uploadedFileInfo;
    }

}
