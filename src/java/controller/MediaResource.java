/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Comment;
import model.Media;
import model.MediaTag;

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
    @Path("/{mediaid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("mediaid") int mediaid) {
        //TODO return proper representation object
        Media m = mssb.readMediaByMediaID(mediaid);

        //comments
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Comment c : m.getCommentCollection()) {
            JsonObjectBuilder commentValue = Json.createObjectBuilder();
            commentValue.add("commentID", c.getId());
            commentValue.add("userName", c.getUserId().getLogin());
            commentValue.add("mediaID", c.getMediaId().getId());
            
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
                .add("id", m.getId())
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
    public void putJson(String content) {
    }
}
