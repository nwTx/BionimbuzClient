package br.unb.cic.bionimbuz.web.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.unb.cic.bionimbuz.configuration.ConfigurationLoader;
import br.unb.cic.bionimbuz.jobcontroller.JobController;

@Named
@SessionScoped
public class FileUploadBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String UPLOADED_FILES_DIRECTORY = ConfigurationLoader.readConfiguration().getUploadedFilesDirectory(); 
	private JobController jobController;

	public FileUploadBean() {
		jobController = new JobController();
	}

	public void handleUploadedFile(FileUploadEvent event) {
		try {
			saveTempFile(event.getFile().getFileName(), event.getFile().getInputstream());
			jobController.uploadFile(event.getFile().getFileName(), UPLOADED_FILES_DIRECTORY + event.getFile().getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		FacesMessage message = new FacesMessage("Arquivo enviado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void saveTempFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(UPLOADED_FILES_DIRECTORY + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
