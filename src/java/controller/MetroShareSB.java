/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
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
 * @author Mafields
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
        return em.createNamedQuery("User.findAll").getResultList();
    }

    public User readUserByID(int id) {
        return em.find(User.class, id);
    }

    public User readUserByLogin(String login) {
        return (User) em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
    }
    
    public User readUserBySessionID(String sessionid) {
        return (User) em.createNativeQuery("SELECT * FROM `user` WHERE user.SessionID = '" + sessionid +"'", User.class).getSingleResult();
    }

    // Media
    public Media insert(Media i) {
        em.persist(i);
        return i;
    }

    public void update(Media i) {
        em.merge(i);
    }

    public int readLastIndexOfMedias() {
        return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult();
    }
    
    public int getNextMediaId() {
        return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult() + 1;
    }
    
    public List<Media> searchMediaByTag(String word) {
        return em.createNativeQuery("SELECT * FROM media,media_tag,tag WHERE media_tag.mediaId = media.ID and media_tag.tagId = tag.ID and tag.tag LIKE '%" + word +"%'", Media.class).getResultList();
         
    }
    
    public List<Media> searchMediaByUserLogin(String word) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM media, user WHERE media.userId = user.ID AND user.Login LIKE '%" + word +"%'", Media.class).getResultList();
        return mlst;
    }
    
    public List<Media> readNLatestMedias(int num) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM `media` ORDER BY date DESC, ID DESC LIMIT " + num, Media.class).getResultList();
        return mlst;
    }

    public List<Media> searchMediaByTitle(String word) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM `media` WHERE title LIKE '%" + word +"%'", Media.class).getResultList();
        return mlst;
    }
    
    public List<Media> readAllMedias() {
        return em.createNamedQuery("Media.findAll").getResultList();
    }

    public Media readMediaByMediaID(int id) {
        Media m = (Media)em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        em.refresh(m); // <--- IMPORTANT
        return m;
    }
    
    public List<Media> readMediaFromFriends(String ids) {
        return em.createNativeQuery("SELECT * FROM `media` WHERE userId in (" + ids +") LIMIT 6", Media.class).getResultList();
    }

    public List<Media> readMediaByUserID(int id) {
        return em.createNamedQuery("User.findById").setParameter("id", id).getResultList();
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
        return em.createNamedQuery("Comment.findAll").getResultList();
    }

    public List<Comment> readCommentByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Comment>(m.getCommentCollection());
    }

    public List<Comment> readCommentByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Comment>(u.getCommentCollection());
    }

    // Friend
    public Friend insert(Friend f) {
        em.persist(f);
        return f;
    }

    public void update(Friend f) {
        em.merge(f);
    }

    public List<Friend> readAllFriends() {
        return em.createNamedQuery("Friend.findAll").getResultList();
    }

    public List<Friend> readFriendByOwnerID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Friend>(u.getFriendCollection());
    }

    public List<Friend> readFriendByFriendID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Friend>(u.getFriendCollection1());
    }

    // MediaLike
    public MediaLike insert(MediaLike l) {
        em.persist(l);
        return l;
    }

    public void update(MediaLike l) {
        em.merge(l);
    }

    public List<MediaLike> readAllMediaLikes() {
        return em.createNamedQuery("MediaLike.findAll").getResultList();
    }

    public List<MediaLike> readLikeByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<MediaLike>(m.getMediaLikeCollection());
    }

    public List<MediaLike> readLikeByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<MediaLike>(u.getMediaLikeCollection());
    }

    // MediaTag
    public MediaTag insert(MediaTag it) {
        em.persist(it);
        return it;
    }

    public void update(MediaTag it) {
        em.merge(it);
    }

    public List<MediaTag> readAllMediaTags() {
        return em.createNamedQuery("MediaTag.findAll").getResultList();
    }

    public List<MediaTag> readMediaTagByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<MediaTag>(m.getMediaTagCollection());
    }

    public List<MediaTag> readMediaTagByTagID(int id) {
        Tag t = (Tag) em.createNamedQuery("Tag.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<MediaTag>(t.getMediaTagCollection());
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
        return em.createNamedQuery("Tag.findAll").getResultList();
    }

    public List<Tag> readTagById(int id) {
        return em.createNamedQuery("Tag.findById").setParameter("id", id).getResultList();
    }

    public List<Tag> readTagByTag(String tag) {
        return em.createNamedQuery("Tag.findByTag").setParameter("tag", tag).getResultList();
    }
}
