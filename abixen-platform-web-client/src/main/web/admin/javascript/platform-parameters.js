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

var platformParameters = {
    events: {
        ADF_TOGGLE_EDIT_MODE_EVENT: 'ADF_TOGGLE_EDIT_MODE',
        ADF_TOGGLE_EDIT_MODE_RESPONSE_EVENT: 'ADF_TOGGLE_EDIT_MODE_INVOKED',
        ADF_ADD_WIDGET_EVENT: 'ADF_ADD_WIDGET',
        ADF_EDIT_DASHBOARD_EVENT: 'ADF_EDIT_DASHBOARD',
        ADF_WIDGET_DELETED_EVENT: 'ADF_WIDGET_DELETED',
        ADF_STRUCTURE_CHANGED_EVENT: 'ADF_STRUCTURE_CHANGED',
        ADF_DASHBOARD_CHANGED_EVENT: 'ADF_DASHBOARD_CHANGED',
        MODULE_READY: 'MODULE_READY',
        RELOAD_MODULE: 'RELOAD_MODULE',
        MODULE_FORBIDDEN: 'MODULE_FORBIDDEN',
        START_REQUEST: 'START_REQUEST',
        STOP_REQUEST: 'STOP_REQUEST',
        SHOW_LOADER: 'SHOW_LOADER',
        HIDE_LOADER: 'HIDE_LOADER',
        SHOW_PERMISSION_DENIED_TO_MODULE: 'SHOW_PERMISSION_DENIED_TO_MODULE',
        SIDEBAR_ELEMENT_SELECTED: 'SIDEBAR_ELEMENT_SELECTED'
    },
    formats: {
        DATE_TIME_FORMAT: 'MMM d, y h:mm:ss a'
    },

    statusAlertTypes: {
        SUCCESS: 'success',
        ERROR: 'error',
        WARNING: 'warning',
        NOTE: 'note',
        WAIT: 'wait'
    }
};