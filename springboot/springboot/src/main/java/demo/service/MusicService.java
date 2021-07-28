package demo.service;

import demo.dao.MusicListRepository;
import demo.dao.MusicRepository;
import demo.dao.UserRepository;
import demo.entity.music;
import demo.entity.music_list;
import demo.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.*;

@Service
public class MusicService {

    @Value("${filePath}")
    String filepath;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicListRepository musicListRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isExit (String id){
        return musicRepository.findById(Integer.parseInt(id))!=null;
    }

    public String addMusic(String name,String singer,String status,String u_phone,String url){

            music music = new music(name,url,singer,new Date(),Integer.parseInt(status));       //初始化新的music
            music_list music_list = musicListRepository.findByPhoneAndName(u_phone,"我的上传");

            music.setUser(userRepository.getById(u_phone));                                 //设置外键user.u_phone属性


            Set<music_list> setList = new HashSet<>();
            setList.add(music_list);
            music.setMusic_listList(setList);                          //添加到关系表


            Set<music> setMusic = new HashSet<>();
            setMusic.add(music);
            music_list.setMusicList(setMusic);

            musicRepository.save(music);
            musicListRepository.save(music_list);

            return "true";

    }

    public List<music> findAll(){
        return musicRepository.findAll();
    }

    public String deleteById(String id){
        try{
            music music = musicRepository.getById(Integer.parseInt(id));
            if(isExit(id)){
                String url = music.getUrl();
                String FileName = filepath + url.substring(url.lastIndexOf("/upload"));      //切割到端口号后面

                File file = new File(FileName);// 根据指定的文件名创建File对象
                if (file.exists()) { // 要删除的文件不存在
                    if(file.delete()){
                        musicRepository.deleteById(Integer.parseInt(id));
                        return "true";
                    }
                }
            }
            return "false";
        }catch (Exception e){
            return "false";
        }
    }

    public String alterMusic(String id,String filename, String singer, String status){
        try{
            music music = musicRepository.getById(Integer.parseInt(id));
            music.setName(filename);
            music.setSinger(singer);
            music.setStatus(Integer.parseInt(status));
            musicRepository.save(music);
            return "true";
        }catch (Exception e){
            return "false";
        }
    }

    public List<music> searchByName(String name){
        return musicRepository.findByNameLike(name);
    }
    public List<music> searchBySinger(String singer){
        return musicRepository.findBySingerLike(singer);
    }

    public List<music> findSongByList(String id){
        music_list music_list = musicListRepository.getById(Integer.parseInt(id));
        return new ArrayList(music_list.getMusicList());
    }

    public String addConnect(String list_id,String music_id){

        music music = musicRepository.getById(music_id);
        music_list music_list = musicListRepository.getById(list_id);

        music.getMusic_listList().add(music_list);
        musicRepository.save(music);

        music_list.getMusicList().add(music);
        musicListRepository.save(music_list);

        return "true";
    }
    public String deleteConnect(String list_id,String music_id){

        music music = musicRepository.getById(music_id);
        music_list music_list = musicListRepository.getById(list_id);

        music.getMusic_listList().remove(music_list);
        musicRepository.save(music);

        music_list.getMusicList().remove(music);
        musicListRepository.save(music_list);

        return "true";
    }


}
