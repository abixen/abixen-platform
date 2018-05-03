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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "page")
@SequenceGenerator(sequenceName = "page_seq", name = "page_seq", allocationSize = 1)
public final class Page extends AuditingModel implements SecurableModel {

    public static final int PAGE_TITLE_MAX_LENGTH = 40;
    public static final int PAGE_ICON_MAX_LENGTH = 40;
    public static final int PAGE_DESCRIPTION_MAX_LENGTH = 255;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "page_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = PAGE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "icon", length = PAGE_ICON_MAX_LENGTH, nullable = false)
    private String icon;

    @Column(name = "description", length = PAGE_DESCRIPTION_MAX_LENGTH)
    private String description;

    @JoinColumn(name = "layout_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Layout layout;

    private Page() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    private void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Layout getLayout() {
        return layout;
    }

    private void setLayout(Layout layout) {
        this.layout = layout;
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void changeLayout(Layout layout) {
        setLayout(layout);
    }

    public void changeIcon(String icon) {
        setIcon(icon);
    }

    public void changeDescription(String description) {
        setDescription(description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Page> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new Page();
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder layout(Layout layout) {
            this.product.setLayout(layout);
            return this;
        }

        public Builder icon(String icon) {
            this.product.setIcon(icon);
            return this;
        }

        public Builder description(String description) {
            this.product.setDescription(description);
            return this;
        }

    }
}