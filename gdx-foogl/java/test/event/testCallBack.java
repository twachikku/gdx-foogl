package test.event;

import net.devtrainer.foogl.event.CallBack;

public class testCallBack {

	public testCallBack () {
		// TODO Auto-generated constructor stub
	}

	public void test1(int x,int y){
		System.out.println("test1 x:"+x+" y:"+y);
	}
	public void test2(int x,int y){
		System.out.println("test2 x:"+x+" y:"+y);
	}
	public void test3(int x,int y){
		System.out.println("test3 x:"+x+" y:"+y);
	}
	public void handle(int x,int y){
		
	}
	public static void main (String[] args) {
		testCallBack a=new testCallBack();
		CallBack callback = new CallBack(testCallBack.class);

		callback.addHandler(a,"test1");
		callback.addHandler(a,"test2");
		callback.addHandler(a,"test3");
		callback.handle(4,5);
	}

}
