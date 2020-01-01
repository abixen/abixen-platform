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

package com.abixen.platform.common.application.representation;

import com.abixen.platform.common.application.form.Form;

import java.util.ArrayList;
import java.util.List;


public class FormValidationResultRepresentation<T extends Form> {

    private T form;

    private List<FormErrorRepresentation> formErrors;

    public FormValidationResultRepresentation(T form, List<FormErrorRepresentation> formErrors) {
        this.form = form;
        this.formErrors = formErrors;
    }

    public FormValidationResultRepresentation(T form) {
        this.form = form;
        this.formErrors = new ArrayList<>();
    }

    public FormValidationResultRepresentation() {
    }

    public T getForm() {
        return form;
    }

    public void setForm(T form) {
        this.form = form;
    }

    public List<FormErrorRepresentation> getFormErrors() {
        return formErrors;
    }

    public void setFormErrors(List<FormErrorRepresentation> formErrors) {
        this.formErrors = formErrors;
    }

}