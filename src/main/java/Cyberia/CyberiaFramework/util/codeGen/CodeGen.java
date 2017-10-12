package Cyberia.CyberiaFramework.util.codeGen;

import javax.json.JsonObject;

/**
 * This is a static class for generating code
 * @author Inhaler
 *
 */
public class CodeGen {
	
	public static void genClassForJson(JsonObject jsonObj) {
		
	}

	
	
	/**
	 * Handles creating variables in generated code
	 * designed to be a simple class that handles complexity.
	 * @author Inhaler
	 *
	 */
	public class CodeGenVar {
		public String varType = "String";
		public String varName , varDefaultValue;
		
		
		public CodeGenVar(String type,String name) {
			this.varType = type;
			this.varName = name;
		}
		
		public CodeGenVar(String type,String name, String defaultVal) {
			this.varType = type;
			this.varName = name;
			this.varDefaultValue = defaultVal;
		}
	}
}
