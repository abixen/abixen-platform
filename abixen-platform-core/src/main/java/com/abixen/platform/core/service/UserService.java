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

package com.abixen.platform.core.service;

import com.abixen.platform.core.form.UserChangePasswordForm;
import com.abixen.platform.core.form.UserForm;
import com.abixen.platform.core.form.UserRolesForm;
import com.abixen.platform.core.form.UserSearchForm;
import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.model.impl.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {

    String generateUserPassword();

    User buildUser(UserForm userForm, String userPassword);

    User createUser(User user);

    UserForm updateUser(UserForm userForm);

    User updateUser(User user);

    void deleteUser(Long id);

    Page<User> findAllUsers(Pageable pageable, UserSearchForm userSearchForm);

    User findUser(Long id);

    User buildUserRoles(UserRolesForm userRolesForm);

    User findUser(String username);

    User activate(String userHashKey);

    UserChangePasswordForm changeUserPassword(User user, UserChangePasswordForm userChangePasswordForm);

    User changeUserAvatar(Long userId, MultipartFile avatarFile) throws IOException;

    UserLanguage updateSelectedLanguage(Long userId, UserLanguage selectedLanguage);
}
