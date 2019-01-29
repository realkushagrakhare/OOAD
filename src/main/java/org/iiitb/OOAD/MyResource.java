package org.iiitb.OOAD;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
		@Path("userInfo")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserInfo(String user) throws Exception {
        JSONObject user_d = new JSONObject(user);
        String user_name = user_d.getString("user_name");
        
        DatabaseConnection dc = new DatabaseConnection();
        return dc.authenticateUser(user_name);
     }
		@Path("itemInfo")
	    @POST
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getItemInfo(String item) throws Exception{
	      
	    	JSONObject item_d = new JSONObject(item);
	    	int item_id = item_d.getInt("item_id");
	    	
	    	DatabaseConnection dc = new DatabaseConnection();
	    	return dc.getItemDetails(item_id);
	    }
		
		@Path("addUser")
	    @POST
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    @Produces(MediaType.TEXT_PLAIN)
	    public String addUser(
	    		@FormDataParam("path") InputStream uploadedInputStream,
	    		@FormDataParam("path") FormDataContentDisposition fileDetail,
	    		@FormDataParam("user_name") String name,
	    		@FormDataParam("dob") String dob) throws Exception{
	     	    		 	
	    	DatabaseConnection dc = new DatabaseConnection();
	    	String relPath = "images/" + name + "_" + fileDetail.getFileName();
	    	
	    	
	    	String uploadedFileLocation = "/Users/sankeerth/Documents/iiitb/ooad/OOAD/src/main/webapp/images/" + name  + fileDetail.getFileName();
//	    	String uploadedFileLocation = "/Users/sankeerth/Documents/iiitb/ooad/OOAD/src/main/webapp/images/" + name + "_" + fileDetail.getFileName();
	    	String fpath="images/"+ name+fileDetail.getFileName();

	    	writeToFile(uploadedInputStream, uploadedFileLocation);
	    	String dob_array[] = dob.split("-");
	    	String new_dob = dob_array[2]+"-"+dob_array[1]+"-"+dob_array[0];
	    	
	    	dob = new_dob;
	    	/*
	    	 * Code to store image in path
	    	 */
	    	if(dc.addUser(name, dob,fpath ))
	    	{
	    		return "Success";
	    	}
	    	else
	    	{
	    		return "Failure";
	    	}
//	    	return null;
	    }
		
		@Path("updateUser")
	    @POST
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    @Produces(MediaType.TEXT_PLAIN)
		public String updateUser(
	    		@FormDataParam("path") InputStream uploadedInputStream,
	    		@FormDataParam("path") FormDataContentDisposition fileDetail,
	    		@FormDataParam("id") String id,
	    		@FormDataParam("user_name") String name,
	    		@FormDataParam("dob") String dob) throws Exception{
	     	    		 	
	    	DatabaseConnection dc = new DatabaseConnection();
//	    	String relPath = "images/" + name + "_" + fileDetail.getFileName();
	    	String uploadedFileLocation = "/Users/sankeerth/Documents/iiitb/ooad/OOAD/src/main/webapp/images/" + name + "_" + fileDetail.getFileName();
	    	writeToFile(uploadedInputStream, uploadedFileLocation);
	    	
	    	String dob_array[] = dob.split("-");
	    	String new_dob = dob_array[2]+"-"+dob_array[1]+"-"+dob_array[0];
	    	
	    	dob = new_dob;
	    	/*
	    	 * Code to store image in path
	    	 */
	    	if(dc.updateUser(id,name, dob,uploadedFileLocation ))
	    	{
	    		return "Success";
	    	}
	    	else
	    	{
	    		return "Failure";
	    	}
//	    	return null;
	    }
		
		private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation)
		{
			try
			{
				OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1)
				{
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			}catch (IOException e)
			{

				e.printStackTrace();
			}

		}
		
}
