package com.springboot.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/*
该类处理文件上传与下载功能
 */
@RestController
@RequestMapping("/files")
public class FileController {

    //文件上传存储路径
    //代码意思为根目录的file文件夹，会自动创建
    private  static  final String filePath=System.getProperty("user.dir")+"/file/";

    /*
    文件上传
     */
//    @PostMapping("/upload")
//    public Result upload(MultipartFile file){
//        synchronized (FileController.class){
//            //获取时间戳
//            String flag=System.currentTimeMillis()+"";
//            //获取文件原始文件名(即上传文件本身的名字)
//            String filename=file.getOriginalFilename();
//            try{
//                if(!FileUtil.isDirectory(filePath)){
//                    //若没有file文件夹，则在项目根目录创建file文件夹
//                    FileUtil.mkdir(filePath);
//                }
//                //文件存储形式：时间戳-文件名
//                FileUtil.writeBytes(filename.getBytes(),filePath+flag+"-"+filename);
//                System.out.println(filename+"--上传成功");
//                Thread.sleep(1L);
//            }catch (Exception e){
//                System.err.println(filename+"--上传失败");
//            }
//            return Result.success(flag);
//        }
//
//    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String flag = System.currentTimeMillis() + "";
        String filename = file.getOriginalFilename();
        try {
            String fullFileName = flag + "-" + filename;  // 关键修改：保存完整文件名
            FileUtil.writeBytes(file.getBytes(), filePath + fullFileName);
            return Result.success(fullFileName);  // 返回完整标识
        } catch (Exception e) {
            return Result.error("上传失败");
        }
    }

    /*
    获取文件
     */
//    @GetMapping("/{flag}")
//    private  void avatarPath(@PathVariable String flag, HttpServletResponse response){
//        if(!FileUtil.isDirectory(filePath)){
//            FileUtil.mkdir(filePath);
//        }
//        OutputStream os;
//        List<String> fileNames=FileUtil.listFileNames(filePath);
//        String avatar=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
//        try{
//            if(StrUtil.isNotEmpty(avatar)){
//                response.addHeader("Content-Disposition","attachement;filename="+ URLEncoder.encode(avatar,"UTF-8"));
//                response.setContentType("application/octet-stream");
//                byte[] bytes=FileUtil.readBytes(filePath+avatar);
//                os=response.getOutputStream();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        }catch (Exception e){
//            System.out.println("文件下载失败");
//        }
//    }
    @GetMapping("/{flag}")
    public void download(@PathVariable String flag, HttpServletResponse response) {
        try {
            // 1. 根据flag查找文件
            List<String> fileNames = FileUtil.listFileNames(filePath);
            String fileName = fileNames.stream()
                    .filter(name -> name.contains(flag))
                    .findAny()
                    .orElse("");

            // 2. 设置响应头
            if (StrUtil.isNotEmpty(fileName)) {
                response.setContentType("image/jpeg"); // 根据实际类型调整
                response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 3. 写入文件流
                OutputStream os = response.getOutputStream();
                os.write(FileUtil.readBytes(filePath + fileName));
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

}
