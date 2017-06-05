package main.java;

/**
 * Created by cezar on 6/1/17.
 */
public class HZTest {
    public static void main(String[] args) {
        System.out.println(doubleChar("abcdabdc", 'c'));
    }

    public String doubleChar(String s, char ch) {
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        for (int i = 0, j = s.length() - 1; i < s.length() / 2 && j >= s.length() / 2; i++, j--) {
            if (s.charAt(i) == ch) {
                start.append(ch).append(ch);
            } else {
                start.append(s.charAt(i));
            }
            if (s.charAt(j) == ch) {
                end.append(ch).append(ch);
            } else {
                end.append(s.charAt(j));
            }
        }
        end.reverse();
        return start.append(end).toString();
    }
}
