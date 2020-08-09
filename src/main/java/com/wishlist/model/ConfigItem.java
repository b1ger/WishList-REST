package com.wishlist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sys_config_item")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConfigItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String configKey;

    @Column
    @NotBlank
    private String configValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigItem that = (ConfigItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(configKey, that.configKey) &&
                Objects.equals(configValue, that.configValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configKey, configValue);
    }
}
