# 智能点餐系统

一个基于 Spring Boot 的智能点餐系统，提供商品管理、订单管理、餐桌管理等功能。

## 功能特性

### 商品管理
- 商品分类管理
- 商品信息管理
- 商品规格管理
- 商品搜索功能

### 订单管理
- 订单创建（支持多商品、多规格）
- 订单状态管理（已下单、已支付、已完成）
- 订单查询（按ID、订单号、用户ID、桌号）
- 智能订单号生成（32位，包含时间戳和校验位）

### 餐桌管理
- 餐桌信息管理
- 餐桌状态管理
- 餐桌二维码生成
- 餐桌订单关联

## 技术栈

- 后端框架：Spring Boot
- 数据库：MySQL
- 缓存：Redis
- 构建工具：Maven
- 版本控制：Git

## 项目结构

```
com.example.dining
├── common                // 通用模块
│   ├── enums            // 枚举类
│   ├── exception        // 异常处理
│   ├── model            // 通用模型
│   └── util             // 工具类
├── controller           // 控制器层
├── service              // 服务层
│   └── impl            // 服务实现
├── mapper              // 数据访问层
├── entity              // 实体类
└── dto                 // 数据传输对象
```

## API文档

### 商品接口

#### 获取所有商品
```
GET /api/items
```

#### 根据分类获取商品
```
GET /api/items/category/{categoryId}
```

#### 获取单个商品
```
GET /api/items/{id}
```

#### 搜索商品
```
GET /api/items/search?keyword={keyword}
```

### 订单接口

#### 创建订单
```
POST /api/orders
Content-Type: application/json

{
    "userId": "用户ID",
    "tableNo": "桌号",
    "totalAmount": 100.00,
    "orderItems": [
        {
            "itemId": "商品ID",
            "specId": 1,
            "quantity": 2,
            "price": 50.00,
            "amount": 100.00
        }
    ],
    "remark": "备注"
}
```

#### 更新订单状态
```
PUT /api/orders/{id}/status?status={status}
```

#### 获取订单详情
```
GET /api/orders/{id}
```

#### 根据订单号查询
```
GET /api/orders/orderNo/{orderNo}
```

#### 查询用户订单
```
GET /api/orders/user/{userId}
```

#### 查询餐桌订单
```
GET /api/orders/table/{tableNo}
```

### 餐桌接口

#### 创建餐桌
```
POST /api/tables
Content-Type: application/json

{
    "tableNo": "桌号",
    "capacity": 4,
    "status": 0
}
```

#### 更新餐桌状态
```
PUT /api/tables/{id}/status?status={status}
```

#### 更新餐桌二维码
```
PUT /api/tables/{id}/qrcode
Content-Type: application/json

{
    "qrCode": "二维码URL"
}
```

#### 获取餐桌信息
```
GET /api/tables/{id}
```

#### 根据桌号查询
```
GET /api/tables/tableNo/{tableNo}
```

#### 查询所有餐桌
```
GET /api/tables
```

#### 根据状态查询餐桌
```
GET /api/tables/status/{status}
```

## 开发环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

## 快速开始

1. 克隆项目
```bash
git clone https://github.com/yourusername/dining.git
```

2. 配置数据库
- 创建MySQL数据库
- 修改application.yml中的数据库配置

3. 运行项目
```bash
mvn spring-boot:run
```

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

[MIT License](LICENSE) 