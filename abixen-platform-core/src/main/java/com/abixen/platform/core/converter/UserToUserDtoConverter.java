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

package com.abixen.platform.core.converter;


import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.core.dto.UserDto;
import com.abixen.platform.core.model.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UserToUserDtoConverter extends AbstractConverter<User, UserDto> {

    private final RoleToRoleDtoConverter roleToRoleDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public UserToUserDtoConverter(RoleToRoleDtoConverter roleToRoleDtoConverter,
                                  AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public UserDto convert(User user, Map<String, Object> parameters) {
        UserDto userDto = new UserDto();


        userDto
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setScreenName(user.getScreenName())
                .setFirstName(user.getFirstName())
                .setMiddleName(user.getMiddleName())
                .setLastName(user.getLastName())
                .setJobTitle(user.getJobTitle())
                .setBirthday(user.getBirthday())
                .setGender(user.getGender())
                .setSelectedLanguage(user.getSelectedLanguage())
                .setAvatarFileName(user.getAvatarFileName())
                .setRegistrationIp(user.getRegistrationIp())
                .setState(user.getState())
                .setRoles(roleToRoleDtoConverter.convertToSet(user.getRoles()));

        auditingModelToAuditingDtoConverter.convert(user, userDto);

        return userDto;
    }
}