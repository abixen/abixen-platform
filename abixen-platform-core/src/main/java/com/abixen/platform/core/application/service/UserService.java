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

package com.abixen.platform.core.application.service;

import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserForm;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {

    String generatePassword();

    User create(UserForm userForm, String userPassword);

    UserForm update(UserForm userForm);

    void delete(Long id);

    Page<User> findAll(Pageable pageable, UserSearchForm userSearchForm);

    User find(Long id);

    User updateRoles(UserRolesForm userRolesForm);

    User find(String username);

    void activate(String userHashKey);

    UserChangePasswordForm changePassword(User user, UserChangePasswordForm userChangePasswordForm);

    User changeAvatar(Long userId, MultipartFile avatarFile) throws IOException;

    UserLanguage updateSelectedLanguage(Long userId, UserLanguage selectedLanguage);
}