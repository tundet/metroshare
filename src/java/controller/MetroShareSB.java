/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Stateless
public class MetroShareSB {

     @PersistenceContext
    private EntityManager em;

    // User
    public User insert(User u) {
        em.persist(u);
        return u;
    }

    public void update(User u) {
        em.merge(u);
    }
    
    public List<User> readAllUsers() {
        List<User> ulst = em.createNamedQuery("User.findAll").getResultList();
        if (ulst == null) {
            User u = new User(0, "Error", "Users not found", "user");
            ulst.add(u);
            return ulst;
        } else {
            return ulst;
        }
    }
    
    public User readUserByID(int id) {
        User u = em.find(User.class, id);
        if (u == null) {
            u = new User(0, "Error", "User not found", "user");
            return u;
        } else {
            return u;
        }
    }
    
    public List<User> readUserByLogin(String login) {
        List<User> ul = em.createNamedQuery("User.findByLogin").setParameter("login", login).getResultList();
        if (ul == null) {
            User u = new User(0, "Error", "User not found", "user");
            ul.add(u);
            return ul;
        } else {
            return ul;
        }
    }
    
   // Media
   public Media insert(Media i) {
        em.persist(i);
        return i;
    }

    public void update(Media i) {
        em.merge(i);
    }
    
    public List<Media> readAllMedias() {
        List<Media> ilst = em.createNamedQuery("Media.findAll").getResultList();
        if (ilst == null) {
            Media i = new Media(null);
            ilst.add(i);
            return ilst;
        } else {
            return ilst;
        }
    }
    
    public List<Media> readMediaByUserID(int id) {
        List<Media> ilst = em.createNamedQuery("Media.findByUserId").setParameter("userId", id).getResultList();
        if (ilst == null) {
            Media i = new Media(null);
            ilst.add(i);
            return ilst;
        } else {
            return ilst;
        }
    }

   // Comments
   public Comment insert(Comment c) {
        em.persist(c);
        return c;
    }

    public void update(Comment c) {
        em.merge(c);
    }
    
    public List<Comment> readAllComments() {
        List<Comment> clst = em.createNamedQuery("Comment.findAll").getResultList();
        if (clst == null) {
            Comment c = new Comment(null);
            clst.add(c);
            return clst;
        } else {
            return clst;
        }
    }
    
    public List<Comment> readCommentByMediaID(int id) {
        List<Comment> clst = em.createNamedQuery("Comment.findByMediaId").setParameter("imgId", id).getResultList();
        if (clst == null) {
            Comment c = new Comment(null);
            clst.add(c);
            return clst;
        } else {
            return clst;
        }
    }
    
    public List<Comment> readCommentByUserID(int id) {
        List<Comment> clst = em.createNamedQuery("Comment.findByUserId").setParameter("userId", id).getResultList();
        if (clst == null) {
            Comment c = new Comment(null);
            clst.add(c);
            return clst;
        } else {
            return clst;
        }
    }
    
    // Friend
    public Friend insert(Friend f) {
        em.persist(f);
        return f;
    }

    public void update(Friend f) {
        em.merge(f);
    }
    
    public List<Friend>readAllFriends() {
        List<Friend> flst = em.createNamedQuery("Friend.findAll").getResultList();
        if (flst == null) {
            Friend f = new Friend(null);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    public List<Friend> readFriendByOwnerID(int id) {
        List<Friend> flst = em.createNamedQuery("Friend.findByOwnerId").setParameter("ownerId", id).getResultList();
        if (flst == null) {
            Friend f = new Friend(null);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    public List<Friend> readFriendByFriendID(int id) {
        List<Friend> flst = em.createNamedQuery("Friend.findByFriendId").setParameter("friendId", id).getResultList();
        if (flst == null) {
            Friend f = new Friend(null);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    // MediaLike
    public MediaLike insert(MediaLike l) {
        em.persist(l);
        return l;
    }

    public void update(MediaLike l) {
        em.merge(l);
    }
    
    public List<MediaLike>readAllMediaLikes() {
        List<MediaLike> imgllst = em.createNamedQuery("MediaLike.findAll").getResultList();
        if (imgllst == null) {
            MediaLike l = new MediaLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    public List<MediaLike> readLikeByMediaID(int id) {
        List<MediaLike> imgllst = em.createNamedQuery("MediaLike.findByMediaId").setParameter("imgId", id).getResultList();
        if (imgllst == null) {
            MediaLike l = new MediaLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    public List<MediaLike> readLikeByUserID(int id) {
        List<MediaLike> imgllst = em.createNamedQuery("MediaLike.findByUserId").setParameter("userId", id).getResultList();
        if (imgllst == null) {
            MediaLike l = new MediaLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    // MediaTag
    public MediaTag insert(MediaTag it) {
        em.persist(it);
        return it;
    }

    public void update(MediaTag it) {
        em.merge(it);
    }
    
    public List<MediaTag>readAllMediaTags() {
        List<MediaTag> imgtlst = em.createNamedQuery("MediaTag.findAll").getResultList();
        if (imgtlst == null) {
            MediaTag it = new MediaTag(null);
            imgtlst.add(it);
            return imgtlst;
        } else {
            return imgtlst;
        }
    }
    
    public List<MediaTag> readMediaTagByMediaID(int id) {
        List<MediaTag> imgtlst = em.createNamedQuery("MediaTag.findByMediaId").setParameter("imgId", id).getResultList();
        if (imgtlst == null) {
            MediaTag it = new MediaTag(null);
            imgtlst.add(it);
            return imgtlst;
        } else {
            return imgtlst;
        }
    }
    
    public List<MediaTag> readMediaTagByTagID(int id) {
        List<MediaTag> imgtlst = em.createNamedQuery("MediaTag.findByTagId").setParameter("tagId", id).getResultList();
        if (imgtlst == null) {
            MediaTag it = new MediaTag(null);
            imgtlst.add(it);
            return imgtlst;
        } else {
            return imgtlst;
        }
    }
    
    // Tag
    public Tag insert(Tag t) {
        em.persist(t);
        return t;
    }

    public void update(Tag t) {
        em.merge(t);
    }
    
    public List<Tag> readAllTags() {
        List<Tag> tlst = em.createNamedQuery("Tag.findAll").getResultList();
        if (tlst == null) {
            Tag t = new Tag(null);
            tlst.add(t);
            return tlst;
        } else {
            return tlst;
        }
    }
    
    public List<Tag> readTagById(int id) {
        List<Tag> tlst = em.createNamedQuery("Tag.findById").setParameter("id", id).getResultList();
        if (tlst == null) {
            Tag l = new Tag(null);
            tlst.add(l);
            return tlst;
        } else {
            return tlst;
        }
    }
    
    public List<Tag> readTagByTag(String tag) {
        List<Tag> tlst = em.createNamedQuery("Tag.findByTag").setParameter("tag", tag).getResultList();
        if (tlst == null) {
            Tag l = new Tag(null);
            tlst.add(l);
            return tlst;
        } else {
            return tlst;
        }
    }
}
