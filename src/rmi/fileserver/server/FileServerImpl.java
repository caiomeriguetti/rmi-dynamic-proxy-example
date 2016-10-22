package rmi.fileserver.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.fileserver.common.FileInfo;
import rmi.fileserver.common.FileServer;

public class FileServerImpl extends UnicastRemoteObject implements FileServer {

	private static final long serialVersionUID = 1L;
	
	private String directoryToServe;
	
	protected FileServerImpl(String directory) throws RemoteException {
		super();
		if (!directory.endsWith("/")) {
			directory = directory + "/";
		}
		
		this.directoryToServe = directory;
	}

	@Override
	public boolean upload(byte[] file, String destination) throws RemoteException {
		
		try {
			String filePath = this.directoryToServe + destination;
			filePath = filePath.replace("//", "/");
			
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(file);
			fos.close();
			
			return true;
		} catch (Exception e) {
			throw new RemoteException("Problem uploading the file: " + e.getMessage());
		}
	}

	@Override
	public byte[] download(String fileName) throws RemoteException {

		String filePath = this.directoryToServe + fileName;
		filePath = filePath.replace("//", "/");
		
		File f = new File(filePath);
		if (!f.exists()) {
			throw new RemoteException("The file " + fileName + " doesnt exist");
		}
		
		Path path = FileSystems.getDefault().getPath(filePath);
		try {
			byte[] content = Files.readAllBytes(path);
			return content;
		} catch (IOException e) {
			throw new RemoteException("Problem reading the file");
		}

	}

	@Override
	public FileInfo[] getAllFiles() throws RemoteException {
		
		File folder = new File(this.directoryToServe);
		File[] listOfFiles = folder.listFiles();
		
		FileInfo[] infos = new FileInfo[listOfFiles.length];
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	
			try {
				System.out.println(i + " -> " + listOfFiles[i].getName());
		    	FileInfo info = new FileInfo();
				info.setName(listOfFiles[i].getName());
				info.setSize(listOfFiles[i].length());
				
				if (listOfFiles[i].isFile()) {
					info.setDirectory(false);
				} else if (listOfFiles[i].isDirectory()) {
					info.setDirectory(true);
				}
				
				infos[i] = info;
			} catch (Exception e) {
				throw new RemoteException(e.getMessage());
			}
	    }
		
		return infos;
	}

}
