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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class FileUploadBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadBean.class);

    private static final long serialVersionUID = 1L;
    private RestService restService;
    private OutputStream outputStream;

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
        UploadedFileInfo fileInfo = new UploadedFileInfo();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Creates Information object about the file
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
            FacesMessage message = new FacesMessage("Erro interno", "Não foi possível enviar o arquivo");
            FacesContext.getCurrentInstance().addMessage(null, message);

            e.printStackTrace();

            return;
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If file was uploaded with success, adds file on user file list
        sessionBean.getLoggedUser().getFiles().add(fileInfo);

        FacesMessage message = new FacesMessage("Arquivo enviado com sucesso", "");
        FacesContext.getCurrentInstance().addMessage(null, message);

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

            in.close();

            LOGGER.info("Temporary file created [path="
                    + ConfigurationRepository.UPLOADED_FILES_PATH
                    + fileName + "]");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
