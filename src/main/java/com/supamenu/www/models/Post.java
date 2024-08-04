package com.supamenu.www.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "posts")
@Getter
@Setter
public class Post extends Base {
    @Column(name = "title" , nullable = false)
    private String title;
    @Column(name = "content" , nullable = false)
    private String content;
    private int numberOfComments;
    private int numberOfLikes;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
