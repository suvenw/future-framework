miniprogram/
├── pages/                    # 页面文件
│   ├── index/               # 首页
│   │   ├── index.js         # 页面逻辑
│   │   ├── index.json       # 页面配置
│   │   ├── index.wxml       # 页面结构
│   │   └── index.wxss       # 页面样式
│   └── logs/
│       └── logs.js
├── components/              # 自定义组件
│   ├── nav-bar/
│   │   ├── nav-bar.js
│   │   ├── nav-bar.json
│   │   ├── nav-bar.wxml
│   │   └── nav-bar.wxss
│   └── ...
├── utils/                   # 工具类
│   ├── util.js             # 通用工具函数
│   ├── request.js          # 网络请求封装
│   └── storage.js          # 存储管理
├── styles/                  # 全局样式
│   ├── theme.wxss          # 主题变量
│   └── common.wxss         # 通用样式
├── assets/                  # 静态资源
│   ├── images/             # 图片资源
│   └── icons/              # 图标资源
├── app.js                  # 小程序入口
├── app.json               # 全局配置
├── app.wxss              # 全局样式
└── project.config.json   # 项目配置