package demo.controller;


import demo.entity.user;
import demo.service.UserService;
import demo.util.resType;
import demo.util.uploadfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    uploadfile up = new uploadfile();

    @Value("${imageFilePath}")
    private String uploadImage;

    int status;

    //测试如果没有传递一个参数能否判空
    @PostMapping(value="/addUser")
    public resType addUser(@RequestParam(value = "phone") String phone,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "username") String username, HttpServletRequest request){
        try{
            if(request.getParameter("status")!=null){
                status = 1;
            }else {
                status = 0;
            }
            return new resType(userService.add(phone, password, username,status));
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping(value="/deleteUser")
    public resType deleteUser(@RequestParam(value = "phone") String phone){
            return new resType(userService.deleteUser(phone));
    }

    @PostMapping(value="/alterUser")
    public resType alterUser(@RequestParam(value = "phone") String phone,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "username") String username){
        try{
            return new resType(Arrays.asList(new user[]{userService.alterUser(phone, password, username)}),"true");
        }catch (Exception e){
            return new resType();
        }
    }

    @GetMapping(value="/findAllUser")           //查询有问题，怀疑跟外键的注入有关系
    public resType findAllUser(){
        try{
            return new resType(userService.findAll(),"true");
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping(value = "/login")
    public resType login(@RequestParam(value = "phone") String phone,
                        @RequestParam(value = "password") String password){
        try{
            user user = userService.login(phone, password);
            if(user.getPhone()==null){
                return new resType();
            }
            return new resType(Arrays.asList(new user[]{user}),"true");
        }catch (Exception e){
            return new resType();
        }
    }


    @PostMapping(value = "/changeImage")
    public resType changeImage(HttpServletRequest req,
                              @RequestParam MultipartFile file,
                              @RequestParam(value = "phone") String phone){

        try{
            //获取文件后缀名
            String name = file.getOriginalFilename();
            String filename = phone + name.substring(name.lastIndexOf("."));  //上传时，文件必须带有后缀
            String res = up.uploadfile(file,filename,req,uploadImage);
            if(res.equals("false")){
                return new resType();
            }else {
                return new resType(userService.changeImg(phone,res));
            }
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping(value = "/getUserInfo")
    public resType getUserInfo(@RequestParam("phone")String phone){
        try {
            return new resType(Arrays.asList(new user[]{userService.findByPhone(phone)}),"true");
        }catch (Exception e){
            return new resType();
        }
    }

}
