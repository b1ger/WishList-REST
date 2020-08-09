package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "gift")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    @NotBlank
    private String name;

    @Column(length = 500)
    private String link;

    @Column(length = 5000)
    @NotBlank
    private String description;

    private Byte[] picture;

    @OneToMany(mappedBy = "gift")
    private Set<GiftPicture> giftPictureList;

    @ManyToOne
    @JoinColumn(name = "list_id")
    @JsonIgnore
    private List list;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private boolean booked;

    @JsonIgnore
    public boolean isBooked() {
        return booked;
    }
}
