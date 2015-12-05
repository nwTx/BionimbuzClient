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
import javax.faces.application.FacesMessage.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class FileUploadBean implements Serializable {

    private static final Long MAX_STORAGE_SIZE = 268435456L;    // 256 Mb

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
    public void handleUploadedFile(FileUploadEvent event) {
        // Verifies if the file doesn't overflow the user storage usage
        if ((sessionBean.getLoggedUser().getStorageUsage() + event.getFile().getSize()) > MAX_STORAGE_SIZE) {
            showFacesMessage(FacesMessage.SEVERITY_WARN, "Seu espaco de armazenamento ultrapassou 256 Mb!");
        }

        UploadedFileInfo fileInfo = new UploadedFileInfo();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Set file information
        fileInfo.setName(event.getFile().getFileName());
        fileInfo.setSize(event.getFile().getSize());
        fileInfo.setUploadTimestamp(format.format(new Date()));
        fileInfo.setUserId(sessionBean.getLoggedUser().getId());

        try {
            // Saves temporary file in disk
            saveTempFile(event.getFile().getFileName(), event.getFile().getInputstream());

            // Calls RestService to upload file
            restService.uploadFile(fileInfo);

        } catch (Exception e) {
            showFacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Interno. Não foi possível enviar o arquivo");

            e.printStackTrace();
            return;

        } finally {
            try {
                outputStream.flush();
                //    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If file was uploaded with success, adds file on user file list
        sessionBean.getLoggedUser().getFiles().add(fileInfo);
        
        // Adds the size of the uploaded file to the user storage usage
        sessionBean.getLoggedUser().addStorageUsage(event.getFile().getSize());

        showFacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo enviado com sucesso!");
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
            System.out.println(e.getMessage());
        }
    }

    // Shows JSF Faces Message to the user
    private void showFacesMessage(Severity severity, String msg) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public int getStorageUsage() {
        Long t = (sessionBean.getLoggedUser().getStorageUsage() / MAX_STORAGE_SIZE ) * 100;
        
        return t.intValue();
    }

}
