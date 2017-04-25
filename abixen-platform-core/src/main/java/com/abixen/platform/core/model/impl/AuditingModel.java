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

import com.abixen.platform.common.model.AuditingModelBase;
import com.abixen.platform.common.model.Model;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingModel extends Model implements AuditingModelBase<User> {

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_id")
    private User lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public abstract Long getId();

    public User getCreatedBy() {
        if (this instanceof User) {
            if (!this.equals(createdBy)) {
                return createdBy;
            } else {
                return null;
            }
        }
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        if (this instanceof User) {
            if (this.equals(createdBy)) {
                this.createdBy = null;
            } else {
                this.createdBy = createdBy;
            }
        } else {
            this.createdBy = createdBy;
        }
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        if (this instanceof User) {
            if (!this.equals(lastModifiedBy)) {
                return lastModifiedBy;
            } else {
                return null;
            }
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        if (this instanceof User) {
            if (this.equals(lastModifiedBy)) {
                this.lastModifiedBy = null;
            } else {
                this.lastModifiedBy = lastModifiedBy;
            }
        } else {
            this.lastModifiedBy = lastModifiedBy;
        }
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

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

                    Object object = field.get(this);

                    if (object instanceof AuditingModel) {
                        result.append(((AuditingModel) field.get(this)).getId());
                    } else {
                        result.append(field.get(this));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                result.append(newLine);
            }
        }

        result.append("    createdBy: ");
        result.append(getCreatedBy() != null ? getCreatedBy().getUsername() : null);
        result.append(newLine);

        result.append("    createdDate: ");
        result.append(getCreatedDate());
        result.append(newLine);

        result.append("    lastModifiedBy: ");
        result.append(getLastModifiedBy() != null ? getLastModifiedBy().getUsername() : null);
        result.append(newLine);

        result.append("    lastModifiedDate: ");
        result.append(getLastModifiedDate());
        result.append(newLine);

        result.append("}");

        return result.toString();
    }
}
