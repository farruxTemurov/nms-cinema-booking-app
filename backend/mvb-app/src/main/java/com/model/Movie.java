package com.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private String genre;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "theater_id")  // This defines the foreign key column in the movies table
    private Theater theater;
}
