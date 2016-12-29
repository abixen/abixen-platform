'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        applicationLibs: 'src/main/resources/static/application/web-content-service/abixen/lib',
        adminLibs: 'src/main/resources/static/admin/web-content-service/abixen/lib',
        adminScripts: 'admin/web-content-service/abixen/web-content-service.min.js',
        applicationScripts: 'application/web-content-service/abixen/web-content-service.min.js',
        applicationStyles: 'application/web-content-service/abixen/web-content-service.min.css',
        adminStyles: 'admin/web-content-service/abixen/web-content-service.min.css'
    },
    applicationLibs: {
        files: [
        ]
    },
    adminLibs: {
        files: [
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
            'src/main/web/**/*.web-content-service.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/admin/web-content-service/**/*.scss'
        ],
        adminSass: [
            'src/main/web/admin/web-content-service/abixen/scss/web-content-service.scss'
        ],

        applicationWatch: [
            'src/main/web/application/web-content-service/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/application/web-content-service/abixen/scss/web-content-service.scss'
        ]
    }
};