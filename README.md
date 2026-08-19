# 🎒 InventoryMaster — Ultimate Inventory QoL Mod & Server Plugin

<div align="center">
  <img src="assets/banner.png" alt="InventoryMaster Banner" width="100%" />

  <p>
    <a href="https://modrinth.com/mod/inventorymaster"><img src="https://img.shields.io/badge/Modrinth-Available%20Now-00AF5C?style=for-the-badge&logo=modrinth" alt="Modrinth" /></a>
    <a href="https://www.curseforge.com/projects/1659668"><img src="https://img.shields.io/badge/CurseForge-Available%20Now-F16436?style=for-the-badge&logo=curseforge" alt="CurseForge" /></a>
    <a href="https://github.com/Krylo-60/InventoryMaster"><img src="https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github" alt="GitHub" /></a>
    <a href="https://krishivstudios.github.io"><img src="https://img.shields.io/badge/Website-Krishiv%20Studios-00f2ff?style=for-the-badge&logo=googlechrome" alt="Website" /></a>
    <a href="https://discord.gg/2hSXQKHvvX"><img src="https://img.shields.io/badge/Discord-Join%20Community-5865f2?style=for-the-badge&logo=discord" alt="Discord" /></a>
  </p>
</div>

---

**The ultimate 1-click inventory & chest management, quick stacking, auto-refill, and tool durability protection mod & server plugin for Minecraft (1.20 – 26.2)!**

---

## 🎮 Compatibility & Supported Platforms

| Platform / Loader | Supported Versions | Notes |
| :--- | :---: | :--- |
| 🟢 **Fabric & Quilt** | **1.20 – 26.2** | Full Client UI & Keybinds |
| 🟠 **NeoForge & Forge** | **1.20 – 26.2** | Full Client UI & Keybinds |
| 📜 **Paper, Purpur, Spigot, Folia** | **1.20 – 1.21.x** | Server Plugin (`/sort`, `/chestsort`) |

---

## ⚡ Features

### 🔄 1-Click Smart Inventory & Chest Sorting
- **Smart Category Sorting**: Groups your items logically by Combat, Tools, Armor, Minerals & Valuables, Food, Redstone, Building Blocks, and Misc.
- **Merge Incomplete Stacks**: Automatically combines partial stacks into neat full 64 stacks.
- **Multiple Triggers**:
  - Click the sleek **`🔄`** button in the top-right of your inventory or container.
  - Press **`R`** key anytime.
  - **Middle-Click (Mouse Button 3)** in any empty container slot.
  - Server command: `/sort` or `/chestsort`.

### 📥 1-Click Quick Stack / Quick Deposit
- Inside any chest, double chest, barrel, or shulker box, click the **`📥` (Quick Stack)** button.
- Automatically transfers all matching items from your inventory into existing chest stacks in one millisecond!

### 🔄 Auto-Refill & Tool Replacer
- When building and your block stack reaches 0, the next stack from your inventory is automatically placed into your hand.
- When an axe, pickaxe, or sword breaks, an identical replacement is pulled directly from your inventory.

### 🛡️ Auto-Totem Restock
- Automatically equips a new **Totem of Undying** to your off-hand slot when popped in combat.

### ⚠️ Low Durability Critical Warning Alert
- Plays a warning chime and displays a red HUD toast alert when your active tool or Elytra reaches critical health ($\le 10\%$ durability or $\le 15$ hits left) so you never accidentally break valuable gear!

---

## ⌨️ Controls & Commands

| Trigger | Context | Description |
| :--- | :--- | :--- |
| **`R`** | Client | Instantly sorts items by category and merges stacks |
| **`Middle-Click`** | Client | Middle-click anywhere in container to sort |
| **`🔄 Button`** | Client | Click UI button in top-right of container |
| **`📥 Button`** | Client | Quick-deposit matching items into chest |
| **`/sort`** | Server Plugin | Sorts player inventory via Paper command |
| **`/chestsort`** | Server Plugin | Sorts the targeted chest or container |

---

## 📜 Authors & License
- **Creator & Maintainer**: [Krylo_plays](https://github.com/Krylo-60)
- **Organization**: [Krishiv Studios](https://github.com/KrishivStudios)
- **License**: [GNU General Public License v3.0 (GPL-3.0)](LICENSE)
