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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mafields
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friend.findAll", query = "SELECT f FROM Friend f")
    , @NamedQuery(name = "Friend.findById", query = "SELECT f FROM Friend f WHERE f.id = :id")})
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @JoinColumn(name = "friendId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User friendId;
    @JoinColumn(name = "ownerId", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User ownerId;

    public Friend() {
    }

    public Friend(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getFriendId() {
        return friendId;
    }

    public void setFriendId(User friendId) {
        this.friendId = friendId;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
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
        if (!(object instanceof Friend)) {
            return false;
        }
        Friend other = (Friend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Friend[ id=" + id + " ]";
    }
    
}
