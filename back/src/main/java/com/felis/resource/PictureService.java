package com.felis.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    public void insert(Picture picture){
        pictureMapper.insert(picture);
    }

    public Picture selectByHash(String md5){
        return pictureMapper.selectByHash(md5);
    }
}
