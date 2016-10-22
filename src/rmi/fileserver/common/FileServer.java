package rmi.fileserver.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileServer extends Remote {
	public boolean upload(byte[] file, String destination) throws RemoteException;
	public byte[] download(String fileName) throws RemoteException;
	public FileInfo[] getAllFiles() throws RemoteException;
}
