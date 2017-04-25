'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        libs: 'src/main/resources/static/lib',
        loginScripts: 'login/application.min.js',
        adminScripts: 'control-panel/application.min.js',
        applicationScripts: 'application/application.min.js',
        commonScripts: 'common/modules.min.js',
        loginStyles: 'login/application.min.css',
        applicationStyles: 'application/application.min.css',
        adminStyles: 'control-panel/application.min.css',
        loginImages: 'src/main/resources/static/login/image',
        commonNavigationImages: 'src/main/resources/static/common/navigation/image',
        libStyles: 'lib/lib.min.css',
        fontawesome: 'src/main/resources/static/lib/fonts/fontawesome',
        roboto: 'src/main/resources/static/fonts/roboto',
        glyphicons: 'src/main/resources/static/fonts/bootstrap',
        ckeditor: 'src/main/resources/static/lib/ckeditor',
        moment: 'src/main/resources/static/lib/moment',
        applicationTemplateCache: 'src/main/web/application/javascript',
        controlPanelTemplateCache: 'src/main/web/control-panel/javascript'
    },
    images: {
        login: 'src/main/web/login/image/*',
        commonNavigation: 'src/main/web/common/navigation/image/*'
    },
    libs: {
        files: [
            'bower_components/angular/angular.min.js',
            'bower_components/angular-resource/angular-resource.min.js',
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/angular-touch/angular-touch.min.js',
            'bower_components/angular-route/angular-route.min.js',
            'bower_components/angular-cookies/angular-cookies.min.js',
            'bower_components/angular-ui-router/release/angular-ui-router.min.js',
            'bower_components/angular-ui-grid/ui-grid.min.js',
            'bower_components/angular-ui-grid/ui-grid.min.css',
            'bower_components/angular-ui-grid/ui-grid.eot',
            'bower_components/angular-ui-grid/ui-grid.svg',
            'bower_components/angular-ui-grid/ui-grid.ttf',
            'bower_components/angular-ui-grid/ui-grid.woff',
            'bower_components/angular-aside/dist/js/angular-aside.min.js',
            'bower_components/angular-translate/angular-translate.min.js',
            'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
            'bower_components/Sortable/Sortable.min.js',
            'bower_components/pdfmake/build/pdfmake.min.js',
            'bower_components/pdfmake/build/vfs_fonts.js',
            'bower_components/AngularJS-Toaster/toaster.min.js',
            'bower_components/AngularJS-Toaster/toaster.min.css',
            'bower_components/angular-xeditable/dist/js/xeditable.min.js',
            'bower_components/angular-xeditable/dist/css/xeditable.css',
            'bower_components/angular-file-upload/dist/angular-file-upload.min.js',
            'bower_components/codemirror/lib/codemirror.css',
            'bower_components/codemirror/lib/codemirror.js',
            'bower_components/angular-ui-codemirror/ui-codemirror.js',
            'bower_components/angular-loading-bar/src/loading-bar.js',
            'bower_components/angular-loading-bar/src/loading-bar.css',
            'bower_components/ng-scrollbar/dist/ng-scrollbar.min.js',
            'bower_components/ng-scrollbar/dist/ng-scrollbar.min.css',
            'bower_components/moment/moment.js',
            'bower_components/moment/locale/ru.js',
            'bower_components/moment/locale/es.js',
            'bower_components/moment/locale/pl.js',
            'bower_components/moment/locale/uk.js',
            'bower_components/moment/locale/en-gb.js',
            'bower_components/angular-moment/angular-moment.js',
            'src/main/web/lib/show-errors.min.js',
            'src/main/web/lib/ng-storage.min.js',
            'src/main/web/lib/code-mirror/xml.js'
        ],
        fontawesome: 'bower_components/fontawesome/fonts/*',
        roboto: 'bower_components/roboto-fontface/fonts/*',
        glyphicons: 'bower_components/bootstrap-sass-official/assets/fonts/bootstrap/*',
        ckeditor: 'bower_components/ckeditor/**',
        moment: 'bower_components/moment/**'
    },
    templates: {
        files: 'src/main/web/**/*.html',
        minifyOpts: {
            removeComments: true,
            removeCommentsFromCDATA: true,
            collapseWhitespace: true,
            conservativeCollapse: false,
            collapseInlineTagWhitespace: true,
            preserveLineBreaks: false,
            useShortDoctype: true,
            keepClosingSlash: true,
            caseSensitive: true,
            quoteCharacter: '"'
        }
    },
    scripts: {
        loginFiles: [
            'src/main/web/login/**/*.js'
        ],
        adminFiles: [
            'src/main/web/control-panel/**/*.js'
        ],
        applicationFiles: [
            'src/main/web/application/**/*.js'
        ],
        commonFiles: [
            'src/main/web/common/**/*.js'
        ],
        concatOrder: [
            'src/main/web/**/*.templatecache.config.js',
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        loginWatch: [
            'src/main/web/**/*.scss'
        ],
        loginSass: [
            'src/main/web/login/scss/application.scss'
        ],

        adminWatch: [
            'src/main/web/**/*.scss'
        ],
        adminSass: [
            'src/main/web/control-panel/scss/application.scss'
        ],

        applicationWatch: [
            'src/main/web/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/application/scss/application.scss'
        ],

        libSass: [
            'src/main/web/lib/bootstrap/bootstrap.scss',
            'src/main/web/lib/font-awesome/font-awesome.scss',
            'src/main/web/common/fonts/roboto.scss'
        ]
    }
};