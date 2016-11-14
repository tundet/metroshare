/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;
import model.Comment;
import model.Friend;
import model.Media;
import model.MediaLike;
import model.MediaTag;
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
            r += "<th>ID</th><th>Login</th><th>Password</th><th>Privileges</th>";
        } else {
            r += "<th>Login</th><th>Class</th>";
        }
        r += "</tr></thead><tbody>";
        if (showIdAndPassword) {
            for (User u : ulst) {
                r += "<tr><td>" + u.getId() + "</td><td>" + u.getLogin() + "</td><td>" + u.getPassword() + "</td><td>" + u.getPrivileges() + "</td></tr>";
            }
        } else {
            for (User u : ulst) {
                r += "<tr><td>" + u.getLogin() + "</td><td>" + u.getPrivileges() + "</td></tr>";
            }
        }
        r += "</tbody></table>";
        return r;
    }

    public String TableForMedias(List<Media> mlst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>User ID</th><th>Image src</th><th>Date</th>";
        r += "</tr></thead><tbody>";
        for (Media m : mlst) {
            r += "<tr><td>" + m.getId() + "</td><td>" + m.getUserId() + "</td><td>" + m.getImageLocation() + "</td><td>" + m.getDate() + "</td></tr>";
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
            r += "<tr><td>" + c.getId() + "</td><td>" + c.getMediaId() + "</td><td>" + c.getUserId() + "</td><td>" + c.getMessage() + "</td><td>" + c.getDate() + "</td></tr>";
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
    
    public String TableForLikes(List<MediaLike> mllst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Img ID</th><th>User ID</th><th>Like</th><th>Date</th>";
        r += "</tr></thead><tbody>";
        for (MediaLike ml : mllst) {
            r += "<tr><td>" + ml.getId() + "</td><td>" + ml.getMediaId() + "</td><td>" + ml.getUserId() + "</td><td>" + ml.getLikeBoolean() + "</td><td>" + ml.getDate() + "</td></tr>";
        }
        r += "</tbody></table>";
        return r;
    }

    public String TableForImgTags(List<MediaTag> mtlst) {
        String r = "";
        r += "<table><thead><tr>";
        r += "<th>ID</th><th>Img ID</th><th>Tag ID</th>";
        r += "</tr></thead><tbody>";
        for (MediaTag mt : mtlst) {
            r += "<tr><td>" + mt.getId() + "</td><td>" + mt.getMediaId() + "</td><td>" + mt.getTagId() + "</td></tr>";
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
