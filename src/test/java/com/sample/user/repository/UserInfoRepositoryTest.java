package com.sample.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sample.user.entity.UserInfoEntity;

import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;

/*
 * Added as sample, to modify further
 */
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserInfoRepositoryTest {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@BeforeEach
    void initialize() {
		UserInfoEntity user = new UserInfoEntity();
	    userInfoRepository.save(user);
    }
	
	@AfterEach
    public void destroyAll(){
		userInfoRepository.deleteAll();
    }
	
	@Test
    void findAll_success() {
        List<UserInfoEntity> allCustomers = (List<UserInfoEntity>) userInfoRepository.findAll();
        assertThat(allCustomers.size()).isEqualTo(1);
    }
	

	@Test
	void save_success() {
		UserInfoEntity user = new UserInfoEntity();
	    UserInfoEntity saved = userInfoRepository.save(user);
	    List<UserInfoEntity> allCustomers = (List<UserInfoEntity>) userInfoRepository.findAll();
        assertThat(allCustomers.size()).isEqualTo(2);
	}
	
	@Test
	void delete_success() {
	    userInfoRepository.deleteById(1);
	    List<UserInfoEntity> allCustomers = (List<UserInfoEntity>) userInfoRepository.findAll();
        assertThat(allCustomers.size()).isEqualTo(0);
	}
}
