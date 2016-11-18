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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
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
     * @param login
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{login}/activity")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("login") String login) {
        //TODO return proper representation object
        String ua = "";
        User u = mssb.readUserByLogin(login).get(0);
        ua += "{ activity: '" + u.getActivity() + "'}";
        return ua;
    }

    /**
     * PUT method for updating or creating an instance of UsersResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
