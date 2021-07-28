package demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import demo.util.MusicListSerializer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@JsonSerialize(using = MusicListSerializer.class)
public class music_list {

    @Id     //为主键标识(唯一属性)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int list_id;

    @Column(nullable = false,length = 100)
    private String list_name;

    @Column(nullable = false)
    private Date time;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch = FetchType.LAZY)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="u_phone")//设置在music表中的关联字段(外键)
    private user user;//所属作者


    @JsonIgnore
    @ManyToMany(mappedBy = "music_listList")
    @JsonIgnoreProperties(value = { "music_listList" })
    private Set<music> musicList;

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public demo.entity.user getUser() {
        return user;
    }

    public void setUser(demo.entity.user user) {
        this.user = user;
    }

    public Set<music> getMusicList() {
        return musicList;
    }

    public void setMusicList(Set<music> musicList) {
        this.musicList = musicList;
    }

}
