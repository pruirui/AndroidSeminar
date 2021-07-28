package demo.service;

import demo.dao.UserRepository;
import demo.entity.user;
import demo.util.format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class UserService {
    @Value("${filePath}")
    String filepath;

    @Autowired
    private UserRepository userRepository;

    public format format = new format();

    public boolean isExit (String phone){
        return userRepository.findByPhone(phone)!=null;
    }
    //是否存在该用户名
    public boolean isExitName (String name){
        return userRepository.findByName(name)!=null;
    }

    public user findByPhone(String phone){
        return userRepository.getById(phone);
    }

    //注册新账户     判断是否已经注册过&手机号和密码是否符合规则
    public String add(String phone,String password,String name,int status) {

        if(isExit(phone)){
            return "false";
        }else if(!format.isRightPhone(phone)){
            return "false";
        }else{
            userRepository.save(phone,password,name,status);
//            userRepository.save(new user(phone,password,name,status));    //原生save方法会导致phone数据割裂
            return "true";
        }
    }

    //删除旧账户        是否存在该手机号
    public String deleteUser(String phone) {
        try{
            user user = userRepository.getById(phone);
            if(isExit(phone)){
                String url = user.getImage();
                String FileName = filepath + url.substring(url.lastIndexOf("/upload"));      //切割到端口号后面

                File file = new File(FileName);// 根据指定的文件名创建File对象
                if (file.exists()) { // 要删除的文件不存在
                    if(file.delete()){
                        userRepository.deleteById(phone);
                    }
                }
                return "true";
            }
            return "false";
        }catch (Exception e){
            return "false";
        }
    }

    //更改用户信息       是否存在该手机号&手机号和密码是否符合规范
    public user alterUser(String phone,String password,String name) {
        if(isExit(phone)){
            userRepository.alterUser(phone,password,name);
            return userRepository.getById(phone);
        }
        return new user();
    }

    public List<user> findAll(){
        return userRepository.findAllUser();
    }

    public user login(String phone,String password){
        if(userRepository.findpass(phone).equals(password)){
            return userRepository.getById(phone);
        }
        return new user();
    }

    public String changeImg(String phone,String url){
            user user = userRepository.getById(phone);
            user.setImage(url);
            userRepository.save(user);
            return "true";
    }

}
