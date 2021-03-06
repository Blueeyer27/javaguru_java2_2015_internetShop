package lv.javaguru.java2.mock;

/**
 * Created by Anton on 2015.03.23..
 */

import java.io.*;
import java.nio.charset.Charset;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;

public class MockFileItem implements FileItem {

    private String fieldName;
    private String contentType;
    private String name;
    private byte[] content;
    private boolean formField;

    public MockFileItem(String fieldName, String content) {
        this.fieldName = fieldName;
        this.content = content.getBytes();
        this.formField = true;
    }

    public MockFileItem(String fieldName, String name, byte[] content) {
        this.fieldName = fieldName;
        this.contentType = "application/octet-stream";
        this.name = name;
        this.content = content;
    }

    public MockFileItem(String fieldName, String contentType, String name, byte[] content) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.name = name;
        this.content = content;
    }

    @Override
    public void delete() {

    }

    @Override
    public byte[] get() {
        return content;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public long getSize() {
        return content == null ? 0 : content.length;
    }

    @Override
    public String getString() {
        return new String(content);
    }

    @Override
    public String getString(String charsetName) throws UnsupportedEncodingException {
        try {
            return new String(content, Charset.forName(charsetName));
        }catch (Exception e) {
            throw new UnsupportedEncodingException();
        }
    }

    @Override
    public boolean isFormField() {
        return formField;
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public void setFieldName(String arg0) {

    }

    @Override
    public void setFormField(boolean arg0) {

    }

    @Override
    public void write(File arg0) throws Exception {

    }

    public FileItemHeaders getHeaders() {
        return null;
    }

    public void setHeaders(FileItemHeaders arg0) {

    }
}