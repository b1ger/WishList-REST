package com.wishlist.web.request.converter;

import com.wishlist.model.List;
import com.wishlist.web.request.NewListRequest;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ListConverter implements Converter<NewListRequest, List> {

    @Synchronized
    @Nullable
    @Override
    public List convert(NewListRequest source) {
        if (source == null) {
            return null;
        }
        final List list = new List();
        list.setName(source.getName());
        list.setReason(source.getReason());
        list.setDescription(source.getDescription());
        //list.setDate(source.getDate());
        list.setInvitation(source.getInvitation());
        list.setAddress(source.getAddress());
        return list;
    }
}
