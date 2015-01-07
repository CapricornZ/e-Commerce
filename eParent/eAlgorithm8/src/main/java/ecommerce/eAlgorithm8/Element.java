package ecommerce.eAlgorithm8;

import java.util.Arrays;

import ecommerce.base.Decide;

public class Element {
	
	private char[] data0, data1;
	public Element(String data){
		data0 = data.substring(0,6).toCharArray();
		data1 = data.substring(6,12).toCharArray();
	}
	
	public Decide execute(){
		
		char[] data = Arrays.copyOf(data1, data1.length);
		Result result0 = Result.parse(data0);
		Result result1 = null;
		{//test 'A'
			data[data.length-1] = 'A';
			result1 = Result.parse(data);
		}
		if(!result0.equals(result1)){//test 'B'
			data[data.length-1] = 'B';
			result1 = Result.parse(data);
		}
		
		if(!result0.equals(result1))
			return Decide.INVALID;
		
		return this.data1[data1.length-1] == data[data1.length-1]?Decide.TRUE:Decide.FALSE;
	}

	private static class Result{
		
		public static Result parse(char[] data){
			return new Result(data);
		}
		
		private int[] vs = new int[2];
		private char val;
		
		public boolean equals(Result other){
			return this.vs[0]==other.vs[0] && this.vs[1]==other.vs[1] && this.val==other.val;
		}
		
		public Result(char[] data){
			
			int countOfA = 0, countOfB = 0;
			int i=0;
			for(; i<data.length-1; i++){
				char c = data[i];
				if(c=='A')
					countOfA++;
				else
					countOfB++;
			}
			
			if(countOfA>countOfB){
				if(data[i]=='A'){
					countOfA++;
					this.val='D';
				}
				else{
					countOfB++;
					this.val='S';
				}
			}else{
				if(data[i]=='A'){
					countOfA++;
					this.val='S';
				}
				else{
					countOfB++;
					this.val='D';
				}
			}
			this.vs[0] = countOfA>countOfB?countOfA:countOfB;
			this.vs[1] = countOfB>countOfA?countOfA:countOfB;
		}
	}

}
