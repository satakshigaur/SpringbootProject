package com.sample.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.user.entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfoEntity,Integer>{

}
