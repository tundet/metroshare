package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Friend;
import model.Media;
import model.User;

/**
 * Users resource.
 * 
 * This resource is responsible for retrieving users, their media, friends,
 * last activity time.
 */
@Path("users")
public class UsersResource {

    @Context
    private UriInfo context;

    @EJB
    private MetroShareSB mssb;

    /**
     * Create a JSON object from the given user containing,
     * the username and also friends and media of the user.
     *
     * @param login Username of the user
     * @return JSON object of the user and its media and friends
     */
    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("login") String login) {
        User u = mssb.readUserByLogin(login);
        Map<String, Object> config = new HashMap<String, Object>();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        
        // Media.
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : u.getMediaCollection()) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("title", m.getTitle())
                    .add("date", m.getDate().toString())
                    .add("nsfw", m.getNsfw())
                    .add("mediaLocation", m.getMediaLocation()).build();
            builder.add(mediaValue);
        }
        JsonArray mediaA = builder.build();
        
        // Friends.
        builder = Json.createArrayBuilder();
        for (Friend f : u.getFriendCollection()) {
            JsonObject friendValue = Json.createObjectBuilder()
                    .add("friend", f.getFriendId().getLogin()).build();
            builder.add(friendValue);
        }
        JsonArray friendsA = builder.build();
        
        
        JsonArray value = factory.createArrayBuilder()
                .add(factory.createObjectBuilder()
                        .add("login", u.getLogin())
                        .add("media", mediaA)
                        .add("friends", friendsA))
                .build();

        return value.toString();
    }
    
    /**
     * Get the matching username from the given session ID.
     *
     * @param username Username of the user
     * @return Username of the matching user
     */
    @GET
    @Path("/friends/media/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFriendsMediaJson(@PathParam("username") String username) {
        String r = "";
        User u = mssb.readUserByLogin(username);
        String ids = "";
        
        for (Friend f : u.getFriendCollection()) {
            ids += f.getFriendId().getId() + ",";
        }
        
        if (ids.endsWith(",")){
            ids = ids.substring(0, ids.length() -1);
        }
        
        List<Media> mlst = mssb.readMediaFromFriends(ids);
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (Media m : mlst) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }

        JsonArray mediaA = builder.build();
        
        return mediaA.toString();
    }
    
    /**
     * Get the matching username from the given session ID.
     *
     * @param sessionid Session ID of the user
     * @return Username of the matching user
     */
    @GET
    @Path("/sessionid/{sessionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSessionJson(@PathParam("sessionid") String sessionid) {
        String r = "";
        
        User u = mssb.readUserBySessionID(sessionid);
        
        r += "{\"username\":\"" + u.getLogin() + "\"}";
        
        return r;
    }
    
    /**
     * Get a user by its session ID.
     * 
     * @param sessionid Session ID of the user
     * @return User object of the matching user
     */
    @GET
    @Path("/getuser/{sessionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserBySession(@PathParam("sessionid") String sessionid) {
        return mssb.readUserBySessionID(sessionid);
    }
    
    /**
     * Get the last activity time of the given user.
     * 
     * Date format is YYYY-MM-DD HH:MM:SS.
     *
     * @param login Username of the user
     * @return Last activity time
     */
    @GET
    @Path("/{login}/activity")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserJson(@PathParam("login") String login) {
        String ua = "";
        
        User u = mssb.readUserByLogin(login);
        
        ua += "{\"activity\":\"" + u.getActivity() + "\"}";
        
        return ua;
    }
}
