/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import model.Comment;
import model.Friend;
import model.Img;
import model.ImgLike;
import model.ImgTag;
import model.Tag;
import model.User;

/**
 *
 * @author jozi_
 */
public class MakeTableFromList {

    public String TableForUsers(List<User> ulst, boolean showIdAndPassword) {
        String r = "";
        r += "<table><thead><tr>";
        if (showIdAndPassword) {
            r += "<th>ID</th><th>Login</th><th>Password</th><th>Class</th>";
        } else {
            r += "<th>Login</th><th>Class</th>";
        }
        r += "</tr></thead><tbody>";
        if (showIdAndPassword) {
            for (User u : ulst) {
                r += "<tr><td>" + u.getId() + "</td><td>" + u.getLogin() + "</td><td>" + u.getPassword() + "</td><td>" + u.getClass1() + "</td></tr>";
            }
        } else {
            for (User u : ulst) {
                r += "<tr><td>" + u.getLogin() + "</td><td>" + u.getClass1() + "</td></tr>";
            }
        }
        r += "</tbody></table>";
        return r;
    }

    public String TableForImages(List<Img> imglst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>User ID</th><th>Image src</th><th>Date</th>";
        r += "</tr></thead><tbody>";
        for (Img i : imglst) {
            r += "<tr><td>" + i.getId() + "</td><td>" + i.getUserId() + "</td><td>" + i.getImageLocation() + "</td><td>" + i.getDate() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }
    
    public String TableForComment(List<Comment> clst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Img ID</th><th>User ID</th><th>Message</th><th>Date</th>";
        r += "</tr></thead><tbody>";
        for (Comment c : clst) {
            r += "<tr><td>" + c.getId() + "</td><td>" + c.getImgId() + "</td><td>" + c.getUserId() + "</td><td>" + c.getMessage() + "</td><td>" + c.getDate() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }
    
    public String TableForFriends(List<Friend> flst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Owner ID</th><th>Friend ID</th>";
        r += "</tr></thead><tbody>";
        for (Friend f : flst) {
            r += "<tr><td>" + f.getId() + "</td><td>" + f.getOwnerId() + "</td><td>" + f.getFriendId() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }
    
    public String TableForLikes(List<ImgLike> llst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Img ID</th><th>User ID</th><th>Like</th><th>Date</th>";
        r += "</tr></thead><tbody>";
        for (ImgLike l : llst) {
            r += "<tr><td>" + l.getId() + "</td><td>" + l.getImgId() + "</td><td>" + l.getUserId() + "</td><td>" + l.getLikeBoolean() + "</td><td>" + l.getDate() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }

    public String TableForImgTags(List<ImgTag> imgtlst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Img ID</th><th>Tag ID</th>";
        r += "</tr></thead><tbody>";
        for (ImgTag t : imgtlst) {
            r += "<tr><td>" + t.getId() + "</td><td>" + t.getImgId() + "</td><td>" + t.getTagId() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }
    
    public String TableForTags(List<Tag> tlst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Tag</th>";
        r += "</tr></thead><tbody>";
        for (Tag t : tlst) {
            r += "<tr><td>" + t.getId() + "</td><td>" + t.getTag() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }

}
