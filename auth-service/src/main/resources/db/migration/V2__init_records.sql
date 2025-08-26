-- ============================================
-- Reset all tables
-- ============================================
TRUNCATE TABLE
    role,
    users,
    user_roles,
    role_permissions
    RESTART IDENTITY CASCADE;
-- ============================================
-- Init records
-- ============================================
-- Roles

INSERT INTO public.role (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');


-- Users
-- admin -> admin@123
-- all users --> user@123
INSERT INTO public.users (id, is_active, created_at, modified_at, email, password)
VALUES (1, TRUE, NOW(), null, 'admin@gmail.com',
        '$2a$10$ifpkKdMm/LISgybFvnAwUuIgBSp3BLNVQK9DV5qu1Llppo5ZWHQzC'),
       (2, TRUE, NOW(), null, 'user1@gmail.com',
        '$2a$10$hD9wZqIzD0lN6SPZ6OQK7eI9QCuWH7CBFcXKNgmrJ5YQHZ3UEu5lS'),
       (3, TRUE, NOW(), null, 'user2@gmail.com',
        '$2a$10$hD9wZqIzD0lN6SPZ6OQK7eI9QCuWH7CBFcXKNgmrJ5YQHZ3UEu5lS'),
       (4, FALSE, NOW(), null, 'user@disable.com',
        '$2a$10$hD9wZqIzD0lN6SPZ6OQK7eI9QCuWH7CBFcXKNgmrJ5YQHZ3UEu5lS');


-- Role Permissions
INSERT INTO public.role_permissions (role_id, permissions)
VALUES (1, 'ADMIN_CREATE'),
       (1, 'ADMIN_READ'),
       (1, 'ADMIN_UPDATE'),
       (1, 'ADMIN_DELETE'),
       (2, 'USER_CREATE'),
       (2, 'USER_READ'),
       (2, 'USER_UPDATE'),
       (2, 'USER_DELETE');


-- User Roles
INSERT INTO public.user_roles (role_id, user_id)
VALUES (1, 1), -- admin@gmail.com is ADMIN
       (2, 1), -- admin@gmail.com is also USER
       (2, 2), -- user1@gmail.com is USER
       (2, 3), -- user2@gmail.com is USER
       (2, 4); -- user@disable.com is USER