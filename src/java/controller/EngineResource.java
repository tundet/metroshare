/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;

/**
 * REST Web Service
 *
 * @author jozi_
 */
@Path("engine")
public class EngineResource {

    @Context
    private UriInfo context;
    

    /**
     * Creates a new instance of EngineResource
     */
    public EngineResource() {
    }

    /**
     * Retrieves representation of an instance of controller.EngineResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml(@Context HttpHeaders hh) throws IOException {
        //TODO return proper representation object
        
//         MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
//        //Map<String, Cookie> pathParams = hh.getCookies();
//        System.out.println(headerParams.toString()); // GET requested url path Url path 
//        System.out.println(hh.getCookies().toString());
//        
//        
//        MultivaluedMap<String, String> queryParams = context.getQueryParameters();
//        MultivaluedMap<String, String> urlpathParams = context.getPathParameters();
//        
//        System.out.println(queryParams.toString()); // list of parametters?
//        System.out.println(urlpathParams.toString());

        
                
        // PATH!!
        String absolutepath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        absolutepath = absolutepath.substring(0, absolutepath.indexOf("WEB"));
        absolutepath = absolutepath.substring(1);
        
        String r = "";
        
        r +="Path: " + context.getPath();
        boolean loggedin = false;
        if (loggedin){
           //r += new String(Files.readAllBytes(Paths.get(absolutepath + "head-loggedin.html"))); // works
        } else {
           //r += new String(Files.readAllBytes(Paths.get(absolutepath + "head-notloggedin.html"))); // works
        }

        
       
        
        return r;
    }

    /**
     * PUT method for updating or creating an instance of EngineResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
    
        
   
    
}
