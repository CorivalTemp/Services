package org.realityfn.fortnite.core.utils;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.decoder.BrotliInputStream;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.aayushatharva.brotli4j.encoder.Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BrotliUtils {
    static {
        try {
            Brotli4jLoader.ensureAvailability();
            if (!Brotli4jLoader.isAvailable()) {
                throw new RuntimeException("Brotli4j native library is not available");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Brotli4j native library", e);
        }
    }
    /**
     * Compress data using Brotli algorithm
     * @param data The data to compress
     * @return Compressed data as byte array
     * @throws IOException if compression fails
     */
    public static byte[] compress(byte[] data) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             BrotliOutputStream brotliOut = new BrotliOutputStream(baos)) {

            brotliOut.write(data);
            brotliOut.flush();
            brotliOut.close();

            return baos.toByteArray();
        }
    }

    /**
     * Compress data using Brotli algorithm with custom quality level
     * @param data The data to compress
     * @param quality Compression quality (0-11, where 11 is highest compression)
     * @return Compressed data as byte array
     * @throws IOException if compression fails
     */
    public static byte[] compress(byte[] data, int quality) throws IOException {
        Encoder.Parameters params = new Encoder.Parameters().setQuality(quality);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             BrotliOutputStream brotliOut = new BrotliOutputStream(baos, params)) {

            brotliOut.write(data);
            brotliOut.flush();
            brotliOut.close();

            return baos.toByteArray();
        }
    }

    /**
     * Decompress Brotli compressed data
     * @param compressedData The compressed data to decompress
     * @return Decompressed data as byte array
     * @throws IOException if decompression fails
     */
    public static byte[] decompress(byte[] compressedData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
             BrotliInputStream brotliIn = new BrotliInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = brotliIn.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }

    /**
     * Compress string and return Base64 encoded result
     * @param text The text to compress
     * @return Base64 encoded compressed data
     * @throws IOException if compression fails
     */
    public static String compressToBase64(String text) throws IOException {
        byte[] compressed = compress(text.getBytes(StandardCharsets.UTF_8), 6);
        return Base64.getEncoder().encodeToString(compressed);
    }

    /**
     * Compress byte array and return Base64 encoded result
     * @param data The byte array to compress
     * @return Base64 encoded compressed data
     * @throws IOException if compression fails
     */
    public static String compressToBase64(byte[] data) throws IOException {
        byte[] compressed = compress(data, 6);
        return Base64.getEncoder().encodeToString(compressed);
    }

    /**
     * Decompress Base64 encoded Brotli data to string
     * @param base64CompressedData Base64 encoded compressed data
     * @return Decompressed string
     * @throws IOException if decompression fails
     */
    public static String decompressFromBase64(String base64CompressedData) throws IOException {
        byte[] compressedData = Base64.getDecoder().decode(base64CompressedData);
        byte[] decompressed = decompress(compressedData);
        return new String(decompressed, StandardCharsets.UTF_8);
    }
}