package com.sample.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sample.user.entity.UserInfoEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserInfoRepositoryTest {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Test
    void findAll_success() {
        List<UserInfoEntity> allCustomers = (List<UserInfoEntity>) userInfoRepository.findAll();
        assertThat(allCustomers.size()).isGreaterThanOrEqualTo(0);
    }
	
}
