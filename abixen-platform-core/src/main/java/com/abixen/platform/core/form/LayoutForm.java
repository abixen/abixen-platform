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

package com.abixen.platform.core.form;


import com.abixen.platform.common.form.Form;
import com.abixen.platform.common.model.LayoutBase;
import com.abixen.platform.core.model.impl.Layout;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


public class LayoutForm implements Form {

    private Long id;

    @NotNull
    @Length(max = LayoutBase.LAYOUT_TITLE_MAX_LENGTH)
    private String title;

    @NotNull
    @Length(max = LayoutBase.LAYOUT_CONTENT_MAX_LENGTH)
    private String content;

    @Length(max = LayoutBase.LAYOUT_ICON_FILE_NAME_MAX_LENGTH)
    @NotNull
    private String iconFileName;


    public LayoutForm() {

    }

    public LayoutForm(Layout layout) {
        this.id = layout.getId();
        this.iconFileName = layout.getIconFileName();
        this.title = layout.getTitle();
        this.content = layout.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

}