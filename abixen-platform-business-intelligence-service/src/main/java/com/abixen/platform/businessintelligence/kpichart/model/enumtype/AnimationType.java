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

package com.abixen.platform.businessintelligence.kpichart.model.enumtype;


public enum AnimationType {

    linearEase("linearEase"),
    easeInQuad("easeInQuad"),
    easeOutQuad("easeOutQuad"),
    easeInOutQuad("easeInOutQuad"),
    easeInCubic("easeInCubic"),
    easeOutCubic("easeOutCubic"),
    easeInOutCubic("easeInOutCubic"),
    easeInQuart("easeInQuart"),
    easeOutQuart("easeOutQuart"),
    easeInOutQuart("easeInOutQuart"),
    easeInQuint("easeInQuint"),
    easeOutQuint("easeOutQuint"),
    easeInOutQuint("easeInOutQuint"),
    easeInSine("easeInSine"),
    easeOutSine("easeOutSine"),
    easeInOutSine("easeInOutSine"),
    easeInExpo("easeInExpo"),
    easeOutExpo("easeOutExpo"),
    easeInOutExpo("easeInOutExpo"),
    easeInCirc("easeInCirc"),
    easeOutCirc("easeOutCirc"),
    easeInOutCirc("easeInOutCirc"),
    easeInElastic("easeInElastic"),
    easeOutElastic("easeOutElastic"),
    easeInOutElastic("easeInOutElastic"),
    easeInBack("easeInBack"),
    easeOutBack("easeOutBack"),
    easeInOutBack("easeInOutBack"),
    easeInBounce("easeInBounce"),
    easeOutBounce("easeOutBounce"),
    easeInOutBounce("easeInOutBounce");

    private final String name;

    public String getName() {
        return name;
    }

    AnimationType(String name) {
        this.name = name;
    }
}
