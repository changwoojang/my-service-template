package com.kakaopay.changwoo.domain.model.bank;

import com.kakaopay.changwoo.domain.code.BankEnum;
import lombok.*;

import javax.persistence.*;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BANK")
public class BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    /**
     * 은행 이름
     */
    String bankName;

    /**
     * 은행 코드
     */
    String bankCode;

    BankEnum bankEnum;
}
