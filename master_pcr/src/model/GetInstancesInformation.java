package model;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import resources.AmazonIndex;
import resources.GoogleCloud;
import resources.Instance;
import resources.ServicePricing;
import resources.ServiceWorkflow;

public class GetInstancesInformation {
	static double alpha1 = 1; // Custo (Alterar somente nesta linha)
	static double alpha2 = 1 - alpha1; // Desempenho
	static double lambda1 = 500; // Custo (Alterar somente nesta linha)
	static double lambda2 = 500; // Desempenho
	static double custoM = 2100000000; // Custo m�ximo
	static double tempoM = 2100000000; // tempo m�ximo
	static ArrayList<ServiceWorkflow> services = new ArrayList<ServiceWorkflow>();
	static ArrayList<ServicePricing> servicesCost = new ArrayList<ServicePricing>();

	public static void main(String[] args) throws IOException {
		ServicePricing costsService = new ServicePricing(1,"Google Compute Engine",0.026,0.01,0.12);
		ServicePricing costsService1 = new ServicePricing(2,"Amazon EC2",0.0405,0.160,0.25);
		servicesCost.add(costsService);
		servicesCost.add(costsService1);
		ServiceWorkflow service = new ServiceWorkflow(1,"Bowtie",5908405,0.90,0,0);
		ServiceWorkflow service1 = new ServiceWorkflow(2,"TopHat",3355801,0.60,0,0);
		ServiceWorkflow service2 = new ServiceWorkflow(3,"Trinity",5908405,0.60,0,0);
		ServiceWorkflow service3 = new ServiceWorkflow(4,"Trinity",19943000,1.90,0,0);
		ServiceWorkflow service4 = new ServiceWorkflow(5,"TopHat",3000000,0.90,0,0);
		services.add(service);
		services.add(service1);
		services.add(service2);
		services.add(service3);
		services.add(service4);
//		services.add(service5);
		GoogleCloud gc = new GoogleCloud();
		AmazonIndex idx = new AmazonIndex();
		ArrayList<Instance> allInstances = new ArrayList<Instance>();
		
		allInstances.addAll(gc.getListInstanceGCE());
		allInstances.addAll(idx.getListInstanceEc2());
		HashSet<Instance> hs = new HashSet<Instance>();
		hs.addAll(allInstances);
		allInstances.clear();
		allInstances.addAll(hs);
		for (int i = 0; i < allInstances.size(); i++) {
			allInstances.get(i).setIdN(i);
		}
		double max = 0;
		Instance object = new Instance();

		// remove mv preemptivas
		Iterator<Instance> mv = allInstances.iterator();
		while (mv.hasNext()) {
			Instance s = mv.next(); // must be called before you can call
									// i.remove()
			// Do something
//			if (s.getCpuHtz() == 0 || z(alpha1, alpha2, s) < 0)
				if (s.getCpuHtz() == 0 || s.getMemory() < 6 || s.getNumCores() < 2 || s.getNumCores() > 16)
					mv.remove();
		}

		Collections.sort(allInstances, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				Instance ins1 = (Instance) o1;
				Instance ins2 = (Instance) o2;
				double val1 = (ins1.getCostPerHour() * alpha1) + (( ins1.getNumCores() * ins1.getCostPerHour() + ins1.getMemory()) * alpha2);
				double val2 = (ins2.getCostPerHour() * alpha1) + (( ins2.getNumCores() * ins2.getCostPerHour() + ins2.getMemory()) * alpha2);
				return val1 > val2 ? -1 : (val1 < val2 ? +1 : 0);
			}
		});

		 System.out.println("************ Lista Pronta ************");
		
		 allInstances.forEach((i) -> {
		 System.out.println(i + " " + (( i.getCostPerHour() * alpha1) +
		 ((i.getNumCores()) * alpha2)) );
		 });
		
//		 System.exit(0);

		// *********************Estimate pelo R*********************

//		 RConnection connection = null;
//		 try {
//		 /*
//		 * Create a connection to Rserve instance running on default port
//		 * 6311
//		 */
//		 connection = new RConnection();
//		
//		 /* Note four slashes (\\\\) in the path */
//		 connection.eval("source('D:\\\\Dropbox\\\\Mestrado\\\\regr.R')");
//		 // int num1 = 10;
//		 // int num2 = 20;
//		 // int sum = connection.eval("myAdd(" + num1 + "," + num2 +
//		 // ")").asInteger();
//		 // System.out.println("The sum is=" + sum);
//		
//		 String programa = "TopHat";
//		 int qntReads = 9153141;
//		
//		 for (int i = 0; i < allInstances.size(); i++) {
//		 if (allInstances.get(i).getCpuHtz() > 0 &&
//		 allInstances.get(i).getCostPerHour() < 10) {
//		 double estimateTime = connection.eval("estimateTime(" + qntReads + ","+ allInstances.get(i).getQuantityCPU() + "," + programa + ")").asDouble();
//		 System.out.printf("%s %.2f %s %s %.2f \n", "Estimate Time: ",
//		 Math.exp((double) estimateTime), " | ", "Proc. Power: ", (double)
//		 allInstances.get(i).getQuantityCPU() );
//		 }
//		 };
//		
//		 } catch (RserveException e) {
//		 e.printStackTrace();
//		 } catch (REXPMismatchException e) {
//		 e.printStackTrace();
//		 } finally {
//		 connection.close();
//		 }

		// *****************************************
//		double menor = 0;
//		ArrayList<Double> ordenacao = new ArrayList();
//		ArrayList<Instance> escolhas = new ArrayList();
//		for (Instance i : allInstances) {
//			ordenacao.add(z(alpha1, alpha2, i));
//			escolhas.add(i);
//		}
//
//		Collections.sort(ordenacao);
//		int cont = 0;
//		double sum = 0;
//		for (Double i : ordenacao) {
//			if (cont < 20) {
//				sum += i;
//				System.out.println(escolhas.get(cont));
//			}
//			cont++;
//		}
//		grasp(allInstances);
		 System.out.println("<<<---------------------------------------------------------->>>");
			long tempoInicio = System.currentTimeMillis();
//			bruteforce(allInstances);
			grasp(allInstances);
			System.out.println("Tempo Total: "+(System.currentTimeMillis()-tempoInicio)/1000);
			System.out.println("<<<---------------------------------------------------------->>>");
//			alpha1 = 0.1; // Custo (Alterar somente nesta linha)
//			alpha2 = 1 - alpha1; // Desempenho
//			lambda1 = 500; // Custo (Alterar somente nesta linha)
//			lambda2 = 500; // Desempenho
//			custoM = 2100000000; // Custo m�ximo
//			tempoM = 10000; // tempo m�ximo
//		    tempoInicio = System.currentTimeMillis();
////			bruteforce(allInstances);
//		    grasp(allInstances);
//			System.out.println("Tempo Total 2: "+(System.currentTimeMillis()-tempoInicio)/1000);
	}
	
	public static void bruteforce(ArrayList<Instance> allInstances){
		int[] num = new int[20];
		int[] list = new int[3];
		for(int i = 0; i < num.length; i++ )
			num[i] = i+1;
		for(int i = 0; i < list.length; i++ )
			list[i] = i;
//		for(int i = 0; i < num.length; i++ )
//			System.out.println(num[i]);
		
		ArrayList<Instance> solution = new ArrayList<Instance>(); 
		ArrayList<Instance> best = new ArrayList<Instance>(); 
		best.add(allInstances.get(0));
		best.add(allInstances.get(0));
		best.add(allInstances.get(0));
		best.add(allInstances.get(0));
		zAllImpress(alpha1, alpha2, best);
		double all = Math.pow(allInstances.size(), services.size());
//		System.out.print(all);
		all = allInstances.size();
		int cont = 0;
		for(int i = 0; i < all; i++){
			for(int j = 0; j < all; j++){
				for(int k = 0; k < all; k++){
					for(int l = 0; l < all; l++){
						cont++;
//						System.out.println((i+1) + " " + (j+1) + " " + (k+1) + " " + (l+1));
						solution.clear();
						solution.add(allInstances.get(i));
						solution.add(allInstances.get(j));
						solution.add(allInstances.get(k));
						solution.add(allInstances.get(l));
//						System.out.println(zAll(alpha1, alpha2, best) + " | " + zAll(alpha1, alpha2, solution));
						if (zAll(alpha1, alpha2, best) > zAll(alpha1, alpha2, solution)) {
//							System.out.println(zAll(alpha1, alpha2, best) + " | " + zAll(alpha1, alpha2, solution));
							best.clear();
							best = new ArrayList<Instance>(solution);
//							zAllImpress(alpha1, alpha2, best);
						}
					}
				}
			}
		}
		zAllImpress(alpha1, alpha2, best);
		System.out.println("Contador: " + cont);
			
	}

	public static double estimateTime(double qntCpu, ServiceWorkflow service) {
		double intercept = -10.605121;
		double regrQntReads = 0.937015;
		double regrfatorCor = 0.578613;
		double regrDummyprogTop = 1.884547;
		double regrDummyprogTri = 3.715994;
		double qntCPUregr = -0.036601;
		double qntReads = Math.log(service.qntReads);
		double fatorCor = 4 / qntCpu;
		int dummyprogTop = 0;
		int dummyprogTri = 0;
		
		if(service.nameService == "Trinity")
			dummyprogTri = 1;
		else{
			if(service.nameService == "TopHat")
				dummyprogTop = 1;
		}

		double timeEstimate = intercept + (regrQntReads * qntReads) + (fatorCor * regrfatorCor)	+ (dummyprogTop * regrDummyprogTop) + (dummyprogTri * regrDummyprogTri);
		return timeEstimate;
	}

	public static double estimateTimeRegression(double qntCpu, Instance mv, String program) {
		RConnection connection = null;
		connection = null;
		double estimateTime = 0;
		try {
			connection = new RConnection();
			connection.eval("source('D:\\\\Dropbox\\\\Mestrado\\\\regr.R')");
			String programa = "TopHat";
			programa = program;
			int qntReads = 9153141;
			estimateTime = connection.eval("estimateTime(" + qntReads + "," + mv.getQuantityCPU() + "," + programa + ")").asDouble();
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return estimateTime;

	}

//	public static double z(double alpha1, double alpha2, Instance solution) {
//		double z = 0;
//		z = alpha1 * (solution.getCostPerHour() * estimateTime(solution.getQuantityCPU() * solution.getCpuHtz()))
//				+ alpha2 * (estimateTime(solution.getQuantityCPU() * solution.getCpuHtz()));
//		return z;
//	}

	public static double zAll(double alpha1, double alpha2, ArrayList<Instance> solution) {
		double z = 0;
		int j = 0;
		double custo = 0;
		double time = 0;
		double custoTotal = 0;
		double timeTotal = 0;
		int auxCost = 1;
		for (Instance i : solution) {
			time = Math.exp(estimateTime(i.getQuantityCPU(), services.get(j)));
			auxCost = i.getProvider() == "Google Compute Engine" ?  1 :  60;
//			custo = (
//					((i.getCostPerHour()/auxCost) * time) + 
//					(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 0 : 1).costBucket * services.get(j).getFileInput()) + 
//					(j > 0 ? 
//							(solution.get(j-1).getProvider() != solution.get(j).getProvider() ? 
//									(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
//											0 : 1).costDownEx * services.get(j).fileInput ) : 
//										(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
//												0 : 1).costDownIn * services.get(j).fileInput )) : 0 ));
			
			custo = (
					((i.getCostPerHour()/auxCost)*time) + 
					(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 0 : 1).costBucket * services.get(j).getFileInput()) + 
					(j > 0 ? 
							(solution.get(j-1).getProvider() != solution.get(j).getProvider() ? 
									(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
											0 : 1).costDownEx * services.get(j).fileInput ) : 
										(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
												0 : 1).costDownIn * services.get(j).fileInput )) : 0 )) ;

//			z += alpha1 * (custo) +	alpha2 * (time);
			custoTotal += custo;
			timeTotal += time;
			services.get(j).setCostExecution(custo);
			services.get(j).setTimeEstimated(time);;
			j++;
		};
//		 for (Instance i : solution) {
//			 double value = estimateTimeRegression(i.getQuantityCPU(),i,services.get(j++).nameService);
//			 z += alpha1 * (i.getCostPerHour() * value) + alpha2 * (value * i.getCpuHtz());
//		 };
		z = ((alpha1 * custoTotal) + (alpha2 * timeTotal)) + lambda1 *(Math.max(0, (timeTotal - tempoM))) + lambda2 * (Math.max(0, (custoTotal - custoM)));
		return z;
	}

	public static void zAllImpress(double alpha1, double alpha2, ArrayList<Instance> solution) {
		DecimalFormat df = new DecimalFormat("#.##");
		double time = 0;
		double custo = 0;
		double custoTotal = 0;
		double timeTotal = 0;
		int j = 0;
		int auxCost = 1;
		for (Instance i : solution) {
			time = Math.exp(estimateTime(i.getQuantityCPU(), services.get(j)));
			auxCost = i.getProvider() == "Google Compute Engine" ?  1 :  60;
			custo =  (
					((i.getCostPerHour()/auxCost) * time) + 
					(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 0 : 1).costBucket * services.get(j).getFileInput()) + 
					(j > 0 ? 
							(solution.get(j-1).getProvider() != solution.get(j).getProvider() ? 
									(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
											0 : 1).costDownEx * services.get(j).fileInput ) : 
										(servicesCost.get(i.getProvider() == "Google Compute Engine" ? 
												0 : 1).costDownIn * services.get(j).fileInput )) : 0 ));			
			
			System.out.print(" | " + df.format(custo) + " | ");
			System.out.print(" -- " + df.format(time) + " -- ");
			custoTotal += custo;
			timeTotal += time;
			j++;
		}
		System.out.println("");
		for (Instance i : solution) {
			System.out.print(i.getIdN() + "::" + i.getType() + "::" + i.getCpuHtz() + "::" + i.getLocality() +":: MEN: " + i.getMemory() +":: CPU: " + i.getQuantityCPU()+"::" + i.getCostPerHour() +" | ");
		}
		System.out.println("");
		System.out.println("Time: " + df.format(timeTotal));
		System.out.println("Custo: " + df.format(custoTotal));
		System.out.println("Z: " + df.format(((alpha1 * custoTotal) + (alpha2 * timeTotal)) + lambda1 *(Math.max(0, (timeTotal - tempoM))) + lambda2 * (Math.max(0, (custoTotal - custoM)))));
	}

	public static ArrayList<Instance> construction(ArrayList<Instance> instances) {
		ArrayList<Instance> solution = new ArrayList<Instance>();
		int inter = 20;
		inter = services.size();
		int qntMaxProv = instances.size();
		int sorter;
		double alfa = 1;
		qntMaxProv =  (int) (0 + (alfa*(qntMaxProv - 0)));
		Random gerador = new Random();
		while (inter != 0) {
			sorter = gerador.nextInt(qntMaxProv);
			solution.add(instances.get(sorter));
			inter--;
		}
		return solution;
	}

	public static ArrayList<Instance> localSearch(ArrayList<Instance> instances, ArrayList<Instance> allInstances,
			double alpha1, double alpha2) {
		ArrayList<Instance> solution = new ArrayList<Instance>();
		ArrayList<Instance> solution2 = new ArrayList<Instance>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		double improving = zAll(alpha1, alpha2, instances);
		double improvings = 0;
		int inter = 50;
		
		solution = (ArrayList<Instance>) instances.clone();

//		while (inter > 0) {
			for (int i = 0; i < (solution.size()); i++) {
				int j = 0;
				while (j < solution.size() && (allInstances.indexOf(solution.get(i)) + j) < allInstances.size()) {
					solution2.clear();
					solution2 = (ArrayList<Instance>) solution.clone();
					solution2.set(i, allInstances.get(allInstances.indexOf(solution2.get(i))+j));
					j++;
					improvings = zAll(alpha1, alpha2, solution2);
					if (improvings < improving) {						
						i = -1;
						instances.clear();
						instances = (ArrayList<Instance>) solution2.clone();
						inter = 50;
						improving = improvings;
						solution.clear();
						solution = (ArrayList<Instance>) solution2.clone();
						j = solution.size();
					}										
				}
			}
//			inter--;
//		}
		return instances;
	}

	public static void grasp(ArrayList<Instance> instances) {
//		int vet[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };
//		System.out.println();
//		System.out.println("( 4 , 11 , 22 , 29 )");
//		int vet2[] = { 4, 11, 22, 29 };
//		for (int i = 0; i < (vet2.length); i++) {
//			int j = 0;
//			int vet3[];
//			while (j < vet2.length && (vet2[i] + j) < vet.length) {
//				vet3 = vet2.clone();
//				vet3[i] = vet[vet3[i] + j];
//				System.out.print("( ");
//				for (int l = 0; l < vet3.length; l++) {
//					System.out.print(vet3[l]);
//					if (l != vet3.length - 1)
//						System.out.print(" , ");
//				}
//				System.out.println(" )");
//				j++;
//			}
//		}
		DecimalFormat df = new DecimalFormat("#.##");
		ArrayList<Instance> solution = null;
		ArrayList<Instance> bestSolution = null;
		Instance obj = new Instance();
		obj.setCpuHtz(200.00);
		obj.setCostPerHour(9999.99);
		obj.setNumCores(0);
		ArrayList<Instance> solution2 = new ArrayList<Instance>();
		solution2.add(obj);
		int iter = 5000;
		int aux = 0;
		int cont = 0;
		// while (aux++ <= iter){
		int value = 1;
//		for (Instance mv : instances)
//			System.out.println(z(alpha1, alpha2, mv));
		while (iter > 0) {
			iter--;
			solution = construction(instances);
			if (bestSolution == null) {
				bestSolution = new ArrayList<Instance>(solution);
			}
			double entrada = zAll(alpha1, alpha2, solution);
			solution2 = localSearch(solution, instances, alpha1, alpha2);
			double saida = zAll(alpha1, alpha2, solution2);
			if (zAll(alpha1, alpha2, bestSolution) > zAll(alpha1, alpha2, solution2)) {
				bestSolution.clear();
				bestSolution = new ArrayList<Instance>(solution2);
				aux = 0;
				System.out.println("<<--------------------->>");
				System.out.println("Cont: " + cont);
				System.out.println("Values: ");
				zAllImpress(alpha1, alpha2, bestSolution);
				value = (int) zAll(alpha1, alpha2, solution2);
				System.out.println("Entrada: " + df.format(entrada));
				System.out.println("Saida: " + df.format(saida));
				iter = 5000;
			}
			cont++;
		}
	}
}

// library(Rserve)
// Rserve()
