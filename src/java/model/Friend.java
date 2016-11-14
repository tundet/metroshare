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
@XmlRootElement
@Table(name="friend")
@NamedQueries({
    @NamedQuery(name = "Friend.findAll", query = "SELECT f FROM Friend f")
    , @NamedQuery(name = "Friend.findById", query = "SELECT f FROM Friend f WHERE f.id = :id")
    , @NamedQuery(name = "Friend.findByOwnerId", query = "SELECT f FROM Friend f WHERE f.ownerId = :ownerId")
    , @NamedQuery(name = "Friend.findByFriendId", query = "SELECT f FROM Friend f WHERE f.friendId = :friendId")})
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    private int ownerId;
    @Basic(optional = false)
    @NotNull
    private int friendId;

    public Friend() {
    }

    public Friend(Integer id) {
        this.id = id;
    }

    public Friend(Integer id, int ownerId, int friendId) {
        this.id = id;
        this.ownerId = ownerId;
        this.friendId = friendId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
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
