package com.wishlist.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="list")
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "user")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

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
    private String invitation;

    @Column(length = 500)
    private String address;

    @Lob
    private Byte[] picture;

    @OneToMany(mappedBy = "list")
    private Set<Gift> gifts;

    public void addGift(Gift gift) {
        gifts.add(gift);
    }
}
