package com.exam.examserver.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceFoundException(String resourceName,String fieldName,String fieldValue){
        super(String.format("%s With %s : %s Already Exists",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
