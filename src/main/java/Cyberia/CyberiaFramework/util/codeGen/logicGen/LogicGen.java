package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.ArrayList;

public class LogicGen {
	private static final String GREATER = ">";
	private static final String GREATEREQUAL = ">=";
	private static final String LESSEQUAL = "<=";
	private static final String LESS = "<";
	//Constants for operations
	public static ArrayList<String> singleOps = new ArrayList<>();
	static {
		singleOps.add(GREATER);
		singleOps.add(GREATEREQUAL);
		singleOps.add(LESSEQUAL);
		singleOps.add(LESS);
	}
}
