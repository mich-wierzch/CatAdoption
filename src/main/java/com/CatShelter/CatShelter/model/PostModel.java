package com.CatShelter.CatShelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Lob
    private List<String> imageFile;
    private String description;
    @Embedded
    private Location location;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private UserModel user;



}
