package com.CatShelter.CatShelter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name="Cats"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatModel {
    @Id
    @SequenceGenerator(
            name = "cat_sequence",
            sequenceName = "cat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cat_sequence"
    )
    private Long catId;

    @Column(
            name = "cat_name",
            nullable = false
    )
    private String name;

    @Column(
            name = "cat_sex",
            nullable = false
    )
    private String sex;

    @Column(
            name= "cat_age",
            nullable = false
    )
    private Integer age;

    @Column(
            name = "cat_breed",
            nullable = false
    )
    private String breed;

    @Column(
            name="image_url",
            nullable = false
    )
    private String imageUrl;


    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private UserModel userModel;

}
