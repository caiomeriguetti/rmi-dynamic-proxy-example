package rmi.fileserver.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.fileserver.common.FileInfo;
import rmi.fileserver.common.FileServer;

public class FileServerClientApp {
	public static void main(String[] args) throws NotBoundException, IOException {
		
		FileServer serverProxy = FileServerClientFactory.create("localhost");
		
		try {
			
			//
			// DOWNLOAD
			// 
			
			String fileToDownload = "atividade-rmi.apdf";
			
			byte[] downloadedFile = serverProxy.download(fileToDownload);
			
			FileOutputStream fos = new FileOutputStream("/tmp/arquivo.pdf");
			fos.write(downloadedFile);
			fos.close();
			
			System.out.println("Downloaded file " + fileToDownload);

		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			//
			// UPLOAD
			//
			Path path = FileSystems.getDefault().getPath("/home/caiomeriguetti/Pictures/download.jpg");
			try {
				byte[] content = Files.readAllBytes(path);
				serverProxy.upload(content, "uploaded-download.jpg");
				System.out.println("Uploaded file " + "download.jpg");
			} catch (IOException e) {
				throw new RemoteException("Problem reading the file");
			}
			
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			//
			// LIST OF FILES
			// 
			
			FileInfo[] infos = serverProxy.getAllFiles();
			
			for (FileInfo info: infos) {
				System.out.println(info);
			}
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}

	}
}
