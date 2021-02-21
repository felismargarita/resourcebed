package com.felis.resource;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws IOException{
        String base64 = Base64Encoder.encode(file.getBytes());
        byte[] hash = DigestUtils.md5Digest(base64.getBytes(StandardCharsets.UTF_8));
        String hashHex = DigestUtils.md5DigestAsHex(hash);
        if(pictureService.selectByHash(hashHex)!=null){
            return new Result(200,"success",hashHex);
        }
        Picture picture = new Picture();
        picture.setMd5(hashHex);
        picture.setBase64(base64);
        pictureService.insert(picture);
        return new Result(200,"success",hashHex);
    }

    @GetMapping("")
    public void getByHash(HttpServletResponse response, @RequestParam("md5") String md5) throws IOException{
        Picture picture = pictureService.selectByHash(md5);
        byte[] bytes = Base64Decoder.decode(picture.getBase64().getBytes(StandardCharsets.UTF_8));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        InputStream is=new ByteArrayInputStream(bytes);
        BufferedImage bi= ImageIO.read(is);
        ImageIO.write(bi, "png", response.getOutputStream());

    }

}
