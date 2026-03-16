package com.example.demo_springboot_api.modules.user.repository.dsl;

import com.example.demo_springboot_api.modules.user.entity.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface IUserDSLRepository extends Repository<User, Long> {
  public Boolean checkCodeAvailable(String code);

  public Boolean checkEmailAvailable(String email);
}
