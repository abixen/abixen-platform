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

package com.abixen.platform.service.businessintelligence.magicnumber.service;

import com.abixen.platform.service.businessintelligence.magicnumber.form.MagicNumberModuleConfigurationForm;
import com.abixen.platform.service.businessintelligence.magicnumber.model.impl.MagicNumberModule;


public interface MagicNumberModuleService {

    MagicNumberModule buildMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm);

    MagicNumberModuleConfigurationForm createMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm);

    MagicNumberModuleConfigurationForm updateMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm);

    MagicNumberModule findMagicNumberModuleByModuleId(Long id);

    MagicNumberModule createMagicNumberModule(MagicNumberModule magicNumberModule);

    MagicNumberModule updateMagicNumberModule(MagicNumberModule magicNumberModule);

    void removeMagicNumberModule(Long moduleId);
}
