package com.CatShelter.CatShelter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name="Comments"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommentModel {
    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long commentId;

    @ManyToOne
    @JoinColumn(
            name = "commenter_id"
    )
    private UserModel commenter;

    @ManyToOne
    @JoinColumn(
            name="user_id"
    )
    private UserModel user;

    @Column(
            name="text_content",
            nullable = false
    )
    private String text;
    @Column(
            nullable = false
    )
    private LocalDateTime timestamp;
}
