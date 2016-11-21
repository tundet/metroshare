/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import model.Comment;
import model.Media;
import model.MediaTag;
import model.Tag;
import model.User;

/**
 * REST Web Service
 *
 * @author Mafields
 */
@Path("admin")
public class AdminResource {

    @Context
    private UriInfo context;
    
    @EJB
    private MetroShareSB mssb;

    /**
     * Creates a new instance of AdminResource
     */
    public AdminResource() {
    }

    /**
     * Retrieves representation of an instance of controller.AdminResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves representation of an instance of controller.AdminResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsersJson() {
        //TODO return proper representation object
        String ul = "{ \"users\": {";
        
        List<User> ulst = mssb.readAllUsers();
        // user found and returning json data about login priviliges and activity
        for (User u: ulst) {
            ul += "{\"id\": \"" + u.getId() + "\", ";
            ul += "\"username\": \"" + u.getLogin() + "\", ";
            ul += "\"password\": \"" + u.getPassword() + "\", ";
            ul += "\"privileges\": \"" + u.getPrivileges() + "\", ";
            ul += "\"activity\": \"" + u.getActivity() + "\"}, ";
        }
        ul += "}}";
        return ul;
    }
    
    /**
     * Retrieves representation of an instance of controller.AdminResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommentsJson() {
        //TODO return proper representation object
        String cl = "{ 'Comments': {";
        
        List<Comment> clst = mssb.readAllComments();
        // user found and returning json data about login priviliges and activity
        for (Comment c: clst) {
            cl += "'id': '" + c.getId() + "', ";
            cl += "'mediaid': '" + c.getMediaId() + "', ";
            cl += "'userid': '" + c.getUserId() + "', ";
            cl += "'message': '" + c.getMessage() + "', ";
            cl += "'date': '" + c.getDate() + "' ";
        }
        cl += "}}";
        return cl;
    }
    
    /**
     * Retrieves representation of an instance of controller.AdminResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/medias")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMediasJson() {
        //TODO return proper representation object
        String ml = "{ 'Medias': {";
        
        List<Media> mlst = mssb.readAllMedias();
        // user found and returning json data about login priviliges and activity
        for (Media m: mlst) {
            ml += "'id': '" + m.getId() + "', ";
            ml += "'userid': '" + m.getUserId() + "', ";
            ml += "'medialocation': '" + m.getMediaLocation() + "', ";
            ml += "'date': '" + m.getDate() + "' ";
            ml += "'nsfw': '" + m.getNsfw() + "', ";
            ml += "'tags': {";
            for (MediaTag mt :m.getMediaTagCollection()) {
                ml += "'tagid': '" + mt.getTagId().getId() + "', ";
                ml += "'tag': '" + mt.getTagId().getTag() + "' ";
            }
            ml += "}";
        }
        ml += "}}";
        return ml;
    }
    
    /**
     * Retrieves representation of an instance of controller.AdminResource
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTagsJson() {
        //TODO return proper representation object
        String tl = "{ 'Tags': {";
        
        List<Tag> tlst = mssb.readAllTags();
        // user found and returning json data about login priviliges and activity
        for (Tag t: tlst) {
            tl += "'id': '" + t.getId() + "', ";
            tl += "'tag': '" + t.getTag() + "', ";
        }
        tl += "}}";
        return tl;
    }
    
    /**
     * PUT method for updating or creating an instance of AdminResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
