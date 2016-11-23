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

package com.abixen.platform.core.model.web;

import com.abixen.platform.core.model.enumtype.UserGender;
import com.abixen.platform.core.model.impl.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;


@JsonSerialize(as = UserWeb.class)
@JsonDeserialize(as = User.class)
public interface UserWeb {

    Long getId();

    String getUsername();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getJobTitle();

    Date getBirthday();

    UserGender getGender();

    String getAvatarFileName();

}
