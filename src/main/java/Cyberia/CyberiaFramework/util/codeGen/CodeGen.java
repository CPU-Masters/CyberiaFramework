package Cyberia.CyberiaFramework.util.codeGen;

import java.util.stream.Stream;

import javax.json.JsonObject;

import org.omg.CORBA.Environment;

/**
 * This is a static class for generating code
 * @author Inhaler
 *
 */
public class CodeGen {
	
	public static String genClassForJson(JsonObject jsonObj) {
		
		String codeGen = "";
		Stream<CodeGenVar> CodeGenVars = jsonObj.entrySet().stream().map(
				x -> new CodeGenVar(x.getKey(),x.getValue()));
		
		codeGen += CodeGenVars.map(x -> x.getVarDefine()).reduce((i,j) -> i+System.lineSeparator()+j).get();
		
		return codeGen;
	}

	/**
	 * Handles creating variables in generated code
	 * designed to be a simple class that handles complexity.
	 * @author Inhaler
	 *
	 */
	public static class CodeGenVar {
		public String varType = "String";
		public String varName , varDefaultValue;
		
		
		public CodeGenVar(String name, Object val) {
			this.varName = name;
			this.determineType(val);
		}

		public CodeGenVar(String name,String type) {
			this.varType = type;
			this.varName = name;
		}
		
		public CodeGenVar(String name,String type, String defaultVal) {
			this.varType = type;
			this.varName = name;
			this.varDefaultValue = defaultVal;
		}
		
		//Generating code
		
		public String getVarDefine() {
			String rtrn = "Public " + varType + " " + varName;
			if (varDefaultValue != null && !varDefaultValue.isEmpty()) {
				
				rtrn += " = " + this.getDefaultValue();//ADD QUOTES for string
				
			}
				rtrn += ";" + System.lineSeparator();
			
			
			
			return rtrn;
		}
		
		public String getDefaultValue() {
			String rtrn;
			
			if (varType.equals("String")) {
				rtrn = "\"" + varDefaultValue + "\"";
			} else {
			rtrn  = getDefaultValue();
			}
			return rtrn;
		}
		
		public void determineType(Object val) {
			
			if (val instanceof String) {
			this.varType = "String";	
			} else if (val instanceof Long) {
				this.varType = "Long";
			} else if (val instanceof Integer) {
				this.varType = "Integer";
			}  else if (val instanceof Double) {
				this.varType = "Double";
			} else if (val instanceof Float) {
			this.varType = "Float";
			} else {
				this.varType = "Object";
			}
		}
	}
	

}
