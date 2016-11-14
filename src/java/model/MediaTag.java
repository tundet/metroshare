/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "media_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaTag.findAll", query = "SELECT m FROM MediaTag m")
    , @NamedQuery(name = "MediaTag.findById", query = "SELECT m FROM MediaTag m WHERE m.id = :id")})
public class MediaTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @JoinColumn(name = "mediaId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Media mediaId;
    @JoinColumn(name = "tagId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tag tagId;

    public MediaTag() {
    }

    public MediaTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Media getMediaId() {
        return mediaId;
    }

    public void setMediaId(Media mediaId) {
        this.mediaId = mediaId;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
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
        if (!(object instanceof MediaTag)) {
            return false;
        }
        MediaTag other = (MediaTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MediaTag[ id=" + id + " ]";
    }
    
}
