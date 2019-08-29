package com.wishlist.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
public class Error {

    private Map<String, String> errors;

    @JsonIgnore
    public void addError(String type, String arg) {
        if (this.errors == null) {
            errors = new HashMap<>();
        }
        errors.put(type, arg);
    }
}
