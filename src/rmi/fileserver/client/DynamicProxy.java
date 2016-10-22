package rmi.fileserver.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

public class DynamicProxy implements InvocationHandler {
	
	Object instance;
	
	public DynamicProxy(Object instance) {
		this.instance = instance;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Method toBeCalled = this.instance.getClass().getMethod(method.getName(), method.getParameterTypes());
			Object result = toBeCalled.invoke(this.instance, args); 
			return result;
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			throw new RemoteException(cause.getMessage());
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

}
