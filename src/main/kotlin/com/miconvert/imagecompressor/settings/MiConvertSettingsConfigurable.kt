package com.miconvert.imagecompressor.settings

import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.*
import javax.swing.JComponent
import javax.swing.JPanel

class MiConvertSettingsConfigurable : Configurable {

    private var overwriteOriginal: Boolean = true
    private var jpegQuality: Int = 80
    private var mainPanel: JPanel? = null

    override fun getDisplayName(): String = "MiConvert"

    override fun createComponent(): JComponent {
        val settings = MiConvertSettings.getInstance()
        overwriteOriginal = settings.overwriteOriginal
        jpegQuality = settings.jpegQuality

        mainPanel = panel {
            group("File Output") {
                buttonsGroup("When compressing an image:") {
                    row {
                        radioButton("Overwrite the original file", true)
                            .comment("The original image will be replaced with the compressed version")
                    }
                    row {
                        radioButton("Create a new file with _min suffix", false)
                            .comment("e.g., photo.jpg → photo_min.jpg")
                    }
                }.bind(::overwriteOriginal)
            }

            group("Compression Quality") {
                row("JPEG Quality:") {
                    spinner(1..100, 5)
                        .bindIntValue(::jpegQuality)
                        .comment("Higher values = better quality, larger file size (default: 80)")
                }
            }

            group("Large File Handling") {
                row {
                    label("Files larger than ${settings.fileSizeThresholdMb}MB will be redirected to MiConvert.com for deep optimization.")
                }
                row {
                    browserLink("Visit MiConvert.com", "https://miconvert.com/en/compress-tools")
                }
            }

            group("About") {
                row {
                    label("MiConvert Image Compressor v1.0.0")
                }
                row {
                    comment("Compress and optimize images directly within your IDE.<br>Powered by <a href=\"https://miconvert.com\">MiConvert.com</a>")
                }
            }
        }

        return mainPanel!!
    }

    override fun isModified(): Boolean {
        val settings = MiConvertSettings.getInstance()
        return overwriteOriginal != settings.overwriteOriginal ||
                jpegQuality != settings.jpegQuality
    }

    override fun apply() {
        val settings = MiConvertSettings.getInstance()
        settings.overwriteOriginal = overwriteOriginal
        settings.jpegQuality = jpegQuality
    }

    override fun reset() {
        val settings = MiConvertSettings.getInstance()
        overwriteOriginal = settings.overwriteOriginal
        jpegQuality = settings.jpegQuality
    }

    override fun disposeUIResources() {
        mainPanel = null
    }
}
