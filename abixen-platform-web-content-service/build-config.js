'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        adminLibs: 'src/main/resources/static/service/abixen/web-content/admin/lib',
        adminScripts: 'service/abixen/web-content/admin/web-content.min.js',
        adminStyles: 'service/abixen/web-content/admin/web-content.min.css',
        templateCache: 'src/main/web/service/abixen/web-content/admin/js'
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
            'src/main/web/service/abixen/web-content/admin/**/*.js'
        ],
        concatOrder: [
            'src/main/web/**/*template-cache.config.js',
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/service/abixen/web-content/admin/**/*.scss'
        ],
        adminSass: [
            'src/main/web/service/abixen/web-content/admin/scss/web-content.scss'
        ]
    }
};