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

package com.abixen.platform.core.security;


import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlatformMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    // private PageRepository pageRepository;

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public PlatformMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean canViewPage(Long pageId) {
        log.debug("canViewPage - pageId:" + pageId);
        log.debug("authentication:" + authentication);
        //Page page = null;//pageRepository.findByName(pageName);
        //log.debug("page:" + page);
        //if (page == null) {
        //    return true;
        //}
        //return hasPermission(page, PermissionName.PAGE_VIEW);
        return true;
    }

    //public void setPageRepository(PageRepository pageRepository) {
    //this.pageRepository = pageRepository;
    //}

    protected void setThis(Object target) {
        this.target = target;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}
