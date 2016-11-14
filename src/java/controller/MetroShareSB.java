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
import model.Img;
import model.ImgLike;
import model.ImgTag;
import model.User;
import model.Tag;

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
    
   // Img
   public Img insert(Img i) {
        em.persist(i);
        return i;
    }

    public void update(Img i) {
        em.merge(i);
    }
    
    public List<Img> readAllImgs() {
        List<Img> ilst = em.createNamedQuery("Img.findAll").getResultList();
        if (ilst == null) {
            Img i = new Img(0, 0, "Image not found", null);
            ilst.add(i);
            return ilst;
        } else {
            return ilst;
        }
    }
    
    public List<Img> readImgByUserID(int id) {
        List<Img> ilst = em.createNamedQuery("Img.findByUserId").setParameter("userId", id).getResultList();
        if (ilst == null) {
            Img i = new Img(0, 0, "Image not found", null);
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
            Comment c = new Comment(0, 0, 0, "Comment not found", null);
            clst.add(c);
            return clst;
        } else {
            return clst;
        }
    }
    
    public List<Comment> readCommentByImgID(int id) {
        List<Comment> clst = em.createNamedQuery("Comment.findByImgId").setParameter("imgId", id).getResultList();
        if (clst == null) {
            Comment c = new Comment(0, 0, 0, "Comment not found", null);
            clst.add(c);
            return clst;
        } else {
            return clst;
        }
    }
    
    public List<Comment> readCommentByUserID(int id) {
        List<Comment> clst = em.createNamedQuery("Comment.findByUserId").setParameter("userId", id).getResultList();
        if (clst == null) {
            Comment c = new Comment(0, 0, 0, "Comment not found", null);
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
            Friend f = new Friend(0, 0, 0);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    public List<Friend> readFriendByOwnerID(int id) {
        List<Friend> flst = em.createNamedQuery("Friend.findByOwnerId").setParameter("ownerId", id).getResultList();
        if (flst == null) {
            Friend f = new Friend(0, 0, 0);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    public List<Friend> readFriendByFriendID(int id) {
        List<Friend> flst = em.createNamedQuery("Friend.findByFriendId").setParameter("friendId", id).getResultList();
        if (flst == null) {
            Friend f = new Friend(0, 0, 0);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }
    
    // ImgLike
    public ImgLike insert(ImgLike l) {
        em.persist(l);
        return l;
    }

    public void update(ImgLike l) {
        em.merge(l);
    }
    
    public List<ImgLike>readAllImgLikes() {
        List<ImgLike> imgllst = em.createNamedQuery("ImgLike.findAll").getResultList();
        if (imgllst == null) {
            ImgLike l = new ImgLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    public List<ImgLike> readLikeByImgID(int id) {
        List<ImgLike> imgllst = em.createNamedQuery("ImgLike.findByImgId").setParameter("imgId", id).getResultList();
        if (imgllst == null) {
            ImgLike l = new ImgLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    public List<ImgLike> readLikeByUserID(int id) {
        List<ImgLike> imgllst = em.createNamedQuery("ImgLike.findByUserId").setParameter("userId", id).getResultList();
        if (imgllst == null) {
            ImgLike l = new ImgLike(null);
            imgllst.add(l);
            return imgllst;
        } else {
            return imgllst;
        }
    }
    
    // ImgTag
    public ImgTag insert(ImgTag it) {
        em.persist(it);
        return it;
    }

    public void update(ImgTag it) {
        em.merge(it);
    }
    
    public List<ImgTag>readAllImgTags() {
        List<ImgTag> imgtlst = em.createNamedQuery("ImgTag.findAll").getResultList();
        if (imgtlst == null) {
            ImgTag it = new ImgTag(null);
            imgtlst.add(it);
            return imgtlst;
        } else {
            return imgtlst;
        }
    }
    
    public List<ImgTag> readImgTagByImgID(int id) {
        List<ImgTag> imgtlst = em.createNamedQuery("ImgTag.findByImgId").setParameter("imgId", id).getResultList();
        if (imgtlst == null) {
            ImgTag it = new ImgTag(null);
            imgtlst.add(it);
            return imgtlst;
        } else {
            return imgtlst;
        }
    }
    
    public List<ImgTag> readImgTagByTagID(int id) {
        List<ImgTag> imgtlst = em.createNamedQuery("ImgTag.findByTagId").setParameter("tagId", id).getResultList();
        if (imgtlst == null) {
            ImgTag it = new ImgTag(null);
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