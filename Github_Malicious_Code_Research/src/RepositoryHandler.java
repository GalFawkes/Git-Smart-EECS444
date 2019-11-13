import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class RepositoryHandler {
	public static String FS = System.getProperty("file.separator");
	
	public static String GIT_DOWNLOAD_TAR_URL = "/archive/master.tar.gz";
	public static String GIT_DOWNLOAD_ZIP_URL = "/archive/trunk.zip";
	public static String TEMP_FOLDER = FS+"temp"+FS;
	public static String TAR_EXTENSION = ".tar.gz";
	public static String ZIP_EXTENSION = ".zip";
	
	public static String TEST_SCAN = "C:\\Users\\short\\compusec-f2019-project\\Github Malicious Code Research\\testFiles\\testScan";
	
	public URL url;
	public File repository;
	public ArrayList<VirusCheck> results;
	
	public static void main(String[] args){
		RepositoryHandler rph = new RepositoryHandler("https://github.com/CSho27/SoftwareSecurity_HW2");
		rph.close();
	}
	
	public RepositoryHandler(String url){
		URL tarUrl = null;
		URL zipUrl = null;
		try {
			tarUrl = new URL(url+GIT_DOWNLOAD_TAR_URL);
			zipUrl = new URL(url+GIT_DOWNLOAD_ZIP_URL);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		
		String[] tokens = url.toString().split("/");
		String filename = tokens[tokens.length-1];
		
		if(tarUrl != null && zipUrl != null){
			try{
				File file = new File(System.getProperty("user.dir")+TEMP_FOLDER+filename+TAR_EXTENSION);
				FileUtils.copyURLToFile(tarUrl,file);
				this.url = tarUrl;
				this.repository = file;
			} catch (FileNotFoundException e){
				try{
					File file = new File(System.getProperty("user.dir")+TEMP_FOLDER+filename+ZIP_EXTENSION);
					FileUtils.copyURLToFile(zipUrl,file);
					this.url = zipUrl;
					this.repository = file;
				} catch(FileNotFoundException nfe){
					System.out.println("Error: Could not find:"+ nfe.getMessage());
					this.url = tarUrl;
				} catch (IOException e1) {
					e1.printStackTrace();
					this.url = tarUrl;
				}
			} catch (IOException e) {
				e.printStackTrace();
				this.url = tarUrl;
			}
		} else {
			this.url = tarUrl;
		}
	}
	
	public void close() {
		if(repository != null){
			repository.delete();
		}
	}
	
	private boolean emptyAndDelete(File file){
		boolean success = true;
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f: files){
				if(!emptyAndDelete(f))
					success = false;
			}
			if(!file.delete()){
				System.out.println("ERROR: "+file.getName()+" failed to be deleted");
				success = false;
			}
			return success;
		} else {
			success = file.delete();
			if(!success)
				System.out.println("ERROR: "+file.getName()+" failed to be deleted");

			return success;
		}
	}
	
	private void unzipTar(File in, File out) throws FileNotFoundException, IOException{
		TarArchiveInputStream inputStream = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(in)));
		TarArchiveEntry entry;
		while((entry = inputStream.getNextTarEntry()) != null) {
			if(entry.isDirectory()){
				continue;
			}
			File curFile = new File(out, entry.getName());
			File parent = curFile.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream(curFile);
			IOUtils.copy(inputStream, outputStream);
			outputStream.close();
		}
		inputStream.close();
	}
	
	private ArrayList<File> scanForBinaries(File file){
		ArrayList<File> binaries = new ArrayList<File>();
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f: files){
				binaries.addAll(scanForBinaries(f));
			}
			return binaries;
		} else {
			if(isExecutable(file)){
				binaries.add(file);
			}
			return binaries;
		}
	}
	
	private static boolean isExecutable(File file) {
		  byte[] firstBytes = new byte[4];
		  try {
		    FileInputStream input = new FileInputStream(file);
		    input.read(firstBytes);
		    input.close();
		    
		    //windows executable
		    if(firstFourBytesEqual(firstBytes, "4d5a")){
		    	return true;
		    //java class file
		    }else if(firstFourBytesEqual(firstBytes, "cafebabe")){
		    	return true;
		    //jar file
		    }else if(firstFourBytesEqual(firstBytes, "504B0304")){
		    	return true;
		    //linux run file
		    }else if(firstFourBytesEqual(firstBytes, "7f454c46")){
		    	return true;
		    } else {
		    	return false;
		    }
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return false;
		  }
	}
	
	private static boolean firstFourBytesEqual(byte[] bytes, String hexString){
		byte[] hexBytes = new byte[(hexString.length()/2)];
		char[] chars = hexString.toCharArray();
		
		for(int i=0; i<4 && i<hexString.length()/2; i++){
			if(bytes[i] != Integer.parseInt(""+chars[i*2]+chars[i*2+1], 16)){
				return false;
			}
		}
		return true;
	}
	
	ResponseHandler<String> repoDownloadResponseHandler = response -> {
		int status = response.getStatusLine().getStatusCode();
		 if (status >= 200 && status < 300) {
		  HttpEntity entity = response.getEntity();
		  return entity != null ? EntityUtils.toString(entity) : null;
		 } else {
		  throw new ClientProtocolException("Unexpected response status: " + status);
		 }
	};
}
