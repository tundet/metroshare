/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import model.User;
import util.MakeTableFromList;

/**
 * REST Web Service
 *
 * @author Mafields
 */
@Path("home")
public class HomeResource {

    private MakeTableFromList mkfl = new MakeTableFromList();

    @Context
    private UriInfo context;

    @Context
    HttpHeaders h;

    @EJB
    private MetroShareSB mssb;

    /**
     * Creates a new instance of HomeResource
     */
    public HomeResource() {
    }

    /**
     * Retrieves representation of an instance of controller.HomeResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        String s = "Session Stuff:</BR>";
        //s += h.getRequestHeaders().toString();
        
        
        // Does not work!
        // Cookie login = new Cookie("MetroShare", "MetroShareSessionID");
        // h.getCookies().putIfAbsent("Metroshare", login);
        
        
        for (Cookie c : h.getCookies().values()){
            s += "Domain: " + c.getDomain() + "</br>";
            s += "Name: " + c.getName() + "</br>";
            s += "Path: " + c.getPath() + "</br>";
            s += "Value: " + c.getValue() + "</br>";
            s += c.toString() + "</br>";
        }
        s += "</BR></BR>";
        //TODO return proper representation object
        String r = s + "USER TABLE QUERIES</BR>";
        r += "</BR>USER BY ALL SHOW ALL:</BR>";
        r += mkfl.TableForUsers(mssb.readAllUsers(), true);
        r += "</BR>USER BY ALL SHOW LOGIN & CLASS</BR>";
        r += mkfl.TableForUsers(mssb.readAllUsers(), false);
        r += "</BR>USER BY ID SINGLE:</BR>";
        User u = mssb.readUserByID(3);
        r += "Login: " + u.getLogin() + ", Password; " + u.getPassword() + ", Privileges: " + u.getPrivileges() + "</br>";
        r += "</BR>USER BY LOGIN SHOW ALL:</BR>";
        //r += mkfl.TableForUsers(mssb.readUserByLogin("matti"), true);
        r += "</BR>";

        r += "MEDIA TABLE QUERIES</BR>";
        r += "</BR>MEDIA BY ALL:</BR>";
        r += mkfl.TableForImages(mssb.readAllMedias());
        r += "</BR>MEDIA BY ID:</BR>";
        r += mkfl.TableForImages(mssb.readMediaByUserID(3));
        r += "</BR>";

        r += "COMMENT TABLE QUERIES</BR>";
        r += "</BR>COMMENTS BY ALL:</BR>";
        r += mkfl.TableForComment(mssb.readAllComments());
        r += "</BR>COMMENTS BY MEDIA ID:</BR>";
        r += mkfl.TableForComment(mssb.readCommentByMediaID(2));
        r += "</BR>COMMENTS BY USER ID:</BR>";
        r += mkfl.TableForComment(mssb.readCommentByUserID(2));
        r += "</BR>";

        r += "FRIEND TABLE QUERIES</BR>";
        r += "</BR>FRIENDS BY ALL:</BR>";
        r += mkfl.TableForFriends(mssb.readAllFriends());
        r += "</BR>FRIENDS BY OWNER ID:</BR>";
        r += mkfl.TableForFriends(mssb.readFriendByOwnerID(3));
        r += "</BR>FRIENDS BY FRIEND ID:</BR>";
        r += mkfl.TableForFriends(mssb.readFriendByFriendID(3));
        r += "</BR>";

        r += "LIKES TABLE QUERIES</BR>";
        r += "</BR>LIKES BY ALL:</BR>";
        r += mkfl.TableForLikes(mssb.readAllMediaLikes());
        r += "</BR>LIKES BY MEDIA ID:</BR>";
        r += mkfl.TableForLikes(mssb.readLikeByMediaID(2));
        r += "</BR>LIKES BY USER ID:</BR>";
        r += mkfl.TableForLikes(mssb.readLikeByUserID(3));
        r += "</BR>";

        r += "MEDIATAG TABLE QUERIES</BR>";
        r += "</BR>MEDIATAG BY ALL:</BR>";
        r += mkfl.TableForMediaTags(mssb.readAllMediaTags());
        r += "</BR>MEDIATAG BY MEDIA ID:</BR>";
        r += mkfl.TableForMediaTags(mssb.readMediaTagByMediaID(2));
        r += "</BR>MEDIATAG BY TAG ID:</BR>";
        r += mkfl.TableForMediaTags(mssb.readMediaTagByTagID(2));
        r += "</BR>";

        r += "TAG TABLE QUERIES</BR>";
        r += "</BR>TAG BY ALL:</BR>";
        r += mkfl.TableForTags(mssb.readAllTags());
        r += "</BR>TAG BY ID:</BR>";
        r += mkfl.TableForTags(mssb.readTagById(2));
        r += "</BR>TAG BY TAG:</BR>";
        r += mkfl.TableForTags(mssb.readTagByTag("Seppo"));
        r += "</BR>";

        return r;
    }

    /**
     * PUT method for updating or creating an instance of HomeResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
