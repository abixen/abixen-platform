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

package com.abixen.platform.core.repository.custom.impl.specification;


import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.AclEntry;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SecuredSpecifications {
    public static <T> Specification<T> getSpecification(final User user, final AclClassName aclClassName, final PermissionName permissionName) {
        log.debug("getSpecification() - user.id: {}, aclClassName: {}, permissionName: {}", user.getId(), aclClassName, permissionName);

        final String rolesField = "roles";
        final String permissionsField = "permissions";
        final String idField = "id";
        final String createdByField = "createdBy";
        final String permissionNameField = "permissionName";
        final String permissionField = "permission";
        final String aclSidField = "aclSid";
        final String sidTypeField = "sidType";
        final String sidIdField = "sidId";
        final String aclObjectIdentityField = "aclObjectIdentity";
        final String aclClassField = "aclClass";
        final String aclClassNameField = "aclClassName";
        final String objectIdField = "objectId";

        return (candidateRoot, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Subquery<Long> subqueryUserPermissionCheck = criteriaQuery.subquery(Long.class);
            Root<User> userRoot = subqueryUserPermissionCheck.from(User.class);
            Join<User, Role> userRole = userRoot.join(rolesField);
            Join<Role, Permission> rolePermission = userRole.join(permissionsField);
            List<Predicate> parametersUserPermissionCheck = new ArrayList<>();
            parametersUserPermissionCheck.add(cb.equal(userRoot.get(idField), user.getId()));
            parametersUserPermissionCheck.add(cb.equal(rolePermission.get(permissionNameField), permissionName));
            subqueryUserPermissionCheck
                    .select(cb.count(userRoot))
                    .where(cb.and(parametersUserPermissionCheck.toArray(new Predicate[parametersUserPermissionCheck.size()])));

            Subquery<Long> subqueryAclEntryRoleCheck = criteriaQuery.subquery(Long.class);
            Root<AclEntry> aclEntryRoleRoot = subqueryAclEntryRoleCheck.from(AclEntry.class);
            List<Predicate> parametersAclEntryRoleCheck = new ArrayList<>();
            parametersAclEntryRoleCheck.add(cb.equal(aclEntryRoleRoot.get(permissionField).get(permissionNameField), permissionName));
            parametersAclEntryRoleCheck.add(cb.equal(aclEntryRoleRoot.get(aclSidField).get(sidTypeField), AclSidType.ROLE));
            List<Long> roleIds = user.getRoles().stream().map(r -> r.getId()).collect(Collectors.toList());
            parametersAclEntryRoleCheck.add(aclEntryRoleRoot.get(aclSidField).get(sidIdField).in(roleIds));
            parametersAclEntryRoleCheck.add(cb.equal(aclEntryRoleRoot.get(aclObjectIdentityField).get(aclClassField).get(aclClassNameField), aclClassName));
            parametersAclEntryRoleCheck.add(cb.equal(aclEntryRoleRoot.get(aclObjectIdentityField).get(objectIdField), candidateRoot.get(idField)));
            subqueryAclEntryRoleCheck
                    .select(cb.count(aclEntryRoleRoot))
                    .where(cb.and(parametersAclEntryRoleCheck.toArray(new Predicate[parametersAclEntryRoleCheck.size()])));

            Subquery<Long> subqueryAclEntryOwnerCheck = criteriaQuery.subquery(Long.class);
            Root<AclEntry> aclEntryOwnerRoot = subqueryAclEntryOwnerCheck.from(AclEntry.class);
            List<Predicate> parametersAclEntryOwnerCheck = new ArrayList<>();
            parametersAclEntryOwnerCheck.add(cb.equal(aclEntryOwnerRoot.get(permissionField).get(permissionNameField), permissionName));
            parametersAclEntryOwnerCheck.add(cb.equal(aclEntryOwnerRoot.get(aclSidField).get(sidTypeField), AclSidType.OWNER));
            final Long ownerId = 0L;
            parametersAclEntryOwnerCheck.add(aclEntryOwnerRoot.get(aclSidField).get(sidIdField).in(ownerId));
            parametersAclEntryOwnerCheck.add(cb.equal(aclEntryOwnerRoot.get(aclObjectIdentityField).get(aclClassField).get(aclClassNameField), aclClassName));
            parametersAclEntryOwnerCheck.add(cb.equal(aclEntryOwnerRoot.get(aclObjectIdentityField).get(objectIdField), candidateRoot.get(idField)));
            parametersAclEntryOwnerCheck.add(cb.equal(candidateRoot.get(createdByField), user));
            subqueryAclEntryOwnerCheck
                    .select(cb.count(aclEntryOwnerRoot))
                    .where(cb.and(parametersAclEntryOwnerCheck.toArray(new Predicate[parametersAclEntryOwnerCheck.size()])));

            final Long minLimitValue = 0L;
            predicates.add(cb.greaterThan(subqueryUserPermissionCheck, minLimitValue));
            predicates.add(cb.greaterThan(subqueryAclEntryRoleCheck, minLimitValue));
            predicates.add(cb.greaterThan(subqueryAclEntryOwnerCheck, minLimitValue));

            return cb.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}