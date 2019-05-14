package com.wishlist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
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
    @NotBlank
    private String invitation;

    @Column(length = 500)
    @NotBlank
    private String address;

    @Lob
    private Byte[] picture;

    @OneToMany(mappedBy = "list")
    private Set<Gift> gifts;

    public void addGift(Gift gift) {
        gifts.add(gift);
    }
}
