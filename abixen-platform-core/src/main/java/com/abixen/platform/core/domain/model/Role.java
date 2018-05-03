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

package com.abixen.platform.core.domain.model;


import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.enumtype.RoleType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@JsonSerialize(as = Role.class)
@Entity
@Table(name = "role_")
@SequenceGenerator(sequenceName = "role_seq", name = "role_seq", allocationSize = 1)
public final class Role extends AuditingModel {

    public static final int ROLE_NAME_MAX_LENGTH = 300;

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

    private Role() {
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void changeDetails(String name, RoleType type) {
        setName(name);
        setRoleType(type);
    }

    public void changePermissions(Set<Permission> permissions) {
        getPermissions().clear();
        getPermissions().addAll(permissions);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Role> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new Role();
        }

        public Builder name(String name) {
            this.product.setName(name);
            return this;
        }

        public Builder type(RoleType roleType) {
            this.product.setRoleType(roleType);
            return this;
        }

    }
}