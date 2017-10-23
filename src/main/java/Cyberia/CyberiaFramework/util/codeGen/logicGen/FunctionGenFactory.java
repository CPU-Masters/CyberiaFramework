package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;
import Cyberia.CyberiaFramework.util.codeGen.logicGen.FunctionGen.FunctionalOp;

/**
 * Class that generates and modifies functions.
 * @author Josh Benton
 *
 */
public class FunctionGenFactory {
	static Random rand =  new Random();
	static Double maxConst = 100.0;
	static Double minConst = -100.0;
	//Generation Functions
	public static ArrayList<FunctionalOp<Double>> pureRandomGen(ArrayList<Double> parameters) {
		ArrayList<FunctionalOp<Double>> fops = new ArrayList<>();
		int operations = 1 + rand.nextInt(10);
		for (int i = 0; i< operations; i++) {
			//step 1 determine operation
			int opVal = rand.nextInt(FunctionGen.singleOps.size());
			String op = FunctionGen.singleOps.get(opVal);
			FunctionalOp<Double> fop = new FunctionalOp<>(op);
			//step 2-3 determine where to pull values 1 and 2 from (1st op must be parameter or constant)
			fop = randomDetemineVal(i,parameters,fop,0);
			fop = randomDetemineVal(i,parameters,fop,1);
			
			fops.add(fop);
		}
		//TODO tie together functions with random operations?
		//TODO simplify 2 constants into 1, generate another operation.
		
		fops = removeUnusedFunctionalOps(fops,findUnusedResults(fops));
		//Function must have at least 1 parameter if not ??
		//debugging
		System.out.println(toString(fops));
		return fops;
	}
	
	//Checking functions / validation
	//TODO put validation in it's own class.
	public static ArrayList<FunctionalOp<Double>> removeUnusedFunctionalOps(ArrayList<FunctionalOp<Double>> fops, List<Integer> unusedOps) {
		ArrayList<FunctionalOp<Double>> fopsToRemove = new ArrayList<>();
		for (Integer i : unusedOps) {
			fopsToRemove.add(fops.get(i));
			//CyberiaDebug.output("Removing operation: " + i + " : " +fops.get(i));
		}
		
		HashMap<Integer,Integer> reductionMap1 = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> reductionMap2 = new HashMap<Integer,Integer>();
		for (int i : unusedOps) {
			for (int j=i;j<fops.size();j++) {
				FunctionalOp<Double> fop = fops.get(j);
				if (fop.val1RefType==FunctionalOp.RESULT) {
					if (fop.val1Pointer>=i) {
						if (reductionMap1.containsKey(j)) {
							reductionMap1.put(j, reductionMap1.get(j)-1);
						} else {
							reductionMap1.put(j, -1);
						}
					}
				}
				if (fop.val2RefType==FunctionalOp.RESULT) {
					if (fop.val2Pointer>=i) {
						//fop.val2Pointer --;
						if (reductionMap2.containsKey(j)) {
							reductionMap2.put(j, reductionMap2.get(j)-1);
						} else {
							reductionMap2.put(j, -1);
						}
					}
				}
			}
		}
		
		for(int key : reductionMap1.keySet()) {
			fops.get(key).val1Pointer += reductionMap1.get(key);
		}
		for(int key : reductionMap2.keySet()) {
			fops.get(key).val2Pointer += reductionMap2.get(key);
		}
		
		
		fops.removeAll(fopsToRemove);
		return fops;
	}
	/**
	 * Finds all the results that aren't used.
	 * @param fops a list of functional operations
	 * @return a list of results that aren't used. (this corresponds to the functional op in the same position)
	 * ex result 3 isn't used, so FOP 3 isn't used in the final answer.
	 */
	public static List<Integer> findUnusedResults(ArrayList<FunctionalOp<Double>> fops) {
		List<Integer> rtrn = new ArrayList<Integer>();
		if (fops.size() > 0) {
		//work backwards from the last result (Functional op)
		FunctionalOp<Double> fop = fops.get(fops.size()-1);//get the last fop
		
		Set<Integer> usedResults = recursiveResultCheck(fops,fop, new HashSet<Integer>());
		usedResults.add(fops.size()-1);
		for (int i=0;i<fops.size()-1;i++) {
			if (!usedResults.contains(i)) {
				rtrn.add(i);
			}
		}
		
		
		}
		return rtrn;
	}
	
	private static Set<Integer> recursiveResultCheck(ArrayList<FunctionalOp<Double>> fops,FunctionalOp<Double> fop, Set<Integer> usedResults) {
		if (fop.val1RefType == FunctionalOp.RESULT) {
			usedResults.add(fop.val1Pointer);
			
			recursiveResultCheck(fops,fops.get(fop.val1Pointer), usedResults);
		}
		if (fop.val2RefType == FunctionalOp.RESULT) {
			usedResults.add(fop.val2Pointer);
			recursiveResultCheck(fops,fops.get(fop.val2Pointer), usedResults);
		}
		return usedResults;
	}

	//Sub Generation functions
 	public static FunctionalOp<Double> randomDetemineVal(int i,ArrayList<Double> parameters, FunctionalOp<Double> fop,int spot) {
		//step 2, determine where to pull value from (const results or parameters)
		int rndVal = rand.nextInt(FunctionalOp.REFS.size());
		if (i<1) {
			if (rndVal == FunctionalOp.RESULT)
				rndVal = FunctionalOp.INPUT;
		}
		if (spot == 0) {
			fop.val1RefType = rndVal;
			} else {
				fop.val2RefType = rndVal;
			}
		//step 3 determine pointers/values
		switch (rndVal) {
		case (FunctionalOp.CONST):
			Double cVal = genRandomConst();
			if (spot == 0) {
			fop.val1Const = cVal;
			} else {
				fop.val2Const = cVal;
			}
			break;
		case (FunctionalOp.INPUT):
			Integer rVal = rand.nextInt(parameters.size());
		if (spot == 0) {
			fop.val1Pointer = rVal;
			} else {
				fop.val2Pointer = rVal;
			}
			break;
		case (FunctionalOp.RESULT):
			//TODO have an option to use only independent calculations ?
			Integer resultVal = rand.nextInt(i);
		if (spot == 0) {
			fop.val1Pointer = resultVal;
			} else {
				fop.val2Pointer = resultVal;
			}
			break;
		}
		return fop;
		
	}
	
	public static Double genRandomConst() {
		//100-(-100) + (-100)
		return rand.nextDouble()*(maxConst-minConst)+minConst;
	}

	public static String toString(ArrayList<FunctionalOp<Double>> fops) {
		return fops.stream().map(x -> x.toString()).reduce((i, j) -> i + System.lineSeparator() + j).get();
	}
}
