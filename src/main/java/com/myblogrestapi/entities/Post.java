package com.myblogrestapi.entities;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content",nullable = false)
    private String content;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "title",nullable = false)
    private String title;
    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();
}
