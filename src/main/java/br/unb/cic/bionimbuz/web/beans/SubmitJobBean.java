package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import br.unb.cic.bionimbuz.info.ProgramInfo;

@Named
@SessionScoped
public class SubmitJobBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ProgramInfo> programList = ConfigurationRepository.getProgramConfiguration().getPrograms();

	public List<ProgramInfo> getProgramList() {
		return programList;
	}

}
