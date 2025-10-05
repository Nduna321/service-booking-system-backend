package com.servicebookingsystem.repository;

import com.servicebookingsystem.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
  //  List<Ad> findAllByUserId(Long userId);

    List<Ad> findAllByServiceNameContaining(String name);

    @Query("SELECT a FROM Ad a JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Ad> findAllByUserId(@Param("userId") Long userId);

}
