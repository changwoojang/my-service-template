package com.kakaopay.changwoo.web.bank.dto.result;

import lombok.*;

import javax.persistence.Lob;

/**
 * Created by changwooj111@gmail.com on 2019-08-14
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FileUploadResult {

    private String fileType;

    private int fileRecordCount;

    private Long fileByteSize;

}
