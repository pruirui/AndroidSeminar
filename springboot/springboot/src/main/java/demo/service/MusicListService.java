package demo.service;

import demo.dao.MusicListRepository;
import demo.dao.UserRepository;
import demo.entity.music_list;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MusicListService {


    @Autowired
    MusicListRepository musicListRepository;

    @Autowired
    UserRepository userRepository;

    public List<music_list> findAllByPhone(String phone){
        return musicListRepository.findAllByPhone(phone);
    }

    public String addList(String name,String phone){
        try {
            music_list music_list = new music_list();
            music_list.setList_name(name);
            music_list.setTime(new Date());
            music_list.setUser(userRepository.getById(phone));
            musicListRepository.save(music_list);

            return "true";
        }catch (Exception e){
            return "false";
        }
    }

    public List<music_list> findAll(){
        return musicListRepository.findAll();
    }

}
