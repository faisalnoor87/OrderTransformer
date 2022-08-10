package com.order.summary.transformer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class FileService implements IFile {

    @Override
    public byte[] readFile(String fileName) throws IOException {
        log.info("START : readFile");

        var file = ResourceUtils.getFile(fileName);
        var stream = new FileInputStream(file);

        var payload = stream.readAllBytes();
        stream.close();

        log.info("END : readFile");
        return payload;
    }

    @Override
    public void writeFile(String fileName, byte[] data) throws IOException {
        log.info("START : writeFile");

        var out = new FileOutputStream(fileName);
        out.write(data);
        out.close();

        log.info("END : writeFile");
    }
}
