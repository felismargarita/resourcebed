package com.felis.resource.mapper;

import com.felis.resource.entity.Picture;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureMapper {
    void insert(Picture picture);
    Picture selectByHash(String md5);
}
