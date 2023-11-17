public class OffByN implements CharacterComparator {

    private int N;

    public OffByN(int x) {
        this.N = x;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return Math.abs(diff) == this.N;
    }
}
