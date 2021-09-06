package ru.geekbrains.base;

public enum Images {
    MORE_THAN_10MB_IMAGE("", ""),
    PIXEL_1X1("", "image/jpeg"),
    IMAGE_URL("https://refactoring.guru/images/content-public/logos/logo-covid-2x.png", "image/png"),
    ORDINARY_FILE("src/test/resources/img.png", "image/png");

    private String path;
    private String format;
    Images(String path, String format) {
        this.path = path;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String getPath() {
        return path;
    }
}
