package TestObjects;

public class EnumedType {
    public enum ItsAnEnum{
        enum1,
        enum2,
        enum3
    }

    public ItsAnEnum getAnEnum() {
        return anEnum;
    }

    public EnumedType(){}

    ItsAnEnum anEnum;

    public EnumedType(ItsAnEnum anEnum) {
        this.anEnum = anEnum;
    }

}
