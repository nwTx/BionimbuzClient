package br.unb.cic.bionimbuz.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unb.cic.bionimbuz.info.FileInfo;
import br.unb.cic.bionimbuz.rest.service.RestService;

@Named
@RequestScoped
public class DeleteFileBean {
	private RestService restService;
	
	public DeleteFileBean() {
		restService = new RestService();
	}
	
	/**
	 * Handle file delete request by the user
	 * @param file
	 */
	public void deleteFile(FileInfo file) {
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getId());
		
		try {
			restService.deleteFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
