public enum Stufe {
    Genin, Chunin, Jonin, Kage;

    public static Stufe fromString(String str) {

        return Stufe.valueOf(str);
    }
}
