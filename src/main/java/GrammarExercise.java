import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GrammarExercise {
    public static void main(String[] args) {
        //需要从命令行读入
        String firstWordList = "";
        String secondWordList = "";

        List<String> result = findCommonWordsWithSpace(firstWordList,secondWordList);
        //按要求输出到命令行

    }

    public static List<String> findCommonWordsWithSpace(String firstWordList, String secondWordList) {
        //在这编写实现代码

        String subWordsList = firstWordList + "," + secondWordList;

        Map<String, String> wordsMap = new HashMap<>();
        Map<String, String> repeatWordsMaps = new HashMap<>();

        String[] resultStrings = Stream.of(subWordsList.split(","))
                .filter(s -> {    // 排除连续逗号的情况
                    if (!s.equals("")) {
                        return true;
                    }else {
                        throw new RuntimeException("出现连续逗号的情况，非法输入");
                    }
                })
                .map(String::toLowerCase)    // 统一转为小写
                .filter(s -> {               // 去除含有非小写字母的单词，即去除非法单词
                    return Stream.of(s.split(""))
                            .allMatch(a -> {
                                if (a.toCharArray()[0] < 'a' || a.toCharArray()[0] > 'z') {
                                    throw new RuntimeException("出现非字母字符，非法输入");
                                }
                                return true;
                            });
                })
                .filter(s -> {     // 去除重复的单词
                    if (wordsMap.isEmpty()) {
                        wordsMap.put(s, s);
                        return true;
                    }
                    if (wordsMap.get(s) != null) {
                        repeatWordsMaps.put(s, s);
                        return false;
                    }else {
                        wordsMap.put(s,s);
                        return true;
                    }
                })
                .sorted()                     // 排序
                .toArray(String[]::new);

        // 正常结束，输出一个没有重复单词的List
        return Arrays.asList(resultStrings);

//         Test1:should_return_common_words_with_space
//        String[] repeatWords = repeatWordsMaps.entrySet().stream()
//                .map(Map.Entry::getKey)
//                .toArray(String[]::new);
//
//        String[] repeatStings = Stream.of(repeatWords)
//                .map(String::toUpperCase)
//                .map(s -> {
//                    StringBuffer sb = new StringBuffer();
//                    Stream.of(s.split(""))
//                            .allMatch(a -> {
//                                sb.append(a);
//                                sb.append(" ");
//                                return true;
//                            });
//
//                    String resultString = sb.toString();
//                    return resultString.substring(0, resultString.length() - 1);
//                })
//                .toArray(String[]::new);
//
//        return Arrays.asList(repeatStings);
    }
}
