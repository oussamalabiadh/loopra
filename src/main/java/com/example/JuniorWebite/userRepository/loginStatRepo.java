package com.example.JuniorWebite.userRepository;


import com.example.JuniorWebite.Dto.StatLoginDTO;
import com.example.JuniorWebite.Entity.LoginStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface loginStatRepo  extends JpaRepository<LoginStat,Long> {
    @Query("SELECT COUNT(e) as count , DATE_FORMAT(e.dateOfLogin, '%Y-%m') as date FROM LoginStat e GROUP BY DATE_FORMAT(e.dateOfLogin, '%Y-%m')")
    List<Object[]> getCountByMonth();


}
