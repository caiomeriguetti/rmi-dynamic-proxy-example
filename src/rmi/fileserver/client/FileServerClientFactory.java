package rmi.fileserver.client;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.fileserver.common.FileServer;

public class FileServerClientFactory {
	
	public static FileServer create(String host) throws MalformedURLException, RemoteException, NotBoundException {
		Object serverRemoteObject = Naming.lookup("rmi://" + host + "/file-server");
		
		FileServer serverProxy = (FileServer) Proxy.newProxyInstance(FileServer.class.getClassLoader(),
				new Class[] { FileServer.class }, new DynamicProxy(serverRemoteObject));
		
		return serverProxy;
	}

}
