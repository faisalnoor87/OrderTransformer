package com.order.summary.transformer;

import java.io.IOException;

public interface IFile {
    byte[] readFile(String fileName) throws IOException;
    void writeFile(String fileName, byte[] data) throws IOException;
}
