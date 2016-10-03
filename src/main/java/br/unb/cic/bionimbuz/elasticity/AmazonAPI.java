package br.unb.cic.bionimbuz.elasticity;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.auth.PropertiesCredentials;


import java.io.*;
import java.util.*;

public class AmazonAPI {

    public static AmazonEC2 EC2;
    public static Scanner user_input = new Scanner(System.in);
    public static String instanceid;
    public static KeyPair keyPair;
    public static int count = 1;

    public static void setup() {
        try {
            String credentialsFile = "AwsCredentials.properties";
            PropertiesCredentials credentials = new PropertiesCredentials(AmazonAPI.class.getResourceAsStream(credentialsFile));
            EC2 = new AmazonEC2Client(credentials);
            EC2.setEndpoint("ec2.sa-east-1.amazonaws.com");
        } catch( IOException ioe) {
            throw new RuntimeException(ioe);
        }//, IllegalArgumentException
        
    }

    public static void shutdown() {
        AmazonAPI.setup();
        EC2.shutdown();
    }

    public static List<Instance> listinstances() {
        AmazonAPI.setup();

        DescribeInstancesResult describeInstancesRequest = EC2.describeInstances();
        List<Reservation> reservations = describeInstancesRequest.getReservations();
        List<Instance> instances = new ArrayList<>();

        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }

        return instances;

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

//            Scanner keyscan = new Scanner(System.in);
//            System.out.println("Do you want to use an existing keypair or do you want to create a new one?");
//            System.out.println("#1 Use existing keypair");
//            System.out.println("#2 Create a new keypair");
//            int keypairoption;
//            String key; //existing keypair name
//            keypairoption = keyscan.nextInt();
//            if (keypairoption == 1) {
//                System.out.println("Enter the existing keypair name to use with the new instance");
//                key = keyscan.next();
//                rir.withKeyName(key);
//            } else if (keypairoption == 2) {
//                //count++;
//                System.out.println("Enter the keypair name to create");
//                String newkeyname;
//                newkeyname = keyscan.next();
//                CreateKeyPairRequest newKeyRequest = new CreateKeyPairRequest();
//                newKeyRequest.setKeyName(newkeyname);
//                CreateKeyPairResult keyresult = EC2.createKeyPair(newKeyRequest);
//
//                keyPair = keyresult.getKeyPair();
//                System.out.println("The key we created is = "
//                        + keyPair.getKeyName() + "\nIts fingerprint is="
//                        + keyPair.getKeyFingerprint() + "\nIts material is= \n"
//                        + keyPair.getKeyMaterial());
//
//                System.out.println("Enter the directory to store .pem file (eg: Windows C:\\Users\\user\\Desktop\\, Linux /user/home)");
//                String dir;
//                dir = keyscan.next();
//                String fileName = dir + newkeyname + ".pem";
//                File distFile = new File(fileName);
//                BufferedReader bufferedReader = new BufferedReader(new StringReader(keyPair.getKeyMaterial()));
//                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(distFile));
//                char buf[] = new char[1024];
//                int len;
//                while ((len = bufferedReader.read(buf)) != -1) {
//                    bufferedWriter.write(buf, 0, len);
//                }
//                bufferedWriter.flush();
//                bufferedReader.close();
//                bufferedWriter.close();
//
//            }

            rir.withSecurityGroups("default");

            RunInstancesResult result = EC2.runInstances(rir);

            System.out.println("waiting");
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("OK");

            List<Instance> resultInstance = result.getReservation().getInstances();

            String createdInstanceId = null;
            for (Instance ins : resultInstance) {

                createdInstanceId = ins.getInstanceId();
                System.out.println("New instance has been created: " + ins.getInstanceId());//print the instance ID

            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Restarting the application");
            System.out.println();
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

    public static void stopinstance() throws IOException {
        AmazonAPI.setup();
        try {

            System.out.println("#4 Stop the Instance");
            System.out.println("Enter the instance id");
            instanceid = user_input.next();

            //stop instance
            StopInstancesRequest sireq = new StopInstancesRequest().withInstanceIds(instanceid);
            StopInstancesResult sires = EC2.stopInstances(sireq);
            System.out.println("Stopping instance " + instanceid);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Restarting the application");
            System.out.println();
//        AmazonAPI.enteroption();	
        } catch (Exception e) {
            System.out.println("Give a valid input");
            System.out.println("");
//			AmazonAPI.enteroption();
        }

    }

    public static void startinstance() throws IOException {
        AmazonAPI.setup();
        try {

            System.out.println("#3 Start the Instance");
            System.out.println("Enter the instance id");
            instanceid = user_input.next();

            //start instance
            List<String> instancesToStart = new ArrayList<String>();
            instancesToStart.add(instanceid);
            StartInstancesRequest startr = new StartInstancesRequest();
            startr.setInstanceIds(instancesToStart);
            EC2.startInstances(startr);
            System.out.println("Starting instance " + instanceid);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Restarting the application");
            System.out.println();
//        AmazonAPI.enteroption();
        } catch (Exception e) {
            System.out.println("Give a valid input");
            System.out.println("");
//			AmazonAPI.enteroption();
        }

    }

    public static void terminate() throws IOException {
        AmazonAPI.setup();
        try {

            System.out.println("#6 Terminate the Instance");
            System.out.println("Enter the instance id to terminate");
            instanceid = user_input.next();

            List<String> instancesToTerminate = new ArrayList<String>();
            instancesToTerminate.add(instanceid);
            TerminateInstancesRequest tir = new TerminateInstancesRequest(instancesToTerminate);
            EC2.terminateInstances(tir);
            System.out.println("Terminating the instance : " + instanceid);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Restarting the application");
            System.out.println();
//        AmazonAPI.enteroption();
        } catch (Exception e) {
            System.out.println("Give a valid input");
            System.out.println("");
//			AmazonAPI.enteroption();
        }

    }

    public static void rebootinstance() throws AmazonClientException, IOException {
        AmazonAPI.setup();
        try {

            System.out.println("#5 Reboot instance");
            System.out.println("Enter the instance id to reboot");
            instanceid = user_input.next();
            List<String> instancesToReboot = new ArrayList<String>();
            instancesToReboot.add(instanceid);

            RebootInstancesRequest rir = new RebootInstancesRequest().withInstanceIds(instancesToReboot);
            EC2.rebootInstances(rir);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Rebooted the instance " + instanceid);
            System.out.println("Restarting the application");
            System.out.println();
//	        AmazonAPI.enteroption();
        } catch (Exception e) {
            System.out.println("Give a valid input");
            System.out.println("");
//			AmazonAPI.enteroption();
        }
    }

    public static void createami() throws IOException {
        AmazonAPI.setup();
        try {

            System.out.println("#8 Create AMI");
            System.out.println("Enter the instance id to create AMI");
            instanceid = user_input.next();

            CreateImageRequest cir = new CreateImageRequest(instanceid, instanceid);
            CreateImageResult cires = EC2.createImage(cir);
            String imageid;
            imageid = cires.getImageId();
            System.out.println("The imageid of the newly created AMI is " + imageid);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("Restarting the application");
            System.out.println();
//        AmazonAPI.enteroption();
        } catch (Exception e) {
            System.out.println("Give a valid input");
            System.out.println("");
//			AmazonAPI.enteroption();
        }

    }

} //main end
