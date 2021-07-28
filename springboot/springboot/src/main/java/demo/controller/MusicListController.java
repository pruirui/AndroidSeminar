package demo.controller;

import demo.service.MusicListService;
import demo.util.resType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/list")
public class MusicListController {

    @Autowired
    private MusicListService musicListService;

    @PostMapping(value = "addList")
    public resType addList(@RequestParam(value = "list_name")String name,
                           @RequestParam(value = "u_phone")String u_phone){

        return new resType(musicListService.addList(name,u_phone));
    }

    @GetMapping(value = "/findAll")
    public resType findAllList(){
        try{
            return new resType(musicListService.findAll(),"true");
        }catch (Exception e){
            return new resType();
        }
    }

    @PostMapping(value = "/findAllByPhone")
    public resType findAllByPhone(@RequestParam(value = "u_phone")String u_phone){
        try{
            return new resType(musicListService.findAllByPhone(u_phone),"true");
        }catch (Exception e){
            return new resType();
        }
    }

}
