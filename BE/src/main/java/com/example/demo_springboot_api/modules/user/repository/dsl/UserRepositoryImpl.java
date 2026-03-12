package com.example.demo_springboot_api.modules.user.repository.dsl;

import com.example.demo_springboot_api.modules.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements IUserDSLRepository {
  @Autowired private JPAQueryFactory queryFactory;

  public Boolean checkCodeAvailable(String code) {
    QUser user = QUser.user;

    return queryFactory.selectOne().from(user).where(user.code.eq(code)).fetchFirst() == null;
  }

  public Boolean checkEmailAvailable(String email) {
    QUser user = QUser.user;

    return queryFactory.selectOne().from(user).where(user.email.eq(email)).fetchFirst() == null;
  }
}
