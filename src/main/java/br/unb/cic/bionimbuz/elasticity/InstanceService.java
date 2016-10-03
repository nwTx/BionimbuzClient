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

 
@ManagedBean(name = "instanceService")
@ApplicationScoped
public class InstanceService {
   
    
    public List<Instance> getInstances() {
        List<Instance> list = new ArrayList<>();
         
        for (com.amazonaws.services.ec2.model.Instance instanceAWS : AmazonAPI.listinstances()) {
            Instance instance = new Instance();
            instance.setId(instanceAWS.getImageId());
            instance.setState(instanceAWS.getState().getName());
            list.add(instance);
        }
        return list;
    }
    
    public static void createinstance() throws IOException {
        AmazonAPI.setup();

        try {

            System.out.println("Criando nova maquina BioninbuZ");
            String imageId = "ami-6e3bb102";

            int minInstanceCount = 1; // 
            int maxInstanceCount = 1;
            RunInstancesRequest rir = new RunInstancesRequest(imageId, minInstanceCount, maxInstanceCount);
            rir.setInstanceType("t1.micro");
            rir.withSecurityGroups("default");

            RunInstancesResult result = AmazonAPI.EC2.runInstances(rir);

            System.out.println("waiting");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("OK");

            List<com.amazonaws.services.ec2.model.Instance> resultInstance = result.getReservation().getInstances();

            String createdInstanceId = null;
            for (com.amazonaws.services.ec2.model.Instance ins : resultInstance) {

                createdInstanceId = ins.getInstanceId();
                System.out.println("New instance has been created: " + ins.getInstanceId());//print the instance ID

            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//    AmazonAPI.enteroption();	
        } catch (AmazonServiceException ase) {
            System.out.println("Caught Exception: " + ase.getMessage());
            System.out.println("Reponse Status Code: " + ase.getStatusCode());
            System.out.println("Error Code: " + ase.getErrorCode());
            System.out.println("Request ID: " + ase.getRequestId());
            System.out.println("Give a valid input");
            System.out.println("");
//		AmazonAPI.enteroption();
        }

    }
         
}