package com.felis.resource.controller;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.felis.resource.entity.Picture;
import com.felis.resource.service.PictureService;
import com.felis.resource.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Value("${path.windows}")
    private String pathWindows;

    @Value("${path.linux}")
    private String pathLinux;

    private String getPath(){
        OsInfo info = SystemUtil.getOsInfo();
        if(info.isLinux()){
            return pathLinux;
        }
        if(info.isWindows()){
            return pathWindows;
        }
        throw new RuntimeException("操作系统路径不存在");
    }

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws IOException{
        byte[] hash = DigestUtils.md5Digest(file.getBytes());
        String hashHex = DigestUtils.md5DigestAsHex(hash);
        if(pictureService.selectByHash(hashHex)!=null){
            return new Result(200,"success",hashHex);
        }
        //写到文件系统中
        File fileOnFs = new File(getPath()+hashHex);
        file.transferTo(fileOnFs);

        //基本信息入库
        String[] fileNames = file.getOriginalFilename().split("\\.");
        Picture picture = new Picture();

        String suffix = fileNames[fileNames.length-1];
        picture.setSuffix(suffix);

        String filename = Arrays.stream(fileNames).limit(fileNames.length-1).collect(Collectors.joining("."));
        picture.setFilename(filename);
        picture.setMd5(hashHex);
        pictureService.insert(picture);
        return new Result(200,"success",hashHex);
    }

    @GetMapping("")
    public void getByHash(HttpServletResponse response, @RequestParam("md5") String md5) throws IOException{
        Picture picture = pictureService.selectByHash(md5);
        //读取文件
        File file = new File(getPath()+picture.getMd5());
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String contentType = "image/";
        contentType+=picture.getSuffix();
        response.setContentType(contentType);
        InputStream is=new FileInputStream(file);
        int len = 0;
        byte[] buffer = new byte[1024];
        // 输出流
        ServletOutputStream out = response.getOutputStream();
        while ((len = is.read(buffer)) > 0) // len的长度等于input.read(buffer)并且大于0的时候一直输出
        {
            out.write(buffer, 0, len);
        }
        is.close();
        out.close();
    }

}
