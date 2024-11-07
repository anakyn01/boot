package com.hyi.dev.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLogRepository extends JpaRepository<BookLog, Integer>{

}
//당장 필요한 리포지토리 기능은 JPA리포지토리만 상속 받습니다
