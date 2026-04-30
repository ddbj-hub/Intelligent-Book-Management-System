# 智能图书管理系统 (Intelligent Book Management System)

一个基于 **JavaSE** 的 C/S 架构图书管理系统，支持图书增删改查、借阅归还、数据持久化、多线程后台任务、多客户端远程访问及反射动态权限控制。

---

## 📋 功能特性

- **图书管理**：添加、删除、修改、查询图书信息
- **借阅管理**：借阅、归还图书，自动跟踪借阅状态
- **数据持久化**：图书数据保存至本地文件，支持启动自动加载和退出自动保存
- **后台任务**：系统时钟每 5 秒显示，数据每 30 秒自动保存
- **多客户端支持**：基于 TCP 的 C/S 架构，多个客户端可同时连接操作
- **反射权限控制**：管理员可通过反射动态调用高级功能（如清空图书、打印日志）
- **线程安全**：`synchronized` 锁保护共享数据，防止并发冲突

---

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 基础语法 | Java 基础、数组、字符串、流程控制 |
| 面向对象 | 封装、继承、多态、内部类 |
| 集合框架 | `ArrayList`、`ConcurrentHashMap` |
| IO 流 | `BufferedReader`/`BufferedWriter`、`FileReader`/`FileWriter`、文件持久化 |
| 多线程 | `Thread`、`Runnable`、`synchronized`、`volatile` |
| 网络编程 | `ServerSocket`、`Socket`、`PrintWriter`、自定义通信协议 |
| 反射 | `Class.forName`、`Method.invoke`、动态权限控制 |

---

## 📁 项目结构

```
IntelligentBookManagementSystem/
├── src/
│   └── com/
│       └── AVALONLibrary/
│           ├── Main.java                    # 单机版入口 (V4及之前)
│           ├── model/
│           │   └── Book.java                # 图书实体类
│           ├── util/
│           │   └── FileUtil.java            # 文件读写工具类
│           ├── server/
│           │   ├── Server.java              # 服务端主类（启动入口）
│           │   └── ClientHandler.java       # 客户端处理线程
│           ├── client/
│           │   └── Client.java              # 客户端主类
│           └── admin/
│               └── AdminUtil.java           # 管理员工具类（反射调用）
├── Data/
│   └── books.txt                            # 图书数据文件
└── README.md
```

---

## 🚀 快速开始

### 环境要求
- **JDK 21+**（推荐 JDK 25）
- 任意操作系统（Windows / macOS / Linux）

### 编译运行

1. **编译所有源文件**
```bash
cd src
javac -encoding UTF-8 com/AVALONLibrary/**/*.java
```

2. **启动服务端**
```bash
java com.AVALONLibrary.server.Server
```

3. **启动客户端**（可开多个窗口模拟多用户）
```bash
java com.AVALONLibrary.client.Client
```

4. **根据菜单提示操作**

---

## 📖 使用说明

### 客户端菜单

| 选项 | 功能 | 通信协议（请求格式） |
|------|------|---------------------|
| 1 | 退出系统 | `EXIT` |
| 2 | 显示所有图书 | `LIST` |
| 3 | 添加图书 | `ADD`|`书名`|`作者`|`ISBN` |
| 4 | 删除图书 | `REMOVE`|`ISBN` |
| 5 | 修改图书 | `REVISE`|`ISBN`|`新书名`|`新作者`|`新ISBN` |
| 6 | 查询图书 | `QUERY`|`关键字` |
| 7 | 借阅图书 | `BORROW`|`ISBN` |
| 8 | 归还图书 | `RETURN`|`ISBN` |
| 9 | 执行远程命令 | `REFLECT`|`类名`|`方法名` |

### 管理员权限

在 `AdminUtil.java` 中修改 `isAdmin` 字段：

```java
public static boolean isAdmin = true;  // true = 管理员, false = 普通用户
```

- 当 `isAdmin = false` 时，选项 9 会返回 `ERROR|权限不足`
- 管理员可反射调用 `AdminUtil` 中的方法（如 `deleteAllBooks`、`printSystemLog`）

---

## 🧩 版本演进

| 版本 | 主要新增内容 | 技术点 |
|------|-------------|--------|
| **V1** | 控制台菜单、硬编码图书数据 | 数组、流程控制 |
| **V2** | 面向对象设计、增删改查、借阅归还 | 封装、集合、构造方法 |
| **V3** | 文件持久化，重启数据不丢失 | IO 流、缓冲读写 |
| **V4** | 多线程后台时钟和自动保存 | 线程创建、`synchronized` |
| **V5** | C/S 架构，多客户端远程访问 | `Socket`通信、自定义协议 |
| **V6** | 反射动态调用、管理员权限控制 | `Class.forName`、`Method.invoke` |

---

## 📝 作者

**AVALON(肖jw)**  
*2026-04-29*

---

## 📜 许可证

本项目仅用于学习交流，不涉及商业用途。
