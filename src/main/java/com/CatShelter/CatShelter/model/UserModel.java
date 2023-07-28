package com.CatShelter.CatShelter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name="Users",
        uniqueConstraints = @UniqueConstraint(
                name="email_unique",
                columnNames = "email"
        )

)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel{
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long userId;

    @Column(
            name="username",
            nullable = false
    )
    private String username;


    @Column(
            name = "email",
            nullable = false
    )
    private String email;


    @ToString.Exclude
    @Column(
            name="password",
            nullable = false
    )
    private String password;


    @Column(
            name="first_name",
            nullable = false
    )
    private String firstName;


    @Column(
            name="last_name",
            nullable = false
    )
    private String lastName;
    @Column(
            name="mobile_number",
            nullable = false
    )
    private String mobile;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
