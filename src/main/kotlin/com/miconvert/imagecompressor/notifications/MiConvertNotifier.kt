package com.miconvert.imagecompressor.notifications

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object MiConvertNotifier {

    private const val GROUP_ID = "MiConvert Notifications"

    fun notifySuccess(project: Project?, message: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(
                "MiConvert",
                message,
                NotificationType.INFORMATION
            )
            .notify(project)
    }

    fun notifyError(project: Project?, message: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(
                "MiConvert",
                message,
                NotificationType.ERROR
            )
            .notify(project)
    }

    fun notifyWarning(project: Project?, message: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(
                "MiConvert",
                message,
                NotificationType.WARNING
            )
            .notify(project)
    }
}
