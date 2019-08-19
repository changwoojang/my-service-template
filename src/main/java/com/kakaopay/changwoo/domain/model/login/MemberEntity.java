package com.kakaopay.changwoo.domain.model.login;

/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */

import lombok.*;
import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    /**
     * MEMBER ID
     */
    @Column(name = "MEMEBER_ID", nullable = false, unique = true)
    String memberId;

    /**
     * PASSWORD
     */
    @Column(name = "PASSWORD", nullable = false)
    String password;
}
