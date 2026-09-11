# ImmersiveUI Fabric 26.2 非官方移植

## 中文说明

这是 Immersive UI 面向 **Minecraft Java 26.2 + Fabric** 的非官方移植包。

当前移植内容包括：

- 光标悬停物品缩放与漂浮效果
- 拖拽物品旋转与移动反馈
- 稀有物品粒子效果
- 热栏选择框动画
- 附魔台、熔炉、铁砧、锻造台和进度提示动画
- Mod Menu 设置入口

本项目与 Mojang、Fabric、Modrinth 或 Octo-Studios 无隶属关系。原始 Immersive UI 项目的名称、代码和素材权利归原作者所有。

### 依赖下载

- [Fabric Loader](https://fabricmc.net/use/installer/)
- [Fabric API 26.2](https://modrinth.com/mod/fabric-api)
- [ShatterLib / OctoLib 26.2 Fabric](https://modrinth.com/mod/shatterbyte-lib/versions)
- [Mod Menu 26.2 Fabric，可选但设置入口需要](https://modrinth.com/mod/modmenu)

### 构建

需要 Java 25：

```powershell
./gradlew.bat build --no-daemon
```

构建结果：

`build/libs/ImmersiveUI-FABRIC-0.3.7-beta.1+26.2.jar`

## English

This is an **unofficial Fabric port of Immersive UI for Minecraft Java 26.2**.

Included features:

- Hovered-item scaling and floating animation
- Dragged-item rotation and movement feedback
- Rarity particles
- Animated hotbar selection
- Enchantment-table, furnace, anvil, smithing-table and advancement-toast animations
- Mod Menu configuration entry

This project is not affiliated with Mojang, Fabric, Modrinth, or Octo-Studios. The original Immersive UI name, code, and assets remain attributed to their original authors.

### Dependencies

- [Fabric Loader](https://fabricmc.net/use/installer/)
- [Fabric API 26.2](https://modrinth.com/mod/fabric-api)
- [ShatterLib / OctoLib 26.2 Fabric](https://modrinth.com/mod/shatterbyte-lib/versions)
- [Mod Menu 26.2 Fabric, optional but required for the config button](https://modrinth.com/mod/modmenu)

### Build

Java 25 is required:

```powershell
./gradlew.bat build --no-daemon
```

Output:

`build/libs/ImmersiveUI-FABRIC-0.3.7-beta.1+26.2.jar`
