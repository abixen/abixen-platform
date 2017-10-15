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
package com.abixen.platform.core.domain.model

import com.abixen.platform.common.domain.model.enumtype.ResourcePage
import com.abixen.platform.common.domain.model.enumtype.ResourcePageLocation
import com.abixen.platform.common.domain.model.enumtype.ResourceType
import spock.lang.Specification

class ResourceBuilderTest extends Specification {

    void "should build Resource entity"() {
        given:
        final String relativeUrl = "/resource-builder-test"
        final ResourcePageLocation resourcePageLocation = ResourcePageLocation.BODY
        final ResourcePage resourcePage = ResourcePage.ADMIN
        final ResourceType resourceType = ResourceType.JAVASCRIPT
        final ModuleType moduleType = new ModuleTypeBuilder()
                .basic("resource-builder", "resource-builder-title", "resource-builder-description")
                .angular("resource", "resource")
                .initUrl("/resource-builder")
                .serviceId("rb")
                .build()

        when:
        Resource resource = new ResourceBuilder()
                .relativeUrl(relativeUrl)
                .pageLocation(resourcePageLocation)
                .page(resourcePage)
                .type(resourceType)
                .moduleType(moduleType)
                .build()

        then:
        resource.relativeUrl == relativeUrl
        resource.resourcePageLocation == resourcePageLocation
        resource.resourcePage == resourcePage
        resource.resourceType == resourceType
        resource.moduleType == moduleType
    }

}
