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

package com.abixen.platform.common.model;

import com.abixen.platform.common.model.enumtype.UserGender;
import com.abixen.platform.common.model.enumtype.UserState;
import com.abixen.platform.common.model.enumtype.UserLanguage;

import java.util.Date;
import java.util.Set;


public interface SimpleUserBase<Role extends SimpleRoleBase> {

    Long getId();

    void setId(Long id);

    String getUsername();

    void setUsername(String username);

    String getScreenName();

    void setScreenName(String screenName);

    String getPassword();

    void setPassword(String password);

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    String getLastName();

    void setLastName(String lastName);

    String getJobTitle();

    void setJobTitle(String jobTitle);

    Date getBirthday();

    void setBirthday(Date birthday);

    UserGender getGender();

    void setGender(UserGender gender);

    UserLanguage getSelectedLanguage();

    void setSelectedLanguage(UserLanguage selectedLanguage);

    String getAvatarFileName();

    void setAvatarFileName(String avatarFileName);

    String getRegistrationIp();

    void setRegistrationIp(String registrationIp);

    UserState getState();

    void setState(UserState state);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    String getHashKey();

    void setHashKey(String hashKey);

}
