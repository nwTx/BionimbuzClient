package br.unb.cic.bionimbuz.web.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import br.unb.cic.bionimbuz.model.UploadedFileInfo;
import br.unb.cic.bionimbuz.rest.service.RestService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class FileUploadBean implements Serializable {

    private static final String MAX_STORAGE = "256 Mb";
    private static final Long MAX_STORAGE_SIZE = 268435456l;    // 256 Mb

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadBean.class);

    private static final long serialVersionUID = 1L;
    private final RestService restService;
    private OutputStream outputStream;
    private int storageUsage;

    @Inject
    private SessionBean sessionBean;

    public FileUploadBean() {
        restService = new RestService();
        outputStream = null;
    }

    /**
     * Triggered when an user uploads a file
     *
     * @param event
     */
    public void handleUploadedFile(FileUploadEvent event) throws IOException {
        // Verifies if the file doesn't overflow the user storage usage
        if ((sessionBean.getLoggedUser().getStorageUsage() + event.getFile().getSize()) > MAX_STORAGE_SIZE) {
            showFacesMessage(FacesMessage.SEVERITY_ERROR, "Seu espaco de armazenamento ultrapassou " + MAX_STORAGE + "!");

            return;
        }

        // Verifies if it wasn't uploaded previously
        for (UploadedFileInfo u : sessionBean.getLoggedUser().getFiles()) {
            if (event.getFile().getFileName().equals(u.getName())) {
                showFacesMessage(FacesMessage.SEVERITY_ERROR, "Arquivo existente!");
                return;
            }
        }

        try {
            // Creates a new FileInfo
            UploadedFileInfo fileInfo = new UploadedFileInfo();

            // Set file information
            fileInfo.setName(event.getFile().getFileName());
            fileInfo.setSize(event.getFile().getSize());
            fileInfo.setUploadTimestamp(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            fileInfo.setUserId(sessionBean.getLoggedUser().getId());

            InputStreamReader isr = new InputStreamReader(event.getFile().getInputstream());
            BufferedReader in = new BufferedReader(isr);

            StringBuilder builder = new StringBuilder();

//            String line = in.readLine();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            fileInfo.setPayload(builder.toString().getBytes());

            // Saves temporary file in disk
            //saveTempFile(event.getFile().getFileName(), event.getFile().getInputstream());
            // Calls RestService to upload file
            restService.uploadFile(fileInfo);

            // If file was uploaded with success, adds file on user file list
            sessionBean.getLoggedUser().getFiles().add(fileInfo);

            // Adds the size of the uploaded file to the user storage usage
            sessionBean.getLoggedUser().addStorageUsage(event.getFile().getSize());

            // Shows message to the user
            showFacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo enviado com sucesso!");

        } catch (Exception e) {
            showFacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Interno. Não foi possível enviar o arquivo");

            e.printStackTrace();
            LOGGER.error("[Exception - " + e.getMessage() + "]");

        }
//        finally {
//            try {
//                outputStream.flush();
//                //    outputStream.close();
//            } catch (IOException e) {
//                LOGGER.error("[IOException - " + e.getMessage() + "]");
//            }
//        }

    }

    /**
     * Saves temporary file in disk
     *
     * @param fileName
     * @param in
     */
    public void saveTempFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(ConfigurationRepository.UPLOADED_FILES_PATH + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // in.close();
            LOGGER.info("Temporary file created [path="
                    + ConfigurationRepository.UPLOADED_FILES_PATH
                    + fileName + "]");

        } catch (IOException e) {
            LOGGER.error("[IOException - " + e.getMessage() + "]");
        }
    }

    // Shows JSF Faces Message to the user
    private void showFacesMessage(Severity severity, String msg) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Retrieves user's storage usage
    public int getStorageUsage() {
        // Gets user storage usage
        BigDecimal usage = new BigDecimal(sessionBean.getLoggedUser().getStorageUsage());

        /**
         * Returns the user storage usage in percentual. returns int value
         * because PrimeFaces component accepts only int value from 0 to 100
         */
        return usage.divide(new BigDecimal(MAX_STORAGE_SIZE)).multiply(new BigDecimal(100)).intValue();
    }

}
