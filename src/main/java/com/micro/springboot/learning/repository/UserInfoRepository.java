package com.micro.springboot.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.micro.springboot.learning.entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfoEntity,Integer>{

}
