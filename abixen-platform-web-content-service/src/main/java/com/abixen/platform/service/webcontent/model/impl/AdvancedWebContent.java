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

package com.abixen.platform.service.webcontent.model.impl;

import com.abixen.platform.service.webcontent.model.enumtype.WebContentType;
import com.abixen.platform.service.webcontent.model.web.AdvancedWebContentWeb;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "advanced_web_content")
@DiscriminatorValue(value = WebContentType.Values.ADVANCED)
public class AdvancedWebContent extends WebContent implements AdvancedWebContentWeb, Serializable {

    private static final long serialVersionUID = -9126772442611909363L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @Override
    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

}
