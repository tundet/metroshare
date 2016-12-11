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
        try {
            em.persist(u);
            return u;
        } catch (Exception e) {
            System.err.println("User insert error: " + e);
            return null;
        }
    }

    /**
     * Update a user.
     *
     * @param u User to update
     */
    public void update(User u) {
        try {
            em.merge(u);
        } catch (Exception e) {
            System.err.println("User update error: " + e);
        }
    }

    /**
     * Retrieve all users.
     *
     * @return All users as a list
     */
    public List<User> readAllUsers() {
        try {
            List<User> ul = em.createNamedQuery("User.findAll").getResultList();
            return ul;
        } catch (Exception e) {
            System.err.println("User real all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a user by its ID.
     *
     * @param id ID of the user
     * @return Instance of the user
     */
    public User readUserByID(int id) {
        try {
            User u = em.find(User.class, id);
            em.refresh(u);
            return u;
        } catch (Exception e) {
            System.err.println("User read by id error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a user by its username.
     *
     * @param login Username of the user
     * @return Instance of the user
     */
    public User readUserByLogin(String login) {
        try {
            User u = (User) em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
            return u;
        } catch (Exception e) {
            System.err.println("User read by login error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a user by its session ID.
     *
     * @param sessionid Session ID of the user
     * @return Instance of the user
     */
    public User readUserBySessionID(String sessionid) {
        try {
            return (User) em.createNativeQuery("SELECT * FROM `user` WHERE user.SessionID = '" + sessionid.trim() + "';",User.class).getSingleResult();
        } catch (Exception e) {
            System.err.println("User read by session id error: " + e);
            return null;
        }
    }

    /**
     * Create a new medium.
     *
     * @param i Medium to create
     * @return Instance of the created medium
     */
    public Media insert(Media m) {
        try {
            em.persist(m);
            return m;
        } catch (Exception e) {
            System.err.println("Media insert error: " + e);
            return null;
        }
    }

    /**
     * Update a medium.
     *
     * @param i Medium to update
     */
    public void update(Media m) {
        try {
            em.merge(m);
        } catch (Exception e) {
            System.err.println("Media update error: " + e);
        }
    }

    /**
     * Retrieve the ID of the latest medium.
     *
     * @return The greatest ID as an integer
     */
    public int readLastIndexOfMedias() {
        System.out.println("Works here");
        try {
            return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult();
        } catch (Exception e) {
            System.err.println("Media read last index error: " + e);
            return 0;
        }
    }

    /**
     * Retrieve the ID of the next medium.
     *
     * Calculate and return the ID that the next medium will get in the
     * database.
     *
     * @return The next ID as an integer
     */
    public int readNextMediaId() {
        try {
            return (int) em.createNativeQuery("SELECT MAX(ID) FROM media").getSingleResult() + 1;
        } catch (Exception e) {
            System.err.println("Media read next id error: " + e);
            return 0;
        }
    }

    /**
     * Retrieve the given amount of the latest media.
     *
     * @param num Amount of media to retrieve
     * @return The latest media as a list
     */
    public List<Media> readNLatestMedias(int num) {
        try {
            List<Media> ml = em.createNativeQuery("SELECT * FROM `media` ORDER BY date DESC, ID DESC LIMIT " + num, Media.class).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read latest n error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media.
     *
     * @return All media as a list
     */
    public List<Media> readAllMedias() {
        try {
            List<Media> ml = em.createNamedQuery("Media.findAll").getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all medium IDs.
     *
     * @return All media IDs as a list
     */
    public List<Media> readAllMediaIds() {
        try {
            List<Media> ml = em.createNativeQuery("SELECT ID FROM media").getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read all ids error: " + e);
            return null;
        }
    }

    /**
     * Retrieve medium by its ID.
     *
     * @param id ID of the medium
     * @return Instance of the medium
     */
    public Media readMediaByMediaID(int id) {
        try {
            Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
            em.refresh(m); // <--- IMPORTANT
            return m;
        } catch (Exception e) {
            System.err.println("Media read by mediaId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve media a from the given IDs.
     *
     * @param ids IDs whose media to retrieve
     * @return All matching media as a list
     */
    public List<Media> readMediaFromFriends(String ids) {
        try {
            List<Media> ml = em.createNativeQuery("SELECT * FROM `media` WHERE userId in (" + ids + ") LIMIT 6", Media.class).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read by friends error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media that belong to the given user ID.
     *
     * @param id ID of the user
     * @return All matching media as a list
     */
    public List<Media> readMediaByUserID(int id) {
        try {
            List<Media> ml = em.createNamedQuery("User.findById").setParameter("id", id).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read by userId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media by the given tag.
     *
     * @param word Search keyword
     * @return All matching media as a list
     */
    public List<Media> searchMediaByTag(String word) {
        try {
            List<Media> ml = em.createNativeQuery("SELECT * FROM media,media_tag,tag WHERE media_tag.mediaId = media.ID and media_tag.tagId = tag.ID and tag.tag LIKE '%" + word + "%'", Media.class).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media read bby tag error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media by the given username.
     *
     * @param word Username of the user
     * @return All matching media as a list
     */
    public List<Media> searchMediaByUserLogin(String word) {
        try {
            List<Media> ml = em.createNativeQuery("SELECT * FROM media, user WHERE media.userId = user.ID AND user.Login LIKE '%" + word + "%'", Media.class).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media search by login error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media by the given title
     *
     * @param word Search keyword
     * @return All matching media as a list
     */
    public List<Media> searchMediaByTitle(String word) {
        try {
            List<Media> ml = em.createNativeQuery("SELECT * FROM `media` WHERE title LIKE '%" + word + "%'", Media.class).getResultList();
            return ml;
        } catch (Exception e) {
            System.err.println("Media search by title error: " + e);
            return null;
        }
    }

    /**
     * Create a comment.
     *
     * @param c Comment to create
     * @return Instance of the comment
     */
    public Comment insert(Comment c) {
        try {
            em.persist(c);
            return c;
        } catch (Exception e) {
            System.err.println("Comment insert error: " + e);
            return null;
        }
    }

    /**
     * Update a comment.
     *
     * @param c Comment to update
     */
    public void update(Comment c) {
        try {
            em.merge(c);
        } catch (Exception e) {
            System.err.println("Comment update error: " + e);
        }
    }

    /**
     * Retrieve all comments.
     *
     * @return All comments as a list
     */
    public List<Comment> readAllComments() {
        try {
            List<Comment> cl = em.createNamedQuery("Comment.findAll").getResultList();
            return cl;
        } catch (Exception e) {
            System.err.println("Comment real all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all comments of the given medium by its ID.
     *
     * @param id ID of the medium
     * @return All matching comments as a list
     */
    public List<Comment> readCommentByMediaID(int id) {
        try {
            Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
            em.refresh(m);
            return new ArrayList<Comment>(m.getCommentCollection());
        } catch (Exception e) {
            System.err.println("Comment read by mediaId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all comments of the given user by its ID.
     *
     * @param id ID of the user
     * @return All matching comments as a list
     */
    public List<Comment> readCommentByUserID(int id) {
        try {
            User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
            em.refresh(u);
            return new ArrayList<Comment>(u.getCommentCollection());
        } catch (Exception e) {
            System.err.println(" error: " + e);
            return null;
        }
    }

    /**
     * Create a friend.
     *
     * @param f Friend to create
     * @return Instance of the friend
     */
    public Friend insert(Friend f) {
        try {
            em.persist(f);
            return f;
        } catch (Exception e) {
            System.err.println("Friend insert error: " + e);
            return null;
        }
    }

    /**
     * Update a friend
     *
     * @param f Friend to update
     */
    public void update(Friend f) {
        try {
            em.merge(f);
        } catch (Exception e) {
            System.err.println("Friend update error: " + e);
        }
    }

    /**
     * Retrieve all friends.
     *
     * @return All friends as as list
     */
    public List<Friend> readAllFriends() {
        try {
            List<Friend> fl = em.createNamedQuery("Friend.findAll").getResultList();
            return fl;
        } catch (Exception e) {
            System.err.println("Friend read all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve friends of the given user by ID.
     *
     * @param id User whose friends to retrieve
     * @return All friends of the user as a list
     */
    public List<Friend> readFriendByOwnerID(int id) {
        try {
            User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
            em.refresh(u);
            return new ArrayList<Friend>(u.getFriendCollection());
        } catch (Exception e) {
            System.err.println("Friend read by ownerId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a friend by friend ID.
     *
     * @param id ID of the friend
     * @return Instance of the user
     */
    public List<Friend> readFriendByFriendID(int id) {
        try {
            User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
            em.refresh(u);
            return new ArrayList<Friend>(u.getFriendCollection1());
        } catch (Exception e) {
            System.err.println("Friend read by friendId error: " + e);
            return null;
        }
    }

    /**
     * Create a like for a medium.
     *
     * @param l Like to create
     * @return Instance of the like
     */
    public MediaLike insert(MediaLike ml) {
        try {
            em.persist(ml);
            return ml;
        } catch (Exception e) {
            System.err.println("MediaLike insert error: " + e);
            return null;
        }
    }

    /**
     * Update a like.
     *
     * @param l Like to update
     */
    public void update(MediaLike ml) {
        try {
            em.merge(ml);
        } catch (Exception e) {
            System.err.println("MediaLike update error: " + e);
        }
    }

    /**
     * Remove a like from a medium.
     *
     * @param id ID of the like
     */
    public void removeMediaLike(int id) {
        try {
            em.remove((MediaLike) em.createNamedQuery("MediaLike.findById").setParameter("id", id).getSingleResult());
        } catch (Exception e) {
            System.err.println("MediaLike remove error: " + e);
        }
    }

    /**
     * Retrieve all media likes.
     *
     * @return All media likes as a list
     */
    public List<MediaLike> readAllMediaLikes() {
        try {
            List<MediaLike> mll = em.createNamedQuery("MediaLike.findAll").getResultList();
            return mll;
        } catch (Exception e) {
            System.err.println("MediaLike read all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all likes of the given medium by ID.
     *
     * @param id ID of the media
     * @return All likes of the medium as a list
     */
    public List<MediaLike> readLikeByMediaID(int id) {
        try {
            Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
            em.refresh(m);
            return new ArrayList<MediaLike>(m.getMediaLikeCollection());
        } catch (Exception e) {
            System.err.println("MediaLike read by mediaId error: " + e);
            return null;
        }
    }
    
    /**
     * Retrieve media with most likes in descending order.
     * 
     * @return All media likes as a list
     */
    public List<MediaLike> readMostMediaLikes() {
        try {
            List<MediaLike> mll = em.createNativeQuery("SELECT media_like.Id,media_like.mediaId, SUM(likeBoolean) FROM media_like GROUP BY mediaId ORDER BY SUM(likeBoolean) DESC", MediaLike.class).getResultList();
            return mll;
        } catch (Exception e) {
            System.err.println("Media search by most likes error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all likes of the given user ID.
     *
     * @param id ID of the user
     * @return All likes of the user as a list
     */
    public List<MediaLike> readLikeByUserID(int id) {
        try {
            User u = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
            em.refresh(u);
            return new ArrayList<MediaLike>(u.getMediaLikeCollection());
        } catch (Exception e) {
            System.err.println("MediaLike read by userId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a media like by its ID.
     *
     * @param id ID of the like
     * @return Instance of the like on success, null on failure
     */
    public MediaLike readMediaLikeByMediaId(int id) {
        try {
            MediaLike ml = (MediaLike) em.createNamedQuery("MediaLike.findById").setParameter("id", id).getSingleResult();
            return ml;
        } catch (Exception e) {
            System.err.println("MediaLike red by mediaId error: " + e);
            return null;
        }
    }

    /**
     * Create a media tag.
     *
     * @param it Media tag to create
     * @return Instance of the media tag
     */
    public MediaTag insert(MediaTag mt) {
        try {
            em.persist(mt);
            return mt;
        } catch (Exception e) {
            System.err.println("MediaTag insert error: " + e);
            return null;
        }
    }

    /**
     * Update a media tag.
     *
     * @param it Media tag to update
     */
    public void update(MediaTag mt) {
        try {
            em.merge(mt);
        } catch (Exception e) {
            System.err.println("MediaTag update error: " + e);
        }
    }

    /**
     * Retrieve all media tags.
     *
     * @return All media tags as as list
     */
    public List<MediaTag> readAllMediaTags() {
        try {
            List<MediaTag> mtl = em.createNamedQuery("MediaTag.findAll").getResultList();
            return mtl;
        } catch (Exception e) {
            System.err.println("MediaTag read all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve all media tags of a medium by its ID.
     *
     * @param id ID of the medium
     * @return All media tags of the medium
     */
    public List<MediaTag> readMediaTagByMediaID(int id) {
        try {
            Media m = (Media) em.createNamedQuery("Media.findById").setParameter("id", id).getSingleResult();
            em.refresh(m);
            return new ArrayList<MediaTag>(m.getMediaTagCollection());
        } catch (Exception e) {
            System.err.println("MediaTag read by mediaId error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a media tag by its ID.
     *
     * @param id ID of the media tag
     * @return All matching media tags as as list
     */
    public List<MediaTag> readMediaTagByTagID(int id) {
        try {
            Tag t = (Tag) em.createNamedQuery("Tag.findById").setParameter("id", id).getSingleResult();
            em.refresh(t);
            return new ArrayList<MediaTag>(t.getMediaTagCollection());
        } catch (Exception e) {
            System.err.println("MediaTag read by tagId error: " + e);
            return null;
        }
    }

    /**
     * Create a tag.
     *
     * @param t Tag to create
     * @return Instance of the tag
     */
    public Tag insert(Tag t) {
        try {
            em.persist(t);
            return t;
        } catch (Exception e) {
            System.err.println("Tag insert error: " + e);
            return null;
        }
    }

    /**
     * Update a tag.
     *
     * @param t Tag to update
     */
    public void update(Tag t) {
        try {
            em.merge(t);
        } catch (Exception e) {
            System.err.println("Tag update error: " + e);
        }
    }

    /**
     * Retrieve all tags.
     *
     * @return All tags as a list
     */
    public List<Tag> readAllTags() {
        try {
            List<Tag> tl = em.createNamedQuery("Tag.findAll").getResultList();
            return tl;
        } catch (Exception e) {
            System.err.println("Tag read all error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a tag by its ID.
     *
     * @param id ID of the tag
     * @return Instance of the tag
     */
    public List<Tag> readTagById(int id) {
        try {
            List<Tag> tl = em.createNamedQuery("Tag.findById").setParameter("id", id).getResultList();
            return tl;
        } catch (Exception e) {
            System.err.println("Tag read by id error: " + e);
            return null;
        }
    }

    /**
     * Retrieve a tag by its name.
     *
     * @param tag Name of the tag
     * @return Instance of the tag
     */
    public List<Tag> readTagByTag(String tag) {
        try {
            List<Tag> tl = em.createNamedQuery("Tag.findByTag").setParameter("tag", tag).getResultList();
            return tl;
        } catch (Exception e) {
            System.err.println("Tag read by tag error: " + e);
            return null;
        }
    }
}
