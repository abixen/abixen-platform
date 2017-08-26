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


import com.abixen.platform.common.model.enumtype.UserLanguage;
import com.abixen.platform.core.application.dto.UserDto;
import com.abixen.platform.core.application.form.UserChangePasswordForm;
import com.abixen.platform.core.application.form.UserForm;
import com.abixen.platform.core.application.form.UserRolesForm;
import com.abixen.platform.core.application.form.UserSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserManagementService {

    UserDto findUser(Long id);

    UserDto findUser(String username);

    Page<UserDto> findAllUsers(Pageable pageable, UserSearchForm userSearchForm);

    UserRolesForm findUserRoles(Long id);

    UserForm createUser(UserForm userForm);

    UserForm updateUser(UserForm userForm);

    UserRolesForm updateUserRoles(UserRolesForm userRolesForm);

    void deleteUser(Long id);

    ResponseEntity<byte[]> getUserAvatar(String hash) throws IOException;

    UserDto updateUserAvatar(Long id, MultipartFile avatarFile) throws IOException;

    UserChangePasswordForm changeUserPassword(UserChangePasswordForm userChangePasswordForm);

    UserLanguage updateUserSelectedLanguage(UserLanguage selectedLanguage);

    void activate(String userHashKey);

}