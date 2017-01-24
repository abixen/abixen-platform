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
package com.abixen.platform.core.model;

public interface CommentBase<Comment extends CommentBase, Module extends ModuleBase>  {

    public static final int COMMENT_MESSAGE_MIN_LENGTH = 1;
    public static final int COMMENT_MESSAGE_MAX_LENGTH = 1000;

    Long getId();
    void setId(Long id);

    String getMessage();
    void setMessage(String message);

    Comment getParent();
    void setParent(Comment parent);

    Module getModule();
    void setModule(Module module);
}
