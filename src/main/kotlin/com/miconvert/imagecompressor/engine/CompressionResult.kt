package com.miconvert.imagecompressor.engine

import java.io.File

data class CompressionResult(
    val originalSize: Long,
    val compressedSize: Long,
    val outputFile: File,
    val format: String
) {
    val savedBytes: Long get() = originalSize - compressedSize
    val savedPercentage: Double get() = if (originalSize > 0) (savedBytes.toDouble() / originalSize * 100) else 0.0

    fun formatSummary(): String {
        val savedKb = savedBytes / 1024.0
        return "Compressed successfully! Saved ${String.format("%.1f", savedKb)}KB (${String.format("%.0f", savedPercentage)}%)"
    }
}
