package com.wishlist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name="list")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    @NotBlank
    private String name;

    @Column(length = 500)
    @NotBlank
    private String reason;

    @Column(length = 4000)
    @NotBlank
    private String description;

    private Instant date;

    @Column(length = 4000)
    @NotBlank
    private String invitation;

    @Column(length = 500)
    @NotBlank
    private String address;

    @Lob
    private Byte[] picture;
}
