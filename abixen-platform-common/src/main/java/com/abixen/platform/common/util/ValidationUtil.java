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

package com.abixen.platform.common.util;

import com.abixen.platform.common.dto.FormErrorDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


public class ValidationUtil {

    public static List<FormErrorDto> extractFormErrors(BindingResult result) {
        List<FormErrorDto> formErrors = new ArrayList<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            formErrors.add(new FormErrorDto(fieldError));
        }
        return formErrors;
    }
}
