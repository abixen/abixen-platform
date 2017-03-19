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

package com.abixen.platform.common.model.audit;

import com.abixen.platform.common.model.Model;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingModel extends Model implements AuditingModelBase {

    @CreatedBy
    @Column(name = "created_by_id")
    private Long createdById;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by_id")
    private Long lastModifiedById;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public abstract Long getId();

    @Override
    public Long getCreatedById() {
        return createdById;
    }

    @Override
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Long getLastModifiedById() {
        return lastModifiedById;
    }

    @Override
    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    @Override
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName());
        result.append(newLine);
        result.append("Object {");
        result.append(newLine);

        Field[] fields = this.getClass().getDeclaredFields();

        AccessibleObject.setAccessible(fields, true);

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                result.append("    ");
                try {
                    result.append(field.getName());
                    result.append(": ");
                    //requires access to private field
                    result.append(field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                result.append(newLine);
            }
        }

        result.append("    createdById: ");
        result.append(getCreatedById());
        result.append(newLine);

        result.append("    createdDate: ");
        result.append(getCreatedDate());
        result.append(newLine);

        result.append("    lastModifiedById: ");
        result.append(getLastModifiedById());
        result.append(newLine);

        result.append("    lastModifiedDate: ");
        result.append(getLastModifiedDate());
        result.append(newLine);

        result.append("}");

        return result.toString();
    }
}
