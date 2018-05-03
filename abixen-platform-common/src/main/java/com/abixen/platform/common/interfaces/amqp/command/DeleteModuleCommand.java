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

package com.abixen.platform.common.interfaces.amqp.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class DeleteModuleCommand {

    private Long moduleId;
    private String moduleTypeName;


    public DeleteModuleCommand() {
    }

    public DeleteModuleCommand(Long moduleId, String moduleTypeName) {
        this.moduleId = moduleId;
        this.moduleTypeName = moduleTypeName;
    }

}