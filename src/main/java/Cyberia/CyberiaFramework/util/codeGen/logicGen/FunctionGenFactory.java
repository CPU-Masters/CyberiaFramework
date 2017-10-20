package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.ArrayList;
import java.util.Random;

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
		//TODO tie together functions with random operations
		//Function must have at least 1 parameter if not ??
		//debugging
		System.out.println(toString(fops));
		return fops;
	}
	
	public static FunctionalOp<Double> randomDetemineVal(int i,ArrayList<Double> parameters, FunctionalOp<Double> fop,int spot) {
		//step 2, determine where to pull value from (const results or parameters)
		int rndVal = rand.nextInt(FunctionalOp.REFS.size());
		if (i<1) {
			if (rndVal == FunctionalOp.RESULT)
				rndVal = FunctionalOp.INPUT;
		}
		if (spot == 0) {
			fop.val1Ref = rndVal;
			} else {
				fop.val2Ref = rndVal;
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
			//TODO to improve this to use only independent calculations
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
