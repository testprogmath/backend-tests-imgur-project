package ru.geekbrains.base;

public enum Images {
    MORE_THAN_10MB_IMAGE(""),
    PIXEL_1X1(""),
    IMAGE_URL("https://refactoring.guru/images/content-public/logos/logo-covid-2x.png");

    private String path;
    Images(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
