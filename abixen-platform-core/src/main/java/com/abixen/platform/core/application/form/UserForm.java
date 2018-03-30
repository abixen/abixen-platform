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

package com.abixen.platform.core.application.form;

import com.abixen.platform.common.application.form.Form;
import com.abixen.platform.common.domain.model.enumtype.UserGender;
import com.abixen.platform.common.domain.model.enumtype.UserLanguage;
import com.abixen.platform.core.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserForm implements Form {

    private Long id;

    @NotNull
    @Email
    @Length(min = User.USERNAME_MIN_LENGTH, max = User.USERNAME_MAX_LENGTH)
    private String username;

    @NotNull
    @Length(min = User.SCREEN_NAME_MIN_LENGTH, max = User.SCREEN_NAME_MAX_LENGTH)
    private String screenName;

    @NotNull
    @Length(min = User.FIRST_NAME_MIN_LENGTH, max = User.FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @Length(min = User.MIDDLE_NAME_MIN_LENGTH, max = User.MIDDLE_NAME_MAX_LENGTH)
    private String middleName;

    @NotNull
    @Length(min = User.LAST_NAME_MIN_LENGTH, max = User.LAST_NAME_MAX_LENGTH)
    private String lastName;

    private String jobTitle;

    @NotNull
    private UserLanguage selectedLanguage;

    private Date birthday;

    private UserGender gender;

    public UserForm() {

    }

    public UserForm(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.screenName = user.getScreenName();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.jobTitle = user.getJobTitle();
        this.selectedLanguage = user.getSelectedLanguage();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
    }

}