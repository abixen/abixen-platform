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

package com.abixen.platform.core.dto;


import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.common.model.enumtype.UserState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UserDto extends AuditingDto {

    private Long id;
    private String username;
    private String password;
    private String screenName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String jobTitle;
    private Date birthday;
    private UserGender gender;
    private UserLanguage selectedLanguage;
    private String avatarFileName;
    private String registrationIp;
    private UserState state;
    private Set<RoleDto> roles;
}