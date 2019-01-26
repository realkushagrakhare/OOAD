package org.iiitb.OOAD;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
        String user_name = user_d.getString("username");
        
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
}
