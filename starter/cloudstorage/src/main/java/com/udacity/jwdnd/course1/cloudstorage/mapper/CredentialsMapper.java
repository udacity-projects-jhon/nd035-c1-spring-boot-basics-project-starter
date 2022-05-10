package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int create(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialid}")
    Credential getCredential(int userid, int credentialid);

    @Update("UPDATE CREDENTIALS " +
            "SET url='${url}', username='${username}', key='${key}', password='${password}' " +
            "WHERE userid=#{userid} AND credentialid=#{credentialid}")
    void updateCredential(Credential cred);


    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialid}")
    void delete(int userid, int credentialid);

}
