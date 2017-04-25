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

import com.abixen.platform.common.form.Form;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;


public class FormValidationResultDto {

    @JsonView(WebModelJsonSerialize.class)
    private Form form;

    @JsonView(WebModelJsonSerialize.class)
    private List<FormErrorDto> formErrors;

    public FormValidationResultDto(Form form, List<FormErrorDto> formErrors) {
        this.form = form;
        this.formErrors = formErrors;
    }

    public FormValidationResultDto(Form form) {
        this.form = form;
        this.formErrors = new ArrayList<>();
    }

    public FormValidationResultDto() {

    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<FormErrorDto> getFormErrors() {
        return formErrors;
    }

    public void setFormErrora(List<FormErrorDto> formErrors) {
        this.formErrors = formErrors;
    }


}
