INSERT INTO user_ (id, created_date, last_modified_date, first_name, last_name, password, hash_key, registration_ip, state, username, created_by_id, last_modified_by_id) VALUES (nextval('user_seq'), NULL, NULL, 'Joe', 'Brown', '$2a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '$1a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '127.0.0.1', 'ACTIVE', 'admin', NULL, NULL);
INSERT INTO user_ (id, created_date, last_modified_date, first_name, last_name, password, hash_key, registration_ip, state, username, created_by_id, last_modified_by_id) VALUES (nextval('user_seq'), NULL, NULL, 'Leo', 'Newman', '$2a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '$2a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '127.0.0.1', 'ACTIVE', 'user', NULL, NULL);
INSERT INTO user_ (id, created_date, last_modified_date, first_name, last_name, password, hash_key, registration_ip, state, username, created_by_id, last_modified_by_id) VALUES (nextval('user_seq'), NULL, NULL, 'Richard', 'Smith', '$2a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '$3a$10$sTqNSmgY2BDQVp9A01LQ4OF7AaJW9QQTOHCH.h1XdtL0sIaSplOOu', '127.0.0.1', 'ACTIVE', 'editor', NULL, NULL);

INSERT INTO role_ (id, created_date, last_modified_date, name, role_type, created_by_id, last_modified_by_id) VALUES (nextval('role_seq'), NULL, NULL, 'Administrator', 'ROLE_ADMIN', NULL, NULL);
INSERT INTO role_ (id, created_date, last_modified_date, name, role_type, created_by_id, last_modified_by_id) VALUES (nextval('role_seq'), NULL, NULL, 'User', 'ROLE_USER', NULL, NULL);
INSERT INTO role_ (id, created_date, last_modified_date, name, role_type, created_by_id, last_modified_by_id) VALUES (nextval('role_seq'), NULL, NULL, 'Page Editor', 'ROLE_PAGE_EDITOR', NULL, NULL);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO acl_class (id, name) VALUES (nextval('acl_class_seq'), 'com.abixen.platform.core.model.impl.Layout');
INSERT INTO acl_class (id, name) VALUES (nextval('acl_class_seq'), 'com.abixen.platform.core.model.impl.Module');
INSERT INTO acl_class (id, name) VALUES (nextval('acl_class_seq'), 'com.abixen.platform.core.model.impl.Page');
INSERT INTO acl_class (id, name) VALUES (nextval('acl_class_seq'), 'com.abixen.platform.core.model.impl.Role');
INSERT INTO acl_class (id, name) VALUES (nextval('acl_class_seq'), 'com.abixen.platform.core.model.impl.User');

INSERT INTO permission_acl_class_category (id, acl_class_id, name, title, description) VALUES (nextval('permission_acl_class_category_seq'), 1, 'layout', 'Layout', 'Layout');
INSERT INTO permission_acl_class_category (id, acl_class_id, name, title, description) VALUES (nextval('permission_acl_class_category_seq'), 2, 'module', 'Module', 'Module');
INSERT INTO permission_acl_class_category (id, acl_class_id, name, title, description) VALUES (nextval('permission_acl_class_category_seq'), 3, 'page', 'Page', 'Page');
INSERT INTO permission_acl_class_category (id, acl_class_id, name, title, description) VALUES (nextval('permission_acl_class_category_seq'), 4, 'role', 'Role', 'Role');
INSERT INTO permission_acl_class_category (id, acl_class_id, name, title, description) VALUES (nextval('permission_acl_class_category_seq'), 5, 'user', 'User', 'User');

INSERT INTO permission_general_category (id, name, title, description) VALUES (nextval('permission_general_category_seq'), 'some-category', 'Some Category', 'Some Category Description');

INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to view a page.', 3, 1, 'PAGE_VIEW', 'Page View', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to add a page.', 3, 1, 'PAGE_ADD', 'Page Add', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to edit a page.', 3, 1, 'PAGE_EDIT', 'Page Edit', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to delete a page.', 3, 1, 'PAGE_DELETE', 'Page Delete', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to configure a page.', 3, 1, 'PAGE_CONFIGURATION', 'Page Configuration', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to change permissions to a page.', 3, 1, 'PAGE_PERMISSION', 'Page Permission', NULL, NULL);

INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to view a module.', 2, 1, 'MODULE_VIEW', 'Module View', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to add a module.', 2, 1, 'MODULE_ADD', 'Module Add', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to edit a module.', 2, 1, 'MODULE_EDIT', 'Module Edit', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to delete a module.', 2, 1, 'MODULE_DELETE', 'Module Delete', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to configure a module.', 2, 1, 'MODULE_CONFIGURATION', 'Module Configuration', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to change permissions to a module.', 2, 1, 'MODULE_PERMISSION', 'Module Permission', NULL, NULL);

INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to view a layout.', 1, 1, 'LAYOUT_VIEW', 'Layout View', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to add a alyout.', 1, 1, 'LAYOUT_ADD', 'Layout Add', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to edit a layout.', 1, 1, 'LAYOUT_EDIT', 'Layout Edit', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to delete a layout.', 1, 1, 'LAYOUT_DELETE', 'Layout Delete', NULL, NULL);
INSERT INTO permission (id, created_date, last_modified_date, description, permission_acl_class_category_id, permission_general_category_id, permission_name, title, created_by_id, last_modified_by_id) VALUES (nextval('permission_seq'), NULL, NULL, 'Allows to change permissions to a page.', 1, 1, 'LAYOUT_PERMISSION', 'Layout Permission', NULL, NULL);

INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 2);
--The Role Page Editor gets following permissions:
--Allows to view a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 7);
--Allows to add a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 8);
--Allows to edit a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 9);
--Allows to delete a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 10);
--Allows to configure a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 11);
--Allows to change permissions to a module
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 12);

--Owner
INSERT INTO acl_sid (id, sid_type, sid_id) VALUES (nextval('acl_sid_seq'), 'OWNER', 0);
--Admin
INSERT INTO acl_sid (id, sid_type, sid_id) VALUES (nextval('acl_sid_seq'), 'ROLE', 1);
--User
INSERT INTO acl_sid (id, sid_type, sid_id) VALUES (nextval('acl_sid_seq'), 'ROLE', 2);
--Page Editor
INSERT INTO acl_sid (id, sid_type, sid_id) VALUES (nextval('acl_sid_seq'), 'ROLE', 3);

--Module instance with id equal 1
INSERT INTO acl_object_identity (id, acl_class_id, object_id) VALUES (nextval('acl_object_identity_seq'), 2, 1);
--Page instance with id equal 1
INSERT INTO acl_object_identity (id, acl_class_id, object_id) VALUES (nextval('acl_object_identity_seq'), 3, 1);
--Layout instance with id equal 1
INSERT INTO acl_object_identity (id, acl_class_id, object_id) VALUES (nextval('acl_object_identity_seq'), 1, 1);

--Allows to view a module with id equal 1 for users with role Admin.
INSERT INTO acl_entry (id, permission_id, acl_object_identity_id, acl_sid_id) VALUES (nextval('acl_entry_seq'), 7, 1, 2);
--Allows to add a module with id equal 1 for users with role Admin.
INSERT INTO acl_entry (id, permission_id, acl_object_identity_id, acl_sid_id) VALUES (nextval('acl_entry_seq'), 8, 1, 2);
--Allows to edit a module with id equal 1 for user who is an owner.
INSERT INTO acl_entry (id, permission_id, acl_object_identity_id, acl_sid_id) VALUES (nextval('acl_entry_seq'), 9, 1, 1);

INSERT INTO module_type (id, created_date, last_modified_date, name, description, title, init_url, created_by_id, last_modified_by_id) VALUES (nextval('module_type_seq'), NULL, NULL, 'multi-visualization', 'This is a multi visualization module', 'Multi Visualization', '/application/modules/abixen/multi-visualization/html/index.html', NULL, NULL);
INSERT INTO module_type (id, created_date, last_modified_date, name, description, title, init_url, created_by_id, last_modified_by_id) VALUES (nextval('module_type_seq'), NULL, NULL, 'magic-number', 'This is a magic number module', 'Magic Number', '/application/modules/abixen/magic-number/html/index.html', NULL, NULL);
INSERT INTO module_type (id, created_date, last_modified_date, name, description, title, init_url, created_by_id, last_modified_by_id) VALUES (nextval('module_type_seq'), NULL, NULL, 'kpi-chart', 'This is a KPI chart module', 'KPI Chart', '/application/modules/abixen/kpi-chart/html/index.html', NULL, NULL);

INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 1, '/application/modules/abixen/modules.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 1, '/application/modules/abixen/lib/d3.min.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 1, '/application/modules/abixen/lib/nvd3/nv.d3.min.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 1, '/application/modules/abixen/lib/nvd3/nv.d3.min.css', 'HEADER', 'CSS', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 1, '/application/modules/abixen/lib/angular-nvd3.min.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 2, '/application/modules/abixen/modules.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 3, '/application/modules/abixen/modules.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);
INSERT INTO resource (id, module_type_id, relative_url, resource_location, resource_type, created_date, last_modified_date, created_by_id, last_modified_by_id) VALUES (nextval('resource_seq'), 3, '/application/modules/abixen/lib/round-progress.min.js', 'BODY', 'JAVASCRIPT', NULL, NULL, NULL, NULL);

INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '1 (100)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]}]}', 'layout-icon-1.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (50/50)', '{"rows":[{"columns":[{"styleClass":"col-md-6"},{"styleClass":"col-md-6"}]}]}', 'layout-icon-2.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (33/67)', '{"rows":[{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-8"}]}]}', 'layout-icon-3.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (67/33)', '{"rows":[{"columns":[{"styleClass":"col-md-8"},{"styleClass":"col-md-4"}]}]}', 'layout-icon-4.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '3 (33/34/33)', '{"rows":[{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-4"},{"styleClass":"col-md-4"}]}]}', 'layout-icon-5.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '4 (25/25/25/25)', '{"rows":[{"columns":[{"styleClass":"col-md-3"},{"styleClass":"col-md-3"},{"styleClass":"col-md-3"},{"styleClass":"col-md-3"}]}]}', 'layout-icon-6.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (50/50) - 1 (100)', '{"rows":[{"columns":[{"styleClass":"col-md-6"},{"styleClass":"col-md-6"}]},{"columns":[{"styleClass":"col-md-12"}]}]}', 'layout-icon-7.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '1 (100) - 2 (50/50)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]},{"columns":[{"styleClass":"col-md-6"},{"styleClass":"col-md-6"}]}]}', 'layout-icon-8.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (33/67) - 1 (100)', '{"rows":[{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-8"}]},{"columns":[{"styleClass":"col-md-12"}]}]}', 'layout-icon-9.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '1 (100) - 2 (67/33)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]},{"columns":[{"styleClass":"col-md-8"},{"styleClass":"col-md-4"}]}]}', 'layout-icon-10.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '2 (67/33) - 2 (33/67)', '{"rows":[{"columns":[{"styleClass":"col-md-8"},{"styleClass":"col-md-4"}]},{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-8"}]}]}', 'layout-icon-11.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), ' 1 (100) - 2 (33/67)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]},{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-8"}]}]}', 'layout-icon-12.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '3 (33/34/33) - 1 (100)', '{"rows":[{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-4"},{"styleClass":"col-md-4"}]},{"columns":[{"styleClass":"col-md-12"}]}]}', 'layout-icon-13.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '1 (100)  - 2 (50/50) - 1 (100)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]},{"columns":[{"styleClass":"col-md-6"},{"styleClass":"col-md-6"}]},{"columns":[{"styleClass":"col-md-12"}]}]}', 'layout-icon-14.png');
INSERT INTO layout (id, title, content, icon_file_name) VALUES (nextval('layout_seq'), '1 (100) - 3 (33/34/33)', '{"rows":[{"columns":[{"styleClass":"col-md-12"}]},{"columns":[{"styleClass":"col-md-4"},{"styleClass":"col-md-4"},{"styleClass":"col-md-4"}]}]}', 'layout-icon-15.png');

INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #1', 'dashboard1', 'Consumer behaviour', 3, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #2', 'dashboard2', 'Social media activity', 15, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #3', 'dashboard2', 'Sample page', 1, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #3', 'dashboard2', 'Sales in stores', 1, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #3', 'dashboard2', 'Customers', 1, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #3', 'dashboard2', 'Europe market', 1, 2, 2);
INSERT INTO page (id, created_date, last_modified_date, description, name, title, layout_id, created_by_id, last_modified_by_id) VALUES (nextval('page_seq'), NULL, NULL, 'This is sample dashboard page #3', 'dashboard2', 'Test page', 1, 2, 2);

INSERT INTO module (id, created_date, last_modified_date, description, title, module_type_id, page_id, row_index, column_index, order_index, created_by_id, last_modified_by_id) VALUES (nextval('module_seq'), NULL, NULL, 'This is chart sample instance visualization module', 'Chart sample visualization', 1, 1, 0, 0, 0, 1, 1);
INSERT INTO module (id, created_date, last_modified_date, description, title, module_type_id, page_id, row_index, column_index, order_index, created_by_id, last_modified_by_id) VALUES (nextval('module_seq'), NULL, NULL, 'This is chart sample instance visualization module #2', 'Chart sample visualization', 1, 2, 1, 1, 0, 2, 2);