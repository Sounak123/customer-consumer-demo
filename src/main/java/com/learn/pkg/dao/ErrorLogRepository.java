package com.learn.pkg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.pkg.entity.ErrorLog;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {}
