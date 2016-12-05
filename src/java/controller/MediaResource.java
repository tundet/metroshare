/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Comment;
import model.Media;
import model.MediaTag;
import model.User;

/**
 * REST Web Service
 *
 * @author Mafields
 */
@Path("media")
public class MediaResource {

    @Context
    private UriInfo context;

    @EJB
    private MetroShareSB mssb;

    /**
     * Creates a new instance of MediaResource
     */
    public MediaResource() {
    }

    @GET
    @Path("/{mediaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("mediaId") int mediaId) {
        //TODO return proper representation object
        Media m = mssb.readMediaByMediaID(mediaId);

        //comments
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Comment c : m.getCommentCollection()) {
            JsonObjectBuilder commentValue = Json.createObjectBuilder();
            commentValue.add("commentID", c.getId());
            commentValue.add("userName", c.getUserId().getLogin());
            commentValue.add("mediaId", c.getMediaId().getId());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd @ hh:mm:ss");
            Date date = c.getDate();
            String dateS = formatter.format(date);

            commentValue.add("date", dateS);
            commentValue.add("message", c.getMessage());
            builder.add(commentValue.build());
        }
        JsonArray commentA = builder.build();
        System.out.println(commentA.toString());

        //tags
        builder = Json.createArrayBuilder();
        for (MediaTag mt : m.getMediaTagCollection()) {
            JsonObject tagValue = Json.createObjectBuilder()
                    .add("tagid", mt.getTagId().getId())
                    .add("tag", mt.getTagId().getTag()).build();
            builder.add(tagValue);
        }
        JsonArray tagA = builder.build();

        // TODO likes here
        builder = Json.createArrayBuilder();
        JsonObject mediaValue = Json.createObjectBuilder()
                .add("mediaId", m.getId())
                .add("uploaderuser", m.getUserId().getLogin())
                .add("medialocation", m.getMediaLocation())
                .add("title", m.getTitle())
                .add("nsfw", m.getNsfw())
                .add("comments", commentA)
                .add("tags", tagA)
                .add("likes", true)
                .build();
        builder.add(mediaValue);

        JsonArray media = builder.build();

        return media.toString();
    }

    @POST
    @Path("/comment/")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeCommentToMedia(@FormParam("sender") String sender, @FormParam("mediaid") String mediaid, @FormParam("comment") String comment) {
        //TODO return proper representation object
        System.out.println("getting user by sessionid:" + sender);
        
        Comment c = new Comment();
        c.setUserId(mssb.readUserBySessionID(sender));
        c.setMediaId(mssb.readMediaByMediaID(Integer.parseInt(mediaid)));
        c.setMessage(comment);
        c = mssb.insert(c);
        if (c.getId() != null) {
            return "{\"succes\": \"commentid " + c.getId() +"\"}";
        } else {
            return "{\"error\": \"no comment saved\"}";
        }
    }
    
    @GET
    @Path("/latest/{numberOfMediasWanted}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNLatestsMediasJson(@PathParam("numberOfMediasWanted") int medias) {
        //TODO return proper representation object
        //System.out.println("getting latest:" + medias + " medias");
        List<Media> ml = mssb.readNLatestMedias(medias);

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : ml) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }

        JsonArray media = builder.build();
        return media.toString();
    }

    @GET
    @Path("/get/{numberOfMediasWanted}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThisAmountOfMediasJson(@PathParam("numberOfMediasWanted") int media) {
        //TODO return proper representation object
        int last = mssb.readLastIndexOfMedias();
        //System.out.println(last);

        Random rnd = new Random();
        ArrayList<Media> mal = new ArrayList<Media>();

        while (mal.size() < media) {
            Media m = mssb.readMediaByMediaID(rnd.nextInt(last) + 1);
            if (m != null) {
                if (!mal.contains(m)) {
                    mal.add(m);
                }
            }
        }

        String r = "media IDs: ";
        for (Media m : mal) {
            r += m.getId() + ", ";
        }

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : mal) {
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

    @GET
    @Path("/search/{searchword}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearch(@PathParam("searchword") String search) {
        System.out.println("Search: " + search);

        List<Media> ml = null;
        List<Media> ul = null;
        List<Media> tl = null;

        try {
            ml = mssb.searchMediaByTitle(search);
        } catch (Exception e) {
            return "{\"error\": \"no media\"}";
        }
        try {
            ul = mssb.searchMediaByUserLogin(search);
        } catch (Exception e) {
            return "{\"error\": \"no users\"}";
        }
        try {
            tl = mssb.searchMediaByTag(search);
        } catch (Exception e) {
            return "{\"error\": \"no tags\"}";
        }
        JsonArrayBuilder wholeList = Json.createArrayBuilder();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : ml) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        for (Media m : ul) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        for (Media m : tl) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        JsonArray mediaA = wholeList.build();

        return mediaA.toString();
    }
    
    @POST
    @Path("/search/")
    @Produces(MediaType.APPLICATION_JSON)
    public String postSearch(@FormParam("search") String search) {
        System.out.println("Search: " + search);

        List<Media> ml = null;
        List<Media> ul = null;
        List<Media> tl = null;

        try {
            ml = mssb.searchMediaByTitle(search);
        } catch (Exception e) {
            return "{\"error\": \"no media\"}";
        }
        try {
            ul = mssb.searchMediaByUserLogin(search);
        } catch (Exception e) {
            return "{\"error\": \"no users\"}";
        }
        try {
            tl = mssb.searchMediaByTag(search);
        } catch (Exception e) {
            return "{\"error\": \"no tags\"}";
        }
        JsonArrayBuilder wholeList = Json.createArrayBuilder();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : ml) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        for (Media m : ul) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        for (Media m : tl) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .add("mediaLocation", m.getMediaLocation())
                    .add("title", m.getTitle())
                    .add("nsfw", m.getNsfw())
                    .build();
            builder.add(mediaValue);
        }
        wholeList.add(builder.build());
        JsonArray mediaA = wholeList.build();

        return mediaA.toString();
    }

    /**
     * Retrieves representation of an instance of controller.MediaResource
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
     * PUT method for updating or creating an instance of MediaResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content
    ) {
    }
}
