package com.miconvert.imagecompressor.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.APP)
@State(
    name = "com.miconvert.imagecompressor.settings.MiConvertSettings",
    storages = [Storage("MiConvertSettings.xml")]
)
class MiConvertSettings : PersistentStateComponent<MiConvertSettings> {

    var overwriteOriginal: Boolean = true
    var jpegQuality: Int = 80
    var fileSizeThresholdMb: Int = 2

    override fun getState(): MiConvertSettings = this

    override fun loadState(state: MiConvertSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        @JvmStatic
        fun getInstance(): MiConvertSettings {
            return ApplicationManager.getApplication().getService(MiConvertSettings::class.java)
        }
    }
}
