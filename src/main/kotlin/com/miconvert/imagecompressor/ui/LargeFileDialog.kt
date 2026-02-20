package com.miconvert.imagecompressor.ui

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import java.io.File
import javax.swing.Action
import javax.swing.JComponent

class LargeFileDialog(
    private val file: File
) : DialogWrapper(true) {

    init {
        title = "MiConvert — Large File Detected"
        setOKButtonText("Open Browser")
        setCancelButtonText("Cancel")
        init()
    }

    override fun createCenterPanel(): JComponent {
        val fileSizeMb = String.format("%.1f", file.length() / (1024.0 * 1024.0))

        return panel {
            row {
                icon(com.intellij.icons.AllIcons.General.Warning)
                label("<html><b>File exceeds 2MB</b> (${fileSizeMb}MB)</html>")
            }

            separator()

            row {
                text(
                    """
                    <html>
                    <p>To ensure high quality and preserve IDE performance,<br>
                    MiConvert will open your browser to process this large file.</p>
                    <br>
                    <p style="color: #888;">File: <b>${file.name}</b></p>
                    </html>
                    """.trimIndent()
                )
            }
        }
    }

    override fun doOKAction() {
        super.doOKAction()
        BrowserUtil.browse("https://miconvert.com/en/compress-tools")
    }
}
