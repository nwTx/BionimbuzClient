package br.unb.cic.bionimbuz.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unb.cic.bionimbuz.info.FileInfo;
import br.unb.cic.bionimbuz.rest.service.RestService;

@Named
@RequestScoped
public class FileListBean {
	private FileInfo fileInfo;
	private RestService restService;
	
	public FileListBean() {
		restService = new RestService();
	}
	
	/**
	 * Handle file delete request by the user
	 * @param file
	 */
	public void deleteFile(FileInfo file) {
		try {
			restService.deleteFile(fileInfo);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Ocorreu um erro interno... Tente novamente mais tarde", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			e.printStackTrace();
			return;
		}
	}
}
