package com.felis.resource;

import org.springframework.stereotype.Repository;

@Repository
public interface PictureMapper {
    void insert(Picture picture);
    Picture selectByHash(String md5);
}
