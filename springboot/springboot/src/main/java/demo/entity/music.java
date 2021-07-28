package demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import demo.util.MusicSerializer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@JsonSerialize(using = MusicSerializer.class)
public class music {

    @Id     //为主键标识(唯一属性)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int music_id;

    @Column(nullable = false,length = 100)
    private String name;        //如果变量名和数据库中对应的列名相同  即可不用加入@Column注解

    @Column(nullable = false,length = 100)
    private String url;

    @Column(nullable = false,length = 100)
    private String singer;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false,length = 1)
    private int status;    //是否公开(0为私密，1为公开，2为待审核)


    @JsonIgnore
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch = FetchType.LAZY)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="u_phone")//设置在music表中的关联字段(外键)
    private user user;//所属作者


    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "music_music_list",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "list_id"))
    //1、关系维护端，负责多对多关系的绑定和解除
    //2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(music)
    //3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(musicList)
    //4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为music_musicList
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即authority_id
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
    private Set<music_list> music_listList;

    public music(String name, String url, String singer, Date time, int status) {
        this.name = name;
        this.url = url;
        this.singer = singer;
        this.time = time;
        this.status = status;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public demo.entity.user getUser() {
        return user;
    }

    public void setUser(demo.entity.user user) {
        this.user = user;
    }

    public Set<music_list> getMusic_listList() {
        return music_listList;
    }

    public void setMusic_listList(Set<music_list> music_listList) {
        this.music_listList = music_listList;
    }

}
