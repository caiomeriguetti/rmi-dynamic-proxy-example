package rmi.fileserver.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class FileServerApp {
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		String filesDir = "/home/caiomeriguetti/ciencia_comp/sistemas_distribuidos/rmi/files";
		
		FileServerImpl impl = new FileServerImpl(filesDir);
		
		Naming.rebind("rmi://0.0.0.0/file-server", impl);
		
		System.out.println("FileServer is running...");
	}
}
