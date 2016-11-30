package net.devtrainer.foogl.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.gdx.utils.Array;

/**
 * Dynamic type callback interface
 * @author twachi
 *
 */
public class CallBack{
	Array<Handler> list=new Array<Handler>();
	Class[] parameterTypes;
	String template_txt = "";
	
	public CallBack(Class... parameterTypes) {
		super();
		this.parameterTypes = parameterTypes;
		init();
	}
	public CallBack(Class eventType) {
		super();
		Method template_method=null;
		for(Method m:eventType.getMethods()){
		  if(m.getName().equals("handle")){
			  template_method = m;
			  break;
		  }
		}
		if(template_method==null){
			throw new RuntimeException("The handle method is not found.");
		}		
		this.parameterTypes=template_method.getParameterTypes();
		init();
	}
	
	private void init () {
		template_txt="handle(";
		int i=0;
		for(Class c:this.parameterTypes){
			if(i>0) template_txt += ",";
			template_txt += c.getName();
			i++;
		}
		template_txt+=")";
	}

	public void addHandler(Object handler){
		addHandler(handler,"handle");
	}
	public void addHandler(Object handler,String method){
	   try {	   	
			Method m = handler.getClass().getMethod(method,parameterTypes);
			Handler h=new Handler(handler, m);
			if(!list.contains(h,true)){
		  	  list.add(h);   
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("The valid handle method is not found:"+template_txt);
		}	   
	}
	public void removeHandler(Object handler,String method){
	   try {
			Method m = handler.getClass().getMethod(method,parameterTypes);
			Handler h=new Handler(handler, m);
			if(!list.contains(h,true)){
		  	  list.removeValue(h,true);   
			}
		} catch (Exception e) {
		}   	
	}
		
	public void handle(Object ...args){
		for(Handler h:list){
			h.invoke(args);
		}
	}	
	public Array<Handler> getList () {
		return list;
	}
	public Class[] getParameterTypes () {
		return parameterTypes;
	}
	public String getTemplate_txt () {
		return template_txt;
	}


	class Handler{
		Object obj;
		Method method;
		public Handler (Object obj, Method method) {
			super();
			this.obj = obj;
			this.method = method;
		}
		public void invoke(Object ...args){
			try {
				method.invoke(obj,args);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public boolean equals (Object obj) {
			if(obj instanceof Handler){
				Handler h=(Handler)obj;
				return (h.obj==obj) && (h.method==method);
			}
			return false;
		}
		
	}	
}
