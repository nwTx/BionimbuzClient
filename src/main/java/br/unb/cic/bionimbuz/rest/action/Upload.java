package br.unb.cic.bionimbuz.rest.action;

import br.unb.bionimbuz.storage.bucket.BioBucket;
import br.unb.bionimbuz.storage.bucket.CloudStorageMethods;
import br.unb.bionimbuz.storage.bucket.methods.CloudMethodsAmazonGoogle;
import br.unb.bionimbuz.storage.bucket.PeriodicCheckerBuckets;
import br.unb.cic.bionimbuz.model.FileInfo;
import javax.ws.rs.client.Client;

import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.UploadResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final String bionimbuzIP = config.getBionimbuzAddress();

    @Override
    public void setup(Client client, RequestInfo requestInfo) {
        this.request = (UploadRequest) requestInfo;
    }

    @Override
    public void prepareTarget() {
    }

    @Override
    public UploadResponse execute() {
        boolean returnFromServer = false;

        // Create HttpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        if (config.getStorageMode().equalsIgnoreCase("1")) { // Cloud Storage
            
            UploadRequest req = (UploadRequest) this.request;
            
            try {
                
                FileOutputStream fos = new FileOutputStream(config.getTemporaryWorkflowFolder() + "/" + req.getFileInfo().getName());
                fos.write(req.getFileInfo().getPayload());
                fos.close();
                
                BioBucket dest = PeriodicCheckerBuckets.getBestBucket(PeriodicCheckerBuckets.getBucketList());
                
                LOGGER.info("Uploading file to bucket: " + dest.getName());
                
                CloudStorageMethods methodsInstance = new CloudMethodsAmazonGoogle();
                
                methodsInstance.StorageAuth(dest.getProvider());
                methodsInstance.StorageUploadFile(dest, "/data-folder/", config.getTemporaryWorkflowFolder() + "/", req.getFileInfo().getName());
                
                File aux = new File (config.getTemporaryWorkflowFolder() + "/" + req.getFileInfo().getName());
                aux.delete();

                
                // Creates HttpPost with server address
                HttpPost httpPost = new HttpPost(bionimbuzIP + REST_UPLOAD_URL);

                // Set bucket
                
                req.getFileInfo().setBucket(dest.getName());
                
                // Set hash
                req.getFileInfo().setHash(generateHash(req.getFileInfo().getPayload()));

                // Avoid the payload to be sent twice (because it was already set as ByteArrayBody part)
                req.getFileInfo().setPayload(null);

                // Transforms the file metadata into JSON
                String jsonFileInfo = new ObjectMapper().writeValueAsString(req.getFileInfo());

                // Adds it as a part of the request
                StringBody fileInfoBody = new StringBody(jsonFileInfo, ContentType.APPLICATION_JSON);

                // Create the request with the two parts: Metadata and the file as byte[]
                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("file_info", fileInfoBody)
                        .build();

                // Adds it to the httpPost object
                httpPost.setEntity(reqEntity);

                LOGGER.info("Sending Upload request (fileId=" + req.getFileInfo().getId() + ") to BioNimbuZ (path: " + REST_UPLOAD_URL);

                // Creates the response and fires the post request
                CloseableHttpResponse response = httpclient.execute(httpPost);

                try {
                    HttpEntity resEntity = response.getEntity();

                    if (resEntity != null) {
                        LOGGER.info("Upload request got " + response.getStatusLine() + " response");

                        // Verifies response status code
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
                } finally {
                    response.close();
                }

                
            } catch (Throwable t) {
                LOGGER.error("Exception caught: " + t.getMessage());
                t.printStackTrace();
            }
            
            
            
        } else { // Old Storage 
            try {

                // Creates HttpPost with server address
                HttpPost httpPost = new HttpPost(bionimbuzIP + REST_UPLOAD_URL);
                UploadRequest req = (UploadRequest) this.request;

                // Add a part as the file (bute[])
                ByteArrayBody file = new ByteArrayBody(req.getFileInfo().getPayload(), req.getFileInfo().getName());

                // Set hash
                req.getFileInfo().setHash(generateHash(req.getFileInfo().getPayload()));

                // Avoid the payload to be sent twice (because it was already set as ByteArrayBody part)
                req.getFileInfo().setPayload(null);

                // Transforms the file metadata into JSON
                String jsonFileInfo = new ObjectMapper().writeValueAsString(req.getFileInfo());

                // Adds it as a part of the request
                StringBody fileInfoBody = new StringBody(jsonFileInfo, ContentType.APPLICATION_JSON);

                // Create the request with the two parts: Metadata and the file as byte[]
                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("file", file)
                        .addPart("file_info", fileInfoBody)
                        .build();

                // Adds it to the httpPost object
                httpPost.setEntity(reqEntity);

                LOGGER.info("Sending Upload request (fileId=" + req.getFileInfo().getSize() + ") to BioNimbuZ (path: " + REST_UPLOAD_URL);

                // Creates the response and fires the post request
                CloseableHttpResponse response = httpclient.execute(httpPost);

                try {
                    HttpEntity resEntity = response.getEntity();

                    if (resEntity != null) {
                        LOGGER.info("Upload request got " + response.getStatusLine() + " response");

                        // Verifies response status code
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
                } finally {
                    response.close();
                }
            } catch (IOException ex) {
                LOGGER.error("[IOException] " + ex.getMessage());

                returnFromServer = false;
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    LOGGER.error("[IOException] " + ex.getMessage());

                    returnFromServer = false;
                }
            }
        }

        /*
        MultipartFormDataOutput multipart = new MultipartFormDataOutput();

        // Generate Hash
        ((UploadRequest) request).getUploadedFileInfo().setHash(generateHash(((UploadRequest) request).getUploadedFileInfo().getPayload()));

        multipart.addFormData("file", ((UploadRequest) request).getUploadedFileInfo().getPayload(), MediaType.MULTIPART_FORM_DATA_TYPE);
        multipart.addFormData("file_info", ((UploadRequest) request).getUploadedFileInfo(), MediaType.APPLICATION_JSON_TYPE);

        GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(multipart) {
        };

        Response response = target
                .request()
                .post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE), Response.class);
         
        return new UploadResponse(response.readEntity(boolean.class));
         */
        return new UploadResponse(returnFromServer);
    }

    /**
     * Generate SHA-3 from the input file
     *
     * @param inputFile
     * @return
     */
    private String generateHash(byte[] inputFile) {

        SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
        md.update(inputFile);

        byte[] mdbytes = md.digest();

        //Convert the byte to hex format
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
