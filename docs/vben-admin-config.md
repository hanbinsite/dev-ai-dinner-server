# Vue Vben Admin 配置文档

## 1. 环境配置

### 1.1 开发环境
```bash
# 安装依赖
pnpm install

# 启动开发服务器
pnpm dev

# 构建生产环境
pnpm build
```

### 1.2 环境变量
```env
# 开发环境
VITE_GLOB_APP_TITLE=点餐系统
VITE_GLOB_API_URL=/api
VITE_GLOB_UPLOAD_URL=/api/upload
VITE_GLOB_APP_SHORT_NAME=dining
```

## 2. 路由配置

### 2.1 路由结构
```typescript
// src/router/routes/modules/
export default {
  path: '/system',
  name: 'System',
  component: 'LAYOUT',
  meta: {
    title: '系统管理',
    icon: 'ion:settings-outline',
    orderNo: 2,
  },
  children: [
    {
      path: 'user',
      name: 'UserManagement',
      meta: {
        title: '用户管理',
        icon: 'ion:people-outline',
      },
      component: 'system/user/index',
    },
    {
      path: 'role',
      name: 'RoleManagement',
      meta: {
        title: '角色管理',
        icon: 'ion:key-outline',
      },
      component: 'system/role/index',
    },
    {
      path: 'menu',
      name: 'MenuManagement',
      meta: {
        title: '菜单管理',
        icon: 'ion:menu-outline',
      },
      component: 'system/menu/index',
    },
  ],
};
```

### 2.2 权限配置
```typescript
// src/router/guard/permissionGuard.ts
export function createPermissionGuard(router: Router) {
  router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const permissionStore = usePermissionStore();
    
    // 判断是否需要登录
    if (to.meta.requiresAuth && !userStore.getToken) {
      next({
        path: '/login',
        query: { redirect: to.fullPath },
      });
      return;
    }
    
    // 判断是否有权限
    if (to.meta.permission) {
      const hasPermission = permissionStore.hasPermission(to.meta.permission);
      if (!hasPermission) {
        next('/403');
        return;
      }
    }
    
    next();
  });
}
```

## 3. 权限配置

### 3.1 权限指令
```vue
<template>
  <a-button v-auth="'user:add'">新增用户</a-button>
  <a-button v-auth="'user:edit'">编辑用户</a-button>
  <a-button v-auth="'user:delete'">删除用户</a-button>
</template>
```

### 3.2 权限函数
```typescript
// src/hooks/web/usePermission.ts
export function usePermission() {
  const permissionStore = usePermissionStore();
  
  return {
    hasPermission: (permission: string) => {
      return permissionStore.hasPermission(permission);
    },
    hasRole: (role: string) => {
      return permissionStore.hasRole(role);
    },
  };
}
```

## 4. 菜单配置

### 4.1 菜单结构
```typescript
// src/store/modules/permission.ts
export interface Menu {
  id: number;
  parentId: number | null;
  name: string;
  path: string;
  component?: string;
  redirect?: string;
  meta: {
    title: string;
    icon?: string;
    hideMenu?: boolean;
    hideChildrenInMenu?: boolean;
    currentActiveMenu?: string;
    ignoreKeepAlive?: boolean;
    orderNo?: number;
    affix?: boolean;
    frameSrc?: string;
  };
  children?: Menu[];
}
```

### 4.2 菜单生成
```typescript
// src/store/modules/permission.ts
export function generateRoutes(menus: Menu[]): RouteRecordRaw[] {
  return menus.map((menu) => {
    const route: RouteRecordRaw = {
      path: menu.path,
      name: menu.name,
      component: menu.component ? getComponent(menu.component) : undefined,
      redirect: menu.redirect,
      meta: {
        ...menu.meta,
        hideMenu: menu.meta.hideMenu,
        hideChildrenInMenu: menu.meta.hideChildrenInMenu,
        currentActiveMenu: menu.meta.currentActiveMenu,
        ignoreKeepAlive: menu.meta.ignoreKeepAlive,
        orderNo: menu.meta.orderNo,
        affix: menu.meta.affix,
        frameSrc: menu.meta.frameSrc,
      },
    };
    
    if (menu.children) {
      route.children = generateRoutes(menu.children);
    }
    
    return route;
  });
}
```

## 5. 接口配置

### 5.1 接口请求
```typescript
// src/api/sys/user.ts
export function login(params: LoginParams) {
  return defHttp.post<LoginResultModel>({
    url: '/auth/login',
    params,
  });
}

export function getUserInfo() {
  return defHttp.get<UserInfo>({
    url: '/user/info',
  });
}

export function getPermCode() {
  return defHttp.get<string[]>({
    url: '/user/permissions',
  });
}
```

### 5.2 接口拦截器
```typescript
// src/utils/http/axios/index.ts
const http = createAxios({
  baseURL: '/api',
  timeout: 10 * 1000,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
  authenticationScheme: 'Bearer',
  requestOptions: {
    joinTime: true,
    isTransformResponse: true,
    isReturnNativeResponse: false,
    joinParamsToUrl: false,
    formatDate: true,
    errorMessageMode: 'message',
    apiUrl: '/api',
  },
});
```

## 6. 状态管理

### 6.1 用户状态
```typescript
// src/store/modules/user.ts
export const useUserStore = defineStore({
  id: 'app-user',
  state: (): UserState => ({
    token: undefined,
    userInfo: null,
    roleList: [],
    permissionList: [],
  }),
  getters: {
    getToken(): string {
      return this.token || '';
    },
    getUserInfo(): UserInfo | null {
      return this.userInfo;
    },
    getRoleList(): string[] {
      return this.roleList;
    },
    getPermissionList(): string[] {
      return this.permissionList;
    },
  },
  actions: {
    setToken(info: string | undefined) {
      this.token = info;
    },
    setUserInfo(info: UserInfo | null) {
      this.userInfo = info;
    },
    setRoleList(roleList: string[]) {
      this.roleList = roleList;
    },
    setPermissionList(permissionList: string[]) {
      this.permissionList = permissionList;
    },
    async login(params: LoginParams) {
      try {
        const { token } = await login(params);
        this.setToken(token);
        return this.afterLogin();
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async afterLogin() {
      try {
        const userInfo = await getUserInfo();
        const permissionList = await getPermCode();
        this.setUserInfo(userInfo);
        this.setPermissionList(permissionList);
        return userInfo;
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async logout() {
      this.setToken(undefined);
      this.setUserInfo(null);
      this.setRoleList([]);
      this.setPermissionList([]);
    },
  },
});
```

### 6.2 权限状态
```typescript
// src/store/modules/permission.ts
export const usePermissionStore = defineStore({
  id: 'app-permission',
  state: (): PermissionState => ({
    permissionList: [],
    roleList: [],
  }),
  getters: {
    getPermissionList(): string[] {
      return this.permissionList;
    },
    getRoleList(): string[] {
      return this.roleList;
    },
  },
  actions: {
    setPermissionList(permissionList: string[]) {
      this.permissionList = permissionList;
    },
    setRoleList(roleList: string[]) {
      this.roleList = roleList;
    },
    hasPermission(permission: string): boolean {
      return this.permissionList.includes(permission);
    },
    hasRole(role: string): boolean {
      return this.roleList.includes(role);
    },
  },
});
```

## 7. 主题配置

### 7.1 主题变量
```scss
// src/styles/var.scss
:root {
  // 主色
  --primary-color: #1890ff;
  --success-color: #52c41a;
  --warning-color: #faad14;
  --error-color: #f5222d;
  
  // 文字
  --text-color: rgba(0, 0, 0, 0.85);
  --text-color-secondary: rgba(0, 0, 0, 0.45);
  
  // 背景
  --background-color-light: #f5f5f5;
  --background-color-base: #f0f2f5;
  
  // 边框
  --border-color-base: #d9d9d9;
  --border-color-split: #f0f0f0;
}
```

### 7.2 主题切换
```typescript
// src/hooks/web/useTheme.ts
export function useTheme() {
  const themeStore = useThemeStore();
  
  return {
    setTheme: (theme: ThemeEnum) => {
      themeStore.setTheme(theme);
    },
    getTheme: () => {
      return themeStore.getTheme;
    },
  };
}
```

## 8. 国际化配置

### 8.1 语言包
```typescript
// src/locales/lang/zh_CN.ts
export default {
  common: {
    add: '新增',
    edit: '编辑',
    delete: '删除',
    save: '保存',
    cancel: '取消',
    search: '搜索',
    reset: '重置',
    action: '操作',
  },
  login: {
    title: '登录',
    username: '用户名',
    password: '密码',
    captcha: '验证码',
    login: '登录',
    logout: '退出登录',
  },
  system: {
    user: {
      title: '用户管理',
      username: '用户名',
      nickname: '昵称',
      role: '角色',
      status: '状态',
    },
    role: {
      title: '角色管理',
      name: '角色名称',
      code: '角色编码',
      permission: '权限',
    },
    menu: {
      title: '菜单管理',
      name: '菜单名称',
      path: '路由路径',
      component: '组件路径',
      icon: '图标',
    },
  },
};
```

### 8.2 语言切换
```typescript
// src/hooks/web/useLocale.ts
export function useLocale() {
  const localeStore = useLocaleStore();
  
  return {
    setLocale: (locale: LocaleType) => {
      localeStore.setLocale(locale);
    },
    getLocale: () => {
      return localeStore.getLocale;
    },
  };
}
``` 