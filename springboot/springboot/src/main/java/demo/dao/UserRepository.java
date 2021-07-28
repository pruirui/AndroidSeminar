package demo.dao;

import demo.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<user,String> {
    @Query(nativeQuery = true,value = "insert into user(phone,password,username,status) values (:phone,:password,:username,:status)")
    @Modifying
    @Transactional
    void save(@Param("phone") String  phone,@Param("password")String password,@Param("username")String username,@Param("status")int status);

    @Query(nativeQuery = true,value = "select phone from user where phone= :phone")
    String findByPhone(@Param("phone") String  phone);

    @Query(nativeQuery = true,value = "update user set password = :password,username = :username where phone = :phone")
    @Modifying
    @Transactional
    void alterUser(@Param("phone") String  phone,@Param("password") String  password,@Param("username") String username);

    @Query(nativeQuery = true,value = "select password from user where phone= :phone")
    String findpass(@Param("phone") String  phone);

    @Query(nativeQuery = true,value = "select phone,password,username,status,image from user")
    List<user> findAllUser();




    @Query(nativeQuery = true,value = "select username from user where username= :name")
    String findByName(@Param("name") String  name);


    @Query(nativeQuery = true,value = "select password from user where username= :name")
    String findpassbyname(@Param("name") String  name);

    @Query(nativeQuery = true,value = "SELECT userid FROM user WHERE phone= :phone OR username= :phone")
    String getid(@Param("phone") String phone);
}
