package elements.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eAlgorithmWS.ISourceRow;
import eAlgorithmWS.Item;
import elements.algorithmX.IAlgorithm;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	class ItemComparator implements Comparator<Item>{
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getIndex() - o2.getIndex();
		}
	}
	
	private IAlgorithm[] algorithms;
	public IAlgorithm[] getAlgorithms() {
		return algorithms;
	}
	public void setAlgorithms(IAlgorithm[] algorithms) {
		this.algorithms = algorithms;
	}

	@RequestMapping("/helloworld")
	@ResponseBody
    public List<String> helloworld(@RequestParam("source")String source) {

		List<Phase> phases = new ArrayList<Phase>();
		
		eAlgorithmWS.MultiSourceRow multi = new eAlgorithmWS.MultiSourceRow(source);
		for(eAlgorithmWS.ISourceRow single : multi.generate()){

			List<eAlgorithmWS.ITrueAndFalse> tafs = new ArrayList<eAlgorithmWS.ITrueAndFalse>();
			for(IAlgorithm algorithm : this.algorithms)
				tafs.add(algorithm.execute(single));
			
			List<Item> tmp = new ArrayList<Item>();
			tmp.addAll(tafs.get(0).getSource());
			for(int i=1; i<tafs.size(); i++){
				int size = tmp.size();
				for(Item item : tafs.get(i).getSource()){
					boolean match = false;
					for(int j=0; j<size && !match; j++){
						if(item.getIndex() == tmp.get(j).getIndex()){
							match = true;
							tmp.get(j).setCount(item.getCount() + tmp.get(j).getCount());
						}
					}
					if(!match){
						tmp.add(item);
					}
				}
				Collections.sort(tmp, new ItemComparator());
			}
			phases.add(new Phase(tmp, single));
		}//for

		List<String> rtn = new ArrayList<String>();
		
		String sourceRow = "";
		/*for(int index=0; index<multi.getSource().length(); index++){
			int max = 0;
			for(Phase phase : phases){
				for(int i=0; i<phase.getResult().size(); i++){
					List<Item> row = phase.getResult();
					if(row.get(i).getIndex() == index)
						max = max>row.get(i).getCount()?max:row.get(i).getCount();
				}
			}
			sourceRow += multi.getSource().charAt(index);
			int size = String.format("%d", max).length();
			for(int i=0; i<size-1; i++)
				sourceRow += " ";
		}*/
		sourceRow = multi.getRealSource();
		
		rtn.add(sourceRow);
		String format = multi.getFormatSource();
		System.out.println(format);rtn.add(format);
		
		for(Phase phase:phases){

			List<Item> row = phase.getResult();			
			String result = "";
			int startOff = 1;
			System.out.println(String.format("%d vs %d", row.get(row.size()-1).getIndex(), multi.getSource().length()-1));
			int size = row.get(row.size()-1).getIndex() == multi.getSource().length()-1 ? row.size()-1:row.size();
			//int size = row.size();
			for(int index=0; index<size; index++){
				Item item = row.get(index);
				for(int i=startOff; i<item.getIndex(); i++)
					result += " ";
				int count = Math.abs(item.getCount());
				result += String.format("<font color=%s>%d</font>", item.getCount()>0?"RED":"BLUE", count);
				
				startOff = item.getIndex()+1;
			}
			if(row.get(row.size()-1).getIndex() == multi.getSource().length()-1){
				Item item = row.get(row.size()-1);
				if(item.getCount()>0)
					result += String.format("\t(%c*%d)", item.getCharactor(), item.getCount());
				else if(item.getCount()<0)
					result += String.format("\t(%c*%d)", item.getCharactor()=='A'?'B':'A', Math.abs(item.getCount()));
			}
			
			/*for(Item item:row){
				for(int i=startOff; i<item.getIndex(); i++)
					result += " ";
				
				int count = Math.abs(item.getCount());
				result += String.format("<font color=%s>%d</font>", item.getCount()>0?"RED":"BLUE", count);
				
				startOff = item.getIndex()+1;
			}*/
			System.out.println(result);rtn.add(result);
		}
		
		Result result = new Result();
		result.setSourceRow(sourceRow);
		result.setFormatSourceRow(format);
        return rtn;
    }
}