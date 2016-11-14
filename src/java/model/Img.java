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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@XmlRootElement
@Table(name="img")
@NamedQueries({
    @NamedQuery(name = "Img.findAll", query = "SELECT i FROM Img i")
    , @NamedQuery(name = "Img.findById", query = "SELECT i FROM Img i WHERE i.id = :id")
    , @NamedQuery(name = "Img.findByUserId", query = "SELECT i FROM Img i WHERE i.userId = :userId")
    , @NamedQuery(name = "Img.findByImageLocation", query = "SELECT i FROM Img i WHERE i.imageLocation = :imageLocation")
    , @NamedQuery(name = "Img.findByDate", query = "SELECT i FROM Img i WHERE i.date = :date")})
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String imageLocation;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Img() {
    }

    public Img(Integer id) {
        this.id = id;
    }

    public Img(Integer id, int userId, String imageLocation, Date date) {
        this.id = id;
        this.userId = userId;
        this.imageLocation = imageLocation;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        return "model.Img[ id=" + id + " ]";
    }
    
}
