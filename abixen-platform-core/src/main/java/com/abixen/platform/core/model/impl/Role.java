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

package com.abixen.platform.core.model.impl;


import com.abixen.platform.common.model.RoleBase;
import com.abixen.platform.common.model.enumtype.RoleType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@JsonSerialize(as = Role.class)
@Entity
@Table(name = "role_")
@SequenceGenerator(sequenceName = "role_seq", name = "role_seq", allocationSize = 1)
public class Role extends AuditingModel implements RoleBase<Permission> {

    /**
     *
     */
    private static final long serialVersionUID = -1247915702100609524L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "role_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @Column(name = "name", unique = true, length = ROLE_NAME_MAX_LENGTH, nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "permission_id", nullable = false, updatable = false)})
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public RoleType getRoleType() {
        return roleType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
