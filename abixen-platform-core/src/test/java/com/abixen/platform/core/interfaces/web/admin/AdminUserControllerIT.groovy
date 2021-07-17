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

import com.abixen.platform.common.application.representation.FormValidationResultRepresentation
import com.abixen.platform.common.domain.model.enumtype.RoleType
import com.abixen.platform.common.interfaces.web.page.PlatformPageImpl
import com.abixen.platform.core.AbstractPlatformIT
import com.abixen.platform.core.application.dto.RoleDto
import com.abixen.platform.core.application.dto.UserDto
import com.abixen.platform.core.application.dto.UserRoleDto
import com.abixen.platform.core.application.form.UserRolesForm
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import java.nio.charset.Charset


class AdminUserControllerIT extends AbstractPlatformIT {

    @LocalServerPort
    private int port

    //@Autowired
    private RestTemplate restTemplate = new RestTemplate()


    void setup() {
    }

    void "should find all users"() {

        given:
        final HttpHeaders headers = createHeaders(ADMIN_USERNAME, ADMIN_PASSWORD)

        final HttpEntity<String> httpEntity = new HttpEntity<>(null, headers)

        when:
        final ResponseEntity<PlatformPageImpl<UserDto>> responseEntity = restTemplate
                .exchange("http://localhost:${port}/api/control-panel/users?page=0&size=20&sort=id,asc", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PlatformPageImpl<UserDto>>(){})

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        final Page<UserDto> users = responseEntity.getBody()

        users.getContent().size() == 1
        users.getNumber() == 0
        users.getNumberOfElements() == 1
        users.getSize() == 20
        users.getTotalElements() == 1
        users.getTotalPages() == 1
        users.isFirst()
        users.isLast()
        //users.getSort().size() == 1

        /*final Sort.Order order = users.getSort().iterator().next()
        order.isAscending()
        order.direction == Sort.Direction.ASC
        !order.isIgnoreCase()
        order.getProperty() == "id"
        order.getNullHandling() == Sort.NullHandling.NATIVE*/
    }

    void "should update user roles"() {

        given:
        final HttpHeaders headers = createHeaders(ADMIN_USERNAME, ADMIN_PASSWORD)

        final Long userId = 1L
        final UserDto userDto = new UserDto()
                .setId(userId)

        final List<UserRoleDto> newUserRoles = new ArrayList<>()

        final UserRoleDto userRoleDto = new UserRoleDto()
        userRoleDto.setSelected(false)
        userRoleDto.setRole(new RoleDto()
                .setName("Administrator")
                .setRoleType(RoleType.ROLE_ADMIN)
                .setId(1L))
        newUserRoles.add(userRoleDto)

        final UserRolesForm userRolesForm = new UserRolesForm()
        userRolesForm.setUser(userDto)
        userRolesForm.setUserRoles(newUserRoles)

        final HttpEntity<UserRolesForm> requestEntity = new HttpEntity<UserRolesForm>(userRolesForm, headers)

        when:
        final ResponseEntity<String> responseEntity = restTemplate
                .exchange("http://localhost:${port}/api/control-panel/users/${userId}/roles",
                HttpMethod.PUT,
                requestEntity,
                String.class)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        final ObjectMapper mapper = new ObjectMapper()
        final FormValidationResultRepresentation<UserRolesForm> formValidationResultDto = mapper.readValue(responseEntity.getBody(),
                new TypeReference<FormValidationResultRepresentation<UserRolesForm>>() {
                })

        formValidationResultDto.getFormErrors().size() == 0

        final UserRolesForm updatedUserRolesForm = formValidationResultDto.getForm()
        updatedUserRolesForm.getUser().getId() == userDto.getId()
        updatedUserRolesForm.getUserRoles().size() == 1
        !updatedUserRolesForm.getUserRoles().get(0).isSelected()
        updatedUserRolesForm.getUserRoles().get(0).getRole().getId() == 1L

        cleanup:
        userRolesForm.getUserRoles().get(0).setSelected(true)
        final HttpEntity<UserRolesForm> requestEntityCleanup = new HttpEntity<UserRolesForm>(userRolesForm, headers)

        restTemplate
                .exchange("http://localhost:${port}/api/control-panel/users/${userId}/roles",
                HttpMethod.PUT,
                requestEntityCleanup,
                String.class)
    }

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            final String auth = username + ":" + password
            final byte[] encodedAuth = Base64.getEncoder().encode(
                    auth.getBytes(Charset.forName("US-ASCII")) )
            final String authHeader = "Basic " + new String( encodedAuth )
            set( "Authorization", authHeader );
        }}
    }

}