package Cyberia.CyberiaFramework.util.codeGen.logicGen;

import java.util.List;

/**
 * Class for generating aggregate functions
 * (things like Sums / averages / ect)
 * @author josh.benton
 *
 */
public class AggregateGen {
	
	
	public Double process(List<Double> ToAggregate, List<AggregateOp> ops,int size) {
		return ToAggregate.stream().reduce((i, j) -> exec(i,j,ops)).get();
	}
	
	
	private Double exec(Double i, Double j, List<AggregateOp> ops) {
		
		return null;
	}


	public static class AggregateOp {
		public static final int ADD = 0;
		public static final int MULT = 1;
	}
}
