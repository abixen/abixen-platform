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

var commonSelectionModalWindowControllers = angular.module('modalSelectionWindowController', []);

commonSelectionModalWindowControllers.controller('ModalSelectionWindowController', ['$scope', '$uibModalInstance', 'data','selectionType','variable','acceptFunction', 'okButtonClass', 'cancelButtonClass', 'title', 
    function ($scope, $uibModalInstance,data,selectionType,variable,acceptFunction,okButtonClass,cancelButtonClass, title) {

        $scope.data = data; // the data we need to display
        $scope.selectionType = selectionType;// either it's SINGLE OR MULTIPLE

        // the variable on where to assign the selection. 
        $scope.variable = variable; // always an array even if the selected value is only one.

        //  Standard variables
        $scope.title = title;
        $scope.okButtonClass = okButtonClass;
        $scope.cancelButtonClass = cancelButtonClass;

        //  get the selected item - this will be called if the the selectionType is SINGLE
        $scope.selectedId;
        $scope.select = function(rec) {
            rec.selected = 'Y';
            $scope.selectedId = rec.id;
            // deselect everything except selected.
              for (var i=0;i<$scope.data.length;i++) {
                if ($scope.data[i].id != rec.id) {
                    $scope.data[i].selected = 'N';
                }
              }
        }

        //  get the selected item - this will be called if the selectionType is MULTIPLE
        $scope.selectedIds = [];
        $scope.addToSelection = function(rec) {
            rec.selected = 'Y';
            $scope.selectedIds.push(rec.id);
        }

        //  Just discard
        $scope.discard = function () {
            $uibModalInstance.dismiss();
        };

        //  Just push the selected one back to the variable.
        $scope.accept = function () {

            if ($scope.selectionType == platformParameters.modalSelectionType.SINGLE) {
                $scope.variable.push($scope.selectedId);
            }else if ($scope.selectionType == platformParameters.modalSelectionType.MULTIPLE) {
                $scope.variable.push($scope.selectedIds);
            }

            //  and execute whatever the developer wants to execute.
            if (acceptFunction != null)  {
                acceptFunction();
            }
            //  Just set the selected values to $scope.variable
            $uibModalInstance.dismiss();
        };
}]);