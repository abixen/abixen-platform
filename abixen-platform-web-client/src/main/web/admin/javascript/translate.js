platformAdminApplication.config(['$translateProvider',
    function ($translateProvider) {

        $translateProvider
            .translations('en', {
                'search': 'Search',
                'dashboard': 'Dashboard',
                'users': 'Users',
                'roles': 'Roles',
                'permissions': 'Permissions',
                'pages': 'Pages',
                'modules': 'Modules',
                'templates': 'Templates',
                'data-sources': 'Data Sources',
                'my-account': 'My Account',
                'logout': 'Logout'
            })
            .translations('pl', {
                'search': 'Szukaj',
                'dashboard': 'Dashboard',
                'users': 'Użytkownicy',
                'roles': 'Role',
                'permissions': 'Uprawnienia',
                'pages': 'Strony',
                'modules': 'Moduły',
                'templates': 'Szablony',
                'data-sources': 'Źródła Danych',
                'my-account': 'Moje Konto',
                'logout': 'Wyloguj'
            });
        $translateProvider.preferredLanguage('en');
    }
]);
