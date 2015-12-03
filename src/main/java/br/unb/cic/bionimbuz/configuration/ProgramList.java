package br.unb.cic.bionimbuz.configuration;

import java.util.ArrayList;

import br.unb.cic.bionimbuz.model.ProgramInfo;

/**
 * List of the programs that can't be parallalized
 *
 * @author Vinicius
 */
public class ProgramList implements Configuration {

    private ArrayList<ProgramInfo> programs = new ArrayList<ProgramInfo>();

    public ArrayList<ProgramInfo> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<ProgramInfo> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("programs=[\n");

        for (ProgramInfo p : programs) {
            builder.append("\tid=" + p.getId() + ", ");
            builder.append("\tname=" + p.getName() + ", ");
            builder.append("\tdescription=" + p.getDescription() + "\n");
        }

        return builder.append("]").toString();
    }

}
