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

package com.abixen.platform.core.model;


public interface UserBase<Role extends SimpleRoleBase> extends SimpleUserBase<Role> {

    int USERNAME_MAX_LENGTH = 32;

    int USERNAME_MIN_LENGTH = 3;

    int PASSWORD_MAX_LENGTH = 60;

    int FIRST_NAME_MAX_LENGTH = 64;
    int FIRST_NAME_MIN_LENGTH = 2;

    int LAST_NAME_MAX_LENGTH = 64;
    int LAST_NAME_MIN_LENGTH = 2;

    int SCREEN_NAME_MIN_LENGTH = 3;
    int SCREEN_NAME_MAX_LENGTH = 32;

    int MIDDLE_NAME_MIN_LENGTH = 2;
    int MIDDLE_NAME_MAX_LENGTH = 64;
    /**
     * IPv4-mapped IPv6 (45 bytes)
     */
    int REGISTRATION_IP_MAX_LENGTH = 45;

    int HASH_KEY_MAX_LENGTH = 60;

}
