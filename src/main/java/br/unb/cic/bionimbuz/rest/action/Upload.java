package br.unb.cic.bionimbuz.rest.action;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.UploadResponse;
import br.unb.cic.bionimbuz.security.HashUtil;

/**
 * Since there is a bug in Resteasy upload method (documented here
 * https://issues.jboss.org/browse/RESTEASY-1201), the implementation of this
 * Action is made with Apache HttpClient.
 *
 * @author Vinicius (with Edrward's help)
 */
public class Upload extends Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(Upload.class);
    private static final String REST_UPLOAD_URL = "/rest/file/upload";
    private final String bionimbuzIP = this.config.getBionimbuzAddress();

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // br.unb.cic.bionimbuz.rest.action.Action#setup(javax.ws.rs.client.Client,
    // br.unb.cic.bionimbuz.rest.request.RequestInfo)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void setup(Client client, RequestInfo requestInfo) {
        this.request = requestInfo;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see br.unb.cic.bionimbuz.rest.action.Action#prepareTarget()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void prepareTarget() {
        // Nothing to do
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see br.unb.cic.bionimbuz.rest.action.Action#execute()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public UploadResponse execute() {
        boolean returnFromServer = false;
        final UploadRequest req = (UploadRequest) this.request;
        final HttpPost httpPost = new HttpPost(this.bionimbuzIP + REST_UPLOAD_URL);
        try (
             // Create HttpClient
             final CloseableHttpClient httpclient = HttpClients.createDefault();
             final InputStream inputStream = req.getFileInfo().getInputStream();) {
            // Add a part as the file (bute[])
            final InputStreamBody file = new InputStreamBody(inputStream, req.getFileInfo().getName());
            // Set hash
            req.getFileInfo().setHash(HashUtil.calculateSha3(inputStream));
            // Transforms the file metadata into JSON
            final String jsonFileInfo = new ObjectMapper().writeValueAsString(req.getFileInfo());
            // Adds it as a part of the request
            final StringBody fileInfoBody = new StringBody(jsonFileInfo, ContentType.APPLICATION_JSON);
            // Create the request with Metadata and the file
            final HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", file).addPart("file_info", fileInfoBody).build();
            // Adds it to the httpPost object
            httpPost.setEntity(reqEntity);
            LOGGER.info("Sending Upload request (fileId=" + req.getFileInfo().getSize() + ") to BioNimbuZ (path: " + REST_UPLOAD_URL);
            try (
                 final CloseableHttpResponse response = httpclient.execute(httpPost);) {
                final HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    LOGGER.info("Upload request got " + response.getStatusLine() + " response");
                    if (response.getStatusLine().getStatusCode() == 200) {
                        returnFromServer = true;
                    } else {
                        LOGGER.error("Response code from server is different of HTTP 200");
                        returnFromServer = false;
                    }
                } else {
                    LOGGER.error("Response from Server is null");
                }
                EntityUtils.consume(resEntity);
            }
        } catch (final IOException ex) {
            LOGGER.error("[IOException] " + ex.getMessage());
            returnFromServer = false;
        }
        return new UploadResponse(returnFromServer);
    }
}
