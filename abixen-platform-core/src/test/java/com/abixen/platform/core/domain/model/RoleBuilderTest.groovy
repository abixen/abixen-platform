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
package com.abixen.platform.core.domain.model

import com.abixen.platform.common.domain.model.enumtype.RoleType
import spock.lang.Specification

class RoleBuilderTest extends Specification {

    void "should build Role entity"() {
        given:
        final String name = "rolename"
        final RoleType roleType = RoleType.ROLE_USER

        when:
        Role role = Role.builder()
                .name(name)
                .type(roleType)
                .build()
        then:
        role.name == name
        role.roleType == roleType
    }
}
