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

package com.abixen.platform.common.model;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public abstract class Model implements Serializable {

    public abstract Long getId();

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

                    if (object instanceof Model) {
                        result.append(((Model) field.get(this)).getId());
                    } else {
                        result.append(field.get(this));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                result.append(newLine);
            }
        }

        result.append("}");

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null)
            return false;
        if (!getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        Model other = (Model) o;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}