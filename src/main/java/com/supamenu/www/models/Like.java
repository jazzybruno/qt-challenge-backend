package com.supamenu.www.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "likes")
@Getter
@Setter
public class Like extends Base{

    @ManyToOne()
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

}
