package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "media_like")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaLike.findAll", query = "SELECT m FROM MediaLike m")
    , @NamedQuery(name = "MediaLike.findById", query = "SELECT m FROM MediaLike m WHERE m.id = :id")
    , @NamedQuery(name = "MediaLike.findByLikeBoolean", query = "SELECT m FROM MediaLike m WHERE m.likeBoolean = :likeBoolean")
    , @NamedQuery(name = "MediaLike.findByDate", query = "SELECT m FROM MediaLike m WHERE m.date = :date")})
public class MediaLike implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "likeBoolean")
    private Boolean likeBoolean;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "userId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "mediaId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Media mediaId;

    public MediaLike() {
    }

    public MediaLike(Integer id) {
        this.id = id;
    }

    public MediaLike(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLikeBoolean() {
        return likeBoolean;
    }

    public void setLikeBoolean(Boolean likeBoolean) {
        this.likeBoolean = likeBoolean;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Media getMediaId() {
        return mediaId;
    }

    public void setMediaId(Media mediaId) {
        this.mediaId = mediaId;
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
        if (!(object instanceof MediaLike)) {
            return false;
        }
        MediaLike other = (MediaLike) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MediaLike[ id=" + id + " ]";
    }
    
}
