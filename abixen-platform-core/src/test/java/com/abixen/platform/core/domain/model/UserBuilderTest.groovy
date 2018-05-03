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
package com.abixen.platform.core.domain.model

import com.abixen.platform.common.domain.model.enumtype.RoleType
import com.abixen.platform.common.domain.model.enumtype.UserGender
import com.abixen.platform.common.domain.model.enumtype.UserLanguage
import com.abixen.platform.common.domain.model.enumtype.UserState
import spock.lang.Specification

class UserBuilderTest extends Specification {

    void "should build User entity"() {
        given:
        final String username = "username"
        final String password = "password"
        final String firstName = "firstName"
        final String middleName = "middleName"
        final String lastName = "lastName"
        final String registrationIp = "127.0.0.1"
        final String screenName = "screenName"
        final String jobTitle = "jobTitle"
        final UserLanguage language = UserLanguage.ENGLISH
        final UserGender gender = UserGender.MALE
        final Date birthday = new Date()
        final Role role = Role.builder()
                .name("name")
                .type(RoleType.ROLE_USER)
                .build();

        when:
        final User user = User.builder()
                .credentials(username, password)
                .personalData(firstName, middleName, lastName)
                .additionalData(birthday, jobTitle, language, gender)
                .registrationIp(registrationIp)
                .screenName(screenName)
                .roles(role)
                .build()

        then:
        user.username == username
        user.firstName == firstName
        user.middleName == middleName
        user.lastName == lastName
        user.birthday == birthday
        user.jobTitle == jobTitle
        user.selectedLanguage == language
        user.gender == gender
        user.screenName == screenName
        user.registrationIp == registrationIp
        user.roles.size() == 1
        user.state == UserState.CREATED
        user.avatarFileName == null
    }

}