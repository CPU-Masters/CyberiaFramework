package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.ArrayList;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;

/**
 * Code relating to function/formula generation.
 * @author Josh Benton
 *
 */
public class FunctionGen {

	private static final String ADD = "+";
	private static final String SUBTRACT = "-";
	private static final String MULT = "*";
	private static final String DIV = "/";
	private static final String MOD = "%";
	//Constants for operations
	public static ArrayList<String> singleOps = new ArrayList<>();
	static {
		singleOps.add(ADD);
		singleOps.add(SUBTRACT);
		singleOps.add(MULT);
		singleOps.add(DIV);
		singleOps.add(MOD);
		
	}
	//actual execution
	
	public Double process(ArrayList<MathOp<Double>> ops) {
		ArrayList<Double> results = new ArrayList<>();
		
		for(MathOp<Double> o : ops) {
			Double val1,val2;
			if (o.result1!=null) {
				val1 = results.get(o.result1);
			} else {
				val1 = o.val1;
			}
			if (o.result2!=null) {
				val2 = results.get(o.result2);
			} else {
				val2 = o.val2;
			}
			results.add(op(val1,val2,o.op));
		}
		
		return results.get(results.size()-1);
	}
	//TODO duplicate code for floats / doubles
	public Double op(Double x,Double y, String op) {
		switch (op) {
		case (ADD):
			return x + y;
		case (SUBTRACT):
			return x - y;
		case (MULT):
			return x * y;
		case (DIV):
			return x / y;
		case (MOD):
			return x % y;
		}
		CyberiaDebug.output("INVALID OP: " + op);
		return 0;
	}
	
	//program code generation
	
	//helper classes
	public class MathOp<T> {
		/**
		 * The result to reference
		 */
		public Integer result1 = null;
		public Integer result2 = null;
		public T val1,val2;
		public String op;
		
		public MathOp() {
			
		}
		public MathOp(T val1,T val2) {
			this.val1 = val1;
			this.val2 = val2;
		}
		public void setVal1(T v) {
			this.val1 = v;
		}
		public void setVal2(T v) {
			this.val2 = v;
		}
		public void setResult1(Integer r) {
			this.result1 = r;
		}
		public void setResult2(Integer r) {
			this.result2 = r;
		}
		public void setOp(String op) {
			this.op = op;
		}
	}
}
