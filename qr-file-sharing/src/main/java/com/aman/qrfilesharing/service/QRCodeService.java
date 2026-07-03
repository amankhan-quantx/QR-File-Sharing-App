package com.aman.qrfilesharing.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService {

        @Value("${app.upload-dir}")
        private String uploadDir;

        // ======================================
        // Logger
        // ======================================

                private static final Logger logger =
                        LoggerFactory.getLogger(QRCodeService.class);

        // ======================================
        // QR Code Generation
        // ======================================

                public String generateQRCode(
                        String text,
                        Long id)
                        throws WriterException, IOException {

                        QRCodeWriter qrCodeWriter =
                                new QRCodeWriter();

                        BitMatrix bitMatrix =
                                qrCodeWriter.encode(
                                        text,
                                        BarcodeFormat.QR_CODE,
                                        300,
                                        300
                                );

                        String qrFileName = "qr_" + id + ".png";

                        Files.createDirectories(Paths.get(uploadDir));

                        Path path = Paths.get(uploadDir, qrFileName);

                        MatrixToImageWriter.writeToPath(
                                bitMatrix,
                                "PNG",
                                path
                        );

                        logger.info(
                                "QR Code generated successfully: {}",
                                qrFileName
                        );

                        return path.toString();
                }

}