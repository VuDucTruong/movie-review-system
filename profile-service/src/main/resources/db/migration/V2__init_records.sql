
-- ============================================
-- Reset all tables
-- ============================================
TRUNCATE TABLE
    profile
    RESTART IDENTITY CASCADE;


INSERT INTO public.profile (user_id, created_at, modified_at, avatar_url, bio, display_name, email)
VALUES (1, now(), null, 'https://example.com/avatars/user1.png', 'Love watching action movies',
        'John Doe', 'admin@gmail.com'),
       (2, now(), null, 'https://example.com/avatars/user2.png', 'Comedy is life!', 'Jane Smith',
        'kococu147@gmail.com'),
       (3, now(), null, 'https://example.com/avatars/user3.png', 'Drama enthusiast',
        'Michael Johnson', 'user2@gmail.com'),
       (4, now(), null, 'https://example.com/avatars/user4.png', 'Horror fan forever',
        'Emily Davis', 'user@disable.com');
