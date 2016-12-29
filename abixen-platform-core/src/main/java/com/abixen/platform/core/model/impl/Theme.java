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

import com.abixen.platform.core.model.SecurableModel;
import com.abixen.platform.core.model.ThemeBase;
import com.abixen.platform.core.model.web.ThemeWeb;

import javax.persistence.*;


@Entity
@Table(name = "theme")
@SequenceGenerator(sequenceName = "theme_seq", name = "theme_seq", allocationSize = 1)
public class Theme extends AuditingModel implements ThemeBase, ThemeWeb, SecurableModel<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "theme_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = THEME_TITLE_MAX_LENGTH, nullable = false)
    private String title;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

}
