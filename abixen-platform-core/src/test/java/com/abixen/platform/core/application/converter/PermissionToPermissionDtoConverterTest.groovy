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

package com.abixen.platform.core.application.converter

import com.abixen.platform.core.application.dto.PermissionDto
import com.abixen.platform.core.domain.model.Permission
import com.abixen.platform.core.domain.model.PermissionAclClassCategory
import com.abixen.platform.core.domain.model.PermissionGeneralCategory
import spock.lang.Specification

class PermissionToPermissionDtoConverterTest extends Specification {

    private PermissionToPermissionDtoConverter permissionToPermissionDtoConverter

    void setup() {
        permissionToPermissionDtoConverter = new PermissionToPermissionDtoConverter()
    }


    void "should convert Permission entity to PermissionDto"() {
        given:
        final Permission permission = [
                id                        : 1,
                title                     : "title",
                description               : "description",
                permissionAclClassCategory: PermissionAclClassCategory.newInstance(),
                permissionGeneralCategory : PermissionGeneralCategory.newInstance()

        ] as Permission

        when:
        final PermissionDto permissionDto = permissionToPermissionDtoConverter.convert(permission)

        then:
        permissionDto != null
        permissionDto.id == permission.id
        permissionDto.title == permission.title
        permissionDto.description == permission.description
        permissionDto.permissionAclClassCategory == permission.permissionAclClassCategory
        permissionDto.permissionGeneralCategory == permission.permissionGeneralCategory

        0 * _
    }

}