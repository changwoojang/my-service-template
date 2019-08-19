package com.kakaopay.changwoo.web.bank;

import com.kakaopay.changwoo.web.bank.dto.result.FileUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import javax.validation.constraints.Size;

/**
 * Created by changwooj111@gmail.com on 2019-08-13
 */
@RestController
@RequestMapping(value = "/v1/file")
@Validated
@Slf4j
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FileUploadResult handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("handleFileUpload : {} - {} -{} - {}",file.getOriginalFilename(), file.getResource(),file.getContentType(), file);
        FileUploadResult fileUploadResult = fileUploadService.saveFileRecords(file);
        return fileUploadResult;
    }

}
