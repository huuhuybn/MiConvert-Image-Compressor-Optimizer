package com.miconvert.imagecompressor.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.miconvert.imagecompressor.engine.ImageCompressor
import com.miconvert.imagecompressor.engine.ImageCompressionTask
import com.miconvert.imagecompressor.settings.MiConvertSettings
import com.miconvert.imagecompressor.ui.LargeFileDialog
import java.io.File

class CompressImageAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = virtualFile != null &&
                !virtualFile.isDirectory &&
                ImageCompressor.isSupportedImage(File(virtualFile.path))
    }

    override fun actionPerformed(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val project = e.project ?: return
        val file = File(virtualFile.path)

        if (!file.exists()) return

        val settings = MiConvertSettings.getInstance()
        val thresholdBytes = settings.fileSizeThresholdMb * 1024L * 1024L

        if (file.length() > thresholdBytes) {
            // Large file — show dialog to redirect to browser
            val dialog = LargeFileDialog(file)
            dialog.show()
        } else {
            // Small file — compress in background
            ImageCompressionTask(project, listOf(file)).queue()
        }
    }
}
