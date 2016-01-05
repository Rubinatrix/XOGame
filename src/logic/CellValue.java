package logic;

public enum CellValue {
    
    VALUE_X ("X"),
    VALUE_O ("O"),
    NONE (" ");
    
    final private String textValue;

    private CellValue(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }
    
}
