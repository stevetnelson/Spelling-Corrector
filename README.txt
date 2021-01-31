This program takes a text file containing a list of words to be used as a dictionary and an inputted word.
If the inputted word does not match a word in the dictionary, a spelling correction is suggested. If the word is too dissimilar from any word in the dictionary, no suggestion is given.
The words in the dictionary are populated into a Trie (storing both spelling and occurance of all given words). The given suggested word is weighted in part according to the number of times a given word is in the dictionary.
For example, if there was a tie between the suggested word being "of" or "off", then the word that occured more frequently would be suggested.
The dictionary and suggested word are case insensitive.
While searching for suggestions, the algorithm checks any variation of the given word with one letter missing, any letter added at any point, switching any two adjacent letters, or replacing any letter in the word one at a time with all other letters.
If no suggestion is found in the dictionary with those given possibilities, then the same algorithm is run on each of the initial possibilities.
The command line arguments are first the file name of the dictionary and then the word to have its spelling corrected.
What is returned is the "Suggestion is" " and the suggested word or the statement "No similar word found".

Example input:
"java spell.Main myDictionary.txt offff"

Example output:
"Suggestion is: off"


Example input:
"java spell.Main wordsToCheck.txt hlleo"

Example output:
"Suggestion is: hello"


Example input:
"java spell.Main words.txt abcxyz"

Example output:
"No similar word found"