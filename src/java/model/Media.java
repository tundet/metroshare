/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "media")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Media.findAll", query = "SELECT m FROM Media m")
    , @NamedQuery(name = "Media.findById", query = "SELECT m FROM Media m WHERE m.id = :id")
    , @NamedQuery(name = "Media.findByMediaLocation", query = "SELECT m FROM Media m WHERE m.mediaLocation = :mediaLocation")
    , @NamedQuery(name = "Media.findByDate", query = "SELECT m FROM Media m WHERE m.date = :date")
    , @NamedQuery(name = "Media.findByNsfw", query = "SELECT m FROM Media m WHERE m.nsfw = :nsfw")})
public class Media implements Serializable {

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mediaLocation")
    private String mediaLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nsfw")
    private boolean nsfw;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mediaId")
    private Collection<MediaTag> mediaTagCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mediaId", fetch = FetchType.EAGER)
    private Collection<Comment> commentCollection;
    @JoinColumn(name = "userId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mediaId")
    private Collection<MediaLike> mediaLikeCollection;

    public Media() {
    }

    public Media(Integer id) {
        this.id = id;
    }

    public Media(Integer id, String mediaLocation, Date date, boolean nsfw) {
        this.id = id;
        this.mediaLocation = mediaLocation;
        this.date = date;
        this.nsfw = nsfw;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediaLocation() {
        return mediaLocation;
    }

    public void setMediaLocation(String mediaLocation) {
        this.mediaLocation = mediaLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    //@XmlTransient
    public Collection<MediaTag> getMediaTagCollection() {
        return mediaTagCollection;
    }

    public void setMediaTagCollection(Collection<MediaTag> mediaTagCollection) {
        this.mediaTagCollection = mediaTagCollection;
    }

    //@XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    //@XmlTransient
    public Collection<MediaLike> getMediaLikeCollection() {
        return mediaLikeCollection;
    }

    public void setMediaLikeCollection(Collection<MediaLike> mediaLikeCollection) {
        this.mediaLikeCollection = mediaLikeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Media)) {
            return false;
        }
        Media other = (Media) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Media[ id=" + id + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
