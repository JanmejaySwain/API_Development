package com.myblogrestapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String name;
    private String username;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL) //we use eager bcz when user table is loaded then automatically role should be ready for accessibility
   //Cascade defines that when we change some things in one table then changes will apply to another table
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
    )
    //here we are joining two table by creating one intermediate table user_roles,1st join column is first table to join
    //name is the column name of first table in new table reference column is that col of first table to  join
    //inverse column is second table
    private Set<Role> roles; //we use set bcz in list it can be duplicate but in set it is always unique...
                             // if we want to define as many then we will use set
}
