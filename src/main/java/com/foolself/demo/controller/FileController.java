package com.foolself.demo.controller;

import com.foolself.demo.entity.User;
import com.foolself.demo.service.UserService;
import com.foolself.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author http://foolself.github.io
 * @date 2018/10/26 21:05
 */
@Controller
public class FileController {

    @Autowired
    private UserService userService;

    //获取主机端口
    private String port = "8080";
    //获取本机ip
    private String host;
    //图片存放根路径
    private String rootPath = "E:";
    //图片存放根目录下的子目录
    private String sonPath = "/img/";
    //获取图片链接
    private String imgPath;

    // TODO: 2018/10/30 上傳頭像，還沒有裁剪功能，沒有時間去弄了，有需要，后续可以参看插件 cropper。
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request,
                         Model model) {
        if (file.isEmpty() || !MyUtils.isPicture(file.getOriginalFilename())) {
            model.addAttribute("msg", "Please select a file to upload.");
            System.out.println("---> file.isEmpty()");
            return "/userInfo";
        }

        if (!MyUtils.isPicture(file.getOriginalFilename())){
            model.addAttribute("msg", "not a picture file");
            System.out.println("---> not a picture.");
            return "/userInfo";
        }

        String username = (String) request.getSession().getAttribute("username");
        if (username == null){
            model.addAttribute("msg", "session missed.");
            System.out.println("---> session missed.");
            return "/userInfo";
        }
//        try {
//            host = InetAddress.getLocalHost().getHostAddress();
//        } catch (Exception e) {
//            System.out.println("---> Exception: " + e.getMessage());
//        }

        try {
            byte[] bytes = file.getBytes();
            String unityFileName = MyUtils.getUnityFileName(file.getOriginalFilename());
            Path path = Paths.get("E:/img/" + unityFileName);
            Files.write(path, bytes);
            userService.updateAvatar(sonPath + unityFileName, username);
            User user = userService.findByUsername(username);
            System.out.println("---> FileController.upload(), user: " + user.toString());
            model.addAttribute("user", user);
        } catch (IOException e) {
            model.addAttribute("msg", e.getCause().getMessage());
        }
        model.addAttribute("msg", "upload avatar success.");
        return "/userInfo";
    }
}