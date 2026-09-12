# ImmersiveUI Fabric 26.2 非官方移植

## 中文说明

这是 Immersive UI 面向 **Minecraft Java 26.2 + Fabric** 的非官方移植包。

当前移植内容包括：

- 光标悬停物品缩放与漂浮效果
- 拖拽物品旋转与移动反馈
- 稀有物品粒子效果
- 热栏选择框动画
- 附魔台、熔炉、铁砧、锻造台和进度提示动画
- 物品拿起与放回槽位动画
- Mod Menu 设置入口

### 相较原版的改动

- 适配 Minecraft 26.2 的 `GuiGraphicsExtractor`、容器槽位渲染和 Fabric Mixin 结构。
- 修复 26.2 中进度/配方提示使用 `fakeItem` 导致的 `AdvancementToast` 注入兼容问题。
- 新增物品拿起动画：物品从槽位平滑移动到光标位置。
- 新增物品放回动画：物品从光标位置平滑移动回目标槽位。
- 快速拖拽分配堆叠物品时不会错误触发多段放回动画。
- 相同物品的槽位运动效果在放回物品后会平滑衰减，而不是立即停止。
- 新增拿起/放回动画开关与动画时长设置。
- Mod Menu 配置说明在简体中文（`zh_cn`）下显示中文，其他语言保持英文。

本移植包没有加入实验性的物品阴影功能。

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

`build/libs/ImmersiveUI-FABRIC-1.0.9+26.2.jar`

## English

This is an **unofficial Fabric port of Immersive UI for Minecraft Java 26.2**.

Included features:

- Hovered-item scaling and floating animation
- Dragged-item rotation and movement feedback
- Rarity particles
- Animated hotbar selection
- Enchantment-table, furnace, anvil, smithing-table and advancement-toast animations
- Item pickup and return animations
- Mod Menu configuration entry

### Changes compared with the original mod

- Ported the GUI, container-slot rendering, and Fabric Mixin integration to Minecraft 26.2.
- Updated the advancement/recipe toast hook for the 26.2 `fakeItem` rendering path.
- Added smooth slot-to-cursor pickup animation.
- Added smooth cursor-to-slot return animation.
- Prevented quick-craft drag placement from producing incorrect return animations.
- Added smooth fade-out for matching-item slot motion after the carried stack is released.
- Added configuration toggles and duration control for pickup and return animations.
- Configuration descriptions are localized to Simplified Chinese (`zh_cn`) only; other languages remain English.

The port does not include the experimental item-shadow feature.

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

`build/libs/ImmersiveUI-FABRIC-1.0.9+26.2.jar`
