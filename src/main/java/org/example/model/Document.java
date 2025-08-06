package org.example.model;

public class Document {
    private final long id;
    private final String fileName;
    private final String filePath;
    private final String mimeType;
    private final long sizeBytes;
    private final String createdAt;
    private final String sha256;
    private final boolean ocrDone;

    public Document(long id, String fileName, String filePath, String mimeType,
                    long sizeBytes, String createdAt, String sha256, boolean ocrDone) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.mimeType = mimeType;
        this.sizeBytes = sizeBytes;
        this.createdAt = createdAt;
        this.sha256 = sha256;
        this.ocrDone = ocrDone;
    }

    public long getId() { return id; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public String getMimeType() { return mimeType; }
    public long getSizeBytes() { return sizeBytes; }
    public String getCreatedAt() { return createdAt; }
    public String getSha256() { return sha256; }
    public boolean isOcrDone() { return ocrDone; }
}
