package com.aman.qrfilesharing.service;

import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService {
    public String generateQRCode(String text)
        throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(
            text,
            BarcodeFormat.QR_CODE,
            300,
            300
            
        );
        Path path = Paths.get("uploads", "qr.png");
        MatrixToImageWriter.writeToPath(
            bitMatrix,
            "PNG",
            path
        );

        return path.toString();
    }

}