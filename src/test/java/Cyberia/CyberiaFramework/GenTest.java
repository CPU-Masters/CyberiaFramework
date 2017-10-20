package Cyberia.CyberiaFramework;

import java.util.ArrayList;

import Cyberia.CyberiaFramework.util.codeGen.logicGen.FunctionGen;
import Cyberia.CyberiaFramework.util.codeGen.logicGen.FunctionGen.FunctionalOp;
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
		
		ArrayList<Double> paramsTest = new ArrayList<Double>();
		paramsTest.add(120.0);
		paramsTest.add(60.0);
		paramsTest.add(-5.0);
		ArrayList<Double> paramsTest2 = new ArrayList<Double>();
		paramsTest2.add(50.0);
		paramsTest2.add(10.0);
		paramsTest2.add(90.0);
		ArrayList<FunctionalOp<Double>> fops = new ArrayList<>();
		FunctionalOp<Double> f1 = new FunctionalOp<>(FunctionGen.ADD);
		f1.setARef1(0);//input (120)
		f1.setARef2(1);//input (60)
		fops.add(f1);
		FunctionalOp<Double> f2 = new FunctionalOp<>(FunctionGen.SUBTRACT);
		f2.setRRef1(0);//Result (120+60)
		f2.setARef2(2);//input (-5)
		fops.add(f2);
		FunctionalOp<Double> f3 = new FunctionalOp<>(FunctionGen.DIV);
		f3.setRRef1(1);//input (120)
		f3.setCVal2(100.0);//input (60)
		fops.add(f3);
		Double fr1 = FunctionGen.process(fops, paramsTest);
		Double fr2 = FunctionGen.process(fops, paramsTest2);
		System.out.println(fr1 + " | " + fr2);
		
	}
}
