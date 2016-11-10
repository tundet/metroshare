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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "like")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Like1.findAll", query = "SELECT l FROM Like1 l")
    , @NamedQuery(name = "Like1.findById", query = "SELECT l FROM Like1 l WHERE l.id = :id")
    , @NamedQuery(name = "Like1.findByImgId", query = "SELECT l FROM Like1 l WHERE l.imgId = :imgId")
    , @NamedQuery(name = "Like1.findByUserId", query = "SELECT l FROM Like1 l WHERE l.userId = :userId")
    , @NamedQuery(name = "Like1.findByLikeBoolean", query = "SELECT l FROM Like1 l WHERE l.likeBoolean = :likeBoolean")
    , @NamedQuery(name = "Like1.findByDate", query = "SELECT l FROM Like1 l WHERE l.date = :date")})
public class Like1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imgId")
    private int imgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userId")
    private int userId;
    @Column(name = "likeBoolean")
    private Boolean likeBoolean;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Img> imgCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "like1")
    private Img img;

    public Like1() {
    }

    public Like1(Integer id) {
        this.id = id;
    }

    public Like1(Integer id, int imgId, int userId, Date date) {
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

    @XmlTransient
    public Collection<Img> getImgCollection() {
        return imgCollection;
    }

    public void setImgCollection(Collection<Img> imgCollection) {
        this.imgCollection = imgCollection;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
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
        if (!(object instanceof Like1)) {
            return false;
        }
        Like1 other = (Like1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Like1[ id=" + id + " ]";
    }
    
}
