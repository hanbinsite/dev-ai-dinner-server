-- 修改管理员表，添加密码盐值字段
ALTER TABLE admin 
ADD COLUMN salt VARCHAR(100) NOT NULL COMMENT '密码盐值' AFTER password,
MODIFY COLUMN password VARCHAR(100) NOT NULL COMMENT '加密后的密码'; 