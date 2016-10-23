function AbstractModuleApplicationCrudDetailController($scope, $log, Entity) {
    $log.log('AbstractModuleApplicationCrudDetailController');

    $scope.entity = null;
    $scope.formErrors = [];

    $scope.get = function (id) {
        $log.log('selected entity id:', id);
        if (id) {
            Entity.get({id: id}, function (data) {
                $scope.entity = data;
                $log.log('Entity has been got: ', $scope.entity);
            });
        } else {
            $scope.entity = {};
        }
    };

    $scope.update = function () {
        $log.log('update() - entity: ', $scope.entity);
        Entity.update({id: $scope.entity.id}, $scope.entity, function () {
            $log.log('Entity has been updated2: ', $scope.entity);
            $scope.$emit('VIEW_MODE');
        });
    };

    $scope.save = function () {
        $log.log('$scope.entityForm.$invalid: ' + $scope.entityForm.$invalid);
        $log.log('$scope.entityForm.$invalid: ', $scope.entityForm);

        if($scope.entityForm.$invalid){
            $log.log('Form is invalid and could not be saved.');
            $scope.$broadcast('show-errors-check-validity');
            return;
        }

        $log.log('save() - entity: ', $scope.entity);

        Entity.save($scope.entity, function (data) {
            $scope.entityForm.$setPristine();
            $log.log('data.form: ' , data);
            angular.forEach(data.form, function (rejectedValue, fieldName) {
                $log.log('fiel22dName: ' + fieldName + ', ' + rejectedValue);
                if(fieldName !== 'id' && fieldName !== 'moduleId'){
                    $scope.entityForm[fieldName].$setValidity('serverMessage', true);
                }
            });

            if (data.formErrors.length == 0) {
                $log.log('Entity has been saved: ', $scope.entity);
                $scope.$emit('VIEW_MODE');
                return;
            }

            for (var i = 0; i < data.formErrors.length; i++) {
                var fieldName = data.formErrors[i].field;
                $log.log('fieldName: ' + fieldName);
                var message = data.formErrors[i].message;
                var serverMessage = $parse('entityForm.' + fieldName + '.$error.serverMessage');
                $scope.entityForm[fieldName].$setValidity('serverMessage', false);
                serverMessage.assign($scope, message);
            }

            $scope.$broadcast('show-errors-check-validity');
        });
    };

    $scope.saveForm = function () {
        $log.log('saveForm()');
        if ($scope.entity.id == null) {
            $scope.save();
        } else {
            $scope.update();
        }
    };

}