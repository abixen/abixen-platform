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

package com.abixen.platform.core.interfaces.web.facade;


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

public interface UserFacade {

    UserDto find(Long id);

    UserDto find(String username);

    Page<UserDto> findAll(Pageable pageable, UserSearchForm userSearchForm);

    UserRolesForm findRoles(Long id);

    UserForm create(UserForm userForm);

    UserForm update(UserForm userForm);

    UserRolesForm updateRoles(UserRolesForm userRolesForm);

    void delete(Long id);

    ResponseEntity<byte[]> getAvatar(String hash) throws IOException;

    UserDto updateAvatar(Long id, MultipartFile avatarFile) throws IOException;

    UserChangePasswordForm changePassword(UserChangePasswordForm userChangePasswordForm);

    UserLanguage updateSelectedLanguage(UserLanguage selectedLanguage);
}