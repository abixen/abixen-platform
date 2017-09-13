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

package com.abixen.platform.core.interfaces.web.admin

import com.abixen.platform.common.interfaces.web.page.PlatformPageImpl
import com.abixen.platform.core.AbstractPlatformIT
import com.abixen.platform.core.application.dto.UserDto
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class AdminUserControllerIT extends AbstractPlatformIT {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate template


    void setup() {
    }

    void "should find all users"() {

        given:

        when:
        final ResponseEntity<String> responseEntity = template
                .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                .getForEntity("http://localhost:${port}/api/control-panel/users?page=0&size=20&sort=id,asc", String.class)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        final ObjectMapper mapper = new ObjectMapper();
        final PlatformPageImpl<UserDto> users = mapper.readValue(responseEntity.getBody(),
                new TypeReference<PlatformPageImpl<UserDto>>() {
                });

        users.getContent().size() == 1
        users.getNumber() == 0
        users.getNumberOfElements() == 1
        users.getSize() == 20
        users.getTotalElements() == 1
        users.getTotalPages() == 1
        users.isFirst()
        users.isLast()
        users.getSort().size() == 1

        final Sort.Order order = users.getSort().iterator().next()
        order.isAscending()
        order.direction == Sort.Direction.ASC
        !order.isIgnoreCase()
        order.getProperty() == "id"
        order.getNullHandling() == Sort.NullHandling.NATIVE
    }

}