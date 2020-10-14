package com.ibm.gbs.cai.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/simon")
public class Plan {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String doStuff()
	{
		if((new File("/my-special-folder")).exists()) {
			if(new File("/my-special-folder").canRead()){
				File Dockerfile = new File("/my-special-folder/Dockerfile");
				if(Dockerfile.exists()) {
					if(Dockerfile.canRead()) {
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/my-special-folder/Dockerfile")));
							StringBuffer retVal = new StringBuffer();
							String line;
							while((line = br.readLine()) != null)
								retVal.append(line + "\n");
							br.close();
							retVal.deleteCharAt(retVal.length() - 1);
							String secretVar = System.getenv("MY_SECRET_VAR");
							if(secretVar != null && !secretVar.equals(""))
								retVal.append("\nThe secret is " + secretVar + "\n");
							return retVal.toString();
						} catch (FileNotFoundException e) {
							return "Programmer error: Unexpected FileNotFoundException";
						} catch (IOException e) {
							return "Programmer error: Unexpected IOException";
						}
					}else
						return "/my-special-folder/Dockerfile is not readable";
				}else
					return "/my-special-folder/Dockerfile does not exist";
			}else{
				return "/my-special-folder has the wrong permissions";
			}
		}else
			return "/my-special-folder does not exist";
	}
}
