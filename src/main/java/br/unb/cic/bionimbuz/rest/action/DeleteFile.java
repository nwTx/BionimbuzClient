package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.unb.cic.bionimbuz.rest.request.DeleteFileRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.DeleteFileResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteFile extends Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFile.class);
    private static final String REST_DELETE_FILE_URL = "/rest/file/";
    private String fileId;
    private final String bionimbuzIP = config.getBionimbuzAddress();

    @Override
    public void setup(Client client, RequestInfo reqInfo) {
        this.target = client.target(config.getBionimbuzAddress());
        this.request = (DeleteFileRequest) reqInfo;
        this.fileId = ((DeleteFileRequest) request).getFileInfo().getId();
    }

    @Override
    public void prepareTarget() {
        // Path: /rest/file/{fileId}
        target = target.path(REST_DELETE_FILE_URL + this.fileId);
    }

    @Override
    public DeleteFileResponse execute() {
        logAction(REST_DELETE_FILE_URL, DeleteFile.class);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .delete();

        return new DeleteFileResponse(true);
    }

}
