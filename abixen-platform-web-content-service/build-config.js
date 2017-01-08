'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        adminLibs: 'src/main/resources/static/admin/web-content-service/lib',
        adminScripts: 'admin/web-content-service/web-content.min.js',
        adminStyles: 'admin/web-content-service/web-content.min.css'
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
        concatOrder: [
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/admin/web-content-service/**/*.scss'
        ],
        adminSass: [
            'src/main/web/admin/web-content-service/scss/web-content.scss'
        ]
    }
};