package com.supamenu.www.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comments")
@Getter
@Setter
public class Comment extends Base{

    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

}
