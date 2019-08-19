package com.kakaopay.changwoo.domain.code;

import java.util.Arrays;
import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */

public enum FileExtensionEnum {

    FILE_EXTENSION(Arrays.asList("text/csv"));

    private List<String> fileExtList;

    FileExtensionEnum(List<String> fileExt){
        this.fileExtList = fileExt;
    }

    public boolean hasFileExtensions(String fileExtention){
        return fileExtList.stream().anyMatch(content->content.equals(fileExtention));
    }

}
