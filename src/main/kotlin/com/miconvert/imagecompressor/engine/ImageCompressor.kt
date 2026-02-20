package com.miconvert.imagecompressor.engine

import com.miconvert.imagecompressor.settings.MiConvertSettings
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.stream.MemoryCacheImageOutputStream

object ImageCompressor {

    private val SUPPORTED_EXTENSIONS = setOf("png", "jpg", "jpeg", "webp")

    fun isSupportedImage(file: File): Boolean {
        return file.isFile && file.extension.lowercase() in SUPPORTED_EXTENSIONS
    }

    fun compress(file: File): CompressionResult {
        val settings = MiConvertSettings.getInstance()
        val extension = file.extension.lowercase()
        val originalSize = file.length()

        val image = ImageIO.read(file)
            ?: throw IllegalArgumentException("Cannot read image: ${file.name}")

        val compressedBytes = when (extension) {
            "jpg", "jpeg" -> compressJpeg(image, settings.jpegQuality)
            "png" -> compressPng(image)
            "webp" -> compressWebp(image)
            else -> throw IllegalArgumentException("Unsupported format: $extension")
        }

        val outputFile = getOutputFile(file, settings.overwriteOriginal)
        outputFile.writeBytes(compressedBytes)

        // Ensure the virtual file system picks up the change
        return CompressionResult(
            originalSize = originalSize,
            compressedSize = compressedBytes.size.toLong(),
            outputFile = outputFile,
            format = extension.uppercase()
        )
    }

    private fun compressJpeg(image: BufferedImage, quality: Int): ByteArray {
        val baos = ByteArrayOutputStream()

        // Convert to RGB if needed (JPEG doesn't support alpha)
        val rgbImage = if (image.type == BufferedImage.TYPE_INT_ARGB ||
            image.type == BufferedImage.TYPE_4BYTE_ABGR
        ) {
            val rgb = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)
            val g = rgb.createGraphics()
            g.drawImage(image, 0, 0, java.awt.Color.WHITE, null)
            g.dispose()
            rgb
        } else {
            image
        }

        val writers = ImageIO.getImageWritersByFormatName("jpeg")
        if (!writers.hasNext()) throw IllegalStateException("No JPEG writer available")

        val writer = writers.next()
        val param = writer.defaultWriteParam.apply {
            compressionMode = ImageWriteParam.MODE_EXPLICIT
            compressionQuality = quality / 100f
        }

        MemoryCacheImageOutputStream(baos).use { ios ->
            writer.output = ios
            writer.write(null, IIOImage(rgbImage, null, null), param)
            writer.dispose()
        }

        return baos.toByteArray()
    }

    private fun compressPng(image: BufferedImage): ByteArray {
        val baos = ByteArrayOutputStream()

        val writers = ImageIO.getImageWritersByFormatName("png")
        if (!writers.hasNext()) throw IllegalStateException("No PNG writer available")

        val writer = writers.next()
        val param = writer.defaultWriteParam

        // PNG compression via ImageIO — re-encodes with default (max) compression
        MemoryCacheImageOutputStream(baos).use { ios ->
            writer.output = ios
            writer.write(null, IIOImage(image, null, null), param)
            writer.dispose()
        }

        return baos.toByteArray()
    }

    private fun compressWebp(image: BufferedImage): ByteArray {
        // Try to use ImageIO WebP support if available
        val writers = ImageIO.getImageWritersByFormatName("webp")
        if (writers.hasNext()) {
            val baos = ByteArrayOutputStream()
            val writer = writers.next()
            val param = writer.defaultWriteParam

            MemoryCacheImageOutputStream(baos).use { ios ->
                writer.output = ios
                writer.write(null, IIOImage(image, null, null), param)
                writer.dispose()
            }

            return baos.toByteArray()
        }

        // Fallback: convert to PNG if WebP writer is not available
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        return baos.toByteArray()
    }

    private fun getOutputFile(original: File, overwrite: Boolean): File {
        return if (overwrite) {
            original
        } else {
            val nameWithoutExt = original.nameWithoutExtension
            val ext = original.extension
            File(original.parentFile, "${nameWithoutExt}_min.$ext")
        }
    }
}
