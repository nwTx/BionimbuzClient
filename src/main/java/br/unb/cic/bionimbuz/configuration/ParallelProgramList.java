package br.unb.cic.bionimbuz.configuration;

import java.util.ArrayList;

import br.unb.cic.bionimbuz.info.ProgramInfo;

/**
 * List of the programs which can be parallelized
 *  
 * @author Vinicius
 */
public class ParallelProgramList implements Configuration {
	private ArrayList<ProgramInfo> programs = new ArrayList<ProgramInfo>();

	public ArrayList<ProgramInfo> getPrograms() {
		return programs;
	}

	public void setPrograms(ArrayList<ProgramInfo> programs) {
		this.programs = programs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Parallel Program List: [programs= ");

		for (ProgramInfo p : programs) {
			builder.append("{" + p.getId() + ",");
			builder.append(p.getName() + ",");
			builder.append(p.getDescription() + "} ");
		}

		return builder.append("]").toString();
	}

}
