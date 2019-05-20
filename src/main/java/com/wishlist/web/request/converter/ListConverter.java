package com.wishlist.web.request.converter;

import com.wishlist.model.List;
import com.wishlist.util.DateUtils;
import com.wishlist.web.request.ListRequest;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ListConverter implements Converter<ListRequest, List> {

    private DateUtils dateUtils;

    @Autowired
    public ListConverter(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Synchronized
    @Nullable
    @Override
    public List convert(ListRequest source) {
        if (source == null) {
            return null;
        }
        final List list = new List();
        if (source.getId() != null) {
            list.setId(source.getId());
        }
        list.setName(source.getName());
        list.setReason(source.getReason());
        list.setDescription(source.getDescription());
        if (source.getDate() != null) {
            if (source.getTime() != null) {
                list.setDate(dateUtils.parseFromBrowser(source.getDate(), source.getTime()));
            } else {
                list.setDate(dateUtils.parseFromBrowser(source.getDate()));
            }
        }
        list.setInvitation(source.getInvitation());
        list.setAddress(source.getAddress());
        return list;
    }
}
