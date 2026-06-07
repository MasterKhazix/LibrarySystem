-- =============================================
-- 为现有 library_db.users 表补充联系方式约束
-- 适用于已经执行过 init.sql、不想重建数据库的情况
-- =============================================

USE library_db;

-- 先查看是否已有不合法数据。若查出记录，请先修正或置空后再添加约束。
SELECT id, username, phone
FROM users
WHERE phone IS NOT NULL
  AND phone NOT REGEXP '^[0-9]{11}$';

SELECT id, username, email
FROM users
WHERE email IS NOT NULL
  AND email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$';

-- 可选：自动清理不合法联系方式，避免添加 CHECK 约束失败。
UPDATE users
SET phone = NULL
WHERE phone IS NOT NULL
  AND phone NOT REGEXP '^[0-9]{11}$';

UPDATE users
SET email = NULL
WHERE email IS NOT NULL
  AND email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$';

-- 添加数据库层约束。若提示约束已存在，说明之前已经添加过，可忽略。
ALTER TABLE users
    MODIFY phone VARCHAR(11) DEFAULT NULL,
    MODIFY email VARCHAR(100) DEFAULT NULL,
    ADD CONSTRAINT chk_users_phone CHECK (phone IS NULL OR phone REGEXP '^[0-9]{11}$'),
    ADD CONSTRAINT chk_users_email CHECK (email IS NULL OR email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$');
