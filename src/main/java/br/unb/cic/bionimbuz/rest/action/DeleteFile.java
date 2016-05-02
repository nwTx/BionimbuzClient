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

//        boolean returnFromServer = false;
//        
//        try {
//            DeleteFileRequest req = (DeleteFileRequest) this.request;
//
//            // Create HttpClient
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//
//            // Creates HttpPost with server address
//            HttpPost httpPost = new HttpPost(bionimbuzIP + REST_DELETE_FILE_URL);
//
//            req.getFileInfo().setPayload(null);
//
//            // Transforms the file metadata into JSON
//            String jsonFileInfo = new ObjectMapper().writeValueAsString(req.getFileInfo());
//
//            // Adds it as a part of the request
//            StringBody fileInfoBody = new StringBody(jsonFileInfo, ContentType.APPLICATION_JSON);
//
//            // Create the request with the two parts: Metadata and the file as byte[]
//            HttpEntity reqEntity = MultipartEntityBuilder.create()
//                    .addPart("file_info", fileInfoBody)
//                    .build();
//            
//            // Adds it to the httpPost object
//            httpPost.setEntity(reqEntity);
//
//            LOGGER.info("Sending DeleteFile request (fileId=" + req.getFileInfo().getId() + ") to BioNimbuZ (path: " + REST_DELETE_FILE_URL);
//
//            httpclient.execute(httpPost);
//
//            returnFromServer = true;
//
//        } catch (Throwable t) {
//            LOGGER.error("Exception caught: " + t.getMessage());
//            t.printStackTrace();
//        }
        
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .delete();

        return new DeleteFileResponse(true);
    }

}
