package com.miconvert.imagecompressor.engine

import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.miconvert.imagecompressor.notifications.MiConvertNotifier
import java.io.File

class ImageCompressionTask(
    project: Project,
    private val files: List<File>
) : Task.Backgroundable(project, "MiConvert: Compressing images...", true) {

    override fun run(indicator: ProgressIndicator) {
        indicator.isIndeterminate = false
        var totalSaved: Long = 0
        var totalOriginal: Long = 0
        var successCount = 0
        var failCount = 0

        files.forEachIndexed { index, file ->
            if (indicator.isCanceled) return

            indicator.fraction = index.toDouble() / files.size
            indicator.text = "Compressing: ${file.name}"

            try {
                val result = ImageCompressor.compress(file)
                totalSaved += result.savedBytes
                totalOriginal += result.originalSize
                successCount++

                // Refresh the virtual file system to show changes
                LocalFileSystem.getInstance().refreshAndFindFileByIoFile(result.outputFile)
                    ?.refresh(true, false)

                // Also refresh the original file's parent
                LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file.parentFile)
                    ?.refresh(true, false)
            } catch (e: Exception) {
                failCount++
                MiConvertNotifier.notifyError(
                    project,
                    "Failed to compress ${file.name}: ${e.message}"
                )
            }
        }

        indicator.fraction = 1.0
        indicator.text = "Compression complete"

        // Show summary notification
        if (successCount > 0) {
            val savedKb = totalSaved / 1024.0
            val percentage = if (totalOriginal > 0) (totalSaved.toDouble() / totalOriginal * 100) else 0.0

            if (files.size == 1) {
                MiConvertNotifier.notifySuccess(
                    project,
                    "✅ Compressed successfully! Saved ${String.format("%.1f", savedKb)}KB (${String.format("%.0f", percentage)}%)"
                )
            } else {
                MiConvertNotifier.notifySuccess(
                    project,
                    "✅ Compressed $successCount image(s). Total saved: ${String.format("%.1f", savedKb)}KB (${String.format("%.0f", percentage)}%)" +
                            if (failCount > 0) " · $failCount failed" else ""
                )
            }
        }
    }
}
