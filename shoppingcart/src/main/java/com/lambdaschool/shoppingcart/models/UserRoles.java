package com.lambdaschool.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

//this creates our own join table
//does not have a primary key - only a composite primary key...we'll have to handle it manually
@Entity
@Table(name = "userroles")
@IdClass(UserRolesId.class)
public class UserRoles extends Auditable implements Serializable
{
    //2 fields will have primary key
    //no generation value is needed because we're not generating it ... we're getting it from another table
    //many roles to one user
    //Jackson needs help when it runs into a composite ID like we're creating here (implements Serializable -- this is for Jackson to
    //create JSON from composite id's like below
    //if you have serializable you must have two additional methods (equals and hashcode)
    //allowSetters is needed (didn't need it before even though it wouldn't have been wrong to have it) now we need it with auditing
    @Id
    @ManyToOne
    @JoinColumn(name = "userid") //userid is a field in user
    @JsonIgnoreProperties(value = "roles", allowSetters = true) //roles is a field in user
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "roleid")//roleid is a field in use
    @JsonIgnoreProperties(value = "users", allowSetters = true)//users is a field in roles
    private Role role;

    public UserRoles()
    {
    }

    public UserRoles(
        User user,
        Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)    //if they're the same object return true (this checks first)
        {
            return true;
        }
        if (!(o instanceof com.lambdaschool.shoppingcart.models.UserRoles))
        {
            return false;
        }
        com.lambdaschool.shoppingcart.models.UserRoles that = (com.lambdaschool.shoppingcart.models.UserRoles) o;

        return (this.user == null ? 0 : this.user.getUserid()) == (that.user == null ? 0 : that.user.getUserid()) &&
               (this.role ==null ? 0 : this.role.getRoleid()) == (that.role == null ? 0 : that.role.getRoleid());
    }

    @Override
    public int hashCode()
    {
        //return Objects.hash(user, role);
        return 37;
    }
}
