package demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class user {

    //@GeneratedValue(strategy = GenerationType.AUTO)   非String类型主键，即 long、short、int
    //特例 用 String 类型做主键
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length=11) //对应数据库中的列名
    private String phone;

    @Column(nullable = false,length = 100)
    private String username;        //如果变量名和数据库中对应的列名相同  即可不用加入@Column注解

    @Column(nullable = false,length = 20)
    private String password;

    @Column(nullable = false,length = 1)
    private int status;    //用户身份(0为用户,1为总管理员,-1为注销的用户)

    @Column(length = 100)
    private String image;


    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)

    //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="user"中的user是music中的user属性
    private Set<music> musicList;//音乐列表
    public void addUser(music music) {
        musicList.add(music);
        music.setUser(this);
    }

    public void removeUser(music music) {
        musicList.remove(music);
        music.setUser(null);
    }


    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
    private Set<music_list> music_listList;//歌单列表

    public void addUser(music_list music_list) {
        music_listList.add(music_list);
        music_list.setUser(this);
    }

    public void removeUser(music_list music_list) {
        music_listList.remove(music_list);
        music_list.setUser(null);
    }




    public user(String phone, String password,String username) {
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.image = "";    //默认图片链接
        this.status = 0;
    }

    //注册管理员用户

    public user(String phone, String password,String username,int status) {
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.image = "";    //默认图片链接
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<music> getMusicList() {
        return musicList;
    }

    public void setMusicList(Set<music> musicList) {
        this.musicList = musicList;
    }

    public Set<music_list> getMusic_listList() {
        return music_listList;
    }

    public void setMusic_listList(Set<music_list> music_listList) {
        this.music_listList = music_listList;
    }
}
