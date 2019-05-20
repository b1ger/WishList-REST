package com.wishlist.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
public class BaseResponse implements Serializable {

    public static final String OK_STATUS = "OK";
    public static final String ERROR_STATUS = "ERROR";

    private Object results;
    private String status;
    private Map<String, Object> additionalValues;

    public BaseResponse(Object results, String status) {
        this.results = results;
        this.status = status;
    }

    @JsonIgnore
    public void setResults(Object results, String status) {
        this.results = results;
        this.status = status;
    }

    @JsonIgnore
    public void addAdditionalValues(String key, String value) {
        if (this.additionalValues == null) {
            this.additionalValues = new HashMap<>();
        }
        this.additionalValues.put(key, value);
    }
}
