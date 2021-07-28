package demo.controller;


import demo.service.MusicService;
import demo.util.resType;
import demo.util.uploadfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Value("${musicFilePath}")
    private String musicFilePath;

    uploadfile up = new uploadfile();

    @PostMapping(value = "/addMusic")
    public resType addMusic(HttpServletRequest req, @RequestParam MultipartFile file,
                           @RequestParam(value = "name") String filename,
                           @RequestParam(value = "singer") String singer,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "u_phone") String u_phone){

        try{
            String name = file.getOriginalFilename();   //上传文件的源文件名
            String musicname = filename + "·" + singer + name.substring(name.lastIndexOf("."));  //上传时，文件必须带有后缀
            System.out.println(musicname);
            String res = up.uploadfile(file,musicname,req,musicFilePath);
            if(res.equals("false")){
                return new resType();
            }else {
                return new resType(musicService.addMusic(filename,singer,status,u_phone,res));
            }
        }catch (Exception e){
            return new resType();
        }
    }
    @GetMapping(value = "/findAllMusic")            //返回music类的时候 外键u_phone问题
    public resType findAllMusic(){
        try{
            return new resType(musicService.findAll(),"true");
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping("/deleteMusic")
    public resType deleteMusic(@RequestParam(value = "id") String id){
            return new resType(musicService.deleteById(id));
    }

    @PostMapping(value = "/alterMusic")
    public resType addMusic(@RequestParam(value = "id")String id,
            @RequestParam(value = "name") String filename,
            @RequestParam(value = "singer") String singer,
            @RequestParam(value = "status") String status){
            return new resType(musicService.alterMusic(id,filename,singer,status));
    }

    @PostMapping(value="/searchByName")         //歌曲名
    public resType searchByName(@RequestParam(value = "name") String name){
        try{
            return new resType(musicService.searchByName(name),"true");
        }catch (Exception e){
            return new resType();
        }
    }
    @PostMapping(value="/searchBySinger")         //歌手名
    public resType searchBySinger(@RequestParam(value = "singer") String singer){
        try{
            return new resType(musicService.searchBySinger(singer),"true");
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping(value = "/findSongByList")
    public resType findSongByList (@RequestParam(value = "list_id")String id){
        try{
            return new resType(musicService.findSongByList(id),"true");
        }catch (Exception e){
            return new resType();
        }
    }
    @PostMapping(value = "/addConnect ")
    public resType addConnect (@RequestParam(value = "list_id")String list_id,@RequestParam("music_id")String music_id){
        try{
            return new resType(musicService.addConnect(list_id, music_id));
        }catch (Exception e){
            return new resType();
        }
    }
    @PostMapping(value = "/deleteConnect ")
    public resType deleteConnect (@RequestParam(value = "list_id")String list_id,@RequestParam("music_id")String music_id){
        try{
            return new resType(musicService.deleteConnect(list_id, music_id));
        }catch (Exception e){
            return new resType();
        }
    }
}
