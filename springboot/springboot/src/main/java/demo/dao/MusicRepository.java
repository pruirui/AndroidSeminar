package demo.dao;

import demo.entity.music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MusicRepository extends JpaRepository<music,Object> {

    @Query(nativeQuery = true,value = "select music_id from music where music_id= :id")
    String findById(@Param("id") int id);

    @Query(nativeQuery = true,value = "insert into music(music.name,music.url,music.singer,music.time,music.status,music.u_phone) values (:name,:url,:singer,:time,:status,:u_phone)")
    @Modifying
    @Transactional
    void addMusic(@Param("name")String name,@Param("url")String url,@Param("singer")String singer,@Param("time")String time,@Param("status")String status,@Param("u_phone")String u_phone);


    @Query(nativeQuery = true,value = "select * from music where name like %:name%")
    List<music> findByNameLike(@Param("name") String name);

    @Query(nativeQuery = true,value = "select * from music where singer like %:singer%")
    List<music> findBySingerLike(@Param("singer") String singer);



}