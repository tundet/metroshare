/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Random;
import javax.ejb.EJB;
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
import model.User;

/**
 * REST Web Service
 *
 * @author Mafields
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;

    @EJB
    private MetroShareSB mssb;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String postHtml(@FormParam("username") String username, @FormParam("password") String password) {
        String login = "{";

        User u = mssb.readUserByLogin(username).get(0);
        String sid = nextSessionId();
        u.setSessionID(sid);
        mssb.update(u);
        // user found and returning json data about login priviliges and activity
        if (u != null && username.equals(u.getLogin()) && password.equals(u.getPassword())) {
            login += "username: '" + u.getLogin() + "', ";
            login += "privileges: '" + u.getPrivileges() + "', ";
            login += "activity: '" + u.getActivity() + "', ";
            login += "sessionid: '" + u.getSessionID() + "' ";
            // TODO set new activity time
        } else // error handling returning reasons of error as json
        {
            if (u != null && !username.equals(u.getLogin())) {
                return " error: 'Login name was not found!' }";
            } else if (u != null && !password.equals(u.getPassword())) {
                return " error: 'Password incorrect!' }";
            } else {
                return " error: 'Login was not found!' }";
            }
        }

        login += "}";
        return login;
    }

    /**
     * Retrieves representation of an instance of controller.LoginResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object

        return "{ error: 'Nothing posted' }";
    }

    /**
     * PUT method for updating or creating an instance of LoginResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)

    public void putJson(String content) {
    }

    public String nextSessionId() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789-!@+&=?#".toCharArray();
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
