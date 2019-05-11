package com.wishlist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "gift_picture")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class GiftPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    @NotBlank
    private String name;

    @Lob
    private Byte[] picture;
}
