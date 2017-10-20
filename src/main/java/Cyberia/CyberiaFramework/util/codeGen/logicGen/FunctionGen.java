package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.ArrayList;
import java.util.stream.Collectors;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;

/**
 * Code relating to function/formula generation.
 * @author Josh Benton
 *
 */
public class FunctionGen {

	public static final String ADD = "+";
	public static final String SUBTRACT = "-";
	public static final String MULT = "*";
	public static final String DIV = "/";
	public static final String MOD = "%";
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
	public static Double process(ArrayList<FunctionalOp<Double>> functions, ArrayList<Double> args) {
		ArrayList<MathOp<Double>> ops = new ArrayList<>();
		//create ArrayList of ops
		ops = (ArrayList<MathOp<Double>>) functions.stream().map(x -> {
			MathOp<Double> o = new MathOp<Double>();
			o.load(x,args);
			return o;
		}).collect(Collectors.toList());
		
		return process(ops);
	}
	public static Double process(ArrayList<MathOp<Double>> ops) {
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
			//System.out.println(val1 + o.op + val2);
			results.add(op(val1,val2,o.op));
		}
		
		return results.get(results.size()-1);
	}
	//TODO duplicate code for floats / doubles
	public static Double op(Double x,Double y, String op) {
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
		return 0.0;
	}
	
	//program code generation
	
	//generation of functions?
	public static void SimpleFunctionGen(ArrayList<Double> vals) {
		//generates 
	}
	
	//helper classes
	public static class MathOp<T> {
		/**
		 * The result to reference
		 */
		public Integer result1 = null;
		public Integer result2 = null;
		public T val1,val2;
		public String op;
		
		public MathOp() {
			
		}
		public void load(FunctionalOp<T> function,ArrayList<T> parameters) {
			
			this.op = function.operation;
			switch (function.val1Ref) {
			case (FunctionalOp.CONST):
				this.val1 = function.val1Const;
			break;
			case (FunctionalOp.INPUT):
				this.val1 = parameters.get(function.val1Pointer);
			break;
			case (FunctionalOp.RESULT):
				this.result1 = function.val1Pointer;
			break;
			}
			
			switch (function.val2Ref) {
			case (FunctionalOp.CONST):
				this.val2 = function.val2Const;
			break;
			case (FunctionalOp.INPUT):
				this.val2 = parameters.get(function.val2Pointer);
			break;
			case (FunctionalOp.RESULT):
				this.result2 = function.val2Pointer;
			break;
			}
			
		}
		public MathOp(T val1,T val2,String op) {
			this.val1 = val1;
			this.val2 = val2;
			this.op = op;
		}
		public MathOp(String op,Integer r1,Integer r2) {
			this.result1 = r1;
			this.result2 = r2;
			this.op = op;
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
	
	/**
	 * Generates MathOps
	 * Holds a position in an arraylist or a result and an operation
	 * This is essentially a single function. make more complex functions
	 * by tossing this in an arraylist.
	 * @author josh.benton
	 *
	 */
	public static class FunctionalOp<T> {
		
		public static final int CONST = 0;
		public static final int INPUT = 1;
		public static final int RESULT = 2;
		public static ArrayList<Integer> REFS = new ArrayList<>();
		static {
			REFS.add(CONST);
			REFS.add(INPUT);
			REFS.add(RESULT);
		}
		public int val1Ref = 0;
		public int val2Ref = 0;
		public T val1Const,val2Const;
		public int val1Pointer,val2Pointer;
		public String operation;
		
		public FunctionalOp(String operation) {
			this.operation = operation;
		}
		public void setCVal1(T conValue) {
			this.val1Ref = CONST;
			this.val1Const = conValue;
		}
		public void setCVal2(T conValue) {
			this.val2Ref = CONST;
			this.val2Const = conValue;
		}
		public void setARef1(int i) {
			this.val1Ref = INPUT;
			this.val1Pointer = i;
		}
		public void setARef2(int i) {
			this.val2Ref = INPUT;
			this.val2Pointer = i;
		}
		public void setRRef1(int i) {
			this.val1Ref = RESULT;
			this.val1Pointer = i;
		}
		public void setRRef2(int i) {
			this.val2Ref = RESULT;
			this.val2Pointer = i;
		}
		
		public String toString() {
			return operation + " : "
					+ val1Ref + " ( " + val1Const + " | " + val1Pointer + " ) "
					+ val2Ref + " ( " + val2Const + " | " + val2Pointer + " ) ";
		}
	}
}
