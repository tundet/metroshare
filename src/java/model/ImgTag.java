/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@Table(name = "img_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImgTag.findAll", query = "SELECT i FROM ImgTag i")
    , @NamedQuery(name = "ImgTag.findById", query = "SELECT i FROM ImgTag i WHERE i.id = :id")
    , @NamedQuery(name = "ImgTag.findByImgId", query = "SELECT i FROM ImgTag i WHERE i.imgId = :imgId")
    , @NamedQuery(name = "ImgTag.findByTagId", query = "SELECT i FROM ImgTag i WHERE i.tagId = :tagId")})
public class ImgTag implements Serializable {

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
    private int tagId;

    public ImgTag() {
    }

    public ImgTag(Integer id) {
        this.id = id;
    }

    public ImgTag(Integer id, int imgId, int tagId) {
        this.id = id;
        this.imgId = imgId;
        this.tagId = tagId;
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

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
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
        if (!(object instanceof ImgTag)) {
            return false;
        }
        ImgTag other = (ImgTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ImgTag[ id=" + id + " ]";
    }
    
}
