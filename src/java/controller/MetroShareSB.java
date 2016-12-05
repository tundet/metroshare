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
        List<Media> ilst = em.createNamedQuery("Media.findAll").getResultList();
        if (ilst == null) {
            Media i = new Media(null);
            ilst.add(i);
            return ilst;
        } else {
            return ilst;
        }
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
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        List<Media> mlst = new ArrayList<Media>();
        for (Media m : u.getMediaCollection()) {
            mlst.add(m);
        }
        if (mlst == null) {
            Media i = new Media(null);
            mlst.add(i);
            return mlst;
        } else {
            return mlst;
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
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Comment>(m.getCommentCollection());
    }

    public List<Comment> readCommentByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        List<Comment> clst = new ArrayList<Comment>();
        for (Comment c : u.getCommentCollection()) {
            clst.add(c);
        }
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

    public List<Friend> readAllFriends() {
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
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        List<Friend> flst = new ArrayList<Friend>();
        for (Friend f : u.getFriendCollection()) {
            flst.add(f);
        }
        if (flst == null) {
            Friend f = new Friend(null);
            flst.add(f);
            return flst;
        } else {
            return flst;
        }
    }

    public List<Friend> readFriendByFriendID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        List<Friend> flst = new ArrayList<Friend>();
        for (Friend f : u.getFriendCollection1()) {
            flst.add(f);
        }
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

    public List<MediaLike> readAllMediaLikes() {
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
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        List<MediaLike> mllst = new ArrayList<MediaLike>();
        for (MediaLike ml : m.getMediaLikeCollection()) {
            mllst.add(ml);
        }
        if (mllst == null) {
            MediaLike l = new MediaLike(null);
            mllst.add(l);
            return mllst;
        } else {
            return mllst;
        }
    }

    public List<MediaLike> readLikeByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        List<MediaLike> mllst = new ArrayList<MediaLike>();
        for (MediaLike ml : u.getMediaLikeCollection()) {
            mllst.add(ml);
        }
        if (mllst == null) {
            MediaLike ml = new MediaLike(null);
            mllst.add(ml);
            return mllst;
        } else {
            return mllst;
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

    public List<MediaTag> readAllMediaTags() {
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
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        List<MediaTag> mtlst = new ArrayList<MediaTag>();
        for (MediaTag mt : m.getMediaTagCollection()) {
            mtlst.add(mt);
        }
        if (mtlst == null) {
            MediaTag it = new MediaTag(null);
            mtlst.add(it);
            return mtlst;
        } else {
            return mtlst;
        }
    }

    public List<MediaTag> readMediaTagByTagID(int id) {
        Tag t = (Tag) em.createNamedQuery("Tag.findById").setParameter("id", id).getSingleResult();
        List<MediaTag> mtlst = new ArrayList<MediaTag>();
        for (MediaTag mt : t.getMediaTagCollection()) {
            mtlst.add(mt);
        }
        if (mtlst == null) {
            MediaTag mt = new MediaTag(null);
            mtlst.add(mt);
            return mtlst;
        } else {
            return mtlst;
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
