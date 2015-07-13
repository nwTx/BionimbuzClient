package br.unb.cic.bionimbuz.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
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
		} catch (ServerNotReachableException e) {
			e.printStackTrace();
		}
	}
}
