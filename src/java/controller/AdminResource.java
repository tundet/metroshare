/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
     *
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
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsersJson() {
        //TODO return proper representation object

        List<User> ulst = mssb.readAllUsers();
        // user found and returning json data about login priviliges and activity        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (User u : ulst) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("ID", u.getId());
            job.add("Login", u.getLogin());
            job.add("password", u.getPassword());
            job.add("privileges", u.getPrivileges());
            job.add("activity", u.getActivity().toString());
            if (u.getSessionID() != null) {
                job.add("sessionID", u.getSessionID());
            } else {
                job.add("sessionID", 0);
            }
            JsonObject userValue = job.build();
            builder.add(userValue);
        }
        JsonArray userA = builder.build();

        return userA.toString();
    }

    /**
     * Retrieves representation of an instance of controller.AdminResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCommentsJson() {
        //TODO return proper representation object
        List<Comment> clst = mssb.readAllComments();

        // user found and returning json data about login priviliges and activity
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Comment c : clst) {
            JsonObject commentValue = Json.createObjectBuilder()
                    .add("ID", c.getId())
                    .add("login", c.getUserId().getLogin())
                    .add("mediaId", c.getMediaId().getId())
                    .add("message", c.getMessage())
                    .add("date", c.getDate().toString()).build();
            builder.add(commentValue);
        }
        JsonArray commentA = builder.build();

        return commentA.toString();
    }

    /**
     * Retrieves representation of an instance of controller.AdminResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/medias")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMediasJson() {
        //TODO return proper representation object
        List<Media> mlst = mssb.readAllMedias();

        // user found and returning json data about login priviliges and activity
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : mlst) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("ID", m.getId());
            job.add("Login", m.getUserId().getLogin());
            job.add("medialocation", m.getMediaLocation());
            job.add("nsfw", m.getNsfw());
            job.add("date", m.getDate().toString());
            if (m.getTitle() != null) {
                job.add("sessionID", m.getTitle());
            } else {
                job.add("sessionID", m.getDate().toString());
            }
            JsonArrayBuilder tagAbuilder = Json.createArrayBuilder();
            for (MediaTag mt : m.getMediaTagCollection()) {
                JsonObjectBuilder tagjob = Json.createObjectBuilder();
                tagjob.add("tagid", mt.getTagId().getId());
                tagjob.add("tag", mt.getTagId().getTag());
                tagAbuilder.add(tagjob);
            }
            job.add("tags", tagAbuilder);
            JsonObject mediaValue = job.build();
            builder.add(mediaValue);
        }
        JsonArray mediaA = builder.build();

        return mediaA.toString();

        // NEVER AGAIN!!!
//        String ml = "{ \"medias\": [";
//        for (Media m : mlst) {
//            ml += "{\"id\": \"" + m.getId() + "\", ";
//            ml += "\"userid\": \"" + m.getUserId() + "\", ";
//            ml += "\"medialocation\": \"" + m.getMediaLocation() + "\", ";
//            ml += "\"date\": \"" + m.getDate() + "\", ";
//            ml += "\"nsfw\": \"" + m.getNsfw() + "\", ";
//            if (m == mlst.get(mlst.size() - 1)) {
//                ml += "\"tags\": [";
//                for (MediaTag mt : m.getMediaTagCollection()) {
//                    ml += "{\"tagid\": \"" + mt.getTagId().getId() + "\", ";
//                    if (mt == m.getMediaTagCollection().toArray()[m.getMediaTagCollection().size() - 1]) {
//                        ml += "\"tag\": \"" + mt.getTagId().getTag() + "\" }";
//                    } else {
//                        ml += "\"tag\": \"" + mt.getTagId().getTag() + "\" },";
//                    }
//                }
//                ml += "]}";
//            } else {
//                ml += "\"tags\": [";
//                for (MediaTag mt : m.getMediaTagCollection()) {
//                    ml += "{\"tagid\": \"" + mt.getTagId().getId() + "\", ";
//                    if (mt == m.getMediaTagCollection().toArray()[m.getMediaTagCollection().size() - 1]) {
//                        ml += "\"tag\": \"" + mt.getTagId().getTag() + "\" }";
//                    } else {
//                        ml += "\"tag\": \"" + mt.getTagId().getTag() + "\" },";
//                    }
//                }
//                ml += "]},";
//            }
//        }
//        ml += "]}";
    }

    /**
     * Retrieves representation of an instance of controller.AdminResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTagsJson() {
        //TODO return proper representation object
        List<Tag> tlst = mssb.readAllTags();

        // user found and returning json data about login priviliges and activity
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Tag t : tlst) {
            JsonObject tagValue = Json.createObjectBuilder()
                    .add("ID", t.getId())
                    .add("tag", t.getTag()).build();
            builder.add(tagValue);
        }
        JsonArray tagA = builder.build();

        return tagA.toString();
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public String postSignUp(@FormParam("username") String username, @FormParam("password") String password) {
        User user = new User();

        user.setLogin(username);
        user.setPassword(password);
        user.setPrivileges("user");

        mssb.insert(user);

        return "{\"status\": \"true\"}";
    }

    @POST
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    public String postSignIn(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            User user = mssb.readUserByLogin(username);

            if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
                user.setSessionID(nextSessionId());
                mssb.update(user);
                
                return user.getSessionID();
            } else {
                return "false";
            }
        } catch (Exception e) {
            return "false";
        }
    }

    public String nextSessionId() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-!@+&=?#".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 64; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String sessionid = sb.toString();
        return sessionid;

    }
}
