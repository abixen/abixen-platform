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

package com.abixen.platform.core.application.converter

import com.abixen.platform.common.domain.model.enumtype.RoleType
import com.abixen.platform.common.domain.model.enumtype.UserGender
import com.abixen.platform.common.domain.model.enumtype.UserLanguage
import com.abixen.platform.core.application.dto.RoleDto
import com.abixen.platform.core.application.dto.UserDto
import com.abixen.platform.core.domain.model.Role
import com.abixen.platform.core.domain.model.User
import spock.lang.Specification

class UserToUserDtoConverterTest extends Specification {

    private AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter
    private RoleToRoleDtoConverter roleToRoleDtoConverter
    private UserToUserDtoConverter userToUserDtoConverter

    void setup() {
        auditingModelToAuditingDtoConverter = Mock()
        roleToRoleDtoConverter = Mock()
        userToUserDtoConverter = new UserToUserDtoConverter(roleToRoleDtoConverter, auditingModelToAuditingDtoConverter)
    }


    void "should convert User entity to UserDto"() {
        given:
        final Role role = Role.builder()
                .name("name")
                .type(RoleType.ROLE_USER)
                .build()
        final Set<Role> roles = Collections.singleton(role)
        final RoleDto roleDto = new RoleDto()
        final Set<RoleDto> roleDtos = Collections.singleton(roleDto)

        final User user = User.builder()
                .credentials("username", "password")
                .personalData("firstName", "middleName", "lastName")
                .additionalData(new Date(), "jobTitle", UserLanguage.ENGLISH, UserGender.MALE)
                .screenName("screenName")
                .registrationIp("127.0.0.1")
                .roles(role)
                .build()
        user.id = 1L

        roleToRoleDtoConverter.convertToSet(roles) >> roleDtos

        when:
        final UserDto userDto = userToUserDtoConverter.convert(user)

        then:
        userDto != null
        userDto.id == user.id
        userDto.username == user.username
        userDto.password == user.password
        userDto.firstName == user.firstName
        userDto.lastName == user.lastName
        userDto.middleName == user.middleName
        userDto.screenName == user.screenName
        userDto.selectedLanguage == user.selectedLanguage
        userDto.gender == user.gender
        userDto.jobTitle == user.jobTitle
        userDto.birthday == user.birthday
        userDto.registrationIp == user.registrationIp
        userDto.roles == roleDtos

        1 * roleToRoleDtoConverter.convertToSet(roles) >> roleDtos
        1 * auditingModelToAuditingDtoConverter.convert(_, _)
        0 * _
    }

}