package ecommerce.base;

import org.apache.velocity.VelocityContext;

public class Context {
	private VelocityContext context = new VelocityContext();
	
	public void put(String key, Object obj){
		this.context.put(key, obj);
	}
	
	public void mergeContext(Context context){
		for(Object key : context.getContext().getKeys())
			this.context.put((String)key, context.getContext().get((String)key));
	}
	
	public VelocityContext getContext(){
		return this.context;
	}
}
