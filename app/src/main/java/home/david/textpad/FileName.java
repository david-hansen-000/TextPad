package home.david.textpad;

import android.support.v4.provider.DocumentFile;

public class FileName {
    private String name;
    private DocumentFile file;

    public FileName(DocumentFile file) {
        this.file = file;
        name=file.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentFile getFile() {
        return file;
    }

    public void setFile(DocumentFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return name;
    }
}
