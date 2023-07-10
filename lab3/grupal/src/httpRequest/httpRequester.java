package httpRequest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.File;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

public class httpRequester {

	/* Write content in a file */
	private static String writeInFile(StringBuffer content) {
			String outputFile = "archivoSalida.out";
			
			try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile))){
				byte[] buffer = content.toString().getBytes();
				output.write(buffer);
				return outputFile;
			}
			catch (Exception e){
				e.printStackTrace();
				return outputFile;
			}
	}
    
	/* Delete a file in `path` */
	public void deleteFile(String path){
        File f = new File(path);
		if (!f.delete()){
            System.out.println("No se puede borrar " + path);
        }
    }

	/* Get feed content from a given url */
	public String getFeed(String urlFeed) {
		try {
			URL url = new URL(urlFeed);
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Create connection
			con.setRequestProperty("User-Agent", "agent");
			con.setRequestMethod("GET");						// Make a request

			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));	// Read to buffer
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {		// Write content to inputLine
				content.append(inputLine);
			}
			in.close();											// Close reader
			con.disconnect();									// Disconnect
			
			String outputFile = writeInFile(content);			// Write content in a file
			return outputFile;
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
			return "";
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
