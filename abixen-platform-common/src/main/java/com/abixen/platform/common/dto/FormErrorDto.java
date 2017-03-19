/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.common.dto;

import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.validation.FieldError;

public class FormErrorDto {

    @JsonView(WebModelJsonSerialize.class)
    private String field;

    @JsonView(WebModelJsonSerialize.class)
    private String code;

    @JsonView(WebModelJsonSerialize.class)
    private String message;

    @JsonView(WebModelJsonSerialize.class)
    private Object rejectedValue;

    public FormErrorDto() {

    }

    public FormErrorDto(FieldError fieldError) {
        this.field = fieldError.getField();
        this.code = fieldError.getCode();
        this.message = fieldError.getDefaultMessage();
        this.rejectedValue = fieldError.getRejectedValue();
    }

    public FormErrorDto(String field, String code, String message, Object rejectedValue) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }


}
