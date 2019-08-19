package com.kakaopay.changwoo.domain.model.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    MemberEntity findByMemberId(String memberId);
}
