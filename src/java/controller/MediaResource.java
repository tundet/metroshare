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
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Comment;
import model.Media;
import model.MediaLike;
import model.MediaTag;
import model.User;

/**
 * Media resource.
 *
 * This resource is responsible for actions such as retrieving, creating, liking
 * and disliking media.
 */
@Path("media")
public class MediaResource {

    @Context
    private UriInfo context;

    @EJB
    private MetroShareSB mssb;

    /**
     * Retrieve a single medium and its comments, tags, likes and dislikes.
     *
     * @param mediaId ID of the medium
     * @param sessionid Session ID of the user who the medium belongs to
     * @return Data about the medium as JSON
     */
    @GET
    @Path("/{mediaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("mediaId") int mediaId, @CookieParam("SessionID") String sessionid) {
        User u = null;

        if (!sessionid.isEmpty()) {
            u = mssb.readUserBySessionID(sessionid);
            System.out.println("userfound!");
        }
        Media m = mssb.readMediaByMediaID(mediaId);

        // Comments.
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

        // Tags.
        builder = Json.createArrayBuilder();
        for (MediaTag mt : m.getMediaTagCollection()) {
            JsonObject tagValue = Json.createObjectBuilder()
                    .add("tagid", mt.getTagId().getId())
                    .add("tag", mt.getTagId().getTag()).build();
            builder.add(tagValue);
        }
        JsonArray tagA = builder.build();

        int likes = 0;
        int dislikes = 0;
        boolean userHasOpinion = false;
        boolean userOpinion = false;
        int likeid = 0;
        for (MediaLike ml : m.getMediaLikeCollection()) {
            System.out.println(ml);
            if (!sessionid.isEmpty()) {
                System.out.println(ml.getUserId().getId());
                System.out.println(u.getId());
                if (ml.getUserId().getId() == u.getId()) {
                    System.out.println("USER HAS OPINION");
                    userHasOpinion = true;
                    userOpinion = ml.getLikeBoolean();
                    likeid = ml.getId();
                }
            }
            if (ml.getLikeBoolean()) {
                likes++;
            } else {
                dislikes++;
            }
        }
        JsonObject likesValue = Json.createObjectBuilder()
                .add("likes", likes)
                .add("dislikes", dislikes)
                .add("userhasopinion", userHasOpinion)
                .add("useropinion", userOpinion)
                .add("likeid", likeid)
                .build();

        builder = Json.createArrayBuilder();
        JsonObject mediaValue = Json.createObjectBuilder()
                .add("mediaId", m.getId())
                .add("uploaderuser", m.getUserId().getLogin())
                .add("medialocation", m.getMediaLocation())
                .add("title", m.getTitle())
                .add("nsfw", m.getNsfw())
                .add("comments", commentA)
                .add("tags", tagA)
                .add("likes", likesValue)
                .build();
        builder.add(mediaValue);

        JsonArray media = builder.build();

        return media.toString();
    }

    /**
     * Create a new comment.
     *
     * Saves a new comment from the currently logged in user to the database.
     *
     * @param sender Username of the commenter
     * @param mediaid ID of the commented media
     * @param comment Contents of the comment
     * @return Status message as JSON
     */
    @POST
    @Path("/opinion/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeLikeFromMedia(@FormParam("mediaid") String mediaid, @FormParam("hasopinion") String likeid, @CookieParam("SessionID") String sessionid) {

        if (Integer.parseInt(likeid) != 0) {
            mssb.removeMediaLike(Integer.parseInt(likeid));
        }

        return "{\"succes\": \"1\"}";
    }

    /**
     * Create a new comment.
     *
     * Saves a new comment from the currently logged in user to the database.
     *
     * @param sender Username of the commenter
     * @param mediaid ID of the commented media
     * @param comment Contents of the comment
     * @return Status message as JSON
     */
    @POST
    @Path("/opinion/dislike")
    @Produces(MediaType.APPLICATION_JSON)
    public String setDislikeToMedia(@FormParam("mediaid") String mediaid, @FormParam("hasopinion") String likeid, @CookieParam("SessionID") String sessionid) {
        //System.out.println(sessionid);
        //System.out.println(mediaid);
        User u = mssb.readUserBySessionID(sessionid);

        MediaLike ml = new MediaLike();
        if (Integer.parseInt(likeid) == 0) {
            ml.setMediaId(mssb.readMediaByMediaID(Integer.parseInt(mediaid)));
            ml.setUserId(u);
            ml.setLikeBoolean(Boolean.FALSE);
            mssb.insert(ml);
        } else {
            ml = mssb.readMediaLikeByMediaId(Integer.parseInt(likeid));
            ml.setLikeBoolean(Boolean.FALSE);
            ml.setDate(null);
            mssb.update(ml);
        }

        return "{\"succes\": \"1\"}";
    }

    /**
     * Create a new comment.
     *
     * Saves a new comment from the currently logged in user to the database.
     *
     * @param sender Username of the commenter
     * @param mediaid ID of the commented media
     * @param comment Contents of the comment
     * @return Status message as JSON
     */
    @POST
    @Path("/opinion/like")
    @Produces(MediaType.APPLICATION_JSON)
    public String setLikeToMedia(@FormParam("mediaid") String mediaid, @FormParam("hasopinion") String likeid, @CookieParam("SessionID") String sessionid) {
        //System.out.println(sessionid);
        //System.out.println(mediaid);
        User u = mssb.readUserBySessionID(sessionid);

        MediaLike ml = new MediaLike();
        if (Integer.parseInt(likeid) == 0) {
            ml.setMediaId(mssb.readMediaByMediaID(Integer.parseInt(mediaid)));
            ml.setUserId(u);
            ml.setLikeBoolean(Boolean.TRUE);
            mssb.insert(ml);
        } else {
            ml = mssb.readMediaLikeByMediaId(Integer.parseInt(likeid));
            ml.setLikeBoolean(Boolean.TRUE);
            ml.setDate(null);
            mssb.update(ml);
        }

        return "{\"succes\": \"1\"}";
    }

    /**
     * Create a new comment.
     *
     * Saves a new comment from the currently logged in user to the database.
     *
     * @param sender Username of the commenter
     * @param mediaid ID of the commented media
     * @param comment Contents of the comment
     * @return Status message as JSON
     */
    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeCommentToMedia(@CookieParam("SessionID") String sessionid, @FormParam("mediaid") String mediaid, @FormParam("comment") String comment) {
        User u = mssb.readUserBySessionID(sessionid);
        System.err.println(u);
        Comment c = new Comment();
        c.setUserId(u);
        c.setMediaId(mssb.readMediaByMediaID(Integer.parseInt(mediaid)));
        c.setMessage(comment);
        mssb.insert(c);

        JsonObject commentValue = Json.createObjectBuilder()
                .add("status", "1").build();
        System.out.println(commentValue.toString());
        return commentValue.toString();

    }

    /**
     * Retrieve a specific amount of the latest media.
     *
     * @param medias Amount of media to retrieve
     * @return Latest media as JSON
     */
    @GET
    @Path("/latest/{numberOfMediasWanted}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNLatestsMediasJson(@PathParam("numberOfMediasWanted") int medias) {
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

    /**
     * Retrieve a specific amount of random media.
     *
     * @param media Amount of media to retrieve
     * @return Random media as JSON
     */
    @GET
    @Path("/get/{numberOfMediasWanted}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThisAmountOfMediasJson(@PathParam("numberOfMediasWanted") String media) {
        int last = mssb.readLastIndexOfMedias();
        System.err.println(last);

        Random rnd = new Random();
        ArrayList<Media> mal = new ArrayList<Media>();

        List<Media> midal = mssb.readAllMediaIds();

        while (mal.size() < Integer.parseInt(media)) {
            int id = (rnd.nextInt(last) + 1);
            if (midal.contains(id)) {
                Media m = mssb.readMediaByMediaID(id);
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

    /**
     * Retrieve total amount of the latest media.
     *
     * @return All media.
     */
    @GET
    @Path("/total")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalMediasJson() {
        List<Media> ml = mssb.readAllMedias();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Media m : ml) {
            JsonObject mediaValue = Json.createObjectBuilder()
                    .add("mediaId", m.getId())
                    .build();
            builder.add(mediaValue);
        }

        JsonArray media = builder.build();

        return media.toString();
    }

    /**
     * Retrieve media with most likes.
     *
     * @return All media with likes in descending order.
     */
    @GET
    @Path("/mostlikes")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMostLikesJson() {
        List<MediaLike> mll = mssb.readMostMediaLikes();
        Media m = mll.get(0).getMediaId();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObject mediaValue = Json.createObjectBuilder()
                .add("mediaId", m.getId())
                .add("mediaLocation", m.getMediaLocation())
                .add("title", m.getTitle())
                .add("nsfw", m.getNsfw())
                .build();
        builder.add(mediaValue);

        JsonArray media = builder.build();

        return media.toString();
    }
    
    /**
     * Retrieve media with most comments.
     *
     * @return list of media with most comments in descending order.
     */
    @GET
    @Path("/mostcomments")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMostCommentsJson() {
        List<Comment> cl = mssb.readMostMediaComments();
        Media m = cl.get(0).getMediaId();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObject mediaValue = Json.createObjectBuilder()
                .add("mediaId", m.getId())
                .add("mediaLocation", m.getMediaLocation())
                .add("title", m.getTitle())
                .add("nsfw", m.getNsfw())
                .build();
        builder.add(mediaValue);

        JsonArray media = builder.build();

        return media.toString();
    }

    /**
     * Search media by author, tag or title as a GET request.
     *
     * @param search Search keyword
     * @return All media matching the keyword
     */
    @GET
    @Path("/search/{searchword}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearch(@PathParam("searchword") String search) {
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
     * Search media by author, tag or title as a POST request.
     *
     * @param search Search keyword
     * @return All media matching the keyword
     */
    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public String postSearch(@FormParam("search") String search) {
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
}
