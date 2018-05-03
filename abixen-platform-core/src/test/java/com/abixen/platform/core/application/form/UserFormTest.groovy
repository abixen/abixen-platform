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

package com.abixen.platform.core.application.form

import com.abixen.platform.common.domain.model.enumtype.UserGender
import com.abixen.platform.common.domain.model.enumtype.UserLanguage
import com.abixen.platform.core.domain.model.User
import spock.lang.Specification

class UserFormTest extends Specification {

    void "should build UserForm from User entity"() {
        given:
        final User user = User.builder()
                .credentials("username", "password")
                .personalData("firstName", "middleName", "lastName")
                .additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE)
                .screenName("screenName")
                .registrationIp("127.0.0.1")
                .build()
        user.id = 1L

        when:
        final UserForm userForm = new UserForm(user)

        then:
        userForm.id == user.id
        userForm.birthday == user.birthday
        userForm.jobTitle == user.jobTitle
        userForm.gender == user.gender
        userForm.selectedLanguage == user.selectedLanguage
        userForm.screenName == user.screenName
        userForm.lastName == user.lastName
        userForm.middleName == user.middleName
        userForm.firstName == user.firstName
        userForm.username == user.username
    }

}