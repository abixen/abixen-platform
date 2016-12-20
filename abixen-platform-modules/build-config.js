'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        applicationLibs: 'src/main/resources/static/application/modules/abixen/lib',
        adminLibs: 'src/main/resources/static/admin/modules/abixen/lib',
        adminScripts: 'admin/modules/abixen/modules.min.js',
        applicationScripts: 'application/modules/abixen/modules.min.js',
        applicationStyles: 'application/modules/abixen/modules.min.css',
        adminStyles: 'admin/modules/abixen/modules.min.css'
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
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/admin/modules/**/*.scss'
        ],
        adminSass: [
            'src/main/web/admin/modules/abixen/scss/modules.scss'
        ],

        applicationWatch: [
            'src/main/web/application/modules/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/application/modules/abixen/scss/modules.scss'
        ]
    }
};