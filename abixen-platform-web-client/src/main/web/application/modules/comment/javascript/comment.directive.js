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

(function () {
    'use strict';

    angular
        .module('platformCommentModule')
        .directive('comment', commentDirective);

    commentDirective.$inject = ['$log', '$http'];

    function commentDirective($log, $http) {
        var counter = 0,
            depth = null;

        return {
            restrict: 'E',
            templateUrl: '/application/modules/comment/html/comment.template.html',
            scope: {
                commentItem: '='
            },
            compile: compile,
            controller: CommentsDirectiveController,
            controllerAs: 'comment',
            bindToController: true
        };

        function compile(tElement, tAttrs) {
            depth = tAttrs.depth || depth || 1;
            if (counter == depth) {
                tElement.find('comment').remove();
                depth = 'end';
            }
            depth == 'end' ? counter = 0 : counter++;
        }
    }

    function CommentsDirectiveController($scope, $log, $http) {
        var comment = this;
    }
})();