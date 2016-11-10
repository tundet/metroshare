/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "img")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Img.findAll", query = "SELECT i FROM Img i")
    , @NamedQuery(name = "Img.findById", query = "SELECT i FROM Img i WHERE i.id = :id")
    , @NamedQuery(name = "Img.findByImageLocation", query = "SELECT i FROM Img i WHERE i.imageLocation = :imageLocation")
    , @NamedQuery(name = "Img.findByDate", query = "SELECT i FROM Img i WHERE i.date = :date")})
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "imageLocation")
    private String imageLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private Like1 userId;
    @JoinColumn(name = "ID", referencedColumnName = "imgId", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Like1 like1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "img")
    private User user;

    public Img() {
    }

    public Img(Integer id) {
        this.id = id;
    }

    public Img(Integer id, String imageLocation, Date date) {
        this.id = id;
        this.imageLocation = imageLocation;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Like1 getUserId() {
        return userId;
    }

    public void setUserId(Like1 userId) {
        this.userId = userId;
    }

    public Like1 getLike1() {
        return like1;
    }

    public void setLike1(Like1 like1) {
        this.like1 = like1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Img)) {
            return false;
        }
        Img other = (Img) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Img[ id=" + id + " ]";
    }
    
}
