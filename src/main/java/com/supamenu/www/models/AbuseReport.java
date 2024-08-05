package com.supamenu.www.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "abuse_reports")
@Getter
@Setter
public class AbuseReport extends Base {
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
