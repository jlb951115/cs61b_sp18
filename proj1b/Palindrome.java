public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> D = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            D.addLast(word.charAt(i));
        }
        return D;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> ref = wordToDeque(word);
        while (ref.size() > 0) {
            if (ref.size() == 1) {
                break;
            } else {
                Character a = ref.removeFirst();
                Character b = ref.removeLast();
                if (a != b) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> ref = wordToDeque(word);
        while (ref.size() > 0) {
            if (ref.size() == 1) {
                break;
            } else {
                Character a = ref.removeFirst();
                Character b = ref.removeLast();
                if (!cc.equalChars(a, b)) {
                    return false;
                }
            }
        }
        return true;
    }
}
