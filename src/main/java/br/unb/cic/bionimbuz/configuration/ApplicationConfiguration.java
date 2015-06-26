package br.unb.cic.bionimbuz.configuration;

import javax.ejb.Singleton;

@Singleton
public class ApplicationConfiguration implements Configuration {
	private String address;
	private String bionimbuzAddress;
	private String uploadedFilesDirectory;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBionimbuzAddress() {
		return bionimbuzAddress;
	}

	public void setBionimbuzAddress(String bionimbuzAddress) {
		this.bionimbuzAddress = bionimbuzAddress;
	}

	public String getUploadedFilesDirectory() {
		return uploadedFilesDirectory;
	}

	public void setUploadedFilesDirectory(String uploadedFilesDirectory) {
		this.uploadedFilesDirectory = uploadedFilesDirectory;
	}

	@Override
	public String toString() {
		return "ApplicationConfiguration [address=" + address
				+ ", bionimbuzAddress=" + bionimbuzAddress
				+ ", uploadedFilesDirectory=" + uploadedFilesDirectory + "]";
	}

}
