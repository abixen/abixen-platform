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

package com.abixen.platform.core.form;

import com.abixen.platform.common.form.search.SearchField;
import com.abixen.platform.common.form.search.SearchForm;
import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearchForm implements SearchForm {

    @SearchField
    private String username;

    @SearchField
    private String firstName;

    @SearchField
    private String lastName;

    @SearchField(operator = SearchField.Operator.EQUALS)
    private UserLanguage selectedLanguage;

    @SearchField(operator = SearchField.Operator.EQUALS)
    private UserGender gender;

}