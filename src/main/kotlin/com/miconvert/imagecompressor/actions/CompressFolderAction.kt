package com.miconvert.imagecompressor.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.miconvert.imagecompressor.engine.ImageCompressor
import com.miconvert.imagecompressor.engine.ImageCompressionTask
import com.miconvert.imagecompressor.notifications.MiConvertNotifier
import com.miconvert.imagecompressor.settings.MiConvertSettings
import com.miconvert.imagecompressor.ui.LargeFileDialog
import java.io.File

class CompressFolderAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = virtualFile != null && virtualFile.isDirectory
    }

    override fun actionPerformed(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val project = e.project ?: return
        val directory = File(virtualFile.path)

        if (!directory.exists() || !directory.isDirectory) return

        val settings = MiConvertSettings.getInstance()
        val thresholdBytes = settings.fileSizeThresholdMb * 1024L * 1024L

        // Recursively find all supported image files
        val allImages = directory.walkTopDown()
            .filter { ImageCompressor.isSupportedImage(it) }
            .toList()

        if (allImages.isEmpty()) {
            MiConvertNotifier.notifyWarning(project, "No image files (JPG, PNG, WebP) found in this folder.")
            return
        }

        // Separate small and large files
        val smallFiles = allImages.filter { it.length() <= thresholdBytes }
        val largeFiles = allImages.filter { it.length() > thresholdBytes }

        // Compress small files in background
        if (smallFiles.isNotEmpty()) {
            ImageCompressionTask(project, smallFiles).queue()
        }

        // Notify about large files
        if (largeFiles.isNotEmpty()) {
            val names = largeFiles.joinToString(", ") { it.name }
            MiConvertNotifier.notifyWarning(
                project,
                "⚠️ ${largeFiles.size} file(s) exceed ${settings.fileSizeThresholdMb}MB " +
                        "and need web processing: $names"
            )
            // Show dialog for the first large file
            LargeFileDialog(largeFiles.first()).show()
        }
    }
}
