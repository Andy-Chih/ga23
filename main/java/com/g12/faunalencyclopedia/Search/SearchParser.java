package com.g12.faunalencyclopedia.Search;
import java.util.List;

/**
 * @author UID:u7574003 Name: Andy Chih
 */
 
public class SearchParser {

    private final List<String> tokens;
    private int index = 0;

    public SearchParser(List<String> tokens) {
        this.tokens = tokens;
    }

    public Object parse() throws SyntaxException {
        if (tokens.isEmpty()) {
            return new ErrorQuery("Input is empty. Please enter a valid query.");
        }
        if (index >= tokens.size()) {
            return new ErrorQuery("Unexpected end of input. Please check your query syntax.");
        }

        String token = tokens.get(index);

        // Check if the token contains only letters
        for (char ch : token.toCharArray()) {
            if (!Character.isLetter(ch) && ch != '#') {  // Allow '#' for hashtag queries
                return new ErrorQuery("Please enter it in: '#class' or 'animal name' or '#class + animal name'! ");
            }
        }


        if (token.startsWith("#")) {
            return parseHashtagQuery();
        } else {
            return parseWordQuery();
        }
    }

    private Object parseHashtagQuery() throws SyntaxException {
        if (index >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input while parsing hashtag query.");
        }
        String hashtag = tokens.get(index++);
        // Process the hashtag token, e.g., remove the '#' prefix
        return new HashtagQuery(hashtag.substring(1));
    }



    private Object parseWordQuery() throws SyntaxException {
        if (index >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input while parsing word query.");
        }
        String word = tokens.get(index++);
        return new WordQuery(word);
    }
}
