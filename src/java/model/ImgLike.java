/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "img_like")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImgLike.findAll", query = "SELECT i FROM ImgLike i")
    , @NamedQuery(name = "ImgLike.findById", query = "SELECT i FROM ImgLike i WHERE i.id = :id")
    , @NamedQuery(name = "ImgLike.findByImgId", query = "SELECT i FROM ImgLike i WHERE i.imgId = :imgId")
    , @NamedQuery(name = "ImgLike.findByUserId", query = "SELECT i FROM ImgLike i WHERE i.userId = :userId")
    , @NamedQuery(name = "ImgLike.findByLikeBoolean", query = "SELECT i FROM ImgLike i WHERE i.likeBoolean = :likeBoolean")
    , @NamedQuery(name = "ImgLike.findByDate", query = "SELECT i FROM ImgLike i WHERE i.date = :date")})
public class ImgLike implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    private int imgId;
    @Basic(optional = false)
    @NotNull
    private int userId;
    private Boolean likeBoolean;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public ImgLike() {
    }

    public ImgLike(Integer id) {
        this.id = id;
    }

    public ImgLike(Integer id, int imgId, int userId, Date date) {
        this.id = id;
        this.imgId = imgId;
        this.userId = userId;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImgLike)) {
            return false;
        }
        ImgLike other = (ImgLike) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ImgLike[ id=" + id + " ]";
    }
    
}
