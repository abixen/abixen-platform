/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.common.security;


import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(as = PlatformWebUser.class)
public interface PlatformWebUser {

    Long getId();

    String getUsername();

    String getFirstname();

    String getLastname();

    boolean isAdmin();

    UserLanguage getSelectedLanguage();

    String getAvatarFileName();
}