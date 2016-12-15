package br.unb.cic.bionimbuz.elasticity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

 
@ManagedBean(name = "instanceService")
@ApplicationScoped
public class InstanceService {
    
    public void createInstance (String type){
        
        try {
            AmazonAPI amazonapi = new AmazonAPI();
            GoogleAPI googleapi = new GoogleAPI();

            switch (type) {
                case "t2.micro":
                    System.out.println("amazon");
                    amazonapi.createinstance("t2.micro");
                    break;
                case "n1-standard-1":
                    System.out.println("google");
                    googleapi.createinstance("n1-standard-1", "teste2");
                    break;
                default:
                    System.out.println("Este não é um tipo válido!");
            }
        } catch (IOException ex) {
            Logger.getLogger(InstanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
   
    
//    public List<Instance> getInstances() {
//        List<Instance> list = new ArrayList<>();
//         
//        for (com.amazonaws.services.ec2.model.Instance instanceAWS : AmazonAPI.listinstances()) {
//            Instance instance = new Instance();
//            instance.setId(instanceAWS.getImageId());
//            instance.setState(instanceAWS.getState().getName());
//            list.add(instance);
//        }
//        return list;
//    }
    
    
         
}