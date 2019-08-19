package com.kakaopay.changwoo.domain.model.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Repository
public interface BankRepository extends JpaRepository<BankEntity, String> {
    BankEntity findByBankCode(String bankCode);
}
