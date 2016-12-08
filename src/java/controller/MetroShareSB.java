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
 * MetroShare session bean.
 * 
 * Responsible for handling ORM and other SQL queries.
 */
@Stateless
public class MetroShareSB {

    @PersistenceContext
    private EntityManager em;

    /**
     * Create a new user.
     * 
     * @param u User to create
     * @return Instance of the created user
     */
    public User insert(User u) {
        em.persist(u);
        return u;
    }

    /**
     * Update a user.
     * 
     * @param u User to update
     */
    public void update(User u) {
        em.merge(u);
    }

    /**
     * Retrieve all users.
     * 
     * @return All users as a list
     */
    public List<User> readAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }
    
    /**
     * Retrieve a user by its ID.
     * 
     * @param id ID of the user
     * @return Instance of the user
     */
    public User readUserByID(int id) {
        return em.find(User.class, id);
    }

    /**
     * Retrieve a user by its username.
     * 
     * @param login Username of the user
     * @return Instance of the user
     */
    public User readUserByLogin(String login) {
        return (User) em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
    }
    
    /**
     * Retrieve a user by its session ID.
     * 
     * @param sessionid Session ID of the user
     * @return Instance of the user
     */
    public User readUserBySessionID(String sessionid) {
        return (User) em.createNativeQuery("SELECT * FROM `user` WHERE user.SessionID = '" + sessionid +"'", User.class).getSingleResult();
    }

    /**
     * Create a new medium.
     * 
     * @param i Medium to create
     * @return Instance of the created medium
     */
    public Media insert(Media i) {
        em.persist(i);
        return i;
    }

    /**
     * Update a medium.
     * 
     * @param i Medium to update
     */
    public void update(Media i) {
        em.merge(i);
    }

    /**
     * Retrieve the ID of the latest medium.
     * 
     * @return The greatest ID as an integer
     */
    public int readLastIndexOfMedias() {
        return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult();
    }
    
    /**
     * Retrieve the ID of the next medium.
     * 
     * Calculate and return the ID that the next medium will get in the database.
     * 
     * @return The next ID as an integer
     */
    public int getNextMediaId() {
        return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult() + 1;
    }
    
    /**
     * Retrieve all media by the given tag.
     * 
     * @param word Search keyword
     * @return All matching media as a list
     */
    public List<Media> searchMediaByTag(String word) {
        return em.createNativeQuery("SELECT * FROM media,media_tag,tag WHERE media_tag.mediaId = media.ID and media_tag.tagId = tag.ID and tag.tag LIKE '%" + word +"%'", Media.class).getResultList();
    }
    
    /**
     * Retrieve all media by the given username.
     * 
     * @param word Username of the user
     * @return All matching media as a list
     */
    public List<Media> searchMediaByUserLogin(String word) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM media, user WHERE media.userId = user.ID AND user.Login LIKE '%" + word +"%'", Media.class).getResultList();
        return mlst;
    }
    
    /**
     * Retrieve the given amount of the latest media.
     * 
     * @param num Amount of media to retrieve
     * @return The latest media as a list
     */
    public List<Media> readNLatestMedias(int num) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM `media` ORDER BY date DESC, ID DESC LIMIT " + num, Media.class).getResultList();
        return mlst;
    }

    /**
     * Retrieve all media by the given title
     * 
     * @param word Search keyword
     * @return All matching media as a list
     */
    public List<Media> searchMediaByTitle(String word) {
        List<Media> mlst = em.createNativeQuery("SELECT * FROM `media` WHERE title LIKE '%" + word +"%'", Media.class).getResultList();
        return mlst;
    }
    
    /**
     * Retrieve all media.
     * 
     * @return All media as a list
     */
    public List<Media> readAllMedias() {
        return em.createNamedQuery("Media.findAll").getResultList();
    }
    
    /**
     * Retrieve all medium IDs.
     * 
     * @return All media IDs as a list
     */
    public List<Media> readAllMediaIds() {
        return em.createNativeQuery("SELECT ID FROM media").getResultList();  
    }

    /**
     * Retrieve medium by its ID.
     * 
     * @param id ID of the medium
     * @return Instance of the medium
     */
    public Media readMediaByMediaID(int id) {
        Media m = (Media)em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        em.refresh(m);
        
        return m;
    }
    
    /**
     * Retrieve media a from the given IDs.
     * 
     * @param ids IDs whose media to retrieve
     * @return All matching media as a list
     */
    public List<Media> readMediaFromFriends(String ids) {
        return em.createNativeQuery("SELECT * FROM `media` WHERE userId in (" + ids +") LIMIT 6", Media.class).getResultList();
    }

    /**
     * Retrieve all media that belong to the given user ID.
     * 
     * @param id ID of the user
     * @return All matching media as a list
     */
    public List<Media> readMediaByUserID(int id) {
        return em.createNamedQuery("User.findById").setParameter("id", id).getResultList();
    }

    /**
     * Create a comment.
     * 
     * @param c Comment to create
     * @return Instance of the comment
     */
    public Comment insert(Comment c) {
        em.persist(c);
        
        return c;
    }

    /**
     * Update a comment.
     * 
     * @param c Comment to update
     */
    public void update(Comment c) {
        em.merge(c);
    }

    /**
     * Retrieve all comments.
     * 
     * @return All comments as a list
     */
    public List<Comment> readAllComments() {
        return em.createNamedQuery("Comment.findAll").getResultList();
    }

    /**
     * Retrieve all comments of the given medium by its ID.
     * 
     * @param id ID of the medium
     * @return All matching comments as a list
     */
    public List<Comment> readCommentByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Comment>(m.getCommentCollection());
    }

    /**
     * Retrieve all comments of the given user by its ID.
     * 
     * @param id ID of the user
     * @return All matching comments as a list
     */
    public List<Comment> readCommentByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<Comment>(u.getCommentCollection());
    }

    /**
     * Create a friend.
     * 
     * @param f Friend to create
     * @return Instance of the friend
     */
    public Friend insert(Friend f) {
        em.persist(f);
        
        return f;
    }

    /**
     * Update a friend
     * 
     * @param f Friend to update
     */
    public void update(Friend f) {
        em.merge(f);
    }

    /**
     * Retrieve all friends.
     * 
     * @return All friends as as list
     */
    public List<Friend> readAllFriends() {
        return em.createNamedQuery("Friend.findAll").getResultList();
    }

    /**
     * Retrieve friends of the given user by ID.
     * 
     * @param id User whose friends to retrieve
     * @return All friends of the user as a list
     */
    public List<Friend> readFriendByOwnerID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        
        return new ArrayList<Friend>(u.getFriendCollection());
    }

    /**
     * Retrieve a friend by friend ID.
     * 
     * @param id ID of the friend
     * @return Instance of the user
     */
    public List<Friend> readFriendByFriendID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        
        return new ArrayList<Friend>(u.getFriendCollection1());
    }

    /**
     * Create a like for a medium.
     * 
     * @param l Like to create
     * @return Instance of the like
     */
    public MediaLike insert(MediaLike l) {
        em.persist(l);
        
        return l;
    }

    /**
     * Update a like.
     * 
     * @param l Like to update
     */
    public void update(MediaLike l) {
        em.merge(l);
    }

    /**
     * Retrieve all likes.
     * 
     * @return All likes as a list
     */
    public List<MediaLike> readAllMediaLikes() {
        return em.createNamedQuery("MediaLike.findAll").getResultList();
    }

    /**
     * Retrieve all likes of the given medium by ID.
     * 
     * @param id ID of the media
     * @return All likes of the medium as a list
     */
    public List<MediaLike> readLikeByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        
        
        return new ArrayList<MediaLike>(m.getMediaLikeCollection());
    }

    /**
     * Retrieve all likes of the given user ID.
     * 
     * @param id ID of the user
     * @return All likes of the user as a list
     */
    public List<MediaLike> readLikeByUserID(int id) {
        User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        
        return new ArrayList<MediaLike>(u.getMediaLikeCollection());
    }

    /**
     * Create a media tag.
     * 
     * @param it Media tag to create
     * @return Instance of the media tag
     */
    public MediaTag insert(MediaTag it) {
        em.persist(it);
        
        return it;
    }

    /**
     * Update a media tag.
     * 
     * @param it Media tag to update
     */
    public void update(MediaTag it) {
        em.merge(it);
    }

    /**
     * Retrieve all media tags.
     * 
     * @return All media tags as as list
     */
    public List<MediaTag> readAllMediaTags() {
        return em.createNamedQuery("MediaTag.findAll").getResultList();
    }

    /**
     * Retrieve all media tags of a medium by its ID.
     * 
     * @param id ID of the medium
     * @return All media tags of the medium
     */
    public List<MediaTag> readMediaTagByMediaID(int id) {
        Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
        
        return new ArrayList<MediaTag>(m.getMediaTagCollection());
    }

    /**
     * Retrieve a media tag by its ID.
     * 
     * @param id ID of the media tag
     * @return All matching media tags as as list
     */
    public List<MediaTag> readMediaTagByTagID(int id) {
        Tag t = (Tag) em.createNamedQuery("Tag.findById").setParameter("id", id).getSingleResult();
        return new ArrayList<MediaTag>(t.getMediaTagCollection());
    }

    /**
     * Create a tag.
     * 
     * @param t Tag to create
     * @return Instance of the tag
     */
    public Tag insert(Tag t) {
        em.persist(t);
        
        return t;
    }

    /**
     * Update a tag.
     * 
     * @param t Tag to update
     */
    public void update(Tag t) {
        em.merge(t);
    }

    /**
     * Retrieve all tags.
     * 
     * @return All tags as a list
     */
    public List<Tag> readAllTags() {
        return em.createNamedQuery("Tag.findAll").getResultList();
    }

    /**
     * Retrieve a tag by its ID.
     * 
     * @param id ID of the tag
     * @return Instance of the tag
     */
    public List<Tag> readTagById(int id) {
        return em.createNamedQuery("Tag.findById").setParameter("id", id).getResultList();
    }

    /**
     * Retrieve a tag by its name. 
     * 
     * @param tag Name of the tag.
     * @return Instance of the tag
     */
    public List<Tag> readTagByTag(String tag) {
        return em.createNamedQuery("Tag.findByTag").setParameter("tag", tag).getResultList();
    }
}
