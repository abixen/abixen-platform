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

package com.abixen.platform.core.domain.repository;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.infrastructure.repository.PlatformJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ModuleRepository extends PlatformJpaRepository<Module, Long> {

    default org.springframework.data.domain.Page<Module> findAllSecured(Pageable pageable, ModuleSearchForm moduleSearchForm, User user, PermissionName permissionName) {
        return findAll(pageable, moduleSearchForm, user, AclClassName.MODULE, permissionName);
    }

    @Query("FROM Module m WHERE m.page = ?1")
    List<Module> findAll(Page page);

    @Query("FROM Module m WHERE m.page = ?1 AND m.id NOT IN (?2)")
    List<Module> findAllExcept(Page page, List<Long> ids);

    @Query("DELETE FROM Module m WHERE m.page = ?1 AND m.id NOT IN (?2)")
    @Modifying(clearAutomatically = true)
    void removeAllExcept(Page page, List<Long> ids);

    @Query("DELETE FROM Module m WHERE m.page = ?1")
    @Modifying(clearAutomatically = true)
    void removeAll(Page page);

}