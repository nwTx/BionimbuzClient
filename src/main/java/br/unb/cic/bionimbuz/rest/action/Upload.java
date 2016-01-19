package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.UploadResponse;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Upload extends Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(Upload.class);

    private static final String REST_UPLOAD_URL = "/rest/file/upload";

    @Override
    public void setup(Client client, RequestInfo requestInfo) {
        this.target = client.target(appConfiguration.getBionimbuzAddress());
        this.request = (UploadRequest) requestInfo;
    }

    @Override
    public void prepareTarget() {
        target = target.path(REST_UPLOAD_URL);
    }

    @Override
    public UploadResponse execute() {
        logAction(REST_UPLOAD_URL, Upload.class);
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

        LOGGER.info("Generated Hash: " + sb.toString());

        return sb.toString();
    }
}
