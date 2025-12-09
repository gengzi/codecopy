package fun.gengzi.algorithm;

public class CanConstruct {
    public boolean canConstruct(String ransomNote, String magazine) {
        // 通过hash
        int[] ints = new int[26];
        char[] charArray1 = ransomNote.toCharArray();
        char[] charArray = magazine.toCharArray();

        /**
         * 所有字母的个数
         */
        for (int i = 0; i < charArray.length; i++) {
            ints[charArray[i] - 'a']++;
        }


        for (int i = 0; i < charArray1.length; i++) {

            if (ints[charArray1[i] - 'a'] < 0) {
                return false;
            }
            ints[charArray1[i] - 'a']--;
        }
        return true;

    }
}
