# MiConvert Image Compressor & Optimizer — JetBrains Plugin

<p align="center">
  <img src="src/main/resources/META-INF/pluginIcon.svg" alt="MiConvert Plugin Icon" width="80" height="80"/>
</p>

<p align="center">
  <strong>Compress and optimize images (PNG, JPG, WebP) directly within your IDE with a single click.</strong><br>
  Instantly reduce project size. Powered by <a href="https://miconvert.com">MiConvert.com</a>
</p>

<p align="center">
  <a href="https://miconvert.com"><img src="https://img.shields.io/badge/Powered%20by-MiConvert.com-blue?style=for-the-badge" alt="Powered by MiConvert.com"/></a>
  <a href="https://huuhuybn.github.io/MiConvert-Image-Compressor-Optimizer/"><img src="https://img.shields.io/badge/🌐_Landing_Page-Visit-blueviolet?style=for-the-badge" alt="Landing Page"/></a>
  <img src="https://img.shields.io/badge/Platform-IntelliJ%20%7C%20WebStorm%20%7C%20Android%20Studio-orange?style=for-the-badge" alt="Platforms"/>
  <img src="https://img.shields.io/badge/Version-1.0.0-green?style=for-the-badge" alt="Version"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="License"/>
</p>

<p align="center">
  📌 <strong><a href="https://huuhuybn.github.io/MiConvert-Image-Compressor-Optimizer/">View Landing Page →</a></strong>
</p>

---

## 🚀 The Problem

Large image files **bloat your repository**, slow down builds, and waste bandwidth. But leaving your IDE to compress them breaks your coding flow.

**MiConvert solves this.** Right-click → Compress → Done. Zero context switching.

---

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| ⚡ **One-Click Compression** | Right-click any image file (or folder) and select "Compress Image" |
| 🧠 **Smart Engine** | Automatically compresses smaller files (<2MB) seamlessly in the background |
| 🌐 **Deep Web Integration** | Intelligently redirects heavy files (>2MB) to the powerful [MiConvert.com](https://miconvert.com) web engine |
| 📁 **Batch Processing** | Compress all images in a folder with a single click |
| 🎨 **Multi-Format Support** | Works flawlessly with **JPG**, **PNG**, and **WebP** |
| ⚙️ **Customizable** | Choose to overwrite originals or create `_min` suffix copies |
| 🔔 **Native Notifications** | Success/error balloons with compression stats (KB saved, %) |

---

## 📦 Installation

### From JetBrains Marketplace
1. Open your JetBrains IDE (IntelliJ IDEA, WebStorm, Android Studio, etc.)
2. Go to **File → Settings → Plugins → Marketplace**
3. Search for **"MiConvert Image Compressor"**
4. Click **Install** → Restart IDE

### From Disk
1. Download the latest release ZIP from [Releases](https://github.com/huuhuybn/MiConvert-Image-Compressor-Optimizer/releases)
2. Go to **File → Settings → Plugins → ⚙️ → Install Plugin from Disk**
3. Select the downloaded ZIP file → Restart IDE

---

## 🎯 How to Use

### Compress a Single Image
1. Right-click any image file (`.jpg`, `.png`, `.webp`) in the **Project Tree**
2. Select **MiConvert → ⚡ Compress Image...**
3. For files **< 2MB**: Compression happens in the background with a progress bar
4. For files **> 2MB**: A dialog opens offering to process via [MiConvert.com](https://miconvert.com/en/compress-tools)

### Compress All Images in a Folder
1. Right-click any folder in the **Project Tree**
2. Select **MiConvert → 📁 Compress All Images in Folder...**
3. All supported images are compressed automatically

### Configure Settings
1. Go to **File → Settings → Tools → MiConvert**
2. Choose your preferred output mode:
   - **Overwrite original** — replaces the file in place
   - **Create \_min suffix** — creates `photo_min.jpg` alongside the original
3. Adjust **JPEG quality** (1–100, default: 80)

---

## 🛠️ Build from Source

```bash
# Clone the repository
git clone https://github.com/huuhuybn/MiConvert-Image-Compressor-Optimizer.git
cd MiConvert-Image-Compressor-Optimizer

# Build the plugin
./gradlew buildPlugin

# Output: build/distributions/miconvert-image-compressor-1.0.0.zip

# Run a sandboxed IDE for testing
./gradlew runIde
```

**Requirements:**
- JDK 17+
- Gradle 8.10+ (included via wrapper)

---

## 🏗️ Project Structure

```
src/main/kotlin/com/miconvert/imagecompressor/
├── actions/
│   ├── CompressImageAction.kt      ← Right-click single image
│   └── CompressFolderAction.kt     ← Right-click folder
├── engine/
│   ├── ImageCompressor.kt          ← JPEG/PNG/WebP compression engine
│   ├── ImageCompressionTask.kt     ← Background task with progress bar
│   └── CompressionResult.kt        ← Result data model
├── notifications/
│   └── MiConvertNotifier.kt        ← IDE notification balloons
├── settings/
│   ├── MiConvertSettings.kt        ← Persistent preferences
│   └── MiConvertSettingsConfigurable.kt ← Settings UI panel
└── ui/
    └── LargeFileDialog.kt          ← Browser redirect dialog
```

---

## 🤝 Compatible IDEs

- IntelliJ IDEA (Community & Ultimate)
- WebStorm
- Android Studio
- PhpStorm
- PyCharm
- CLion
- GoLand
- Rider
- RubyMine
- DataGrip

**Requires:** IntelliJ Platform 2024.1+

---

## 📄 License

MIT License — see [LICENSE](LICENSE) for details.

---

## 🔗 Links

- 📌 **Landing Page:** [huuhuybn.github.io/MiConvert-Image-Compressor-Optimizer](https://huuhuybn.github.io/MiConvert-Image-Compressor-Optimizer/)
- 🌐 **Website:** [miconvert.com](https://miconvert.com)
- 🖼️ **Image Compressor:** [miconvert.com/en/compress-tools](https://miconvert.com/en/compress-tools)
- 📧 **Contact:** [miconvert.com/en/contact](https://miconvert.com/en/contact)
- 🐛 **Issues:** [GitHub Issues](https://github.com/huuhuybn/MiConvert-Image-Compressor-Optimizer/issues)

---

<p align="center">
  <strong>Powered by <a href="https://miconvert.com">MiConvert.com</a></strong><br>
  <sub>© 2026 MiConvert — Compress smarter, code faster.</sub>
</p>
