package com.CatShelter.CatShelter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private String name;
    private String gender;
    private Integer age;
    private String breed;
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostImagesModel> images;
    private String description;
    @Embedded
    private PostLocation location;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private UserModel user;



}
