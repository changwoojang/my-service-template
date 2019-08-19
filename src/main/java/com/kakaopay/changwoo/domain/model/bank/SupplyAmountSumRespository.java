package com.kakaopay.changwoo.domain.model.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Repository
public interface SupplyAmountSumRespository extends JpaRepository<SupplyAmountSumEntity, String> {

    List<SupplyAmountSumEntity> findSupplyAmountEntitiesByYear(String year);

    List<SupplyAmountSumEntity> findByYearNot(String year);

}
