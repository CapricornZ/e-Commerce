package ecommerce.eAlgorithm11;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;

public class ResultRow implements IResultRow {
	
	static private final Logger logger = LoggerFactory.getLogger(ResultRow.class);
	
	private Relay[] relays;
	private int startOff;
	private List<RelayPosition> relayPos;
	public List<RelayPosition> getRelayPositions(){return this.relayPos;}
	
	public ResultRow(Relay relay0, Relay relay1, int startOff){
		this.relays = new Relay[]{relay0, relay1};
		this.startOff = startOff;
	}

	@Override
	public IRow run() {
		return null;
	}

	@Override
	public void print() {
		//logger.debug("<Process>\r\n");
		for(int relayIndex=0; relayIndex<relays.length; relayIndex++){
			for(int rowIndex=0; rowIndex<relays[relayIndex].getLengthOfElement(); rowIndex++){
				StringBuilder sbRow = new StringBuilder();
				for(int columnIndex=0; columnIndex<relays[relayIndex].getElements().size(); columnIndex++)
					sbRow.append(this.relays[relayIndex].getValue(rowIndex, columnIndex));
				logger.debug("{}\r\n", sbRow.toString());
			}
			logger.debug("\r\n");
		}
		//logger.debug("</Process>\r\n");
	}

	@Override
	public List<ITrueAndFalse> getResult() {

		List<List<Boolean>> total = new ArrayList<List<Boolean>>();
		//List<Boolean> total = new ArrayList<Boolean>();
		this.relayPos = new ArrayList<RelayPosition>();
		Relay.Result result = null;
		int i=0;
		result = this.relays[i].run(this.startOff);
		if(result.position!=null)
			this.relayPos.add(result.position);
		while(result.result.size()>0){
			total.addAll(result.detail);
			//total.add(result.result);
			//total.addAll(result.result);
			//swap
			result = this.relays[(++i)%2].run(result.startOff);
			if(result.position!=null)
				this.relayPos.add(result.position);
		}
		List<ITrueAndFalse> rtn = new ArrayList<ITrueAndFalse>();
		rtn.add(new TrueAndFalseEx(total));
		return rtn;
	}

}
