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

import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("FROM Resource r WHERE r.moduleType.id = ?1")
    Page<Resource> findAll(Long moduleId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Resource r WHERE r.moduleType = ?1")
    void deleteAll(ModuleType moduleType);

}