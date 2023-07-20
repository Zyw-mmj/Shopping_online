package com.example.webstore.controller;


import com.example.webstore.domain.ResultInfo;
import com.example.webstore.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {



    @Autowired
    private IUserInfoService iUserInfoService;

    @PostMapping("/uploadImage")
    public ResultInfo uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("account") String account) throws IOException {
        // 保存图片到文件系统中
//        Path path = Paths.get("src/main/images/userImages", file.getOriginalFilename());
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        Path path = Paths.get("src/main/images/userImages",timeStamp+"_"+file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);

        // 手动删除临时文件夹中的临时文件   未完成
        file.getInputStream().close();
//        File tempFile = ((DiskFileItem) file).getStoreLocation();
//        if (tempFile != null) {
//            tempFile.delete();
//        }
//        Files.delete(file.get);
        // 将图片路径存储在数据库中
        String imagePath = path.toAbsolutePath().toString();
//        ImageEntity image = imageService.saveImagePath(imagePath);
//        Long res =image.getId();
        String accounts= account;
        Boolean s=this.iUserInfoService.updateTouxiang(accounts,imagePath);
        log.info(s.toString());
        if (s){
            return ResultInfo.success(1);
        }else {
            return  ResultInfo.error("上传失败");
        }
//
    }

}

