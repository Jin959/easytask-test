package com.easytask.easytask.src.user.repository;

import com.easytask.easytask.src.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    List<User> findUserOnMatchingMail(Map<String, Object> param);
}
