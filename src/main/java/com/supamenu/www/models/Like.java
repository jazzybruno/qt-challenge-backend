package com.supamenu.www.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "likes")
@Getter
@Setter
public class Like extends Base {

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
}
