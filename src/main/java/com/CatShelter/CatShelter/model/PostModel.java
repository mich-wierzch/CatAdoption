package com.CatShelter.CatShelter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name="Posts"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModel {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long postId;
    private String catName;
    private String catSex;
    private Integer catAge;
    private String catBreed;
    private String imageUrl;
    private String description;
    private String location;
    private LocalDate createdAt;
    private String userFirstName;
    private String userLastName;
    private String userMobilePhone;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private UserModel user;



}
