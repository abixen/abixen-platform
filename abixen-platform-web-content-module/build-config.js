'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        applicationLibs: 'src/main/resources/static/application/web-content-module/abixen/lib',
        adminLibs: 'src/main/resources/static/admin/web-content-module/abixen/lib',
        adminScripts: 'admin/web-content-module/abixen/web-content-module.min.js',
        applicationScripts: 'application/web-content-module/abixen/web-content-module.min.js',
        applicationStyles: 'application/web-content-module/abixen/web-content-module.min.css',
        adminStyles: 'admin/web-content-module/abixen/web-content-module.min.css'
    },
    applicationLibs: {
        files: [
            'bower_components/angular-svg-round-progressbar/build/roundProgress.min.js',
            'bower_components/d3/d3.min.js',
            'bower_components/nvd3/build/nv.d3.min.js',
            'bower_components/nvd3/build/nv.d3.min.css',
            'bower_components/angular-nvd3/dist/angular-nvd3.min.js'
        ]
    },
    adminLibs: {
        files: [
            'bower_components/js-xlsx/dist/xlsx.full.min.js'
        ]
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
        adminFiles: [
            'src/main/web/admin/**/*.js'
        ],
        applicationFiles: [
            'src/main/web/application/**/*.js'
        ],
        concatOrder: [
            'src/main/web/**/*.web-content-module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/admin/web-content-module/**/*.scss'
        ],
        adminSass: [
            'src/main/web/admin/web-content-module/abixen/scss/web-content-module.scss'
        ],

        applicationWatch: [
            'src/main/web/application/web-content-module/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/application/web-content-module/abixen/scss/web-content-module.scss'
        ]
    }
};