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

package com.abixen.platform.core.application.form

import com.abixen.platform.core.domain.model.Layout
import spock.lang.Specification

class LayoutFormTest extends Specification {

    void "should build LayoutForm from Layout entity"() {
        given:
        final Layout layout = Layout.builder()
                .iconFileName("iconFileName")
                .content("content")
                .title("title")
                .build()
        layout.id = 1L

        when:
        final LayoutForm layoutForm = new LayoutForm(layout)

        then:
        layoutForm.id == layout.id
        layoutForm.title == layout.title
        layoutForm.content == layout.content
        layoutForm.iconFileName == layout.iconFileName
    }

}