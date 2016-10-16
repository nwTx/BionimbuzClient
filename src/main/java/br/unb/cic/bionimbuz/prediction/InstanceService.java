package br.unb.cic.bionimbuz.prediction;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name = "InstaceServicePrediction")
@ApplicationScoped
public class InstanceService {
     
    private final static String[] provider;
    private final static String[] programa;
    
     
    static {
        provider = new String[3];
        provider[0] = "Amazon";
        provider[1] = "Azure";
        provider[2] = "Google";

         
        programa = new String[3];
        programa[0] = "sam2bad";
        programa[1] = "Bowtie";
        programa[2] = "Genome2Interval";
        
    }
     
    public List<Instance> createInstances(int size) {
        List<Instance> list = new ArrayList<Instance>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Instance(getRandomId(), getRandomPrograma(), getRandomMem(), getRandomCpu(), getRandomPrice(), getRandomProvider()));
            //list.add(new Instance(getRandomId()));
        }
         
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private String getRandomPrograma() {
        return programa[(int) (Math.random() * 3)];
    }
    
    public int getRandomMem() {
        return (int) (Math.random() * 10);
    }
    
    public int getRandomCpu() {
        return (int) (Math.random() * 10);
    }
    
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
    
    private String getRandomProvider() {
        return provider[(int) (Math.random() * 3)];
    }

    public List<String> getProvider() {
        return Arrays.asList(provider);
    }
     
    public List<String> getPrograma() {
        return Arrays.asList(programa);
    }
}