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

 /**
  * Usage:
  * modalSelectionWindow.openSelectionDialog(title, variable, data, selectionType, windowClass, callback);
  * 	title - the title of the dialog
  *		variable - the static variable that the dialog will assign the selected value
  *		data - the data in such json format [{'id':'<>','text':'','selected':'N'},{'id':'<>','text':'','selected':'N'}]
  *				id - is the identifier that gets passed to the variable.
  *       text - to be displayed
  *       selected - sets to selected to indicate that the user clicked on the option.
  *		selectionType - can be SINGLE or MULTIPLE. If MULTIPLE, an array of id is passed to the variable.
  *     windowClass - the window style class
  * 	callback - Javascript function that will be called after the OK button is clicked.
  */
var platformModalSelectionModule = angular.module('platformModalSelectionModule', ['modalSelectionWindowController', 'modalSelectionWindowService']);