package br.unb.cic.bionimbuz.info;

import java.util.UUID;

public class FileInfo {
	private String id = UUID.randomUUID().toString();
	private String name;
	private String path;
	private String uploadTimestamp;
	private long size;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUploadTimestamp() {
		return uploadTimestamp;
	}

	public void setUploadTimestamp(String uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
