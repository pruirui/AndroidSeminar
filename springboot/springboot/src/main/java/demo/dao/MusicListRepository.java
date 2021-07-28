package demo.dao;

import demo.entity.music_list;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MusicListRepository extends JpaRepository<music_list,Object> {
    @Query(nativeQuery = true,value = "select * from music_list where u_phone = :phone and list_name = :list_name")
    music_list findByPhoneAndName(@Param("phone") String phone, @Param("list_name") String list_name);

    @Query(nativeQuery = true,value = "select * from music_list where u_phone = :phone")
    List<music_list> findAllByPhone(String phone);


}