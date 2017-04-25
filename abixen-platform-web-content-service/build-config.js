'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        adminLibs: 'src/main/resources/static/service/abixen/web-content/control-panel/lib',
        adminScripts: 'service/abixen/web-content/control-panel/web-content.min.js',
        adminStyles: 'service/abixen/web-content/control-panel/web-content.min.css',
        templateCache: 'src/main/web/service/abixen/web-content/control-panel/js',
        applicationScripts: 'service/abixen/web-content/application/web-content.min.js',
        applicationStyles: 'service/abixen/web-content/application/web-content.min.css',
        applicationTemplateCache: 'src/main/web/service/abixen/web-content/application/javascript',
        applicationLibs: 'src/main/resources/static/service/abixen/web-content/application/lib'

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
            'src/main/web/service/abixen/web-content/control-panel/**/*.js'
        ],
        applicationFiles: [
            'src/main/web/service/abixen/web-content/application/**/*.js'
        ],
        concatOrder: [
            'src/main/web/**/*templatecache.config.js',
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/service/abixen/web-content/control-panel/**/*.scss'
        ],
        adminSass: [
            'src/main/web/service/abixen/web-content/control-panel/scss/web-content.scss'
        ],
        applicationWatch: [
            'src/main/web/service/abixen/web-content/application/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/service/abixen/web-content/application/scss/web-content.scss'
        ]
    }
};