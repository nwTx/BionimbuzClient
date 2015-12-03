package br.unb.cic.bionimbuz.rest.request;

import br.unb.cic.bionimbuz.model.UploadedFileInfo;

public class DeleteFileRequest extends BaseRequest {

    private UploadedFileInfo fileInfo;

    public DeleteFileRequest(UploadedFileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public UploadedFileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(UploadedFileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

}
