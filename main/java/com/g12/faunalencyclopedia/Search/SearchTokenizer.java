package com.g12.faunalencyclopedia.Search;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author UID:u7574003 Name: Andy Chih
 */
 
public class SearchTokenizer {

    // Define patterns for each token (need to be changed)
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");
    private static final Pattern WORD_PATTERN = Pattern.compile("\\w+");

    public List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();

        Matcher hashtagMatcher = HASHTAG_PATTERN.matcher(input);
        Matcher wordMatcher = WORD_PATTERN.matcher(input);

        while (hashtagMatcher.find()) {
            tokens.add(hashtagMatcher.group());
        }

        while (wordMatcher.find()) {
            tokens.add(wordMatcher.group());
        }

        return tokens;
    }
}
