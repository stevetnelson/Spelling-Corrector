package spell;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    Trie myTrie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next();
            myTrie.add(word);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String word = inputWord.toLowerCase();
        if (myTrie.find(word) != null) {
            return word;
        }
        String distOneString = this.distOneSimilarWord(word);
        if (distOneString != null) {
            return distOneString;
        }
        return this.distTwoSimilarWord(word);
    }
    private String distOneSimilarWord(String inputWord) {
        ArrayList<String> possibleWords = getSimilarWords(inputWord);
        return this.parseResults(possibleWords);
    }
    private String distTwoSimilarWord(String inputWord) {
        ArrayList<String> possibleWords = new ArrayList<>();
        StringBuilder inputWordBuilder = new StringBuilder(inputWord);
        for (int i = 0; i < inputWord.length(); ++i) {
            possibleWords.addAll(this.getSimilarWords(inputWordBuilder.deleteCharAt(i).toString()));
            inputWordBuilder.setLength(0);
            inputWordBuilder.append(inputWord);
        }
        for (int i = 0; i < inputWord.length() - 1; ++i) {
            inputWordBuilder.setLength(0);
            inputWordBuilder.append(inputWord);
            char tempChar = inputWordBuilder.charAt(i);
            inputWordBuilder.setCharAt(i, inputWordBuilder.charAt(i + 1));
            inputWordBuilder.setCharAt(i + 1, tempChar);
            possibleWords.addAll(this.getSimilarWords(inputWordBuilder.toString()));
        }
        for (int i = 0; i < inputWord.length(); ++i) {
            for (int j = 0; j < 26; ++j) {
                inputWordBuilder.setLength(0);
                inputWordBuilder.append(inputWord);
                inputWordBuilder.setCharAt(i, (char)(j + 97));
                possibleWords.addAll(this.getSimilarWords(inputWordBuilder.toString()));
            }
        }
        for (int i = 0; i < inputWord.length() + 1; ++i) {
            for (int j = 0; j < 26; ++j) {
                inputWordBuilder.setLength(0);
                inputWordBuilder.append(inputWord);
                inputWordBuilder.insert(i, (char)(j + 97));
                possibleWords.addAll(this.getSimilarWords(inputWordBuilder.toString()));
            }
        }

        return this.parseResults(possibleWords);
    }
    private ArrayList<String> getSimilarWords(String inputWord) {
        ArrayList<String> possibleWords = new ArrayList<>();
        StringBuilder inputWordBuilder = new StringBuilder(inputWord);
        for (int i = 0; i < inputWord.length(); ++i) {
            if (myTrie.find(inputWordBuilder.deleteCharAt(i).toString()) != null) {
                possibleWords.add(inputWordBuilder.toString());
            }
            inputWordBuilder.setLength(0);
            inputWordBuilder.append(inputWord);
        }
        for (int i = 0; i < inputWord.length() - 1; ++i) {
            inputWordBuilder.setLength(0);
            inputWordBuilder.append(inputWord);
            char tempChar = inputWordBuilder.charAt(i);
            inputWordBuilder.setCharAt(i, inputWordBuilder.charAt(i + 1));
            inputWordBuilder.setCharAt(i + 1, tempChar);
            if (myTrie.find(inputWordBuilder.toString()) != null) {
                possibleWords.add(inputWordBuilder.toString());
            }
        }
        for (int i = 0; i < inputWord.length(); ++i) {
            for (int j = 0; j < 26; ++j) {
                inputWordBuilder.setLength(0);
                inputWordBuilder.append(inputWord);
                inputWordBuilder.setCharAt(i, (char)(j + 97));
                if (myTrie.find(inputWordBuilder.toString()) != null) {
                    possibleWords.add(inputWordBuilder.toString());
                }
            }
        }
        for (int i = 0; i < inputWord.length() + 1; ++i) {
            for (int j = 0; j < 26; ++j) {
                inputWordBuilder.setLength(0);
                inputWordBuilder.append(inputWord);
                inputWordBuilder.insert(i, (char)(j + 97));
                if (myTrie.find(inputWordBuilder.toString()) != null) {
                    possibleWords.add(inputWordBuilder.toString());
                }
            }
        }
        return possibleWords;
    }
    private String parseResults(ArrayList<String> possibleWords) {
        if (possibleWords.size() == 1) {
            return possibleWords.get(0);
        }
        else if (possibleWords.size() != 0) {
            ArrayList<String> suggestions = new ArrayList<>();
            int maxCount = 0;
            for (int i = 0; i < possibleWords.size(); ++i) {
                int counter = myTrie.find(possibleWords.get(i)).getValue();
                if (counter > maxCount) {
                    maxCount = counter;
                    suggestions.clear();
                    suggestions.add(possibleWords.get(i));
                }
                else if (counter == maxCount) {
                    suggestions.add(possibleWords.get(i));
                }
            }
            if (suggestions.size() == 1) {
                return suggestions.get(0);
            }
            Collections.sort(suggestions);
            return suggestions.get(0);
        }
        return null;
    }
}
