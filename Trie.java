package spell;

public class Trie implements ITrie{
    private final Node root = new Node();
    private int nodeCount = 1;
    private int wordCount = 0;


    public Node getRoot() {
        return root;
    }

    @Override
    public void add(String word) {
        String wordToAdd = word.toLowerCase();
        recurAdd(this.root, wordToAdd);
    }
    private void recurAdd(Node myNode, String wordToAdd) {
        if (wordToAdd.length() == 0) {
            if (myNode.getValue() == 0) {
                wordCount++;
            }
            myNode.incrementValue();
        }
        else {
            int letterIndex = (wordToAdd.charAt(0) % 97);
            if (myNode.getChildren()[letterIndex] == null) {
                myNode.getChildren()[letterIndex] = new Node();
                nodeCount++;
            }
            if (wordToAdd.length() == 1) {
                recurAdd(myNode.getChildren()[letterIndex], "");
            }
            else {
                recurAdd(myNode.getChildren()[letterIndex], wordToAdd.substring(1));
            }
        }
    }

    @Override
    public INode find(String word) {
        String wordToFind = word.toLowerCase();
        return recurFind(root, wordToFind);
    }
    private Node recurFind(Node myNode, String wordToFind) {
        if (wordToFind.length() == 0) {
            if (myNode.getValue() == 0) {
                return null;
            }
            else {
                return myNode;
            }
        }
        else {
            int letterIndex = (wordToFind.charAt(0) % 97);
            if (myNode.getChildren()[letterIndex] == null) {
                return null;
            }
            else {
                if (wordToFind.length() == 1) {
                    return recurFind(myNode.getChildren()[letterIndex], "");
                }
                else {
                    return recurFind(myNode.getChildren()[letterIndex], wordToFind.substring(1));
                }
            }
        }
    }

    @Override
    public int getWordCount() {
        return recurWordCount(root);
    }
    private int recurWordCount(Node nodeToCheck) {
        int myCounter = 0;
        for (int i = 0; i < 26; ++i) {
            if (nodeToCheck.getChildren()[i] != null) {
                if (nodeToCheck.getChildren()[i].getValue() > 0) {
                    myCounter += 1;
                }
                myCounter += recurWordCount(nodeToCheck.getChildren()[i]);
            }
        }
        return myCounter;
    }

    @Override
    public int getNodeCount() {
        return recurNodeCount(root) + 1;
    }
    private int recurNodeCount(Node nodeToCheck) {
        int myCounter = 0;
        for (int i = 0; i < 26; ++i) {
            if (nodeToCheck.getChildren()[i] != null) {
                myCounter++;
                myCounter += recurNodeCount(nodeToCheck.getChildren()[i]);
            }
        }
        return myCounter;
    }

    @Override
    public String toString() {
        return recurToString(root, new StringBuilder());
    }
    private String recurToString(Node nodeToString, StringBuilder tempStringBuilder) {
        StringBuilder finalStringBuilder = new StringBuilder();
        for (int i = 0; i < 26; ++i) {
            if (nodeToString.getChildren()[i] != null) {
                if (nodeToString.getChildren()[i].getValue() > 0) {
                    tempStringBuilder.append((char)(i + 97));
                    finalStringBuilder.append(tempStringBuilder.toString());
                    finalStringBuilder.append("\n");
                }
                else {
                    tempStringBuilder.append((char)(i + 97));
                }
                finalStringBuilder.append(recurToString(nodeToString.getChildren()[i], tempStringBuilder));
                tempStringBuilder.deleteCharAt(tempStringBuilder.length() - 1);
            }
        }
        return finalStringBuilder.toString();
    }

    @Override
    public int hashCode(){
        return getFirstIndexPlusOne(root) * wordCount * nodeCount;
    }
    private int getFirstIndexPlusOne(Node nodeToCheck) {
        for (int i = 0; i < 26; ++i) {
            if (nodeToCheck.getChildren()[i] != null) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Trie) {
            return recurEquals(root, ((Trie) o).getRoot());
        }
        else {
            return false;
        }
    }
    private boolean recurEquals(Node thisNode, Node nodeToCheck) {
        for (int i = 0; i < 26; ++i) {
            if ((nodeToCheck.getChildren()[i] != null) && (thisNode.getChildren()[i] != null)) {
                if (nodeToCheck.getChildren()[i].getValue() != thisNode.getChildren()[i].getValue()) {
                    return false;
                }
                if (!(recurEquals(thisNode.getChildren()[i], nodeToCheck.getChildren()[i]))) {
                    return false;
                }
            }
            else if (!((nodeToCheck.getChildren()[i] == null) && (thisNode.getChildren()[i] == null))) {
                return false;
            }
        }
        return true;
    }
}
