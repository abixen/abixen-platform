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

package com.abixen.platform.core.util;

import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;

import java.util.Date;


public interface UserBuilder {

    User build();

    UserBuilder credentials(String username, String password);

    UserBuilder screenName(String screenName);

    UserBuilder personalData(String firstName, String middleName, String lastName);

    UserBuilder additionalData(Date birthday, String jobTitle, UserLanguage language, UserGender gender);

    UserBuilder roles(Role... roles);

    UserBuilder registrationIp(String registrationIp);

}
