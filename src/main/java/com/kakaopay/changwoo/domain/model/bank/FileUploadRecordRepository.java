package com.kakaopay.changwoo.domain.model.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Repository
public interface FileUploadRecordRepository extends JpaRepository<SupplyAmountEntity, Long> {

}
