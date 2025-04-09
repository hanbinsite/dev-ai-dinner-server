# 智能点餐系统后端服务

这是一个基于 Spring Boot 的智能点餐系统后端服务，提供商品、分类、规格等核心功能的管理接口。

## 技术栈

- **核心框架**：Spring Boot 3.x
- **响应式编程**：Project Reactor
- **数据库**：MySQL
- **缓存**：Redis
- **ORM框架**：MyBatis
- **API文档**：Swagger/OpenAPI
- **构建工具**：Maven

## 项目结构

```
src/main/java/com/example/dining/
├── common/           # 通用组件
│   ├── BusinessException.java    # 业务异常
│   ├── GlobalExceptionHandler.java # 全局异常处理
│   ├── Result.java              # 统一返回结果
│   └── ResultCode.java          # 返回码枚举
├── config/           # 配置类
│   └── RedisConfig.java         # Redis配置
├── controller/       # 控制器
├── entity/           # 实体类
│   ├── Category.java           # 分类实体
│   ├── Item.java               # 商品实体
│   ├── ItemSpec.java           # 商品规格关联
│   ├── ItemSpecOption.java     # 商品规格选项
│   ├── Spec.java               # 规格实体
│   └── SpecOption.java         # 规格选项
├── mapper/           # MyBatis映射接口
├── service/          # 服务接口
└── service/impl/     # 服务实现
```

## 核心功能

1. **商品管理**
   - 商品基本信息维护
   - 商品分类管理
   - 商品规格管理
   - 商品与规格关联

2. **缓存管理**
   - 使用 Redis 缓存商品分类数据
   - 自动缓存更新机制
   - 缓存一致性保证

3. **统一异常处理**
   - 业务异常处理
   - 参数校验异常处理
   - 全局异常拦截

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 配置说明

1. 数据库配置
   - 创建数据库：`dining`
   - 配置数据源：`application.yml`

2. Redis配置
   - 默认端口：6379
   - 配置缓存：`RedisConfig.java`

### 启动服务

```bash
mvn spring-boot:run
```

## API文档

启动服务后访问：`http://localhost:8080/swagger-ui.html`

## 开发规范

1. **代码规范**
   - 遵循阿里巴巴Java开发手册
   - 使用Lombok简化代码
   - 统一异常处理

2. **命名规范**
   - 类名：大驼峰
   - 方法名：小驼峰
   - 变量名：小驼峰
   - 常量名：全大写

3. **注释规范**
   - 类注释：说明类的作用
   - 方法注释：说明方法的功能、参数和返回值
   - 关键代码注释：说明复杂逻辑

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

[MIT License](LICENSE) 