/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Friend;
import model.Media;
import model.User;

/**
 * REST Web Service
 *
 * @author Mafields
 */
@Path("users")
public class UsersResource {

    @Context
    private UriInfo context;

    @EJB
    private MetroShareSB mssb;

    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }

    /**
     * Retrieves representation of an instance of controller.UsersResource
     *
     * @param login
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("login") String login) {
        //TODO return proper representation object
        User u = mssb.readUserByLogin(login);
        Map<String, Object> config = new HashMap<String, Object>();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        
        //Media
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
        
        //Friends
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
     * Retrieves representation of an instance of controller.UsersResource
     *
     * @param login
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/sessionid/{sessionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSessionJson(@PathParam("sessionid") String sessionid) {
        //TODO return proper representation object
        String r = "";
        User u = mssb.readUserBySessionID(sessionid);
        r += "{\"username\":\"" + u.getLogin() + "\"}";
        return r;
    }
    
    /**
     * Retrieves representation of an instance of controller.UsersResource
     *
     * @param login
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{login}/activity")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserJson(@PathParam("login") String login) {
        //TODO return proper representation object
        String ua = "";
        User u = mssb.readUserByLogin(login);
        ua += "{\"activity\":\"" + u.getActivity() + "\"}";
        return ua;
    }

    /**
     * PUT method for updating or creating an instance of UsersResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
