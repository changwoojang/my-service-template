package com.kakaopay.changwoo.domain.model.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Repository
public interface SupplyAmountRespository extends JpaRepository<SupplyAmountEntity, String> {

    @Query("SELECT DISTINCT a.year FROM SupplyAmountEntity a")
    List<String> findDistinctYear();

    List<SupplyAmountEntity> findSupplyAmountEntitiesByYear(String year);


}
