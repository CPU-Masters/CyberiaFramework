package Cyberia.CyberiaFramework;

import java.util.ArrayList;

import Cyberia.CyberiaFramework.util.codeGen.logicGen.FunctionGen;
import Cyberia.CyberiaFramework.util.codeGen.logicGen.FunctionGen.MathOp;
import junit.framework.TestCase;

public class GenTest extends TestCase {
	
	
	public void testFunctions() {
		FunctionGen.MathOp<Double> mop0 = new MathOp<Double>(15.0,16.0,FunctionGen.ADD);
		FunctionGen.MathOp<Double> mop1 = new MathOp<Double>(45.0,1.0,FunctionGen.SUBTRACT);
		FunctionGen.MathOp<Double> mop2 = new MathOp<Double>(FunctionGen.MULT,0,1);//reference result 0 and result 1
		ArrayList<MathOp<Double>> ops = new ArrayList<>();
		ops.add(mop0);
		ops.add(mop1);
		ops.add(mop2);
		
		Double result = FunctionGen.process(ops);
		System.out.println(result);
		
	}
}
